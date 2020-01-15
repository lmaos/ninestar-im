package org.ninestar.example.tweserver.s2;

import org.ninestar.im.client.NineStarImCliRequest;
import org.ninestar.im.client.NineStarImClient;
import org.ninestar.im.client.error.NineStarCliRequestTimeoutException;
import org.ninestar.im.client.handle_v0.NineStarImMsgCliV0Request;
import org.ninestar.im.client.handle_v0.NineStarImV0Output;
import org.ninestar.im.nameser.NineStarNameser;
import org.ninestar.im.server.config.EnableNineStarImServer;
import org.ninestar.im.server.config.EnableNineStarZkRegister;
import org.ninestar.im.server.handler_v0.NineStarImMsgSerV0Response;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@EnableNineStarImServer(port=7789, host="localhost")
@EnableNineStarZkRegister
@Controller
public class TweServerDemo2 implements InitializingBean{
	
	public static void main(String[] args) {
		SpringApplication.run(TweServerDemo2.class, args);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("启动完成");
	}
	@Autowired
	NineStarNameser nameser; 
	
	@ResponseBody
	@RequestMapping("/ok")
	public String ok() throws NineStarCliRequestTimeoutException {
		NineStarImClient client = nameser.getNineStarImClient("ns:localhost:7788");
		NineStarImV0Output output = client.getNineStarImV0Output();
		NineStarImCliRequest request = new NineStarImMsgCliV0Request("/test/ok");
		System.out.println(output.sendSync(request).getMsgPackId());
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("/ok2")
	public String ok2() throws NineStarCliRequestTimeoutException {
		NineStarImMsgSerV0Response response = NineStarImMsgSerV0Response.createNineStarImMsgSerV0Response("/subscribe/ok");
		response.setBody("time: "+ System.currentTimeMillis());
		nameser.send(response);
		return "ok";
	}

}
