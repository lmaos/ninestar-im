package org.ninestar.im.server;

import java.util.concurrent.atomic.AtomicLong;

import org.ninestar.im.imcoder.ImMsgDecode;
import org.ninestar.im.imcoder.ImMsgEncode;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NineStarImServer extends ChannelInitializer<SocketChannel>
		implements InitializingBean, ApplicationContextAware {
	private int port;
	private ApplicationContext applicationContext;

	public NineStarImServer() {
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	private NioEventLoopGroup boss = null;
	private NioEventLoopGroup workers = null;
	private ServerBootstrap b;

	public void start() {
		this.boss = new NioEventLoopGroup();
		this.workers = new NioEventLoopGroup();
		this.b = new ServerBootstrap();
		b.group(boss, workers)
		.channel(NioServerSocketChannel.class)
		.handler(new LoggingHandler(LogLevel.INFO))
		.childHandler(this).option(ChannelOption.SO_KEEPALIVE, true)
		.option(ChannelOption.SO_REUSEADDR, true)
		.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
		.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		try {
			System.out.println("绑定端口 - port:" + port);
			ChannelFuture f = b.bind(port).sync();
			ChannelFuture channelFuture = f.channel().closeFuture();
			// channelFuture.addListener(listener) // 增加关闭的监听
			channelFuture.sync();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("绑定端口失败 - port:" + port);
		} finally {
			this.boss.shutdownGracefully();
			this.workers.shutdownGracefully();
			System.out.println("连接关闭 - port:" + port);
		}

	}

	public void shutdown() {
		this.boss.shutdownGracefully();
		this.workers.shutdownGracefully();
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new ImMsgEncode());
		p.addLast(new ImMsgDecode());
		NineStarImSerHandler nineStarImHandler = applicationContext.getBean(NineStarImSerHandler.class);
		p.addLast(nineStarImHandler);
	}

	private static AtomicLong id = new AtomicLong();

	@Override
	public void afterPropertiesSet() throws Exception {
		AnnotationConfigServletWebServerApplicationContext a = (AnnotationConfigServletWebServerApplicationContext) applicationContext;
		BeanDefinitionBuilder nineStarImHandlerB = BeanDefinitionBuilder.genericBeanDefinition(NineStarImSerHandler.class);
		nineStarImHandlerB.addConstructorArgValue(applicationContext);
		nineStarImHandlerB.addConstructorArgValue( this);
		nineStarImHandlerB.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		BeanDefinition beanDefinition = nineStarImHandlerB.getBeanDefinition();
		a.registerBeanDefinition("nineStarImHandler", beanDefinition);

		Thread serverRun = new Thread(this::start, "SERVER-RUN-" + id);
		serverRun.start();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
