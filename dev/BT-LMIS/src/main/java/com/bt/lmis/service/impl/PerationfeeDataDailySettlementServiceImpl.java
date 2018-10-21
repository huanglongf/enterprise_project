package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.PerationfeeDataDailySettlementMapper;
import com.bt.lmis.model.PerationfeeDataDailySettlement;
import com.bt.lmis.service.PerationfeeDataDailySettlementService;
@Service
public class PerationfeeDataDailySettlementServiceImpl<T> extends ServiceSupport<T> implements PerationfeeDataDailySettlementService<T> {

	@Autowired
    private PerationfeeDataDailySettlementMapper<T> mapper;

	public PerationfeeDataDailySettlementMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<PerationfeeDataDailySettlement> findByEntity(PerationfeeDataDailySettlement pfd) {
		return mapper.findByEntity(pfd);
	}
		
	
}
