package com.bt.workOrder.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.common.controller.param.Parameter;
import com.bt.workOrder.model.Operation;
import com.bt.workOrder.model.WoProcessLog;
import com.bt.workOrder.model.WoProcessRecord;
import com.bt.workOrder.model.WorkOrder;
import com.bt.workOrder.model.WorkOrderEventMonitor;
import com.bt.workOrder.model.WorkOrderStore;

/** 
 * @ClassName: WorkOrderPlatformStoreMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年8月23日 下午2:48:37 
 * 
 * @param <T>
 */
public interface WorkOrderPlatformStoreMapper<T> {
	/**
	 * @Title: search
	 * @param parameter
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月23日 下午6:55:25
	 */
	List<Map<String, Object>> search(Parameter parameter);

	/**
	 * @Title: countSearch
	 * @param parameter
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月23日 下午6:56:09
	 */
	int countSearch(Parameter parameter);
	
	/**
	 * @Title: listWoType
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月23日 下午8:28:24
	 */
	List<Map<String, Object>> listWoType();
	
	/**
	 * @Title: countCurrentCreate
	 * @param parameter
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月24日 下午3:40:00
	 */
	int countCurrentCreate(Parameter parameter);
	/**
	 * @Title: countcountCurrentProcessed
	 * @param parameter
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月24日 下午3:40:06
	 */
	int countcountCurrentProcessed(Parameter parameter);
	/**
	 * @Title: countCurrentOver
	 * @param parameter
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月24日 下午3:40:10
	 */
	int countCurrentOver(Parameter parameter);
	/**
	 * @Title: countCurrentUnprocessed
	 * @param parameter
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月24日 下午3:40:13
	 */
	int countCurrentUnprocessed(Parameter parameter);
	/**
	 * @Title: listCurrentCreate
	 * @param parameter
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月24日 下午4:31:32
	 */
	List<Map<String, Object>> listCurrentCreate(Parameter parameter);
	/**
	 * @Title: listCurrentOver
	 * @param parameter
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月24日 下午4:31:41
	 */
	List<Map<String, Object>> listCurrentOver(Parameter parameter);
	
	/**
	 * @Title: getWorkOrderStoreById
	 * @param id
	 * @return: WorkOrderStore
	 * @author: Ian.Huang
	 * @date: 2017年8月24日 下午8:21:57
	 */
	WorkOrderStore getWorkOrderStoreById(@Param("id")String id);
	
	/**
	 * @Title: listOrderByOrderNo
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param orderNo
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月29日 下午2:02:08
	 */
	List<Map<String, Object>> listOrderByOrderNo(@Param("orderNo")String orderNo);
	
	/**
	 * @Title: getOrderById
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param id
	 * @return: Map<String,Object>
	 * @author: Ian.Huang
	 * @date: 2017年8月24日 下午10:40:22
	 */
	Map<String, Object> getOrderById(@Param("id")String id);
	
	/**
	 * @Title: listOrderDetailByWaybill
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param waybill
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月24日 下午10:49:27
	 */
	List<Map<String, Object>> listOrderDetailByWaybill(@Param("waybill")String waybill);
	
	/**
	 * @Title: listWoOrderByWoId
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param woId
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月29日 下午2:34:43
	 */
	List<Map<String, Object>> listWoOrderByWoId(@Param("woId")String woId);
	
	/**
	 * @Title: getWoOrderById
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param id
	 * @return: Map<String,Object>
	 * @author: Ian.Huang
	 * @date: 2017年8月29日 下午2:39:13
	 */
	Map<String, Object> getWoOrderById(@Param("id")String id);
	
	/**
	 * @Title: listOrderDetailByWoOrderId
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param woOrederId
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月29日 下午2:36:45
	 */
	List<Map<String, Object>> listOrderDetailByWoOrderId(@Param("woOrederId")String woOrederId);
	
	/**
	 * @Title: getStoreById
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param id
	 * @return: Map<String,Object>
	 * @author: Ian.Huang
	 * @date: 2017年8月25日 下午2:55:15
	 */
	Map<String, Object> getStoreById(@Param("id")String id);
	
	/**
	 * @Title: getWoTypeName
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param code
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年8月25日 下午4:58:33
	 */
	String getWoTypeName(@Param("code")String code);
	
	/**
	 * @Title: getErrorTypeName
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param id
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年8月25日 下午5:02:41
	 */
	String getErrorTypeName(@Param("id")String id);
	
	/**
	 * @Title: listOrderById
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param id
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月25日 下午5:32:08
	 */
	List<Map<String, Object>> listOrderById(String[] id);
	
	/**
	 * @Title: getInitialLevel
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param wk_type
	 * @return: Map<String,Object>
	 * @author: Ian.Huang
	 * @date: 2017年8月25日 下午11:15:16
	 */
	Map<String, Object> getInitialLevel(@Param("wk_type")String wk_type);
	
	/**
	 * @Title: addWorkOrder
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param wos
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月26日 上午12:19:58
	 */
	int addWorkOrder(WorkOrderStore wos);
	
	/**
	 * @Title: addWorkOrderProcessLog
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param wpl
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月26日 上午12:28:33
	 */
	int addWorkOrderProcessLog(WoProcessLog wpl);
	
	/**
	 * @Title: addWoOrder
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param order
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月26日 上午1:21:42
	 */
	int addWoOrder(List<Map<String, Object>> order);
	
	/**
	 * @Title: addWoOrderDetail
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param orderDetail
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月26日 上午1:21:59
	 */
	int addWoOrderDetail(List<Map<String, Object>> orderDetail);
	
	/**
	 * @Title: delTempWorkOrder
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param woId
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月27日 下午12:26:32
	 */
	int delTempWorkOrder(@Param("woId")String woId);
	
