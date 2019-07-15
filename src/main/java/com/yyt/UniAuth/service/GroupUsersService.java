package com.yyt.UniAuth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyt.UniAuth.mapper.GroupMapper;
import com.yyt.UniAuth.model.entity.GroupUsers;

@Service
public class GroupUsersService {
	
	@Autowired
	private GroupMapper groupMapper;
	
	public void addGrpUsers(GroupUsers groupUsers) {
		groupMapper.addUserToGroup(groupUsers);
	}

}
