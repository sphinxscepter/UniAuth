package com.yyt.UniAuth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.yyt.UniAuth.appstartop.AppStartOpTest;
import com.yyt.UniAuth.appstartop.AppStartOpTest2;
import com.yyt.UniAuth.appstartop.CmdRunnerTest;

@SpringBootApplication
@MapperScan("com.yyt.UniAuth.mapper")
public class UniAuthApp {

	public static void main(String[] args) {
		SpringApplication.run(UniAuthApp.class, args);
	}
	
	@Bean
	public AppStartOpTest opTest1() {
		return new AppStartOpTest();
	}
	
	@Bean
	public AppStartOpTest2 opTest2() {
		return new AppStartOpTest2();
	}
	
	@Bean
	public CmdRunnerTest cmdTest() {
		return new CmdRunnerTest();
	}

}
