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
		// 获得 V0版本输出对象
		NineStarImV0Output output = client.getNineStarImV0Output();
		NineStarImMsgCliV0Request request = new NineStarImMsgCliV0Request("/test/hello");

		output.send(request, new NineStarImCliRespCallback<NineStarImMsgCliV0Response>() {

			@Override
			public void onMessage(NineStarImMsgCliV0Response response) {
				System.out.println("state:" + response.getState());
			}
		});

		try {
			NineStarImMsgCliV0Response response = output.sendSync(request);
			System.out.println(response);
			System.out.println(response.getState());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			Sleep.sleep(1000);
			client.close();
		}

		// 结束此功能
		NineStarImV0Output.closeThread();
	}
}
