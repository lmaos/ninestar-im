package org.ninestar.im.server.controller.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
public @interface NineStarSerUri {
	@AliasFor("uri")
	public String value() default "";

	@AliasFor("value")
	public String uri() default "";
	
	public String contentType() default "";
}
