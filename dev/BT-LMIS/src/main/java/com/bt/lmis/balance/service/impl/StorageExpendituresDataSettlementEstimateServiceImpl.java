package com.bt.lmis.balance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.dao.StorageExpendituresDataSettlementEstimateMapper;
import com.bt.lmis.balance.model.StorageExpendituresDataSettlementEstimate;
import com.bt.lmis.balance.service.StorageExpendituresDataSettlementEstimateService;

@Service
public class StorageExpendituresDataSettlementEstimateServiceImpl implements StorageExpendituresDataSettlementEstimateService {

	@Autowired
	private StorageExpendituresDataSettlementEstimateMapper dao;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return dao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(StorageExpendituresDataSettlementEstimate record) {
		// TODO Auto-generated method stub
		return dao.insert(record);
	}

	@Override
	public int insertSelective(StorageExpendituresDataSettlementEstimate record) {
		// TODO Auto-generated method stub
		return dao.insertSelective(record);
	}

	@Override
	public StorageExpendituresDataSettlementEstimate selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return dao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(StorageExpendituresDataSettlementEstimate record) {
		// TODO Auto-generated method stub
		return dao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(StorageExpendituresDataSettlementEstimate record) {
		// TODO Auto-generated method stub
		return dao.updateByPrimaryKey(record);
	}

	@Override
	public int save(StorageExpendituresDataSettlementEstimate storageExpendituresDataSettlementEstimate) {
		// TODO Auto-generated method stub
		return dao.insert(storageExpendituresDataSettlementEstimate);
	}

	@Override
	public List<StorageExpendituresDataSettlementEstimate> findBySEDS(
			StorageExpendituresDataSettlementEstimate storageExpendituresDataSettlementEstimate) {
		// TODO Auto-generated method stub
		return dao.findBySEDS(storageExpendituresDataSettlementEstimate);
	}

}
