package com.bt.lmis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.ContractBasicinfoQueryParam;
import com.bt.lmis.controller.form.HandlingFeeRawDataQueryParam;
import com.bt.lmis.dao.ContractBasicinfoMapper;
import com.bt.lmis.dao.HandlingFeeRawDataMapper;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.model.HandlingFeeRawData;
import com.bt.lmis.model.SearchBean;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ContractBasicinfoService;
import com.bt.lmis.service.HandlingFeeRawDataService;

/** 
* @ClassName: ContractBasicinfoServiceImpl 
* @Description: TODO(合同ServiceImpl) 
* @author Yuriy.Jiang
* @date 2016年6月20日 下午3:56:26 
*  
*/
@Service
public class HandlingFeeRawDataServiceImpl<T> extends ServiceSupport<T> implements HandlingFeeRawDataService<T> {

	@Autowired
	private HandlingFeeRawDataMapper<HandlingFeeRawData> mapper;
	
	@Override
	public QueryResult<HandlingFeeRawData> findAll(HandlingFeeRawDataQueryParam queryParam) {
		QueryResult<HandlingFeeRawData> qr = new QueryResult<HandlingFeeRawData>();
		qr.setResultlist(mapper.findCB(queryParam));
		qr.setTotalrecord(mapper.countCBRecords(queryParam));
		return qr;
	}


	@Override
	public void batchDelete(Integer[] ids) throws Exception {
		mapper.batchDelete(ids);
	}


	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
}
