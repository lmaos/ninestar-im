package org.ninestar.example.quickstart.server;

import org.ninestar.im.server.config.EnableNineStarImServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableNineStarImServer(port = 7788)
public class QuickStartServerDemo {
    public static void main(String[] args) {
        SpringApplication.run(QuickStartServerDemo.class, args);
    }
}
