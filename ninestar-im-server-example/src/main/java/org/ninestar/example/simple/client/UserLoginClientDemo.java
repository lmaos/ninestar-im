package org.ninestar.example.simple.client;

import org.ninestar.im.client.NineStarImClient;
import org.ninestar.im.client.error.NineStarCliRequestTimeoutException;
import org.ninestar.im.client.handle_v0.NineStarImMsgCliV0Request;
import org.ninestar.im.client.handle_v0.NineStarImMsgCliV0Response;
import org.ninestar.im.client.handle_v0.NineStarImV0Output;

public class UserLoginClientDemo {
	public static void main(String[] args) {
		NineStarImClient c = new NineStarImClient("172.22.89.2", 12345);
		NineStarImV0Output out = c.getNineStarImV0Output();

		// 注册的请求
		NineStarImMsgCliV0Request signupReq = new NineStarImMsgCliV0Request("/user/signup");
		signupReq.setHeaderValue("username", "ninestar");
		signupReq.setHeaderValue("password", "123456");

		try {
			// 注册
			NineStarImMsgCliV0Response signupResp = out.sendSync(signupReq);

			int state = signupResp.getState(); // 返回的状态
			String msg = signupResp.getMsg(); // 返回的消息
			System.out.println("signupResp: " + signupResp);
			System.out.println(String.format("signupResp: state:%d,msg:%s", state, msg));
		} catch (NineStarCliRequestTimeoutException e) {
			System.out.println("请求超时");
		}

//		// 登陆的请求
//		NineStarImMsgCliV0Request loginReq = new NineStarImMsgCliV0Request("/user/login");
//		loginReq.setHeaderValue("username", "ninestar");
//		loginReq.setHeaderValue("password", "123456");
//
//		try {
//			// 登陆一下
//			NineStarImMsgCliV0Response loginResp = out.sendSync(loginReq);
//
//			int state = loginResp.getState(); // 返回的状态
//			String msg = loginResp.getMsg(); // 返回的消息
//			System.out.println("loginResp: " + loginResp);
//			System.out.println(String.format("loginResp: state:%d,msg:%s", state, msg));
//		} catch (NineStarCliRequestTimeoutException e) {
//			System.out.println("请求超时");
//		}
		c.close();
	}
}
