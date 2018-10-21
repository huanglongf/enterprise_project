package com.bt.workOrder.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.model.Employee;
import com.bt.lmis.page.QueryParameter;
import com.bt.workOrder.bean.Packages;
import com.bt.workOrder.model.ClaimCount;
import com.bt.workOrder.model.ClaimDetail;
import com.bt.workOrder.model.ClaimSchedule;
import com.bt.workOrder.model.Manhours;
import com.bt.workOrder.model.ManhoursAlter;
import com.bt.workOrder.model.OMSClaim;
import com.bt.workOrder.model.Operation;
import com.bt.workOrder.model.WorkOrder;
import com.bt.workOrder.model.WorkOrderEventMonitor;

/**
 * @Title:WorkOrderManagementMapper
 * @Description: TODO
 * @author Ian.Huang 
 * @date 2017年1月11日下午1:50:31
 */
public interface WorkOrderManagementMapper<T> {
	
	/**
	 * @Title: getWorkOrderByExpressNumber
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param express_number
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年6月7日 上午11:16:40
	 */
	List<Map<String, Object>> getWorkOrderByExpressNumber(@Param("express_number")String express_number);
	
	/**
	 * 
	 * @Description: TODO
	 * @param ids
	 * @return: List<WorkOrder>  
	 * @author Ian.Huang 
	 * @date 2017年4月20日下午5:42:59
	 */
	List<WorkOrder> getWorkOrdersById(String[] ids);
	
	/**
	 * 
	 * @Description: TODO
	 * @param waybill
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年4月13日下午2:55:41
	 */
	List<Map<String, Object>> getWaybillDetailByWaybill(@Param("waybill")String waybill);
	
