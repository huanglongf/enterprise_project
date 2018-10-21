package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.PerationSettlementFixedMapper;
import com.bt.lmis.model.PerationSettlementFixed;
import com.bt.lmis.service.PerationSettlementFixedService;
@Service
public class PerationSettlementFixedServiceImpl<T> extends ServiceSupport<T> implements PerationSettlementFixedService<T> {

	@Autowired
    private PerationSettlementFixedMapper<T> mapper;

	public PerationSettlementFixedMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<Map<String, Object>> findByEntity(PerationSettlementFixed entity) {
		return mapper.findByEntity(entity);
	}
		
	
}
