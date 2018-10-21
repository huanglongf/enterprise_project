package com.bt.orderPlatform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.orderPlatform.controller.form.ExpressinfoMasterInputlistQueryParam;
import com.bt.orderPlatform.dao.ExpressinfoMasterInputlistMapper;
import com.bt.orderPlatform.model.ExpressinfoMasterInputlist;
import com.bt.orderPlatform.page.QueryResult;
import com.bt.orderPlatform.service.ExpressinfoMasterInputlistService;
@Service
public class ExpressinfoMasterInputlistServiceImpl<T> implements ExpressinfoMasterInputlistService<T> {

	@Autowired
    private ExpressinfoMasterInputlistMapper<T> mapper;

	public ExpressinfoMasterInputlistMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public QueryResult<Map<String, Object>> findAllExpressinfoMasterInputlist(ExpressinfoMasterInputlistQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<Map<String, Object>> qr= new QueryResult<Map<String, Object>>();
		qr.setResultlist(mapper.selectAllExpressinfoMasterInputlist(queryParam));
		qr.setTotalrecord(mapper.countAllExpressinfoMasterInputlist(queryParam));
		return qr;
	}

	@Override
	public void insertExpressinfoMasterInputlist(ExpressinfoMasterInputlist expressinfoMasterInputlist) {
		// TODO Auto-generated method stub
		mapper.insertExpressinfoMasterInputlist(expressinfoMasterInputlist);
	}

	@Override
	public void deletetByBatId(String id) {
		// TODO Auto-generated method stub
		mapper.deletetByBatId(id);
	}

	@Override
	public List<ExpressinfoMasterInputlist> selectByBatId(String id) {
		// TODO Auto-generated method stub
		return mapper.selectByBatId(id);
	}

	@Override
	public void updateByBatId(String id, Integer flag) {
		// TODO Auto-generated method stub
		mapper.updateByBatId(id,flag);
	}

	@Override
	public void updateByBatIdAndSuccess(String id, Integer total_num) {
		// TODO Auto-generated method stub
		mapper.updateByBatIdAndSuccess(id,total_num);
	}

	
}
