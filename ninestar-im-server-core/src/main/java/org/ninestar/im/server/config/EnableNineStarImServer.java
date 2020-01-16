package org.ninestar.im.server.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * 启用九星服务IM服务器
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableNineStarImServerInit
@Import(RunNineStarImServer.class)
public @interface EnableNineStarImServer {
	/**
	 * 设置端口号
	 * 
	 * @return
	 */
	int port() default 7788;
	
	String host() default "localhost";

}
