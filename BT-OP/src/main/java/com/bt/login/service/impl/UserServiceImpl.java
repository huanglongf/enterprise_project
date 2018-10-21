package com.bt.login.service.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.login.controller.form.UserQueryParam;
import com.bt.login.dao.UserMapper;
import com.bt.login.model.User;
import com.bt.login.service.UserService;
import com.bt.orderPlatform.page.QueryResult;
@Service
public class UserServiceImpl<T> implements UserService<T> {

	@Autowired
    private UserMapper<T> mapper;

	public UserMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public QueryResult<Map<String, Object>> findUser(UserQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
		qr.setResultlist(mapper.selectAllUser(queryParam));
		qr.setTotalrecord(mapper.countAllUser(queryParam));
		return qr;
	}

	@Override
	public User selectById(String id) {
		// TODO Auto-generated method stub
		return mapper.selectById(id);
	}

	@Override
	public void insertUser(UserQueryParam queryParam) {
		// TODO Auto-generated method stub
		mapper.insertUser(queryParam);
	}

	@Override
	public void updateuser(UserQueryParam queryParam) {
		// TODO Auto-generated method stub
		mapper.updateuser(queryParam);
	}

	
}
