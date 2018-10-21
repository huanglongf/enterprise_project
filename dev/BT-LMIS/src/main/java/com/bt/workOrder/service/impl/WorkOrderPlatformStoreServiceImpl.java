package com.bt.workOrder.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bt.EPlatform;
import com.bt.OSinfo;
import com.bt.base.redis.RedisUtils;
import com.bt.common.CommonUtils;
import com.bt.common.controller.param.Parameter;
import com.bt.common.controller.result.Result;
import com.bt.common.util.MailUtil;
import com.bt.lmis.dao.CommonDataMapper;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.DateUtil;
import com.bt.utils.FileUtil;
import com.bt.utils.ZipUtils;
import com.bt.utils.observer.Observers;
import com.bt.utils.observer.Visual;
import com.bt.workOrder.bean.WoMutualLog;
import com.bt.workOrder.common.Constant;
import com.bt.workOrder.dao.ShopGroupMapper;
import com.bt.workOrder.dao.WoAssociationMapper;
import com.bt.workOrder.dao.WoMutualLogMapper;
import com.bt.workOrder.dao.WorkOrderManagementMapper;
import com.bt.workOrder.dao.WorkOrderPlatformStoreMapper;
import com.bt.workOrder.model.Operation;
import com.bt.workOrder.model.WoAssociation;
import com.bt.workOrder.model.WoProcessLog;
import com.bt.workOrder.model.WoProcessRecord;
import com.bt.workOrder.model.WorkOrder;
import com.bt.workOrder.model.WorkOrderEventMonitor;
import com.bt.workOrder.model.WorkOrderStore;
import com.bt.workOrder.service.WorkOrderPlatformStoreService;
import com.bt.workOrder.utils.WorkOrderUtils;

@Service
public class WorkOrderPlatformStoreServiceImpl implements WorkOrderPlatformStoreService<T> {

	private static final Logger logger = Logger.getLogger(WorkOrderPlatformStoreServiceImpl.class);
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final String AUTO_GROUP_WORK="AUTO_GROUP_WORK";
	private static final String AUTO_EMP_WORK="AUTO_EMP_WORK";
	
	@Autowired
	private CommonDataMapper<T> commonDataMapper;
	@Autowired
	private WorkOrderManagementMapper<T> workOrderManagementMapper;
	@Autowired
	private WorkOrderPlatformStoreMapper<T> workOrderPlatformStoreMapper;
	@Autowired
	private ShopGroupMapper<T> groupMapper;
	@Autowired
	private WoMutualLogMapper woMutualLogMapper;
	@Autowired
	private WoAssociationMapper woAssociationMapper;
	
	@Override
	public QueryResult<Map<String, Object>> search(Parameter parameter) {
		String[] temp = null;
		if(CommonUtils.checkExistOrNot(parameter.getParam().get("create_time"))) {
			temp = parameter.getParam().get("create_time").toString().split(" - ");
			parameter.getParam().put("create_time_start", temp[0]);
			parameter.getParam().put("create_time_end", temp[1]);
		}
		if(CommonUtils.checkExistOrNot(parameter.getParam().get("last_process_time"))) {
			temp = parameter.getParam().get("last_process_time").toString().split(" - ");
			parameter.getParam().put("last_process_time_start", temp[0]);
			parameter.getParam().put("last_process_time_end", temp[1]);
		}
		return new QueryResult<Map<String,Object>>(workOrderPlatformStoreMapper.search(parameter), workOrderPlatformStoreMapper.countSearch(parameter));
	}
	
	@Override
	public List<Map<String, Object>> listWoType() {
		return workOrderPlatformStoreMapper.listWoType();
	}

	@Override
	public JSONObject loadingHome1(Parameter parameter) {
		JSONObject result = new JSONObject();
		try {
			result.put("created", workOrderPlatformStoreMapper.countCurrentCreate(parameter));
			result.put("submited", workOrderPlatformStoreMapper.countcountCurrentProcessed(parameter));
			result.put("ended", workOrderPlatformStoreMapper.countCurrentOver(parameter));
			result.put("waitting", workOrderPlatformStoreMapper.countCurrentUnprocessed(parameter));
			result.put("result", "success");
			
		} catch (Exception e) {
			result.put("result", "error");
			e.printStackTrace();
			logger.error(e);
			
		} finally {}
		return result;
	}

	@Override
	public JSONObject loadingHome2(Parameter parameter) {
		JSONObject result = new JSONObject();
		try {
			List<Map<String, Object>> listCurrentCreate = workOrderPlatformStoreMapper.listCurrentCreate(parameter);
			List<Map<String, Object>> listCurrentOver = workOrderPlatformStoreMapper.listCurrentOver(parameter);
			int[] createdata1 = new int[7];
			int[] overdata2 = new int[7];
			for(int i = 6; i >= 0; i--) {
				// 近七日我创建的
				for(int j = 0; j < listCurrentCreate.size(); j++) {
					if(DateUtil.getPastDate(i).equals(listCurrentCreate.get(j).get("date"))) {
						createdata1[6-i] = Integer.parseInt(listCurrentCreate.get(j).get("num").toString());
						break;
					}
					if(j + 1 == listCurrentCreate.size()) {
						createdata1[6-i] = 0;
					}
				}
				// 近七日我完结的
				for(int j = 0; j < listCurrentOver.size(); j++) {
					if(DateUtil.getPastDate(i).equals(listCurrentOver.get(j).get("date"))) {
						overdata2[6-i] = Integer.parseInt(listCurrentOver.get(j).get("num").toString());
						break;
					}
					if(j + 1 == listCurrentOver.size()) {
						overdata2[6-i] = 0;
					}
				}
			}
			//
			JSONArray series = new JSONArray();
			//
			JSONObject json = new JSONObject();
			json.put("name", "我创建的");
			json.put("type", "line");
			json.put("data", createdata1);
			series.add(json);
			//
			json = new JSONObject();
			json.put("name", "我完结的");
			json.put("type", "line");
			json.put("data", overdata2);
			series.add(json);
			//
			result.put("series", series);
			//
			result.put("result", "success");
		} catch (Exception e) {
			result.put("result", "error");
			e.printStackTrace();
			logger.error(e);
			
		} finally {}
		return result;
	}

	@Override
	public WorkOrderStore getWorkOrderStoreById(String id) {
		return workOrderPlatformStoreMapper.getWorkOrderStoreById(id);
	}

	@Override
	public List<Map<String, Object>> listOrderByOrderNo(String orderNo) {
		return workOrderPlatformStoreMapper.listOrderByOrderNo(orderNo);
	}

	@Override
	public Map<String, Object> getOrderById(String id) {
		return workOrderPlatformStoreMapper.getOrderById(id);
	}

	@Override
	public List<Map<String, Object>> listOrderDetailByWaybill(String waybill) {
		return workOrderPlatformStoreMapper.listOrderDetailByWaybill(waybill);
	}
	
	@Override
	public List<Map<String, Object>> listWoOrderByWoId(String woId) {
		return workOrderPlatformStoreMapper.listWoOrderByWoId(woId);
	}
	
	@Override
	public Map<String, Object> getWoOrderById(String id) {
		return workOrderPlatformStoreMapper.getWoOrderById(id);
	}

	@Override
	public List<Map<String, Object>> listOrderDetailByWoOrderId(String woOrederId) {
		return workOrderPlatformStoreMapper.listOrderDetailByWoOrderId(woOrederId);
	}

