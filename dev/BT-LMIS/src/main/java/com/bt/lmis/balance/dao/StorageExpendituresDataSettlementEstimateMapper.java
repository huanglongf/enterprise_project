package com.bt.lmis.balance.dao;

import java.util.List;

import com.bt.lmis.balance.model.StorageExpendituresDataSettlementEstimate;

public interface StorageExpendituresDataSettlementEstimateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StorageExpendituresDataSettlementEstimate record);

    int insertSelective(StorageExpendituresDataSettlementEstimate record);

    StorageExpendituresDataSettlementEstimate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StorageExpendituresDataSettlementEstimate record);

    int updateByPrimaryKey(StorageExpendituresDataSettlementEstimate record);

	List<StorageExpendituresDataSettlementEstimate> findBySEDS(
			StorageExpendituresDataSettlementEstimate storageExpendituresDataSettlementEstimate);
}