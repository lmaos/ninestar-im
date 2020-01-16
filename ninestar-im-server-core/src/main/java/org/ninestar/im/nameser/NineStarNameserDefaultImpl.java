package org.ninestar.im.nameser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.ninestar.im.client.ConstMPID;
import org.ninestar.im.client.NineStarImClient;
import org.ninestar.im.client.error.NineStarClientConnectionException;
import org.ninestar.im.client.handle_v0.NineStarImV0Output;
import org.ninestar.im.msgcoder.MsgPackage;
import org.ninestar.im.server.NineStarImSerResponse;
import org.ninestar.im.server.NineStarImServer;
import org.ninestar.im.server.handler_v1.NineStarImMsgSerV1Request;
import org.ninestar.im.server.handler_v1.NineStarImSerV1Handler;
import org.ninestar.im.utils.BoxIdUtils;
import org.ninestar.im.utils.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NineStarNameserDefaultImpl implements NineStarNameser {
	private Map<String, NineStarImClient> clients = new ConcurrentHashMap<String, NineStarImClient>();
	private Map<Long, RetrySend> retrySends = new ConcurrentHashMap<Long, RetrySend>();
	private NineStarImServer currentServer;
	private ScheduledExecutorService task = Executors.newScheduledThreadPool(2, Named.newThreadFactory("nameser"));
	private ZookeeperRegister zookeeperRegister;
	
	private static final Logger log = LoggerFactory.getLogger(NineStarNameserDefaultImpl.class);

	public NineStarNameserDefaultImpl(String connectString) {
		try {
			zookeeperRegister = new ZookeeperRegister(connectString, 3000); 
		} catch (Exception e) {
			throw new NineStarNameserException("zookeeper fail", e);
		}
		initServerConnection();
		task.scheduleWithFixedDelay(this::retrySendsRun, 10, 10, TimeUnit.SECONDS);			// 重发请求
		task.scheduleWithFixedDelay(this::initServerConnection, 0, 10, TimeUnit.SECONDS); // 初始化服务连接
	}

	void retrySendsRun() {
		Set<Entry<Long, RetrySend>> entries = retrySends.entrySet();
		for (Entry<Long, RetrySend> entry : entries) {
			RetrySend retrySend = entry.getValue();
			if (retrySend.getSize() >= 25) {
				log.error("重试发送消息出现故障: serId=" + retrySend.getServerId() 
				+ ", msgId="+retrySend.getMsgPackage().getMsgId());
			} else if (retrySend.isTimeout()) {
				String serverId = retrySend.getServerId();
				MsgPackage msgPackage = retrySend.getMsgPackage();
				NineStarImClient client = getNineStarImClient(serverId);
				if (client != null) {
					client.getNineStarImV0Output().send(msgPackage, (resp) -> {
						long msgId = resp.getMsgPackId();
						NineStarNameserDefaultImpl.this.retrySends.remove(msgId);
					});
					retrySend.incr();	
				}
			}
		}
	}
	
	void initServerConnection() {
		Map<String, NineStarImClient> clients_tmp = new ConcurrentHashMap<String, NineStarImClient>();
		Map<String, List<SerAddr>> serverAddrs = zookeeperRegister.getServerAddrs();
		for (Entry<String, List<SerAddr>> entry : serverAddrs.entrySet()) {
			String serverId = entry.getKey();
			NineStarImClient client = clients.get(serverId);
			if (client != null && client.isStart()) {
				clients_tmp.put(serverId, client);
				continue;
			}
			List<SerAddr> addrs = entry.getValue();
			for (int i = addrs.size() - 1; i >= 0; i--) {
				try {
					SerAddr addr = addrs.get(i);
					String host = addr.getHost();
					int port = addr.getPort();
					client = new NineStarImClient(host, port, serverId);
					clients_tmp.put(serverId, client);
					break;
				} catch (NineStarClientConnectionException e) {
					
				}
			}
		}
		
		for (Entry<String, NineStarImClient> entry : this.clients.entrySet()) {
			if (!serverAddrs.containsKey(entry.getKey())) {
				entry.getValue().close();
			}
		}
		this.clients = clients_tmp;
	}

	@Override
	public void register(NineStarImServer server) {
		if (this.currentServer == null) {
			this.currentServer = server;
			zookeeperRegister.register(server);
			server.register(this);
		}
	}

	@Override
	public NineStarImClient getNineStarImClient(String serverId) {

		return clients.get(serverId);
	}

	@Override
	public void send(String[] targerIds, NineStarImSerResponse response) {
		Map<String, Set<String>> targerIdsgroup = new HashMap<String, Set<String>>();
		for (String targerId : targerIds) {
			String serverId = BoxIdUtils.getServerId(targerId, targerId);
			Set<String> set = targerIdsgroup.get(serverId);
			if (set == null) {
				set = new HashSet<>();
				targerIdsgroup.put(serverId, set);
			}
			set.add(targerId);
		}
		for (Entry<String, Set<String>> entry : targerIdsgroup.entrySet()) {
			String serverId = entry.getKey();
			Set<String> targerIdSet = entry.getValue();
			
			if (serverId.equals(this.currentServer.getServerId())) {
				sendCurrentServer(targerIdSet, response);
			} else {
				send(serverId, targerIdSet, response);
			}
		}
	}
	
	private void sendCurrentServer(Set<String> targerIds, NineStarImSerResponse response) {
		long msgPackId = ConstMPID.nextId();
		NineStarImSerV1Handler.send(msgPackId, response.toMsgPackage(), targerIds, currentServer);
	}

	private void send(String serverId, Set<String> targerIds, NineStarImSerResponse response) {
		NineStarImClient client = getNineStarImClient(serverId);
		if (client == null) {
			// 目标服务不存在
			return;
		}
		 NineStarImV0Output output = new NineStarImV0Output(client, 3000);
		Set<String> targerIdSet = targerIds;
		NineStarImMsgSerV1Request v1req = new NineStarImMsgSerV1Request();
		if (targerIds == null) {
			v1req.getHead().setSendall(true);	
		} else {
			v1req.getHead().addTargeIdAll(targerIdSet);
		}
		v1req.setBody(response.toMsgPackage());
		
		MsgPackage msgPackage = v1req.toMsgPackage();
//		retrySends.put(serverId, new RetrySend(sourceId, serverId, targerIdSet, output.getReadTimeout() + 1000, response));
		output.send(msgPackage, (resp) -> {
			long msgId = resp.getMsgPackId();
			NineStarNameserDefaultImpl.this.retrySends.remove(msgId);
		});
	}

	public NineStarImServer getCurrentServer() {
		return currentServer;
	}

	@Override
	public void send(NineStarImSerResponse response) {
		Set<String> serverIds = clients.keySet();
		for (String serverId : serverIds) {
			if (serverId.equals(this.currentServer.getServerId())) {
				sendCurrentServer(null, response);
			} else {
				send(serverId, null, response);
			}
		}
	}
}
