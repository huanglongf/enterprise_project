package com.bt.lmis.balance.service;

import java.util.List;

import com.bt.lmis.balance.model.AddservicefeeEstimateSettlement;
import com.bt.lmis.code.BaseService;

public interface AddservicefeeEstimateSettlementService<T> extends BaseService<T> {
	public List<AddservicefeeEstimateSettlement> findByAll(AddservicefeeEstimateSettlement afs);

	public List<AddservicefeeEstimateSettlement> findByAds(AddservicefeeEstimateSettlement afs);
}
