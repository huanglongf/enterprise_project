package com.bt.workOrder.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;
import com.bt.workOrder.controller.form.GenerationRuleQueryParam;

public interface GenerationRuleService<T> extends BaseService<T> {

	QueryResult<T> findAllData(QueryParameter qr);
	QueryResult<Map<String, Object>> findAllData2(QueryParameter qr);
	
	
	JSONObject addRule(GenerationRuleQueryParam queryParam,HttpServletRequest request)  throws Exception;


	JSONObject deleteRule(GenerationRuleQueryParam queryParam);


	boolean updateRule(GenerationRuleQueryParam queryParam, HttpServletRequest request);
}
