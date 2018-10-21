package com.bt.workOrder.quartz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.workOrder.model.ClaimSchedule;
import com.bt.workOrder.service.WorkOrderManagementService;

public class ClaimWorkOrderAutoProcess {
	// 定义日志
	private static final Logger logger = Logger.getLogger(ClaimWorkOrderAutoProcess.class);
	//
	WorkOrderManagementService<T> workOrderManagementService= SpringUtils.getBean("workOrderManagementServiceImpl");
	
	/**
	 * 
	 * @Description: TODO(索赔工单自动处理)
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年4月6日上午11:18:56
	 */
	public void autoProcess() {
		try {
			List<ClaimSchedule> claimSchedules= workOrderManagementService.getClaimWorkOrderOnDeadline(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
			for(int i= 0; i< claimSchedules.size(); i++) {
				// 方法入参
				Map<String, Object> param= new HashMap<String, Object>();
				// 工单ID
				param.put("wo_id", claimSchedules.get(i).getWoId());
				// 事件
				param.put("event", "saveOperate");
				// 传参内容
				Map<String, Object> content= new HashMap<String, Object>();
				content.put("wo_id", claimSchedules.get(i).getWoId());
				content.put("wo_type", claimSchedules.get(i).getWoType());
				JSONObject value= new JSONObject();
				value.put("code", "claim_status");
				value.put("value", "1");
				JSONArray values= new JSONArray();
				values.add(value);
				content.put("values", values);
				param.put("content", content);
				// 调用方法
				workOrderManagementService.operate(null, param);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		
	}
	
}
