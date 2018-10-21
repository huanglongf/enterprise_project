package com.bt.lmis.balance.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.dao.AddservicefeeBilldataEstimateMapper;
import com.bt.lmis.balance.model.AddservicefeeBilldataEstimate;
import com.bt.lmis.balance.service.AddservicefeeBilldataEstimateService;
import com.bt.lmis.code.ServiceSupport;
@Service
public class AddservicefeeBilldataEstimateServiceImpl<T> extends ServiceSupport<T> implements AddservicefeeBilldataEstimateService<T> {

	@Autowired
    private AddservicefeeBilldataEstimateMapper<T> mapper;

	public AddservicefeeBilldataEstimateMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<AddservicefeeBilldataEstimate> findByList(AddservicefeeBilldataEstimate afb) {
		// TODO Auto-generated method stub
		return mapper.findByList(afb);
	}
		
	
}
