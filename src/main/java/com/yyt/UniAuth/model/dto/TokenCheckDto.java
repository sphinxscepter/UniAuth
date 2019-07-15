package com.yyt.UniAuth.model.dto;

import com.yyt.UniAuth.model.entity.User;

public class TokenCheckDto {
	
	private boolean checkRlt;
	private String errMsg;
	private User user;
	
	public boolean getCheckRlt() {
		return checkRlt;
	}
	public void setCheckRlt(boolean checkRlt) {
		this.checkRlt = checkRlt;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
