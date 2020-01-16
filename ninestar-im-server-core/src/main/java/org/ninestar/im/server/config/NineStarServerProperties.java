package org.ninestar.im.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "ns.im.server")
@Component
public class NineStarServerProperties {
	/** 端口号 */
	private int port;
	/** 服务地址 */
	private String host;

	public int getPort() {
		return port;
	}

	public int getPort(int def) {
		if (port == 0) {
			return def;
		}
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public String getHost(String def) {
		if (host == null || host.isEmpty()) {
			return def;
		}
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
