package org.ninestar.im.nameser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.ninestar.im.server.NineStarImServer;
import org.ninestar.im.utils.IPAddress;
import org.ninestar.im.utils.IPAddress.IP;
import org.ninestar.im.utils.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class ZookeeperRegister {
	private ZooKeeper zk = null;
	public final static String SERVER_CONFIG_ROOT_PATH = "/server";
	public final static String SERVER_CONFIG_ADDR_PATH = "/server/addrs";

	private static final Logger log = LoggerFactory.getLogger(ZookeeperRegister.class);
	private static ScheduledExecutorService exec = Executors.newScheduledThreadPool(1,
			new Named("zookeeperRegister", true));
	private Map<String, List<SerAddr>> serverAddrs = new ConcurrentHashMap<>();

	public ZookeeperRegister(String connectString, int sessionTimeout) throws IOException {
		CountDownLatch cd = new CountDownLatch(1);
		zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				if (event.getState() == KeeperState.SyncConnected) {
					cd.countDown();
				} 
			}
		});
		try {
			cd.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		createNode(SERVER_CONFIG_ROOT_PATH, "ok", CreateMode.PERSISTENT);
		createNode(SERVER_CONFIG_ADDR_PATH, "ok", CreateMode.PERSISTENT);
		initZookeeperRegister();
	}

	private void initZookeeperRegister() {
		initServer();
		exec.scheduleWithFixedDelay(this::initServer, 0, 3, TimeUnit.SECONDS);
	}

	private void initServer() {
		try {

			Map<String, List<SerAddr>> serverAddrs = new ConcurrentHashMap<>();
			List<String> serverIds = zk.getChildren(SERVER_CONFIG_ADDR_PATH, false);
			for (String serverId : serverIds) {
				String path = SERVER_CONFIG_ADDR_PATH + "/" + serverId;
				String dataJson = getData(path, "UTF-8");
				List<SerAddr> addrs = JSON.parseArray(dataJson, SerAddr.class);
				serverAddrs.put(serverId, addrs);
			}
			this.serverAddrs = serverAddrs;
		} catch (Exception e) {
			log.error("获取服务器集合发生异常", e);
		}
	}

	/**
	 * 创建或更新节点, 如果节点存在则更新节点的数据
	 * 
	 * @param path
	 * @param data
	 * @param createMode
	 */
	public void createNode(String path, String data, CreateMode createMode) {
		try {
			Stat stat = zk.exists(path, false);
			if (stat == null) {
				zk.create(path, data.getBytes("UTF-8"), Ids.OPEN_ACL_UNSAFE, createMode);
			} else {
				zk.setData(path, data.getBytes("UTF-8"), stat.getVersion());
			}
		} catch (Exception e) {
			log.error("创建或修改节点发生异常", e);
		}
	}

	/**
	 * 删除节点
	 * 
	 * @param path
	 */
	public boolean deleteNode(String path) {
		try {
			Stat stat = zk.exists(path, false);
			if (stat != null) {
				zk.delete(path, stat.getVersion());
				return true;
			}
		} catch (Exception e) {
			log.error("删除节点发生异常", e);
		}
		return false;
	}

	/**
	 * 删除节点并获取值
	 * 
	 * @param path
	 */
	public byte[] deleteNodeAndGet(String path) {
		try {
			Stat stat = zk.exists(path, false);
			if (stat != null) {
				byte[] data = zk.getData(path, false, stat);
				zk.delete(path, stat.getVersion());
				return data;
			}
		} catch (Exception e) {
			log.error("删除节点发生异常", e);
		}
		return null;
	}

	/**
	 * 指定编码，获得path存储的数据，是一个字符串类型的
	 * 
	 * @param path
	 * @param charsetName
	 *            编码
	 * @return
	 */
	public String getData(String path, String charsetName) {
		byte[] data = getData(path);
		if (data == null) {
			return null;
		}
		return new String(data, Charset.forName(charsetName));
	}

	/**
	 * 获得path存储的数据
	 * 
	 * @param path
	 * @return
	 */
	public byte[] getData(String path) {
		try {
			Stat stat = zk.exists(path, false);
			if (stat != null) {
				byte[] data = zk.getData(path, false, stat);
				return data;
			}
		} catch (Exception e) {
			log.error("获取节点数据发生异常", e);
		}
		return null;
	}

	public void register(NineStarImServer server) {
		String host = server.getHost();
		int port = server.getPort();
		String serverId = server.getServerId();
		List<SerAddr> serAddrs = new ArrayList<SerAddr>();
		if (host.equals("localhost")) {
			List<IP> ips = IPAddress.getIp4s();
			for (IP ip : ips) {
				if (ip.getIp().equals("127.0.0.1")) {
					continue;
				}
				SerAddr addr = new SerAddr();
				addr.setName(ip.getInterfaceName());
				addr.setHost(ip.getIp());
				addr.setPort(port);
				serAddrs.add(addr);
			}
		} else {
			SerAddr addr = new SerAddr();
			addr.setName("default");
			addr.setHost(host);
			addr.setPort(port);
			serAddrs.add(addr);
		}

		String path = SERVER_CONFIG_ADDR_PATH + "/" + serverId;
		String data = JSON.toJSONString(serAddrs);
		createNode(path, data, CreateMode.EPHEMERAL);
	}

	public Map<String, List<SerAddr>> getServerAddrs() {
		return serverAddrs;
	}
}
