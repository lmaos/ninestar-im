package org.ninestar.im.server.controller.dynparams;
@FunctionalInterface
public interface DynMethodParamInjector {
	Object getObject(DynMethodParam param, DynMethodParams methodParams) throws Throwable;
}
