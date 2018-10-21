package com.bt.lmis.balance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.dao.OperationfeeDataDailySettlementEstimateMapper;
import com.bt.lmis.balance.model.OperationfeeDataDailySettlementEstimate;
import com.bt.lmis.balance.service.OperationfeeDataDailySettlementEstimateService;

@Service
public class OperationfeeDataDailySettlementEstimateServiceImpl  
		implements OperationfeeDataDailySettlementEstimateService {

	@Autowired
	private OperationfeeDataDailySettlementEstimateMapper dao;

	@Override
	public int save(OperationfeeDataDailySettlementEstimate operationfeeDataDailySettlementEstimate) {
		// TODO Auto-generated method stub
		return dao.insert(operationfeeDataDailySettlementEstimate);
	}

	@Override
	public List<OperationfeeDataDailySettlementEstimate> findByEntity(OperationfeeDataDailySettlementEstimate pfd) {
		// TODO Auto-generated method stub
		return dao.findByEntity(pfd);
	}

	@Override
	public int countByEntity(OperationfeeDataDailySettlementEstimate pfd) {
		// TODO Auto-generated method stub
		return dao.countByEntity(pfd);
	}
	
	

}
