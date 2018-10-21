package com.bt.workOrder.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.page.QueryResult;
import com.bt.workOrder.controller.param.OMSInterfaceExcpeitonHandlingParam;
import com.bt.workOrder.model.WorkOrder;

/**
 * @Title:WorkOrderManagementService
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年1月11日下午1:44:54
 */
public interface OMSInterfaceExceptionHandlingService {
	
	/**
	 * 
	 * @Description: TODO
	 * @param param
	 * @return: QueryResult<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年4月11日上午11:42:36
	 */
	QueryResult<Map<String, Object>> query(OMSInterfaceExcpeitonHandlingParam param);

	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年4月12日上午10:24:28
	 */
	JSONObject getData(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param wo
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年4月12日下午1:57:17
	 */
	JSONObject createOMSWorkOrder(WorkOrder wo, HttpServletRequest request, JSONObject result) throws Exception;

	/**
	 * @param id
	 * @return
	 */
	int updateById(String id);

	/**
	 * @param parameter
	 * @return
	 */
	int queryById(String id);
	
}