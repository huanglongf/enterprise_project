package com.bt.lmis.balance.dao;

import com.bt.lmis.balance.model.OperationfeeDataEstimate;

public interface OperationfeeDataEstimateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OperationfeeDataEstimate record);

    int insertSelective(OperationfeeDataEstimate record);

    OperationfeeDataEstimate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OperationfeeDataEstimate record);

    int updateByPrimaryKey(OperationfeeDataEstimate record);
}