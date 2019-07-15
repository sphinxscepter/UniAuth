package com.yyt.UniAuth.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yyt.UniAuth.model.dto.TokenCheckDto;
import com.yyt.UniAuth.service.TokenService;
import com.yyt.UniAuth.utils.ExceptionDetail;
import com.yyt.UniAuth.utils.HttpUtils;
import com.yyt.UniAuth.utils.ReturnMessageUtils;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/user")
public class UserController {
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private TokenService tokenService;
	
	@GetMapping("/getCurrentUser")
	public Map<String, Object> getCurrentUser(HttpServletRequest request){
		try {
			String token = HttpUtils.getAccessToken(request);
			TokenCheckDto dto = tokenService.getCurrentUserInfo(token);
			if(dto.getCheckRlt()) {
				return ReturnMessageUtils.genReturnMsg(ReturnMessageUtils.SUCCESS, null, dto.getUser());
			} else {
				return ReturnMessageUtils.genReturnMsg(ReturnMessageUtils.ERROR, dto.getErrMsg(), null);
			}
		} catch (Exception e) {
			logger.error("获取当前用户信息异常");
			logger.error(ExceptionDetail.getTrace(e));
			return ReturnMessageUtils.genReturnMsg(ReturnMessageUtils.ERROR, "获取当前用户信息异常", null);
		}
		
	}

}
