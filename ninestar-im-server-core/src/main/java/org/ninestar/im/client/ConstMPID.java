package org.ninestar.im.client;

import java.util.concurrent.atomic.AtomicLong;

public class ConstMPID {

	private static AtomicLong id = new AtomicLong(System.currentTimeMillis());

	public static long nextId() {
		return id.incrementAndGet();
	}
}
