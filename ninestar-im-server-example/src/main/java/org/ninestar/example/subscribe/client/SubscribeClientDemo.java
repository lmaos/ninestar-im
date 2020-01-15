package org.ninestar.example.subscribe.client;

import java.util.concurrent.Executors;

import org.ninestar.im.client.NineStarImCliResponse;
import org.ninestar.im.client.NineStarImClient;
import org.ninestar.im.client.error.NineStarCliRequestTimeoutException;
import org.ninestar.im.client.error.NineStarClientConnectionException;
import org.ninestar.im.client.subscribe.NineStarSubscribe;
import org.ninestar.im.client.subscribe.UriSubscribe;
import org.ninestar.im.utils.Named;

public class SubscribeClientDemo {
	public static void main(String[] args) throws NineStarClientConnectionException, NineStarCliRequestTimeoutException {
		NineStarImClient c = new NineStarImClient("172.22.89.2", 12345);
		NineStarSubscribe nineStarSubscribe = c.getNineStarSubscribe();
		// 设置订阅线程池
		nineStarSubscribe.setThreadPool(Executors.newFixedThreadPool(3, Named.newThreadFactory("nineStarSubscribe")));
		
		// 增加订阅服务器的推送消息
		c.getNineStarSubscribe().addSubscribe("/subscribe/ok", new UriSubscribe() {
			
			@Override
			public void onmessage(NineStarImCliResponse response) throws Exception {
				System.out.println("订阅："+response.bodyToString());
			}
		});
		
		
//		c.close();
	}
}
