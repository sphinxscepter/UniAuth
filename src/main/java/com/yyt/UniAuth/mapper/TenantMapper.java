package com.yyt.UniAuth.mapper;

import org.apache.ibatis.annotations.Param;

import com.yyt.UniAuth.model.entity.Tenant;

public interface TenantMapper {
	
	public Integer addNewTenant(Tenant tenant);
	
	public Tenant getTenantInfoById(@Param("id") Integer id);
	
	public int getTenantCountByName(@Param("tenantName") String tenantName);
	
	public void delTenant(Tenant tenant);

}
