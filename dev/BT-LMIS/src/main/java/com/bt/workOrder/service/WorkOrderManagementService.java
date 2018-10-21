package com.bt.workOrder.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.page.QueryResult;
import com.bt.workOrder.bean.Enclosure;
import com.bt.workOrder.controller.param.WorkOrderParam;
import com.bt.workOrder.model.ClaimSchedule;
import com.bt.workOrder.model.Operation;
import com.bt.workOrder.model.OperationResult;
import com.bt.workOrder.model.WorkOrder;

/**
 * @Title:WorkOrderManagementService
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年1月11日下午1:44:54
 */
public interface WorkOrderManagementService<T> {
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年4月14日下午1:22:09
	 */
	JSONObject refreshWoem(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param waybill
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年4月14日下午1:16:48
	 */
	List<Map<String, Object>> getWaybillDetailByWaybill(String waybill);
	
	/**
	 * 
	 * @Description: TODO
	 * @param now
	 * @throws Exception
	 * @return: List<ClaimSchedule>  
	 * @author Ian.Huang 
	 * @date 2017年4月21日下午2:07:52
	 */
	List<ClaimSchedule> getClaimWorkOrderOnDeadline(String now) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年3月16日上午10:37:02
	 */
	JSONObject loadingEmployeeInGroupProcessChart(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年3月15日下午4:07:45
	 */
	JSONObject loadingGroupProcessChart(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年3月15日下午3:15:34
	 */
	JSONObject loadingWorkOrderProcessStatusChart(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年3月14日下午1:48:52
	 */
	JSONObject loadingUntreatedWorkOrderChart(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @throws Exception
	 * @return: HttpServletRequest  
	 * @author Ian.Huang 
	 * @date 2017年3月14日下午1:02:02
	 */
	HttpServletRequest toCharts(HttpServletRequest request) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年3月9日上午11:22:33
	 */
	JSONObject judgePower(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年3月9日上午10:33:00
	 */
	JSONObject updateWorkOrder(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年3月8日下午9:42:22
	 */
	JSONObject shiftGroups(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年2月3日下午4:27:55
	 */
	JSONObject monitoringStatus(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年1月25日下午3:19:27
	 */
	JSONObject saveOperation(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param op
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年1月25日下午1:19:48
	 */
	JSONObject toOperateForm(Operation op, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @throws Exception
	 * @return: HttpServletRequest  
	 * @author Ian.Huang 
	 * @date 2017年1月23日下午7:46:09
	 */
	HttpServletRequest toProcessForm(HttpServletRequest request) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param param
	 * @throws Exception
	 * @return: OperationResult  
	 * @author Ian.Huang 
	 * @date 2017年2月6日下午4:42:45
	 */
	OperationResult operate(HttpServletRequest request, Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年1月22日下午1:15:14
	 */
	JSONObject shiftStatus(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年1月19日下午4:26:45
	 */
	JSONObject alloc(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @throws Exception
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年1月19日下午3:45:17
	 */
	List<Map<String, Object>> getEmployeeInGroup(HttpServletRequest request) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年1月19日上午10:51:17
	 */
	JSONObject toAllocForm(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param wo
	 * @param request
	 * @param result
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年1月16日下午5:10:05
	 */
	JSONObject add(WorkOrder wo, HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年1月16日上午11:41:29
	 */
	JSONObject getData(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年1月12日下午9:32:48
	 */
	JSONObject toAddForm(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年2月28日下午9:07:05
	 */
	JSONObject getLevelAndException(HttpServletRequest request, JSONObject result) throws Exception;
	
	
	/**
	 * likun 20170815
	 */
	
	public ArrayList<T>get_wo_level(Map<String, Object> param);
	
	/**
	 * 
	    * <p>Description: </p>
	    * <p>Company: </p>
	    * @author:Administrator
	    * @date:2017年8月16日
	 */
	public ArrayList<T>get_error_type(Map<String,Object>param);
	
	public ArrayList<T>get_reason_info(Map<String,Object>param);
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @throws Exception
	 * @return: HttpServletRequest  
	 * @author Ian.Huang 
	 * @date 2017年1月12日上午11:47:21
	 */
	HttpServletRequest initialize(HttpServletRequest request) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param workOrderParam
	 * @return: QueryResult<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年1月5日下午8:15:49
	 */
	QueryResult<Map<String,Object>> query(WorkOrderParam workOrderParam);
	
	/**
	 * @Title: ensureWorkOrderDetail
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject
	 * @author: Ian.Huang
	 * @date: 2017年6月7日 上午10:57:31
	 */
	JSONObject ensureWorkOrderDetail(HttpServletRequest request, JSONObject result) throws Exception;

	/**
	 * @param map
	 * @return
	 */
	QueryResult<Map<String, Object>> query2(Map<String, Object> map);

	/**
	 * @param parameter
	 * @return
	 */
	List<Enclosure> selectAllByWoNo(String parameter);

    /**
     * @param workOrderParam
     * @return
     */
    List<Map<String, Object>> exportWO(WorkOrderParam workOrderParam);

    /**
     * @return
     */
    List<Map<String, Object>> getWOType();


}