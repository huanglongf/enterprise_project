package com.bt.lmis.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.OperationFeeDataSettlementMapper;
import com.bt.lmis.model.OperationFeeDataSettlement;
import com.bt.lmis.service.OperationFeeDataSettlementService;

@Service
public class OperationFeeDataSettlementServiceImpl<T> extends ServiceSupport<T> implements OperationFeeDataSettlementService<T>{

	@Autowired
	private OperationFeeDataSettlementMapper<T> operationFeeDataSettlementMapper;
	
	public OperationFeeDataSettlementMapper<T> getOperationFeeDataSettlementMapper(){
		return operationFeeDataSettlementMapper;
	}
	
	@Override
	public OperationFeeDataSettlement selectRecord(int contract_id, String settle_period) {
		return operationFeeDataSettlementMapper.selectRecord(contract_id, settle_period);
	}
	
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
