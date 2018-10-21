package com.bt.lmis.balance.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.dao.StorageDataEstimateMapper;
import com.bt.lmis.balance.model.StorageDataEstimate;
import com.bt.lmis.balance.service.StorageDataEstimateService;

@Service
public class StorageDataEstimateServiceImpl implements StorageDataEstimateService {

	@Autowired
	private StorageDataEstimateMapper dao;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return dao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(StorageDataEstimate record) {
		// TODO Auto-generated method stub
		return dao.insert(record);
	}

	@Override
	public int insertSelective(StorageDataEstimate record) {
		// TODO Auto-generated method stub
		return dao.insertSelective(record);
	}

	@Override
	public StorageDataEstimate selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return dao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(StorageDataEstimate record) {
		// TODO Auto-generated method stub
		return dao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(StorageDataEstimate record) {
		// TODO Auto-generated method stub
		return dao.updateByPrimaryKey(record);
	}

	@Override
	public int save(StorageDataEstimate storageDataEstimate) {
		// TODO Auto-generated method stub
		return dao.insert(storageDataEstimate);
	}

}
