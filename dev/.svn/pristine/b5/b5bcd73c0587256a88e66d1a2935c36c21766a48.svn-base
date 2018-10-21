package com.bt.radar.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.radar.dao.ExpressinfoDetailMapper;
import com.bt.radar.model.ExpressinfoDetail;
import com.bt.radar.service.ExpressinfoDetailService;
@Service
public class ExpressinfoDetailServiceImpl<T> extends ServiceSupport<T> implements ExpressinfoDetailService<T> {

	@Autowired
    private ExpressinfoDetailMapper<T> mapper;

	public ExpressinfoDetailMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}
		
	
}
