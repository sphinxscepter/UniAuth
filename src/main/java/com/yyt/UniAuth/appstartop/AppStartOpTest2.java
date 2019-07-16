package com.yyt.UniAuth.appstartop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;

public class AppStartOpTest2 implements ApplicationRunner, Ordered {
	
	private static Logger logger = LoggerFactory.getLogger(AppStartOpTest2.class);

	@Override
	public int getOrder() {
		return 2;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("app start op test 2");
	}

}
