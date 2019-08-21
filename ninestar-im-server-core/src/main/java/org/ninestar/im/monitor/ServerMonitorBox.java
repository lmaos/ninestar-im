package org.ninestar.im.monitor;

public class ServerMonitorBox<T> {
	private String boxId;
	private T value;
	private long timeout;
	private long createTime;
	private long updateTime;
	private boolean destroy;
	private long updateCount;
	private static Object lock = new Object();

	public ServerMonitorBox(String boxId, T value, long timeout) {
		this.boxId = boxId;
		this.value = value;
		this.timeout = timeout;
		this.createTime = System.currentTimeMillis();
		this.updateTime = this.createTime;
		this.destroy = false;
	}

	public boolean isTimeout() {
		return System.currentTimeMillis() - updateTime > timeout;
	}

	public T getValue() {
		return value;
	}

	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}

	public boolean isDestroy() {
		return destroy;
	}

	public String getBoxId() {
		return boxId;
	}

	public void updateTime() {
		synchronized (lock) {
			this.updateCount++;
		}
		this.updateTime = System.currentTimeMillis();
	}

	public long getUpdateCount() {
		synchronized (lock) {
			return updateCount;
		}
	}
}
