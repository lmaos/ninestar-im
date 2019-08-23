package org.ninestar.im.server.error;

public class RequestParamNotExistException extends NineStarServerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RequestParamNotExistException(String paramName) {
		super("paramName=" + paramName);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {

		return null;
	}
}
