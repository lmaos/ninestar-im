package org.ninestar.im.server.demo;

import org.ninestar.im.server.controller.ann.NineStarSerController;
import org.ninestar.im.server.controller.ann.NineStarSerUri;

@NineStarSerController
@NineStarSerUri("/test")
public class SerTestController {

	@NineStarSerUri("/hello")
	public void hello() {
		System.out.println("哥就是不一般");
	}

}
