package com.bt.orderPlatform.service;


import java.util.List;

import com.bt.orderPlatform.model.WaybilDetail;
import com.bt.orderPlatform.model.WaybilMasterDetail;
import com.bt.orderPlatform.model.WaybillMasterDetail;

public interface WaybilDetailService<T> {

	void insert(WaybilDetail waybillDetail);


	List<WaybilDetail> queryOrderByOrderId(String order_id);


	void updatestatus(WaybilDetail quaram);


	void insertwmd(WaybilMasterDetail waybilMasterDetail);


	void deletById(String id);


	void deletByOrder_Id(String order_id);


}
