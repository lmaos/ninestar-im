package org.ninestar.im.utils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class Named implements ThreadFactory {
	private ThreadGroup tg;
	private String name;
	private boolean daemon;
	private static AtomicLong gid = new AtomicLong();
	private static AtomicLong tid = new AtomicLong();

	public Named(String name, boolean daemon) {
		this.name = name;
		this.tg = new ThreadGroup(name + "-g-" + gid.incrementAndGet());
		this.daemon = daemon;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread tr = new Thread(tg, r, name + "-t-" + tid.incrementAndGet());
		tr.setDaemon(daemon);
		return tr;
	}
	
	public static ThreadFactory newThreadFactory(String name) {
		return new Named(name, true);
	}

}
