package org.ninestar.im.server.config;

import org.ninestar.im.server.NineStarImServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;

@Configuration
@Conditional(RunNineStarImServer.class)
public class RunNineStarImServer implements InitializingBean, ImportAware, Condition {
	
	private static final Logger log = LoggerFactory.getLogger(RunNineStarImServer.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("九星服务启动");	
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

	@Override
	public void setImportMetadata(AnnotationMetadata importMetadata) {
		this.importingClassMetadata = importMetadata;
	}

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String[] startAnns = context.getBeanFactory().getBeanNamesForAnnotation(EnableNineStarImServer.class);

		return startAnns != null && startAnns.length > 0;
	}
}
