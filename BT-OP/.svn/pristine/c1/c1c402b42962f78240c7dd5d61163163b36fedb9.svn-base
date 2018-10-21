package com.bt.orderPlatform.service;

import java.util.List;
import java.util.Map;

import com.bt.orderPlatform.controller.form.WaybilMasterDetailQueryParam;
import com.bt.orderPlatform.model.BaoZunWaybilMasterDetail;
import com.bt.orderPlatform.model.WaybilMasterDetail;
import com.bt.orderPlatform.page.QueryResult;

public interface WaybilMasterDetailService<T> {

	void insertWayBilMasterDetail(List<WaybilMasterDetail> wbd_list);

/*	List<WaybilMasterDetail> selectBySerial_number(String serial_number);*/

	void updateByremark(WaybilMasterDetail waybilMasterDetail);

	QueryResult<Map<String, Object>> findAllWaybilMasterDetail(WaybilMasterDetailQueryParam queryParam);

	void deletetByBatId(String id);

	List<WaybilMasterDetail> selectByBatId(String id);

	List<WaybilMasterDetail> selectWaybilMasterDetail(WaybilMasterDetail waybilMasterDetail2);

	void updateByBatId(String id, Integer flag);

	List<WaybilMasterDetail> selectByBatIdAndCustomer(String id);

	List<WaybilMasterDetail> selectWaybilMasterDetailByCustomer(String customer_number,String id);

	List<WaybilMasterDetail> queryByCustomerNum(String customer_number, String id);

	List<WaybilMasterDetail> selectByBatIdAndStuats(String uuid);

	List<WaybilMasterDetail> selectByIdAndBatIdAndCustomer(String uuid);

	List<WaybilMasterDetail> selectByIdAndBatId(String uuid);

	List<WaybilMasterDetail> selectByWaybilMasterDetail(WaybilMasterDetail waybilMasterDetail2);

	void insertBaoZunWayBilMasterDetail(List<BaoZunWaybilMasterDetail> wbd_list1);

	List<WaybilMasterDetail> baozunselectByBatId(String uuid);

}
