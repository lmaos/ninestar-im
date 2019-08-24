package org.ninestar.im.client;

import java.util.concurrent.CountDownLatch;

import org.ninestar.im.client.handle_v0.NineStarImV0Output;
import org.ninestar.im.imcoder.ImMsgDecode;
import org.ninestar.im.imcoder.ImMsgEncode;
import org.ninestar.im.msgcoder.MsgPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NineStarImClient extends ChannelInitializer<SocketChannel> {
	private Bootstrap b = null;
	private NioEventLoopGroup group;
	private Channel channel;

	private static final Logger log = LoggerFactory.getLogger(NineStarImClient.class);

	public NineStarImClient(String host, int port) {
		connect(host, port);
	}

	private void connect(String host, int port) {
		group = new NioEventLoopGroup(1);
		b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class).handler(this);
		ChannelFuture channelFuture = b.connect(host, port);
		CountDownLatch aws = new CountDownLatch(1);
		channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
			@Override
			public void operationComplete(Future<? super Void> future) throws Exception {
				aws.countDown();
				log.info("=客户端启动=");
			}
		});
		channel = channelFuture.channel();
		ChannelFuture closeFuture = channel.closeFuture();
		closeFuture.addListener(new GenericFutureListener<Future<? super Void>>() {

			@Override
			public void operationComplete(Future<? super Void> future) throws Exception {
				aws.countDown();
				log.info("=客户端关闭=");
			}
		});
		try {
			aws.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Thread run = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					closeFuture.sync();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					group.shutdownGracefully();
				}
			}
		});
		run.start();
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new ImMsgEncode());
		p.addLast(new ImMsgDecode());
		p.addLast(new NineStarImHandler(this));
	}

	public void close() {
		channel.close();
		//group.shutdownGracefully();
	}

	public ChannelFuture writeAndFlush(MsgPackage msg) {
		return channel.writeAndFlush(msg);
	}

	public NineStarImV0Output getNineStarImV0Output() {
		return new NineStarImV0Output(this, 10000);
	}

	public static void main(String[] args) {
		NineStarImClient client = new NineStarImClient("localhost", 7788);
		System.out.println("asasdsa");
		client.close();
	}
}
