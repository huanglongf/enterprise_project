package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.ExpressReturnStorageQueryParam;
import com.bt.lmis.dao.ExpressReturnStorageMapper;
import com.bt.lmis.model.ExpressReturnStorage;
import com.bt.lmis.model.ExpressReturnStorageExample;
import com.bt.lmis.model.ExpressReturnStorageExample.Criteria;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ExpressReturnStorageService;
import com.bt.radar.model.ExpressinfoMaster;
@Service
public class ExpressReturnStorageServiceImpl extends ServiceSupport<T> implements ExpressReturnStorageService<T> {

	@Autowired
	ExpressReturnStorageMapper expressReturnStorageMapper;
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryResult<ExpressReturnStorage> getPageData(ExpressReturnStorageQueryParam expressReturnStorage) {
		// TODO Auto-generated method stub
		QueryResult<ExpressReturnStorage> qr=new QueryResult<ExpressReturnStorage>();
		qr.setResultlist(expressReturnStorageMapper.getPageData(expressReturnStorage));
	   	qr.setTotalrecord(expressReturnStorageMapper.getPageDataCount(expressReturnStorage));
		return qr;
	}

	
}
