package com.yyt.UniAuth.model.entity;

import java.sql.Timestamp;

public class AuditLog {
	
	private Integer id;
	private Timestamp opTime;
	private Integer opUser;
	private String opType;
	private String opDesc;
	private String opTable;
	private Integer opDataId;
	private Integer appId;
	private Integer tenantId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Timestamp getOpTime() {
		return opTime;
	}
	public void setOpTime(Timestamp opTime) {
		this.opTime = opTime;
	}
	public Integer getOpUser() {
		return opUser;
	}
	public void setOpUser(Integer opUser) {
		this.opUser = opUser;
	}
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	public String getOpDesc() {
		return opDesc;
	}
	public void setOpDesc(String opDesc) {
		this.opDesc = opDesc;
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
	public String getOpTable() {
		return opTable;
	}
	public void setOpTable(String opTable) {
		this.opTable = opTable;
	}
	public Integer getOpDataId() {
		return opDataId;
	}
	public void setOpDataId(Integer opDataId) {
		this.opDataId = opDataId;
	}

}
