package com.bt.workOrder.service.impl;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import com.bt.lmis.basis.dao.StoreManagerMapper;
import com.bt.lmis.basis.model.Store;
import com.bt.workOrder.dao.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.CommonUtils;
import com.bt.utils.Constant;
import com.bt.utils.DateUtil;
import com.bt.utils.SessionUtils;
import com.bt.workOrder.bean.Enclosure;
import com.bt.workOrder.controller.param.WorkOrderParam;
import com.bt.workOrder.model.ClaimSchedule;
import com.bt.workOrder.model.Operation;
import com.bt.workOrder.model.OperationResult;
import com.bt.workOrder.model.WorkOrder;
import com.bt.workOrder.model.WorkOrderEventMonitor;
import com.bt.workOrder.service.WorkOrderManagementService;
import com.bt.workOrder.utils.WorkOrderUtils;

/**
 * @Title:WorkOrderManagementServiceImpl
 * @Description: TODO  
 * @author Ian.Huang
 * @date 2017年1月11日下午1:47:02
 */
@Transactional
@Service
public class WorkOrderManagementServiceImpl<T> implements WorkOrderManagementService<T> {

	// 声明公平锁
	private static Lock lock = new ReentrantLock(true);
	@Autowired
    private WorkOrderManagementMapper<T> mapper;
	@Autowired
	private WoMutualLogMapper woMutualLogMapper;

	@Autowired
	private FailureReasonMapper<T> failureReasonMapper;

	@Autowired
	private GroupMapper<T> groupMapper;

	@Autowired
	private StoreManagerMapper<T> storeManagerMapper;

    @Autowired
    private WoFollowupResultinfoMapper woFollowupResultinfoMapper;
	
	@Override
	public QueryResult<Map<String,Object>> query(WorkOrderParam workOrderParam) {
		if(!CommonUtils.checkExistOrNot(workOrderParam.getSort_by())) {
			workOrderParam.setSort_by("update_time");
			workOrderParam.setSort("DESC");
			
		}
		QueryResult<Map<String,Object>> qr= new QueryResult<Map<String,Object>>();
		qr.setResultlist(mapper.query(workOrderParam));
		qr.setTotalrecord(mapper.countQuery(workOrderParam));
		return qr;
		
	}
	
	@Override
	public List<Map<String,Object>> exportWO(WorkOrderParam workOrderParam) {
	    if(!CommonUtils.checkExistOrNot(workOrderParam.getSort_by())) {
	        workOrderParam.setSort_by("update_time");
	        workOrderParam.setSort("DESC");
	    }
	    List<Map<String,Object>> list = mapper.exportWO(workOrderParam);
	    return list;
	}

	@Override
	public QueryResult<Map<String, Object>> query2(Map<String, Object> map) {
		if(!CommonUtils.checkExistOrNot(map.get("sort_by"))) {
			map.put("sort_by","update_time");
			map.put("sort", "DESC");
		}
		QueryResult<Map<String,Object>> qr= new QueryResult<Map<String,Object>>();
		qr.setResultlist(mapper.query2(map));
		qr.setTotalrecord(mapper.countQuery2(map));
		return qr;
		
	}
	@Override
	public HttpServletRequest initialize(HttpServletRequest request) throws Exception {
		request.setAttribute("wo_type", mapper.getWOType());
		request.setAttribute("store", mapper.getStore());
		request.setAttribute("warehouse", mapper.getWarehouse());
		request.setAttribute("carrier", mapper.getCarrier());
		request.setAttribute("allgroups",groupMapper.findAllGroups());
		return request;
		
	}

	@Override
	public JSONObject getLevelAndException(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		result.put("wo_level", mapper.getLevel(request.getParameter("wo_type")));
		result.put("exception_type", mapper.getException(request.getParameter("wo_type")));
		return result;
		
	}

	@Override
	public JSONObject toAddForm(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		result.put("woNo", WorkOrderUtils.generateWorkOrderNo());
		result.put("wo_type", mapper.getWOType());
		return result;
		
	}

