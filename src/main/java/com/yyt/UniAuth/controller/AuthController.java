package com.yyt.UniAuth.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyt.UniAuth.model.dto.TokenCheckDto;
import com.yyt.UniAuth.service.TokenService;
import com.yyt.UniAuth.utils.ReturnMessageUtils;

@RestController
@RequestMapping("/api/v1/token")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
	
	private static Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Value("${uni-auth.jwtSecret}")
	private String jwtSecret;
	@Value("${uni-auth.jwtIssuer}")
	private String jwtIssuer;
	
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping(value = "checkToken", method = RequestMethod.GET)
	public Map<String, Object> verifyToken(HttpServletRequest request) throws JsonProcessingException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ObjectMapper mapper = new ObjectMapper();
		logger.info(authentication.getName());
		logger.info(mapper.writeValueAsString(authentication.getPrincipal()));
		logger.info(mapper.writeValueAsString(authentication.getDetails()));
		String token = request.getParameter("access_token");
		if(null == token || "".equals(token)) {
			token = request.getHeader("Authorization").replaceAll("Bearer ", "");
			if(null == token || "".equals(token)) {
				return ReturnMessageUtils.genReturnMsg(ReturnMessageUtils.ERROR, "token为空", null);
			}		
		}
		TokenCheckDto dto = tokenService.tokenCheck(token);
		if(dto.getCheckRlt()) {
			return ReturnMessageUtils.genReturnMsg(ReturnMessageUtils.SUCCESS, "token合法，可以继续使用", null);
		} else {
			return ReturnMessageUtils.genReturnMsg(ReturnMessageUtils.ERROR, dto.getErrMsg(), null);
		}
	}
	
	@RequestMapping(value = "getCurrentUser", method = RequestMethod.GET)
	public Map<String, Object> getCurrentUser(HttpServletRequest request) throws JsonProcessingException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ObjectMapper mapper = new ObjectMapper();
//		logger.info(authentication.getName());
//		logger.info(mapper.writeValueAsString(authentication.getPrincipal()));
//		logger.info(mapper.writeValueAsString(authentication.getDetails()));
		String token = request.getParameter("access_token");
		if(null == token || "".equals(token)) {
			token = request.getHeader("Authorization").replaceAll("Bearer ", "");
			if(null == token || "".equals(token)) {
				return ReturnMessageUtils.genReturnMsg(ReturnMessageUtils.ERROR, "token为空", null);
			}		
		}
		TokenCheckDto dto = tokenService.tokenCheck(token);
		if(dto.getCheckRlt()) {
			return ReturnMessageUtils.genReturnMsg(ReturnMessageUtils.SUCCESS, "", dto.getUser());
		} else {
			return ReturnMessageUtils.genReturnMsg(ReturnMessageUtils.ERROR, dto.getErrMsg(), null);
		}
	}
	
}
