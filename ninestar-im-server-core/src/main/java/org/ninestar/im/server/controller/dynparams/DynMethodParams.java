package org.ninestar.im.server.controller.dynparams;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.ninestar.im.utils.Utils;
import org.springframework.core.annotation.AnnotationUtils;

public class DynMethodParams {

	private DynMethodParamInjector dynMethodParamInjector;
	private static Map<Method, DynMethodParam[]> caches = new ConcurrentHashMap<Method, DynMethodParam[]>();
	private final static DynMethodParam[] PARAM_ZERO = {};
	private final static Object[] OBJECT_ZERO = {};

	private Map<Class<?>, Object> typeParams = new HashMap<>();

	public DynMethodParams setDynMethodParamInjector(DynMethodParamInjector dynMethodParamInjector) {
		this.dynMethodParamInjector = dynMethodParamInjector;
		return this;
	}

	public DynMethodParams put(Class<?> paramType, Object value) {
		if (value == null) {
			return this;
		}
		if (paramType == value.getClass() || paramType.isAssignableFrom(value.getClass())) {
			typeParams.put(paramType, value);
		}

		return this;
	}

	public DynMethodParams put(Class<?> paramTypes[], Object value) {
		for (Class<?> paramType : paramTypes) {
			this.put(paramType, value);
		}
		return this;
	}

	public DynMethodParams remove(Class<?> paramType) {
		this.typeParams.remove(paramType);
		return this;
	}

	public Object get(Class<?> paramType) {

		return typeParams.get(paramType);
	}

	public static DynMethodParam[] getDynMethodParamArr(Method method) {
		Class<?>[] paramTypes = method.getParameterTypes();
		if (paramTypes == null || paramTypes.length == 0) {
			return PARAM_ZERO;
		}

		DynMethodParam[] dynMethodParamArr = caches.get(method);
		if (dynMethodParamArr != null) {
			return dynMethodParamArr;
		}
		Annotation[] methodAnns = method.getAnnotations();
		Map<Class<? extends Annotation>, Annotation> methodAnnMap = new HashMap<>();
		for (int i = 0; i < methodAnns.length; i++) {
			Annotation methodAnn = methodAnns[i];
			methodAnn = AnnotationUtils.getAnnotation(methodAnn, methodAnn.annotationType());
			methodAnns[i] = methodAnn;
			methodAnnMap.put(methodAnn.annotationType(), methodAnn);
		}
		Annotation[][] paramsAnns = method.getParameterAnnotations();
		for (int i = 0; i < paramsAnns.length; i++) {
			for (int j = 0; j < paramsAnns[i].length; j++) {
				Annotation paramAnn = paramsAnns[i][j];
				paramsAnns[i][j] = AnnotationUtils.getAnnotation(paramAnn, paramAnn.annotationType());
			}
		}

		dynMethodParamArr = new DynMethodParam[paramTypes.length];
		for (int i = 0; i < paramTypes.length; i++) {
			Class<?> paramType = paramTypes[i];
			Annotation[] paramTypeArr = paramsAnns[i];
			Map<Class<? extends Annotation>, Annotation> paramAnnMap = new HashMap<>();
			for (int j = 0; j < paramTypeArr.length; j++) {
				Annotation paramAnn = methodAnns[i];
				paramAnnMap.put(paramAnn.annotationType(), paramAnn);
			}
			DynMethodParam dynMethodParam = new DynMethodParam(methodAnnMap, paramAnnMap, i, paramType, method);
			dynMethodParamArr[i] = dynMethodParam;
		}
		caches.put(method, dynMethodParamArr);
		return dynMethodParamArr;
	}

	public Object[] getParams(Method method) throws Throwable {
		DynMethodParam[] dynMethodParamArr = getDynMethodParamArr(method);
		if (dynMethodParamArr.length == 0) {
			return OBJECT_ZERO;
		}
		Object[] paramValues = new Object[dynMethodParamArr.length];
		for (int i = 0; i < paramValues.length; i++) {
			DynMethodParam param = dynMethodParamArr[i];
			Class<?> paramType = param.getParamType();
			Object paramValue = null;
			if (dynMethodParamArr != null) {
				paramValue = dynMethodParamInjector.getObject(param, this);
			}
			if (paramValue == null) {
				paramValue = get(paramType);
			}
			if (paramValue == null) {
				paramValue = Utils.getDefaultValue(paramType);
			}

			paramValues[i] = paramValue;
		}

		return paramValues;
	}

}
