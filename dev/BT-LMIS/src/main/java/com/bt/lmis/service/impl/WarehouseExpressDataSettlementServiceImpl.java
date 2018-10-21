package com.bt.lmis.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.WarehouseExpressDataSettlementQueryParam;
import com.bt.lmis.dao.WarehouseExpressDataSettlementMapper;
import com.bt.lmis.model.WarehouseExpressDataSettlement;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.WarehouseExpressDataSettlementService;
@Service
public class WarehouseExpressDataSettlementServiceImpl<T> extends ServiceSupport<T> implements WarehouseExpressDataSettlementService<T> {

	@Autowired
    private WarehouseExpressDataSettlementMapper<T> warehouseExpressDataSettlementMapper;

	public WarehouseExpressDataSettlementMapper<T> getMapper() {
		return warehouseExpressDataSettlementMapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return warehouseExpressDataSettlementMapper.selectCount(param);
	}

	@Override
	public QueryResult<Map<String, Object>> findAll(WarehouseExpressDataSettlementQueryParam query) {
		// TODO Auto-generated method stub
		QueryResult<Map<String, Object>> qr=new QueryResult<Map<String, Object>>();
		qr.setResultlist(warehouseExpressDataSettlementMapper.selectDateList(query));
		qr.setTotalrecord(warehouseExpressDataSettlementMapper.selectCountNum(query));
		return qr;
	}
		
	
}