	/**
	 * WOACTION
	 */
	@Override
	public Result worderOrderAction(Parameter parameter) {
		int result = 0;
		try {
			switch(parameter.getParam().get("action").toString()) {
			case Constant.ACTION_SAVE:;
			case Constant.ACTION_SUBMIT:
				if(CommonUtils.checkExistOrNot(parameter.getParam().get("woId"))) {
					updateWorkOrder(parameter);
				} else {
					addWorkOrder(parameter);
				}break;
			case Constant.ACTION_FORWARD:
				result = forwardWorkOrder(parameter);break;
			case Constant.ACTION_SPLIT:
				splitWorkOrder(parameter);break;
			case Constant.ACTION_REPLY:
				replyWorkOrder(parameter);break;
			case Constant.ACTION_OVER:
				result = overWorkOrder(parameter);break;
			case Constant.ACTION_CANCLE:
				result = cancleWorkOrder(parameter);break;
			case Constant.ACTION_ASSIST:
				result= assistWorkOrder(parameter);break;
			case Constant.ACTION_UPDATE:
				updWorkOrder(parameter);break;
			default:break;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return new Result(false, Constant.RESULT_FAILURE_MSG + e.getMessage());
		}
		return new Result(true, (Constant.ACTION_FORWARD.equals(parameter.getParam().get("action").toString())
				|| Constant.ACTION_OVER.equals(parameter.getParam().get("action").toString())
				|| Constant.ACTION_CANCLE.equals(parameter.getParam().get("action").toString()) ? result + "条工单记录被操作" : Constant.RESULT_SUCCESS_MSG));
	}
	
	private void addWorkOrder(Parameter parameter) throws Exception {
		int processDepartment = Integer.parseInt(parameter.getParam().get("process_department").toString());
		final WorkOrderStore wos = new WorkOrderStore(UUID.randomUUID().toString());
		// 创建人
		wos.setCreateBy(parameter.getCurrentAccount().getId().toString());
		wos.setCreateByDisplay(parameter.getCurrentAccount().getName());
		String woNoStr = CommonUtils.checkExistOrNot(parameter.getParam().get("woNoStr"))?parameter.getParam().get("woNoStr").toString():"";
		
		Set<String> staffsSet = new HashSet<>();
		if(CommonUtils.checkExistOrNot(woNoStr)){
			String[] woNos = woNoStr.split(",");
			staffsSet = new HashSet<>(Arrays.asList(woNos));
			StringBuffer woNoStrRes = new StringBuffer();
			for(String woNo:staffsSet){
				woNoStrRes.append(woNo+",");
			}
			if(woNoStrRes.length()>1){
				woNoStrRes.deleteCharAt(woNoStrRes.length()-1);
			}
			wos.setRelatedWoNo(woNoStrRes.toString());
		}
		// 创建组
		String teamId="";
		/*List<String> teamIdList = groupMapper.queryTeamIdById(parameter.getCurrentAccount().getId());
		if(teamIdList.size()>0) {
			teamId=teamIdList.get(0);
		}*/
		teamId=parameter.getParam().get("createGroup").toString();
		wos.setCreateByGroup(teamId);
		Map<String, Object> currentroup = workOrderPlatformStoreMapper.getStoreById(teamId);
		wos.setCreateByGroupDisplay(currentroup.get("groupName").toString());
		if("1".equals(currentroup.get("groupType").toString())){
			wos.setCurrentProcessor(parameter.getCurrentAccount().getId().toString());
			wos.setCurrentProcessorDisplay(parameter.getCurrentAccount().getName());
			wos.setCurrentProcessorGroup(teamId);
			wos.setCurrentProcessorGroupDisplay(currentroup.get("groupName").toString());
		}
		// 获取工单号
		Map<String, String> param = new HashMap<String, String>();
		param.put("business_type", "1");
		param.put("serial_code", "");
		commonDataMapper.get_business_code(param);
		wos.setWoNo(param.get("serial_code"));
		// 工单状态
		wos.setWoStatus(Constant.ACTION_SUBMIT.equals(parameter.getParam().get("action"))&&processDepartment!=1 ? 1 : 0);
		// 是否已提交
		wos.setSubmitFlag(Constant.ACTION_SUBMIT.equals(parameter.getParam().get("action")) ? 1 : 0);
		// 工单类型
		wos.setWoType(parameter.getParam().get("woType").toString());
		wos.setWoTypeDisplay(workOrderPlatformStoreMapper.getWoTypeName(wos.getWoType()));
		// 异常类型
		wos.setErrorType(parameter.getParam().get("errorType").toString());
		wos.setErrorTypeDisplay(workOrderPlatformStoreMapper.getErrorTypeName(wos.getErrorType()));
		// 问题描述
		wos.setIssueDescription(parameter.getParam().get("issueDescription").toString());
		//设置跟进状态未跟进
		wos.setFollowUpFlag(0);
		
		wos.setProcessDepartment(processDepartment);
		wos.setRelatedNumber(CommonUtils.checkExistOrNot(parameter.getParam().get("related_number"))?parameter.getParam().get("related_number").toString():"");
		String storeCode = CommonUtils.checkExistOrNot(parameter.getParam().get("problem_store"))?parameter.getParam().get("problem_store").toString():"";
		wos.setProblemStore(storeCode);
		wos.setProblemStoreDisplay(workOrderPlatformStoreMapper.getStoreNameByCode(storeCode));
		wos.setTitle(CommonUtils.checkExistOrNot(parameter.getParam().get("title"))?parameter.getParam().get("title").toString():"");
		wos.setOperationSystem(CommonUtils.checkExistOrNot(parameter.getParam().get("operation_system"))?parameter.getParam().get("operation_system").toString():"");
		String exProcTime =  CommonUtils.checkExistOrNot(parameter.getParam().get("expectation_process_time"))?parameter.getParam().get("expectation_process_time").toString():null;
		if(CommonUtils.checkExistOrNot(exProcTime)){
			wos.setExpectationProcessTime(DateUtil.StrToTime(exProcTime));
		}
		
		// 绑定订单
		List<Map<String, Object>> order = null;
		List<Map<String, Object>> orderDetail = null;
		if(CommonUtils.checkExistOrNot(parameter.getParam().get("orderId[]"))) {
			// 指定绑定订单
			order = workOrderPlatformStoreMapper.listOrderById((String[]) parameter.getParam().get("orderId[]"));
			// 平台订单号
			wos.setPlatformNumber(CommonUtils.checkExistOrNot(order.get(0).get("platform_number")) ? order.get(0).get("platform_number").toString() : "");
			// 绑定订单明细
			orderDetail = new ArrayList<Map<String, Object>>();
			// 运单号
			StringBuffer waybill = new StringBuffer();
			for(int i = 0; i < order.size(); i++) {
				//
				waybill.append(order.get(i).get("waybill"));
				//
				order.get(i).put("id", UUID.randomUUID().toString());
				order.get(i).put("create_by", wos.getCreateBy());
				order.get(i).put("wo_store_id", wos.getId());
				//
				List<Map<String, Object>> tempOrderDetail = workOrderPlatformStoreMapper.listOrderDetailByWaybill(order.get(i).get("waybill").toString());
				for(int j = 0; j < tempOrderDetail.size(); j++) {
					tempOrderDetail.get(j).put("id", UUID.randomUUID().toString());
					tempOrderDetail.get(j).put("create_by", wos.getCreateBy());
					tempOrderDetail.get(j).put("wo_store_id", wos.getId());
					tempOrderDetail.get(j).put("wo_order_id", order.get(i).get("id"));
				}
				orderDetail.addAll(tempOrderDetail);
				if(i + 1 != order.size()) {
					waybill.append(",");
				}
			}
			// 运单号
			wos.setWaybill(waybill.toString());
		}
		if(CommonUtils.checkExistOrNot(parameter.getParam().get("platformNumber"))) {
			// 平台订单号查询
			order = workOrderPlatformStoreMapper.listOrderByPlatformNumberOrWaybill(parameter);
			// 平台订单号
			wos.setPlatformNumber(parameter.getParam().get("platformNumber").toString());
			if(CommonUtils.checkExistOrNot(order)) {
				// 绑定订单明细
				orderDetail = new ArrayList<Map<String, Object>>();
				// 运单号
				StringBuffer waybill = new StringBuffer();
				for(int i = 0; i < order.size(); i++) {
					//
					waybill.append(order.get(i).get("waybill"));
					//
					order.get(i).put("id", UUID.randomUUID().toString());
					order.get(i).put("create_by", wos.getCreateBy());
					order.get(i).put("wo_store_id", wos.getId());
					//
					List<Map<String, Object>> tempOrderDetail = workOrderPlatformStoreMapper.listOrderDetailByWaybill(order.get(i).get("waybill").toString());
					for(int j = 0; j < tempOrderDetail.size(); j++) {
						tempOrderDetail.get(j).put("id", UUID.randomUUID().toString());
						tempOrderDetail.get(j).put("create_by", wos.getCreateBy());
						tempOrderDetail.get(j).put("wo_store_id", wos.getId());
						tempOrderDetail.get(j).put("wo_order_id", order.get(i).get("id"));
					}
					orderDetail.addAll(tempOrderDetail);
					if(i + 1 != order.size()) {
						waybill.append(",");
					}
				}
				// 运单号
				wos.setWaybill(waybill.toString());
			} else {
				if(CommonUtils.checkExistOrNot(parameter.getParam().get("waybill"))) {
					wos.setWaybill(parameter.getParam().get("waybill").toString());
				} else {
					wos.setWaybill(Constant.DEFAULT_WAYBILL);
				}
			}
		}
		wos.setAccessory(parameter.getParam().get("accessory").toString());
		//
		WoProcessLog wpl = null;
		WorkOrder wo = null;
		if(Constant.ACTION_SUBMIT.equals(parameter.getParam().get("action"))) {
			//销售运营没有当前处理人
			if(processDepartment==0){
				// 当前处理人
				wos.setCurrentProcessor(wos.getCreateBy());
				wos.setCurrentProcessorDisplay(wos.getCreateByDisplay());
				// 当前处理组别
				wos.setCurrentProcessorGroup(wos.getCreateByGroup());
				wos.setCurrentProcessorGroupDisplay(wos.getCreateByGroupDisplay());
				// 物流工单
				wo = getWorkOrder(wos);
			}
			//当前负责人
			wos.setOwner(wos.getCreateBy());
			wos.setOwnerDisplay(wos.getCreateByDisplay());
			//当前负责组别
			wos.setOwnerGroup(wos.getCreateByGroup());
			wos.setOwnerGroupDisplay(wos.getCreateByGroupDisplay());
			// 最新处理时间
			wos.setLastProcessTime(new Date());
			// 工单日志
			Map<String, Object> groupMap = groupMapper.getGroupTypeById(teamId);
			String grouType = groupMap.get("group_type").toString();
			if(grouType.equals("0")) {
				wpl = getSubmitWoProcessLog(wos,processDepartment,workOrderPlatformStoreMapper.getDepartmentByGroup(teamId));
			}else {
				wpl = getSubmitWoProcessLog(wos,processDepartment,"销售运营部");
			}
		}
		// 手动提交事务
		WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		//
		try {
			workOrderPlatformStoreMapper.addWorkOrder(wos);
			if(CommonUtils.checkExistOrNot(order)) {
				workOrderPlatformStoreMapper.addWoOrder(order);
				if(CommonUtils.checkExistOrNot(orderDetail)) {
					workOrderPlatformStoreMapper.addWoOrderDetail(orderDetail);
				}
			}
			if(CommonUtils.checkExistOrNot(wpl)) {
				workOrderPlatformStoreMapper.addWorkOrderProcessLog(wpl);
			}
			if(CommonUtils.checkExistOrNot(wo)) {
				// 新增工单
				Date now = new Date();
				wo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
				// 工单事件监控
				WorkOrderEventMonitor woem= new WorkOrderEventMonitor();
				woem.setCreate_by(wo.getCreateBy());
				woem.setWo_id(wo.getId());
				// 创建
				woem.setId(UUID.randomUUID().toString());
				woem.setSort(1);
				woem.setEvent(Constant.WO_EVENT_CREATED);
				woem.setEvent_status(true);
				woem.setEvent_description(WorkOrderUtils.generateEventRemark(woem, null));
				woem.setRemark(
					new StringBuffer("初始工单级别：")
					.append(workOrderManagementMapper.getLevelName(wo.getWoLevel()))
					.append("；{平台订单号：")
					.append(wos.getPlatformNumber())
					.append("/快递单号：")
					.append(wos.getWaybill())
					.append("}").toString()
					);
				workOrderManagementMapper.addWorkOrderEvent(woem);
				// 获取工单类型工单级别对应工时
				Map<String, Object> hours= workOrderManagementMapper.gethours(wo.getWoType(), wo.getWoLevel());
				// 标准工时
				wo.setStandardManhours(new BigDecimal(hours.get("wk_standard").toString()));
				// 计划完成时间
				Calendar cal = Calendar.getInstance();
				cal.setTime(now);
				cal.add(Calendar.MINUTE, Integer.parseInt(hours.get("wk_plan").toString()));
				wo.setEstimatedTimeOfCompletion(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
				// 计划完成时间
				woem.setId(UUID.randomUUID().toString());
				woem.setSort(2);
				woem.setEvent(Constant.WO_EVENT_ETC);
				// 事件状态已为成功
				woem.setEvent_description(WorkOrderUtils.generateEventRemark(woem, null));
				woem.setRemark("计划完成时间：" + wo.getEstimatedTimeOfCompletion());
				workOrderManagementMapper.addWorkOrderEvent(woem);
				// 下一级升级时间
				cal.setTime(now);
				cal.add(Calendar.MINUTE, Integer.parseInt(hours.get("wk_timeout").toString()));
				wo.setCal_date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
				workOrderManagementMapper.add(wo);
				// 附件关联
				if(CommonUtils.checkExistOrNot(wo.getFileName_common())) {
					// 添加附件关联
					Operation op= new Operation();
					op.setId(UUID.randomUUID().toString());
					op.setCreate_by(wo.getCreateBy());
					op.setWo_id(wo.getId());
					op.setWo_type(wo.getWoType());
					op.setColumn_code("fileName");
					op.setColumn_value(wo.getFileName_common());
					workOrderManagementMapper.addOperation(op);
				}
				//增加交互日志
				WoMutualLog record = new WoMutualLog();
				record.setAccessory(CommonUtils.checkExistOrNot(wo.getFileName_common()) ? wo.getFileName_common() : "");
				record.setAction(Constant.WO_EVENT_CREATED);
				record.setCreateBy(parameter.getCurrentAccount().getId().toString());
				record.setCreateTime(new Date());
				record.setLog(wpl.getLog());
//				record.setProcessRemark(wos.getLastProcessInfo());
				record.setWoNo(wos.getWoNo());
				woMutualLogMapper.insert(record);
			}
			if(wos.getSubmitFlag()==1){
				//添加工单关联
				
				for(String woNo:staffsSet){
					if(CommonUtils.checkExistOrNot(woNo)){
						WoAssociation woAssociation = new WoAssociation();
						woAssociation.setWoNoP(wos.getWoNo());
						woAssociation.setWoNoS(woNo);
						woAssociation.setCreateBy(parameter.getCurrentAccount().getId().toString());
						woAssociation.setUpdateBy(parameter.getCurrentAccount().getId().toString());
						woAssociationMapper.insert(woAssociation);
					}
				}
			}
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new Exception(Constant.RESULT_FAILURE_MSG +"add"+ CommonUtils.getExceptionStack(e));
		}
		if(Constant.ACTION_SUBMIT.equals(parameter.getParam().get("action"))) {
			if(processDepartment==0){
					Visual visual=new Visual();
					visual.addObserver(Observers.getInstance());
					visual.setData(wo.getId());
			}else{
				String RedisIds = RedisUtils.get(1,AUTO_GROUP_WORK);
				if(CommonUtils.checkExistOrNot(RedisIds)) {
					List<String> woIdList = JSONArray.parseArray(RedisIds,String.class);
					woIdList.add(wos.getId());
					RedisUtils.set(1,AUTO_GROUP_WORK, JSON.toJSONString(woIdList));
					
				}else {
					ArrayList<String> woIdList=new ArrayList<>();
					woIdList.add(wos.getId());
					RedisUtils.set(1,AUTO_GROUP_WORK, JSON.toJSONString(woIdList));
				}
				
			}
				/*// (自动分配)
				new Thread(new Runnable(){
					@Override
					public void run(){
						try {
							*/
						 /*catch (Exception e) {
							e.printStackTrace();
							logger.error(e);
						}
					}
				}).start();
			}*/
		}
	}
	
	//woSplit 拆分
	private void splitWorkOrder(Parameter parameter) throws Exception {
		String teamId2 = parameter.getCurrentAccount().getTeamId();
		String[] split = teamId2.split(",");
		Map<String,Object> group = workOrderPlatformStoreMapper.getStoreById(split[0]);
		// 是否组内共享
		int ifShare= Integer.parseInt(group.get("ifShare").toString());
		String groupName = CommonUtils.checkExistOrNot(group.get("groupName"))?group.get("groupName").toString():"";
		String groupType = CommonUtils.checkExistOrNot(group.get("groupType"))?group.get("groupType").toString():"";
		String woTypeRe = CommonUtils.checkExistOrNot(parameter.getParam().get("woTypeRe"))?parameter.getParam().get("woTypeRe").toString():null;
		String woTypeReDisplay = null;
		if(CommonUtils.checkExistOrNot(woTypeRe)) {
			woTypeReDisplay = CommonUtils.checkExistOrNot(parameter.getParam().get("woTypeReDisplay"))?parameter.getParam().get("woTypeReDisplay").toString():"";
		} else {
			woTypeReDisplay = null;
		}
		String errorTypeRe = CommonUtils.checkExistOrNot(parameter.getParam().get("errorTypeRe"))?parameter.getParam().get("errorTypeRe").toString():null;
		String errorTypeReDisplay = null;
		if(CommonUtils.checkExistOrNot(errorTypeRe)) {
		    errorTypeReDisplay = CommonUtils.checkExistOrNot(parameter.getParam().get("errorTypeReDisplay"))?parameter.getParam().get("errorTypeReDisplay").toString():"";
		} else {
		    errorTypeReDisplay = null;
		}
		if(CommonUtils.checkExistOrNot(parameter.getParam().get("woId"))) {
			final WorkOrderStore currentWos = workOrderPlatformStoreMapper.getWorkOrderStoreById(parameter.getParam().get("woId").toString());
				if(CommonUtils.checkExistOrNot(parameter.getParam().get("employeeId"))) {
					if(currentWos.getWoStatus() == 0&&currentWos.getSubmitFlag()==1&&currentWos.getCurrentProcessor()!=null) {
						// 未处理
						if(currentWos.getWoNo().split("-").length>1 && Integer.parseInt(currentWos.getCurrentProcessor()) != parameter.getCurrentAccount().getId() && ifShare != 1&&groupType.equals("0")) {
							// 当前处理人不是自己且不是组内共享
							throw new Exception(Constant.RESULT_FAILURE_MSG +"split"+ "此工单不能拆分");
						}
						// 被拆分人ID
						String employeeId = (String) parameter.getParam().get("employeeId");
						//被转发组
						String teamId = (String) parameter.getParam().get("groupId");
						// 1.更新工单主表
						// 当前处理人更新为被转发人[Id,Name]
						// 被转发人名
						String employeeName=workOrderPlatformStoreMapper.getEmpNameById(Integer.parseInt(employeeId));
						//被拆分组名
						String teamName = workOrderPlatformStoreMapper.getGroupNameById(teamId);
						//
						String splitTo = teamName+"-"+employeeName;
						//若为-1 ，则选
						if(employeeId.equals("-1")) {
							employeeId="";
							employeeName="";
							splitTo = teamName;
						}
						WorkOrderStore wos=new WorkOrderStore();
						BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
						BeanUtils.copyProperties(wos, currentWos);
						wos.setId(UUID.randomUUID().toString());
						int num = workOrderPlatformStoreMapper.getWoNoLikeCount(currentWos.getWoNo()+"-");
						num = num+1;
						wos.setWoNo(currentWos.getWoNo()+"-"+num);
						wos.setCurrentProcessor(employeeId);
						wos.setCurrentProcessorDisplay(employeeName);
						wos.setCurrentProcessorGroup(teamId);
						wos.setCurrentProcessorGroupDisplay(teamName);
						wos.setWoTypeRe(woTypeRe);
						wos.setWoTypeReDisplay(woTypeReDisplay);
						wos.setSplitTo(splitTo);
						//子工单负责人是 主工单的 当前处理人
						wos.setOwner(currentWos.getCurrentProcessor());
						wos.setOwnerDisplay(currentWos.getCurrentProcessorDisplay());
						wos.setOwnerGroup(currentWos.getCurrentProcessorGroup());
						wos.setOwnerGroupDisplay(currentWos.getCurrentProcessorGroupDisplay());
						//子单创建组是分配的那个人
						wos.setCreateBy(String.valueOf(parameter.getCurrentAccount().getId()));
						wos.setCreateByDisplay(parameter.getCurrentAccount().getName());
						wos.setCreateByGroup(split[0]);
						wos.setCreateByGroupDisplay(groupName);
						// 手动提交事务
						WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
						DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
						DefaultTransactionDefinition def = new DefaultTransactionDefinition();
						def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
						TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
						try {
							if(workOrderPlatformStoreMapper.addWorkOrder(wos)==1) {
								// 2.新增日志信息
								// 当前操作人组ID获取组信息
								WoProcessLog woProcessLog=new WoProcessLog();
								woProcessLog.setId(UUID.randomUUID().toString());
								woProcessLog.setCreateTime(new Date());
								woProcessLog.setCreateBy(parameter.getCurrentAccount().getId().toString());
								woProcessLog.setCreateByDisplay(parameter.getCurrentAccount().getName());
								woProcessLog.setCreateByGroup(split[0]);
								woProcessLog.setCreateByGroupDisplay(groupName);
								woProcessLog.setUpdateTime(new Date());
								woProcessLog.setUpdateBy(parameter.getCurrentAccount().getId().toString());
								woProcessLog.setWoStoreId(currentWos.getId());
								woProcessLog.setAction(Constant.ACTION_SPLIT);
								if(groupType.equals("0")){
									woProcessLog.setWoLocationNode(Constant.ST_NODE);
									String department = workOrderPlatformStoreMapper.getDepartmentByGroup(split[0]);
									//String department = workOrderPlatformStoreMapper.getDepartmentByGroup(parameter.getCurrentAccount().getTeam_id());
									if(employeeName.equals("")) {
										woProcessLog.setLog(sdf.format(new Date()) + "<br/>"+(CommonUtils.checkExistOrNot(department) ? department : "[没有此部门]")+ "/"+
												(CommonUtils.checkExistOrNot(groupName) ? groupName.toString() : "[组名加载失败]") + "/" + parameter.getCurrentAccount().getName() + "<br/>将工单拆分给<br/>" + teamName+"<br/>子工单号为【"+wos.getWoNo()+"】");
									}else{
										woProcessLog.setLog(sdf.format(new Date()) + "<br/>"+(CommonUtils.checkExistOrNot(department) ? department : "[没有此部门]")+ "/"+
												(CommonUtils.checkExistOrNot(groupName) ? groupName.toString() : "[组名加载失败]") + "/" + parameter.getCurrentAccount().getName() + "<br/>将工单拆分给<br/>" + teamName+"/"+employeeName+"<br/>子工单号为【"+wos.getWoNo()+"】");
									}
								}
								if(groupType.equals("1")){
									woProcessLog.setWoLocationNode(Constant.SO_NODE);
									if(employeeName.equals("")) {
										woProcessLog.setLog(sdf.format(new Date()) + "<br/>"+"销售运营部"+ "/"+
												(CommonUtils.checkExistOrNot(groupName) ? groupName.toString() : "[组名加载失败]") + "/" + parameter.getCurrentAccount().getName() + "<br/>将工单拆分给<br/>" + teamName+"<br/>子工单号为【"+wos.getWoNo()+"】");
									}else {
										woProcessLog.setLog(sdf.format(new Date()) + "<br/>"+"销售运营部"+ "/"+
												(CommonUtils.checkExistOrNot(groupName) ? groupName.toString() : "[组名加载失败]") + "/" + parameter.getCurrentAccount().getName() + "<br/>将工单拆分给<br/>" + teamName+"/"+employeeName+"<br/>子工单号为【"+wos.getWoNo()+"】");
									}
								}
								woProcessLog.setProcessed(1);
								woProcessLog.setSort(System.currentTimeMillis());
								workOrderPlatformStoreMapper.addStoreProcessLog(woProcessLog);
								
								// 3.新增处理记录信息
								WoProcessRecord recode = new WoProcessRecord();
								recode.setId(UUID.randomUUID().toString());
								recode.setCreateTime(new Date());
								recode.setCreateBy(parameter.getCurrentAccount().getId().toString());
								recode.setUpdateTime(new Date());
								recode.setUpdateBy(parameter.getCurrentAccount().getId().toString());
								recode.setWoStoreLogId(woProcessLog.getId());
								recode.setProcessRemark(CommonUtils.checkExistOrNot(parameter.getParam().get("processInfo")) ? parameter.getParam().get("processInfo").toString() : "");
								recode.setAccessory(CommonUtils.checkExistOrNot(parameter.getParam().get("accessory")) ? parameter.getParam().get("accessory").toString() : "");
								recode.setWoTypeRe(woTypeRe);
								workOrderPlatformStoreMapper.addProcessRecord(recode);
								
								woProcessLog.setId(UUID.randomUUID().toString());
								woProcessLog.setWoStoreId(wos.getId());
								workOrderPlatformStoreMapper.addStoreProcessLog(woProcessLog);
								recode.setId(UUID.randomUUID().toString());
								recode.setWoStoreLogId(woProcessLog.getId());
								workOrderPlatformStoreMapper.addProcessRecord(recode);
								
							}
							transactionManager.commit(status);
							
							Map<String,Object> workOrderStore = workOrderPlatformStoreMapper.selectMasterById(wos.getId());
							String toMail = "";
							String content = "";
							String cp = CommonUtils.checkExistOrNot(workOrderStore.get("current_processor"))?String.valueOf(workOrderStore.get("current_processor")):"";
							String cpg = CommonUtils.checkExistOrNot(workOrderStore.get("current_processor_group"))?String.valueOf(workOrderStore.get("current_processor_group")):"";
							String cpgn = CommonUtils.checkExistOrNot(workOrderStore.get("current_processor_group_display"))?String.valueOf(workOrderStore.get("current_processor_group_display")):"";
							String woNo = CommonUtils.checkExistOrNot(workOrderStore.get("wo_no"))?String.valueOf(workOrderStore.get("wo_no")):"";
							if(CommonUtils.checkExistOrNot(cp)){
								String email = workOrderPlatformStoreMapper.getEmailById(cp);
								if(CommonUtils.checkExistOrNot(email)){
									toMail=email;
									content = "您收到一个拆分的工单，工单号为【"+woNo+"】，请及时处理！";
								}
							}else if(CommonUtils.checkExistOrNot(cpg)){
								String teamEmail = workOrderPlatformStoreMapper.getTeamEmailById(cpg);
								if(CommonUtils.checkExistOrNot(teamEmail)){
									toMail=teamEmail;
									content = "您的组【"+cpgn+"】接收到一个拆分的工单，工单号为【"+woNo+"】,请及时处理！";
								}
							}
							if(CommonUtils.checkExistOrNot(toMail)){
								MailUtil.sendWorkOrderMail(toMail,"LMIS工单提醒", content);
							}
							
						} catch (Exception e) {
							transactionManager.rollback(status);
							throw new Exception(Constant.RESULT_FAILURE_MSG +"split"+ CommonUtils.getExceptionStack(e));
						}
					}
				}
		} else {
			throw new Exception(Constant.RESULT_FAILURE_MSG +"split"+ Constant.RESULT_FAILURE_REASON_1);
		}
	}
	
	private void updateWorkOrder(Parameter parameter) throws Exception {
		int processDepartment = Integer.parseInt(parameter.getParam().get("process_department").toString());
		final WorkOrderStore wos = workOrderPlatformStoreMapper.getWorkOrderStoreById(parameter.getParam().get("woId").toString());
		// 更新人
		wos.setUpdateBy(parameter.getCurrentAccount().getId().toString());
		// 工单状态
		wos.setWoStatus(Constant.ACTION_SUBMIT.equals(parameter.getParam().get("action"))&&processDepartment!=1 ? 1 : 0);
		// 是否已提交
		wos.setSubmitFlag(Constant.ACTION_SUBMIT.equals(parameter.getParam().get("action")) ? 1 : 0);
		//关联工单
		String woNoStr = CommonUtils.checkExistOrNot(parameter.getParam().get("woNoStr"))?parameter.getParam().get("woNoStr").toString():"";
		Set<String> staffsSet = new HashSet<>();
		if(CommonUtils.checkExistOrNot(woNoStr)){
			String[] woNos = woNoStr.split(",");
			staffsSet = new HashSet<>(Arrays.asList(woNos));
			StringBuffer woNoStrRes = new StringBuffer();
			for(String woNo:staffsSet){
				woNoStrRes.append(woNo+",");
			}
			if(woNoStrRes.length()>1){
				woNoStrRes.deleteCharAt(woNoStrRes.length()-1);
			}
			wos.setRelatedWoNo(woNoStrRes.toString());
		}
		
		// 工单类型
		wos.setWoType(parameter.getParam().get("woType").toString());
		wos.setWoTypeDisplay(workOrderPlatformStoreMapper.getWoTypeName(wos.getWoType()));
		// 异常类型
		wos.setErrorType(parameter.getParam().get("errorType").toString());
		wos.setErrorTypeDisplay(workOrderPlatformStoreMapper.getErrorTypeName(wos.getErrorType()));
		// 问题描述
		wos.setIssueDescription(parameter.getParam().get("issueDescription").toString());
		
		wos.setProcessDepartment(processDepartment);
		wos.setRelatedNumber(CommonUtils.checkExistOrNot(parameter.getParam().get("related_number"))?parameter.getParam().get("related_number").toString():"");
		
		String storeCode = CommonUtils.checkExistOrNot(parameter.getParam().get("problem_store"))?parameter.getParam().get("problem_store").toString():"";
        wos.setProblemStore(storeCode);
        wos.setProblemStoreDisplay(workOrderPlatformStoreMapper.getStoreNameByCode(storeCode));
		
		wos.setTitle(CommonUtils.checkExistOrNot(parameter.getParam().get("title"))?parameter.getParam().get("title").toString():"");
		wos.setOperationSystem(CommonUtils.checkExistOrNot(parameter.getParam().get("operation_system"))?parameter.getParam().get("operation_system").toString():"");
		String exProcTime =  CommonUtils.checkExistOrNot(parameter.getParam().get("expectation_process_time"))?parameter.getParam().get("expectation_process_time").toString():null;
		if(CommonUtils.checkExistOrNot(exProcTime)){
			wos.setExpectationProcessTime(DateUtil.StrToTime(exProcTime));
		}
		// 绑定订单
		List<Map<String, Object>> order = null;
		List<Map<String, Object>> orderDetail = null;
		if(CommonUtils.checkExistOrNot(parameter.getParam().get("orderId[]"))) {
			// 指定绑定订单
			order = workOrderPlatformStoreMapper.listOrderById((String[]) parameter.getParam().get("orderId[]"));
			if(CommonUtils.checkExistOrNot(order)) {
				// 平台订单号
				wos.setPlatformNumber(CommonUtils.checkExistOrNot(order.get(0).get("platform_number")) ? order.get(0).get("platform_number").toString() : "");
				// 绑定订单明细
				orderDetail = new ArrayList<Map<String, Object>>();
				// 运单号
				StringBuffer waybill = new StringBuffer();
				for(int i = 0; i < order.size(); i++) {
					//
					waybill.append(order.get(i).get("waybill"));
					//
					order.get(i).put("id", UUID.randomUUID().toString());
					order.get(i).put("create_by", wos.getCreateBy());
					order.get(i).put("wo_store_id", wos.getId());
					//
					List<Map<String, Object>> tempOrderDetail = workOrderPlatformStoreMapper.listOrderDetailByWaybill(order.get(i).get("waybill").toString());
					for(int j = 0; j < tempOrderDetail.size(); j++) {
						tempOrderDetail.get(j).put("id", UUID.randomUUID().toString());
						tempOrderDetail.get(j).put("create_by", wos.getCreateBy());
						tempOrderDetail.get(j).put("wo_store_id", wos.getId());
						tempOrderDetail.get(j).put("wo_order_id", order.get(i).get("id"));
					}
					orderDetail.addAll(tempOrderDetail);
					if(i + 1 != order.size()) {
						waybill.append(",");
					}
				}
				// 运单号
				wos.setWaybill(waybill.toString());
			}
		}
		if(CommonUtils.checkExistOrNot(parameter.getParam().get("platformNumber"))) {
			// 平台订单号查询
			order = workOrderPlatformStoreMapper.listOrderByPlatformNumberOrWaybill(parameter);
			// 平台订单号
			wos.setPlatformNumber(parameter.getParam().get("platformNumber").toString());
			if(CommonUtils.checkExistOrNot(order)) {
				// 绑定订单明细
				orderDetail = new ArrayList<Map<String, Object>>();
				// 运单号
				StringBuffer waybill = new StringBuffer();
				for(int i = 0; i < order.size(); i++) {
					//
					waybill.append(order.get(i).get("waybill"));
					//
					order.get(i).put("id", UUID.randomUUID().toString());
					order.get(i).put("create_by", wos.getCreateBy());
					order.get(i).put("wo_store_id", wos.getId());
					//
					List<Map<String, Object>> tempOrderDetail = workOrderPlatformStoreMapper.listOrderDetailByWaybill(order.get(i).get("waybill").toString());
					for(int j = 0; j < tempOrderDetail.size(); j++) {
						tempOrderDetail.get(j).put("id", UUID.randomUUID().toString());
						tempOrderDetail.get(j).put("create_by", wos.getCreateBy());
						tempOrderDetail.get(j).put("wo_store_id", wos.getId());
						tempOrderDetail.get(j).put("wo_order_id", order.get(i).get("id"));
					}
					orderDetail.addAll(tempOrderDetail);
					if(i + 1 != order.size()) {
						waybill.append(",");
					}
				}
				// 运单号
				wos.setWaybill(waybill.toString());
			} else {
				if(CommonUtils.checkExistOrNot(parameter.getParam().get("waybill"))) {
					wos.setWaybill(parameter.getParam().get("waybill").toString());
				} else {
					wos.setWaybill(Constant.DEFAULT_WAYBILL);
				}
			}
		}
		wos.setAccessory(parameter.getParam().get("accessory").toString());
		wos.setVersion(Integer.parseInt(parameter.getParam().get("version").toString()));
		//
		WoProcessLog wpl = null;
		WorkOrder wo = null;
		if(Constant.ACTION_SUBMIT.equals(parameter.getParam().get("action"))) {
			if(processDepartment==0){
				// 当前处理人
				wos.setCurrentProcessor(wos.getCreateBy());
				wos.setCurrentProcessorDisplay(wos.getCreateByDisplay());
				// 当前处理组别
				wos.setCurrentProcessorGroup(wos.getCreateByGroup());
				wos.setCurrentProcessorGroupDisplay(wos.getCreateByGroupDisplay());
				// 物流工单
				wo = getWorkOrder(wos);
			}
			//当前负责人
			wos.setOwner(wos.getCreateBy());
			wos.setOwnerDisplay(wos.getCreateByDisplay());
			//当前负责组别
			wos.setOwnerGroup(wos.getCreateByGroup());
			wos.setOwnerGroupDisplay(wos.getCreateByGroupDisplay());
			
			Map<String, Object> currentroup = workOrderPlatformStoreMapper.getStoreById(wos.getCreateByGroup());
			if("1".equals(currentroup.get("groupType").toString())){
				wos.setCurrentProcessor(wos.getCreateBy());
				wos.setCurrentProcessorDisplay(wos.getCreateByDisplay());
				wos.setCurrentProcessorGroup(wos.getCreateByGroup());
				wos.setCurrentProcessorGroupDisplay(wos.getCreateByGroupDisplay());
			}
			// 最新处理时间
			wos.setLastProcessTime(new Date());
			// 工单日志
			final WorkOrderStore Wos = workOrderPlatformStoreMapper.getWorkOrderStoreById(parameter.getParam().get("woId").toString());
			String groupid=Wos.getCreateByGroup();
			//wpl = getSubmitWoProcessLog(wos,processDepartment,workOrderPlatformStoreMapper.getDepartmentByGroup(parameter.getCurrentAccount().getTeam_id()));
			Map<String, Object> groupMap = groupMapper.getGroupTypeById(groupid);
			String grouType = groupMap.get("group_type").toString();
			if(grouType.equals("0")) {
				wpl = getSubmitWoProcessLog(wos,processDepartment,workOrderPlatformStoreMapper.getDepartmentByGroup(groupid));
			}else {
				wpl = getSubmitWoProcessLog(wos,processDepartment,"销售运营部");
			}
		}
		// 手动提交事务
		WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		//
		try {
			//
			if(workOrderPlatformStoreMapper.updateWorkOrder(wos) == 1) {
				if(Integer.parseInt(parameter.getParam().get("orderFlag").toString()) == 1) {
					workOrderPlatformStoreMapper.delWoOrderByWoId(wos.getId());
					workOrderPlatformStoreMapper.delWoOrderDetailByWoId(wos.getId());
				}
				if(CommonUtils.checkExistOrNot(order)) {
					workOrderPlatformStoreMapper.addWoOrder(order);
					if(CommonUtils.checkExistOrNot(orderDetail)) {
						workOrderPlatformStoreMapper.addWoOrderDetail(orderDetail);
					}
				}
				if(CommonUtils.checkExistOrNot(wpl)) {
					workOrderPlatformStoreMapper.addWorkOrderProcessLog(wpl);
				}
				if(CommonUtils.checkExistOrNot(wo)) {
					// 新增工单
					Date now = new Date();
					wo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
					// 工单事件监控
					WorkOrderEventMonitor woem= new WorkOrderEventMonitor();
					woem.setCreate_by(wo.getCreateBy());
					woem.setWo_id(wo.getId());
					// 创建
					woem.setId(UUID.randomUUID().toString());
					woem.setSort(1);
					woem.setEvent(Constant.WO_EVENT_CREATED);
					woem.setEvent_status(true);
					woem.setEvent_description(WorkOrderUtils.generateEventRemark(woem, null));
					woem.setRemark(
						new StringBuffer("初始工单级别：")
						.append(workOrderManagementMapper.getLevelName(wo.getWoLevel()))
						.append("；{平台订单号：")
						.append(wos.getPlatformNumber())
						.append("/快递单号：")
						.append(wos.getWaybill())
						.append("}").toString()
						);
					workOrderManagementMapper.addWorkOrderEvent(woem);
					// 获取工单类型工单级别对应工时
					Map<String, Object> hours= workOrderManagementMapper.gethours(wo.getWoType(), wo.getWoLevel());
					// 标准工时
					wo.setStandardManhours(new BigDecimal(hours.get("wk_standard").toString()));
					// 计划完成时间
					Calendar cal = Calendar.getInstance();
					cal.setTime(now);
					cal.add(Calendar.MINUTE, Integer.parseInt(hours.get("wk_plan").toString()));
					wo.setEstimatedTimeOfCompletion(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
					// 计划完成时间
					woem.setId(UUID.randomUUID().toString());
					woem.setSort(2);
					woem.setEvent(Constant.WO_EVENT_ETC);
					// 事件状态已为成功
					woem.setEvent_description(WorkOrderUtils.generateEventRemark(woem, null));
					woem.setRemark("计划完成时间：" + wo.getEstimatedTimeOfCompletion());
					workOrderManagementMapper.addWorkOrderEvent(woem);
					// 下一级升级时间
					cal.setTime(now);
					cal.add(Calendar.MINUTE, Integer.parseInt(hours.get("wk_timeout").toString()));
					wo.setCal_date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
					workOrderManagementMapper.add(wo);
					// 附件关联
					if(CommonUtils.checkExistOrNot(wo.getFileName_common())) {
						// 添加附件关联
						Operation op= new Operation();
						op.setId(UUID.randomUUID().toString());
						op.setCreate_by(wo.getCreateBy());
						op.setWo_id(wo.getId());
						op.setWo_type(wo.getWoType());
						op.setColumn_code("fileName");
						op.setColumn_value(wo.getFileName_common());
						workOrderManagementMapper.addOperation(op);
					}
				}
			} else {
				new Exception("操作过期");
			}
			if(wos.getSubmitFlag()==1){
				//添加工单关联
				for(String woNo:staffsSet){
					if(CommonUtils.checkExistOrNot(woNo)){
						WoAssociation woAssociation = new WoAssociation();
						woAssociation.setWoNoP(wos.getWoNo());
						woAssociation.setWoNoS(woNo);
						woAssociation.setCreateBy(parameter.getCurrentAccount().getId().toString());
						woAssociation.setUpdateBy(parameter.getCurrentAccount().getId().toString());
						woAssociationMapper.insert(woAssociation);
					}
				}
			}
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new Exception(Constant.RESULT_FAILURE_MSG +"update"+ CommonUtils.getExceptionStack(e));
		}
		if(Constant.ACTION_SUBMIT.equals(parameter.getParam().get("action"))) {
			if(processDepartment==0){
					Visual visual=new Visual();
					visual.addObserver(Observers.getInstance());
					visual.setData(wo.getId());
			}else{
				String RedisIds = RedisUtils.get(1,AUTO_GROUP_WORK);
				if(CommonUtils.checkExistOrNot(RedisIds)) {
					List<String> woIdList = JSONArray.parseArray(RedisIds,String.class);
					woIdList.add(wos.getId());
					RedisUtils.set(1,AUTO_GROUP_WORK, JSON.toJSONString(woIdList));
					
				}else {
					ArrayList<String> woIdList=new ArrayList<>();
					woIdList.add(wos.getId());
					RedisUtils.set(1,AUTO_GROUP_WORK, JSON.toJSONString(woIdList));
				}
				
			}
				/*//  (自动分配)
				new Thread(new Runnable(){
					@Override
					public void run(){
						try {
							*/
						 /*catch (Exception e) {
							e.printStackTrace();
							logger.error(e);
						}
					}
				}).start();
			}*/
		}
	}
	
	private WoProcessLog getSubmitWoProcessLog(WorkOrderStore wos,int processDepartment,String department) {
		WoProcessLog wpl = new WoProcessLog();
		wpl.setId(UUID.randomUUID().toString());
		wpl.setCreateBy(wos.getCreateBy());
		wpl.setCreateByDisplay(wos.getCreateByDisplay());
		wpl.setCreateByGroup(wos.getCreateByGroup());
		wpl.setCreateByGroupDisplay(wos.getCreateByGroupDisplay());
		wpl.setWoStoreId(wos.getId());
		wpl.setWoLocationNode(Constant.ST_NODE);
		wpl.setAction(Constant.ACTION_SUBMIT);
		if(processDepartment==0){
			wpl.setLog(
					sdf.format(Calendar.getInstance().getTime())
					+ "<br/>"
					+ (CommonUtils.checkExistOrNot(department)?department:"没有此部门")
					+ "/"
					+ wos.getCreateByGroupDisplay()
					+ "/"
					+ wos.getCreateByDisplay()
					+ "<br/>提交工单至物流中心"
					);
		}else{
			wpl.setLog(
					sdf.format(Calendar.getInstance().getTime())
					+ "<br/>"
					+ (CommonUtils.checkExistOrNot(department)?department:"没有此部门")
					+ "/"
					+ wos.getCreateByGroupDisplay()
					+ "/"
					+ wos.getCreateByDisplay()
					+ "<br/>提交工单至销售运营"
					);
		}
		wpl.setProcessed(1);
		wpl.setSort(System.currentTimeMillis());
		return wpl;
	}
	
	private WorkOrder getWorkOrder(WorkOrderStore wos) throws Exception {
		WorkOrder wo = new WorkOrder();
		// 工单ID
		wo.setId(UUID.randomUUID().toString());
		// 工单号
		wo.setWoNo(wos.getWoNo());
		//
		wo.setCreateBy(wos.getCreateBy());
		wo.setCreateByDisplay(wos.getCreateByDisplay());
		// 工单来源
		wo.setWoSource(Constant.WO_SOURCE_ST);
		// 工单分配状态
		wo.setWoAllocStatus(Constant.WO_ALLOC_STATUS_WAA);
		wo.setWoAllocStatusDisplay(endueDisplay(Constant.WO_ALLOC_STATUS_WAA));
		// 工单处理状态
		wo.setWoProcessStatus(Constant.WO_PROCESS_STATUS_NEW);
		wo.setWoProcessStatusDisplay(endueDisplay(Constant.WO_PROCESS_STATUS_NEW));
		// 工单类型
		wo.setWoType(wos.getWoType());
		wo.setWoTypeDisplay(wos.getWoTypeDisplay());
		// 工单级别
		Map<String, Object> level = workOrderPlatformStoreMapper.getInitialLevel(wo.getWoType());
		//不为空
		if(!CommonUtils.checkExistOrNot(level)){
			throw new Exception("工单级别为空，可能该工单类型级别没有维护");
		}
		wo.setWoLevel(level.get("code").toString());
		wo.setWoLevelDisplay(level.get("name").toString());
		// 异常类型
		wo.setExceptionType(wos.getErrorTypeDisplay());
		// 查询人
		wo.setQueryPerson(wos.getCurrentProcessorDisplay());
		// 快递单号
		wo.setExpressNumber(wos.getWaybill().split(",")[0]);
		// 按运单号查询数据
		Map<String, Object> data = workOrderManagementMapper.getByExpressNumber(wo.getExpressNumber());
		if(CommonUtils.checkExistOrNot(data)) {
			// 物流承运商
			wo.setCarriers(CommonUtils.checkExistOrNot(data.get("transport_code")) ? data.get("transport_code").toString() : "");
			wo.setCarriersDisplay(CommonUtils.checkExistOrNot(data.get("transport_name")) ? data.get("transport_name").toString() : "");
			// 发货仓库
			wo.setWarehouses(CommonUtils.checkExistOrNot(data.get("warehouse_code")) ? data.get("warehouse_code").toString() : "");
			wo.setWarehousesDisplay(CommonUtils.checkExistOrNot(data.get("warehouse_name")) ? data.get("warehouse_name").toString() : "");
			// 出库时间
			wo.setTransportTime(CommonUtils.checkExistOrNot(data.get("transport_time")) ? data.get("transport_time").toString() : "");
			// 平台订单号
			wo.setPlatformNumber(CommonUtils.checkExistOrNot(data.get("delivery_order")) ? data.get("delivery_order").toString() : "");
			// 相关单据号
			wo.setRelatedNumber(CommonUtils.checkExistOrNot(data.get("epistatic_order")) ? data.get("epistatic_order").toString() : "");
			// 店铺
			wo.setStores(CommonUtils.checkExistOrNot(data.get("store_code")) ? data.get("store_code").toString() : "");
			wo.setStoresDisplay(CommonUtils.checkExistOrNot(data.get("store_name")) ? data.get("store_name").toString() : "");
			// 订单金额
			wo.setOrderAmount(CommonUtils.checkExistOrNot(data.get("order_amount")) ? data.get("order_amount").toString() : "");
			// 收件人
			wo.setRecipient(CommonUtils.checkExistOrNot(data.get("shiptoname")) ? data.get("shiptoname").toString() : "");
			// 联系电话
			wo.setPhone(CommonUtils.checkExistOrNot(data.get("phone")) ? data.get("phone").toString() : "");
			// 联系地址
			wo.setAddress(CommonUtils.checkExistOrNot(data.get("detail_address")) ? data.get("detail_address").toString() : "");
		} else {
			// 物流承运商
			wo.setCarriers("default_carrier");
			wo.setCarriersDisplay("默认物流商");
			// 发货仓库
			wo.setWarehouses("default_warehouse");
			wo.setWarehousesDisplay("默认仓库");
			// 默认发货时间
			wo.setTransportTime(sdf.format(new Date()));
			// 平台订单号
			wo.setPlatformNumber(wos.getPlatformNumber());
			// 相关单据号
			wo.setRelatedNumber("0");
			// 店铺
			wo.setStores("default_store");
			wo.setStoresDisplay("默认店铺");
			//
			wo.setOrderAmount("0");
			//
			wo.setRecipient("默认收件人");
			//
			wo.setPhone("默认联系方式");
			//
			wo.setAddress("默认联系地址");
		}
		// 主要处理内容说明
		wo.setSupplementExplain(wos.getIssueDescription());
		// 附件
		wo.setFileName_common(wos.getAccessory());
		return wo;
	}
	
	/**
	 * @Title: forwardWorkOrder 转发
	 * @param parameter
	 * @throws Exception
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月29日 下午6:52:01
	 */
	private int forwardWorkOrder(Parameter parameter) throws Exception {
		String teamId2 = parameter.getCurrentAccount().getTeamId();
		String[] split = teamId2.split(",");
		Map<String,Object> group = workOrderPlatformStoreMapper.getStoreById(split[0]);
		// 是否组内共享
		int ifShare= Integer.parseInt(group.get("ifShare").toString());
		String groupCode = split[0];
		String groupName = CommonUtils.checkExistOrNot(group.get("groupName"))?group.get("groupName").toString():"";
		String groupType = CommonUtils.checkExistOrNot(group.get("groupType"))?group.get("groupType").toString():"";
		String woTypeRe = CommonUtils.checkExistOrNot(parameter.getParam().get("woTypeRe"))?parameter.getParam().get("woTypeRe").toString():null;
		String woTypeReDisplay = null;
		if(CommonUtils.checkExistOrNot(woTypeRe)) {
			woTypeReDisplay = CommonUtils.checkExistOrNot(parameter.getParam().get("woTypeReDisplay"))?parameter.getParam().get("woTypeReDisplay").toString():"";
		} else {
			woTypeReDisplay = null;
		}
		String errorTypeRe = CommonUtils.checkExistOrNot(parameter.getParam().get("errorTypeRe"))?parameter.getParam().get("errorTypeRe").toString():null;
        String errorTypeReDisplay = null;
        if(CommonUtils.checkExistOrNot(errorTypeRe)) {
            errorTypeReDisplay = CommonUtils.checkExistOrNot(parameter.getParam().get("errorTypeReDisplay"))?parameter.getParam().get("errorTypeReDisplay").toString():"";
        }else{
            errorTypeReDisplay = null;
        }
		int result = 0;
		if(CommonUtils.checkExistOrNot(parameter.getParam().get("woId"))) {
			JSONArray woIds =  JSONObject.parseArray(parameter.getParam().get("woId").toString());
			for(int i = 0; i < woIds.size(); i++) {
				if(CommonUtils.checkExistOrNot(woIds.getJSONObject(i).getString("woId")) &&  CommonUtils.checkExistOrNot(parameter.getParam().get("employeeId"))) {
					WorkOrderStore currentWos = workOrderPlatformStoreMapper.getWorkOrderStoreById(woIds.getJSONObject(i).getString("woId"));
					if(currentWos.getWoStatus() == 0&&currentWos.getSubmitFlag()==1&&currentWos.getCurrentProcessor()!=null) {
						// 未处理
						if(Integer.parseInt(currentWos.getCurrentProcessor()) != parameter.getCurrentAccount().getId() && ifShare != 1&&groupType.equals("0")) {
							// 当前处理人不是自己且不是组内共享
							continue;
						}
						// 被转发人ID
						String employeeId = (String) parameter.getParam().get("employeeId");
						//被转发组
						String teamId = (String) parameter.getParam().get("groupId");
						// 1.更新工单主表
						// 当前处理人更新为被转发人[Id,Name]
						// 被转发人名
						String employeeName=workOrderPlatformStoreMapper.getEmpNameById(Integer.parseInt(employeeId));
						//被转发组名
						String teamName = workOrderPlatformStoreMapper.getGroupNameById(teamId);
						//
						//若为-1 ，则选
						if(employeeId.equals("-1")) {
							employeeId="";
							employeeName="";
						}
						WorkOrderStore wos=new WorkOrderStore();
						wos.setId(woIds.getJSONObject(i).getString("woId"));
						wos.setUpdateTime(new Date());
						wos.setUpdateBy(String.valueOf(parameter.getCurrentAccount().getId()));
						wos.setLastProcessTime(new Date());
						wos.setLastProcessInfo(CommonUtils.checkExistOrNot(parameter.getParam().get("processInfo")) ? parameter.getParam().get("processInfo").toString() : "");
						wos.setVersion(Integer.valueOf(woIds.getJSONObject(i).getString("version")));
						//更改状态为未跟进
						wos.setFollowUpFlag(0);
						wos.setErrorFlag(CommonUtils.checkExistOrNot(parameter.getParam().get("errorFlag"))?Integer.parseInt(parameter.getParam().get("errorFlag").toString()):null);
						wos.setWoTypeRe(woTypeRe);
						wos.setWoTypeReDisplay(woTypeReDisplay);
						if(CommonUtils.checkExistOrNot(woTypeRe)){
						    wos.setWoType(woTypeRe);
						    wos.setWoTypeDisplay(woTypeReDisplay);
						}
						if(CommonUtils.checkExistOrNot(errorTypeRe)){
						    wos.setErrorType(errorTypeRe);
						    wos.setErrorTypeDisplay(errorTypeReDisplay);
						}
						if(Integer.parseInt(currentWos.getOwner())==parameter.getCurrentAccount().getId()&&groupType.equals("0")){
							wos.setOwner(employeeId);
							wos.setOwnerDisplay(employeeName);
							wos.setOwnerGroup(groupCode);
							wos.setOwnerGroupDisplay(groupName);
							wos.setCurrentProcessor(employeeId);
							wos.setCurrentProcessorDisplay(employeeName);
							wos.setCurrentProcessorGroup(groupCode);
							wos.setCurrentProcessorGroupDisplay(groupName);
						}else{
							wos.setCurrentProcessor(employeeId);
							wos.setCurrentProcessorDisplay(employeeName);
							wos.setCurrentProcessorGroup(teamId);
							wos.setCurrentProcessorGroupDisplay(teamName);
						}
						// 手动提交事务
						WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
						DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
						DefaultTransactionDefinition def = new DefaultTransactionDefinition();
						def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
						TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
						try {
							if(workOrderPlatformStoreMapper.updateWoStoreMaster(wos) == 1) {
								// 2.新增日志信息
								// 当前操作人组ID获取组信息
								WoProcessLog woProcessLog=new WoProcessLog();
								woProcessLog.setId(UUID.randomUUID().toString());
								woProcessLog.setCreateTime(new Date());
								woProcessLog.setCreateBy(parameter.getCurrentAccount().getId().toString());
								woProcessLog.setCreateByDisplay(parameter.getCurrentAccount().getName());
								//woProcessLog.setCreateByGroup(parameter.getCurrentAccount().getTeam_id());
								woProcessLog.setCreateByGroup(split[0]);
								
								woProcessLog.setCreateByGroupDisplay(groupName);
								woProcessLog.setUpdateTime(new Date());
								woProcessLog.setUpdateBy(parameter.getCurrentAccount().getId().toString());
								woProcessLog.setWoStoreId(wos.getId());
								woProcessLog.setWoLocationNode(Constant.ST_NODE);
								woProcessLog.setAction(Constant.ACTION_FORWARD);
								if(groupType.equals("0")){
									String department = workOrderPlatformStoreMapper.getDepartmentByGroup(split[0]);
									//String department = workOrderPlatformStoreMapper.getDepartmentByGroup(parameter.getCurrentAccount().getTeam_id());
									String log = sdf.format(new Date()) + "<br/>"+(CommonUtils.checkExistOrNot(department) ? department : "[没有此部门]")+ "/"+
		                                            (CommonUtils.checkExistOrNot(groupName) ? groupName.toString() : "[组名加载失败]") + "/" + parameter.getCurrentAccount().getName() + "<br/>将工单转发给<br/>" + employeeName;
									if(CommonUtils.checkExistOrNot(woTypeRe)){
									    log = log + "<br/>将工单类型（"+currentWos.getWoTypeDisplay()+"）"+"修改成（"+woTypeReDisplay+"）";
			                        }
									if(CommonUtils.checkExistOrNot(errorTypeRe)){
									    log = log + "<br/>将工单子类型（"+currentWos.getErrorTypeDisplay()+"）"+"修改成（"+errorTypeReDisplay+"）";
			                        }
									woProcessLog.setLog(log);
								}
								if(groupType.equals("1")){
									if(employeeName.equals("")) {
									    
									    String log = sdf.format(new Date()) + "<br/>"+"销售运营部"+ "/"+
		                                                (CommonUtils.checkExistOrNot(groupName) ? groupName.toString() : "[组名加载失败]") + "/" + parameter.getCurrentAccount().getName() + "<br/>将工单转发给<br/>" + teamName;
	                                    if(CommonUtils.checkExistOrNot(woTypeRe)){
	                                        log = log + "<br/>将工单类型（"+currentWos.getWoTypeDisplay()+"）"+"修改成（"+woTypeReDisplay+"）";
	                                    }
	                                    if(CommonUtils.checkExistOrNot(errorTypeRe)){
	                                        log = log + "<br/>将工单子类型（"+currentWos.getErrorTypeDisplay()+"）"+"修改成（"+errorTypeReDisplay+"）";
	                                    }
										woProcessLog.setLog(log);
									}else {
									    String log = sdf.format(new Date()) + "<br/>"+"销售运营部"+ "/"+
		                                                (CommonUtils.checkExistOrNot(groupName) ? groupName.toString() : "[组名加载失败]") + "/" + parameter.getCurrentAccount().getName() + "<br/>将工单转发给<br/>" + teamName+"/"+employeeName;
									    if(CommonUtils.checkExistOrNot(woTypeRe)){
                                            log = log + "<br/>将工单类型（"+currentWos.getWoTypeDisplay()+"）"+"修改成（"+woTypeReDisplay+"）";
                                        }
                                        if(CommonUtils.checkExistOrNot(errorTypeRe)){
                                            log = log + "<br/>将工单子类型（"+currentWos.getErrorTypeDisplay()+"）"+"修改成（"+errorTypeReDisplay+"）";
                                        }
										woProcessLog.setLog(log);
									}
								}
								woProcessLog.setProcessed(1);
								woProcessLog.setSort(System.currentTimeMillis());
								woProcessLog.setRecieveBy(employeeId);
								woProcessLog.setRecieveByDisplay(employeeName);
								woProcessLog.setRecieveByGroup(teamId);
								woProcessLog.setRecieveByGroupDisplay(teamName);
								workOrderPlatformStoreMapper.addStoreProcessLog(woProcessLog);
								// 3.新增处理记录信息
								WoProcessRecord recode = new WoProcessRecord();
								recode.setId(UUID.randomUUID().toString());
								recode.setCreateTime(new Date());
								recode.setCreateBy(parameter.getCurrentAccount().getId().toString());
								recode.setUpdateTime(new Date());
								recode.setUpdateBy(parameter.getCurrentAccount().getId().toString());
								recode.setWoStoreLogId(woProcessLog.getId());
								recode.setProcessRemark(wos.getLastProcessInfo());
								recode.setAccessory(CommonUtils.checkExistOrNot(parameter.getParam().get("accessory")) ? parameter.getParam().get("accessory").toString() : "");
								recode.setWoTypeRe(woTypeRe);
								workOrderPlatformStoreMapper.addProcessRecord(recode);
							} else {
								continue;
							}
							transactionManager.commit(status);
							
							Map<String,Object> workOrderStore = workOrderPlatformStoreMapper.selectMasterById(wos.getId());
							String toMail = "";
							String content = "";
							String cp = CommonUtils.checkExistOrNot(workOrderStore.get("current_processor"))?String.valueOf(workOrderStore.get("current_processor")):"";
							String cpg = CommonUtils.checkExistOrNot(workOrderStore.get("current_processor_group"))?String.valueOf(workOrderStore.get("current_processor_group")):"";
							String cpgn = CommonUtils.checkExistOrNot(workOrderStore.get("current_processor_group_display"))?String.valueOf(workOrderStore.get("current_processor_group_display")):"";
							String woNo = CommonUtils.checkExistOrNot(workOrderStore.get("wo_no"))?String.valueOf(workOrderStore.get("wo_no")):"";
							if(CommonUtils.checkExistOrNot(cp)){
								String email = workOrderPlatformStoreMapper.getEmailById(cp);
								if(CommonUtils.checkExistOrNot(email)){
									toMail=email;
									content = "您收到一个转发的工单，工单号为【"+woNo+"】，请及时处理！";
								}
							}else if(CommonUtils.checkExistOrNot(cpg)){
								String teamEmail = workOrderPlatformStoreMapper.getTeamEmailById(cpg);
								if(CommonUtils.checkExistOrNot(teamEmail)){
									toMail=teamEmail;
									content = "您的组【"+cpgn+"】接收到一个转发的工单，工单号为【"+woNo+"】,请及时处理！";
								}
							}
							if(CommonUtils.checkExistOrNot(toMail)){
								MailUtil.sendWorkOrderMail(toMail,"LMIS工单提醒", content);
							}
							
							
						} catch (Exception e) {
							transactionManager.rollback(status);
							throw new Exception(Constant.RESULT_FAILURE_MSG +"forward"+ CommonUtils.getExceptionStack(e) + "；已影响" + result + "条工单记录");
						}
						result++;
					}
				}
			}
		} else {
			throw new Exception(Constant.RESULT_FAILURE_MSG +"forward"+ Constant.RESULT_FAILURE_REASON_1);
		}
		return result;
	}
	
	/**
	 * 修改工单
	 */
	private void updWorkOrder(Parameter parameter) throws Exception {
		if(CommonUtils.checkExistOrNot(parameter.getParam().get("woId"))) {
			// 获取当前回复店铺工单
			final WorkOrderStore currentWos = workOrderPlatformStoreMapper.getWorkOrderStoreById(parameter.getParam().get("woId").toString());
			String woId=currentWos.getId();
			String groupid = groupMapper.queryCurrentGroupByWoId(woId);
			if(CommonUtils.checkExistOrNot(currentWos)) {
				if(currentWos.getWoNo().split("-").length==1){
					List<Map<String,Object>> split_list = getSplitList(currentWos.getWoNo()+"-");
					//主工单
					if(split_list.size()>0){
						throw new Exception("拆分过的工单不能执行修改操作");
					}
				}else{
					throw new Exception("子单不能修改");
				}
				// 更新主表记录
				WorkOrderStore wos = new WorkOrderStore();
				// 店铺工单ID
				wos.setId(currentWos.getId());
				// 更新时间
				wos.setUpdateTime(new Date());
				wos.setOwner(currentWos.getOwner());
				// 更新人
				wos.setUpdateBy(parameter.getCurrentAccount().getId().toString());
				// 跟进状态初始化
				wos.setFollowUpFlag(0);
				wos.setWoType(parameter.getParam().get("wo_type").toString());
				wos.setWoTypeDisplay(parameter.getParam().get("wo_type_display").toString());
				wos.setErrorType(CommonUtils.checkExistOrNot(parameter.getParam().get("error_type"))?parameter.getParam().get("error_type").toString():null);
				wos.setErrorTypeDisplay(workOrderPlatformStoreMapper.getErrorTypeName(wos.getErrorType()));
				wos.setProcessDepartment(Integer.valueOf(parameter.getParam().get("process_department").toString()));
				// 最新处理时间
				wos.setLastProcessTime(new Date());
				// 最新处理意见
				wos.setLastProcessInfo(CommonUtils.checkExistOrNot(parameter.getParam().get("processInfo")) ? parameter.getParam().get("processInfo").toString() : "");
				// 店铺工单版本号
				wos.setVersion(Integer.valueOf(parameter.getParam().get("version").toString()));
				// 当前操作人组别类型
				Integer groupType = Integer.parseInt(workOrderPlatformStoreMapper.getStoreById(groupid).get("groupType").toString());
				// 当前操作人所在组别类型为店铺客服
				if(groupType == 0) {
					// 当处理部门为物流中心时
					if(wos.getProcessDepartment() == 0) {
						// 处理状态-已处理
						wos.setWoStatus(1);
					}
					// 当处理部门为销售运营时
					if(wos.getProcessDepartment() == 1) {
						// 将当前处理人/组清空
						wos.setCurrentProcessor("");
						wos.setCurrentProcessorDisplay("");
						wos.setCurrentProcessorGroup("");
						wos.setCurrentProcessorGroupDisplay("");
					}
				}
				// 当前操作人所在组别类型为销售运营时
				if(groupType == 1) {
					// 将当前处理人/组变为负责人/组
					if(currentWos.getOwner().equals(currentWos.getCurrentProcessor())) {
						wos.setCurrentProcessor("");
						wos.setCurrentProcessorDisplay("");
						wos.setCurrentProcessorGroup("");
						wos.setCurrentProcessorGroupDisplay("");
					}else {
						wos.setCurrentProcessor(currentWos.getOwner());
						wos.setCurrentProcessorDisplay(currentWos.getOwnerDisplay());
						wos.setCurrentProcessorGroup(currentWos.getOwnerGroup());
						wos.setCurrentProcessorGroupDisplay(currentWos.getOwnerGroupDisplay());
					}
				}
				// 是否需要自动分配
				boolean allocFlag = false;
				// 手动提交事务
				WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
				DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
				def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
				TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
				try {
					if (workOrderPlatformStoreMapper.updateWo(wos) == 1) {
						// 处理日志
						WoProcessLog woProcessLog = new WoProcessLog();
						// ID
						woProcessLog.setId(UUID.randomUUID().toString());
						// 创建时间
						woProcessLog.setCreateTime(new Date());
						// 创建人
						woProcessLog.setCreateBy(currentWos.getCurrentProcessor());
						woProcessLog.setCreateByDisplay(currentWos.getCurrentProcessorDisplay());
						// 创建组
						woProcessLog.setCreateByGroup(currentWos.getCurrentProcessorGroup());
						woProcessLog.setCreateByGroupDisplay(currentWos.getCurrentProcessorGroupDisplay());
						// 更新时间
						woProcessLog.setUpdateTime(new Date());
						// 更新人
						woProcessLog.setUpdateBy(currentWos.getCurrentProcessor());
						woProcessLog.setWoStoreId(wos.getId());
						// 动作
						woProcessLog.setAction(Constant.ACTION_REPLY);
						// 日志
						StringBuffer log = new StringBuffer(sdf.format(new Date()));
						// 工单流转
						if(groupType == 0) {
							// 店铺发起
							// 所在节点
							woProcessLog.setWoLocationNode(Constant.ST_NODE);
							// 发起部门
							log.append("<br/>" + workOrderPlatformStoreMapper.getDepartmentByGroup(groupid))
							// 发起组别
							.append("/").append(currentWos.getCurrentProcessorGroupDisplay())
							// 发起者
							.append("/").append(currentWos.getCurrentProcessorDisplay())
							.append("<br/>重新提交工单到");
							if(currentWos.getProcessDepartment() == 0) {
								// 发向物流
								// 查询对应物流工单记录
								WorkOrder currentWo = workOrderPlatformStoreMapper.getWorkOrdersByWoNo(currentWos.getWoNo());
								
								//添加物流工单
								if(!CommonUtils.checkExistOrNot(currentWo)) {
									// 物流工单
									WorkOrder wo = getWorkOrder(currentWos);
									if(CommonUtils.checkExistOrNot(wo)) {
										// 新增工单
										Date now = new Date();
										wo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
										// 工单事件监控
										WorkOrderEventMonitor woem= new WorkOrderEventMonitor();
										woem.setCreate_by(wo.getCreateBy());
										woem.setWo_id(wo.getId());
										// 创建
										woem.setId(UUID.randomUUID().toString());
										woem.setSort(1);
										woem.setEvent(Constant.WO_EVENT_CREATED);
										woem.setEvent_status(true);
										woem.setEvent_description(WorkOrderUtils.generateEventRemark(woem, null));
										woem.setRemark(
											new StringBuffer("初始工单级别：")
											.append(workOrderManagementMapper.getLevelName(wo.getWoLevel()))
											.append("；{平台订单号：")
											.append(wos.getPlatformNumber())
											.append("/快递单号：")
											.append(wos.getWaybill())
											.append("}").toString()
											);
										workOrderManagementMapper.addWorkOrderEvent(woem);
										// 获取工单类型工单级别对应工时
										Map<String, Object> hours= workOrderManagementMapper.gethours(wo.getWoType(), wo.getWoLevel());
										// 标准工时
										wo.setStandardManhours(new BigDecimal(hours.get("wk_standard").toString()));
										// 计划完成时间
										Calendar cal = Calendar.getInstance();
										cal.setTime(now);
										cal.add(Calendar.MINUTE, Integer.parseInt(hours.get("wk_plan").toString()));
										wo.setEstimatedTimeOfCompletion(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
										// 计划完成时间
										woem.setId(UUID.randomUUID().toString());
										woem.setSort(2);
										woem.setEvent(Constant.WO_EVENT_ETC);
										// 事件状态已为成功
										woem.setEvent_description(WorkOrderUtils.generateEventRemark(woem, null));
										woem.setRemark("计划完成时间：" + wo.getEstimatedTimeOfCompletion());
										workOrderManagementMapper.addWorkOrderEvent(woem);
										// 下一级升级时间
										cal.setTime(now);
										cal.add(Calendar.MINUTE, Integer.parseInt(hours.get("wk_timeout").toString()));
										wo.setCal_date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
										workOrderManagementMapper.add(wo);
										// 附件关联
										if(CommonUtils.checkExistOrNot(wo.getFileName_common())) {
											// 添加附件关联
											Operation op= new Operation();
											op.setId(UUID.randomUUID().toString());
											op.setCreate_by(wo.getCreateBy());
											op.setWo_id(wo.getId());
											op.setWo_type(wo.getWoType());
											op.setColumn_code("fileName");
											op.setColumn_value(wo.getFileName_common());
											workOrderManagementMapper.addOperation(op);
										}
										//增加交互日志
										WoMutualLog record = new WoMutualLog();
										record.setAccessory(CommonUtils.checkExistOrNot(wo.getFileName_common()) ? wo.getFileName_common() : "");
										record.setAction(Constant.WO_EVENT_CREATED);
										record.setCreateBy(parameter.getCurrentAccount().getId().toString());
										record.setCreateTime(new Date());
										record.setLog(log.toString());
//										record.setProcessRemark(wos.getLastProcessInfo());
										record.setWoNo(wos.getWoNo());
										woMutualLogMapper.insert(record);
									}
								} else{
									// 接收部门
									log.append("<br/>")
									.append(Constant.WO_NODE_LD)
									// 接收者
									.append("/")
									.append(currentWo.getProcessorDisplay());
									// 物流工单日志
									WorkOrderEventMonitor workOrderEventMonitor = new WorkOrderEventMonitor();
									// ID
									workOrderEventMonitor.setId(UUID.randomUUID().toString());
									// 创建人
									workOrderEventMonitor.setCreate_by(currentWos.getCurrentProcessor());
									// 物流工单ID
									workOrderEventMonitor.setWo_id(currentWo.getId());
									// 排序
									workOrderEventMonitor.setSort((int)System.currentTimeMillis());
									// 事件
									workOrderEventMonitor.setEvent(Constant.ACTION_REPLY);
									// 事件状态-成功
									workOrderEventMonitor.setEvent_status(true);
									// 事件描述
									workOrderEventMonitor.setEvent_description("重新提交");
									// 不计入工时
									workOrderEventMonitor.setOutManhours(true);
									// 相关说明
									StringBuffer remark = new StringBuffer(log);
									remark.append("{").append(Constant.PROCESS_REMARK_TITLE).append(wos.getLastProcessInfo());
									// 需要拼接文件名
									StringBuffer file = new StringBuffer("");
									if(CommonUtils.checkExistOrNot(parameter.getParam().get("accessory"))) {
										String[] fileArray = parameter.getParam().get("accessory").toString().split("_");
										for(int i = 0; i < fileArray.length; i++) {
											if(CommonUtils.checkExistOrNot(fileArray[i])) {
												String[] temp = fileArray[i].split("#");
												file.append(temp[1] + temp[2]);
												if(i + 1 != fileArray.length) {
													file.append("；");
												}
											}
										}
									}
									if(CommonUtils.checkExistOrNot(file.toString())) {
										remark.append("/").append(Constant.ACCESSORY_TITLE).append(file);
									}
									remark.append("}");
									workOrderEventMonitor.setRemark(remark.toString());
									workOrderPlatformStoreMapper.addWorkOrderEvent(workOrderEventMonitor);
									// 物流部工单处理记录
									Operation operation = new Operation();
									operation.setUpdate_by(currentWos.getCurrentProcessor());
									operation.setColumn_value(CommonUtils.checkExistOrNot(parameter.getParam().get("accessory")) ? parameter.getParam().get("accessory").toString() : "");
									operation.setWo_id(workOrderEventMonitor.getWo_id());
									operation.setWo_type(currentWo.getWoType());
									operation.setColumn_code("fileName");
									workOrderPlatformStoreMapper.addOperationFileName(operation);
									// 物流部工单主表更新
									WorkOrder woMaster = new WorkOrder();
									woMaster.setId(workOrderEventMonitor.getWo_id());
									woMaster.setUpdateBy(currentWos.getCurrentProcessor());
									woMaster.setWoProcessStatus(Constant.WO_PROCESS_STATUS_NEW);
									woMaster.setWoProcessStatusDisplay(endueDisplay(woMaster.getWoProcessStatus()));
									woMaster.setProcessStartPoint(null);
									woMaster.setActualManhours(new BigDecimal("0"));
									woMaster.setTerminalTime(null);
									workOrderPlatformStoreMapper.updateWorkOrderLd(woMaster);
									//增加交互日志
									WoMutualLog record = new WoMutualLog();
									record.setAccessory(CommonUtils.checkExistOrNot(parameter.getParam().get("accessory")) ? parameter.getParam().get("accessory").toString() : "");
									record.setAction(Constant.ACTION_REPLY);
									record.setCreateBy(currentWos.getCurrentProcessor());
									record.setCreateTime(new Date());
									record.setLog(log.toString());
									record.setProcessRemark(wos.getLastProcessInfo());
									record.setWoNo(currentWos.getWoNo());
									woMutualLogMapper.insert(record);
								}
							}
							if(currentWos.getProcessDepartment() == 1) {
								// 发向销售运营
								// 接收部门
								log.append("<br/>")
								.append(Constant.WO_NODE_SO)
								// 接收者
								.append("<br/>自动分配");
								allocFlag = true;
							}
						}
						if(groupType == 1) {
							if(currentWos.getOwner().equals(currentWos.getCurrentProcessor())) {
								
								// 所在节点
								woProcessLog.setWoLocationNode(Constant.ST_NODE);
								// 发起部门
								log.append("<br/>" + "销售运营部")
								// 发起组别
								.append("/").append(currentWos.getCurrentProcessorGroupDisplay())
								// 发起者
								.append("/").append(currentWos.getCurrentProcessorDisplay())
								.append("<br/>重新提交工单到");
								
								// 发向销售运营
								// 接收部门
								log.append("<br/>")
								.append(Constant.WO_NODE_SO)
								// 接收者
								.append("<br/>自动分配");
								allocFlag = true;
							}else {
								// 销售运营发起
								// 所在节点
								woProcessLog.setWoLocationNode(Constant.SO_NODE);
								// 发起部门
								log.append("<br/>" + Constant.WO_NODE_SO);
								// 发起组别
								log.append("/").append(currentWos.getCurrentProcessorGroupDisplay())
								// 发起者
								.append("/").append(currentWos.getCurrentProcessorDisplay())
								.append("<br/>重新提交工单到")
								// 接收部门
								.append("<br/>");
								
								Map<String, Object> groupSS = groupMapper.getGroupData(currentWos.getCreateByGroup());
								
								if(groupSS.get("group_type").equals(0)){
									log.append(workOrderPlatformStoreMapper.getDepartmentByGroup(wos.getCurrentProcessorGroup()));
								}else{
									log.append(Constant.WO_NODE_SO);
								}
								
								// 接收者
								log.append("/")
								.append(wos.getCurrentProcessorDisplay());
							}
						}
						woProcessLog.setLog(log.toString());
						// 已处理
						woProcessLog.setProcessed(1);
						// 排序时间戳
						woProcessLog.setSort(System.currentTimeMillis());
						workOrderPlatformStoreMapper.addStoreProcessLog(woProcessLog);
						// 处理记录
						WoProcessRecord woProcessRecord = new WoProcessRecord();
						// ID
						woProcessRecord.setId(UUID.randomUUID().toString());
						// 创建时间
						woProcessRecord.setCreateTime(new Date());
						// 创建人
						woProcessRecord.setCreateBy(currentWos.getCurrentProcessor());
						// 更新时间
						woProcessRecord.setUpdateTime(new Date());
						// 更新人
						woProcessRecord.setUpdateBy(currentWos.getCurrentProcessor());
						// 日志ID
						woProcessRecord.setWoStoreLogId(woProcessLog.getId());
						// 处理意见
						woProcessRecord.setProcessRemark(wos.getLastProcessInfo());
						workOrderPlatformStoreMapper.addProcessRecord(woProcessRecord);
					}
					transactionManager.commit(status);
					
					Map<String,Object> workOrderStore = workOrderPlatformStoreMapper.selectMasterById(wos.getId());
					String toMail = "";
					String content = "";
					String cp = CommonUtils.checkExistOrNot(workOrderStore.get("current_processor"))?String.valueOf(workOrderStore.get("current_processor")):"";
					String cpg = CommonUtils.checkExistOrNot(workOrderStore.get("current_processor_group"))?String.valueOf(workOrderStore.get("current_processor_group")):"";
					String cpgn = CommonUtils.checkExistOrNot(workOrderStore.get("current_processor_group_display"))?String.valueOf(workOrderStore.get("current_processor_group_display")):"";
					String woNo = CommonUtils.checkExistOrNot(workOrderStore.get("wo_no"))?String.valueOf(workOrderStore.get("wo_no")):"";
					if(CommonUtils.checkExistOrNot(cp)){
						String email = workOrderPlatformStoreMapper.getEmailById(cp);
						if(CommonUtils.checkExistOrNot(email)){
							toMail=email;
							content = "您收到一个重新提交的工单，工单号为【"+woNo+"】，请及时处理！";
						}
					}else if(CommonUtils.checkExistOrNot(cpg)){
						String teamEmail = workOrderPlatformStoreMapper.getTeamEmailById(cpg);
						if(CommonUtils.checkExistOrNot(teamEmail)){
							toMail=teamEmail;
							content = "您的组【"+cpgn+"】接收到一个重新提交的工单，工单号为【"+woNo+"】,请及时处理！";
						}
					}
					if(CommonUtils.checkExistOrNot(toMail)){
						MailUtil.sendWorkOrderMail(toMail,"LMIS工单提醒", content);
					}
					
				} catch (Exception e) {
					transactionManager.rollback(status);
					throw new Exception(Constant.RESULT_FAILURE_MSG +"upd"+ CommonUtils.getExceptionStack(e));
				}
				if(allocFlag) {
					
					String RedisIds = RedisUtils.get(1,AUTO_GROUP_WORK);
					if(CommonUtils.checkExistOrNot(RedisIds)) {
						List<String> woIdList = JSONArray.parseArray(RedisIds,String.class);
						woIdList.add(wos.getId());
						RedisUtils.set(1,AUTO_GROUP_WORK, JSON.toJSONString(woIdList));
						
					}else {
						ArrayList<String> woIdList=new ArrayList<>();
						woIdList.add(wos.getId());
						RedisUtils.set(1,AUTO_GROUP_WORK, JSON.toJSONString(woIdList));
					}
				}
			} else {
				throw new Exception(Constant.RESULT_FAILURE_MSG +"upd"+ Constant.RESULT_FAILURE_REASON_2);
			}
		} else {
			throw new Exception(Constant.RESULT_FAILURE_MSG +"upd"+ Constant.RESULT_FAILURE_REASON_1);
		}
	}
	private void replyWorkOrder(Parameter parameter) throws Exception {
		if(CommonUtils.checkExistOrNot(parameter.getParam().get("woId"))) {
			// 获取当前回复店铺工单
			final WorkOrderStore currentWos = workOrderPlatformStoreMapper.getWorkOrderStoreById(parameter.getParam().get("woId").toString());
			String woId=currentWos.getId();
			String groupid = groupMapper.queryCurrentGroupByWoId(woId);
			
			if(CommonUtils.checkExistOrNot(currentWos)) {
				
				if(currentWos.getWoNo().split("-").length==1){
					//主工单
					if(!fatherWoCanProcess(currentWos.getWoNo())){
						throw new Exception("还有子工单没有结束，工单不能操作");
					}
				}else{
					throw new Exception("子单不能回复");
				}
				
				//
				String woTypeRe = CommonUtils.checkExistOrNot(parameter.getParam().get("woTypeRe"))?parameter.getParam().get("woTypeRe").toString():null;
				String woTypeReDisplay = null;
				if(CommonUtils.checkExistOrNot(woTypeRe)) {
					woTypeReDisplay = CommonUtils.checkExistOrNot(parameter.getParam().get("woTypeReDisplay"))?parameter.getParam().get("woTypeReDisplay").toString():"";
				}else{
					woTypeReDisplay = null;
				}
				String errorTypeRe = CommonUtils.checkExistOrNot(parameter.getParam().get("errorTypeRe"))?parameter.getParam().get("errorTypeRe").toString():null;
				String errorTypeReDisplay = null;
                if(CommonUtils.checkExistOrNot(errorTypeRe)) {
                    errorTypeReDisplay = CommonUtils.checkExistOrNot(parameter.getParam().get("errorTypeReDisplay"))?parameter.getParam().get("errorTypeReDisplay").toString():"";
                }else{
                    errorTypeReDisplay = null;
                }
				
				// 更新主表记录
				WorkOrderStore wos = new WorkOrderStore();
				// 店铺工单ID
				wos.setId(currentWos.getId());
				// 更新时间
				wos.setUpdateTime(new Date());
				// 更新人
				wos.setUpdateBy(parameter.getCurrentAccount().getId().toString());
				// 跟进状态初始化
				wos.setFollowUpFlag(0);
				// 是否异常工单
				wos.setErrorFlag(CommonUtils.checkExistOrNot(parameter.getParam().get("errorFlag"))?Integer.parseInt(parameter.getParam().get("errorFlag").toString()):null);
				// 工单类型确认CODE
				wos.setWoTypeRe(woTypeRe);
				// 工单类型确认显示
				wos.setWoTypeReDisplay(woTypeReDisplay);
				
				wos.setWoType(woTypeRe);
				wos.setWoTypeDisplay(woTypeReDisplay);
				
				wos.setErrorType(errorTypeRe);
				wos.setErrorTypeDisplay(errorTypeReDisplay);
				
				// 最新处理时间
				wos.setLastProcessTime(new Date());
				// 最新处理意见
				wos.setLastProcessInfo(CommonUtils.checkExistOrNot(parameter.getParam().get("processInfo")) ? parameter.getParam().get("processInfo").toString() : "");
				// 店铺工单版本号
				wos.setVersion(Integer.valueOf(parameter.getParam().get("version").toString()));
				// 当前操作人组别类型
				Integer groupType = Integer.parseInt(workOrderPlatformStoreMapper.getStoreById(groupid).get("groupType").toString());
				// 当前操作人所在组别类型为店铺客服
				if(groupType == 0) {
					// 当处理部门为物流中心时
					if(currentWos.getProcessDepartment() == 0) {
						// 处理状态-已处理
						wos.setWoStatus(1);
					}
					// 当处理部门为销售运营时
					if(currentWos.getProcessDepartment() == 1) {
						// 将当前处理人/组清空
						wos.setCurrentProcessor("");
						wos.setCurrentProcessorDisplay("");
						wos.setCurrentProcessorGroup("");
						wos.setCurrentProcessorGroupDisplay("");
					}
				}
				// 当前操作人所在组别类型为销售运营时
				if(groupType == 1) {
					// 将当前处理人/组变为负责人/组
					if(currentWos.getOwner().equals(currentWos.getCurrentProcessor())) {
						wos.setCurrentProcessor("");
						wos.setCurrentProcessorDisplay("");
						wos.setCurrentProcessorGroup("");
						wos.setCurrentProcessorGroupDisplay("");
					}else {
						wos.setCurrentProcessor(currentWos.getOwner());
						wos.setCurrentProcessorDisplay(currentWos.getOwnerDisplay());
						wos.setCurrentProcessorGroup(currentWos.getOwnerGroup());
						wos.setCurrentProcessorGroupDisplay(currentWos.getOwnerGroupDisplay());
					}
				}
				// 是否需要自动分配
				boolean allocFlag = false;
				// 手动提交事务
				WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
				DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
				def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
				TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
				try {
					if (workOrderPlatformStoreMapper.updateWoStoreMaster(wos) == 1) {
						// 处理日志
						WoProcessLog woProcessLog = new WoProcessLog();
						// ID
						woProcessLog.setId(UUID.randomUUID().toString());
						// 创建时间
						woProcessLog.setCreateTime(new Date());
						// 创建人
						woProcessLog.setCreateBy(currentWos.getCurrentProcessor());
						woProcessLog.setCreateByDisplay(currentWos.getCurrentProcessorDisplay());
						// 创建组
						woProcessLog.setCreateByGroup(currentWos.getCurrentProcessorGroup());
						woProcessLog.setCreateByGroupDisplay(currentWos.getCurrentProcessorGroupDisplay());
						// 更新时间
						woProcessLog.setUpdateTime(new Date());
						// 更新人
						woProcessLog.setUpdateBy(currentWos.getCurrentProcessor());
						woProcessLog.setWoStoreId(wos.getId());
						// 动作
						woProcessLog.setAction(Constant.ACTION_REPLY);
						// 日志
						StringBuffer log = new StringBuffer(sdf.format(new Date()));
						// 工单流转
						if(groupType == 0) {
							// 店铺发起
							// 所在节点
							woProcessLog.setWoLocationNode(Constant.ST_NODE);
							// 发起部门
							log.append("<br/>" + workOrderPlatformStoreMapper.getDepartmentByGroup(groupid))
							// 发起组别
							.append("/").append(currentWos.getCurrentProcessorGroupDisplay())
							// 发起者
							.append("/").append(currentWos.getCurrentProcessorDisplay())
							.append("<br/>回复");
							if(currentWos.getProcessDepartment() == 0) {
								// 发向物流
								// 查询对应物流工单记录
								WorkOrder currentWo = workOrderPlatformStoreMapper.getWorkOrdersByWoNo(currentWos.getWoNo());
								if(CommonUtils.checkExistOrNot(currentWo)) {
									// 接收部门
									log.append("<br/>")
									.append(Constant.WO_NODE_LD)
									// 接收者
									.append("/")
									.append(currentWo.getProcessorDisplay());
									// 物流工单日志
									WorkOrderEventMonitor workOrderEventMonitor = new WorkOrderEventMonitor();
									// ID
									workOrderEventMonitor.setId(UUID.randomUUID().toString());
									// 创建人
									workOrderEventMonitor.setCreate_by(currentWos.getCurrentProcessor());
									// 物流工单ID
									workOrderEventMonitor.setWo_id(currentWo.getId());
									// 排序
									workOrderEventMonitor.setSort((int)System.currentTimeMillis());
									// 事件
									workOrderEventMonitor.setEvent(Constant.ACTION_REPLY);
									// 事件状态-成功
									workOrderEventMonitor.setEvent_status(true);
									// 事件描述
									workOrderEventMonitor.setEvent_description("回复");
									// 不计入工时
									workOrderEventMonitor.setOutManhours(true);
									// 相关说明
									StringBuffer remark = new StringBuffer(log);
									remark.append("{").append(Constant.PROCESS_REMARK_TITLE).append(wos.getLastProcessInfo());
									// 需要拼接文件名
									StringBuffer file = new StringBuffer("");
									if(CommonUtils.checkExistOrNot(parameter.getParam().get("accessory"))) {
										String[] fileArray = parameter.getParam().get("accessory").toString().split("_");
										for(int i = 0; i < fileArray.length; i++) {
											if(CommonUtils.checkExistOrNot(fileArray[i])) {
												String[] temp = fileArray[i].split("#");
												file.append(temp[1] + temp[2]);
												if(i + 1 != fileArray.length) {
													file.append("；");
												}
											}
										}
									}
									if(CommonUtils.checkExistOrNot(file.toString())) {
										remark.append("/").append(Constant.ACCESSORY_TITLE).append(file);
									}
									remark.append("}");
									workOrderEventMonitor.setRemark(remark.toString());
									workOrderPlatformStoreMapper.addWorkOrderEvent(workOrderEventMonitor);
									// 物流部工单处理记录
									Operation operation = new Operation();
									operation.setUpdate_by(currentWos.getCurrentProcessor());
									operation.setColumn_value(CommonUtils.checkExistOrNot(parameter.getParam().get("accessory")) ? parameter.getParam().get("accessory").toString() : "");
									operation.setWo_id(workOrderEventMonitor.getWo_id());
									operation.setWo_type(currentWo.getWoType());
									operation.setColumn_code("fileName");
									workOrderPlatformStoreMapper.addOperationFileName(operation);
									// 物流部工单主表更新
									WorkOrder woMaster = new WorkOrder();
									woMaster.setId(workOrderEventMonitor.getWo_id());
									woMaster.setUpdateBy(currentWos.getCurrentProcessor());
									woMaster.setWoProcessStatus(Constant.WO_PROCESS_STATUS_NEW);
									woMaster.setWoProcessStatusDisplay(endueDisplay(woMaster.getWoProcessStatus()));
									woMaster.setProcessStartPoint(null);
									woMaster.setActualManhours(new BigDecimal("0"));
									woMaster.setTerminalTime(null);
									workOrderPlatformStoreMapper.updateWorkOrderLd(woMaster);
									//增加交互日志
									WoMutualLog record = new WoMutualLog();
									record.setAccessory(CommonUtils.checkExistOrNot(parameter.getParam().get("accessory")) ? parameter.getParam().get("accessory").toString() : "");
									record.setAction(Constant.ACTION_REPLY);
									record.setCreateBy(currentWos.getCurrentProcessor());
									record.setCreateTime(new Date());
									if(CommonUtils.checkExistOrNot(woTypeRe)){
                                        log = log.append("<br/>将工单类型（"+currentWos.getWoTypeDisplay()+"）"+"修改成（"+woTypeReDisplay+"）");
                                    }
                                    if(CommonUtils.checkExistOrNot(errorTypeRe)){
                                        log = log.append("<br/>将工单子类型（"+currentWos.getErrorTypeDisplay()+"）"+"修改成（"+errorTypeReDisplay+"）");
                                    }
									record.setLog(log.toString());
									record.setProcessRemark(wos.getLastProcessInfo());
									record.setWoNo(currentWos.getWoNo());
									woMutualLogMapper.insert(record);
								} else {
									throw new Exception(Constant.RESULT_FAILURE_MSG+"reply"+Constant.RESULT_FAILURE_REASON_3);
								}
							}
							if(currentWos.getProcessDepartment() == 1) {
								// 发向销售运营
								// 接收部门
								log.append("<br/>")
								.append(Constant.WO_NODE_SO)
								// 接收者
								.append("<br/>自动分配");
								if(CommonUtils.checkExistOrNot(woTypeRe)){
                                    log = log.append("<br/>将工单类型（"+currentWos.getWoTypeDisplay()+"）"+"修改成（"+woTypeReDisplay+"）");
                                }
                                if(CommonUtils.checkExistOrNot(errorTypeRe)){
                                    log = log.append("<br/>将工单子类型（"+currentWos.getErrorTypeDisplay()+"）"+"修改成（"+errorTypeReDisplay+"）");
                                }
								allocFlag = true;
							}
						}
						if(groupType == 1) {
							if(currentWos.getOwner().equals(currentWos.getCurrentProcessor())) {
								
								// 所在节点
								woProcessLog.setWoLocationNode(Constant.ST_NODE);
								// 发起部门
								log.append("<br/>" + "销售运营部")
								// 发起组别
								.append("/").append(currentWos.getCurrentProcessorGroupDisplay())
								// 发起者
								.append("/").append(currentWos.getCurrentProcessorDisplay())
								.append("<br/>回复");
								
								// 发向销售运营
								// 接收部门
								log.append("<br/>")
								.append(Constant.WO_NODE_SO)
								// 接收者
								.append("<br/>自动分配");
								if(CommonUtils.checkExistOrNot(woTypeRe)){
                                    log = log.append("<br/>将工单类型（"+currentWos.getWoTypeDisplay()+"）"+"修改成（"+woTypeReDisplay+"）");
                                }
                                if(CommonUtils.checkExistOrNot(errorTypeRe)){
                                    log = log.append("<br/>将工单子类型（"+currentWos.getErrorTypeDisplay()+"）"+"修改成（"+errorTypeReDisplay+"）");
                                }
								allocFlag = true;
							}else {
								// 销售运营发起
								// 所在节点
								woProcessLog.setWoLocationNode(Constant.SO_NODE);
								// 发起部门
								log.append("<br/>" + Constant.WO_NODE_SO);
								// 发起组别
								log.append("/").append(currentWos.getCurrentProcessorGroupDisplay())
								// 发起者
								.append("/").append(currentWos.getCurrentProcessorDisplay())
								.append("<br/>回复")
								// 接收部门
								.append("<br/>");
								
								Map<String, Object> groupSS = groupMapper.getGroupData(currentWos.getCreateByGroup());
								
								if(groupSS.get("group_type").equals(0)){
									log.append(workOrderPlatformStoreMapper.getDepartmentByGroup(wos.getCurrentProcessorGroup()));
								}else{
									log.append(Constant.WO_NODE_SO);
								}
								
								// 接收者
								log.append("/")
								.append(wos.getCurrentProcessorDisplay());
								if(CommonUtils.checkExistOrNot(woTypeRe)){
                                    log = log.append("<br/>将工单类型（"+currentWos.getWoTypeDisplay()+"）"+"修改成（"+woTypeReDisplay+"）");
                                }
                                if(CommonUtils.checkExistOrNot(errorTypeRe)){
                                    log = log.append("<br/>将工单子类型（"+currentWos.getErrorTypeDisplay()+"）"+"修改成（"+errorTypeReDisplay+"）");
                                }
							}
						}
						woProcessLog.setLog(log.toString());
						// 已处理
						woProcessLog.setProcessed(1);
						// 排序时间戳
						woProcessLog.setSort(System.currentTimeMillis());
						//TODO
//						woProcessLog.setRecieveBy(recieveBy);
//						woProcessLog.setRecieveByDisplay(recieveByDisplay);
//						woProcessLog.setRecieveByGroup(recieveByGroup);
//						woProcessLog.setRecieveByGroupDisplay(recieveByGroupDisplay);
						workOrderPlatformStoreMapper.addStoreProcessLog(woProcessLog);
						// 处理记录
						WoProcessRecord woProcessRecord = new WoProcessRecord();
						// ID
						woProcessRecord.setId(UUID.randomUUID().toString());
						// 创建时间
						woProcessRecord.setCreateTime(new Date());
						// 创建人
						woProcessRecord.setCreateBy(currentWos.getCurrentProcessor());
						// 更新时间
						woProcessRecord.setUpdateTime(new Date());
						// 更新人
						woProcessRecord.setUpdateBy(currentWos.getCurrentProcessor());
						// 日志ID
						woProcessRecord.setWoStoreLogId(woProcessLog.getId());
						// 处理意见
						woProcessRecord.setProcessRemark(wos.getLastProcessInfo());
						// 附件
						woProcessRecord.setAccessory(CommonUtils.checkExistOrNot(parameter.getParam().get("accessory")) ? parameter.getParam().get("accessory").toString() : "");
						// 工单类型确认
						woProcessRecord.setWoTypeRe(woTypeRe);
						workOrderPlatformStoreMapper.addProcessRecord(woProcessRecord);
					}
					transactionManager.commit(status);
					
					Map<String,Object> workOrderStore = workOrderPlatformStoreMapper.selectMasterById(wos.getId());
					String toMail = "";
					String content = "";
					String cp = CommonUtils.checkExistOrNot(workOrderStore.get("current_processor"))?String.valueOf(workOrderStore.get("current_processor")):"";
					String cpg = CommonUtils.checkExistOrNot(workOrderStore.get("current_processor_group"))?String.valueOf(workOrderStore.get("current_processor_group")):"";
					String cpgn = CommonUtils.checkExistOrNot(workOrderStore.get("current_processor_group_display"))?String.valueOf(workOrderStore.get("current_processor_group_display")):"";
					String woNo = CommonUtils.checkExistOrNot(workOrderStore.get("wo_no"))?String.valueOf(workOrderStore.get("wo_no")):"";
					if(CommonUtils.checkExistOrNot(cp)){
						String email = workOrderPlatformStoreMapper.getEmailById(cp);
						if(CommonUtils.checkExistOrNot(email)){
							toMail=email;
							content = "您收到一个回复的工单，工单号为【"+woNo+"】，请及时处理！";
						}
					}else if(CommonUtils.checkExistOrNot(cpg)){
						String teamEmail = workOrderPlatformStoreMapper.getTeamEmailById(cpg);
						if(CommonUtils.checkExistOrNot(teamEmail)){
							toMail=teamEmail;
							content = "您的组【"+cpgn+"】接收到一个回复的工单，工单号为【"+woNo+"】,请及时处理！";
						}
					}
					if(CommonUtils.checkExistOrNot(toMail)){
						MailUtil.sendWorkOrderMail(toMail,"LMIS工单提醒", content);
					}
					
				} catch (Exception e) {
					transactionManager.rollback(status);
					throw new Exception(Constant.RESULT_FAILURE_MSG +"reply"+ CommonUtils.getExceptionStack(e));
				}
				if(allocFlag) {

					String RedisIds = RedisUtils.get(1,AUTO_GROUP_WORK);
					if(CommonUtils.checkExistOrNot(RedisIds)) {
						List<String> woIdList = JSONArray.parseArray(RedisIds,String.class);
						woIdList.add(wos.getId());
						RedisUtils.set(1,AUTO_GROUP_WORK, JSON.toJSONString(woIdList));
						
					}else {
						ArrayList<String> woIdList=new ArrayList<>();
						woIdList.add(wos.getId());
						RedisUtils.set(1,AUTO_GROUP_WORK, JSON.toJSONString(woIdList));
					}
					
					/*// 
					new Thread(new Runnable(){
						@Override
						public void run(){
							try {
								*/
							 /*catch (Exception e) {
								e.printStackTrace();
								logger.error(e);
								}
							}
						}).start();
					}*/
				}
			} else {
				throw new Exception(Constant.RESULT_FAILURE_MSG +"reply"+ Constant.RESULT_FAILURE_REASON_2);
			}
		} else {
			throw new Exception(Constant.RESULT_FAILURE_MSG +"reply"+ Constant.RESULT_FAILURE_REASON_1);
		}
	}
	
	/**
	 * @Title: assistWorkOrder 协助
	 * @param parameter
	 * @throws Exception
	 * @return: Integer
	 * @author: Ian.Huang
	 * @date: 2017年9月18日 上午11:44:27
	 */
	private Integer assistWorkOrder(Parameter parameter) throws Exception {
		
		final WorkOrderStore currentWos = workOrderPlatformStoreMapper.getWorkOrderStoreById(parameter.getParam().get("woId").toString());
		//工单的工单号为母单 并且 母单不能处理
		if(currentWos.getWoNo().split("-").length==1 && !fatherWoCanProcess(currentWos.getWoNo())){
			throw new Exception(Constant.RESULT_FAILURE_MSG +"assist"+ "有子工单没有结束，工单不能操作");
		}
		String woId=currentWos.getId();
		Integer resultInt=0;
		String empId=parameter.getCurrentAccount().getId().toString();
		String empName = parameter.getCurrentAccount().getName();
		String groupid = groupMapper.queryCurrentGroupByWoId(woId);
		//String teamId =parameter.getCurrentAccount().getTeam_id().toString();
		String teamId =groupid;
		String ver=parameter.getParam().get("version").toString();
		Integer version=Integer.parseInt(ver);
		Map<String, Object> group = groupMapper.getGroupData(teamId);
		String teamName = CommonUtils.checkExistOrNot(group.get("group_name")) ? group.get("group_name").toString() : "";
		Date date = new Date();
		try {
			if(CommonUtils.checkExistOrNot(woId)) {
					if(CommonUtils.checkExistOrNot(woId) && CommonUtils.checkExistOrNot(version)) {
						// 主表更新
							WorkOrderStore wos = new WorkOrderStore();
							wos.setId(woId);
							wos.setUpdateTime(date);
							wos.setUpdateBy(empId);
							//wos.setWoStatus(0);
							wos.setFollowUpFlag(0);
							wos.setErrorFlag(CommonUtils.checkExistOrNot(parameter.getParam().get("errorFlag"))?Integer.parseInt(parameter.getParam().get("errorFlag").toString()):null);
							wos.setLastProcessTime(date);
							wos.setLastProcessInfo(CommonUtils.checkExistOrNot(parameter.getParam().get("processInfo")) ? parameter.getParam().get("processInfo").toString() : "");
							wos.setVersion(version);
							// 手动提交事务
							WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
							DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
							DefaultTransactionDefinition def = new DefaultTransactionDefinition();
							def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
							TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
							String storeName = workOrderPlatformStoreMapper.getDepartmentByGroup(teamId);
							try {
								if(workOrderPlatformStoreMapper.updateWSMCurrent(wos) == 1) {
									// 日志记录
									// 当前操作人组ID获取组信息
									WoProcessLog woProcessLog = new WoProcessLog();
									woProcessLog.setId(UUID.randomUUID().toString());
									woProcessLog.setCreateTime(date);
									woProcessLog.setCreateBy(empId);
									woProcessLog.setCreateByDisplay(empName);
									woProcessLog.setCreateByGroup(teamId);
									woProcessLog.setCreateByGroupDisplay(teamName);
									woProcessLog.setUpdateTime(date);
									woProcessLog.setUpdateBy(empId);
									woProcessLog.setWoStoreId(wos.getId());
									woProcessLog.setWoLocationNode(Constant.SO_NODE);
									woProcessLog.setAction(Constant.ACTION_ASSIST);
									woProcessLog.setLog((sdf.format(date) + "<br/>"+storeName+"/" + (CommonUtils.checkExistOrNot(teamName) ? teamName : "[组名加载失败]") + "/" + empName + "<br/>向"+"/"+Constant.WO_NODE_SO+"/发起协助").toString());
									woProcessLog.setProcessed(1);
									woProcessLog.setSort(System.currentTimeMillis());
									workOrderPlatformStoreMapper.addStoreProcessLog(woProcessLog);
									
									// 3.新增处理记录信息
									WoProcessRecord recode = new WoProcessRecord();
									recode.setId(UUID.randomUUID().toString());
									recode.setCreateTime(date);
									recode.setCreateBy(empId);
									recode.setUpdateTime(date);
									recode.setUpdateBy(empId);
									recode.setWoStoreLogId(woProcessLog.getId());
									recode.setProcessRemark(wos.getLastProcessInfo());
									recode.setAccessory(CommonUtils.checkExistOrNot(parameter.getParam().get("accessory")) ? parameter.getParam().get("accessory").toString() : "");
									recode.setWoTypeRe(CommonUtils.checkExistOrNot(parameter.getParam().get("woTypeRe"))?parameter.getParam().get("woTypeRe").toString():"");
									workOrderPlatformStoreMapper.addProcessRecord(recode);
									
								} 
								transactionManager.commit(status);
								resultInt++;

								String RedisIds = RedisUtils.get(1,AUTO_GROUP_WORK);
								if(CommonUtils.checkExistOrNot(RedisIds)) {
									List<String> woIdList = JSONArray.parseArray(RedisIds,String.class);
									woIdList.add(wos.getId());
									RedisUtils.set(1,AUTO_GROUP_WORK, JSON.toJSONString(woIdList));
									
								}else {
									ArrayList<String> woIdList=new ArrayList<>();
									woIdList.add(wos.getId());
									RedisUtils.set(1,AUTO_GROUP_WORK, JSON.toJSONString(woIdList));
								}
								
								/*// 
								new Thread(new Runnable(){
									@Override
									public void run(){
										try {
											*/
										 /*catch (Exception e) {
											e.printStackTrace();
											logger.error(e);
										}
									}
								}).start();
							}*/
							} catch (Exception e) {
								transactionManager.rollback(status);
								throw new Exception(Constant.RESULT_FAILURE_MSG +"assist"+ CommonUtils.getExceptionStack(e) + "；已影响" + 1 + "条工单记录");
							}
				}
			} else {
				throw new Exception(Constant.RESULT_FAILURE_MSG +"assist"+ Constant.RESULT_FAILURE_REASON_1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return resultInt;
	}
	
	
	/**
	 * @Title: overWorkOrder 完结
	 * @param parameter
	 * @throws Exception
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年9月14日 上午11:15:55
	 */
	private int overWorkOrder (Parameter parameter) throws Exception {
		String teamId = groupMapper.queryCurrentGroupByWoId(
				JSONObject.parseArray(
						parameter.getParam().get("woId").toString()
						).getJSONObject(0).getString("woId")
				);
		int result = 0;
		if(CommonUtils.checkExistOrNot(parameter.getParam().get("woId"))) {
			JSONArray woIds = JSONObject.parseArray(parameter.getParam().get("woId").toString());
			for(int i = 0; i < woIds.size(); i++) {
				if(CommonUtils.checkExistOrNot(woIds.getJSONObject(i).getString("woId")) && CommonUtils.checkExistOrNot(woIds.getJSONObject(i).getInteger("version"))) {
					WorkOrderStore currentWos = workOrderPlatformStoreMapper.getWorkOrderStoreById(woIds.getJSONObject(i).getString("woId"));
					//工单的工单号为母单 并且 母单不能处理
					if(currentWos.getWoNo().split("-").length==1 && !fatherWoCanProcess(currentWos.getWoNo())){
						continue;
					}
					if(currentWos.getWoStatus() == 0&&CommonUtils.checkExistOrNot(currentWos.getCurrentProcessor())) {
						//子单 不相等  也可以完结
						if(currentWos.getWoNo().split("-").length==1){	
							// 未处理
							if(Integer.parseInt(currentWos.getCurrentProcessor()) != parameter.getCurrentAccount().getId()
									&& Integer.parseInt(workOrderPlatformStoreMapper.getStoreById(teamId).get("ifShare").toString()) != 1) {
								// 当前处理人不是自己且不是组内共享
								continue;
							}
							if(workOrderPlatformStoreMapper.getGroupTypeById(currentWos.getCurrentProcessorGroup())!=
									workOrderPlatformStoreMapper.getGroupTypeById(currentWos.getCreateByGroup())){
								//当前处理组的组别  不等于 创建组的组别
								continue;
							}
						}
						// 主表更新
						WorkOrderStore wos = new WorkOrderStore();
						wos.setId(woIds.getJSONObject(i).getString("woId"));
						wos.setUpdateTime(new Date());
						wos.setUpdateBy(parameter.getCurrentAccount().getId().toString());
						wos.setWoStatus(2);
						wos.setCurrentProcessor(parameter.getCurrentAccount().getId().toString());
						wos.setCurrentProcessorDisplay(parameter.getCurrentAccount().getName());
						wos.setLastProcessTime(new Date());
						wos.setLastProcessInfo(CommonUtils.checkExistOrNot(parameter.getParam().get("processInfo")) ? parameter.getParam().get("processInfo").toString() : "");
						wos.setOverTime(new Date());
						wos.setVersion(woIds.getJSONObject(i).getInteger("version"));
						wos.setFollowUpFlag(0);
						wos.setErrorFlag(CommonUtils.checkExistOrNot(parameter.getParam().get("errorFlag"))?Integer.parseInt(parameter.getParam().get("errorFlag").toString()):null);
						// 手动提交事务
						WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
						DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
						DefaultTransactionDefinition def = new DefaultTransactionDefinition();
						def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
						TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
						try {
							if(workOrderPlatformStoreMapper.updateWoStoreMaster(wos) == 1) {
								// 日志记录
								// 当前操作人组ID获取组信息
								//Map<String, Object> group = workOrderPlatformStoreMapper.getStoreById(parameter.getCurrentAccount().getTeam_id());
								Map<String, Object> group = workOrderPlatformStoreMapper.getStoreById(teamId);
								
								WoProcessLog woProcessLog = new WoProcessLog();
								woProcessLog.setId(UUID.randomUUID().toString());
								woProcessLog.setCreateTime(new Date());
								woProcessLog.setCreateBy(parameter.getCurrentAccount().getId().toString());
								woProcessLog.setCreateByDisplay(parameter.getCurrentAccount().getName());
								woProcessLog.setCreateByGroup(teamId);
								//woProcessLog.setCreateByGroup(parameter.getCurrentAccount().getTeam_id());
								woProcessLog.setCreateByGroupDisplay(CommonUtils.checkExistOrNot(group.get("groupName")) ? group.get("groupName").toString() : "");
								woProcessLog.setUpdateTime(new Date());
								//woProcessLog.setUpdateBy(parameter.getCurrentAccount().getTeam_id());
								woProcessLog.setUpdateBy(teamId);
								woProcessLog.setWoStoreId(wos.getId());
								woProcessLog.setWoLocationNode(Constant.ST_NODE);
								woProcessLog.setAction(Constant.ACTION_OVER);
								woProcessLog.setLog((sdf.format(new Date()) + "<br/>" + (CommonUtils.checkExistOrNot(group.get("groupName")) ? group.get("groupName").toString() : "[组名加载失败]") + "/" + parameter.getCurrentAccount().getName() + "<br/>将工单完结").toString());
								woProcessLog.setProcessed(1);
								woProcessLog.setSort(System.currentTimeMillis());
								workOrderPlatformStoreMapper.addStoreProcessLog(woProcessLog);
								// 处理记录
								WoProcessRecord woProcessRecord = new WoProcessRecord();
								woProcessRecord.setId(UUID.randomUUID().toString());
								woProcessRecord.setCreateTime(new Date());
								woProcessRecord.setCreateBy(parameter.getCurrentAccount().getId().toString());
								woProcessRecord.setUpdateTime(new Date());
								woProcessRecord.setUpdateBy(parameter.getCurrentAccount().getId().toString());
								woProcessRecord.setWoStoreLogId(woProcessLog.getId());
								woProcessRecord.setProcessRemark(wos.getLastProcessInfo());
								woProcessRecord.setAccessory(CommonUtils.checkExistOrNot(parameter.getParam().get("accessory")) ? parameter.getParam().get("accessory").toString() : "");
								workOrderPlatformStoreMapper.addProcessRecord(woProcessRecord);
								//增加交互日志
								WoMutualLog record = new WoMutualLog();
								record.setAccessory(CommonUtils.checkExistOrNot(parameter.getParam().get("accessory")) ? parameter.getParam().get("accessory").toString() : "");
								record.setAction(Constant.ACTION_OVER);
								record.setCreateBy(parameter.getCurrentAccount().getId().toString());
								record.setCreateTime(new Date());
								record.setLog(woProcessLog.getLog());
								record.setProcessRemark(wos.getLastProcessInfo());
								record.setWoNo(currentWos.getWoNo());
								woMutualLogMapper.insert(record);
							} else {
								continue;
							}
							transactionManager.commit(status);
						} catch (Exception e) {
							transactionManager.rollback(status);
							throw new Exception(Constant.RESULT_FAILURE_MSG +"over"+ CommonUtils.getExceptionStack(e) + "；已影响" + result + "条工单记录");
						}
						result++;
					}
				}
			}
		} else {
			throw new Exception(Constant.RESULT_FAILURE_MSG +"over"+ Constant.RESULT_FAILURE_REASON_1);
		}
		return result;
	}
	/**
	 * cancleWorkOrder 取消
	 */
	private int cancleWorkOrder (Parameter parameter) throws Exception {
		String teamId = groupMapper.queryCurrentGroupByWoId(
				JSONObject.parseArray(
						parameter.getParam().get("woId").toString()
						).getJSONObject(0).getString("woId")
				);
		int result = 0;
		if(CommonUtils.checkExistOrNot(parameter.getParam().get("woId"))) {
			JSONArray woIds = JSONObject.parseArray(parameter.getParam().get("woId").toString());
			for(int i = 0; i < woIds.size(); i++) {
				if(CommonUtils.checkExistOrNot(woIds.getJSONObject(i).getString("woId")) && CommonUtils.checkExistOrNot(woIds.getJSONObject(i).getInteger("version"))) {
					WorkOrderStore currentWos = workOrderPlatformStoreMapper.getWorkOrderStoreById(woIds.getJSONObject(i).getString("woId"));
					//工单的工单号为母单 并且 母单不能处理
					if(currentWos.getWoNo().split("-").length==1 && !fatherWoCanProcess(currentWos.getWoNo())){
						continue;
					}
					if(currentWos.getWoStatus() == 0&&CommonUtils.checkExistOrNot(currentWos.getCurrentProcessor())) {
						//子单 不相等  也可以完结
						if(currentWos.getWoNo().split("-").length==1){	
							// 未处理
							if(Integer.parseInt(currentWos.getCurrentProcessor()) != parameter.getCurrentAccount().getId()
									&& Integer.parseInt(workOrderPlatformStoreMapper.getStoreById(teamId).get("ifShare").toString()) != 1) {
								// 当前处理人不是自己且不是组内共享
								continue;
							}
							if(workOrderPlatformStoreMapper.getGroupTypeById(currentWos.getCurrentProcessorGroup())!=
									workOrderPlatformStoreMapper.getGroupTypeById(currentWos.getCreateByGroup())){
								//当前处理组的组别  不等于 创建组的组别
								continue;
							}
						}
						// 主表更新
						WorkOrderStore wos = new WorkOrderStore();
						wos.setId(woIds.getJSONObject(i).getString("woId"));
						wos.setUpdateTime(new Date());
						wos.setUpdateBy(parameter.getCurrentAccount().getId().toString());
						wos.setWoStatus(3);
						wos.setCurrentProcessor(parameter.getCurrentAccount().getId().toString());
						wos.setCurrentProcessorDisplay(parameter.getCurrentAccount().getName());
						wos.setLastProcessTime(new Date());
						wos.setLastProcessInfo(CommonUtils.checkExistOrNot(parameter.getParam().get("processInfo")) ? parameter.getParam().get("processInfo").toString() : "");
						wos.setOverTime(new Date());
						wos.setVersion(woIds.getJSONObject(i).getInteger("version"));
						wos.setFollowUpFlag(0);
						wos.setErrorFlag(CommonUtils.checkExistOrNot(parameter.getParam().get("errorFlag"))?Integer.parseInt(parameter.getParam().get("errorFlag").toString()):null);
						// 手动提交事务
						WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
						DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
						DefaultTransactionDefinition def = new DefaultTransactionDefinition();
						def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
						TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
						try {
							if(workOrderPlatformStoreMapper.updateWoStoreMaster(wos) == 1) {
								// 日志记录
								// 当前操作人组ID获取组信息
								//Map<String, Object> group = workOrderPlatformStoreMapper.getStoreById(parameter.getCurrentAccount().getTeam_id());
								Map<String, Object> group = workOrderPlatformStoreMapper.getStoreById(teamId);
								
								WoProcessLog woProcessLog = new WoProcessLog();
								woProcessLog.setId(UUID.randomUUID().toString());
								woProcessLog.setCreateTime(new Date());
								woProcessLog.setCreateBy(parameter.getCurrentAccount().getId().toString());
								woProcessLog.setCreateByDisplay(parameter.getCurrentAccount().getName());
								woProcessLog.setCreateByGroup(teamId);
								//woProcessLog.setCreateByGroup(parameter.getCurrentAccount().getTeam_id());
								woProcessLog.setCreateByGroupDisplay(CommonUtils.checkExistOrNot(group.get("groupName")) ? group.get("groupName").toString() : "");
								woProcessLog.setUpdateTime(new Date());
								//woProcessLog.setUpdateBy(parameter.getCurrentAccount().getTeam_id());
								woProcessLog.setUpdateBy(teamId);
								woProcessLog.setWoStoreId(wos.getId());
								woProcessLog.setWoLocationNode(Constant.ST_NODE);
								woProcessLog.setAction(Constant.ACTION_CANCLE);
								woProcessLog.setLog((sdf.format(new Date()) + "<br/>" + (CommonUtils.checkExistOrNot(group.get("groupName")) ? group.get("groupName").toString() : "[组名加载失败]") + "/" + parameter.getCurrentAccount().getName() + "<br/>将工单取消").toString());
								woProcessLog.setProcessed(1);
								woProcessLog.setSort(System.currentTimeMillis());
								workOrderPlatformStoreMapper.addStoreProcessLog(woProcessLog);
								// 处理记录
								WoProcessRecord woProcessRecord = new WoProcessRecord();
								woProcessRecord.setId(UUID.randomUUID().toString());
								woProcessRecord.setCreateTime(new Date());
								woProcessRecord.setCreateBy(parameter.getCurrentAccount().getId().toString());
								woProcessRecord.setUpdateTime(new Date());
								woProcessRecord.setUpdateBy(parameter.getCurrentAccount().getId().toString());
								woProcessRecord.setWoStoreLogId(woProcessLog.getId());
								woProcessRecord.setProcessRemark(wos.getLastProcessInfo());
								woProcessRecord.setAccessory(CommonUtils.checkExistOrNot(parameter.getParam().get("accessory")) ? parameter.getParam().get("accessory").toString() : "");
								workOrderPlatformStoreMapper.addProcessRecord(woProcessRecord);
								//增加交互日志
								WoMutualLog record = new WoMutualLog();
								record.setAccessory(CommonUtils.checkExistOrNot(parameter.getParam().get("accessory")) ? parameter.getParam().get("accessory").toString() : "");
								record.setAction(Constant.ACTION_CANCLE);
								record.setCreateBy(parameter.getCurrentAccount().getId().toString());
								record.setCreateTime(new Date());
								record.setLog(woProcessLog.getLog());
								record.setProcessRemark(wos.getLastProcessInfo());
								record.setWoNo(currentWos.getWoNo());
								woMutualLogMapper.insert(record);
							} else {
								continue;
							}
							transactionManager.commit(status);
						} catch (Exception e) {
							transactionManager.rollback(status);
							throw new Exception(Constant.RESULT_FAILURE_MSG +"cancle"+ CommonUtils.getExceptionStack(e) + "；已影响" + result + "条工单记录");
						}
						result++;
					}
				}
			}
		} else {
			throw new Exception(Constant.RESULT_FAILURE_MSG +"cancle"+ Constant.RESULT_FAILURE_REASON_1);
		}
		return result;
	}
	
	@Override
	public boolean judgeProcessOrNot(Parameter parameter) {
		final WorkOrderStore Wos = workOrderPlatformStoreMapper.getWorkOrderStoreById(parameter.getParam().get("woId").toString());
		String woId=Wos.getId();
		String groupId=groupMapper.queryCurrentGroupByWoId(woId);
		WorkOrderStore wos = workOrderPlatformStoreMapper.getWorkOrderStoreById(parameter.getParam().get("woId").toString());
		try {
			if(wos.getWoStatus() == 0
					&&
					CommonUtils.checkExistOrNot(wos.getCurrentProcessor())
					&&
					(
							parameter.getCurrentAccount().getId() == Integer.parseInt(wos.getCurrentProcessor())
								||
							(
									parameter.getCurrentAccount().getTeamId().indexOf(wos.getCurrentProcessorGroup()) > -1
									&&
									Integer.parseInt(workOrderPlatformStoreMapper.getStoreById(groupId).get("ifShare").toString()) == 1
									)
							)
					) return true;
		} catch (NumberFormatException e) {
			return false;
		}
		return false;
		
		
	}

	@Override
	public List<Map<String, String>> getEnclosureByWKID(String id) {
		return workOrderPlatformStoreMapper.getEnclosureByWKID(id);
	}

	@Override
	public List<Map<String, Object>> listErrorTypeByWoType(Parameter parameter) {
		return workOrderPlatformStoreMapper.listErrorTypeByWoType(parameter);
	}

	@Transactional
	@Override
	public Result delTempWorkOrder(String woId) {
		try {
			workOrderPlatformStoreMapper.delTempWorkOrder(woId);
			workOrderPlatformStoreMapper.delWoOrderByWoId(woId);
			workOrderPlatformStoreMapper.delWoOrderDetailByWoId(woId);
		} catch (Exception e) {
			return new Result(false, Constant.RESULT_FAILURE_MSG +"del"+ e.getMessage());
			
		}
		return new Result(true, Constant.RESULT_SUCCESS_MSG);
	}

	@Override
	public List<Map<String, Object>> exportWorkOrder(Parameter parameter) {
	    String[] temp = null;
        if(CommonUtils.checkExistOrNot(parameter.getParam().get("create_time"))) {
            temp = parameter.getParam().get("create_time").toString().split(" - ");
            parameter.getParam().put("create_time_start", temp[0]);
            parameter.getParam().put("create_time_end", temp[1]);
        }
        if(CommonUtils.checkExistOrNot(parameter.getParam().get("last_process_time"))) {
            temp = parameter.getParam().get("last_process_time").toString().split(" - ");
            parameter.getParam().put("last_process_time_start", temp[0]);
            parameter.getParam().put("last_process_time_end", temp[1]);
        }
		return workOrderPlatformStoreMapper.exportWorkOrder(parameter);
	}
	
	private String endueDisplay(String code) {
		switch(code) {
		case Constant.WO_ALLOC_STATUS_WAA:return "待自动分配";
		case Constant.WO_ALLOC_STATUS_WMA:return "待手动分配";
		case Constant.WO_ALLOC_STATUS_ALD:return "已分配";
		case Constant.WO_PROCESS_STATUS_NEW:return "新建";
		case Constant.WO_PROCESS_STATUS_PRO:return "处理中";
		case Constant.WO_PROCESS_STATUS_PAU:return "挂起待确认";
		case Constant.WO_PROCESS_STATUS_CCD:return "已取消";
		case Constant.WO_PROCESS_STATUS_FIN:return "已处理";
		default: return "";
		}
	}

	@Override
	public Map<String, Object> getStoreById(String id) {
		return workOrderPlatformStoreMapper.getStoreById(id);
	}

	@Override
	public Result empGetWorkOrder(Parameter parameter) {
			int result = 0;
			String empId=parameter.getCurrentAccount().getId().toString();
			String empName = parameter.getCurrentAccount().getName();
		//	String teamId =String.valueOf(groupId);
			//Map<String, String> group = groupMapper.getGroupData(teamId);
			//String teamName = CommonUtils.checkExistOrNot(group.get("group_name")) ? group.get("group_name").toString() : "";
			Date date = new Date();
			try {
				if(CommonUtils.checkExistOrNot(parameter.getParam().get("woId"))) {
					JSONArray woIds = JSONObject.parseArray(parameter.getParam().get("woId").toString());
					for(int i = 0; i < woIds.size(); i++) {
						if(CommonUtils.checkExistOrNot(woIds.getJSONObject(i).getString("woId")) && CommonUtils.checkExistOrNot(woIds.getJSONObject(i).getInteger("version"))) {
							String teamId ="";
							String teamName ="";
							String woId = woIds.getJSONObject(i).getString("woId");
							 WorkOrderStore WOS = workOrderPlatformStoreMapper.getWorkOrderStoreById(woId);
							if(CommonUtils.checkExistOrNot(WOS.getCurrentProcessor())&&WOS.getCurrentProcessor().equals(empId)) {
								continue;
							}
							if(WOS.getWoStatus().equals(2)||WOS.getWoStatus().equals(3)) {
								continue;
							}
							//当前处理组的类型不是销售运营 不可以获取
							Map<String,Object> grouptype = workOrderPlatformStoreMapper.getStoreById(WOS.getCurrentProcessorGroup());
							if(CommonUtils.checkExistOrNot(grouptype)&&1!=Integer.parseInt(grouptype.get("groupType").toString())){
								continue;
							}
							String groupId=groupMapper.queryCurrentGroupByWoId(woId);
							String teamIds = parameter.getCurrentAccount().getTeamId();
							if(CommonUtils.checkExistOrNot(groupId)&&teamIds.contains(groupId)) {
									teamId=groupId;
									Map<String, Object> group = groupMapper.getGroupData(teamId);
									teamName = CommonUtils.checkExistOrNot(group.get("group_name")) ? group.get("group_name").toString() : "";
							}else {
								if(teamIds.contains(",")) {
									
									String[] teamIdArray = teamIds.split(",");
									List<String> teamIdList = Arrays.asList(teamIdArray);
									for (String teamid : teamIdList) {
										Integer count=workOrderPlatformStoreMapper.getCountByGroupId(Integer.parseInt(teamid));
										if(count==0){
											teamId=teamid;
											break;
										}
									}
									if(teamId.equals("")) {
										List<Map<String,Object>> groupMapList=workOrderPlatformStoreMapper.getMinCountGroup(teamIdList);
										teamId = groupMapList.get(0).get("groupId").toString();
										teamName= groupMapList.get(0).get("groupName").toString();
									}else {
										teamName = workOrderPlatformStoreMapper.getGroupNameById(teamId);
									}
									
								}else {
									teamId=teamIds;
									Map<String, Object> group = groupMapper.getGroupData(teamId);
									teamName = CommonUtils.checkExistOrNot(group.get("group_name")) ? group.get("group_name").toString() : "";
								}
							}
							// 主表更新
								WorkOrderStore wos = new WorkOrderStore();
								wos.setId(woId);
								wos.setUpdateTime(date);
								wos.setUpdateBy(empId);
								wos.setCurrentProcessor(empId);
								wos.setCurrentProcessorDisplay(empName);
								wos.setCurrentProcessorGroup(teamId);
								wos.setCurrentProcessorGroupDisplay(teamName);
								wos.setFollowUpFlag(0);
								wos.setLastProcessTime(date);
								wos.setVersion(woIds.getJSONObject(i).getInteger("version"));
								// 手动提交事务
								WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
								DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
								DefaultTransactionDefinition def = new DefaultTransactionDefinition();
								def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
								TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
								try {
									if(workOrderPlatformStoreMapper.updateWoStoreMaster(wos) == 1) {
										// 日志记录
										// 当前操作人组ID获取组信息
										WoProcessLog woProcessLog = new WoProcessLog();
										woProcessLog.setId(UUID.randomUUID().toString());
										woProcessLog.setCreateTime(date);
										woProcessLog.setCreateBy(empId);
										woProcessLog.setCreateByDisplay(empName);
										woProcessLog.setCreateByGroup(teamId);
										woProcessLog.setCreateByGroupDisplay(teamName);
										woProcessLog.setUpdateTime(date);
										woProcessLog.setUpdateBy(empId);
										woProcessLog.setWoStoreId(wos.getId());
										woProcessLog.setWoLocationNode(Constant.SO_NODE);
										woProcessLog.setAction(Constant.ACTION_OBTAIN);
										woProcessLog.setLog((sdf.format(date) +"<br/>"+Constant.WO_NODE_SO+"/" + (CommonUtils.checkExistOrNot(teamName) ? teamName : "[组名加载失败]") + "/" + empName + "<br/>获取工单").toString());
										woProcessLog.setProcessed(0);
										woProcessLog.setSort(System.currentTimeMillis());
										workOrderPlatformStoreMapper.addStoreProcessLog(woProcessLog);
										result++;
									} 
									transactionManager.commit(status);
								} catch (Exception e) {
									transactionManager.rollback(status);
									throw new Exception(Constant.RESULT_FAILURE_MSG +"get"+ CommonUtils.getExceptionStack(e) + "；已影响" + result + "条工单记录");
								}
							}
					}
				} else {
					throw new Exception(Constant.RESULT_FAILURE_MSG +"get"+ Constant.RESULT_FAILURE_REASON_1);
				}
			} catch(Exception e) {
				e.printStackTrace();
				return new Result(false, Constant.RESULT_FAILURE_MSG +"get"+ e.getMessage());
			}
			return new Result(true, (result + "条工单记录被操作"));

		}

	@Override
	public int judgeSOReplyOrNot(String woId) {
		return workOrderPlatformStoreMapper.judgeSOReplyOrNot(woId);
	}


	@Override
	public Map<String, Integer> countAllTab(Parameter parameter) {
		Map<String,Integer> map = new HashMap<>();
		parameter.getParam().put("tabNo", 2);
		int div_2 = workOrderPlatformStoreMapper.countSearch(parameter);
		map.put("div_2", div_2);
		parameter.getParam().put("tabNo", 6);
		int div_6 = workOrderPlatformStoreMapper.countSearch(parameter);
		map.put("div_6", div_6);
		parameter.getParam().put("tabNo", 10);
		int div_10 = workOrderPlatformStoreMapper.countSearch(parameter);
		map.put("div_10", div_10);
		return map;
	}

	@Override
	public Result autoAllocation(String woId) {
		
		Date date=new Date();
		String empId="";
		String empName="";
		String groupName="";
		Integer version=0;
		String autoGroupId="";
		//根据工单id查出 工单类型、所属店铺、是否异常
		List<Map<String, Object>> WOSStoresMap = workOrderPlatformStoreMapper.getWOSStoreById(woId);
		if(WOSStoresMap.size()==0) {
			String woIds = RedisUtils.get(1,AUTO_GROUP_WORK);
			List<String> woIdList = JSONArray.parseArray(woIds,String.class);
			woIdList.remove(0);
			RedisUtils.set(1,AUTO_GROUP_WORK, JSON.toJSONString(woIdList));
			return new Result(false, ("未找到创建此工单的店铺"));
		}
		Map<String, Object> WOSStores = WOSStoresMap.get(0);
		if(CommonUtils.checkExistOrNot(WOSStores.get("current_processor"))){
			String woIds = RedisUtils.get(1,AUTO_GROUP_WORK);
			List<String> woIdList = JSONArray.parseArray(woIds,String.class);
			woIdList.remove(0);
			RedisUtils.set(1,AUTO_GROUP_WORK, JSON.toJSONString(woIdList));
			return new Result(false, ("已经存在当前处理人"));
		}
		String woTypeCode = WOSStores.get("woType").toString();
		String storeCode = WOSStores.get("storeCode").toString();
		String  flag=WOSStores.get("errorFlag").toString();
		version=Integer.parseInt(WOSStores.get("version").toString());
		int errorFlag=0;
		if(flag.equals("true")){
			errorFlag=1;
		}
		Map<String, Object> map =new HashMap<>();
		map.put("woTypeCode", woTypeCode);
		map.put("storeCode", storeCode);
		map.put("errorFlag", errorFlag);
		//根据类型、店铺、是否异常、查询出匹配组
		List<String> groups = workOrderPlatformStoreMapper.findGroupByTypeAndStore(map);
		if(groups.size()==0){
			String woIds = RedisUtils.get(1,AUTO_GROUP_WORK);
			List<String> woIdList = JSONArray.parseArray(woIds,String.class);
			woIdList.remove(0);
			RedisUtils.set(1,AUTO_GROUP_WORK, JSON.toJSONString(woIdList));
			return new Result(false, ("未找到匹配组"));
		}else {
			//取匹配组所有组员
			List<String> empList =workOrderPlatformStoreMapper.getEmpByGroup(groups);
			if(empList.size()>0) {
				
				for (String employeeId : empList) {
					Integer count=workOrderPlatformStoreMapper.getCountByempId(employeeId);
					if(count==0){
						empId=employeeId;
						break;
					}
				}
				
				
				if(empId.equals("")) {
					List<Map<String, Object>> empIdList = workOrderPlatformStoreMapper.getEmpIdByCur(empList);
					empId = empIdList.get(0).get("empId").toString();
					empName = empIdList.get(0).get("empName").toString();
				}else {
					empName = workOrderPlatformStoreMapper.getEmpNameById(Integer.parseInt(empId));
				}
				
				//取所有组员单量处理最少的组员
				/*List<Map<String,Object>> CurList=workOrderPlatformStoreMapper.getEmpIdByCur(empIdList);
				
				empId = CurList.get(0).get("empId").toString();
				empName = CurList.get(0).get("empName").toString();*/
				
				map.put("empId", empId);
				//取匹配组员的当前组
				List<String> groupList = workOrderPlatformStoreMapper.getGroupIdByEmp(map);
				if(groupList.size()==1) {
					autoGroupId = groupList.get(0);
					groupName =workOrderPlatformStoreMapper.getGroupNameById(autoGroupId);
				}else {
					
					for (String groupId : groupList) {
						Integer count=workOrderPlatformStoreMapper.getCountByGroupId(Integer.parseInt(groupId));
						if(count==0){
							autoGroupId=groupId;
							break;
						}
					}
					
					
					if(autoGroupId.equals("")) {
						List<Map<String,Object>> groupMapList=workOrderPlatformStoreMapper.getMinCountGroup(groupList);
						autoGroupId = groupMapList.get(0).get("groupId").toString();
						groupName= groupMapList.get(0).get("groupName").toString();
					}else {
						groupName = workOrderPlatformStoreMapper.getGroupNameById(autoGroupId);
					}
				}
				
			}else {
				for (String groupId : groups) {
					Integer count=workOrderPlatformStoreMapper.getCountByGroupId(Integer.parseInt(groupId));
					if(count==0){
						autoGroupId=groupId;
						break;
					}
				}
				if(autoGroupId.equals("")) {
					List<Map<String,Object>> groupMapList=workOrderPlatformStoreMapper.getMinCountGroup(groups);
					autoGroupId = groupMapList.get(0).get("groupId").toString();
					groupName= groupMapList.get(0).get("groupName").toString();
				}else {
					groupName = workOrderPlatformStoreMapper.getGroupNameById(autoGroupId);
				}
			}
		}
		// 主表更新
		WorkOrderStore UpdateWOSGroup = new WorkOrderStore();
		UpdateWOSGroup.setId(woId);
		UpdateWOSGroup.setUpdateTime(date);
		UpdateWOSGroup.setUpdateBy("0");
		UpdateWOSGroup.setCurrentProcessorGroup(autoGroupId);
		UpdateWOSGroup.setCurrentProcessorGroupDisplay(groupName);
		if(!empId.equals("")) {
			UpdateWOSGroup.setCurrentProcessor(empId);
			UpdateWOSGroup.setCurrentProcessorDisplay(empName);
		}
		UpdateWOSGroup.setLastProcessTime(date);
		UpdateWOSGroup.setVersion(version);
		
		// 手动提交事务
		WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		try {
		
			if(workOrderPlatformStoreMapper.updateWoStoreMaster(UpdateWOSGroup) == 1) {
				
				// 日志记录
				// 当前操作人组ID获取组信息
				String log=(sdf.format(date) + "<br/>系统/将工单自动分配给<br/>"+Constant.WO_NODE_SO+"/" + (CommonUtils.checkExistOrNot(groupName) ? groupName : "[组名加载失败]") ).toString();
				if(!empId.equals("")) {
					log=log+ "/" + empName ;
				}
				WoProcessLog woProcessLog = new WoProcessLog();
				woProcessLog.setId(UUID.randomUUID().toString());
				woProcessLog.setCreateTime(date);
				woProcessLog.setCreateBy("0");
				woProcessLog.setCreateByDisplay("系统分配");
				woProcessLog.setCreateByGroup("000");
				woProcessLog.setCreateByGroupDisplay("系统分配");
				woProcessLog.setUpdateTime(date);
				woProcessLog.setUpdateBy("0");
				woProcessLog.setWoStoreId(UpdateWOSGroup.getId());
				woProcessLog.setWoLocationNode(Constant.SO_NODE);
				woProcessLog.setAction(Constant.ACTION_ALLOC);
				woProcessLog.setLog(log);
				woProcessLog.setProcessed(0);
				woProcessLog.setSort(System.currentTimeMillis());
				woProcessLog.setRecieveBy(empId);
				woProcessLog.setRecieveByDisplay(empName);
				woProcessLog.setRecieveByGroup(autoGroupId);
				woProcessLog.setRecieveByGroupDisplay(groupName);
				workOrderPlatformStoreMapper.addStoreProcessLog(woProcessLog);
			} 
				transactionManager.commit(status);
				
				Map<String,Object> workOrderStore = workOrderPlatformStoreMapper.selectMasterById(woId);
				String toMail = "";
				String content = "";
				String cp = CommonUtils.checkExistOrNot(workOrderStore.get("current_processor"))?String.valueOf(workOrderStore.get("current_processor")):"";
				String cpg = CommonUtils.checkExistOrNot(workOrderStore.get("current_processor_group"))?String.valueOf(workOrderStore.get("current_processor_group")):"";
				String cpgn = CommonUtils.checkExistOrNot(workOrderStore.get("current_processor_group_display"))?String.valueOf(workOrderStore.get("current_processor_group_display")):"";
				String woNo = CommonUtils.checkExistOrNot(workOrderStore.get("wo_no"))?String.valueOf(workOrderStore.get("wo_no")):"";
				
				if(CommonUtils.checkExistOrNot(cp)){
					String email = workOrderPlatformStoreMapper.getEmailById(cp);
					if(CommonUtils.checkExistOrNot(email)){
						toMail=email;
						content = "您收到一个自动分配的工单，工单号为【"+woNo+"】，请及时处理！";
					}
				}else if(CommonUtils.checkExistOrNot(cpg)){
					String teamEmail = workOrderPlatformStoreMapper.getTeamEmailById(cpg);
					if(CommonUtils.checkExistOrNot(teamEmail)){
						toMail=teamEmail;
						content = "您的组【"+cpgn+"】接收到一个自动分配的工单，工单号为【"+woNo+"】,请及时处理！";
					}
				}
				if(CommonUtils.checkExistOrNot(toMail)){
					MailUtil.sendWorkOrderMail(toMail, "LMIS工单提醒",content);
				}
				
			} catch (Exception e) {
				transactionManager.rollback(status);
				String woIds = RedisUtils.get(1,AUTO_GROUP_WORK);
				List<String> woIdList = JSONArray.parseArray(woIds,String.class);
				woIdList.remove(0);
				RedisUtils.set(1,AUTO_GROUP_WORK, JSON.toJSONString(woIdList));
				e.printStackTrace();
				return new Result(false, Constant.RESULT_FAILURE_MSG +"autoAllo"+ e.getMessage());
			}
			
		String woIds = RedisUtils.get(1,AUTO_GROUP_WORK);
		List<String> woIdList = JSONArray.parseArray(woIds,String.class);
		woIdList.remove(0);
		RedisUtils.set(1,AUTO_GROUP_WORK, JSON.toJSONString(woIdList));
		return new Result(true, ("已完成自动分配"));
	}

	@Override
	public List<Map<String, Object>> listWoTypeByDepartment(int department) {
		return workOrderPlatformStoreMapper.listWoTypeByDepartment(department);
	}

	@Override
	public void updateWoStoreMaster(WorkOrderStore workOrderStore) {
		workOrderPlatformStoreMapper.updateWoStoreMaster(workOrderStore);
	}

	@Override
	public Result autoWorkOrderEmp(String woId) {
		Date date=new Date();
		String empId="";
		String empName="";
		String groupName="";
		Integer version=0;
		String autoGroupId="";
		//根据工单id查出 工单类型、所属店铺、是否异常
		//根据ID查询出当前处理组
		Map<String, Object> WOSStoresMap = workOrderPlatformStoreMapper.getCurGroupById(woId);

		if(CommonUtils.checkExistOrNot(WOSStoresMap.get("current_processor"))){
			String woIds = RedisUtils.get(1,AUTO_EMP_WORK);
			List<String> woIdList = JSONArray.parseArray(woIds,String.class);
			woIdList.remove(0);
			RedisUtils.set(1,AUTO_EMP_WORK, JSON.toJSONString(woIdList));
			return new Result(false, ("已经存在当前处理人"));
		}
		
		autoGroupId= WOSStoresMap.get("groupId").toString();
		groupName = WOSStoresMap.get("groupName").toString();
		version = Integer.parseInt(WOSStoresMap.get("version").toString()) ;
		//根据组id查询当前组的组员
		List<String> empList=workOrderPlatformStoreMapper.getEmpIdByTeamId(Integer.parseInt(autoGroupId));
		
		if(empList.size()==0){
			String woIds = RedisUtils.get(1,AUTO_EMP_WORK);
			List<String> woIdList = JSONArray.parseArray(woIds,String.class);
			woIdList.remove(0);
			RedisUtils.set(1,AUTO_EMP_WORK, JSON.toJSONString(woIdList));
			return new Result(false, ("未找到人员"));
		}
		for (String employeeId : empList) {
			Integer count=workOrderPlatformStoreMapper.getCountByempId(employeeId);
			if(count==0){
				empId=employeeId;
				break;
			}
		}
		
		
		if(empId.equals("")) {
			List<Map<String, Object>> empIdList = workOrderPlatformStoreMapper.getEmpIdByCur(empList);
			empId = empIdList.get(0).get("empId").toString();
			empName = empIdList.get(0).get("empName").toString();
		}else {
			empName = workOrderPlatformStoreMapper.getEmpNameById(Integer.parseInt(empId));
		}
		
		// 主表更新
		WorkOrderStore wos = new WorkOrderStore();
		wos.setId(woId);
		wos.setUpdateTime(date);
		wos.setUpdateBy("0");
		wos.setCurrentProcessor(empId);
		wos.setCurrentProcessorDisplay(empName);
		wos.setLastProcessTime(date);
		wos.setVersion(version);
		
			
		// 手动提交事务
		WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		
		try {
			if(workOrderPlatformStoreMapper.updateWoStoreMaster(wos) == 1) {
				// 日志记录
				// 当前操作人组ID获取组信息
				WoProcessLog woProcessLog = new WoProcessLog();
				woProcessLog.setId(UUID.randomUUID().toString());
				woProcessLog.setCreateTime(date);
				woProcessLog.setCreateBy("0");
				woProcessLog.setCreateByDisplay("系统分配");
				woProcessLog.setCreateByGroup("000");
				woProcessLog.setCreateByGroupDisplay("系统分配");
				woProcessLog.setUpdateTime(date);
				woProcessLog.setUpdateBy("0");
				woProcessLog.setWoStoreId(wos.getId());
				woProcessLog.setWoLocationNode(Constant.SO_NODE);
				woProcessLog.setAction(Constant.ACTION_ALLOC);
				woProcessLog.setLog((sdf.format(date) + "<br/>系统/将工单自动分配给<br/>"+Constant.WO_NODE_SO+"/" + (CommonUtils.checkExistOrNot(groupName) ? groupName : "[组名加载失败]") + "/" + empName ).toString());
				woProcessLog.setProcessed(0);
				woProcessLog.setSort(System.currentTimeMillis());
				woProcessLog.setRecieveBy(empId);
				woProcessLog.setRecieveByDisplay(empName);
				woProcessLog.setRecieveByGroup(autoGroupId);
				woProcessLog.setRecieveByGroupDisplay(groupName);
				workOrderPlatformStoreMapper.addStoreProcessLog(woProcessLog);
			} 
			transactionManager.commit(status);
			
			Map<String,Object> workOrderStore = workOrderPlatformStoreMapper.selectMasterById(wos.getId());
			String toMail = "";
			String content = "";
			String cp = CommonUtils.checkExistOrNot(workOrderStore.get("current_processor"))?String.valueOf(workOrderStore.get("current_processor")):"";
			String cpg = CommonUtils.checkExistOrNot(workOrderStore.get("current_processor_group"))?String.valueOf(workOrderStore.get("current_processor_group")):"";
			String cpgn = CommonUtils.checkExistOrNot(workOrderStore.get("current_processor_group_display"))?String.valueOf(workOrderStore.get("current_processor_group_display")):"";
			String woNo = CommonUtils.checkExistOrNot(workOrderStore.get("wo_no"))?String.valueOf(workOrderStore.get("wo_no")):"";
			if(CommonUtils.checkExistOrNot(cp)){
				String email = workOrderPlatformStoreMapper.getEmailById(cp);
				if(CommonUtils.checkExistOrNot(email)){
					toMail=email;
					content = "您收到一个自动分配的工单，工单号为【"+woNo+"】，请及时处理！";
				}
			}else if(CommonUtils.checkExistOrNot(cpg)){
				String teamEmail = workOrderPlatformStoreMapper.getTeamEmailById(cpg);
				if(CommonUtils.checkExistOrNot(teamEmail)){
					toMail=teamEmail;
					content = "您的组【"+cpgn+"】接收到一个自动分配的工单，工单号为【"+woNo+"】,请及时处理！";
				}
			}
			if(CommonUtils.checkExistOrNot(toMail)){
				MailUtil.sendWorkOrderMail(toMail,"LMIS工单提醒", content);
			}
		
		} catch (Exception e) {
			transactionManager.rollback(status);
			String woIds = RedisUtils.get(1,AUTO_EMP_WORK);
			List<String> woIdList = JSONArray.parseArray(woIds,String.class);
			woIdList.remove(0);
			RedisUtils.set(1,AUTO_EMP_WORK, JSON.toJSONString(woIdList));
			e.printStackTrace();
			return new Result(false, Constant.RESULT_FAILURE_MSG +"autoEmp"+ e.getMessage());
		}
		String woIds = RedisUtils.get(1,AUTO_EMP_WORK);
		List<String> woIdList = JSONArray.parseArray(woIds,String.class);
		woIdList.remove(0);
		RedisUtils.set(1,AUTO_EMP_WORK, JSON.toJSONString(woIdList));
		return new Result(true, ("已完成自动分配"));
	}

	@Override
	public void aKeyExport(String woId,HttpServletResponse response) {
		
			WorkOrderStore wos = workOrderPlatformStoreMapper.getWorkOrderStoreById(woId);
		 	File newFile = createNewFile();
		 	
	        // 新文件写入数据，并下载*****************************************************  
	        InputStream is = null;
	        HSSFWorkbook workbook = null;  
	        HSSFSheet sheet1 = null;
	        HSSFSheet sheet2 = null;
	        try {  
	            is = new FileInputStream(newFile);// 将excel文件转为输入流  
	            workbook = new HSSFWorkbook(is);// 创建个workbook，  
	            // 获取第一个sheet  
	            sheet1 = workbook.getSheetAt(0);
	            sheet2 = workbook.getSheetAt(1);
	        } catch (Exception e1) {  
	            e1.printStackTrace();  
	        }
	        //工单信息
	        //提交时间
	        Map<String,Object> logMap=workOrderPlatformStoreMapper.getSubmitLogByWoId(woId);
	        String createTime=logMap.get("create_time").toString();
	        Date createDate = DateUtil.StrToTime(createTime);
	        createTime = DateUtil.formatDate2(createDate);
	        //附件信息
	        String annexStr="";
	        if(CommonUtils.checkExistOrNot(wos.getAccessory())) {
	        	annexStr="有；"+createTime+logMap.get("create_by_display").toString();
	        }else {
	        	annexStr="无";
	        }
	        //进度信息
	        List<Map<String,Object>> recordMap = workOrderPlatformStoreMapper.getRecordByWoId(woId);
	        StringBuffer recordBuff=new StringBuffer();
	        for (Map<String, Object> map : recordMap) {
	        	String LogCreateTime=map.get("create_time").toString();
		        Date LogCreateDate = DateUtil.StrToTime(LogCreateTime);
		        LogCreateTime = DateUtil.formatDate2(LogCreateDate);
	        	recordBuff.append(LogCreateTime).append("/");
	        	recordBuff.append(map.get("create_by_display").toString()).append("/");
	        	recordBuff.append(map.get("create_by_group_display").toString()).append("/");
	        	recordBuff.append(map.get("create_by_department_display").toString()).append("/");
	        	recordBuff.append(map.get("follow_up_record").toString()).append("；").append("\n");
			}
	            try {
	                // 写数据  
	                FileOutputStream fos = new FileOutputStream(newFile);  
	                HSSFRow row = sheet1.getRow(0);  
	                HSSFCell cell = row.getCell(0);
	                
	                //标题
	                if(CommonUtils.checkExistOrNot(wos.getTitle())){
	                	cell.setCellValue("标题："+wos.getTitle());
	                }
	                //店铺
		            if(CommonUtils.checkExistOrNot(wos.getCreateByGroupDisplay())){
		                row = sheet1.getRow(1);  
		                cell = row.getCell(1);  
		                cell.setCellValue(wos.getCreateByGroupDisplay());
		            }
		            
	                //提交人
		            if(CommonUtils.checkExistOrNot(wos.getCreateByDisplay())){
		                cell = row.getCell(3);  
		                cell.setCellValue(wos.getCreateByDisplay());
		            }
	                //工单号
		            if(CommonUtils.checkExistOrNot(wos.getWoNo())){
		                row = sheet1.getRow(2);  
		                cell = row.getCell(1);  
		                cell.setCellValue(wos.getWoNo());
		            }
	                //提交时间
		            
	                cell = row.getCell(3);  
	                cell.setCellValue(createTime);
	                //问题所属店铺
	                if(CommonUtils.checkExistOrNot(wos.getProblemStoreDisplay())){
		                row = sheet1.getRow(3);  
		                cell = row.getCell(1);  
		                cell.setCellValue(wos.getProblemStoreDisplay());
	                }
	                //相关单据号（非销售单）
	                if(CommonUtils.checkExistOrNot(wos.getRelatedNumber())){
		                cell = row.getCell(3);  
		                cell.setCellValue(wos.getRelatedNumber());
	                }
	                //平台订单号
	                if(CommonUtils.checkExistOrNot(wos.getPlatformNumber())){
		                row = sheet1.getRow(4);  
		                cell = row.getCell(1);  
		                cell.setCellValue(wos.getPlatformNumber());
	                }
	                //运单号
	                if(CommonUtils.checkExistOrNot(wos.getWaybill())){
		                cell = row.getCell(3);  
		                cell.setCellValue(wos.getWaybill());
	                }
	                //操作系统
	                if(CommonUtils.checkExistOrNot(wos.getOperationSystem())){
		                row = sheet1.getRow(5);  
		                cell = row.getCell(1);  
		                cell.setCellValue(wos.getOperationSystem());
	                }
	                //工单类型
	                if(CommonUtils.checkExistOrNot(wos.getWoTypeDisplay())){
		                cell = row.getCell(3);  
		                cell.setCellValue(wos.getWoTypeDisplay());
	                }
	                //问题描述
	                if(CommonUtils.checkExistOrNot(wos.getIssueDescription())){
		                row = sheet1.getRow(6);  
		                cell = row.getCell(1);
		                cell.setCellValue(wos.getIssueDescription());
	                }
	                //进度列表
	                row = sheet1.getRow(11);  
	                cell = row.getCell(1);
	                cell.setCellValue(recordBuff.toString());
	                
	                //有无附件
	                
	                row = sheet1.getRow(15);  
	                cell = row.getCell(1);
	                cell.setCellValue(annexStr);
	                
	                //期望处理时间
	                if(CommonUtils.checkExistOrNot(wos.getExpectationProcessTime())){
		                row = sheet1.getRow(16);  
		                cell = row.getCell(1);
		                cell.setCellValue(DateUtil.formatDate2(wos.getExpectationProcessTime()));
	                }
	                //跟进人
	                if(CommonUtils.checkExistOrNot(wos.getCurrentProcessorDisplay())){
		                cell = row.getCell(3);  
		                cell.setCellValue(wos.getCurrentProcessorDisplay());
	                }
	                
	                
	                //处理信息
	                List<Map<String,Object>> logRecordMap = workOrderPlatformStoreMapper.getLogRecordByWoId(woId);
	                for (int i = 0; i < logRecordMap.size(); i++) {
	                	Map<String, Object> map = logRecordMap.get(i);
	                	Date date = DateUtil.StrToTime(map.get("create_time").toString());
	                	String Createtime = DateUtil.formatDate2(date);
	                    row = sheet2.createRow(i+1);  
	                    cell = row.createCell(0);  
		                cell.setCellValue(Createtime);
		                cell = row.createCell(1);  
		                cell.setCellValue(map.get("create_by_display").toString());
		                cell = row.createCell(2);  
		                cell.setCellValue(map.get("process_remark").toString());
		                cell = row.createCell(3);
		                if(CommonUtils.checkExistOrNot(map.get("accessory"))) {
		                	cell.setCellValue("有；"+Createtime+map.get("create_by_display").toString());
		    	        }else {
		    	        	cell.setCellValue("无");
		    	        }
					}
	                
	                workbook.write(fos);  
	                fos.flush();  
	                fos.close();
	                
	                
	                InputStream fis = new BufferedInputStream(new FileInputStream(newFile));  
	                byte[] buffer = new byte[fis.available()];  
	                fis.read(buffer);  
	                
	                
	                fis.close();
	                
	                int length=0;
	        		if(buffer!=null){
	        			length = buffer.length;
	        		}
	        		String Wono = wos.getWoNo();
	        		String fileName=Wono+".xls";
	        		response.setContentType("application/msexcel;charset=utf-8");
	        		response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("GB2312"),"ISO8859-1")); 
	        		response.setContentLength(length);
	        		OutputStream output = response.getOutputStream();
	        		if(buffer != null){
	        			output.write(buffer);
	        		}
	        		output.flush();
	        		response.flushBuffer();
	        		output.close(); 
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            } finally {  
	                try {  
	                    if (null != is) {  
	                        is.close();  
	                    }  
	                } catch (Exception e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	        // 删除创建的新文件  
	         this.deleteFile(newFile);  
	}
	
	
	 /**  
     * 复制文件  
     *   
     * @param s  
     *            源文件  
     * @param t  
     *            复制到的新文件  
     */  
  
    public void fileChannelCopy(File s, File t) {  
        try {  
            InputStream in = null;  
            OutputStream out = null; 
            try {  
                in = new BufferedInputStream(new FileInputStream(s), 1024);  
                out = new BufferedOutputStream(new FileOutputStream(t), 1024);  
                byte[] buffer = new byte[1024];  
                int len;  
                while ((len = in.read(buffer)) != -1) {  
                    out.write(buffer, 0, len);  
                }  
            } finally {  
                if (null != in) {  
                    in.close();  
                }  
                if (null != out) {  
                    out.close();  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
  
    /**  
     * 读取excel模板，并复制到新文件中供写入和下载  
     *   
     * @return  
     */  
    public File createNewFile() {  
        // 读取模板，并赋值到新文件************************************************************  
        // 文件模板路径  
    	String templetPath = CommonUtils.getAllMessage("config","COMMON_TEMPLET_Linux");
        String path = templetPath+"工单导出模板.xls";  
        File file = new File(path);  
        // 保存文件的路径  
        String realPath = templetPath;  
        // 新的文件名  
        String newFileName = "copy工单导出模板.xls";  
        // 判断路径是否存在  
        File dir = new File(realPath);  
        if (!dir.exists()) {  
            dir.mkdirs();  
        }  
        // 写入到新的excel  
        File newFile = new File(realPath, newFileName);  
        try {  
            newFile.createNewFile();  
            // 复制模板到新文件 
            fileChannelCopy(file, newFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return newFile;  
    }  
  
    /**  
     * 下载成功后删除  
     *   
     * @param files  
     */  
    private void deleteFile(File... files) {  
        for (File file : files) {  
            if (file.exists()) {  
                file.delete();
            }  
        }  
    }
    
    
	private String downAnnex(WorkOrderStore wos,HttpServletResponse response,List<Map<String, String>> listMap) throws Exception {
		String woNo = wos.getWoNo();
		String filePathDir = CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname())+woNo;
		String filePath = "";
		String slash="";
		if(OSinfo.getOSname().equals(EPlatform.Linux) || OSinfo.getOSname().equals(EPlatform.Mac_OS_X)) {
			slash="/";
		} else if(OSinfo.getOSname().equals(EPlatform.Windows)) {
			slash="\\";
		}
		filePath=filePathDir+slash;
		String root = CommonUtils.getAllMessage("config", "NGINX_FILE_DOWNLOAD");
		
		for (Map<String, String> map : listMap) {
			String urls = map.get("url");
			String dirName = map.get("dirName");
			//创建文件夹
			String FilePath1=filePath+dirName;
			File file = new File(FilePath1);
			file.mkdirs();
			String[] urlArray = urls.split("_");
			for (String url : urlArray) {
				if(!url.contains("#")) {
					continue;
				}
				String[]  picNameFs= url.split("#");
				String urlName = root+picNameFs[0];
				String trueName = FilePath1+slash+picNameFs[1]+picNameFs[2];
				//从服务器下载图片到指定文件夹
				FileUtil.createImage(urlName,trueName);
			}
		}
		
		//将文件夹打包成zip
		ZipUtils.zip(filePathDir,CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname()));
		FileUtil.deleteDir(new File(filePathDir));
		FileUtil.downloadFile(filePathDir+com.bt.lmis.balance.common.Constant.SYM_POINT+com.bt.lmis.balance.common.Constant.FILE_TYPE_SUFFIX_ZIP,woNo+com.bt.lmis.balance.common.Constant.SYM_POINT+com.bt.lmis.balance.common.Constant.FILE_TYPE_SUFFIX_ZIP,response);
		//返回zip地址
		return filePathDir+com.bt.lmis.balance.common.Constant.SYM_POINT+com.bt.lmis.balance.common.Constant.FILE_TYPE_SUFFIX_ZIP;
	}

	@Override
	public void downloadZip(String woId, HttpServletResponse response) {
		WorkOrderStore wos = workOrderPlatformStoreMapper.getWorkOrderStoreById(woId);
        List<Map<String,Object>> logRecordMap = workOrderPlatformStoreMapper.getLogRecordByWoId(woId);
        String FileStr="";
        File newFile = null;
        List<Map<String,String>> listMap=new ArrayList<>();
        for (int i = 0; i < logRecordMap.size(); i++) {
        	Map<String,String> map=new HashMap<>();
        	Map<String, Object> Logmap = logRecordMap.get(i);
        	Date date = DateUtil.StrToTime(Logmap.get("create_time").toString());
        	String time = DateUtil.formatSS(date);
        	if(CommonUtils.checkExistOrNot(Logmap.get("accessory").toString())) {
        		
        		map.put("url", Logmap.get("accessory").toString());
        		map.put("dirName",time+Logmap.get("create_by_display").toString());
        		
        		listMap.add(map);
        	}
		}
        
        Map<String,Object> logMap=workOrderPlatformStoreMapper.getSubmitLogByWoId(woId);
        String createTime=logMap.get("create_time").toString();
        Date createDate = DateUtil.StrToTime(createTime);
        createTime = DateUtil.formatSS(createDate);
        //附件信息
        if(CommonUtils.checkExistOrNot(wos.getAccessory())) {
        	Map<String,String> map=new HashMap<>();
        	
        	String annexStr=createTime+logMap.get("create_by_display").toString();
        	map.put("url",wos.getAccessory());
        	map.put("dirName",annexStr);
        	
        	listMap.add(map);
        }
        
        try {
       	 if(listMap.size()>0) {
       		FileStr=downAnnex(wos,response,listMap);
       		newFile=new File(FileStr);
       	}
		} catch (Exception e) {
			e.printStackTrace();
		}
        try {
	        InputStream fis = new BufferedInputStream(new FileInputStream(newFile));  
	        byte[] buffer = new byte[fis.available()];  
	        fis.read(buffer);  
	        fis.close();
	        int length=0;
			if(buffer!=null){
				length = buffer.length;
			}
			String Wono = wos.getWoNo();
			String fileName=Wono+".zip";
			response.setContentType("application/msexcel;charset=utf-8");
			response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("GB2312"),"ISO8859-1")); 
			response.setContentLength(length);
			OutputStream output = response.getOutputStream();
			if(buffer != null){
				output.write(buffer);
			}
			output.flush();
			response.flushBuffer();
			output.close(); 
    } catch (Exception e) {  
        e.printStackTrace();  
    } 
	// 删除创建的新文件  
	 this.deleteFile(newFile);  
	}

	@Override
	public List<String> findStroeCodeByGroupID(String teamId) {
		return workOrderPlatformStoreMapper.findStroeCodeByGroupID(teamId);
	}

	@Override
	public List<Map<String, Object>> findGroupListByTeamid(String teamId) {
		return workOrderPlatformStoreMapper.findGroupListByTeamid(teamId);
	}

	@Override
	public Map<String, Object> queryCount(Parameter parameter) {
		return workOrderPlatformStoreMapper.getAllCount(parameter);
	}
  
	@Override
	public List<Map<String, Object>> getWorkOrderByWoNo(String woNo) {
		return workOrderPlatformStoreMapper.getWorkOrderByWoNo(woNo);
	}

	@Override
	public JSONObject addStoreProcessLog(Parameter parameter) {
		JSONObject result = new JSONObject();
		Date date = new Date();
		try{
			//参数 当前工单id-woId，绑定的工单号wo_no
			String woNos = parameter.getParam().get("woNo").toString();
			String empId = String.valueOf(parameter.getCurrentAccount().getId());
			String empName = parameter.getCurrentAccount().getName();
			String teamId ="";
			String teamName ="";
			int groupType = -1;
			String locationNode = "";
			String woId = parameter.getParam().get("woId").toString();
			String groupId=groupMapper.queryCurrentGroupByWoId(woId);
			String department="";
			if(CommonUtils.checkExistOrNot(groupId)) {
				teamId=groupId;
				Map<String, Object> group = groupMapper.getGroupData(teamId);
				teamName = CommonUtils.checkExistOrNot(group.get("group_name")) ? group.get("group_name").toString() : "";
				groupType = Integer.valueOf(group.get("group_type").toString());
				if(groupType==0){
					department = workOrderPlatformStoreMapper.getDepartmentByGroup(groupId);
				}
				if(groupType==1){
					department = "销售运营部";
				}
			}
			WoProcessLog woProcessLog = new WoProcessLog();
			woProcessLog.setId(UUID.randomUUID().toString());
			woProcessLog.setCreateTime(date);
			woProcessLog.setCreateBy(empId);
			woProcessLog.setCreateByDisplay(empName);
			woProcessLog.setCreateByGroup(teamId);
			woProcessLog.setCreateByGroupDisplay(teamName);
			woProcessLog.setUpdateTime(date);
			woProcessLog.setUpdateBy(empId);
			woProcessLog.setWoStoreId(parameter.getParam().get("woId").toString());
			if(0==groupType){
				locationNode = Constant.ST_NODE;
			}else{
				locationNode = Constant.SO_NODE;
			}
			woProcessLog.setWoLocationNode(locationNode);
			woProcessLog.setAction(Constant.ACTION_ASSC);
			//  前缀+关联了工单号：aaaa,bbbbbb    截取：后面的部分   根据，分割出工单号    加上a标签
			woProcessLog.setProcessed(1);
			woProcessLog.setSort(System.currentTimeMillis());
			
			WorkOrderStore wos = workOrderPlatformStoreMapper.getWorkOrderStoreById(woId);
			List<String> waList = woAssociationMapper.selectByWoNoP(wos.getWoNo());
			//添加工单关联
			String[] woNos2 = woNos.split(",");
			StringBuffer sb = new StringBuffer();
			StringBuffer sb2 = new StringBuffer();
			Set<String> staffsSet = new HashSet<>(Arrays.asList(woNos2));
			for(String woNo:staffsSet){
				if(CommonUtils.checkExistOrNot(woNo)){
					if(waList.contains(woNo)){
						sb.append(woNo+",");
						continue;
					}
					WoAssociation woAssociation = new WoAssociation();
					woAssociation.setWoNoP(wos.getWoNo());
					woAssociation.setWoNoS(woNo);
					woAssociation.setCreateBy(parameter.getCurrentAccount().getId().toString());
					woAssociation.setUpdateBy(parameter.getCurrentAccount().getId().toString());
					woAssociationMapper.insert(woAssociation);
					sb2.append(woNo+",");
				}
			}
			if(sb.length()>1){
				sb.deleteCharAt(sb.length()-1);
				sb.append("已存在关联关系!");
			}
			if(sb2.length()>1){
				sb2.deleteCharAt(sb2.length()-1);
				woProcessLog.setLog((sdf.format(date) +"<br/>"+ (CommonUtils.checkExistOrNot(department)?department:"没有此部门") +"/"+ (CommonUtils.checkExistOrNot(teamName) ? teamName : "[组名加载失败]") + "/" + empName + "<br/>关联工单号："+sb2.toString()).toString());
				workOrderPlatformStoreMapper.addStoreProcessLog(woProcessLog);
				sb2.append("关联成功!\n");
			}
			
			result.put("msg", sb2.toString()+sb.toString());
		}catch (Exception e) {
			result.put("msg", "异常："+e);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getIdByWoNos(String string) {
		return workOrderPlatformStoreMapper.getIdByWoNos(string);
	}
	
	@Override
	public String getIdByWoNo(String string) {
		return workOrderPlatformStoreMapper.getIdByWoNo(string);
	}
	
	@Override
	public Map<String,Object> getWOByWoNo(String string) {
		return workOrderPlatformStoreMapper.getWOByWoNo(string);
	}

	@Override
	public List<Map<String, Object>> getWOByWoNoNoId(String woNo, String woId) {
		return workOrderPlatformStoreMapper.getWOByWoNoNoId(woNo,woId);
	}

	@Override
	public List<Map<String, Object>> getSplitList(String woNo) {
		return workOrderPlatformStoreMapper.getSplitList(woNo);
	}

	/*
	 * 主单的子单是不是都终止了
	 */
	private boolean fatherWoCanProcess(String woNo){
		boolean flag = true;
		List<Map<String,Object>> split_list = getSplitList(woNo+"-");
		for(Map<String,Object> map:split_list){
			String woStatus = map.get("wo_status").toString();
			if("0".equals(woStatus)||"1".equals(woStatus)){
				flag=false;
				break;
			}
		}
		return flag;
	}
	
}
