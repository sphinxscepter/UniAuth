package com.yyt.UniAuth.customoauth;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = CustomOAuthExceptionJacksonSerializer.class)
public class CustomOAuthException extends OAuth2Exception {

	public CustomOAuthException(String msg) {
		super(msg);
	}
	
	public CustomOAuthException(String msg, Throwable t) {
		super(msg, t);
	}

}
