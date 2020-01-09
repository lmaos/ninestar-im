package org.ninestar.example.zkdemo;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.ninestar.im.utils.Sleep;

public class ZkTempNodeDemo {
	
	
	public static void main(String[] args) throws Exception {
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
		zk.create("/nnn", "ok".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		Sleep.sleep(10000);
		zk.close();
	}
	
	
}
