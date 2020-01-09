package org.ninestar.example.zkdemo;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.ZooKeeper;

public class ZkNodeCreateDemo {
	public static void main(String[] args) throws Exception{
		CountDownLatch c = new CountDownLatch(1);
		ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 10000, new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				if (event.getState() == KeeperState.SyncConnected) {
					c.countDown();
				}
			}
		});
		c.await();
		Stat stat = zk.exists("/guosheng", false);
		if (stat == null) {
			zk.create("/guosheng", "ok".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} else {
			zk.delete("/guosheng", stat.getVersion());
			zk.create("/guosheng", "23".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		
		zk.close();
	}
}
