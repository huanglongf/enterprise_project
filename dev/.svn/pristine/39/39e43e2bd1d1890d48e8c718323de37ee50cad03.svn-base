package com.bt.lmis.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.ItemtypePriceMapper;
import com.bt.lmis.service.ItemtypePriceService;
@Service
public class ItemtypePriceServiceImpl<T> extends ServiceSupport<T> implements ItemtypePriceService<T> {

	@Autowired
    private ItemtypePriceMapper<T> mapper;

	public ItemtypePriceMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}
		
	
}
