package org.ninestar.example.tweserver.client;

import java.util.concurrent.Executors;

import org.ninestar.im.client.NineStarImCliResponse;
import org.ninestar.im.client.NineStarImClient;
import org.ninestar.im.client.error.NineStarCliRequestTimeoutException;
import org.ninestar.im.client.error.NineStarClientConnectionException;
import org.ninestar.im.client.subscribe.NineStarSubscribe;
import org.ninestar.im.client.subscribe.UriSubscribe;
import org.ninestar.im.utils.Named;

public class TweSubscribeClientDemo {
	public static void main(String[] args) throws NineStarClientConnectionException, NineStarCliRequestTimeoutException {
		NineStarImClient c = new NineStarImClient("localhost", 7789);
		NineStarSubscribe nineStarSubscribe = c.getNineStarSubscribe();
		// 设置订阅线程池
		nineStarSubscribe.setThreadPool(Executors.newFixedThreadPool(3, Named.newThreadFactory("nineStarSubscribe")));
		
		// 增加订阅服务器的推送消息
		c.getNineStarSubscribe().addSubscribe("/subscribe/ok", new UriSubscribe() {
			
			@Override
			public void onmessage(NineStarImCliResponse response) throws Exception {
				System.out.println("订阅89："+response.bodyToString());
			}
		});
		System.out.println("订阅服务启动");
		
//		c.close();
	}
}
