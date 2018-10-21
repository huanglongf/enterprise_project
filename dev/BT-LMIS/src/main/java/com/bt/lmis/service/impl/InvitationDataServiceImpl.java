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
import com.bt.lmis.controller.form.InvitationQueryParam;
import com.bt.lmis.dao.ContractBasicinfoMapper;
import com.bt.lmis.dao.InvitationMapper;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.model.InvitationBean;
import com.bt.lmis.model.SearchBean;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ContractBasicinfoService;
import com.bt.lmis.service.HandlingFeeRawDataService;
import com.bt.lmis.service.InvitationDataService;
import com.bt.lmis.service.InvitationUseanmountDataService;

/** 
* @ClassName: ContractBasicinfoServiceImpl 
* @Description: TODO(合同ServiceImpl) 
* @author Yuriy.Jiang
* @date 2016年6月20日 下午3:56:26 
*  
*/
@Service
public class InvitationDataServiceImpl<T> extends ServiceSupport<T> implements InvitationDataService<T> {

	@Autowired
	private InvitationMapper<InvitationBean> mapper;
	
	@Override
	public QueryResult<InvitationBean> findAll(InvitationQueryParam queryParam) {
		QueryResult<InvitationBean> qr = new QueryResult<InvitationBean>();
		qr.setResultlist(mapper.findCB(queryParam));
		qr.setTotalrecord(mapper.countCBRecords(queryParam));
		return qr;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	
}
