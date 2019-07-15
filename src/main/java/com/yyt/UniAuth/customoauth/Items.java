package com.yyt.UniAuth.customoauth;

import java.util.List;

public class Items<T> {
	
	long count;
    List<T> items;

    public Items() {

    }

    public Items(long count, List<T> items) {
        this.count = count;
        this.items = items;
    }

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}


}
