package com.bt.workOrder.service;

import java.util.Map;

import com.bt.common.controller.param.Parameter;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.page.QueryResult;


public interface FailureReasonService<T> extends BaseService<T>{
	 /**
     * 根据条件分页查询
     * @return
     */
    QueryResult<Map<String, Object>> queryFailureReasonData(Parameter parameter);

    int deleteById(String id);

    int addFailureReason( String reason);
}
