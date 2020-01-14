package org.ninestar.example.tweserver.s1;

import org.ninestar.im.server.NineStarImSerResponse;
import org.ninestar.im.server.controller.ann.NineStarSerController;
import org.ninestar.im.server.controller.ann.NineStarSerUri;

@NineStarSerController
@NineStarSerUri("/test")
public class TControle {

	
	@NineStarSerUri("/ok")
	void ok(NineStarImSerResponse resp) {
		System.out.println("我被访问了");
		resp.setState(0);
	}
}
