package org.ninestar.im.server;

import java.net.InetAddress;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.ninestar.im.imcoder.ImMsgDecode;
import org.ninestar.im.imcoder.ImMsgEncode;
import org.ninestar.im.monitor.ServerMonitor;
import org.ninestar.im.monitor.ServerMonitorBox;
import org.ninestar.im.monitor.ServerMonitorHandler;
import org.ninestar.im.nameser.NineStarNameser;
import org.ninestar.im.server.error.ServerPortException;
import org.ninestar.im.server.error.ServerStartException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger log = LoggerFactory.getLogger(NineStarImServer.class);

	private static ServerMonitor<NineStarImSerHandler> monitor = new ServerMonitor<NineStarImSerHandler>()
			.addMonitorHandler(new ServerMonitorHandler<NineStarImSerHandler>() {

				@Override
				public void timeout(ServerMonitorBox<NineStarImSerHandler> monitorBox) {
					monitorBox.getValue().close();
					log.info("客户端连接心跳超时");
				}
			});
	private int port = 7788;
	private String host = "localhost";
	private ApplicationContext applicationContext;
	private String serverId;
	
	private NioEventLoopGroup boss = null;
	private NioEventLoopGroup workers = null;
	private ServerBootstrap b;
	
	private boolean start = false;
	
	private NineStarNameser nineStarNameser;
	
	public NineStarImServer(String serverId) {
		this.serverId = serverId.replace("/", "_");
	}

	public void register(NineStarNameser nineStarNameser) {
		this.nineStarNameser = nineStarNameser;
		this.nineStarNameser.register(this);
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setHost(String host) {
		if (host == null ) {
			throw new NullPointerException("host null");
		}
		this.host = host;
	}
	
	public String getHost() {
		return host;
	}
	
	public String getServerId() {
		return serverId;
	}

	public void start() {
		if (start) {
			throw new ServerStartException();
		}
		if (port <= 0) {
			throw new ServerPortException(port);
		}
		
		if (host.isEmpty()) {
			this.host = "localhost";
		}
		if (serverId == null || serverId.isEmpty()) {
			this.serverId = "ns:" + host + ":" + port;
		}
		
		try {
			this.start = true;
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
			System.out.println("绑定:" + host + ":" + port);
			ChannelFuture f = null;
			if ("localhost".equals(host)) {
				f = b.bind(port).sync();
			} else {
				f = b.bind(InetAddress.getByName(host), port).sync();
			}
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
			this.start = false;
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
		nineStarImHandlerB.addConstructorArgValue(this);
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
	
	public ServerMonitor<NineStarImSerHandler> getMonitor() {
		return monitor;
	}
	@Override
	public String toString() {
		return "ns://" + host + ":" + port;
	}
	
	public boolean send(String targerId, NineStarImSerResponse response) {
		if (this.nineStarNameser != null) {
			this.nineStarNameser.send(new String[] {targerId}, response);
			return true;
		} 
		
		ServerMonitorBox<NineStarImSerHandler> box = monitor.getBox(targerId);
		if (box == null) {
			return false;
		}
		box.getValue().writeAndFlush(response.toMsgPackage());
		return true;
	}
	
	public void send(String targerIds[], NineStarImSerResponse response) {
		if (this.nineStarNameser != null) {
			this.nineStarNameser.send(targerIds, response);
			return;
		} 
		for (String targerId : targerIds) {
			ServerMonitorBox<NineStarImSerHandler> box = monitor.getBox(targerId);
			if (box != null) {
				box.getValue().writeAndFlush(response.toMsgPackage());
			}
		}
	}
	
	public void send(NineStarImSerResponse response) {
		if (this.nineStarNameser != null) {
			this.nineStarNameser.send(response);
			return;
		}
		Set<String> targerIds = monitor.getBoxIdSet();
		for (String targerId : targerIds) {
			send(targerId, response);
		}
	}
}
