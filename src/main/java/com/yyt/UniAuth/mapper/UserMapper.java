package com.yyt.UniAuth.mapper;

import org.apache.ibatis.annotations.Param;

import com.yyt.UniAuth.model.entity.User;

public interface UserMapper {
	
	public User getUserByMobile(@Param("mobile") String mobile);
	
	public User getUserById(@Param("id") Integer id);
	
	public Integer addUser(User user);

}
