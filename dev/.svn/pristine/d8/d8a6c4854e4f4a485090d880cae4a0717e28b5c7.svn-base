package com.bt.lmis.balance.dao;

import java.util.List;

import com.bt.lmis.balance.model.Contract;

public interface ContractMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Contract record);

    int insertSelective(Contract record);

    Contract selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Contract record);

    int updateByPrimaryKey(Contract record);
    
    List<Contract> findAll();

    List<Integer> findAllId();
}