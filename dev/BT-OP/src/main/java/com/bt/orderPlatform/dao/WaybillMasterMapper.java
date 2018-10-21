package com.bt.orderPlatform.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.orderPlatform.controller.form.SFExpressPrint;
import com.bt.orderPlatform.controller.form.WaybillMasterQueryParam;
import com.bt.orderPlatform.model.WaybilMasterDetail;
import com.bt.orderPlatform.model.WaybillMaster;
import com.bt.orderPlatform.model.WaybillMasterDetail;

/**
* @ClassName: WaybillMasterMapper
* @Description: TODO(WaybillMasterMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface WaybillMasterMapper<T> {

	void updateByObj(WaybillMaster master);

	void CancelOrder(WaybillMaster master);
	
	int insertByObj(WaybillMaster master);

	List<Map<String, Object>> selectAllWaybillMaster(WaybillMasterQueryParam queryParam);

	int countAllWaybillMaster(WaybillMasterQueryParam queryParam);

	Integer findMaxOrderId();

	void insertOrder(WaybillMaster queryParam);

	List<WaybillMaster> queryOrder(WaybillMasterQueryParam queryParam);

	WaybillMaster selectById(String id);

	void updatestatus(WaybillMaster waybillmaster);

	WaybillMaster queryBycustomer_number(String customer_number);

	void insertwmd(WaybilMasterDetail waybillMasterDetail);

	void updatewmd(WaybillMaster waybillMaster);

	void updateOrder(WaybillMaster queryParam);

	void updateByMaster(WaybillMaster master);

	List<WaybillMaster> queryByStatus(@Param("status")String status);

	void setstatus(@Param("orderId")String orderId, @Param("status")String status);

	List<Map<String, Object>> selectByQuery(WaybillMasterQueryParam queryParam);

	WaybillMasterDetail queryByCustomerNum(String customer_number);

	void setPrint_code(WaybillMaster waybillMaster);

	void insertWaybilMaster(WaybilMasterDetail waybilMasterDetail);

	void confirmOrdersById(@Param("id")String id, @Param("status")String status);

	void deleteById(@Param("id")String id);

	List<WaybillMaster> selectByQuery_test(@Param("query_test")String query_test,@Param("org_cde")String org_cde,@Param("status")String status);

	List<WaybillMaster> selectByWaybillMaster(WaybillMaster queryParam);

	List<Map<String, Object>> selectByQueryCSV(WaybillMasterQueryParam queryParam);

	List<WaybillMaster> queryByStatusAndStartDate(@Param("status")String status);

	void setStartDate(@Param("waybill")String waybill,@Param("start_date") Date start_date);

	SFExpressPrint selectSFPrint(String id);

	List<WaybillMaster> selectByBatIdAndStatus(@Param("bat_id")String bat_id);

	List<WaybillMaster> selectIdByBatIdAndStatus(@Param("bat_id")String bat_id);

	void updateplaceError(@Param("order_id")String order_id, @Param("status")String status,@Param("place_error")String placeError);

	void get_business_code(Map<String, String> param);

	List<WaybillMaster> queryByStatusSF();

	List<WaybillMaster> selecttest(WaybillMaster param);

	int updExpTime(Map<String, Object> map);
	
	int updateSendTypeById(@Param("sendType")Integer sendType,@Param("id")String id);

	List<WaybillMaster> queryBySendTypeAndCount(@Param("sendType")Integer sendType,@Param("count")Integer count);

	List<WaybillMaster> query2MonthAgoMaster();
	
	int updateRunTime(String waybill);
	
}
