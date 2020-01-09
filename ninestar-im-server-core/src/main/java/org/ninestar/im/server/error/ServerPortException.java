package org.ninestar.im.server.error;

public class ServerPortException extends NineStarServerRuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ServerPortException(int port) {
		super("端口号错误：" + port);
	}
}
