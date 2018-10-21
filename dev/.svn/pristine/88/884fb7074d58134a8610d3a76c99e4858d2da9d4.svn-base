package com.bt.lmis.balance.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.dao.PackageCharageEstimateMapper;
import com.bt.lmis.balance.service.PackageCharageEstimateService;
import com.bt.lmis.code.ServiceSupport;
@Service
public class PackageCharageEstimateServiceImpl<T> extends ServiceSupport<T> implements PackageCharageEstimateService<T> {

	@Autowired
    private PackageCharageEstimateMapper<T> mapper;

	public PackageCharageEstimateMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}
		
	
}