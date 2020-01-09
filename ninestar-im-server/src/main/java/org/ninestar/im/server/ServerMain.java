package org.ninestar.im.server;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerMain implements InitializingBean, ApplicationContextAware {
	public static void main(String[] args) {
		SpringApplication.run(ServerMain.class, args);
	}

	@Bean
	NineStarImServer nineStarImServer() {
		NineStarImServer nineStarImServer = new NineStarImServer(null);
		nineStarImServer.setPort(7788);
		return nineStarImServer;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

	}
}
