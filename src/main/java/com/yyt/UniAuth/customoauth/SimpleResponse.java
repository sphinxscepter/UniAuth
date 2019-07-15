package com.yyt.UniAuth.customoauth;

public class SimpleResponse extends BaseResponse {

	private Object item;

    protected SimpleResponse() {
    }

    protected SimpleResponse(int status, String msg, Object item) {
        super(status, msg);
        this.item = item;
    }

	public Object getItem() {
		return item;
	}

	public void setItem(Object item) {
		this.item = item;
	}
    
    
}
