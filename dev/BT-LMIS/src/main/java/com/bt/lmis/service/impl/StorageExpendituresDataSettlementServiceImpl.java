package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.StorageExpendituresDataSettlementQueryParam;
import com.bt.lmis.dao.StorageExpendituresDataSettlementMapper;
import com.bt.lmis.model.StorageExpendituresDataSettlement;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.StorageExpendituresDataSettlementService;
@Service
public class StorageExpendituresDataSettlementServiceImpl<T> extends ServiceSupport<T> implements StorageExpendituresDataSettlementService<T> {

	@Autowired
    private StorageExpendituresDataSettlementMapper<T> mapper;

	public StorageExpendituresDataSettlementMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<StorageExpendituresDataSettlement> findBySEDS(StorageExpendituresDataSettlement seds) {
		return mapper.findBySEDS(seds);
	}

	@Override
	public QueryResult<StorageExpendituresDataSettlement> findAll(StorageExpendituresDataSettlementQueryParam queryParam) {
		QueryResult<StorageExpendituresDataSettlement> qr = new QueryResult<StorageExpendituresDataSettlement>();
		qr.setResultlist(mapper.findSEDS(queryParam));
		qr.setTotalrecord(mapper.countSEDSRecords(queryParam));
		return qr;
	}
	
}
