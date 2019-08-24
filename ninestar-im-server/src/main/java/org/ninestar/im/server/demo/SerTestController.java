package org.ninestar.im.server.demo;

import org.ninestar.im.server.controller.ann.NineStarSerController;
import org.ninestar.im.server.controller.ann.NineStarSerUri;
import org.ninestar.im.server.handler_v0.NineStarImMsgSerV0Response;

@NineStarSerController
@NineStarSerUri("/test")
public class SerTestController {

	@NineStarSerUri("/hello")
	public void hello(NineStarImMsgSerV0Response response) {
		System.out.println("哥就是不一般");
		response.setBody("你看到了我的不一般吗：" + System.currentTimeMillis());
		
	}

}
