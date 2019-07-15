package com.yyt.UniAuth.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyt.UniAuth.mapper.UserMapper;
import com.yyt.UniAuth.model.entity.AuditLog;
import com.yyt.UniAuth.model.entity.GroupUsers;
import com.yyt.UniAuth.model.entity.Tenant;
import com.yyt.UniAuth.model.entity.User;
import com.yyt.UniAuth.service.AuditLogService;
import com.yyt.UniAuth.utils.AuditLogThread;
import com.yyt.UniAuth.utils.ExceptionDetail;

@Service
public class UserService implements UserDetailsService {
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private TenantService tenantService;
	@Autowired
	private GroupUsersService groupUsersService;
	@Autowired
	private AuditLogService auditLogService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		logger.info(passwordEncoder.encode("admin1234"));
		try {
			User user = userMapper.getUserByMobile(username);
			if(null == user) {
				logger.info("手机号码[" + username + "]对应的用户在系统中不存在");
				throw new UsernameNotFoundException("用户不存在");
			}
			user.setAccountNonLocked(true);
			user.setAccountNonExpired(true);
			user.setCredentialsNonExpired(true);
			user.setEnabled(true);
			return user;
		} catch (Exception e) {
			logger.error("根据用户手机号码[" + username + "]查询用户信息异常!");
			logger.error(ExceptionDetail.getTrace(e));
			throw new UsernameNotFoundException("查询用户异常");
		}
	}
	
	@Transactional
	public void regUser(User user) throws JsonProcessingException {
		///创建租户
		Tenant tenant = new Tenant();
		tenant.setTenantName(UUID.randomUUID().toString().replace("-", "").toUpperCase());
		tenant.setCustomId(0);
		tenantService.addTenant(tenant);
		logger.info("新创建的租户名称为：" + tenant.getTenantName());
		logger.info("新创建的租户id为：" + tenant.getId());
		
		///创建用户
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setTenantId(tenant.getId());
		userMapper.addUser(user);
		logger.info("新增用户[" + user.getUsername() + "]的id为:" + user.getId());
		
		///将用户添加至租户管理员组
		GroupUsers groupUsers = new GroupUsers();
		groupUsers.setGrpId(2);
		groupUsers.setUserId(user.getId());
		groupUsers.setTenantId(tenant.getId());
		groupUsersService.addGrpUsers(groupUsers);
		logger.info("新增的用户与组的关联关系id为：" + groupUsers.getId());
		
		///添加操作记录
		List<AuditLog> auditLogs = new ArrayList<AuditLog>();
		ObjectMapper mapper = new ObjectMapper();
		for(int i = 0; i < 3; i++) {
			AuditLog auditLog = new AuditLog();
			auditLog.setAppId(0);
			auditLog.setTenantId(tenant.getId());
			auditLog.setOpUser(user.getId());
			auditLog.setOpTime(new Timestamp(System.currentTimeMillis()));
			if(i == 0) {
				auditLog.setOpDataId(tenant.getId());
				auditLog.setOpTable("tenant");
				auditLog.setOpType("CR_TENANT");
				auditLog.setOpDesc(mapper.writeValueAsString(tenant));
			} else if(i == 1) {
				auditLog.setOpDataId(user.getId());
				auditLog.setOpTable("user");
				auditLog.setOpType("CR_USER");
				auditLog.setOpDesc(mapper.writeValueAsString(user));
			} else {
				auditLog.setOpDataId(groupUsers.getId());
				auditLog.setOpTable("grp_users");
				auditLog.setOpType("ADD_USER_GRP");
				auditLog.setOpDesc(mapper.writeValueAsString(groupUsers));
			}
			auditLogs.add(auditLog);
		}
		AuditLogThread auditLogThread = new AuditLogThread(auditLogService, auditLogs);
		new Thread(auditLogThread).start();
	}
	
	public boolean checkUser(String mobile) {
		User user = userMapper.getUserByMobile(mobile);
		if(null == user) {
			return false;
		} else {
			return true;
		}
	}
	
	public User getUserInfoByMobile(String mobile) {
		return userMapper.getUserByMobile(mobile);
	}
	

}
