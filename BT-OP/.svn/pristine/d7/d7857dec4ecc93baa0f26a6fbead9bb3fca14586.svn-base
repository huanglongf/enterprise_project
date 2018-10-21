package com.bt.orderPlatform.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.orderPlatform.dao.WaybilDetailBackupsMapper;
import com.bt.orderPlatform.model.WaybilDetail;
import com.bt.orderPlatform.model.WaybilMasterDetail;
import com.bt.orderPlatform.service.WaybilDetailBackupsService;
@Service
public class WaybilDetailBackupsServiceImpl<T>  implements WaybilDetailBackupsService<T> {

	@Autowired
    private WaybilDetailBackupsMapper<T> mapper;

	public WaybilDetailBackupsMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public void insert(List<WaybilDetail> queryOrderByOrderId) {
		// TODO Auto-generated method stub
		mapper.insertList(queryOrderByOrderId);
	}

	@Override
	public void insertwmd(WaybilMasterDetail waybilMasterDetail) {
		// TODO Auto-generated method stub
		mapper.insertwmd(waybilMasterDetail);
	}

	
}
