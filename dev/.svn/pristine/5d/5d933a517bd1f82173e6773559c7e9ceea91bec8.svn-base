package com.bt.orderPlatform.dao;

import java.util.List;

import com.bt.orderPlatform.model.InterfaceRouteinfo;

/**
* @ClassName: InterfaceRouteinfoMapper
* @Description: TODO(InterfaceRouteinfoMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface InterfaceRouteinfoMapper<T> {
	
	void insertByObj(InterfaceRouteinfo master);

	List<InterfaceRouteinfo> selectByWaybill(String waybill);

	Integer queryByWaybill(String waybill);

	void deleteByWaybill(InterfaceRouteinfo master);

	InterfaceRouteinfo queryByWayBillAndTime(String waybill);

	InterfaceRouteinfo queryByWayBillAndASCTime(String waybill);
}
