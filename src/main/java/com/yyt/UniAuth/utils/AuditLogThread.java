package com.yyt.UniAuth.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yyt.UniAuth.model.entity.AuditLog;
import com.yyt.UniAuth.service.AuditLogService;

public class AuditLogThread implements Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(AuditLogThread.class);
	
	private AuditLogService auditLogService;
	private List<AuditLog> auditLogs;
	
	public AuditLogThread(
			AuditLogService auditLogService,
			List<AuditLog> auditLogs) {
		this.auditLogService = auditLogService;
		this.auditLogs = auditLogs;
	}

	@Override
	public void run() {
		synchronized (auditLogService) {
			try {
				for(AuditLog auditLog : auditLogs) {
					this.auditLogService.addAuditLog(auditLog);
				}
			} catch (Exception e) {
				logger.error("添加用户审计日志失败");
				logger.error(ExceptionDetail.getTrace(e));
			}
		}
	}

}
