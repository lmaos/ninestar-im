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
import org.ninestar.im.server.NineStarImSerResponse;
import org.ninestar.im.server.NineStarImServer;
import org.ninestar.im.server.handler_v1.NineStarImMsgSerV1Request;
import org.ninestar.im.server.handler_v1.NineStarImSerV1Handler;
import org.ninestar.im.utils.BoxIdUtils;
import org.ninestar.im.utils.Named;

public class NineStarNameserDefaultImpl implements NineStarNameser {
	private Map<String, NineStarImClient> clients = new ConcurrentHashMap<String, NineStarImClient>();
	private Map<String, RetrySend> retrySends = new ConcurrentHashMap<String, RetrySend>();
	private NineStarImServer currentServer;
	private ScheduledExecutorService task = Executors.newScheduledThreadPool(2, Named.newThreadFactory("nameser"));
	private ZookeeperRegister zookeeperRegister;
	public NineStarNameserDefaultImpl(String connectString) {
		try {
			zookeeperRegister = new ZookeeperRegister(connectString, 20000); 
		} catch (Exception e) {
			throw new NineStarNameserException("zookeeper fail", e);
		}
		initServerConnection();
		task.scheduleWithFixedDelay(this::retrySendsRun, 10, 10, TimeUnit.SECONDS);			// 重发请求
		task.scheduleWithFixedDelay(this::initServerConnection, 0, 10, TimeUnit.SECONDS); // 初始化服务连接
	}

	void retrySendsRun() {
		for (Entry<String, RetrySend> entry : retrySends.entrySet()) {
			RetrySend retrySend = entry.getValue();
			if (retrySend.isTimeout()) {
				String sourceId = retrySend.getSourceId();
				String serverId = retrySend.getServerId();
				Set<String> targerIds = retrySend.getTargerIds();
				NineStarImSerResponse response = retrySend.getResponse();
				send(sourceId, serverId, targerIds, response);
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
					client = new NineStarImClient(host, port);
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
		this.currentServer = server;
		zookeeperRegister.register(server);
	}

	@Override
	public NineStarImClient getNineStarImClient(String serverId) {

		return clients.get(serverId);
	}

	@Override
	public void send(String sourceId, String[] targerIds, NineStarImSerResponse response) {
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
				sendCurrentServer(sourceId, targerIdSet, response);
			} else {
				send(sourceId, serverId, targerIdSet, response);
			}
		}
	}
	
	private void sendCurrentServer(String sourceId, Set<String> targerIds, NineStarImSerResponse response) {
		long msgPackId = ConstMPID.nextId();
		NineStarImSerV1Handler.send(msgPackId, response.toMsgPackage(), targerIds, currentServer);
	}

	private void send(String sourceId, String serverId, Set<String> targerIds, NineStarImSerResponse response) {
		NineStarImClient client = getNineStarImClient(serverId);
		if (client == null) {
			// 目标服务不存在
			return;
		}
		NineStarImV0Output output = new NineStarImV0Output(client, 3000);
		Set<String> targerIdSet = targerIds;
		retrySends.put(serverId,
				new RetrySend(sourceId, serverId, targerIdSet, output.getReadTimeout() + 1000, response));
		NineStarImMsgSerV1Request v1req = new NineStarImMsgSerV1Request(sourceId);
		if (targerIds == null) {
			v1req.getHead().setSendall(true);	
		} else {
			v1req.getHead().addTargeIdAll(targerIdSet);
		}
		
		
		v1req.setBody(response.toMsgPackage());
		client.getNineStarImV0Output().send(v1req.toMsgPackage(), (resp) -> {
			String respServerId = (String) resp.getHead().get("serverId");
			NineStarNameserDefaultImpl.this.retrySends.remove(respServerId);
		});
	}

	public NineStarImServer getCurrentServer() {
		return currentServer;
	}

	@Override
	public void send(String sourceId, NineStarImSerResponse response) {
		Set<String> serverIds = clients.keySet();
		for (String serverId : serverIds) {
			if (serverId.equals(this.currentServer.getServerId())) {
				sendCurrentServer(sourceId, null, response);
			} else {
				send(sourceId, serverId, null, response);
			}
		}
	}
}
