package com.bt.lmis.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.BtcobopNumTableMapper;
import com.bt.lmis.model.BtcobopNumTable;
import com.bt.lmis.service.BtcobopNumTableService;
@Service
public class BtcobopNumTableServiceImpl<T> extends ServiceSupport<T> implements BtcobopNumTableService<T> {

	@Autowired
    private BtcobopNumTableMapper<T> mapper;

	public BtcobopNumTableMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}
		
	
}
