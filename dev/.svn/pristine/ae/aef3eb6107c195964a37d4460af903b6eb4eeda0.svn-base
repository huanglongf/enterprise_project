package com.bt.lmis.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.BilldeatilsMapper;
import com.bt.lmis.model.Billdeatils;
import com.bt.lmis.service.BilldeatilsService;
@Service
public class BilldeatilsServiceImpl<T> extends ServiceSupport<T> implements BilldeatilsService<T> {

	@Autowired
    private BilldeatilsMapper<T> mapper;

	public BilldeatilsMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}
		
	
}
