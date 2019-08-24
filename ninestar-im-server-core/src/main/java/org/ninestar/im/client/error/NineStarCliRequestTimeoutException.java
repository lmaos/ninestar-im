package org.ninestar.im.client.error;

public class NineStarCliRequestTimeoutException extends NineStarClientException {
	private static final long serialVersionUID = 1L;
	public NineStarCliRequestTimeoutException() {
		super("请求超时");
	}

}
