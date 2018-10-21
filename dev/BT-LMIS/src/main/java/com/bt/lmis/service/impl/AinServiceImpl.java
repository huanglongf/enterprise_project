package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.AinMapper;
import com.bt.lmis.model.Ain;
import com.bt.lmis.service.AinService;
@Service
public class AinServiceImpl<T> extends ServiceSupport<T> implements AinService<T> {

	@Autowired
    private AinMapper<T> mapper;

	public AinMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<Ain> findByCBID(String cbid) {
		return mapper.findByCBID(cbid);
	}
		
	
}
