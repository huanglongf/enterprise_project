package com.bt.orderPlatform.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.orderPlatform.dao.PcodeMapperingMapper;
import com.bt.orderPlatform.model.PcodeMappering;
import com.bt.orderPlatform.service.PcodeMapperingService;
@Service
public class PcodeMapperingServiceImpl<T> implements PcodeMapperingService<T> {

	@Autowired
    private PcodeMapperingMapper<T> mapper;

	public PcodeMapperingMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public PcodeMappering queryBySfopcode(String route_opcode) {
		// TODO Auto-generated method stub
		return mapper.queryBySfopcode(route_opcode);
	}

		
	
}
