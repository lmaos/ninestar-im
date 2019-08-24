package org.ninestar.im.client.handle_v0;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.ninestar.im.client.NineStarImCliResponse;
import org.ninestar.im.client.error.NineStarCliRequestTimeoutException;

public class SyncResult {

	private volatile NineStarImCliResponse response;
	private ReentrantLock lock = new ReentrantLock();
	private Condition condition = null;

	public void syncSetResult(NineStarImCliResponse response) {
		lock.lock();
		this.response = response;
		if (condition != null) {
			condition.signal();
		}
		lock.unlock();
	}

	public NineStarImCliResponse getResponse(long awaitTime) throws NineStarCliRequestTimeoutException {

		lock.lock();
		try {
			if (response == null) {
				condition = lock.newCondition();
				if (!condition.await(awaitTime, TimeUnit.MILLISECONDS)) {
					throw new NineStarCliRequestTimeoutException();
				}
//				condition.await();
			}
		} catch (InterruptedException e) {
			throw new NineStarCliRequestTimeoutException();
		} finally {
			lock.unlock();
		}

		return response;
	}
//	public static void main(String[] args)  {
//		SyncResult r = new SyncResult();
//		
//		try {
//			r.getResponse(1000);
//			System.out.println("dsd");
//		} catch (NineStarCliRequestTimeoutException e) {
//			e.printStackTrace();
//			System.out.println("dsd");
//		}
//	}
}
