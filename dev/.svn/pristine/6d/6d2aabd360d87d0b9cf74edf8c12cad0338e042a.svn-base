package com.bt.vims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.vims.dao.UserMapper;
import com.bt.vims.model.User;
import com.bt.vims.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private UserMapper mapper;
	
	@Override
	public List<User> query_user_by_id(int id) {
		return mapper.query_user_by_id(id);
	}

	@Override
	public User login_check(User user) {
		List<User> uList = mapper.login_check(user);
		if(uList.size()!=0){
			return uList.get(0);
		}else{
			return null;
		}
		
	}

}
