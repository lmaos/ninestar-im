package org.ninestar.im.client.handle_v0;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.ninestar.im.client.NineStarImCliResponse;
import org.ninestar.im.client.error.NineStarCliRequestTimeoutException;

public class SyncResult {

	private NineStarImCliResponse response;
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
				condition.await(awaitTime, TimeUnit.MICROSECONDS);
			}
		} catch (InterruptedException e) {
			throw new NineStarCliRequestTimeoutException();
		} finally {
			lock.unlock();
		}

		return response;
	}
}
