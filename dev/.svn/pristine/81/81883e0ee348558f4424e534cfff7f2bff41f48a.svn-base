package com.bt.workOrder.service.impl;


import java.util.Map;

import com.bt.common.controller.param.Parameter;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.page.QueryResult;
import com.bt.workOrder.dao.FailureReasonMapper;
import com.bt.workOrder.service.FailureReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FailureReasonServiceImpl<T> extends ServiceSupport<T> implements FailureReasonService<T>{
    @Autowired
     private FailureReasonMapper<T> failureReasonMapper;
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return failureReasonMapper.selectCount(param);
	}

	@Override
	public QueryResult<Map<String, Object>> queryFailureReasonData(Parameter parameter) {
		// TODO Auto-generated method stub
		return new QueryResult<>(failureReasonMapper.queryFailureReasonData(parameter),failureReasonMapper.countFailureReasonData(parameter));
	}

	@Override
	public int deleteById(String id) {
		return failureReasonMapper.deleteById(id);
	}

	@Override
	public int addFailureReason(String reason) {
		return failureReasonMapper.addFailureReason("C"+System.currentTimeMillis(),reason);
	}


}
