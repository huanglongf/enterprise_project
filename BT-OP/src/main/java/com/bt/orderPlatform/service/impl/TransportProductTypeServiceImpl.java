package com.bt.orderPlatform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.orderPlatform.controller.form.TransportProductTypeQueryParam;
import com.bt.orderPlatform.dao.TransportProductTypeMapper;
import com.bt.orderPlatform.model.TransportProductType;
import com.bt.orderPlatform.page.QueryResult;
import com.bt.orderPlatform.service.TransportProductTypeService;
@Service
public class TransportProductTypeServiceImpl<T> implements TransportProductTypeService<T> {

	@Autowired
    private TransportProductTypeMapper<T> mapper;

	public TransportProductTypeMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public QueryResult<TransportProductType> findAll(TransportProductTypeQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<TransportProductType> qr=new QueryResult<TransportProductType>();
		qr.setResultlist(mapper.pageData(queryParam));
		qr.setTotalrecord(mapper.Count(queryParam));
		return qr;
	}
	
	
	@Override
	public List findAllType(String carrier_type) {
		return mapper.findAllType(carrier_type);
	}

	@Override
	public void insert(TransportProductType queryParam) {
		// TODO Auto-generated method stub
		mapper.insert(queryParam);
	}

	@Override
	public void deleteBatch(List idslist) {
		// TODO Auto-generated method stub
		mapper.deleteBatch(idslist);
	}

	@Override
	public void updateTransportProduct(TransportProductTypeQueryParam queryParam) {
		// TODO Auto-generated method stub
		mapper.updateTransportProduct(queryParam);
	}

	@Override
	public QueryResult<TransportProductType> pageData(TransportProductTypeQueryParam queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransportProductType> findByExpresscode(String express_code) {
		// TODO Auto-generated method stub
		return mapper.findByExpresscode(express_code);
	}

	@Override
	public TransportProductType selectByProducttype_code(String producttype_code,String expressCode) {
		// TODO Auto-generated method stub
		return mapper.selectByProducttype_code(producttype_code, expressCode);
	}

	@Override
	public TransportProductType selectByProducttype_name(String express_code, String producttype_name,String carrier_type) {
		// TODO Auto-generated method stub
		return mapper.selectByProducttype_name(express_code,producttype_name,carrier_type);
	}

	@Override
	public List<TransportProductType> findByExpresscodeAndcarrier_type(String express_code, String carrier_type) {
		// TODO Auto-generated method stub
		return mapper.findByExpresscodeAndcarrier_type(express_code,carrier_type);
	}


		
	
}
