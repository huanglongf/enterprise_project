package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.ExpressbillDetailTempMapper;
import com.bt.lmis.model.ExpressbillDetailTemp;
import com.bt.lmis.service.ExpressbillDetailTempService;
@Service
public class ExpressbillDetailTempServiceImpl<T> extends ServiceSupport<T> implements ExpressbillDetailTempService<T> {

	@Autowired
    private ExpressbillDetailTempMapper<T> mapper;

	public ExpressbillDetailTempMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<Map<String, Object>> selectByBatId(String bat_id) {
		// TODO Auto-generated method stub
		return mapper.selectByBatId(bat_id);
	}
		
	
}
