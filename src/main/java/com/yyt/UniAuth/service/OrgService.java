package com.yyt.UniAuth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyt.UniAuth.mapper.OrganizationMapper;
import com.yyt.UniAuth.model.entity.Organization;

@Service
public class OrgService {
	
	private static Logger logger = LoggerFactory.getLogger(OrgService.class);
	
	@Autowired
	private OrganizationMapper organizationMapper;
	
	public void addOrg(Organization org) {
		organizationMapper.addOrg(org);
		logger.info("新增加的部门[" + org.getOrgName() + "]的id为" + org.getId());
	}
	
	public String updateOrg(Organization org) {
		Organization orgVal = organizationMapper.getOrgInfoById(org.getId());
		if(null == orgVal) {
			logger.info("要修改的部门id[" + org.getId() + "]在系统中不存在");
			return "要修改的部门[" + org.getOrgName() + "]在系统中不存在";
		}
		organizationMapper.updateOrg(org);
		logger.info("修改部门[" + org.getId() + "]成功");
		return "OK";
	}
	
	public String delOrg(Integer id) {
		Organization org = organizationMapper.getOrgInfoById(id);
		if(org == null) {
			logger.info("要删除的部门id[" + id + "]在系统中不存在");
			return "要删除的部门在系统中不存在";
		}
		org.setDel(1);
		organizationMapper.delOrg(org);;
		logger.info("删除部门[" + org.getId() + "]成功");
		return "OK";
	}
	
}
