package com.yyt.UniAuth.model.entity;

public class AppGroups {
	
	private Integer id;
	private Integer appId;
	private Integer grpId;
	private Integer tenantId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public Integer getGrpId() {
		return grpId;
	}
	public void setGrpId(Integer grpId) {
		this.grpId = grpId;
	}
	public Integer getTenantId() {
		return tenantId;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

}