	@Override
	public JSONObject getData(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		result.put("store", mapper.getStore());
		result.put("warehouse", mapper.getWarehouse());
		result.put("carrier", mapper.getCarrier());
		result.put("data", mapper.getByExpressNumber(request.getParameter("express_number")));
		result.put("wo_num", mapper.getExpressNumberWithWorkOrder(request.getParameter("express_number")));
		result.put("packages", mapper.listOrderDetailByWaybill(request.getParameter("express_number")));
		if(result.get("data")!=null){
			Object storeCode = ((Map<String,Object>)result.get("data")).get("store_code");
			Object transportCode = ((Map<String,Object>)result.get("data")).get("transport_code");
			if(storeCode!=null&&!"".equals(storeCode)){
			    Store store = storeManagerMapper.selectByStoreCode(storeCode.toString());
	            result.put("storebj",store.getStorebj());//店铺保价
			}
			if(transportCode!=null&&!"".equals(transportCode)){
			    List<Map<String, Object>> mapList = storeManagerMapper.selectTransportByStoreCode(storeCode.toString());
	            if(mapList.size()>0){//快递保价
	                for(Map<String, Object> map:mapList) {
	                    if(transportCode.toString().equals(map.get("transport_code"))) {
	                        result.put("tansportbj","true");
	                    }
	                }

	            }else{
	                result.put("tansportbj","false");
	            }    
			}
			
		}

		return result;
		
	}

	@Override
	public JSONObject add(WorkOrder wo, HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		String result_content="";
		OperationResult or=null;
		if(CommonUtils.checkExistOrNot(wo.getBySelf())) {
			Map<String, Object> param= new HashMap<String, Object>();
			param.put("type_code", "create");
			param.put("wo", wo);
			or= operate(request, param);
			
		} else {
			or= WorkOrderUtils.addWorkOrder(wo, SessionUtils.getEMP(request));
			
		}
		if(or.getResult()) {
			// 分配成功
			result.put("result_code", "SUCCESS");
			result_content= "工单新增成功！";
			result.put("woId", or.getReturnMap().get("woId"));
			
		} else {
			result.put("result_code", "FAILURE");
			result_content=or.getResultContent();
			
		}
		result.put("result_content", result_content);
		return result;
		
	}

	@Override
	public JSONObject toAllocForm(HttpServletRequest request, JSONObject result) throws Exception {
		result=new JSONObject();
		List<WorkOrder> wos=mapper.getWorkOrdersById(request.getParameterValues("ids[]"));
		List<Map<String, Object>> groups=null;
		for(int i=0; i<wos.size(); i++) {
			List<Map<String, Object>> temp=mapper.getGroupInPower(wos.get(i), mapper.getWarehouseType(wos.get(i).getWarehouses()));
			if(i==0) {
				groups=temp;
				
			} else {
				groups=WorkOrderUtils.getSameGrouops(groups, temp);
				
			}
			
		}
		List<Map<String, Object>> noControlGroups = mapper.getAllNoControlGroup();
		groups.addAll(noControlGroups);
		groups = new ArrayList<Map<String, Object>>(new LinkedHashSet<Map<String, Object>>(groups));
		// 符合所有权限的组别
		result.put("groups", groups);
		return result;
		
	}

	@Override
	public List<Map<String, Object>> getEmployeeInGroup(HttpServletRequest request) throws Exception {
		Integer except= null;
		if(request.getParameter("flag").equals("1")) {
			except= SessionUtils.getEMP(request).getId();
			
		}
		return mapper.getEmployeeInGroup(Integer.parseInt(request.getParameter("group")), except);
		
	}

	@Override
	public JSONObject alloc(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		// 入参
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("event", Constant.WO_EVENT_ALLOC_M);
		param.put("outManhours", request.getParameter("outManhours"));
		// 工单id
		String[] ids=request.getParameterValues("ids[]");
		String text="";
		for(int i= 0; i< ids.length; i++) {
			text+= "第" + (i + 1) + "行";
			param.put("wo_id", ids[i]);
			OperationResult or=operate(request, param);
			if(or.getResult()) {
				text+= "操作成功";
				
			} else {
				text+= "操作失败>>" + or.getResultContent();
				
			}
			text+="；\n";
			
		}
		result.put("result_content", text);
		return result;
		
	}

