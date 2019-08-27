package org.ninestar.example.simple.server;

import org.ninestar.im.server.config.EnableNineStarImServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 服务器启动实例
 *
 */
@SpringBootApplication
@EnableNineStarImServer(port=12345)
public class ServerStartDemo {
	public static void main(String[] args) {
		SpringApplication.run(ServerStartDemo.class, args);
	}
}
