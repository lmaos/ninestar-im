package org.ninestar.im.server.controller.dynparams;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

public class DynMethodParam {

	private Map<Class<? extends Annotation>, Annotation> methodAnns = null;
	private Map<Class<? extends Annotation>, Annotation> paramAnns = null;
	private int index;
	private Class<?> paramType;
	private Method method;

	public DynMethodParam(Map<Class<? extends Annotation>, Annotation> methodAnns,
			Map<Class<? extends Annotation>, Annotation> paramAnns, int index, Class<?> paramType, Method method) {
		super();
		this.methodAnns = methodAnns;
		this.paramAnns = paramAnns;
		this.index = index;
		this.paramType = paramType;
		this.method = method;
	}

	public Map<Class<? extends Annotation>, Annotation> getMethodAnns() {
		return methodAnns;
	}

	public Map<Class<? extends Annotation>, Annotation> getParamAnns() {
		return paramAnns;
	}

	public int getIndex() {
		return index;
	}

	public Class<?> getParamType() {
		return paramType;
	}

	public Method getMethod() {
		return method;
	}

}
