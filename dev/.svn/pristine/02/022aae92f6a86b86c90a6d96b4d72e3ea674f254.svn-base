package com.bt.radar.service;

import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.page.QueryResult;

import com.bt.radar.controller.form.RoutecodeQueryParam;
import com.bt.radar.model.Routecode;
import com.bt.radar.model.RoutecodeBean;

public interface RoutecodeService<T> extends BaseService<T> {

	QueryResult<RoutecodeBean> findAllData(RoutecodeQueryParam queryParam);

	List<T> selectTransport_vender();
	
	List<T> getTransport_vender();
	
	List<T> findAllRecord(RoutecodeQueryParam queryParam);
	
	List<T>  selectRecordsByWID(Map param);
	
	List<T>  getStatus(Map param);
}
