package com.yyt.UniAuth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyt.UniAuth.mapper.AuditLogMapper;
import com.yyt.UniAuth.model.entity.AuditLog;

@Service
public class AuditLogService {
	
	@Autowired
	private AuditLogMapper auditLogMapper;

	public void addAuditLog(AuditLog auditLog) {
		auditLogMapper.addAuditLog(auditLog);
	}
	
	public AuditLog getAuditLogById(Integer id) {
		return auditLogMapper.getAuditLogById(id);
	}
}
