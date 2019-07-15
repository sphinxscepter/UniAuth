package com.yyt.UniAuth.model.dto;

public class TokenCheckDto {
	
	public boolean checkRlt;
	public String errMsg;
	
	public boolean isCheckRlt() {
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
	
}
