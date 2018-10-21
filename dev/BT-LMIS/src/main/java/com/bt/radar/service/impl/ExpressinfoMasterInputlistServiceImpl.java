package com.bt.radar.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.ExpressinfoMasterInputlistQueryParam;
import com.bt.radar.dao.ExpressinfoMasterInputlistMapper;
import com.bt.radar.model.ExpressinfoMasterInputlist;
import com.bt.radar.service.ExpressinfoMasterInputlistService;
@Service
public class ExpressinfoMasterInputlistServiceImpl<T> extends ServiceSupport<T> implements ExpressinfoMasterInputlistService<T> {

	@Autowired
    private ExpressinfoMasterInputlistMapper<T> mapper;

	public ExpressinfoMasterInputlistMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public QueryResult<ExpressinfoMasterInputlist> findAll(ExpressinfoMasterInputlistQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<ExpressinfoMasterInputlist> qr =new QueryResult<ExpressinfoMasterInputlist>();
		qr.setResultlist(mapper.findAll(queryParam));
		qr.setTotalrecord(mapper.count(queryParam));
		return qr;
	}

	@Override
	public int checkDel(List<String> list) {
		// TODO Auto-generated method stub
		return mapper.checkDel(list);
	}

	@Override
	public void del(List<String> list) {
		// TODO Auto-generated method stub
		mapper.del(list);
	}
		
	
}
