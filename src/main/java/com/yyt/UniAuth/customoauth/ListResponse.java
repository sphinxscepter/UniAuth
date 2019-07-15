package com.yyt.UniAuth.customoauth;

import java.util.List;

public class ListResponse extends BaseResponse {
	
	private long count;
    private List items;

    protected ListResponse(){

    }

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

    
}
