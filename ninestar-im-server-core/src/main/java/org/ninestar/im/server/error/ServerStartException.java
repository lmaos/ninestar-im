package org.ninestar.im.server.error;

public class ServerStartException extends NineStarServerRuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ServerStartException() {
		super("服务已经启动请勿重复调用");
	}
}