	@Override
	public JSONObject shiftStatus(HttpServletRequest request, JSONObject result) throws Exception {
 		result= new JSONObject();
 		//
 		Map<String, Object> param= new HashMap<String, Object>();
 		param.put("event", request.getParameter("operate"));
		param.put("remark", request.getParameter("remark"));
		//
 		String batch_flag= request.getParameter("batch_flag");
 		if(batch_flag.equals("process")) {
 			param.put("wo_id", CommonUtils.toStringArray(request.getParameter("privIds"))[0]);
 			param.put("moveType", batch_flag);
 			OperationResult or= operate(request, param);
 			if(or.getResult()) {
 				result.put("result_code", "SUCCESS");
 				result.put("result_content", "操作成功！");
 				
 			} else {
 				result.put("result_code", "FAILURE");
 				result.put("result_content", or.getResultContent());
 				
 			}
 			
 		} else if(batch_flag.equals("query")) {
 			//
 			String text= "";
 			String[] ids= CommonUtils.toStringArray(request.getParameter("privIds"));
 			for(int i= 0; i< ids.length; i++){
 				text+= "第" + (i + 1) + "行";
 				param.put("wo_id", ids[i]);
 				param.put("moveType", batch_flag);
 				OperationResult or= operate(request, param);
 				if(or.getResult()) {
 					text+= "操作成功";
 					
 				} else {
 					text+= "操作失败>>" + or.getResultContent();
 					
 				}
 				text+="；\n";
 				
 			}
 			result.put("result_content", text);
 			
 		}
		return result;
		
	}

	@Override
	public OperationResult operate(HttpServletRequest request, Map<String, Object> param) throws Exception {
		// 返回处理结果
		OperationResult or=null;
		if(lock.tryLock(15, TimeUnit.SECONDS)) {
			try {
				if(CommonUtils.checkExistOrNot(param.get("type_code"))) {
					or=WorkOrderUtils.addWorkOrder((WorkOrder)param.get("wo"), SessionUtils.getEMP(request));
					
				} else {
					or=new OperationResult();
					WorkOrderEventMonitor woem= WorkOrderUtils.operateWorkOrder(request, param);
					or.setResult(woem.getEvent_status());
					or.setResultContent(woem.getRemark());
					
				}
				
			} catch (Exception e) {
				throw e;
				
			} finally {
				lock.unlock();
				
			}
			
		} else {
			or= new OperationResult();
			or.setResult(false);
			or.setResultContent("当前用户操作频繁，请稍后再试");
			
		}
		return or;
		
	}

