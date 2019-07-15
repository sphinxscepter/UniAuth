package com.yyt.UniAuth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yyt.UniAuth.model.entity.Group;
import com.yyt.UniAuth.model.entity.GroupUsers;
import com.yyt.UniAuth.model.entity.User;

public interface GroupMapper {
	
	public Integer addNewGroup(Group group);
	
	public Group getGrpInfoById(@Param("id") Integer id);
	
	public Integer addUserToGroup(GroupUsers groupUsers);
	
	public List<GroupUsers> getGroupsByUser(@Param("user") User user);
	
	public List<GroupUsers> getUserByGroup(@Param("group") Group group);
	
	public List<Group> getGrpsByApp(@Param("appId") Integer appId);

}
