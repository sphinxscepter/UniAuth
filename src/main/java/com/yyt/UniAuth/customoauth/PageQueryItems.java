package com.yyt.UniAuth.customoauth;

import java.util.List;

public class PageQueryItems {

	private Integer currentPage;
    private Integer pageSize;
    private Long count;
    private List items;

    public PageQueryItems(Integer currentPage, Integer pageSize, Long count, List items) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.count = count;
        this.items = items;
    }

    public PageQueryItems() {
    }

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}
    
    
}