	@Override
	public HttpServletRequest toProcessForm(HttpServletRequest request) throws Exception {
		// 区分工单管理与工单处理平台（query-工单管理/process-工单处理平台）
		request.setAttribute("type", request.getParameter("type"));
		// 区分查询页面与处理页面（query-查询页面/process-处理页面）
		request.setAttribute("role", "process");
		//
		WorkOrder wo= mapper.selectWorkOrderById(request.getParameter("wo_id"));
		//查询这个工单新建的附件
		List<String> fileNamelist = woMutualLogMapper.selectNewAccessory(wo.getWoNo());
		if(fileNamelist!=null&&fileNamelist.size()>0){
			request.setAttribute("createdFile", fileNamelist.get(0));
		}
		request.setAttribute("failureReason",failureReasonMapper.selectFailureReason());
		// 主信息显示
		request.setAttribute("wo_display", mapper.displayWorkOrder(wo.getId()));
		// 运单生成工单数量
		request.setAttribute("wo_num", mapper.getExpressNumberWithWorkOrder(wo.getExpressNumber()));
		// 事件监控
		request.setAttribute("event_monitor", mapper.queryWorkOrderEventMonitor(wo.getId()));
		// 包裹明细
		request.setAttribute("packageDetail", mapper.queryPackageDetail(wo.getExpressNumber()));
		request.setAttribute("packageId", wo.getPackageId());
		request.setAttribute("remarkMain", mapper.getRemark(wo.getId(),wo.getWoType()));
		// 路由明细
		request.setAttribute("routeDetail", mapper.queryRouteDetail(wo.getExpressNumber()));
		// 预警信息
		request.setAttribute("warningDetail", mapper.queryWarningDetail(wo.getExpressNumber()));
		// 工单类型
		request.setAttribute("woTypes", mapper.getWOType());
		// 工单级别
		request.setAttribute("woLevels", mapper.getLevel(wo.getWoType()));
		// 升降级原因
		request.setAttribute("woLevelAlterReason", mapper.getLevelAlterReason(wo.getWoType()));
		// 异常类型
		request.setAttribute("exceptions", mapper.getException(wo.getWoType()));
		//跟进结果 维护
		request.setAttribute("resultInfo", mapper.selectResultInfo(wo.getWoType(),""));
		// 符合所有权限的组别
		request.setAttribute("groups", mapper.getGroupInPower(wo, mapper.getWarehouseType(wo.getWarehouses())));
		// 查询参数uuid
		request.setAttribute("uuid", request.getParameter("uuid"));
		return request;
		
	}

	@Override
	public JSONObject toOperateForm(Operation op, JSONObject result) throws Exception {
		result= new JSONObject();
		List<Map<String, Object>> columns= mapper.getColumnsInPage(op.getWo_type());
        result.put("remark_new",woFollowupResultinfoMapper.selectAll(op.getWo_type(),null));//跟进结果
		List<Map<String, Object>> values= new ArrayList<Map<String, Object>>();
		for(int i= 0; i< columns.size(); i++) {
			// 显示在页面上的值（标题/内容）
			Map<String, Object> value= new HashMap<String, Object>();
			Map<String, Object> column= columns.get(i);
			// 内容
            String column_code = column.get("column_code").toString();
            if(column_code.equals("sendTime")){
                continue;
            }
            
			// 标题
			value.put("code", column.get("column_code"));
			value.put("title", column.get("column_name"));
			value.put("control_type", column.get("control_type"));
			
			op.setColumn_code(column_code);
//			Object content= "";
//			if(op.getWo_type().equals("OMSC")||op.getWo_type().equals("NOMSC")){
//				content=mapper.getValue(op);
//			}
			Object content= mapper.getValue(op);
//			if(column_code.equals("resultInfo")){
//				content="";
//			}
			if(CommonUtils.checkExistOrNot(content)) {
				value.put("value", content);
				
			} else {
				value.put("value", "");
				
			}
			// 封装
			values.add(value);
			
		}
		result.put("values", values);
		if(op.getWo_type().equals("OMSC") || op.getWo_type().equals("NOMSC")) {
			result.put("claimDetail", mapper.getClaimDetailByWoId(op.getWo_id()));
			
		}
		return result;
		
	}

	@Override
	public JSONObject saveOperation(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		//
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("event","saveOperate");	
		param.put("wo_id",JSON.parseObject(request.getParameter("json").toString(), Map.class).get("wo_id").toString());
		OperationResult or=operate(request, param);
		if(or.getResult()) {
			result.put("result_code", "SUCCESS");
			result.put("result_content", "操作成功！");
			
		} else {
			result.put("result_code", "FAILURE");
			result.put("result_content", or.getResultContent());
			
		}
		return result;
		
	}

	@Override
	public JSONObject monitoringStatus(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		List<Map<String, Object>> status= mapper.monitoringStatus(SessionUtils.getEMP(request).getId().toString());
		Map<String, Object> temp= null;
		for(int i= 0; i < status.size(); i++) {
			temp= status.get(i);
			result.put(temp.get("wo_process_status").toString(), temp.get("current_num"));
			
		}
		return result;
		
	}

