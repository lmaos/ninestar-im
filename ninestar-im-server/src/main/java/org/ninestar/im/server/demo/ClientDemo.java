package org.ninestar.im.server.demo;

import org.ninestar.im.client.NineStarImCliRespCallback;
import org.ninestar.im.client.NineStarImClient;
import org.ninestar.im.client.handle_v0.NineStarImMsgCliV0Request;
import org.ninestar.im.client.handle_v0.NineStarImMsgCliV0Response;
import org.ninestar.im.client.handle_v0.NineStarImV0Output;
import org.ninestar.im.utils.Sleep;

/**
 * 客户端的demo
 *
 */
public class ClientDemo {
	public static void main(String[] args) {
		NineStarImClient client = new NineStarImClient("localhost", 7788);
		try {
			// 获得 V0版本输出对象
			NineStarImV0Output output = client.getNineStarImV0Output();
			NineStarImMsgCliV0Request request = new NineStarImMsgCliV0Request("/test/hello");
			// 发送消息 异步回调处理
			output.send(request, new NineStarImCliRespCallback<NineStarImMsgCliV0Response>() {

				@Override
				public void onMessage(NineStarImMsgCliV0Response response) {
					// 异步的消息
					System.out
							.println(String.format("异步的消息 state:%d,  uri:%s", response.getState(), response.getUri()));
				}
			});
			
			// 发送一个同步消息
			NineStarImMsgCliV0Response response = output.sendSync(request);
			// 得到结果
			System.out.println(String.format("同步的消息 state:%d,  uri:%s", response.getState(), response.getUri()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			Sleep.sleep(1000);
			client.close();
		}

	}
}
