package org.ninestar.im.client.subscribe;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import org.ninestar.im.client.NineStarImCliResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscribeController implements NineStarSubscribe {

	private final static Object lock = new Object();
	
	private static final Logger log = LoggerFactory.getLogger(SubscribeController.class);

	private Map<String, List<UriSubscribe>> uris = new ConcurrentHashMap<>();

	private ExecutorService executorService = null;
	
	public SubscribeFuture addSubscribe(String uri, UriSubscribe subscribe) {
		return addSubscribe(new String[] { uri }, subscribe);
	}

	public SubscribeFuture addSubscribe(String[] urixs, UriSubscribe subscribe) {
		for (String uri : urixs) {
			List<UriSubscribe> list = uris.get(uri);
			if (list == null) {
				synchronized (lock) {
					list = uris.get(uri);
					if (list == null) {
						list = Collections.synchronizedList(new LinkedList<>());						
						uris.put(uri, list);
					}
				}
			}
			list.add(subscribe);
		}
		return new SubscribeFuture(urixs, subscribe, this);
	}

	public void onmessage(String uri, NineStarImCliResponse response) {
		List<UriSubscribe> list = uris.get(uri);
		if (list == null) {
			return;
		}
		synchronized (list) {
			for (UriSubscribe subscribe : list) {
					if (executorService == null) {
						onmessage(subscribe, response);
					} else {
						executorService.execute(()->{
							onmessage(subscribe, response);
						});
					}
			}
		}
	}
	
	void onmessage(UriSubscribe subscribe, NineStarImCliResponse response) {
		try {
			subscribe.onmessage(response);
		} catch (Exception e) {
			log.error("订阅消息执行发生异常", e);
		}
	}
	
	void unsubscribe(String uri, UriSubscribe unsubscribe) {
		List<UriSubscribe> list = uris.get(uri);
		if (list == null) {
			return;
		}
		list.remove(unsubscribe);
	}
	
	@Override
	public NineStarSubscribe setThreadPool(ExecutorService executorService) {
		this.executorService = executorService;
		return this;
	}
}
