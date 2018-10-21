package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.WarehouseExpressDataStoreSettlementQueryParam;
import com.bt.lmis.dao.WarehouseExpressDataStoreSettlementMapper;
import com.bt.lmis.model.PackageCharageSummary;
import com.bt.lmis.model.WarehouseExpressDataStoreSettlement;
import com.bt.lmis.model.YFSettlementVo;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.WarehouseExpressDataStoreSettlementService;
@Service
public class WarehouseExpressDataStoreSettlementServiceImpl<T> extends ServiceSupport<T> implements WarehouseExpressDataStoreSettlementService<T> {

	@Autowired
    private WarehouseExpressDataStoreSettlementMapper<T> mapper;

	public WarehouseExpressDataStoreSettlementMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public void insertPGSummary(PackageCharageSummary pcs) {
		mapper.insertPGSummary(pcs);
	}
		
	@Override
	public QueryResult<WarehouseExpressDataStoreSettlement> findAll(WarehouseExpressDataStoreSettlementQueryParam queryParam) {
		QueryResult<WarehouseExpressDataStoreSettlement> qr = new QueryResult<WarehouseExpressDataStoreSettlement>();
		qr.setResultlist(mapper.findAll(queryParam));
		qr.setTotalrecord(mapper.count(queryParam));
		return qr;
	}

	@Override
	public int getCount(YFSettlementVo map) {
		return mapper.getCount(map);
	}

	@Override
	public List<Map<String, Object>> queryExport(YFSettlementVo map) {
		return mapper.queryExport(map);
	}
}
