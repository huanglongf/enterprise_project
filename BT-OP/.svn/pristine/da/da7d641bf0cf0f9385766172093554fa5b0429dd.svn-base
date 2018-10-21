package com.bt.orderPlatform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.orderPlatform.controller.form.TransportVenderQueryParam;
import com.bt.orderPlatform.model.TransportVender;

/**
* @ClassName: TransportVenderMapper
* @Description: TODO(TransportVenderMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface TransportVenderMapper<T> {

	List<TransportVender> getVender(TransportVenderQueryParam queryParam);

	void updateTransportVender(TransportVenderQueryParam queryParam);

	void batchDelete(List idslist);

	void insert(TransportVender queryParam);

	List<TransportVender> pageDataInfo(TransportVenderQueryParam queryParam);

	List<TransportVender> getAllVender(@Param("carrier")String carrier);

	TransportVender selectByProducttype_code(String express_code);

	TransportVender selectByProducttype_name(@Param("express_name")String producttype_name,@Param("express_code")String carrier);
	
	
}
