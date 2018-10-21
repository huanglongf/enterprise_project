package com.bt.orderPlatform.service;

import java.util.List;

import com.bt.orderPlatform.controller.form.TransportVenderQueryParam;
import com.bt.orderPlatform.model.TransportVender;

public interface TransportVenderService<T> {

	List<TransportVender> getVender(TransportVenderQueryParam queryParam);

	void updateTransportVender(TransportVenderQueryParam queryParam);

	void deleteBatch(List idslist);

	void insert(TransportVender queryParam);

	List<TransportVender> pageData(TransportVenderQueryParam queryParam);

	List<TransportVender> getAllVender(String carrier);

	TransportVender selectByProducttype_code(String producttype_code);

	TransportVender selectByProducttype_name(String producttype_name,String carrier);


}
