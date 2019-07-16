package com.yyt.UniAuth.appstartop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

@Order(1)
public class CmdRunnerTest implements CommandLineRunner {
	
	private static Logger logger = LoggerFactory.getLogger(CmdRunnerTest.class);

	@Override
	public void run(String... args) throws Exception {
		logger.info("CommandLineRunner test");
	}

}
