package com.yyt.UniAuth.mapper;

import org.apache.ibatis.annotations.Param;

import com.yyt.UniAuth.model.entity.AuditLog;

public interface AuditLogMapper {
	
	public Integer addAuditLog(AuditLog auditLog);
	
	public AuditLog getAuditLogById(@Param("id") Integer id);

}
