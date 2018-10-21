package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.AddservicefeeDataSettlementMapper;
import com.bt.lmis.model.AddservicefeeDataSettlement;
import com.bt.lmis.service.AddservicefeeDataSettlementService;
@Service
public class AddservicefeeDataSettlementServiceImpl<T> extends ServiceSupport<T> implements AddservicefeeDataSettlementService<T> {

	@Autowired
    private AddservicefeeDataSettlementMapper<T> mapper;

	public AddservicefeeDataSettlementMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<AddservicefeeDataSettlement> findByAll(AddservicefeeDataSettlement afs) {
		return mapper.findByAll(afs);
	}
	
	@Override
	public List<AddservicefeeDataSettlement> findByAds(AddservicefeeDataSettlement afs) {
		return mapper.findByAds(afs);
	}
		
	
}
