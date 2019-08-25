package org.ninestar.im.server.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration()
@ComponentScan(basePackages="org.ninestar.im.server")
public class NineStarImServerInit implements InitializingBean{

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("服务初始化完成");
		
	}
	
//	@Bean
//	NineStarImSerV0Handler handlerV0() {
//		return new NineStarImSerV0Handler();
//	}
}
