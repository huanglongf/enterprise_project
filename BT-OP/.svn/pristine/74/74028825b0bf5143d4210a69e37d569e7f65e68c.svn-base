package com.bt.orderPlatform.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.orderPlatform.controller.form.WaybilMasterDetailQueryParam;
import com.bt.orderPlatform.model.BaoZunWaybilMasterDetail;
import com.bt.orderPlatform.model.WaybilMasterDetail;

/**
* @ClassName: WaybilMasterDetailMapper
* @Description: TODO(WaybilMasterDetailMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface WaybilMasterDetailMapper<T>  {

	void insert(WaybilMasterDetail waybilMasterDetail);

	/*List<WaybilMasterDetail> selectBySerial_number(@Param("serial_number")String serial_number);*/

	void updateByremark(WaybilMasterDetail waybilMasterDetail);

	List<Map<String, Object>> selectAllWaybilMasterDetail(WaybilMasterDetailQueryParam queryParam);

	int countAllWaybilMasterDetail(WaybilMasterDetailQueryParam queryParam);

	void deletetByBatId(@Param("bat_id")String id);

	List<WaybilMasterDetail> selectByBatId(String id);

	List<WaybilMasterDetail> selectWaybilMasterDetail(WaybilMasterDetail waybilMasterDetail2);

	void updateByBatId(@Param("bat_id")String id, @Param("flag")Integer flag);

	List<WaybilMasterDetail> selectByBatIdAndCustomer(@Param("bat_id")String id);

	List<WaybilMasterDetail> selectWaybilMasterDetailByCustomer(@Param("customer_number")String customer_number,@Param("bat_id")String id);

	List<WaybilMasterDetail> queryByCustomerNum(@Param("customer_number")String customer_number, @Param("bat_id")String id);

	List<WaybilMasterDetail> selectByBatIdAndStuats(@Param("bat_id")String uuid);

	List<WaybilMasterDetail> selectByIdAndBatIdAndCustomer(@Param("bat_id")String uuid);

	List<WaybilMasterDetail> selectByIdAndBatId(@Param("bat_id")String uuid);

	List<WaybilMasterDetail> selectByWaybilMasterDetail(WaybilMasterDetail waybilMasterDetail2);

	void insertBaoZun(BaoZunWaybilMasterDetail waybilMasterDetail);

	List<WaybilMasterDetail> baozunselectByBatId(String uuid);
	
	
}
