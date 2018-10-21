package com.bt.orderPlatform.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.orderPlatform.dao.CostCenterMapper;
import com.bt.orderPlatform.model.CostCenter;
import com.bt.orderPlatform.service.CostCenterService;
@Service
public class CostCenterServiceImpl<T> implements CostCenterService<T> {

	@Autowired
    private CostCenterMapper<T> mapper;

	public CostCenterMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public List<CostCenter> selectAll() {
		// TODO Auto-generated method stub
		return mapper.selectAll();
	}

	@Override
	public CostCenter selectByName(String mis) {
		// TODO Auto-generated method stub
		return mapper.selectByName(mis);
	}

	@Override
	public CostCenter selectByCostCenter(String cost_center) {
		// TODO Auto-generated method stub
		return mapper.selectByCostCenter(cost_center);
	}
		
	
}
