package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.TotalVolumeCalculationMapper;
import com.bt.lmis.service.TotalVolumeCalculationService;
@Service
public class TotalVolumeCalculationServiceImpl<T> extends ServiceSupport<T> implements TotalVolumeCalculationService<T> {

	@Autowired
    private TotalVolumeCalculationMapper<T> mapper;

	public TotalVolumeCalculationMapper<T> getMapper() {
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
