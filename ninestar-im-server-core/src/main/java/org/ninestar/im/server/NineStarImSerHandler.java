package org.ninestar.im.server;

import java.util.concurrent.atomic.AtomicLong;

import org.ninestar.im.monitor.ServerMonitor;
import org.ninestar.im.monitor.ServerMonitorBox;
import org.ninestar.im.msgcoder.MsgPackage;
import org.ninestar.im.server.handler_v0.NineStarImSerV0Handler;
import org.ninestar.im.utils.BoxIdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NineStarImSerHandler extends SimpleChannelInboundHandler<MsgPackage> {
	private ApplicationContext applicationContext;
	private NineStarImServer nineStarImServer;
	private ServerMonitorBox<NineStarImSerHandler> box = null;
	private ChannelHandlerContext channelHandlerContext;

	private static final Logger log = LoggerFactory.getLogger(NineStarImSerHandler.class);
	private static final AtomicLong clientIds = new AtomicLong();
	@Autowired
	private NineStarImSerV0Handler handlerV0;
	private ServerMonitor<NineStarImSerHandler> monitor;

	public NineStarImSerHandler(ApplicationContext applicationContext, NineStarImServer nineStarImServer) {

		this.applicationContext = applicationContext;
		this.nineStarImServer = nineStarImServer;
		this.monitor = nineStarImServer.getMonitor();
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		String serverId = this.getNineStarImServer().getServerId();
		long clientId = clientIds.incrementAndGet();
		String boxId = BoxIdUtils.getBoxId(serverId, clientId);
		this.channelHandlerContext = ctx;
		this.box = monitor.createBoxAndPutMonitor(boxId, this, 120000);
		ctx.channel().closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {

			@Override
			public void operationComplete(Future<? super Void> future) throws Exception {
				monitor.remove(box);
				log.info("客户端连接已经断开");
			}
		});
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MsgPackage msg) throws Exception {
		short version = msg.getVersion();
		// 如果是心跳包 (客户端向服务器告知存活)
		if (msg.isHeartbeatReqPack()) {
			this.box.updateTime();
			MsgPackage respMsg = MsgPackage.createHeartbeatRespPack(version);
			ctx.writeAndFlush(respMsg);
			return;
		}

		// 如果是心跳应答 （服务器也可以向客户端发送心跳主动验证存活）
		if (msg.isHeartbeatRespPack()) {
			this.box.updateTime();
			return;
		}

		// 协议版本解析
		if (version == 0) {
			handlerV0.handler(msg, ctx, this);
		}

	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		monitor.remove(box);
		this.close();
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public NineStarImServer getNineStarImServer() {
		return nineStarImServer;
	}

	public ChannelHandlerContext getChannelHandlerContext() {
		return channelHandlerContext;
	}

	public void close() {
		if (channelHandlerContext == null) {
			return;
		}
		if (channelHandlerContext.channel().isActive() || channelHandlerContext.channel().isOpen()) {
			channelHandlerContext.channel().close();
		}
	}

	public ChannelFuture writeAndFlush(MsgPackage msgPackage) {
		if (channelHandlerContext != null && channelHandlerContext.channel().isWritable()) {
			return channelHandlerContext.writeAndFlush(msgPackage);
		}
		return null;
	}
	
	public ServerMonitorBox<NineStarImSerHandler> getBox() {
		return box;
	}
}
