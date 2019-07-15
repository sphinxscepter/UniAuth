package com.yyt.UniAuth.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyt.UniAuth.customoauth.BaseResponse;

public class HttpUtils {

	public static void writerError(BaseResponse bs, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(bs.getStatus());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(),bs);
    }
	
	public static String getAccessToken(HttpServletRequest request) {
		String token = request.getParameter("access_token");
		if(null == token || "".equals(token)) {
			token = request.getHeader("Authorization").replaceAll("Bearer ", "");
		}
		return token;		
	}
	
}
