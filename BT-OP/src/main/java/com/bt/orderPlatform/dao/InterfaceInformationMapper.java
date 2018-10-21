package com.bt.orderPlatform.dao;

import com.bt.orderPlatform.model.InterfaceInformation;

public interface InterfaceInformationMapper<T> {
    int deleteByPrimaryKey(Integer id);

    int insert(InterfaceInformation record);

    int insertSelective(InterfaceInformation record);

    InterfaceInformation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InterfaceInformation record);

    int updateByPrimaryKey(InterfaceInformation record);

	InterfaceInformation selectByCustid(String custid);
}