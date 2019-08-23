package org.ninestar.im.server.handler_v0;

import java.util.Date;

import org.ninestar.im.server.NineStarImSerRequest;
import org.ninestar.im.server.controller.dynparams.DynMethodParam;
import org.ninestar.im.server.controller.dynparams.DynMethodParamInjector;
import org.ninestar.im.server.controller.dynparams.DynMethodParams;
import org.ninestar.im.server.error.RequestParamNotExistException;
import org.ninestar.im.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;

import com.alibaba.fastjson.JSON;
@Component
public class NineStarImMethodParamInjector implements DynMethodParamInjector {

	@Override
	public Object getObject(DynMethodParam param, DynMethodParams methodParams) throws Throwable {
		RequestParam requestParam = param.getParamAnn(RequestParam.class);
		if (requestParam != null) {
			return getValueByRequestParam(requestParam, param, methodParams);
		}
		return null;
	}

	private Object getValueByRequestParam(RequestParam requestParam, DynMethodParam param, DynMethodParams methodParams)
			throws RequestParamNotExistException {
		NineStarImMsgSerV0Request request = (NineStarImMsgSerV0Request) methodParams.get(NineStarImSerRequest.class);
		String paramName = requestParam.value().trim();
		boolean required = requestParam.required();
		String defaultValue = requestParam.defaultValue();

		if (paramName.isEmpty()) {
			return null;
		}
		Class<?> paramType = param.getParamType();

		NineStarImMsgSerV0ReqHead head = request.getHead();
		boolean existValue = head.containsKey(paramName);
		if (!existValue) {
			boolean defaultValueIsEmp = defaultValue.equals(ValueConstants.DEFAULT_NONE);
			if (!defaultValueIsEmp) {
				Object result = Utils.getValue(paramType, defaultValue, null);
				if (result == null && !defaultValue.isEmpty()) {
					char c1 = defaultValue.charAt(0);
					char c2 = defaultValue.charAt(defaultValue.length() - 1);
					if (c1 == '{' && c2 == '}') {
						result = JSON.parseObject(defaultValue, paramType);
					}
				}
				return result;
			} else if (required) {
				throw new RequestParamNotExistException(paramName);
			}
			return null;
		} else {
			Class<?> type = paramType;
			Object result = null;
			if (Utils.isString(paramType)) {
				result = head.getString(paramName);
			} else if (Utils.isbyte(type) || Utils.isByte(type)) {
				result = head.getByte(paramName);
			} else if (Utils.isshort(type) || Utils.isShort(type)) {
				result = head.getShort(paramName);
			} else if (Utils.isint(type) || Utils.isInteger(type)) {
				result = head.getInteger(paramName);
			} else if (Utils.islong(type) || Utils.isLong(type)) {
				result = head.getLong(paramName);
			} else if (Utils.isfloat(type) || Utils.isFloat(type)) {
				result = head.getFloat(paramName);
			} else if (Utils.isdouble(type) || Utils.isDouble(type)) {
				result = head.getDouble(paramName);
			} else if (Utils.isboolean(type) || Utils.isBoolean(type)) {
				result = head.getBoolean(paramName);
			} else if (Utils.ischar(type) || Utils.isCharacter(type)) {
				result = head.getString(paramName).charAt(0);
			} else if (type == String.class) {
				result = head.getString(paramName);
			} else if (type == StringBuilder.class) {
				result = new StringBuilder(head.getString(paramName));
			} else if (type == StringBuffer.class) {
				result = new StringBuffer(head.getString(paramName));
			} else if (type == Date.class) {
				result = head.getDate(paramName);
			}
			return result;
		}

	}
}
