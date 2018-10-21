package com.bt.workOrder.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.workOrder.dao.WkLevelMapper;
import com.bt.workOrder.model.WkLevel;
import com.bt.workOrder.service.WkLevelService;
@Service
public class WkLevelServiceImpl<T> extends ServiceSupport<T> implements WkLevelService<T> {

	@Autowired
    private WkLevelMapper<T> mapper;

	public WkLevelMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}
		
	
}
