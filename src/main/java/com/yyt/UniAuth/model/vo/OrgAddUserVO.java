package com.yyt.UniAuth.model.vo;

public class OrgAddUserVO {
	
	private Integer orgId;
	private Integer[] userIds;
	
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer[] getUserIds() {
		return userIds;
	}
	public void setUserIds(Integer[] userIds) {
		this.userIds = userIds;
	}

}
