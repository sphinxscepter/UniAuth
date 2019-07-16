package com.yyt.UniAuth.appstartop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;

public class AppStartOpTest implements ApplicationRunner, Ordered {
	
	private static Logger logger = LoggerFactory.getLogger(AppStartOpTest.class);

	@Override
	public int getOrder() {
		return 3;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("app start op test 1");
	}

}
