package com.yyt.UniAuth.model.entity;

public class GroupRoleRels {
	
	private Integer id;
	private Integer grpId;
	private Integer roleId;
	private Integer appId;
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
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public Integer getTenantId() {
		return tenantId;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

}
