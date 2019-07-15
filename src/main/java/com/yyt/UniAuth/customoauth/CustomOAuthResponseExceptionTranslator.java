package com.yyt.UniAuth.customoauth;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@Component("customOAuthResponseExceptionTranslator")
public class CustomOAuthResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {
	
	private ThrowableAnalyzer throwableAnalyzer = new ThrowableAnalyzer();

	@Override
	public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
		Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
		Exception exp = (Exception) throwableAnalyzer.getFirstThrowableOfType(
				OAuth2Exception.class, causeChain);
		if(exp != null) {
			return handleOAuth2Exception((OAuth2Exception)exp);
		}
		exp = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class,
				causeChain);
		if (exp != null) {
			return handleOAuth2Exception(new UnauthorizedException(e.getMessage(), e));
		}

		exp = (AccessDeniedException) throwableAnalyzer
				.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
		if (exp instanceof AccessDeniedException) {
			return handleOAuth2Exception(new ForbiddenException(exp.getMessage(), exp));
		}

		exp = (HttpRequestMethodNotSupportedException) throwableAnalyzer.getFirstThrowableOfType(
				HttpRequestMethodNotSupportedException.class, causeChain);
		if (exp instanceof HttpRequestMethodNotSupportedException) {
			return handleOAuth2Exception(new MethodNotAllowed(exp.getMessage(), exp));
		}
		return null;
	}
	
	private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {

		int status = e.getHttpErrorCode();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Cache-Control", "no-store");
		headers.set("Pragma", "no-cache");
		if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
			headers.set("WWW-Authenticate", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
		}
		
		CustomOAuthException exp = new CustomOAuthException(e.getMessage(), e);

		ResponseEntity<OAuth2Exception> response = new ResponseEntity<OAuth2Exception>(exp, headers,
				HttpStatus.valueOf(status));

		return response;

	}
	
	@SuppressWarnings("serial")
	private static class UnauthorizedException extends OAuth2Exception {

		public UnauthorizedException(String msg, Throwable t) {
			super(msg, t);
		}

		@Override
		public String getOAuth2ErrorCode() {
			return "unauthorized";
		}

		@Override
		public int getHttpErrorCode() {
			return 401;
		}

	}
	
	@SuppressWarnings("serial")
	private static class ForbiddenException extends OAuth2Exception {

		public ForbiddenException(String msg, Throwable t) {
			super(msg, t);
		}

		@Override
		public String getOAuth2ErrorCode() {
			return "access_denied";
		}

		@Override
		public int getHttpErrorCode() {
			return 403;
		}

	}
	
	@SuppressWarnings("serial")
	private static class MethodNotAllowed extends OAuth2Exception {

		public MethodNotAllowed(String msg, Throwable t) {
			super(msg, t);
		}

		@Override
		public String getOAuth2ErrorCode() {
			return "method_not_allowed";
		}

		@Override
		public int getHttpErrorCode() {
			return 405;
		}

	}

}
