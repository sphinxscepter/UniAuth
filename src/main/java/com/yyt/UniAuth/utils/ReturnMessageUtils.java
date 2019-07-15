package com.yyt.UniAuth.utils;

import java.util.HashMap;
import java.util.Map;

public class ReturnMessageUtils {
	
	public static Integer SUCCESS = 200;
	public static Integer ERROR = 503;
	
	public static Map<String, Object> genReturnMsg(Integer status, String msg, Object data) {
		Map<String, Object> rltMap = new HashMap<String, Object>();
		rltMap.put("status", status);
		if(null != msg && !"".equals(msg)) {
			rltMap.put("message", msg);
		}
		if(null != data) {
			rltMap.put("data", data);
		}
		return rltMap;
	}

}
