package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.ItemTypeMapper;
import com.bt.lmis.service.ItemTypeService;
@Service
public class ItemTypeServiceImpl<T> extends ServiceSupport<T> implements ItemTypeService<T> {

	@Autowired
    private ItemTypeMapper<T> mapper;

	public ItemTypeMapper<T> getMapper() {
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
		
	
}
