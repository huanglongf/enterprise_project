package com.bt.lmis.balance.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.dao.OperationfeeDataEstimateMapper;
import com.bt.lmis.balance.model.OperationfeeDataEstimate;
import com.bt.lmis.balance.service.OperationfeeDataEstimateService;

/**
 * 
 * @author jindong.lin
 *
 */
@Service
public class OperationfeeDataEstimateServiceImpl implements OperationfeeDataEstimateService {

	@Autowired
	private OperationfeeDataEstimateMapper dao;
	
	@Override
	public int save(OperationfeeDataEstimate operationfeeDataEstimate) {
		// TODO Auto-generated method stub
		return dao.insert(operationfeeDataEstimate);
	}

}
