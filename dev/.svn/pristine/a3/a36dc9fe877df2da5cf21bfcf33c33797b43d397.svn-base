package com.bt.lmis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.dao.ContractBasicinfoMapper;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.service.OtherService;

@Service
public class OtherServiceImpl implements OtherService {

	@Autowired
	private ContractBasicinfoMapper<ContractBasicinfo> contractBasicinfoMapper;
	
	@Override
	public void saveAB(String name) throws Exception{
		contractBasicinfoMapper.textA(name);
		contractBasicinfoMapper.textB(name);
	}
}
