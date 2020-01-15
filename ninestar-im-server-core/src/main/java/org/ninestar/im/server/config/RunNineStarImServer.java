package org.ninestar.im.server.config;

import java.util.Map;

import javax.annotation.Resource;

import org.ninestar.im.server.NineStarImServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;

@Configuration
@Conditional(RunNineStarImServer.class)
public class RunNineStarImServer implements InitializingBean, ImportAware, Condition, ApplicationContextAware {
	
	private static final Logger log = LoggerFactory.getLogger(RunNineStarImServer.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("九星服务启动");	
	}

	private AnnotationMetadata importingClassMetadata;
	private ApplicationContext applicationContext;
	
	@Autowired
	private Environment env;
	
	@Resource
	NineStarServerProperties serverProperties;
	
	@Bean
	NineStarImServer nineStarImServer() {
//		NineStarServerProperties serverProperties = appl
		Map<String, Object> values = importingClassMetadata.getAnnotationAttributes(EnableNineStarImServer.class.getName());
		int port = (int) values.get("port");
		String host = (String) values.get("host");
		String serverId = (String) values.get("serverId");
		if (serverProperties != null) {
			port = serverProperties.getPort(port);
			host = serverProperties.getHost(host);
			serverId = serverProperties.getServerId(serverId);
		}
		if (serverId.isEmpty()) {
			serverId = env.getProperty("spring.application.name", "");
		}
		NineStarImServer server = new NineStarImServer(serverId);
		server.setPort(port);
		server.setHost(host);
		return server;
	}

	@Override
	public void setImportMetadata(AnnotationMetadata importMetadata) {
		this.importingClassMetadata = importMetadata;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String[] startAnns = context.getBeanFactory().getBeanNamesForAnnotation(EnableNineStarImServer.class);

		return startAnns != null && startAnns.length > 0;
	}
}
