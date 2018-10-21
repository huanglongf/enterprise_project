package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.dao.WarehouseExpressDataCMapper;
import com.bt.lmis.model.WarehouseExpressDataC;
import com.bt.lmis.model.YFSettlementVo;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.WarehouseExpressDataCService;
@Service
public class WarehouseExpressDataCServiceImpl implements WarehouseExpressDataCService {

	@Autowired
    private WarehouseExpressDataCMapper mapper;

	public WarehouseExpressDataCMapper getMapper() {
		return mapper;
	}

	@Override
	public QueryResult<WarehouseExpressDataC> findAll(WarehouseExpressDataC queryParam) {
		QueryResult<WarehouseExpressDataC> qr = new QueryResult<WarehouseExpressDataC>();
		qr.setResultlist(mapper.findAll(queryParam));
		qr.setTotalrecord(mapper.getCount(queryParam));
		return qr;
	}

	@Override
	public int getCount(YFSettlementVo map) {
		return mapper.count(map);
	}

	@Override
	public List<Map<String, Object>> queryExport(YFSettlementVo map) {
		return mapper.queryExport(map);
	}
}
