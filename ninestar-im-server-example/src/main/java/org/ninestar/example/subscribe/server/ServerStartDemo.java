package org.ninestar.example.subscribe.server;

import org.ninestar.im.server.NineStarImSerResponse;
import org.ninestar.im.server.NineStarImServer;
import org.ninestar.im.server.config.EnableNineStarImServer;
import org.ninestar.im.server.handler_v0.NineStarImMsgSerV0Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 服务器启动实例
 *
 */
@SpringBootApplication
@EnableNineStarImServer(port=12345, host="localhost")
@Controller
@RequestMapping
public class ServerStartDemo {
	public static void main(String[] args) {
		SpringApplication.run(ServerStartDemo.class, args);
	}
	
	@Autowired
	NineStarImServer nineStarImServer;
	
	@RequestMapping("/ok")
	@ResponseBody
	public String ok() {
		NineStarImSerResponse response = NineStarImMsgSerV0Response.createNineStarImMsgSerV0Response("/subscribe/ok");
		response.setBody(("time:" + System.currentTimeMillis()).getBytes());
		nineStarImServer.send(response);
		return "ok";
	}
}
