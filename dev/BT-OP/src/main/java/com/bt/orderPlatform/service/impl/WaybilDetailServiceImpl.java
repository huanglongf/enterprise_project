package com.bt.orderPlatform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.bt.orderPlatform.dao.WaybilDetailMapper;
import com.bt.orderPlatform.model.WaybilDetail;
import com.bt.orderPlatform.model.WaybilMasterDetail;
import com.bt.orderPlatform.model.WaybillMasterDetail;
import com.bt.orderPlatform.service.WaybilDetailService;
@Service
public class WaybilDetailServiceImpl<T>  implements WaybilDetailService<T> {

	@Autowired
    private WaybilDetailMapper<T> mapper;

	public WaybilDetailMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public void insert(WaybilDetail waybillDetail) {
		
		mapper.insert(waybillDetail);
	}

	@Override
	public List<WaybilDetail> queryOrderByOrderId(String order_id) {
		// TODO Auto-generated method stub
		return mapper.queryOrderByOrderId(order_id);
	}

	@Override
	public void updatestatus(WaybilDetail quaram) {
		// TODO Auto-generated method stub
		mapper.updatestatus(quaram);
	}

	@Override
	public void insertwmd(WaybilMasterDetail waybillMasterDetail) {
		// TODO Auto-generated method stub
		mapper.insertwmd(waybillMasterDetail);
	}

	@Override
	public void deletById(String id) {
		// TODO Auto-generated method stub
		mapper.delete(id);
	}

	@Override
	public void deletByOrder_Id(String order_id) {
		// TODO Auto-generated method stub
		mapper.deletByOrder_Id(order_id);
	}

		
	
}
