package com.yyt.UniAuth.customoauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	private static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";
	
	private String mobileParameter = SPRING_SECURITY_FORM_MOBILE_KEY;
	private boolean postOnly = true;

	protected SmsCodeAuthenticationFilter() {
		super(new AntPathRequestMatcher("/api/v1/smsAuth", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		if(postOnly && !HttpMethod.POST.name().equals(request.getMethod())) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}
		String mobile = obtainMobile(request);
		if(mobile == null) {
			mobile = "";
		}
		mobile = mobile.trim();
		SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	protected String obtainMobile(HttpServletRequest request) {
		return request.getParameter("mobile");
	}
	
	protected void setDetails(HttpServletRequest request,
			SmsCodeAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	public String getMobileParameter() {
		return mobileParameter;
	}

	public void setMobileParameter(String mobileParameter) {
		Assert.hasText(mobileParameter, "Mobile parameter must not be empty or null");
		this.mobileParameter = mobileParameter;
	}
	
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

}
