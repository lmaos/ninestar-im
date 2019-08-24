package org.ninestar.im.utils;

public class Sleep {

	public static boolean sleep(long ms) {
		try {
			Thread.sleep(ms);
			return true;
		} catch (InterruptedException e) {
			return false;
		}
	}
	
}
