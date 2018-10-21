package com.bt.orderPlatform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.orderPlatform.dao.WaybilDetailMapper;
import com.bt.orderPlatform.model.WaybilDetail;
import com.bt.orderPlatform.service.WaybillDetailService;
@Service
public class WaybillDetailServiceImpl<T>  implements WaybillDetailService<T> {

	@Autowired
    private WaybilDetailMapper<T> mapper;
	

	public WaybilDetailMapper<T> getMapper() {
		return mapper;
	}


	@Override
	public List<WaybilDetail> findByObj(WaybilDetail waybilDetail) {
		// TODO Auto-generated method stub
		return mapper.findByObj(waybilDetail);
	}


	@Override
	public void updateByObj(WaybilDetail waybilDetail) {
		// TODO Auto-generated method stub
		mapper.updateByObj(waybilDetail);
	}


	@Override
	public void insert(WaybilDetail waybilDetail) {
		// TODO Auto-generated method stub
		mapper.insert(waybilDetail);
	}

	
}
