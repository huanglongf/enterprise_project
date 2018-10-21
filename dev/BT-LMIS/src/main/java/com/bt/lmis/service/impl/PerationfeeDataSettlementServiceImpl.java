package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.PerationfeeDataSettlementMapper;
import com.bt.lmis.model.PerationfeeDataSettlement;
import com.bt.lmis.service.PerationfeeDataSettlementService;
@Service
public class PerationfeeDataSettlementServiceImpl<T> extends ServiceSupport<T> implements PerationfeeDataSettlementService<T> {

	@Autowired
    private PerationfeeDataSettlementMapper<T> mapper;

	public PerationfeeDataSettlementMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<PerationfeeDataSettlement> findByList(PerationfeeDataSettlement dataSettlement) {
		return mapper.findByList(dataSettlement);
	}
		
	
}
