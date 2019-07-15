package com.yyt.UniAuth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yyt.UniAuth.model.entity.App;

public interface AppMapper {
	
	public Integer addApp(App app);
	
	public App getAppById(@Param("id") Integer id);
	
	public App getAppByAppId(@Param("appId") String appId);
	
	public List<App> getAppsByTenantId(@Param("tenantId") Integer tenantId);
	
	public int getAppCountByTenantId(@Param("tenantId") Integer tenantId);
	
	public int getAppCountByAppNameAndTenantId(
			@Param("appName") String appName,
			@Param("tenantId") Integer tenantId);
	
	public void updateAppSecret(App app);
	
	public void delApp(App app);

}
