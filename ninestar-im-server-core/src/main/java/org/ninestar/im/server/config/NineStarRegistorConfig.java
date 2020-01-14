package org.ninestar.im.server.config;

import java.util.Map;

import org.ninestar.im.nameser.NineStarNameser;
import org.ninestar.im.nameser.NineStarNameserDefaultImpl;
import org.ninestar.im.server.NineStarImServer;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class NineStarRegistorConfig implements ImportAware, Condition {
	private AnnotationMetadata importMetadata;

	@Override
	public void setImportMetadata(AnnotationMetadata importMetadata) {
		this.importMetadata = importMetadata;
	}

	
	@Bean
	NineStarNameser nineStarNameser(@Qualifier("nineStarImServer") NineStarImServer nineStarImServer) {
		Map<String, Object> values = importMetadata.getAnnotationAttributes(EnableNineStarZkRegister.class.getName());
		String connection = (String) values.get("registerConnecton");
		if (connection.equals("")) {
			connection = "127.0.0.1:2181";
		}
		NineStarNameser nameser = new NineStarNameserDefaultImpl(connection);
		nameser.register(nineStarImServer);
		return nameser;
	}
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String[] startAnns1 = context.getBeanFactory().getBeanNamesForAnnotation(EnableNineStarImServer.class);
		String[] startAnns2 = context.getBeanFactory().getBeanNamesForAnnotation(EnableNineStarZkRegister.class);

		return startAnns1 != null && startAnns2 != null && startAnns1.length > 0 && startAnns1.length > 1;
	}
}
