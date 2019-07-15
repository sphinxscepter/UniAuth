package com.yyt.UniAuth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.yyt.UniAuth.customoauth.CustomOAuthFilter;
import com.yyt.UniAuth.customoauth.CustomOAuthResponseExceptionTranslator;
import com.yyt.UniAuth.service.Su360ClientDetailService;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private Su360ClientDetailService su360ClientDetailService;
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	@Autowired
	private CustomOAuthResponseExceptionTranslator customOAuthResponseExceptionTranslator;
	@Autowired
	private CustomOAuthFilter customOAuthFilter;
	
	public TokenStore redisTokenStore() {
		RedisTokenStore rts = new RedisTokenStore(redisConnectionFactory);
		return rts;
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer client) throws Exception {
		client.withClientDetails(su360ClientDetailService);
	}
	
	public void configure(AuthorizationServerEndpointsConfigurer endpointConfig) throws Exception {
		endpointConfig
			.tokenStore(redisTokenStore())
			.accessTokenConverter(jwtAccessTokenConverter)
			.authenticationManager(authenticationManager)
			.allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET)
			.pathMapping("/oauth/token", "/api/v1/auth/token")
			.pathMapping("/oauth/authorize", "/api/v1/auth/authorize");
		endpointConfig.exceptionTranslator(customOAuthResponseExceptionTranslator);
	}
	
	/**
	 * 允许使用表单验证
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer configurer) throws Exception {
		configurer
			.allowFormAuthenticationForClients()
			.tokenKeyAccess("isAuthenticated()")
			.checkTokenAccess("permitAll()");
		configurer.addTokenEndpointAuthenticationFilter(customOAuthFilter);
	}

}
