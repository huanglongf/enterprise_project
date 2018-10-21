package com.bt.lmis.balance.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.dao.WarehouseExpressDataStoreSettlementEstimateMapper;
import com.bt.lmis.balance.service.WarehouseExpressDataStoreSettlementEstimateService;
import com.bt.lmis.code.ServiceSupport;
@Service
public class WarehouseExpressDataStoreSettlementEstimateServiceImpl<T> extends ServiceSupport<T> implements WarehouseExpressDataStoreSettlementEstimateService<T> {

	@Autowired
    private WarehouseExpressDataStoreSettlementEstimateMapper<T> mapper;

	public WarehouseExpressDataStoreSettlementEstimateMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}
		
}
