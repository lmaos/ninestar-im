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
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NineStarImHandler extends SimpleChannelInboundHandler<MsgPackage> {
	private NineStarImClient nineStarImClient;
	private ChannelHandlerContext channelHandlerContext;
	private NineStarImCliV0Handler cliV0Handler;
	private ScheduledExecutorService heartbeatExec = Executors.newScheduledThreadPool(1, Named.newThreadFactory("heartbeatExec"));
	private int errsize;
	private long heartbeatTime = 0;
	
	public NineStarImHandler(NineStarImClient nineStarImClient) {
		this.nineStarImClient = nineStarImClient;
		this.cliV0Handler = new NineStarImCliV0Handler(this);
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
		
		// 启动监听
		ctx.channel().closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
			@Override
			public void operationComplete(Future<? super Void> future) throws Exception {
				heartbeatExec.shutdown();
			}
		});
		
		// 启动心跳程序
		heartbeatExec.scheduleWithFixedDelay(() -> {
			if (heartbeatTime > 0 && System.currentTimeMillis() - heartbeatTime > 10000) {
				errsize ++;
			}
			if (errsize >= 3) { // 心跳记录失败大于等于3次 关闭连接
				ctx.channel().close();
				return;
			}
			if (ctx.channel().isWritable()) {
				ctx.writeAndFlush(MsgPackage.createHeartbeatReqPack((short) 0)).addListener(new GenericFutureListener<Future<? super Void>>() {
					@Override
					public void operationComplete(Future<? super Void> future) throws Exception {
						heartbeatTime = System.currentTimeMillis();
					}
				});
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
			this.errsize = 0;
			this.heartbeatTime = 0;
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
