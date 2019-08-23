package org.ninestar.im.server.controller;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.ninestar.im.server.controller.ann.NineStarSerController;
import org.ninestar.im.server.controller.ann.NineStarSerUri;
import org.ninestar.im.server.controller.dynparams.DynMethodParams;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

@Component
public class ControllerManage implements ApplicationContextAware, InitializingBean, ApplicationRunner {

	private Map<String, ControllerMethod> methods = new ConcurrentHashMap<String, ControllerMethod>();
	private ApplicationContext applicationContext;

	public ControllerManage() {

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Map<String, Object> beans = applicationContext.getBeansWithAnnotation(NineStarSerController.class);
		for (Entry<String, Object> entry : beans.entrySet()) {
			String beanName = entry.getKey();
			Object bean = entry.getValue();
			handlerBean(beanName, bean);
		}
	}

	private void handlerBean(String beanName, Object bean) {
		// AnnotatedElementUtils
		NineStarSerUri rootUri = AnnotatedElementUtils.findMergedAnnotation(bean.getClass(), NineStarSerUri.class);
		String root = "";
		if (rootUri != null) {
			root = rootUri.value().trim().replace("\\", "/");
		}
		Method[] mes = bean.getClass().getDeclaredMethods();
		for (Method m : mes) {
			NineStarSerUri nineStarSerUri = AnnotatedElementUtils.findMergedAnnotation(m, NineStarSerUri.class);
			if (nineStarSerUri != null) {//
				String uri = nineStarSerUri.value().trim().replace("\\", "/");
				String contentType = nineStarSerUri.contentType().trim();
				uri = mergedUri(root, uri, contentType);
				m.setAccessible(true);
				ControllerMethod method = new ControllerMethod(uri, contentType, m, bean, beanName);
				this.methods.put(uri, method);
			}
		}
	}

	private String mergedUri(String root, String uri, String contentType) {
		StringBuilder resUri = new StringBuilder(root.length() + uri.length() + contentType.length() + 20);
		if (root.isEmpty()) {

		} else {
			if (root.charAt(0) != '/') {
				resUri.append("/");
			}

			resUri.append(root);

			if (root.charAt(root.length() - 1) != '/') {
				resUri.append("/");
			}
		}
		if (uri.isEmpty()) {

		} else if (uri.charAt(0) == '/') {
			resUri.append(uri.substring(1));
		} else {
			resUri.append(uri);
		}
		if (!contentType.isEmpty()) {
			resUri.append(";contentType=").append(contentType);
		}
		return resUri.toString();
	}

	public ControllerResult execute(String uri, String contentType, DynMethodParams dynParams) {
		ControllerMethod method = methods.get(uri);
		if (method == null) {
			method = methods.get(uri + ";contentType=" + contentType);
		}
		if (method == null) {
			return ControllerResult.getNotExistResult();
		}
		try {
			Object result = method.execute(dynParams);
			return ControllerResult.getOkResult(method, result);
		} catch (Throwable e) {
			return ControllerResult.getErrorResult(method, e);
		}
	}

}
