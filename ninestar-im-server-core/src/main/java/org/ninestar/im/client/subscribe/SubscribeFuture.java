package org.ninestar.im.client.subscribe;

public class SubscribeFuture {
	private String uris[];
	private UriSubscribe subscribe;
	private SubscribeController subscribeController;
	public SubscribeFuture(String[] uris, UriSubscribe subscribe, SubscribeController subscribeController) {
		super();
		this.uris = uris;
		this.subscribe = subscribe;
		this.subscribeController = subscribeController;
	}
	
	public void unsubscribe() {
		for (String uri : uris) {
			subscribeController.unsubscribe(uri, subscribe);
		}
	} 
}
