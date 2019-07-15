package com.yyt.UniAuth.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.yyt.UniAuth.model.dto.TokenCheckDto;

@Service
public class TokenService {
	
	private static Logger logger = LoggerFactory.getLogger(TokenService.class);
	
	@Value("${uni-auth.jwtSecret}")
	private String jwtSecret;
	
	public TokenCheckDto tokenCheck(String jwtToken) {
		TokenCheckDto dto = new TokenCheckDto();
		Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodeJwt = verifier.verify(jwtToken);
		Date expiresDate = decodeJwt.getExpiresAt();
		Date nowDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
		logger.info("expiresDate=" + expiresDate);
		logger.info("nowDate=" + nowDate);
		if(expiresDate.before(nowDate)) {
			dto.checkRlt = false;
			dto.errMsg = "token已过期";
		} else {
			logger.info(decodeJwt.getClaim("user_name").asString());
			logger.info(decodeJwt.getClaim("client_id").asString());
			dto.checkRlt = true;
		}
		return dto;
	} 

}
