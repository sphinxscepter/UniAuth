package com.yyt.UniAuth.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyt.UniAuth.model.entity.User;
import com.yyt.UniAuth.service.UserService;
import com.yyt.UniAuth.utils.ExceptionDetail;
import com.yyt.UniAuth.utils.ReturnMessageUtils;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/reg")
public class UserRegController {
	
	private static Logger logger = LoggerFactory.getLogger(UserRegController.class);
	
	@Autowired
	private UserService userService;
	
	@PostMapping("regUser")
	public Map<String, Object> userRegister(@RequestBody User user){
		if(null == user) {
			logger.warn("注册用户信息为空");;
			return ReturnMessageUtils.genReturnMsg(
					ReturnMessageUtils.ERROR, "注册用户信息为空", null);
		}
		try {
			userService.regUser(user);
			user.setPassword(null);
			user.setDel(0);
			ObjectMapper mapper = new ObjectMapper();
			logger.info("新注册的用户信息为：" + mapper.writeValueAsString(user));
			return ReturnMessageUtils.genReturnMsg(
					ReturnMessageUtils.SUCCESS, "注册成功", user);
		} catch (Exception e) {
			logger.info("注册用户信息异常");
			logger.error(ExceptionDetail.getTrace(e));
			return ReturnMessageUtils.genReturnMsg(
					ReturnMessageUtils.ERROR, "注册用户信息异常，请稍后再试", null);
		}
	}
	
	@GetMapping("userCheck")
	public Map<String, Object> userCheck(HttpServletRequest request){
		logger.info("queryString=" + request.getQueryString());
		String mobile = request.getParameter("mobile");
		try {
			if(null == mobile || "".equals(mobile)) {
				return ReturnMessageUtils.genReturnMsg(
						ReturnMessageUtils.ERROR, "手机号码为空", null);
			} else {
				boolean chkRlt = userService.checkUser(mobile);
				if(chkRlt) {
					return ReturnMessageUtils.genReturnMsg(
							ReturnMessageUtils.SUCCESS, "用户已注册", chkRlt);
				} else {
					return ReturnMessageUtils.genReturnMsg(
							ReturnMessageUtils.SUCCESS, "用户未注册", chkRlt);
				}
			}
		} catch (Exception e) {
			logger.error("检测用户[" + mobile + "]是否注册出现异常");
			logger.error(ExceptionDetail.getTrace(e));
			return ReturnMessageUtils.genReturnMsg(
					ReturnMessageUtils.ERROR, "检测用户[" + mobile + "]是否注册出现异常", null);
		}
	}
	
}
