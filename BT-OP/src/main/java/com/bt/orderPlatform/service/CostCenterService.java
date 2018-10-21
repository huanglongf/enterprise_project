package com.bt.orderPlatform.service;

import java.util.List;

import com.bt.orderPlatform.model.CostCenter;

public interface CostCenterService<T> {

	List<CostCenter> selectAll();

	CostCenter selectByName(String mis);

	CostCenter selectByCostCenter(String cost_center);

}
