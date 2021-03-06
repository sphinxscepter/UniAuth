package com.yyt.UniAuth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		super.configure(resources);
	}
	
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.authorizeRequests()
//			.antMatchers("/api/v1/**").permitAll()
			.antMatchers("/api/v1/auth/**", "/api/v1/reg/**").permitAll()
			.anyRequest()
			.authenticated();
	}

}
