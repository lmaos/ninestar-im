package org.ninestar.im.server.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * 启用九星服务IM服务器 zookeeper 服务器注册
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableNineStarImServerInit
@Import(NineStarRegistorConfig.class)
public @interface EnableNineStarZkRegister {
	
	String registerConnecton() default "127.0.0.1:2181";
}
