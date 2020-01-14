package org.ninestar.im.server.handler_v0;

import java.nio.charset.Charset;

import org.ninestar.im.msgcoder.MsgPackage;
import org.ninestar.im.server.NineStarImSerHandler;
import org.ninestar.im.server.NineStarImSerRequest;
import org.ninestar.im.server.NineStarImSerResponse;
import org.ninestar.im.server.NineStarImSerVxHandler;
import org.ninestar.im.server.controller.ControllerManage;
import org.ninestar.im.server.controller.ControllerResult;
import org.ninestar.im.server.controller.ControllerResult.ControllerResultState;
import org.ninestar.im.server.controller.dynparams.DynMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

@Component("handlerV0")
public class NineStarImSerV0Handler implements NineStarImSerVxHandler{

	@Autowired
	private ControllerManage controllerManage;
	@Autowired
	private NineStarImMethodParamInjector nineStarImMethodParamInjector;

	private static final Logger log = LoggerFactory.getLogger(NineStarImSerV0Handler.class);

	public void handler(MsgPackage msg, ChannelHandlerContext ctx, NineStarImSerHandler nsih) {
		long msgPackId = msg.getMsgId();
		byte[] headBytes = msg.getHeadBytes();
		byte[] bodyBytes = msg.getBodyBytes();

		String headJson = new String(headBytes, Charset.forName("UTF-8"));
		// 请求头
		NineStarImMsgSerV0ReqHead reqHead = JSON.parseObject(headJson, NineStarImMsgSerV0ReqHead.class, Feature.SupportNonPublicField);
		String uri = reqHead.getUri();
		String contentType = reqHead.getContentType();
		// 封装来自客户端的请求
		NineStarImMsgSerV0Request request = new NineStarImMsgSerV0Request(msgPackId, reqHead, bodyBytes, msg);

		// 应答头
		NineStarImMsgSerV0RespHead respHead = new NineStarImMsgSerV0RespHead(reqHead);
		respHead.setBoxId(nsih.getBox().getBoxId());
		// 服务器封装应答
		NineStarImMsgSerV0Response response = new NineStarImMsgSerV0Response(msgPackId, respHead);
		// ApplicationContext ac = nsih.getApplicationContext();
		DynMethodParams dynParams = new DynMethodParams();
		dynParams.setDynMethodParamInjector(nineStarImMethodParamInjector);
		dynParams.put(new Class<?>[] { NineStarImSerHandler.class}, nsih);
		dynParams.put(new Class<?>[] { NineStarImMsgSerV0Request.class, NineStarImSerRequest.class }, request);
		dynParams.put(new Class<?>[] { NineStarImMsgSerV0Response.class, NineStarImSerResponse.class }, response);

		ControllerResult controllerResult = controllerManage.execute(uri, contentType, dynParams);

		if (controllerResult.getState() == ControllerResultState.OK) {
			// 发送成功
		} else if (controllerResult.getState() == ControllerResultState.NOT_EXIST) {
			// 执行的控制器不存在
			respHead.setState(-1);
			respHead.setMsg("请求资源不存在");
			// 调用异常控制器
			// ... 暂未开发

		} else if (controllerResult.getState() == ControllerResultState.ERROR) {
			// 执行控制器发生异常
			respHead.setState(-2);
			respHead.setMsg("请求发生异常");
			log.error("请求发生异常", controllerResult.getError());
			// 调用异常控制器
			// ... 暂未开发
		}
		MsgPackage respMsg = response.toMsgPackage();
		ChannelFuture channelFuture = ctx.writeAndFlush(respMsg);
		// channelFuture.addListener(listener) // 发生完成调用监听 -- 暂未开发
	}
}
