package org.ninestar.im.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "ns.im.zookeeper")
@Component
public class NineStarZkRegisterProperties {
	/** zookeeper服务地址 */
	private String registerConnecton;

	public String getRegisterConnecton() {
		return registerConnecton;
	}

	public String getRegisterConnecton(String def) {
		if (registerConnecton == null || registerConnecton.isEmpty()) {
			return def;
		}
		return registerConnecton;
	}

	public void setRegisterConnecton(String registerConnecton) {
		this.registerConnecton = registerConnecton;
	}

}
