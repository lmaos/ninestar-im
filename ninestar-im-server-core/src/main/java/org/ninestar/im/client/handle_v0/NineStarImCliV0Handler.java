package org.ninestar.im.client.handle_v0;

import java.nio.charset.Charset;

import org.ninestar.im.client.NineStarImClient;
import org.ninestar.im.client.NineStarImHandler;
import org.ninestar.im.client.subscribe.SubscribeController;
import org.ninestar.im.msgcoder.MsgPackage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

import io.netty.channel.ChannelHandlerContext;

public class NineStarImCliV0Handler {
	private NineStarImHandler nineStarImHandler;
	private NineStarImClient client;
	private NineStarImV0Output output = null;
	 
	public NineStarImCliV0Handler(NineStarImHandler nineStarImHandler) {
		this.nineStarImHandler = nineStarImHandler;
		this.client = nineStarImHandler.getNineStarImClient();
		this.output = new NineStarImV0Output(client);
	}

	public NineStarImHandler getNineStarImHandler() {
		return nineStarImHandler;
	}

	public NineStarImClient getClient() {
		return client;
	}

	public void handler(ChannelHandlerContext ctx, MsgPackage msg) {
		long msgPackId = msg.getMsgId();
		
		byte[] headBytes = msg.getHeadBytes();
		byte[] bodyBytes = msg.getBodyBytes();
		
		String headJson = new String(headBytes, Charset.forName("UTF-8"));
		NineStarImMsgCliV0RespHead respHead = JSON.parseObject(headJson, NineStarImMsgCliV0RespHead.class, Feature.SupportNonPublicField);
		NineStarImMsgCliV0Response response = new NineStarImMsgCliV0Response(msgPackId, respHead, bodyBytes, msg);
		output.setNineStarImCliResponse(response);
		String uri = response.getUri();
		((SubscribeController)client.getNineStarSubscribe()).onmessage(uri, response);
	};

}
