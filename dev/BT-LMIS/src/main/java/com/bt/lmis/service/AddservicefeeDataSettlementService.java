package com.bt.lmis.service;

import java.util.List;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.AddservicefeeDataSettlement;

public interface AddservicefeeDataSettlementService<T> extends BaseService<T> {
	public List<AddservicefeeDataSettlement> findByAll(AddservicefeeDataSettlement afs);

	public List<AddservicefeeDataSettlement> findByAds(AddservicefeeDataSettlement afs);
}
