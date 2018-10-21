package com.bt.login.dao;

import java.util.List;
import java.util.Map;

import com.bt.login.controller.form.UserQueryParam;
import com.bt.login.model.User;

/**
* @ClassName: UserMapper
* @Description: TODO(UserMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface UserMapper<T>{

	List<Map<String, Object>> selectAllUser(UserQueryParam queryParam) throws Exection;

	int countAllUser(UserQueryParam queryParam) throws Exection;

	User selectById(String id) throws Exection;

	void insertUser(UserQueryParam queryParam) throws Exection;

	void updateuser(UserQueryParam queryParam) throws Exection;
	
	
}
