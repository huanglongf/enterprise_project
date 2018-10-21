package com.bt.orderPlatform.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.orderPlatform.dao.PayPathMapper;
import com.bt.orderPlatform.model.PayPath;
import com.bt.orderPlatform.service.PayPathService;
@Service
public class PayPathServiceImpl<T>  implements PayPathService<T> {

	@Autowired
    private PayPathMapper<T> mapper;

	public PayPathMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public List<PayPath> queryAll() {
		// TODO Auto-generated method stub
		return mapper.queryAll();
	}

	@Override
	public PayPath selectByname(String pay_path) {
		// TODO Auto-generated method stub
		return mapper.selectByname(pay_path);
	}

		
	
}
