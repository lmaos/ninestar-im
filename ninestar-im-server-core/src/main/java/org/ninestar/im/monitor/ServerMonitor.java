package org.ninestar.im.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerMonitor<T> {

	private static final Logger log = LoggerFactory.getLogger(ServerMonitor.class);

	Map<String, ServerMonitorBox<T>> boxs = new ConcurrentHashMap<>();
	private static AtomicLong ids = new AtomicLong();
	private static AtomicLong tid = new AtomicLong();
	private Random r = new Random();
	private Thread thread = null;
	private long listenerTime = 30000;
	private volatile boolean close = false;
	private List<ServerMonitorHandler<T>> handlers = new ArrayList<>();

	public ServerMonitorBox<T> createBoxAndPutMonitor(T value, long timeout) {
		return createBoxAndPutMonitor(null, value, timeout);
	}
	/**
	 * 创建一个监控BOX
	 * @param boxId  如果值为 null 则随机生成一个 ID
	 * @param value
	 * @param timeout
	 * @return
	 */
	public ServerMonitorBox<T> createBoxAndPutMonitor(String boxId, T value, long timeout) {
		if (close) {
			throw new RuntimeException("监控器已经关闭，无法创建新的监控盒子");
		}
		if (boxId == null || boxId.isEmpty()) {
			boxId = String.format("%d-%d-%d", System.currentTimeMillis(), r.nextInt(10000) + 10000,
					ids.incrementAndGet());
		}
		ServerMonitorBox<T> monitorBox = boxs.get(boxId);
		if (monitorBox == null) {
			monitorBox = new ServerMonitorBox<T>(boxId, value, timeout);
		}
		monitorBox.updateTime();
		boxs.put(boxId, monitorBox);
		return monitorBox;
	}

	public ServerMonitor() {
		start();
	}

	public ServerMonitor(long listenerTime) {
		this.listenerTime = listenerTime;
		start();
	}

	private ServerMonitor<T> start() {
		String threadName = "ServerMonitor-" + tid.incrementAndGet();
		try {
			log.info(String.format("创建 %s 线程", threadName));
			thread = new Thread(this::run, threadName);
			thread.setDaemon(true);
			thread.start();
		} catch (Exception e) {
			log.error(String.format("启动 %s 线程失败", threadName));
			throw e;
		}
		return this;
	}

	private void run() {
		log.info(String.format("启动 %s 线程成功", Thread.currentThread().getName()));
		while (listenerSleep()) {
			List<String> remIds = new ArrayList<String>();
			for (Entry<String, ServerMonitorBox<T>> entry : boxs.entrySet()) {
				ServerMonitorBox<T> monitorBox = entry.getValue();
				boolean boolTimeout = monitorBox.isTimeout();
				boolean boolDestroy = monitorBox.isDestroy();
				if (boolTimeout || boolDestroy) {
					remIds.add(entry.getKey());
					for (ServerMonitorHandler<T> serverMonitorHandler : handlers) {
						try {
							if (boolTimeout) {
								serverMonitorHandler.timeout(monitorBox);
							} else if (boolDestroy) {
								serverMonitorHandler.destroy(monitorBox);
							}
						} catch (Exception e) {
							log.error("监控盒子处理发生异常", e);
						}
					}
				}

			}
			for (String key : remIds) {
				boxs.remove(key);
			}
		}
		log.info(String.format("关闭 %s 线程成功", Thread.currentThread().getName()));
	}

	public ServerMonitor<T> addMonitorHandler(ServerMonitorHandler<T> handler) {
		this.handlers.add(handler);
		return this;
	}

	private boolean listenerSleep() {
		try {
			Thread.sleep(listenerTime);
		} catch (InterruptedException e) {

		}
		return !close;
	}

	public boolean isClose() {
		return close;
	}

	public void close() {
		this.close = true;
		thread.interrupt();
	}

	public void remove(ServerMonitorBox<T> box) {
		this.boxs.remove(box.getBoxId());
	} 
	
	public ServerMonitorBox<T> getBox(String boxId) {
		return this.boxs.get(boxId);
	}
	
//	public static void main(String[] args) throws InterruptedException {
//		ServerMonitor x = new ServerMonitor<>(1000);
//		x.createBoxAndPutMonitor(100, 1000);
//		x.createBoxAndPutMonitor(101, 1000);
//		x.createBoxAndPutMonitor(103, 1000);
//		x.createBoxAndPutMonitor(104, 1100);
//		x.addMonitorHandler(new ServerMonitorHandler() {
//
//			@Override
//			public void timeout(ServerMonitorBox monitorBox) {
//				System.out.println("box-val:" + monitorBox.getValue());
//			}
//		});
//		Thread.sleep(4000);
//		x.close();
//		Thread.sleep(100);
//	}
}
