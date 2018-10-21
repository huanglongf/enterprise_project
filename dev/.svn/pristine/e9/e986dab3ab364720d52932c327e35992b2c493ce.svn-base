package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.PackagePriceLadderMapper;
import com.bt.lmis.model.PackagePriceLadder;
import com.bt.lmis.service.PackagePriceLadderService;
@Service
public class PackagePriceLadderServiceImpl<T> extends ServiceSupport<T> implements PackagePriceLadderService<T> {

	@Autowired
    private PackagePriceLadderMapper<T> mapper;

	public PackagePriceLadderMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<PackagePriceLadder> findByCBID(PackagePriceLadder packagePriceLadder) {
		return mapper.findByCBID(packagePriceLadder);
	}
		
	
}
