package org.ninestar.im.client.handle_v0;

import org.ninestar.im.client.NineStarImCliRespCallback;

public class CallbackCacheData {

	private NineStarImCliRespCallback callback;
	private long createTime = 0;
	private long timeout = 0;
	private String url;

	public CallbackCacheData(NineStarImCliRespCallback callback, long timeout, String url) {
		super();
		this.callback = callback;
		this.createTime = System.currentTimeMillis();
		this.timeout = timeout;
		this.url = url;
	}

	public NineStarImCliRespCallback getCallback() {
		return callback;
	}

	public boolean isTimeout() {
		return System.currentTimeMillis() - createTime > timeout;
	}

	public String getUrl() {
		return url;
	}
}
