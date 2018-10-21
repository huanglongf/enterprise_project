package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.TotalVolumeMapper;
import com.bt.lmis.service.TotalVolumeService;
@Service
public class TotalVolumeServiceImpl<T> extends ServiceSupport<T> implements TotalVolumeService<T> {

	@Autowired
    private TotalVolumeMapper<T> mapper;

	public TotalVolumeMapper<T> getMapper() {
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
