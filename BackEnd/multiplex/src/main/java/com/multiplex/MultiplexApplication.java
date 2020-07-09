package com.multiplex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.multiplex.feignproxy")
public class MultiplexApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiplexApplication.class, args);
	}

}
