package org.ninestar.example.tweserver.s1;

import org.ninestar.im.server.config.EnableNineStarImServer;
import org.ninestar.im.server.config.EnableNineStarZkRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableNineStarImServer(port=7788, host="localhost")
@EnableNineStarZkRegister
public class TweServerDemo2 {
	public static void main(String[] args) {
		SpringApplication.run(TweServerDemo2.class, args);
	}

}
