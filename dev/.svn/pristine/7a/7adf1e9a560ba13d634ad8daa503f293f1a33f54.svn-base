package com.bt.radar.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.radar.dao.InterfaceRouteinfoMapper;
import com.bt.radar.model.InterfaceRouteinfo;
import com.bt.radar.service.InterfaceRouteinfoService;
@Service
public class InterfaceRouteinfoServiceImpl<T> extends ServiceSupport<T> implements InterfaceRouteinfoService<T> {

	@Autowired
    private InterfaceRouteinfoMapper<T> mapper;

	public InterfaceRouteinfoMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}
		
	
}
