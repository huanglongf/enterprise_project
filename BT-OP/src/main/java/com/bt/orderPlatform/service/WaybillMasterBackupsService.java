package com.bt.orderPlatform.service;

import com.bt.orderPlatform.model.WaybilMasterDetail;
import com.bt.orderPlatform.model.WaybillMaster;

public interface WaybillMasterBackupsService<T> {

	void insert(WaybillMaster waybillMaster);

	void insetWaybilMasterDetailByCustomer(WaybilMasterDetail waybilMasterDetail1);

}