	@Override
	public List<Map<String, Object>> getWaybillDetailByWaybill(String waybill) {
		return mapper.getWaybillDetailByWaybill(waybill);
		
	}

	@Override
	public JSONObject shiftGroups(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		WorkOrder wo= mapper.selectWorkOrderById(request.getParameter("wo_id"));
		wo.setWoLevel(request.getParameter("work_level"));
		// 符合所有权限的组别
		result.put("groups", mapper.getGroupInPower(wo, mapper.getWarehouseType(wo.getWarehouses())));
		return result;
		
	}

	@Override
	public JSONObject updateWorkOrder(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		OperationResult or= operate(request, CommonUtils.request2Map(request));
		if(or.getResult()) {
			result.put("result_code", "SUCCESS");
			result.put("result_content", "操作成功！");
			
		} else {
			result.put("result_code", "FAILURE");
			result.put("result_content", or.getResultContent());
			
		}
		return result;
	}

	@Override
	public JSONObject judgePower(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		WorkOrder wo= mapper.selectWorkOrderById(request.getParameter("wo_id"));
		wo.setWoLevel(request.getParameter("wo_level"));
		wo.setWoType(request.getParameter("wo_type"));
	    System.out.println(request.getParameter("wo_type"));
		if(WorkOrderUtils.judgePower(SessionUtils.getEMP(request).getId(), wo)) {
			result.put("result_code", "SUCCESS");
			
		} else {
			result.put("result_code", "FAILURE");
			result.put("result_content", "异动工单无权限处理，请转发工单");
			
		}
		return result;
	}

	@Override
	public HttpServletRequest toCharts(HttpServletRequest request) throws Exception {
		request.setAttribute("defaultEndDate", DateUtil.getCalendarToStr(Calendar.getInstance()));
		Calendar now= Calendar.getInstance();
		now.add(Calendar.DAY_OF_MONTH, -6);
		request.setAttribute("defaultStartDate", DateUtil.getCalendarToStr(now));
		return request;
		
	}

	@Override
	public JSONObject loadingUntreatedWorkOrderChart(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		String[] dateRange= request.getParameter("dateRange").split(" - ");
		String startDate= dateRange[0];
		String endDate= dateRange[1];
		// 获取汇总
		List<Map<String, Object>> summary= mapper.untreatedWorkOrderSummary(startDate, endDate);
		// 时间段集合
		List<String> dates= new ArrayList<String>();
		if(startDate.equals(endDate)) {
			dates.add(startDate);
			
		} else {
			Calendar dateCalendar= DateUtil.getStrToCalendar(startDate);
			Calendar endDateCalendar= DateUtil.getStrToCalendar(endDate);
			do {
				dates.add(startDate);
				dateCalendar.add(Calendar.DATE, 1);
				startDate= DateUtil.getCalendarToStr(dateCalendar);
				
			} while(!(dateCalendar.compareTo(endDateCalendar) == 0));
			dates.add(endDate);
			
		}
		result.put("dates", dates.toArray(new String[dates.size()]));
		// 获取legend
		List<String> subject= new ArrayList<String>();
		JSONArray series= new JSONArray();
		String temp= "";
		JSONObject json= null;
		int[] data= null;
		for(int i= 0; i< summary.size(); i++) {
			if(!temp.equals(summary.get(i).get("subject"))) {
				if(i != 0) {
					// 非一开始的轮询，需清算之前的值
					json.put("data", data);
					series.add(json);
					
				}
				temp= summary.get(i).get("subject") + "";
				// 添加legend值
				subject.add(summary.get(i).get("subject") + "");
				//
				Map<String, Object> normal= new HashMap<String, Object>();
				normal.put("show", false);
				normal.put("position", "insideTop");
				//
				JSONObject label= new JSONObject();
				label.put("normal", normal);
				//
				json= new JSONObject();
				json.put("name", temp);
				json.put("type", "bar");
				json.put("stack", "总量");
				json.put("label", label);
				//
				data= new int[dates.size()];
				
			}
			for(int j= 0; j< dates.size(); j++) {
				if(summary.get(i).get("create_time").equals(dates.get(j))) {
					data[j]= Integer.parseInt(summary.get(i).get("num") + "");
					break;
					
				}
				
			}
			if(i == summary.size() - 1) {
				// 结尾清算
				json.put("data", data);
				series.add(json);
				
			}
			
		}
		result.put("subject", subject.toArray(new String[subject.size()]));
		result.put("series", series);
		return result;
		
	}

