package com.bt.orderPlatform.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONArray;
import com.bt.orderPlatform.controller.form.SFExpressPrint;
import com.bt.orderPlatform.controller.form.WaybillMasterQueryParam;
import com.bt.orderPlatform.model.BaoZunWaybilMasterDetail;
import com.bt.orderPlatform.model.ExpressinfoMasterInputlist;
import com.bt.orderPlatform.model.WaybilMasterDetail;
import com.bt.orderPlatform.model.WaybillMaster;
import com.bt.orderPlatform.model.WaybillMasterDetail;
import com.bt.orderPlatform.page.QueryResult;

public interface WaybillMasterService<T>  {

	void CancelOrder(WaybillMaster master);

	int insertByObj(WaybillMaster master);

	void updateByObj(WaybillMaster master);

	QueryResult<Map<String, Object>> findAllWaybillMaster(WaybillMasterQueryParam queryParam);


	Integer findMaxOrderId();

	void insertOrder(WaybillMaster queryParam, JSONArray arr, Date date);

	List<WaybillMaster> queryOrder(WaybillMasterQueryParam queryParam);

	WaybillMaster selectById(String id);

	void updatestatus(List<String> list, WaybillMaster waybillmaster);

	/*void insertAllOrder(List<WaybillMasterDetail> wbd_list);*/

	void updateOrder(WaybillMaster queryParam, JSONArray arr, Date date);

	void updateByMaster(WaybillMaster master);

	List<WaybillMaster> queryByStatus(String status);

	void setstatus(String orderId,String status);

	List<Map<String, Object>> selectByQuery(WaybillMasterQueryParam queryParam);

	WaybillMasterDetail queryByCustomerNum(String customer_number);

	void setPrint_code(WaybillMaster waybillMaster);

	void insertWayBilMasterDetail(List<WaybilMasterDetail> wbd_list);

	void insetByWaybilMasterDetail(WaybilMasterDetail waybilMasterDetail2);

	void insetWaybilMasterDetailByCustomer(WaybilMasterDetail waybilMasterDetail);

	void confirmOrdersById(String id, String stauts);

	void updateByremark(WaybilMasterDetail waybilMasterDetail);

	void insertWaybilMasterDetailByCustomer(String uuid);

	void insertWaybilMasterDetailByCustomerList(String uuid);

	void delete(String id);

	List<WaybillMaster> selectByQuery_test(String query_test,String org_cde,String status);

	List<WaybillMaster> selectByWaybillMaster(WaybillMaster queryParam);

	List<Map<String, Object>> selectByQueryCSV(WaybillMasterQueryParam queryParam);

	List<WaybillMaster> queryByStatusAndStartDate(String status);

	void setStartDate(String waybill, Date route_time);

	void updateOrdera(WaybillMaster queryParam);

	SFExpressPrint selectSFPrint(String id);

	List<WaybillMaster> selectByBatIdAndStatus(String bat_id);

	List<WaybillMaster> selectIdByBatIdAndStatus(String bat_id);

	void insertBaoZunWayBilMasterDetail(List<BaoZunWaybilMasterDetail> wbd_list1);

	void updateplaceError(String order_id, String status, String place_error);

	String get_business_code();

	List<WaybillMaster> queryByStatusSF();

	int updExpTime(Map<String, Object> map);
	
	int updateSendTypeById(Integer sendType,String id);

	List<WaybillMaster> queryBySendTypeAndCount(Integer sendType,Integer count);

	List<WaybillMaster> query2MonthAgoMaster();
	
	int updateRunTime(String waybill);
}
