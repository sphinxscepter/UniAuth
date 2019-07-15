package com.yyt.UniAuth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yyt.UniAuth.model.entity.OrgUsers;
import com.yyt.UniAuth.model.entity.Organization;

public interface OrganizationMapper {
	
	public Integer addOrg(Organization org);
	
	public List<Organization> getOrgsByTenantId(@Param("tenantId") Integer tenantId);
	
	public Organization getOrgInfoById(@Param("id") Integer id);
	
	public List<OrgUsers> getUsersByOrgId(@Param("orgId") Integer orgId,
			@Param("start") Integer start, @Param("size") Integer size);
	
	public void updateOrg(Organization org);
	
	public void delOrg(Organization org);
	
	public Integer addUserToOrg(OrgUsers orgUsers);
 
}
