package org.ninestar.im.server.controller;

import java.lang.reflect.Method;

import org.ninestar.im.server.controller.dynparams.DynMethodParams;

public class ControllerMethod {
	private String uri;
	private String contentType;
	private Method method;
	private Object bean;
	private String beanName;

	public ControllerMethod(String uri, String contentType, Method method, Object bean, String beanName) {
		super();
		this.uri = uri;
		this.contentType = contentType;
		this.method = method;
		this.bean = bean;
		this.beanName = beanName;
	}

	public String getUri() {
		return uri;
	}

	public String getContentType() {
		return contentType;
	}

	public Method getMethod() {
		return method;
	}

	public Object getBean() {
		return bean;
	}

	public String getBeanName() {
		return beanName;
	}

	public Object execute(DynMethodParams dynParams) throws Throwable {
		Object[] params = dynParams.getParams(method);
		return method.invoke(bean, params);
	}

}
