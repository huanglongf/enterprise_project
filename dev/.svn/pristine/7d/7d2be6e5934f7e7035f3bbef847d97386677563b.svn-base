package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.AllTrayMapper;
import com.bt.lmis.service.AllTrayService;
@Service
public class AllTrayServiceImpl<T> extends ServiceSupport<T> implements AllTrayService<T> {

	@Autowired
    private AllTrayMapper<T> mapper;

	public AllTrayMapper<T> getMapper() {
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
