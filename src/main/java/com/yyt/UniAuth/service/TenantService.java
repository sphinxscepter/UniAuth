package com.yyt.UniAuth.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyt.UniAuth.mapper.AppMapper;
import com.yyt.UniAuth.mapper.TenantMapper;
import com.yyt.UniAuth.model.dto.ListDataDto;
import com.yyt.UniAuth.model.entity.App;
import com.yyt.UniAuth.model.entity.AuditLog;
import com.yyt.UniAuth.model.entity.Tenant;

@Service
public class TenantService {
	
	private static Logger logger = LoggerFactory.getLogger(TenantService.class);
	
	@Autowired
	private TenantMapper tenantMapper;
	@Autowired
	private AppMapper appMapper;
	
	public String addTenant(Tenant tenant) {
		if(null == tenant) {
			return "租户信息不能为空";
		}
		int tenantCount = tenantMapper.getTenantCountByName(tenant.getTenantName());
		if(tenantCount > 0) {
			logger.warn("要添加的租户名称[" + tenant.getTenantName() + "]已存在");
			return "租户[" + tenant.getTenantName() + "]已存在";
		}
		if(null == tenant.getCustomId()) {
			tenant.setCustomId(0);
		}
		tenantMapper.addNewTenant(tenant);
		logger.info("新增加的租户[" + tenant.getTenantName() + "]的ID为：" + tenant.getId());
		return "OK";
	}
	
	public String delTenant(Integer id) {
		Tenant tenant = tenantMapper.getTenantInfoById(id);
		if(null == tenant) {
			logger.warn("租户[id:" + id + "]在系统中不存在");
			return "要删除的租户在系统中不存在";
		} else {
			tenantMapper.delTenant(tenant);
			logger.info("租户[id:" + id + "]删除成功");
			AuditLog auditLog = new AuditLog();
			auditLog.setAppId(0);
			auditLog.setTenantId(0);
			auditLog.setOpDataId(id);
			auditLog.setOpTable("tenant");
			return "OK";
		}
	}
	
	public String addApp(App app) {
		Tenant tenant = tenantMapper.getTenantInfoById(app.getTenantId());
		if(null == tenant) {
			logger.warn("要添加应用的租户在系统中不存在");
			return "要添加应用的租户在系统中不存在";
		}
		app.setAppId(UUID.randomUUID().toString().replaceAll("-", "").toLowerCase());
		app.setAppSecret(UUID.randomUUID().toString().replaceAll("-", "").toLowerCase());
		appMapper.addApp(app);
		logger.info("新添加的应用[" + app.getAppName() + "]的id为:" + app.getAppId());
		return "OK";
	}
	
	public App getAppById(Integer id) {
		return appMapper.getAppById(id);
	}
	
	public ListDataDto<App> getAppsByTenantId(Integer tenantId) {
		ListDataDto<App> dto = new ListDataDto<App>();
		int appCount = appMapper.getAppCountByTenantId(tenantId);
		dto.setTotal(appCount);
		if(0 == appCount) {
			return dto;
		}
		List<App> apps = appMapper.getAppsByTenantId(tenantId);
		dto.setDataList(apps);
		return dto;
	}
	
	public String updateAppSecret(App app) {
		App appObj = appMapper.getAppById(app.getId());
		if(null == appObj) {
			logger.warn("要修改安全码的应用在系统中不存在");
			return "要修改安全码的应用在系统中不存在";
		}
		app.setAppSecret(UUID.randomUUID().toString().replaceAll("-", "").toLowerCase());
		appMapper.updateAppSecret(app);
		logger.info("修改应用[id:" + app.getId() + "]的安全码成功");
		return "OK";
	}
	
	public String delApp(App app) {
		App appObj = appMapper.getAppById(app.getId());
		if(null == appObj) {
			logger.warn("要修改安全码的应用在系统中不存在");
			return "要修改安全码的应用在系统中不存在";
		}
		appMapper.delApp(app);
		logger.info("删除应用[id:" + app.getId() + "]成功");
		return "OK";
	}

}
