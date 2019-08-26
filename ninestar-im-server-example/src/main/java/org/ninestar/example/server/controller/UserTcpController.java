package org.ninestar.example.server.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.ninestar.im.server.controller.ann.NineStarSerController;
import org.ninestar.im.server.controller.ann.NineStarSerUri;
import org.ninestar.im.server.handler_v0.NineStarImMsgSerV0Response;
import org.ninestar.im.utils.Utils;
import org.springframework.web.bind.annotation.RequestParam;

@NineStarSerController
@NineStarSerUri("/user")
public class UserTcpController {

	Map<String, String> signupMap = new ConcurrentHashMap<String, String>();

	@NineStarSerUri("/login")
	public void login(@RequestParam("username") String username, @RequestParam("password") String password,
			NineStarImMsgSerV0Response response) {
		String passwd = signupMap.get(username);
		if (passwd == null) {
			response.setState(10);
			response.setMsg("用户名不存在");
		} else if (passwd.equals(password)) {
			String token = Utils.getRandomString(Utils.BASIC_CHARS, 64);
			response.setBody(token);
			response.setMsg("登陆成功");
		} else {
			response.setState(11);
			response.setMsg("密码错误");
		}
	}

	@NineStarSerUri("/signup")
	public void signup(@RequestParam("username") String username, @RequestParam("password") String password,
			NineStarImMsgSerV0Response response) {
		String key = username;
		if (signupMap.containsKey(key)) {
			response.setState(10);
			response.setMsg("已经存在用户名");
		} else {
			signupMap.put(username, password);
			response.setMsg("注册成功");
		}

	}
}
