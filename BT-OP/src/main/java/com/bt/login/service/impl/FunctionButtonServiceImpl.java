package com.bt.login.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.login.dao.FunctionButtonMapper;
import com.bt.login.service.FunctionButtonService;
@Service
public class FunctionButtonServiceImpl<T> implements FunctionButtonService<T> {

	@Autowired
    private FunctionButtonMapper<T> mapper;

	public FunctionButtonMapper<T> getMapper() {
		return mapper;
	}

	
}
