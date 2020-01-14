package org.ninestar.im.client.error;

public class NineStarClientConnectionException extends NineStarClientException {

	private static final long serialVersionUID = 1L;
	private String host;
	private int port;
	public NineStarClientConnectionException(String host, int port) {
		super(host + ":" + port);
		this.host = host;
		this.port = port;
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
}