	@Override
	public JSONObject loadingWorkOrderProcessStatusChart(HttpServletRequest request, JSONObject result)
			throws Exception {
		result= new JSONObject();
		String[] dateRange= request.getParameter("dateRange").split(" - ");
		String startDate= dateRange[0];
		String endDate= dateRange[1];
		// 获取汇总
		List<Map<String, Object>> summary= mapper.workOrderProcessStatusSummary(startDate, endDate);
		// 获取legend
		List<String> subject= new ArrayList<String>();
		//
		JSONArray data= new JSONArray();
		for(int i= 0; i< summary.size(); i++) {
			subject.add(summary.get(i).get("subject") + "");
			//
			JSONObject json= new JSONObject();
			json.put("name", summary.get(i).get("subject") + "");
			json.put("value", summary.get(i).get("num") + "");
			data.add(json);
			
		}
		result.put("subject", subject.toArray(new String[subject.size()]));
		result.put("data", data);
		return result;
		
	}

	@Override
	public JSONObject loadingGroupProcessChart(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		String[] dateRange= request.getParameter("dateRange").split(" - ");
		String startDate= dateRange[0];
		String endDate= dateRange[1];
		// 获取汇总
		List<Map<String, Object>> summary= mapper.loadingGroupProcessSummary(startDate, endDate);
		//
		List<String> groups= new ArrayList<String>();
		for(int i= 0; i< summary.size(); i++) {
			if(!groups.contains(summary.get(i).get("groups"))) {
				groups.add(summary.get(i).get("groups") + "");
				
			}
			
		}
		result.put("groups", groups);
		// 获取legend
		List<String> subject= new ArrayList<String>();
		JSONArray series= new JSONArray();
		String temp= "";
		JSONObject json= null;
		int[] data= null;
		for(int i= 0; i< summary.size(); i++) {
			if(!temp.equals(summary.get(i).get("subject"))) {
				if(i != 0) {
					// 非一开始的轮询，需清算之前的值
					json.put("data", data);
					series.add(json);
					
				}
				temp= summary.get(i).get("subject") + "";
				// 添加legend值
				subject.add(summary.get(i).get("subject") + "");
				//
				Map<String, Object> normal= new HashMap<String, Object>();
				normal.put("show", false);
				normal.put("position", "insideTop");
				//
				JSONObject label= new JSONObject();
				label.put("normal", normal);
				//
				json= new JSONObject();
				json.put("name", temp);
				json.put("type", "bar");
				json.put("stack", "总量");
				json.put("label", label);
				//
				data= new int[groups.size()];
				
			}
			for(int j= 0; j< groups.size(); j++) {
				if(summary.get(i).get("groups").equals(groups.get(j))) {
					data[j]= Integer.parseInt(summary.get(i).get("num") + "");
					break;
					
				}
				
			}
			if(i == summary.size() - 1) {
				// 结尾清算
				json.put("data", data);
				series.add(json);
				
			}
			
		}
		result.put("subject", subject.toArray(new String[subject.size()]));
		result.put("series", series);
		return result;
		
	}

