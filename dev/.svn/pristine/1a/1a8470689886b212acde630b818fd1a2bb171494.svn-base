package com.bt.workOrder.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.model.Employee;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.CommonUtils;
import com.bt.utils.DateUtil;
import com.bt.utils.SessionUtils;
import com.bt.workOrder.controller.param.AccountParam;
import com.bt.workOrder.controller.param.ManhoursParam;
import com.bt.workOrder.controller.param.WoHourInterimParam;
import com.bt.workOrder.dao.ManhoursMapper;
import com.bt.workOrder.model.Manhours;
import com.bt.workOrder.model.woHourInterim;
import com.bt.workOrder.service.ManhoursService;
/**
 * @Title:ManhoursServiceImpl
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年1月5日下午7:19:01
 */
@Service
public class ManhoursServiceImpl<T> implements ManhoursService<T> {

	@Autowired
    private ManhoursMapper<T> manhoursMapper;

	@Override
	public JSONObject update(Manhours manhours, HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		if(manhours.getManhours().compareTo(manhoursMapper.selectById(manhours.getId()).getAllocated()) == -1) {
			result.put("result_code", "FAILURE");
			result.put("result_content", "维护工时必须大于等于已分配工时！");
			return result;
			
		} else {
			manhours.setUpdate_by(SessionUtils.getEMP(request).getId().toString());
			manhoursMapper.update(manhours);
			result.put("result_code", "SUCCESS");
			result.put("result_content", "更新工时成功！");
			return result;
			
		}
		
	}
	
	@Override
	public JSONObject add(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		// 所有账号id
		Integer[] accounts= CommonUtils.strToIntegerArray(request.getParameter("accounts"));
		// 日期或日期范围
		List<String> dates= new ArrayList<String>();
		if(request.getParameter("date").contains(" - ")) {
			// 日期范围
			Map<String, String> range= CommonUtils.spiltDateString(request.getParameter("date"));
			String date= range.get("startDate");
			String endDate= range.get("endDate");
			if(date.equals(endDate)) {
				dates.add(date);
				
			} else {
				Calendar dateCalendar= DateUtil.getStrToCalendar(date);
				Calendar endDateCalendar= DateUtil.getStrToCalendar(endDate);
				do {
					dates.add(date);
					dateCalendar.add(Calendar.DATE, 1);
					date= DateUtil.getCalendarToStr(dateCalendar);
					
				} while(!(dateCalendar.compareTo(endDateCalendar) == 0));
				dates.add(endDate);
				
			}
			
		} else {
			dates.add(request.getParameter("date"));
			
		}
		BigDecimal manhours = new BigDecimal(request.getParameter("manhours"));
		String create_by = SessionUtils.getEMP(request).getId().toString();
		int success= 0;
		int failure= 0;
		List<Manhours> workTimes= new ArrayList<Manhours>(); 
		for(int i= 0; i< accounts.length; i++) {
			Integer account= accounts[i];
			for(int j= 0; j< dates.size(); j++) {
				String date= dates.get(j);
				if(manhoursMapper.judgeUnique(account, date) != 0) {
					failure++;
					continue;
					
				}
				Manhours workTime= new Manhours();
				workTime.setId(UUID.randomUUID().toString());
				workTime.setCreate_by(create_by);
				workTime.setAccount(account);
				workTime.setDate(date);
				workTime.setManhours(manhours);
				workTimes.add(workTime);
				success++;
				
			}
			
		}
		if(CommonUtils.checkExistOrNot(workTimes)) {
			manhoursMapper.add(workTimes);
			
		}
		result.put("success", success);
		result.put("failure", failure);
		return result;
		
	}
	
	@Override
	public QueryResult<Map<String,Object>> getSubordinates(AccountParam accountParam, HttpServletRequest request) throws Exception {
		QueryResult<Map<String,Object>> qr= new QueryResult<Map<String,Object>>();
		Integer[] groups= manhoursMapper.getGroups(SessionUtils.getEMP(request).getId());
		if(CommonUtils.checkExistOrNot(groups)) {
			// 此账户是否存在加入组别
			Integer[] subordinateGroups= manhoursMapper.getSubordinateGroups(groups);
			if(CommonUtils.checkExistOrNot(subordinateGroups)) {
				// 是否存在下级组别
				// 所有结果
				List<Map<String, Object>> result= new ArrayList<Map<String,Object>>();
				// 查询当前用户所有存在下级组中账户
				List<Map<String, Object>> subordinates= manhoursMapper.getSubordinates(subordinateGroups, accountParam);
				while(CommonUtils.checkExistOrNot(subordinates)) {
					// 存在账户
					result.addAll(subordinates);
					subordinateGroups= manhoursMapper.getSubordinateGroups(subordinateGroups);
					if(CommonUtils.checkExistOrNot(subordinateGroups)) {
						//
						subordinates= manhoursMapper.getSubordinates(subordinateGroups, accountParam);
						
					} else {
						//　下级组不存在则跳出
						break;
						
					}
					
				}
				if(CommonUtils.checkExistOrNot(result)) {
					// 存在结果集
					Integer fromIndex= accountParam.getFirstResult();
					Integer toIndex= fromIndex+ accountParam.getMaxResult();
					if(toIndex > result.size()) {
						toIndex= result.size();
						
					}
					qr.setResultlist(result.subList(fromIndex, toIndex));
					qr.setTotalrecord(result.size());
					
				}
				
			}
			
		}
		return qr;
		
	}
	
