package org.ninestar.im.monitor;

@FunctionalInterface
public interface ServerMonitorHandler<T> {
	/**
	 * 发生状态变化 销毁或超时
	 * 
	 * @param monitorBox
	 */
	public void timeout(ServerMonitorBox<T> monitorBox);

	default public void destroy(ServerMonitorBox<T> monitorBox) {
		timeout(monitorBox);
	}

}