	@Override
	public JSONObject loadingEmployeeInGroupProcessChart(HttpServletRequest request, JSONObject result)
			throws Exception {
		result= new JSONObject();
		String[] dateRange= request.getParameter("dateRange").split(" - ");
		String startDate= dateRange[0];
		String endDate= dateRange[1];
		// 获取汇总
		List<Map<String, Object>> summary= mapper.loadingEmployeeInGroupProcessSummary(request.getParameter("group"), startDate, endDate);
		//
		List<String> employees= new ArrayList<String>();
		for(int i= 0; i< summary.size(); i++) {
			if(!employees.contains(summary.get(i).get("employee"))) {
				employees.add(summary.get(i).get("employee") + "");
				
			}
			
		}
		result.put("employees", employees);
		// 获取legend
		List<String> subject= new ArrayList<String>();
		JSONArray series= new JSONArray();
		String temp= "";
		JSONObject json= null;
		int[] data= null;
		for(int i= 0; i< summary.size(); i++) {
			if(!temp.equals(summary.get(i).get("subject"))) {
				if(i != 0) {
					// 非一开始的轮询，需清算之前的值
					json.put("data", data);
					series.add(json);
					
				}
				temp= summary.get(i).get("subject") + "";
				// 添加legend值
				subject.add(summary.get(i).get("subject") + "");
				//
				Map<String, Object> normal= new HashMap<String, Object>();
				normal.put("show", false);
				normal.put("position", "insideTop");
				//
				JSONObject label= new JSONObject();
				label.put("normal", normal);
				//
				json= new JSONObject();
				json.put("name", temp);
				json.put("type", "bar");
				json.put("stack", "总量");
				json.put("label", label);
				//
				data= new int[employees.size()];
				
			}
			for(int j= 0; j< employees.size(); j++) {
				if(summary.get(i).get("employee").equals(employees.get(j))) {
					data[j]= Integer.parseInt(summary.get(i).get("num") + "");
					break;
					
				}
				
			}
			if(i == summary.size() - 1) {
				// 结尾清算
				json.put("data", data);
				series.add(json);
				
			}
			
		}
		result.put("subject", subject.toArray(new String[subject.size()]));
		result.put("series", series);
		return result;
		
	}

	@Override
	public List<ClaimSchedule> getClaimWorkOrderOnDeadline(String now) throws Exception {
		return mapper.getClaimWorkOrderOnDeadline(now);
		
	}

	@Override
	public JSONObject refreshWoem(HttpServletRequest request, JSONObject result) throws Exception {
		if(!CommonUtils.checkExistOrNot(result)) {
			result= new JSONObject();
			
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> param=JSON.parseObject(request.getParameter("json").toString(), Map.class);
		String operation_type=param.get("operation_type").toString();
		if(result.get("result_code").equals("SUCCESS") && (operation_type.equals("1") || operation_type.equals("2"))) {
			result.put("woems", mapper.queryWorkOrderEventMonitor(param.get("wo_id").toString()));
			
		}
		return result;
		
	}

	@Override
	public JSONObject ensureWorkOrderDetail(HttpServletRequest request, JSONObject result) throws Exception {
		result=new JSONObject();
		result.put("result_code", "SUCCESS");
		result.put("wo", mapper.getWorkOrderByExpressNumber(request.getParameter("expressNumber")));
		return result;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<T> get_wo_level(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return (ArrayList<T>) mapper.getLevel((String)param.get("wo_type"));
//		return mapper.get_wo_level(param)==null?new ArrayList<T>():mapper.get_wo_level(param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<T> get_error_type(Map<String, Object> param) {
		// TODO Auto-generated method stub
		try {
			return (ArrayList<T>) mapper.getException((String)param.get("wo_type"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<T> get_reason_info(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return (ArrayList<T>) mapper.getLevelAlterReason((String)param.get("wo_type"));
	}

	@Override
	public List<Enclosure> selectAllByWoNo(String woNo) {
		List<Map<String, String>>  wList = woMutualLogMapper.selectAllByWoNo(woNo);
		List<Enclosure> elist = new ArrayList<>();
		for(Map<String, String> map:wList){
			Enclosure e = new Enclosure(map.get("log"),map.get("process_remark"),map.get("accessory"));
			elist.add(e);
		}
		return elist;
	}

    @Override
    public List<Map<String, Object>> getWOType(){
        return mapper.getWOType();
    }

	
}