	@Override
	public JSONObject delWorkTime(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		// 客户ID
		String[] ids= request.getParameter("privIds").split(",");
		result.put("total", ids.length);
		List<String> deletable= new ArrayList<String>();
		for(int i= 0; i< ids.length; i++){
			if(manhoursMapper.judgeManhours(ids[i]) != 0){
				// 工时小于等于今天则不能删除
				continue;
				
			}
			deletable.add(ids[i]);
			
		}
		if(CommonUtils.checkExistOrNot(deletable)){
			manhoursMapper.delManhours(deletable);
			
		}
		result.put("success", deletable.size());
		return result;
		
	}
	
	@Override
	public JSONObject shiftStatus(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		String status= request.getParameter("status").toString();
		manhoursMapper.updateStatus(SessionUtils.getEMP(request).getId().toString(), Boolean.parseBoolean(status), request.getParameterValues("ids[]"));
		result.put("result_code", "SUCCESS");
		if(status.equals("false")){
			result.put("result_content", "工时已禁用！");
			
		} else {
			result.put("result_content", "工时已启用！");
			
		}
		return result;
		
	}
	
	@Override
	public QueryResult<Map<String,Object>> query(ManhoursParam queryParam) {
		QueryResult<Map<String,Object>> qr= new QueryResult<Map<String,Object>>();
		if(CommonUtils.checkExistOrNot(queryParam.getDate())) {
			Map<String, String> region= CommonUtils.spiltDateString(queryParam.getDate());
			queryParam.setDate_start(region.get("startDate"));
			queryParam.setDate_end(region.get("endDate"));
			
		}
		qr.setResultlist(manhoursMapper.query(queryParam));
		qr.setTotalrecord(manhoursMapper.countQuery(queryParam));
		return qr;
		
	}

	@Override
	public void insertManhours(String import_status, String error_info, String work_number, String name,
			String data_time, String man_hour, String to_delete) {
		// TODO Auto-generated method stub
		manhoursMapper.insertManhours(import_status,error_info,work_number,name,data_time,man_hour,to_delete);
	}

	@Override
	public QueryResult<woHourInterim> queryHoursList(WoHourInterimParam woHourInterimPar) {
		// TODO Auto-generated method stub
		QueryResult<woHourInterim> queryResult = new QueryResult<woHourInterim>();
		queryResult.setResultlist(manhoursMapper.querysHours(woHourInterimPar));
		queryResult.setTotalrecord(manhoursMapper.countsHours(woHourInterimPar));
		return queryResult;
	}

	@Override
	public void updateWoHourInterimToDelete() {
		// TODO Auto-generated method stub
		manhoursMapper.updateWoHourInterimToDelete();
	}

	@Override
	public List<woHourInterim> queryHourInterim() {
		// TODO Auto-generated method stub
		return manhoursMapper.queryHourInterim();
	}

	@Override
	public Employee findsEmployeeId(String workNumber) {
		// TODO Auto-generated method stub
		return manhoursMapper.findsEmployeeId(workNumber);
	}

	@Override
	public void deleteWoHourInterim(String id) {
		// TODO Auto-generated method stub
		manhoursMapper.deleteWoHourInterim(id);
	}

	@Override
	public void addWoHourInterim(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		manhoursMapper.addWoHourInterim(paramMap);
	}


	@Override
	public void saveWoManhours(String uuid, String createTime, String updateTime, String ids, String dataTime,
			String manHour, String status,  String userName, String update_by) {
		manhoursMapper.saveWoManhours(uuid,createTime,updateTime,ids,dataTime,manHour,status,userName,update_by);
	}

	@Override
	public void updateWoHourInterim(String id, String import_status, String error_info, String work_number, String name,
			String data_time, String man_hour) {
		// TODO Auto-generated method stub
		manhoursMapper.updateWoHourInterim(id,import_status,error_info,work_number,name,data_time, man_hour);
	}

	@Override
	public QueryResult<Map<String, Object>> getWorkOrderAccounts(AccountParam accountParam) throws Exception {
		QueryResult<Map<String,Object>> qr= new QueryResult<Map<String,Object>>();
		qr.setResultlist(manhoursMapper.getWorkOrderAccounts(accountParam));
		qr.setTotalrecord(manhoursMapper.countWorkOrderAccounts(accountParam));
		return qr;
		
	}


}
