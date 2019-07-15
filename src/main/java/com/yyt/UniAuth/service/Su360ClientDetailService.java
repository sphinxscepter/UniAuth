package com.yyt.UniAuth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import com.yyt.UniAuth.mapper.AppMapper;
import com.yyt.UniAuth.model.entity.App;
import com.yyt.UniAuth.model.entity.Role;
import com.yyt.UniAuth.model.entity.Su360ClientDetail;

@Service
public class Su360ClientDetailService implements ClientDetailsService {
	
	private static Logger Logger = LoggerFactory.getLogger(Su360ClientDetailService.class);
	
	@Autowired
	private AppMapper appMapper;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		App app = appMapper.getAppByAppId(clientId);
		if(null == app) {
			Logger.info("client id[" + clientId + "]不存在");
			throw new UsernameNotFoundException("client id[" + clientId + "]不存在");
		}
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		Su360ClientDetail clientDetail = new Su360ClientDetail();
		clientDetail.setAppId(clientId);
		clientDetail.setSecret(bCryptPasswordEncoder.encode(app.getAppSecret()));
		Set<String> grantTypes = new TreeSet<String>();
		grantTypes.add("password");
		grantTypes.add("client_credentials");
		clientDetail.setAuthorizedGrantTypes(grantTypes);
		Role role = new Role();
		role.setRoleName("admin");
		role.setRoleCode("admin");
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(role);
		clientDetail.setAuthorities(authorities);
		Set<String> scopes = new TreeSet<String>();
		scopes.add("read");
		clientDetail.setScope(scopes);
		return clientDetail;
	}
	
}
