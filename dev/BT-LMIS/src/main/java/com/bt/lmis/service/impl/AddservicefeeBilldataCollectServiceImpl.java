package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.AddservicefeeBilldataCollectMapper;
import com.bt.lmis.model.AddservicefeeBilldataCollect;
import com.bt.lmis.service.AddservicefeeBilldataCollectService;
@Service
public class AddservicefeeBilldataCollectServiceImpl<T> extends ServiceSupport<T> implements AddservicefeeBilldataCollectService<T> {

	@Autowired
    private AddservicefeeBilldataCollectMapper<T> mapper;
	
	public AddservicefeeBilldataCollectMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<AddservicefeeBilldataCollect> findByList(AddservicefeeBilldataCollect addservicefeeBilldataCollect) {
		return mapper.findByList(addservicefeeBilldataCollect);
	}

	@Override
	public List<Map<String, String>> findGroupByServiceName() {
		return mapper.findGroupByServiceName();
	}

}
