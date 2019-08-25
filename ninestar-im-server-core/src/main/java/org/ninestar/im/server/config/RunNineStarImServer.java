package org.ninestar.im.server.config;

import org.ninestar.im.server.NineStarImServer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotationMetadata;

@Configuration
public class RunNineStarImServer implements InitializingBean, ImportAware {

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	private AnnotationMetadata importingClassMetadata;

	@Bean
	NineStarImServer nineStarImServer() {
		int port = (int) importingClassMetadata.getAnnotationAttributes(EnableNineStarImServer.class.getName())
				.get("port");
		NineStarImServer server = new NineStarImServer();
		server.setPort(port);
		return server;
	}

	// @Bean
	// NineStarImSerV0Handler handlerV0() {
	// return new NineStarImSerV0Handler();
	// }
	@Override
	public void setImportMetadata(AnnotationMetadata importMetadata) {
		this.importingClassMetadata = importMetadata;

	}
}
