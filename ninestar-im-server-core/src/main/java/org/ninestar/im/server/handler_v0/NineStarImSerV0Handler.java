package org.ninestar.im.server.handler_v0;

import java.nio.charset.Charset;

import org.ninestar.im.msgcoder.MsgPackage;
import org.ninestar.im.server.NineStarImSerHandler;
import org.ninestar.im.server.NineStarImSerRequest;
import org.ninestar.im.server.controller.ControllerManage;
import org.ninestar.im.server.controller.dynparams.DynMethodParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import io.netty.channel.ChannelHandlerContext;

@Component("handlerV0")
public class NineStarImSerV0Handler {

	@Autowired
	private ControllerManage controllerManage;

	public void handler(MsgPackage msg, ChannelHandlerContext ctx, NineStarImSerHandler nsih) {
		long msgPackId = msg.getMsgId();
		byte[] headBytes = msg.getHeadBytes();
		byte[] bodyBytes = msg.getBodyBytes();

		String headJson = new String(headBytes, Charset.forName("UTF-8"));
		// 请求头
		NineStarImMsgSerV0ReqHead reqHead = JSON.parseObject(headJson, NineStarImMsgSerV0ReqHead.class);
		String uri = reqHead.getUri();
		String contentType = reqHead.getContentType();
		// 请求头
		NineStarImMsgSerV0Request request = new NineStarImMsgSerV0Request(msgPackId, reqHead, bodyBytes, msg);
		// ApplicationContext ac = nsih.getApplicationContext();
		DynMethodParams dynParams = new DynMethodParams();
		dynParams.put(new Class<?>[] { NineStarImMsgSerV0Request.class, NineStarImSerRequest.class }, request);
		controllerManage.execute(uri, contentType, dynParams);
	}
}