	/**
	 * @Title: delWoOrderByWoId
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param woId
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月26日 上午1:47:19
	 */
	int delWoOrderByWoId(@Param("woId")String woId);
	
	/**
	 * @Title: delWoOrderDetailByWoId
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param woId
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月26日 上午1:47:23
	 */
	int delWoOrderDetailByWoId(@Param("woId")String woId);
	
	/**
	 * @Title: updateWorkOrder
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param wos
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月26日 上午1:50:27
	 */
	int updateWorkOrder(WorkOrderStore wos);

	int updateWoStoreMaster(WorkOrderStore wos);

	int addStoreProcessLog(WoProcessLog woProcessLog);

	int addProcessRecord(WoProcessRecord woProcessRecord);

	WorkOrder getWorkOrdersByWoNo(String woNo);

	int addOperationFileName(Operation operation);
	
	String getEmpNameById(@Param("id")Integer id);

	int addWorkOrderEvent(WorkOrderEventMonitor workOrderEventMonitor);
	
	/**
	 * @Title: listErrorTypeByWoType
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月27日 上午11:03:27
	 */
	List<Map<String, Object>> listErrorTypeByWoType(Parameter parameter);
	
	/**
	 * @Title: listOrderByPlatformNumberOrWaybill
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月27日 下午4:21:54
	 */
	List<Map<String, Object>> listOrderByPlatformNumberOrWaybill(Parameter parameter);
	
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
	 * @Title: exportWorkOrder
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月27日 下午8:57:22
	 */
	List<Map<String, Object>> exportWorkOrder(Parameter parameter);

	int updateWorkOrderLd(WorkOrder workOrder);

	/**
	 * @Title: judgeSOReplyOrNot
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param woId
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年9月12日 下午9:36:15
	 */
	int judgeSOReplyOrNot(@Param("woId")String woId);

	List<String> findGroupByTypeAndStore(Map<String, Object> map);

	List<Map<String, Object>> getWOSStoreById(@Param("woId")String woId);


	List<Map<String, Object>> getAutoGroup(List<String> groupIds);

	/**
	 * @param parameter
	 * @return
	 */
	List<Map<String, Object>> listWoTypeByDepartment(@Param("so_flag")int parameter);
	
	Map<String, Object> getAutoEmp(List<Integer> empIds);

	List<String> getEmpIdByTeamId(@Param("groupId")Integer groupId);

	int updateWSMCurrent(WorkOrderStore wos);
	
	/**
	 * @Title: getDepartmentByGroup
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param groupId
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年9月14日 下午7:12:18
	 */
	String getDepartmentByGroup(@Param("groupId")String groupId);

	List<String> findWorkStoreByCur();

	List<Map<String, Object>> getempIdsByCuPrGroup(@Param("groupId")String autoGroupId);

	Integer getCountByGroupId(@Param("groupId") Integer id);

	Integer getCountByempId(@Param("empid") String empid);

	String getMinGroup(@Param("teamIds") String teamIds);

	List<String> findWoIdByEmp();

	Map<String, Object> getCurGroupById(@Param("woId") String woId);

	List<String> getEmpByGroup(List<String> groupIds);

	List<Map<String,Object>> getEmpIdByCur(List<String> empIdList);

	List<String> getGroupIdByEmp(Map<String, Object> map);

	String getGroupNameById(@Param("id") String autoGroupId);

	List<Map<String, Object>> getMinCountGroup(List<String> groupList);

	Map<String, Object> getSubmitLogByWoId(@Param("woId") String woId);

	List<Map<String, Object>> getRecordByWoId(@Param("woId") String woId);

	List<Map<String, Object>> getLogRecordByWoId(@Param("woId") String woId);

	List<String> findStroeCodeByGroupID(@Param("teamId") String teamId);

	List<Map<String, Object>> findGroupListByTeamid(@Param("teamId") String teamId);

	Map<String, Object> getAllCount(Parameter parameter);

	/**
	 * @param parseInt
	 * @return
	 */
	String getEmailById(@Param("id")String parseInt);

	/**
	 * @param teamId
	 * @return
	 */
	String getTeamEmailById(@Param("id")String teamId);

	/**
	 * @param woId
	 * @return
	 */
	Map<String,Object> selectMasterById(@Param("id")String woId);

	/**
	 * @param woNo
	 * @return
	 */
	List<Map<String, Object>> getWorkOrderByWoNo(@Param("woNo")String woNo);

	/**
	 * @param string
	 * @return
	 */
	List<Map<String, Object>> getIdByWoNos(@Param("woNos")String string);

	/**
	 * @param woNo
	 * @param woId
	 * @return
	 */
	List<Map<String, Object>> getWOByWoNoNoId(@Param("woNo")String woNo, @Param("woId")String woId);

	/**
	 * @return
	 */
	int getGroupTypeById(@Param("id")String id);

	/**
	 * @param woNo
	 * @return
	 */
	List<Map<String, Object>> getSplitList(@Param("woNo")String woNo);

	/**
	 * @param woNo
	 * @return
	 */
	int getWoNoLikeCount(String woNo);

	/**
	 * @param currentWos
	 * @return
	 */
	int updateWoStoreMasterSplitTo(WorkOrderStore currentWos);

	/**
	 * @param string
	 * @return
	 */
	String getIdByWoNo(@Param("woNo")String string);
	
	Map<String,Object> getWOByWoNo(@Param("woNo")String string);

	/**
	 * @param wos
	 * @return
	 */
	int updateWo(WorkOrderStore wos);

    /**
     * @param storeCode
     * @return
     */
    String getStoreNameByCode(@Param("store_code")String storeCode);

	
}
