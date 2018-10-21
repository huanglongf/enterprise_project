package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.TotalAreaMapper;
import com.bt.lmis.service.TotalAreaService;
@Service
public class TotalAreaServiceImpl<T> extends ServiceSupport<T> implements TotalAreaService<T> {

	@Autowired
    private TotalAreaMapper<T> mapper;

	public TotalAreaMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<Map<String, Object>> findByCBID(String id) {
		return mapper.findByCBID(id);
	}
		
	
}
