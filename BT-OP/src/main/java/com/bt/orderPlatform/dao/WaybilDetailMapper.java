package com.bt.orderPlatform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.orderPlatform.model.WaybilDetail;
import com.bt.orderPlatform.model.WaybilMasterDetail;
import com.bt.orderPlatform.model.WaybillMasterDetail;

/**
* @ClassName: WaybilDetailMapper
* @Description: TODO(WaybilDetailMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface WaybilDetailMapper<T> {
	void updateByObj(WaybilDetail waybilDetail);
	
	List<WaybilDetail> findByObj(WaybilDetail waybilDetail);

	void insert(WaybilDetail waybilDetail);

	List<WaybilDetail> queryOrderByOrderId(String order_id);

	void updatestatus(WaybilDetail quaram);

	void insertwmd(WaybilMasterDetail waybilMasterDetail);

	void delete(String id);

	void deletByOrder_Id(@Param("order_id")String order_id);

	
}
