package com.bt.lmis.service;

import java.util.List;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.PerationfeeDataDailySettlement;

public interface PerationfeeDataDailySettlementService<T> extends BaseService<T> {
	List<PerationfeeDataDailySettlement> findByEntity(PerationfeeDataDailySettlement pfd);
}
