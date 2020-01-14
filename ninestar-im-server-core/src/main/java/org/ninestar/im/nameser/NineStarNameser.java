package org.ninestar.im.nameser;

import org.ninestar.im.client.NineStarImClient;
import org.ninestar.im.server.NineStarImSerResponse;
import org.ninestar.im.server.NineStarImServer;

public interface NineStarNameser {
	// 进行注册
	void register(NineStarImServer server);// 注册服务
	
	// 获得服务对应的客户端
	NineStarImClient getNineStarImClient(String serverId);
	
	void send(String sourceId, String[] targerIds, NineStarImSerResponse response);
}
