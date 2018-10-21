package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.dao.WarehouseExpressDataSettlementCMapper;
import com.bt.lmis.model.WarehouseExpressDataSettlementC;
import com.bt.lmis.model.YFSettlementVo;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.WarehouseExpressDataSettlementCService;
@Service
public class WarehouseExpressDataSettlementCServiceImpl implements WarehouseExpressDataSettlementCService {

	@Autowired
    private WarehouseExpressDataSettlementCMapper mapper;

	public WarehouseExpressDataSettlementCMapper getMapper() {
		return mapper;
	}

	@Override
	public QueryResult<WarehouseExpressDataSettlementC> findAll(WarehouseExpressDataSettlementC queryParam) {
		QueryResult<WarehouseExpressDataSettlementC> qr = new QueryResult<WarehouseExpressDataSettlementC>();
		qr.setResultlist(mapper.findAll(queryParam));
		qr.setTotalrecord(mapper.getCount(queryParam));
		return qr;
	}

	@Override
	public List<Map<String, Object>> queryExport(YFSettlementVo map) {
		
		return mapper.queryExport(map);
	}

	@Override
	public int getCount(YFSettlementVo map) {
		// TODO Auto-generated method stub
		return mapper.count(map);
	}
}
