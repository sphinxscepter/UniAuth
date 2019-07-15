package com.yyt.UniAuth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yyt.UniAuth.mapper")
public class UniAuthApp {

	public static void main(String[] args) {
		SpringApplication.run(UniAuthApp.class, args);
	}

}
