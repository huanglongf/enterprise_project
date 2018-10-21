package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.WarehouseAreaMapper;
import com.bt.lmis.service.WarehouseAreaService;
@Service
public class WarehouseAreaServiceImpl<T> extends ServiceSupport<T> implements WarehouseAreaService<T> {

	@Autowired
    private WarehouseAreaMapper<T> mapper;

	public WarehouseAreaMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<Map<String, Object>> findByWhid(int id) {
		return mapper.findByWhid(id);
	}
		
	
}
