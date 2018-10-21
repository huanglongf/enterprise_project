package com.bt.orderPlatform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.orderPlatform.controller.form.TransportProductTypeQueryParam;
import com.bt.orderPlatform.model.TransportProductType;

/**
* @ClassName: TransportProductTypeMapper
* @Description: TODO(TransportProductTypeMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface TransportProductTypeMapper<T> {

	List<TransportProductType> findAll(TransportProductTypeQueryParam queryParam);

	int Count(TransportProductTypeQueryParam queryParam);

	void insert(TransportProductType queryParam);

	void deleteBatch(List idslist);

	void updateTransportProduct(TransportProductTypeQueryParam queryParam);

	List<TransportProductType> pageData(TransportProductTypeQueryParam queryParam);

	List findAllType(String carrier_type);

	List<TransportProductType> findByExpresscode(String express_code);

	TransportProductType selectByProducttype_code(@Param("producttype_code")String producttype_code,@Param("express_code") String expressCode);

	TransportProductType selectByProducttype_name(@Param("express_code")String express_code, @Param("producttype_name")String producttype_name,@Param("producttype_code")String carrier_type);

	List<TransportProductType> findByExpresscodeAndcarrier_type(@Param("express_code")String express_code, @Param("producttype_code")String carrier_type);
	
	
}
