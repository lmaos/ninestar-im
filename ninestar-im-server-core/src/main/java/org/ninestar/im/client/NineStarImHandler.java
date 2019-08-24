package org.ninestar.im.client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.ninestar.im.client.handle_v0.NineStarImCliV0Handler;
import org.ninestar.im.msgcoder.MsgPackage;
import org.ninestar.im.utils.Named;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NineStarImHandler extends SimpleChannelInboundHandler<MsgPackage> {
	private NineStarImClient nineStarImClient;
	private ChannelHandlerContext channelHandlerContext;
	private NineStarImCliV0Handler cliV0Handler = new NineStarImCliV0Handler(this);
	private ScheduledExecutorService heartbeatExec = Executors.newScheduledThreadPool(1,
			Named.newThreadFactory("heartbeatExec"));

	public NineStarImHandler(NineStarImClient nineStarImClient) {
		this.nineStarImClient = nineStarImClient;
	}

	public NineStarImClient getNineStarImClient() {
		return nineStarImClient;
	}

	public ChannelHandlerContext getChannelHandlerContext() {
		return channelHandlerContext;
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		this.channelHandlerContext = ctx;
		heartbeatExec.scheduleWithFixedDelay(() -> {
			if (ctx.channel().isWritable()) {
				ctx.writeAndFlush(MsgPackage.createHeartbeatReqPack((short) 0));
			}
		}, 0, 15, TimeUnit.SECONDS);
	}

	public ChannelFuture writeAndFlush(MsgPackage msg) {
		return this.channelHandlerContext.writeAndFlush(msg);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MsgPackage msg) throws Exception {
		short version = msg.getVersion();
		if (msg.isHeartbeatRespPack()) {
			// 心跳应答
			return;
		}
		if (msg.isHeartbeatReqPack()) {
			ctx.writeAndFlush(MsgPackage.createHeartbeatRespPack(version));
			return;
		}

		if (version == 0) {
			cliV0Handler.handler(ctx, msg);
		}
	}

}
