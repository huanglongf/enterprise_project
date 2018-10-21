package com.bt.lmis.balance.dao;

import com.bt.lmis.balance.model.StorageDataEstimate;

public interface StorageDataEstimateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StorageDataEstimate record);

    int insertSelective(StorageDataEstimate record);

    StorageDataEstimate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StorageDataEstimate record);

    int updateByPrimaryKey(StorageDataEstimate record);
}