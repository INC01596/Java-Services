package com.incture.cherrywork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;


@SuppressWarnings("deprecation")
@EnableAuthorizationServer
@SpringBootApplication//(scanBasePackages={"com.incture.cherrywork"})
public class OrderManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderManagementApplication.class, args);
	}

}
