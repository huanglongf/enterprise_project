package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.InvitationUseanmountDataQueryParam;
import com.bt.lmis.dao.InvitationUseanmountDataMapper;
import com.bt.lmis.model.InvitationUseanmountData;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.InvitationUseanmountDataService;
@Service
public class InvitationUseanmountDataServiceImpl<T> extends ServiceSupport<T> implements InvitationUseanmountDataService<T> {

	@Autowired
    private InvitationUseanmountDataMapper<T> mapper;

	public InvitationUseanmountDataMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<Map<String, Object>> findAll() {
		return mapper.findAll();
	}

	@Override
	public QueryResult<Map<String,Object>> findAllData(QueryParameter queryParam) {
		
		QueryResult<Map<String,Object>> qr = new QueryResult<Map<String,Object>>();
		qr.setResultlist(mapper.findAllData(queryParam));
		qr.setTotalrecord(mapper.findDataCount(queryParam));
		return qr;
	}
		
	
	
}
