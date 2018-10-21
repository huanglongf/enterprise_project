package com.bt.lmis.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.BtcobopbillTableMapper;
import com.bt.lmis.model.BtcobopbillTable;
import com.bt.lmis.service.BtcobopbillTableService;
@Service
public class BtcobopbillTableServiceImpl<T> extends ServiceSupport<T> implements BtcobopbillTableService<T> {

	@Autowired
    private BtcobopbillTableMapper<T> mapper;

	public BtcobopbillTableMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}
		
	
}