	/**
	 * 
	 * @Description: TODO
	 * @param parent_id
	 * @return: List<ClaimDetail>  
	 * @author Ian.Huang 
	 * @date 2017年4月12日下午8:54:50
	 */
	List<ClaimDetail> selectOMSInterfaceClaimDetail(@Param("parent_id")Integer parent_id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @return: OMSClaim  
	 * @author Ian.Huang 
	 * @date 2017年4月12日下午3:51:01
	 */
	OMSClaim getOMSClaimById(@Param("id")Integer id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param woId
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年4月21日下午2:40:54
	 */
	String getClaimStatus(@Param("woId")String woId);
	
	/**
	 * 
	 * @Description: TODO
	 * @param now
	 * @return: List<ClaimSchedule>  
	 * @author Ian.Huang 
	 * @date 2017年4月21日下午2:06:52
	 */
	List<ClaimSchedule> getClaimWorkOrderOnDeadline(@Param("now")String now);
	
	/**
	 * 
	 * @Description: TODO
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年4月5日下午9:21:30
	 */
	Integer getWarningDays();
	
	/**
	 * 
	 * @Description: TODO
	 * @param woId
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年4月5日下午8:16:37
	 */
	Integer delCliamSchedule(@Param("woId")String woId);
	
	/**
	 * 
	 * @Description: TODO
	 * @param claimSchedule
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年4月5日下午7:59:36
	 */
	Integer addCliamSchedule(ClaimSchedule claimSchedule);
	
	/**
	 * 
	 * @Description: TODO
	 * @param group
	 * @param dateRangeStart
	 * @param dateRangeEnd
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年3月16日下午3:42:50
	 */
	List<Map<String, Object>> loadingEmployeeInGroupProcessSummary(@Param("group")String group, @Param("dateRangeStart")String dateRangeStart, @Param("dateRangeEnd")String dateRangeEnd);
	
	/**
	 * 
	 * @Description: TODO
	 * @param dateRangeStart
	 * @param dateRangeEnd
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年3月15日下午4:06:55
	 */
	List<Map<String, Object>> loadingGroupProcessSummary(@Param("dateRangeStart")String dateRangeStart, @Param("dateRangeEnd")String dateRangeEnd);
	
	/**
	 * 
	 * @Description: TODO
	 * @param dateRangeStart
	 * @param dateRangeEnd
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年3月15日下午2:51:08
	 */
	List<Map<String, Object>> workOrderProcessStatusSummary(@Param("dateRangeStart")String dateRangeStart, @Param("dateRangeEnd")String dateRangeEnd);
	
	/**
	 * 
	 * @Description: TODO
	 * @param dateRangeStart
	 * @param dateRangeEnd
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年3月14日下午3:02:57
	 */
	List<Map<String, Object>> untreatedWorkOrderSummary(@Param("dateRangeStart")String dateRangeStart, @Param("dateRangeEnd")String dateRangeEnd);
	
	/**
	 * 
	 * @Description: TODO
	 * @param level
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年2月16日上午10:22:03
	 */
	String getLevelName(@Param("level")String level);
	
	/**
	 * 
	 * @Description: TODO
	 * @param wo_id
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年2月8日上午11:22:28
	 */
	Integer getSort(@Param("wo_id")String wo_id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年2月23日下午2:03:02
	 */
	void cancelWaybillWarning(@Param("id")String id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param processor
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年2月3日下午4:26:40
	 */
	List<Map<String, Object>> monitoringStatus(@Param("processor")String processor);
	
	/**
	 * 
	 * @Description: TODO
	 * @param update_by
	 * @param id
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年2月3日上午12:45:03
	 */
	Integer delClaimDetail(@Param("update_by")String update_by,@Param("id")String id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @return: ClaimDetail  
	 * @author Ian.Huang 
	 * @date 2017年2月2日下午10:43:26
	 */
	ClaimDetail getClaimDetailById(@Param("id")String id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param wo_no
	 * @return: List<ClaimDetail>
	 * @author Ian.Huang 
	 * @date 2017年2月2日下午10:43:00
	 */
	List<ClaimDetail> getClaimDetailByWoId(@Param("wo_id")String wo_id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param claimDetail
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年2月2日下午10:35:07
	 */
	Integer updateCliamDetail(ClaimDetail claimDetail);
	
	/**
	 * 
	 * @Description: TODO
	 * @param claimDetail
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年2月2日下午10:34:53
	 */
	Integer addCliamDetail(List<ClaimDetail> claimDetail);
	
	/**
	 * 
	 * @Description: TODO
	 * @param cc
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年1月26日下午11:18:52
	 */
	Integer updateNum(ClaimCount cc);
	
	/**
	 * 
	 * @Description: TODO
	 * @param cc
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年1月26日下午11:18:30
	 */
	Integer initializeNum(ClaimCount cc);
	
	/**
	 * 
	 * @Description: TODO
	 * @param date
	 * @return: ClaimCount  
	 * @author Ian.Huang 
	 * @date 2017年1月26日下午11:19:10
	 */
	ClaimCount getCurrentNum(@Param("date")String date);
	
	/**
	 * 
	 * @Description: TODO
	 * @param date
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年1月26日下午10:21:35
	 */
	Integer judgeDateExist(@Param("date")String date);
	
	/**
	 * 
	 * @Description: TODO
	 * @param wk_type
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年1月26日下午3:14:23
	 */
	String getInitialLevel(@Param("wk_type")String wk_type);
	
	/**
	 * likun 20170815
	 */
	
	public ArrayList<T>get_wo_level(Map<String, Object> param);
	
	/**
	 * 
	 * @Description: TODO
	 * @param ops
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年4月12日下午7:28:19
	 */
	Integer addOperations(List<Operation> ops);
	
	/**
	 * 
	 * @Description: TODO
	 * @param op
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年1月25日下午5:16:52
	 */
	Integer addOperation(Operation op);
	
	/**
	 * 
	 * @Description: TODO
	 * @param op
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年1月25日下午5:09:50
	 */
	Integer updateOperation(Operation op);
	
	/**
	 * 
	 * @Description: TODO
	 * @param op
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年1月25日下午5:06:41
	 */
	Integer judgeOperationExist(Operation op);
	
	/**
	 * 
	 * @Description: TODO
	 * @param op
	 * @return: Object  
	 * @author Ian.Huang 
	 * @date 2017年1月25日下午1:44:14
	 */
	Object getValue(Operation op);
	
	/**
	 * 
	 * @Description: TODO
	 * @param woType
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年1月25日下午1:27:01
	 */
	List<Map<String, Object>> getColumnsInPage(@Param("woType")String woType);
	
	/**
	 * 
	 * @Description: TODO
	 * @param express_number
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年1月24日下午4:42:03
	 */
	List<Map<String, Object>> queryWarningDetail(@Param("express_number")String express_number);
	
	/**
	 * 
	 * @Description: TODO
	 * @param express_number
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年1月24日下午4:23:49
	 */
	List<Map<String, Object>> queryRouteDetail(@Param("express_number")String express_number);
	
	/**
	 * 
	 * @Description: TODO
	 * @param express_number
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年1月24日下午4:07:43
	 */
	List<Map<String, Object>> queryPackageDetail(@Param("express_number")String express_number);
	
	/**
	 * 
	 * @Description: TODO
	 * @param wo_id
	 * @return: List<WorkOrderEventMonitor>  
	 * @author Ian.Huang 
	 * @date 2017年1月24日上午9:52:53
	 */
	List<WorkOrderEventMonitor> queryWorkOrderEventMonitor(@Param("wo_id")String wo_id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param wo_id
	 * @return: Map<String, Object>  
	 * @author Ian.Huang 
	 * @date 2017年1月23日下午7:45:21
	 */
	Map<String, Object> displayWorkOrder(@Param("wo_id")String wo_id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param mh_id
	 * @param wo_id
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年1月22日下午7:30:30
	 */
	String getReturnStandardManhours(@Param("mh_id")String mh_id, @Param("wo_id")String wo_id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param wo_id
	 * @return: WorkOrderEventMonitor  
	 * @author Ian.Huang 
	 * @date 2017年1月20日下午8:40:20
	 */
	WorkOrderEventMonitor getLastManhoursAlterEvent(@Param("wo_id")String wo_id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param new_wo
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年1月20日下午8:40:11
	 */
	Integer updateWorkOrder(WorkOrder new_wo);
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @return: Employee  
	 * @author Ian.Huang 
	 * @date 2017年1月19日下午4:35:20
	 */
	Employee getEmployeeById(@Param("id")Integer id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param group
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年1月19日下午3:52:07
	 */
	List<Map<String, Object>> getEmployeeInGroup(@Param("group")Integer group, @Param("except")Integer except);
	
	/**
	 * 
	 * @Description: TODO
	 * @param wo
	 * @param store
	 * @param warehouseType
	 * @return: List<Map<String, Object>>  
	 * @author Ian.Huang 
	 * @date 2017年1月19日下午3:08:56
	 */
	List<Map<String, Object>> getGroupInPower(@Param("wo")WorkOrder wo, @Param("warehouseType")Integer warehouseType);
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @return: WorkOrder  
	 * @author Ian.Huang 
	 * @date 2017年1月19日上午10:59:55
	 */
	WorkOrder selectWorkOrderById(@Param("id")String id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param wo
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年1月17日下午2:00:06
	 */
	Integer add(WorkOrder wo);
	
	/**
	 * 
	 * @Description: TODO
	 * @param woem
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年1月18日上午10:50:45
	 */
	Integer addWorkOrderEvent(WorkOrderEventMonitor woem);
	
	/**
	 * 
	 * @Description: TODO
	 * @param event
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年1月18日上午10:50:07
	 */
	String getEventRemark(@Param("event")String event);	
	/**
	 * 
	 * @Description: TODO
	 * @param manhoursAlter
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年1月17日下午3:04:40
	 */
	Integer addManhoursAlter(ManhoursAlter manhoursAlter);
	
	/**
	 * 
	 * @Description: TODO
	 * @param manhours
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年1月17日下午2:50:12
	 */
	Integer allocatedManhours(Manhours manhours);
	
	/**
	 * 
	 * @Description: TODO
	 * @param account
	 * @param date
	 * @return: Manhours
	 * @author Ian.Huang 
	 * @date 2017年1月17日下午2:22:34
	 */
	Manhours getManhours(@Param("account")Integer account, @Param("date")String date);
	
	/**
	 * 
	 * @Description: TODO
	 * @param woType
	 * @param woLevel
	 * @return: Map<String,Object>
	 * @author Ian.Huang 
	 * @date 2017年1月17日下午1:59:33
	 */
	Map<String, Object> gethours(@Param("woType")String woType, @Param("woLevel")String woLevel);
	
	/**
	 * 
	 * @Description: TODO
	 * @param group
	 * @param wo
	 * @return: Map<String,Object>  
	 * @author Ian.Huang 
	 * @date 2017年1月18日下午5:46:00
	 */
	Map<String, Object> judgeGroupStorePower(@Param("group")Integer group, @Param("wo")WorkOrder wo);
	
	/**
	 * 
	 * @Description: TODO
	 * @param warehouse
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年1月18日下午5:49:33
	 */
	Integer getWarehouseType(@Param("warehouse")String warehouse);
	
	/**
	 * 
	 * @Description: TODO
	 * @param group
	 * @param wo
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年1月17日下午9:50:26
	 */
	Integer judgeGroupWorkPower(@Param("group")Integer group, @Param("wo")WorkOrder wo);
	
	/**
	 * 
	 * @Description: TODO
	 * @param employee
	 * @return: List<Integer>  
	 * @author Ian.Huang 
	 * @date 2017年1月16日下午8:44:57
	 */
	List<Integer> selectGroups(@Param("employee")Integer employee);
	
	/**
	 * 
	 * @Description: TODO
	 * @param express_number
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年3月2日下午1:30:45
	 */
	Integer getExpressNumberWithWorkOrder(@Param("express_number")String express_number);
	
	/**
	 * 
	 * @Description: TODO
	 * @param express_number
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年1月16日下午1:54:04
	 */
	Map<String, Object> getByExpressNumber(@Param("express_number")String express_number);
	
	/**
	 * 
	 * @Description: TODO
	 * @param type
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年2月28日下午9:14:47
	 */
	List<Map<String, Object>> getException(@Param("type")String type);
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年3月21日下午10:22:05
	 */
	String getLevelAlterReasonById(@Param("id")Integer id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param type
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年3月8日下午1:27:21
	 */
	List<Map<String, Object>> getLevelAlterReason(@Param("type")String type);
	
	/**
	 * 
	 * @Description: TODO
	 * @param type
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年1月12日下午2:18:19
	 */
	List<Map<String, Object>> getLevel(@Param("type")String type);
	
	/**
	 * 
	 * @Description: TODO
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年1月12日下午1:24:02
	 */
	List<Map<String, Object>> getCarrier();
	
	/**
	 * 
	 * @Description: TODO
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年1月12日下午1:23:47
	 */
	List<Map<String, Object>> getWarehouse();
	
	/**
	 * 
	 * @Description: TODO
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年1月12日下午1:15:09
	 */
	List<Map<String, Object>> getStore();
	
	/**
	 * 
	 * @Description: TODO
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年1月12日上午11:54:44
	 */
	List<Map<String, Object>> getWOType();
	
	/**
	 * 
	 * @Description: TODO(列表计数)
	 * @param queryParam
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年1月5日下午7:55:10
	 */
	Integer countQuery(QueryParameter queryParam);
	
	/**
	 * 
	 * @Description: TODO(列表查询)
	 * @param queryParam
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年1月5日下午7:55:01
	 */
	List<Map<String, Object>> query(QueryParameter queryParam);
	
	List<Map<String, Object>> exportWO(QueryParameter queryParam);

	public void stopWatch(String string);

	public List<Map<String, Object>> findNeedLevelUpData(Map param);

	public void updateWorkOrder_se(Map<String, Object> obj);

	public List<Map<String, Object>> finWoMasterData(Map param);

	public List<Map<String, Object>> findNeedLevelUpData_ADV(Map param);
	
	public List<Map<String,Object>>  claimDetailFormSelect(Map<String,Object> param);

	public void delClaimDetailByWid(String wo_id);
	
	public List<Map<String,Object>> getTicketForWorkOrder(String waybill);

	List<Map<String, Object>> query2(Map<String, Object> map);

	int countQuery2(Map<String, Object> map);
	
	public ArrayList<T>get_reason_info(Map<String,Object>param);
	
	public void add_log_pro(WorkOrderEventMonitor bean);

	int judgeGroupWorkPowerNoLevel(@Param("group")Integer group, @Param("wo")WorkOrder wo);

	int getProcessControl(@Param("groupId")Integer groupId);

	/**
	 * @return
	 */
	List<Map<String, Object>> getAllNoControlGroup();

	/**
	 * @param woNo
	 * @return
	 */
	String getEmailByWoNo(@Param("woNo")String woNo);
	
	int updateFailureReason(@Param("id")String id,@Param("failureReason")String failureReason);

	List<Packages> listOrderDetailByWaybill(@Param("waybill")String waybill);

    List<String> selectResultInfo(@Param("wkType")String wkType,@Param("errorType")String errorType);

    /**
     * @param id
     * @param string 
     * @return
     */
    String getRemark(@Param("id")String id, @Param("woType")String string);
    
}
