package com.yyt.UniAuth.model.entity;

public class GroupUsers {
	
	private Integer id;
	private Integer grpId;
	private Integer userId;
	private Integer tenantId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGrpId() {
		return grpId;
	}
	public void setGrpId(Integer grpId) {
		this.grpId = grpId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getTenantId() {
		return tenantId;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
}
