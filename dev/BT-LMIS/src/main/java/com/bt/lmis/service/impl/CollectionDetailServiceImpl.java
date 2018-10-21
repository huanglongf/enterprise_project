package com.bt.lmis.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.CollectionDetailMapper;
import com.bt.lmis.model.CollectionDetail;
import com.bt.lmis.service.CollectionDetailService;
@Service
public class CollectionDetailServiceImpl<T> extends ServiceSupport<T> implements CollectionDetailService<T> {

	@Autowired
    private CollectionDetailMapper<T> mapper;

	public CollectionDetailMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}
		
	
}
