package com.bt.workOrder.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;
import com.bt.common.controller.param.Parameter;
import com.bt.common.controller.result.Result;
import com.bt.lmis.page.QueryResult;
import com.bt.workOrder.model.WorkOrderStore;

/** 
 * @ClassName: WorkOrderPlatformStoreService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年8月23日 下午6:57:44 
 * 
 * @param <T>
 */
public interface WorkOrderPlatformStoreService<T> {


	/** 
	* @Title: getEnclosureByWKID 
	* @Description: TODO(根据工单ID查询回复及附件) 
	* @param @param id
	* @param @return    设定文件 
	* @return Map<String,String>    返回类型 
	* @throws 
	*/
	List<Map<String, String>> getEnclosureByWKID(@Param("id")String id);
	
	/**
	 * @Title: search
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: QueryResult<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月23日 下午8:10:12
	 */
	QueryResult<Map<String, Object>> search(Parameter parameter);
	
	/**
	 * @Title: listWoType
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月23日 下午8:10:50
	 */
	List<Map<String, Object>> listWoType();
	
	/**
	 * @Title: loadingHome1
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: JSONObject
	 * @author: Ian.Huang
	 * @date: 2017年8月24日 下午4:13:19
	 */
	JSONObject loadingHome1(Parameter parameter);
	
	/**
	 * @Title: loadingHome2
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: JSONObject
	 * @author: Ian.Huang
	 * @date: 2017年8月24日 下午4:25:37
	 */
	JSONObject loadingHome2(Parameter parameter);
	
	/**
	 * @Title: getWorkOrderStoreById
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param id
	 * @return: WorkOrderStore
	 * @author: Ian.Huang
	 * @date: 2017年8月24日 下午8:22:29
	 */
	WorkOrderStore getWorkOrderStoreById(String id);
	
	/**
	 * @Title: listOrderByOrderNo
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param orderNo
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月24日 下午9:50:52
	 */
	List<Map<String, Object>> listOrderByOrderNo(String orderNo);
	
	/**
	 * @Title: getOrderById
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param id
	 * @return: Map<String,Object>
	 * @author: Ian.Huang
	 * @date: 2017年8月24日 下午10:40:55
	 */
	Map<String, Object> getOrderById(String id);
	
	/**
	 * @Title: listOrderDetailByWaybill
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param waybill
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月24日 下午10:49:55
	 */
	List<Map<String, Object>> listOrderDetailByWaybill(String waybill);
	
	/**
	 * @Title: listWoOrderByWoId
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param woId
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月29日 下午2:23:05
	 */
	List<Map<String, Object>> listWoOrderByWoId(String woId);
	
	/**
	 * @Title: getWoOrderById
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param id
	 * @return: Map<String,Object>
	 * @author: Ian.Huang
	 * @date: 2017年8月29日 下午2:41:06
	 */
	Map<String, Object> getWoOrderById(String id);
	
	/**
	 * @Title: listOrderDetailByWoOrderId
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param id
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月29日 下午2:41:22
	 */
	List<Map<String, Object>> listOrderDetailByWoOrderId(String id);
	
	/**
	 * @Title: worderOrderAction
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: Result
	 * @author: Ian.Huang
	 * @date: 2017年8月25日 上午11:44:38
	 */
	Result worderOrderAction(Parameter parameter);
	
	/**
	 * @Title: Binders
	 * @Description: TODO(工单绑定人员)
	 * @param parameter
	 * @return: Result
	 * @author: lingling.zhang
	 * @date: 2017年9月12日16:50:19
	 */
	Result empGetWorkOrder(Parameter parameter);
	
	/**
	 * @Title: judgeProcessOrNot
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: boolean
	 * @author: Ian.Huang
	 * @date: 2017年8月25日 下午2:47:33
	 */
	boolean judgeProcessOrNot(Parameter parameter);

	/**
	 * @Title: listErrorTypeByWoType
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月27日 上午11:03:56
	 */
	List<Map<String, Object>> listErrorTypeByWoType(Parameter parameter);
	
	/**
	 * @Title: delTempWorkOrder
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param woId
	 * @return: Result
	 * @author: Ian.Huang
	 * @date: 2017年8月27日 下午12:29:29
	 */
	Result delTempWorkOrder(String woId);
	
	/**
	 * @Title: exportWorkOrder
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月27日 下午8:59:19
	 */
	List<Map<String, Object>> exportWorkOrder(Parameter parameter);
	
	/**
	 * @Title: getStoreById
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param id
	 * @return: Map<String,Object>
	 * @author: Ian.Huang
	 * @date: 2017年8月30日 上午10:53:03
	 */
	Map<String, Object> getStoreById(String id);
	
	/**
	 * @Title: judgeSOReplyOrNot
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param woId
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年9月12日 下午9:37:18
	 */
	int judgeSOReplyOrNot(String woId);

	Result autoAllocation(String woId);

	/**
	 * @param parameter 
	 * @return
	 */
	Map<String, Integer> countAllTab(Parameter parameter);

	/**
	 * @param integer
	 * @return
	 */
	List<Map<String, Object>> listWoTypeByDepartment(int integer);

	void updateWoStoreMaster(WorkOrderStore workOrderStore2);

	Result autoWorkOrderEmp(String woId);

	void aKeyExport(String woId, HttpServletResponse response);

	void downloadZip(String woId, HttpServletResponse response);

	List<String> findStroeCodeByGroupID(String teamId);

	List<Map<String, Object>> findGroupListByTeamid(String teamId);

	Map<String, Object> queryCount(Parameter parameter);

	List<Map<String, Object>> getWorkOrderByWoNo(String woNo);

	JSONObject addStoreProcessLog(Parameter parameter);

	List<Map<String, Object>> getIdByWoNos(String string);

	List<Map<String, Object>> getWOByWoNoNoId(String woNo, String woId);

	List<Map<String,Object>> getSplitList(String woNo);

	String getIdByWoNo(String string);
	
	Map<String,Object> getWOByWoNo(String string);

	
}
