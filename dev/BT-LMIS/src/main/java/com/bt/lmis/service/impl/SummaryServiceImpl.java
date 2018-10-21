package com.bt.lmis.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.StorageExpendituresDataSettlementQueryParam;
import com.bt.lmis.dao.SummaryMapper;
import com.bt.lmis.model.StorageExpendituresDataSettlement;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.SummaryService;
@Service
public class SummaryServiceImpl<T> extends ServiceSupport<T> implements SummaryService<T> {

	@Autowired
    private SummaryMapper<T> mapper;

	public SummaryMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public QueryResult<StorageExpendituresDataSettlement> findAll(StorageExpendituresDataSettlementQueryParam queryParam) {
		QueryResult<StorageExpendituresDataSettlement> qr = new QueryResult<StorageExpendituresDataSettlement>();
		qr.setResultlist(mapper.find(queryParam));
		qr.setTotalrecord(mapper.countRecords(queryParam));
		return qr;
	}
		
	
}
