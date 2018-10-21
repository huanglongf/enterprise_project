package com.bt.radar.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.radar.dao.LogicWarehouseMapper;
import com.bt.radar.model.LogicWarehouse;
import com.bt.radar.service.LogicWarehouseService;
@Service
public class LogicWarehouseServiceImpl<T> extends ServiceSupport<T> implements LogicWarehouseService<T> {

	@Autowired
	private LogicWarehouseMapper<T> logicWarehouseMapper;
	
	public LogicWarehouseMapper<T> getLogicWarehouseMapper(){
		return logicWarehouseMapper;
	}
	
	@Override
	public List<LogicWarehouse> getLogicWarehouse(LogicWarehouse logicWarehouse) throws Exception {
		return logicWarehouseMapper.selectRecords(logicWarehouse);
	}
	
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return null;
	}
}
