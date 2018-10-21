package com.bt.lmis.balance.service;

import java.util.List;

import com.bt.lmis.balance.model.OperationfeeDataDailySettlementEstimate;

public interface OperationfeeDataDailySettlementEstimateService {
	
	
	public int save(OperationfeeDataDailySettlementEstimate operationfeeDataDailySettlementEstimate);

	public List<OperationfeeDataDailySettlementEstimate> findByEntity(OperationfeeDataDailySettlementEstimate pfd);

	public int countByEntity(OperationfeeDataDailySettlementEstimate pfd);

}
