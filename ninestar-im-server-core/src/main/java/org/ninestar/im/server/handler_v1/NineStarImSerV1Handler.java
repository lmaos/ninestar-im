package org.ninestar.im.server.handler_v1;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.ninestar.im.monitor.ServerMonitor;
import org.ninestar.im.monitor.ServerMonitorBox;
import org.ninestar.im.msgcoder.MsgPackage;
import org.ninestar.im.msgcoder.MsgUtils;
import org.ninestar.im.server.NineStarImSerHandler;
import org.ninestar.im.server.NineStarImSerVxHandler;
import org.ninestar.im.server.NineStarImServer;
import org.ninestar.im.utils.Named;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

/**
 * 用于转发的消息处理
 *
 */
@Component("handlerV1")
public class NineStarImSerV1Handler implements NineStarImSerVxHandler {

	private static Map<String, Long> mpids = new ConcurrentHashMap<String, Long>(128);
	private static ScheduledExecutorService task = Executors.newScheduledThreadPool(1, Named.newThreadFactory("mpids"));

	public static NineStarImMsgSerV1Response send(long msgPackId, MsgPackage resp, Set<String> targeIds,
			NineStarImServer server) {
		
		ServerMonitor<NineStarImSerHandler> monitor = server.getMonitor();
		if (targeIds == null) {
			targeIds = monitor.getBoxIdSet();
		}
		int successSize = 0;
		int totalSize = targeIds.size();
		for (String targeId : targeIds) {
			ServerMonitorBox<NineStarImSerHandler> box = monitor.getBox(targeId);
			if (box != null) {
				successSize++;
				box.getValue().writeAndFlush(resp);
			}
		}
		NineStarImMsgSerV1Response response = new NineStarImMsgSerV1Response(msgPackId);
		response.setState(0);
		response.setMsg("ACK");
		response.put("serverId", server.getServerId());
		response.put("successSize", successSize);
		response.put("totalSize", totalSize);
		return response;
	}

	@Override
	public void handler(MsgPackage msg, ChannelHandlerContext ctx, NineStarImSerHandler nsih) throws Exception {
		long msgPackId = msg.getMsgId();
		String clientId = nsih.getBox().getBoxId();
		String reqKey = clientId + "-" + msgPackId;
		if (mpids.containsKey(reqKey)) {
			NineStarImMsgSerV1Response response = new NineStarImMsgSerV1Response(msgPackId);
			response.setState(1);
			ctx.writeAndFlush(response);
			return;
		}
		// 接受消息
		byte[] headBytes = msg.getHeadBytes();
		byte[] bodyBytes = msg.getBodyBytes();
		MsgPackage body = MsgUtils.readMsgPackage((short) 0, Unpooled.wrappedBuffer(bodyBytes));
		String headJson = new String(headBytes, Charset.forName("UTF-8"));
		NineStarImMsgSerV1ReqHead reqHeadv1 = JSON.parseObject(headJson, NineStarImMsgSerV1ReqHead.class);
		Set<String> targeIds = null;
		if (!reqHeadv1.isSendall()) {
			targeIds = reqHeadv1.getTargeIds();	
		}
		
		NineStarImMsgSerV1Response response = send(msgPackId, body, targeIds, nsih.getNineStarImServer());
		mpids.put(reqKey, System.currentTimeMillis());
		ctx.writeAndFlush(response.toMsgPackage());

	}
	
	static {
		task.scheduleWithFixedDelay(NineStarImSerV1Handler::clear, 0, 10, TimeUnit.SECONDS);
	}
	
	private static void clear() {
		List<String> clearKeys = new ArrayList<String>(100);
		for (Entry<String, Long> entry : mpids.entrySet()) {
			if (System.currentTimeMillis() - entry.getValue() > 120000) {
				clearKeys.add(entry.getKey());
			}
		}
		for (String clearKey : clearKeys) {
			mpids.remove(clearKey);
		}
	}
}
