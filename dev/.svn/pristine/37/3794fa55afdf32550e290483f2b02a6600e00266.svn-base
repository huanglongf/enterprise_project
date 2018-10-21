package com.bt.orderPlatform.service;

import java.util.List;

import com.bt.orderPlatform.controller.form.TransportProductTypeQueryParam;
import com.bt.orderPlatform.model.TransportProductType;
import com.bt.orderPlatform.page.QueryResult;

public interface TransportProductTypeService<T>  {

	QueryResult<TransportProductType> findAll(TransportProductTypeQueryParam queryParam);

	QueryResult<TransportProductType> pageData(TransportProductTypeQueryParam queryParam);
	
	void insert(TransportProductType queryParam);

	void deleteBatch(List idslist);

	void updateTransportProduct(TransportProductTypeQueryParam queryParam);

	List findAllType(String carrier_type);

	List<TransportProductType> findByExpresscode(String express_code);

	TransportProductType selectByProducttype_code(String producttype_code, String expressCode);

	TransportProductType selectByProducttype_name(String express_code, String producttype_name,String carrier_type);

	List<TransportProductType> findByExpresscodeAndcarrier_type(String express_code, String carrier_type);


}
