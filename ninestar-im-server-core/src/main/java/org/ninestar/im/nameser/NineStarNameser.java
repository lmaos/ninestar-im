package org.ninestar.im.nameser;

import java.util.Set;

import org.ninestar.im.client.NineStarImClient;
import org.ninestar.im.server.NineStarImSerResponse;
import org.ninestar.im.server.NineStarImServer;
/**
 * 服务器命名注册
 *
 */
public interface NineStarNameser {
	// 进行注册
	void register(NineStarImServer server);// 注册服务
	
	// 获得服务对应的客户端
	NineStarImClient getNineStarImClient(String serverId);
	/**
	 * 向客户端推送消息
	 * @param targerIds 客户端目标的标识ID
	 * @param response	发送的消息
	 */
	void send(String[] targerIds, NineStarImSerResponse response);
	/**
	 * 向全平台客户端推送消息
	 * @param response	发送的消息
	 */
	void send(NineStarImSerResponse response);
	
	/**
	 * 获得已经注册的所有serverId
	 * @return
	 */
	Set<String> getServerIds();
}
