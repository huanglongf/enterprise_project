package com.bt.lmis.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.InternalPriceMapper;
import com.bt.lmis.service.InternalPriceService;
@Service
public class InternalPriceServiceImpl<T> extends ServiceSupport<T> implements InternalPriceService<T> {

	@Autowired
    private InternalPriceMapper<T> mapper;

	public InternalPriceMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}
		
	
}
