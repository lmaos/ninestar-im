package org.ninestar.example.subscribe.server;

import java.nio.charset.Charset;

import org.ninestar.im.server.NineStarImSerResponse;
import org.ninestar.im.server.ServerUser;
import org.ninestar.im.server.controller.ann.ClientId;
import org.ninestar.im.server.controller.ann.NineStarSerController;
import org.ninestar.im.server.controller.ann.NineStarSerUri;
import org.ninestar.im.server.handler_v0.NineStarImMsgSerV0Response;

@NineStarSerController
@NineStarSerUri("/info")
public class Controller {
	
	@NineStarSerUri("/clientId")
	public void clientId(@ClientId String clientId, NineStarImSerResponse response) {
		response.setBody(clientId.getBytes(Charset.forName("UTF-8")));
	}
	@NineStarSerUri("/test")
	public void test(ServerUser serverUser, NineStarImSerResponse response) {
		NineStarImSerResponse responsex = NineStarImMsgSerV0Response.crateNineStarImMsgSerV0Response("/subscribe/ok");
		responsex.setBody("123456789".getBytes());
		serverUser.send(responsex);
	}
	
	
}
