package com.bt.lmis.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.CollectionMasterMapper;
import com.bt.lmis.model.CollectionMaster;
import com.bt.lmis.service.CollectionMasterService;
@Service
public class CollectionMasterServiceImpl<T> extends ServiceSupport<T> implements CollectionMasterService<T> {

	@Autowired
    private CollectionMasterMapper<T> mapper;

	public CollectionMasterMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}
		
	
}
