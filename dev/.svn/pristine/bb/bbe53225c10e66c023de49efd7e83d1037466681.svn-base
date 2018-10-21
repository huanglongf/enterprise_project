package com.bt.radar.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.radar.controller.form.WarningRoutestatusListQueryParam;
import com.bt.radar.dao.WarningRoutestatusListMapper;
import com.bt.radar.model.WarningRoutestatusList;
import com.bt.radar.service.WarningRoutestatusListService;
@Service
public class WarningRoutestatusListServiceImpl<T> extends ServiceSupport<T> implements WarningRoutestatusListService<T> {

	@Autowired
    private WarningRoutestatusListMapper<T> mapper;

	public WarningRoutestatusListMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<T> findAll(WarningRoutestatusListQueryParam query) {
		// TODO Auto-generated method stub
		return mapper.findAll(query);
	}

	@Override
	public int checkExisit(WarningRoutestatusListQueryParam query) {
		// TODO Auto-generated method stub
		return mapper.checkExisit(query);
	}
		
	
}
