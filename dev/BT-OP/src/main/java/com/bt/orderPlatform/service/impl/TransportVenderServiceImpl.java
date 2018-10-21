package com.bt.orderPlatform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.orderPlatform.controller.form.TransportVenderQueryParam;
import com.bt.orderPlatform.dao.TransportVenderMapper;
import com.bt.orderPlatform.model.TransportVender;
import com.bt.orderPlatform.service.TransportVenderService;
@Service
public class TransportVenderServiceImpl<T>implements TransportVenderService<T> {

	@Autowired
    private TransportVenderMapper<T> mapper;

	public TransportVenderMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public List<TransportVender> getVender(TransportVenderQueryParam queryParam) {
		// TODO Auto-generated method stub
		return mapper.getVender(queryParam);
	}

	@Override
	public void updateTransportVender(TransportVenderQueryParam queryParam) {
		// TODO Auto-generated method stub
		 mapper.updateTransportVender(queryParam);
	}

	@Override
	public void deleteBatch(List idslist) {
		// TODO Auto-generated method stub
		mapper.batchDelete(idslist);
	}

	@Override
	public void insert(TransportVender queryParam) {
		// TODO Auto-generated method stub
		mapper.insert(queryParam);
	}

	@Override
	public List<TransportVender> pageData(TransportVenderQueryParam queryParam) {
		// TODO Auto-generated method stub
		return mapper.pageDataInfo(queryParam);
	}
	
	@Override
	public List<TransportVender> getAllVender(String carrier) {
		// TODO Auto-generated method stub
		return mapper.getAllVender(carrier);
	}

	@Override
	public TransportVender selectByProducttype_code(String express_code) {
		// TODO Auto-generated method stub
		return mapper.selectByProducttype_code(express_code);
	}

	@Override
	public TransportVender selectByProducttype_name(String producttype_name,String carrier) {
		// TODO Auto-generated method stub
		return mapper.selectByProducttype_name(producttype_name,carrier);
	}

}
