package org.ninestar.im.client.subscribe;

import java.util.concurrent.ExecutorService;

/**
 * 推送的消息订阅
 * @author zhangxingyu
 *
 */
public interface NineStarSubscribe {
	
	public SubscribeFuture addSubscribe(String uri, UriSubscribe subscribe);

	public SubscribeFuture addSubscribe(String[] urixs, UriSubscribe subscribe);
	
	public NineStarSubscribe setThreadPool(ExecutorService executorService);
}
