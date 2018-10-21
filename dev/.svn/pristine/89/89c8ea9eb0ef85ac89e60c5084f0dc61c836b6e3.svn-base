package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.AllAreaMapper;
import com.bt.lmis.service.AllAreaService;
@Service
public class AllAreaServiceImpl<T> extends ServiceSupport<T> implements AllAreaService<T> {

	@Autowired
    private AllAreaMapper<T> mapper;

	public AllAreaMapper<T> getMapper() {
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
