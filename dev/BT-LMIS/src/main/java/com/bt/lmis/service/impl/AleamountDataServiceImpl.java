package com.bt.lmis.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.AleamountDataMapper;
import com.bt.lmis.model.AleamountData;
import com.bt.lmis.service.AleamountDataService;
@Service
public class AleamountDataServiceImpl<T> extends ServiceSupport<T> implements AleamountDataService<T> {

	@Autowired
    private AleamountDataMapper<T> mapper;

	public AleamountDataMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}
		
	
}
