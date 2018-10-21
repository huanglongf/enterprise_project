package com.bt.lmis.balance.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.dao.AddservicefeeEstimateSettlementMapper;
import com.bt.lmis.balance.model.AddservicefeeEstimateSettlement;
import com.bt.lmis.balance.service.AddservicefeeEstimateSettlementService;
import com.bt.lmis.code.ServiceSupport;
@Service
public class AddservicefeeEstimateSettlementServiceImpl<T> extends ServiceSupport<T> implements AddservicefeeEstimateSettlementService<T> {

	@Autowired
    private AddservicefeeEstimateSettlementMapper<T> mapper;

	public AddservicefeeEstimateSettlementMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<AddservicefeeEstimateSettlement> findByAll(AddservicefeeEstimateSettlement afs) {
		return mapper.findByAll(afs);
	}
	
	@Override
	public List<AddservicefeeEstimateSettlement> findByAds(AddservicefeeEstimateSettlement afs) {
		return mapper.findByAds(afs);
	}
		
	
}
