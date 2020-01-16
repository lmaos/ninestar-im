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
/**
 * 服务器启动的Spring管理方式
 */
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
	/**
	 * 创建 NineStarImServer
	 * @return
	 */
	@Bean
	NineStarImServer nineStarImServer() {
		Map<String, Object> values = importingClassMetadata.getAnnotationAttributes(EnableNineStarImServer.class.getName());
		int port = (int) values.get("port");
		String host = (String) values.get("host");
		if (serverProperties != null) {
			port = serverProperties.getPort(port);
			host = serverProperties.getHost(host);
		}
		NineStarImServer server = new NineStarImServer();
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
		// 仅仅在@EnableNineStarImServer注解存在的时候使用
		return startAnns != null && startAnns.length > 0;
	}
}
