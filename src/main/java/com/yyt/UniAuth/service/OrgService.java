package com.yyt.UniAuth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yyt.UniAuth.mapper.OrganizationMapper;
import com.yyt.UniAuth.mapper.UserMapper;
import com.yyt.UniAuth.model.entity.OrgUsers;
import com.yyt.UniAuth.model.entity.Organization;
import com.yyt.UniAuth.model.entity.User;
import com.yyt.UniAuth.model.vo.OrgAddUserVO;

@Service
public class OrgService {
	
	private static Logger logger = LoggerFactory.getLogger(OrgService.class);
	
	@Autowired
	private OrganizationMapper organizationMapper;
	@Autowired
	private UserMapper userMapper;
	
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
	
	@Transactional
	public String addUsersToOrg(OrgAddUserVO addUserVO) {
		Organization org = organizationMapper.getOrgInfoById(addUserVO.getOrgId());
		if(org == null) {
			return "部门id[" + addUserVO.getOrgId() + "]在系统中不存在";
		}
		StringBuffer sb = new StringBuffer();
		for(Integer userId : addUserVO.getUserIds()) {
			User user = userMapper.getUserById(userId);
			if(user == null) {
				logger.warn("用户id[" + userId + "]在系统中不存在");
				if(sb.length() > 0) {
					sb.append(",");
				}
				sb.append(userId);
				continue;
			}
			OrgUsers orgUsers = new OrgUsers();
			orgUsers.setOrgId(addUserVO.getOrgId());
			orgUsers.setUserId(userId);
			orgUsers.setTenantId(org.getTenantId());
			organizationMapper.addUserToOrg(orgUsers);
		}
		if(sb.length() == 0) {
			return "OK";
		} else {
			return sb.toString();
		}
	}
	
}
