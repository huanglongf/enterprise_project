package com.bt.orderPlatform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.orderPlatform.dao.InterfaceInformationMapper;
import com.bt.orderPlatform.model.InterfaceInformation;
import com.bt.orderPlatform.service.InterfaceInformationService;

@Service
public class InterfaceInformationServiceImpl<T> implements InterfaceInformationService<T> 
{

	@Autowired
	private InterfaceInformationMapper<T> interfaceInformationMapper;

	public InterfaceInformationMapper<T> getMapper() {
		return interfaceInformationMapper;
	}

	@Override
	public InterfaceInformation selectByCustid(String custid) {
		// TODO Auto-generated method stub
		return interfaceInformationMapper.selectByCustid(custid);
	}

	

}
