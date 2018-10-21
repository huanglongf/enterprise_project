package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.AllVolumeMapper;
import com.bt.lmis.service.AllVolumeService;
@Service
public class AllVolumeServiceImpl<T> extends ServiceSupport<T> implements AllVolumeService<T> {

	@Autowired
    private AllVolumeMapper<T> mapper;

	public AllVolumeMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<Map<String, Object>> findByCBID(String id) {
		return mapper.findByCBID(id);
	}
		
	
}
