package com.bt.orderPlatform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.bt.orderPlatform.dao.InterfaceRouteinfoMapper;
import com.bt.orderPlatform.model.InterfaceRouteinfo;
import com.bt.orderPlatform.service.InterfaceRouteinfoService;
@Service
public class InterfaceRouteinfoServiceImpl<T>  implements InterfaceRouteinfoService<T> {

	@Autowired
    private InterfaceRouteinfoMapper<T> mapper;

	public InterfaceRouteinfoMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public void insertByObj(InterfaceRouteinfo master) {
		// TODO Auto-generated method stub
		mapper.insertByObj(master);
	}

	@Override
	public List<InterfaceRouteinfo> selectByWaybill(String waybill) {
		// TODO Auto-generated method stub
		return mapper.selectByWaybill(waybill);
	}

	@Override
	public Integer queryByWaybill(String waybill) {
		// TODO Auto-generated method stub
		return mapper.queryByWaybill(waybill);
	}

	@Override
	public void deleteByWaybill(InterfaceRouteinfo master) {
		// TODO Auto-generated method stub
		mapper.deleteByWaybill(master);
	}

	@Override
	public InterfaceRouteinfo queryByWayBillAndTime(String waybill) {
		// TODO Auto-generated method stub
		return mapper.queryByWayBillAndTime(waybill);
	}

	@Override
	public InterfaceRouteinfo queryByWayBillAndASCTime(String waybill) {
		// TODO Auto-generated method stub
		return mapper.queryByWayBillAndASCTime(waybill);
	}

	
}
