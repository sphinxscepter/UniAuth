package com.yyt.UniAuth.customoauth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.yyt.UniAuth.service.UserService;

public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
	
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SmsCodeAuthenticationToken token = (SmsCodeAuthenticationToken) authentication;
		UserDetails userDetails = userService.loadUserByUsername((String)token.getPrincipal());
		if(userDetails == null) {
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		}
		SmsCodeAuthenticationToken authenticationToken = 
				new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
		authenticationToken.setDetails(authentication);
		return authenticationToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
