package com.yyt.UniAuth.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.yyt.UniAuth.model.dto.TokenCheckDto;
import com.yyt.UniAuth.model.entity.User;

@Service
public class TokenService {
	
	private static Logger logger = LoggerFactory.getLogger(TokenService.class);
	
	@Value("${uni-auth.jwtSecret}")
	private String jwtSecret;
	
	@Autowired
	private UserService userService;
	
	public TokenCheckDto tokenCheck(String jwtToken) {
		TokenCheckDto dto = new TokenCheckDto();
		DecodedJWT decodeJwt = decodeToken(jwtToken);
		boolean isTokenExpired = tokenExpiredCheck(decodeJwt);
		if(isTokenExpired) {
			dto.setCheckRlt(false);
			dto.setErrMsg("token已过期");
		} else {
			dto.setCheckRlt(true);
		}
		return dto;
	}
	
	public TokenCheckDto getCurrentUserInfo(String jwtToken) {
		DecodedJWT decodeJwt = decodeToken(jwtToken);
		boolean isTokenExpired = tokenExpiredCheck(decodeJwt);
		TokenCheckDto dto = new TokenCheckDto();
		if(isTokenExpired) {
			dto.setCheckRlt(false);
			dto.setErrMsg("token已过期");
			return dto;
		} else {
			String userMobile = decodeJwt.getClaim("user_id").toString();
			User user = userService.getUserInfoByMobile(userMobile);
			if(null == user) {
				dto.setCheckRlt(false);
				dto.setErrMsg("token中的手机号码在系统中不存在");
			} else {
				dto.setCheckRlt(true);
				dto.setUser(user);
			}
			return dto;
		}
	}
	
	private boolean tokenExpiredCheck(DecodedJWT decodedJwt) {
		Date expiresDate = decodedJwt.getExpiresAt();
		Date nowDate = new Date();
		logger.info("expiresDate=" + expiresDate);
		logger.info("nowDate=" + nowDate);
		if(expiresDate.before(nowDate)) {
			return true;
		} else {
			return false;
		}
	}
	
	private DecodedJWT decodeToken(String jwtToken) {
		Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodeJwt = verifier.verify(jwtToken);
		return decodeJwt;
	}

}
