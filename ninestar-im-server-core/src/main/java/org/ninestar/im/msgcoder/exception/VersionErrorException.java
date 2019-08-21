package org.ninestar.im.msgcoder.exception;

public class VersionErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VersionErrorException(int currentVer, int configVer) {
		super(String.format("消息版本号: %d, 配置要求版本号: %d", currentVer, configVer));
	}

	@Override
	public synchronized Throwable fillInStackTrace() {

		return null;
	}
}
