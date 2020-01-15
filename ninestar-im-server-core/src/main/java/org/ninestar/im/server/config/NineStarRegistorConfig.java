package org.ninestar.im.server.config;

import java.util.Map;

import javax.annotation.Resource;

import org.ninestar.im.nameser.NineStarNameser;
import org.ninestar.im.nameser.NineStarNameserDefaultImpl;
import org.ninestar.im.server.NineStarImServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;

@Conditional(NineStarRegistorConfig.class)
@Configuration
public class NineStarRegistorConfig implements ImportAware, Condition, InitializingBean {
	private AnnotationMetadata importMetadata;

	@Override
	public void setImportMetadata(AnnotationMetadata importMetadata) {
		this.importMetadata = importMetadata;
	}
	@Resource
	private NineStarZkRegisterProperties nsZkRegisterProperties;
	
	
	private static final Logger log = LoggerFactory.getLogger(NineStarRegistorConfig.class);

	@Bean
	NineStarNameser nineStarNameser(@Autowired NineStarImServer nineStarImServer) {
		Map<String, Object> values = importMetadata.getAnnotationAttributes(EnableNineStarZkRegister.class.getName());
		String connection = (String) values.get("registerConnecton");
		if (nsZkRegisterProperties != null) {
			connection = nsZkRegisterProperties.getRegisterConnecton(connection);
		}
		
		if (connection.equals("")) {
			connection = "127.0.0.1:2181";
		}
		NineStarNameser nameser = new NineStarNameserDefaultImpl(connection);
		nameser.register(nineStarImServer);
		log.info("注册服务器：" + nineStarImServer.getServerId());
		return nameser;
	}
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String[] startAnns1 = context.getBeanFactory().getBeanNamesForAnnotation(EnableNineStarImServer.class);
		String[] startAnns2 = context.getBeanFactory().getBeanNamesForAnnotation(EnableNineStarZkRegister.class);
		
		return startAnns1 != null && startAnns2 != null && startAnns1.length > 0 && startAnns2.length > 0;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("zookeeper 注册完成");
	}
}
