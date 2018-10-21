package com.bt.workOrder.service.impl;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;
import com.bt.workOrder.controller.param.OMSInterfaceExcpeitonHandlingParam;
import com.bt.workOrder.dao.OMSInterfaceExceptionHandlingMapper;
import com.bt.workOrder.model.OperationResult;
import com.bt.workOrder.model.WorkOrder;
import com.bt.workOrder.service.OMSInterfaceExceptionHandlingService;
import com.bt.workOrder.utils.WorkOrderUtils;

@Transactional
@Service("omsInterfaceExceptionHandlingServiceImpl")
public class OMSInterfaceExceptionHandlingServiceImpl implements OMSInterfaceExceptionHandlingService {

	// 声明公平锁
	private static Lock lock = new ReentrantLock(true);
	@Autowired
	OMSInterfaceExceptionHandlingMapper<T> mapper;
	
	@Override
	public QueryResult<Map<String, Object>> query(OMSInterfaceExcpeitonHandlingParam param) {
		QueryResult<Map<String,Object>> qr= new QueryResult<Map<String,Object>>();
		qr.setResultlist(mapper.query(param));
		qr.setTotalrecord(mapper.count(param));
		return qr;
	}

	@Override
	public JSONObject getData(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		result.put("stores", mapper.getStores());
		Map<String,Object> claim  = mapper.queryById(request.getParameter("id"));
		result.put("claim", claim);
		result.put("warehouses", mapper.getWarehouses());
		result.put("carriers", mapper.getCarriers());
		result.put("data", mapper.getByExpressNumber(claim.get("transNumber").toString()));
		return result;
		
	}

	@Override
	public JSONObject createOMSWorkOrder(WorkOrder wo, HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		result.put("result_code", "FAILURE");
		if(lock.tryLock(15, TimeUnit.SECONDS)) {
			try {
				if(mapper.queryEHById(wo.getClaimId())!=0) {
					result.put("result_content", "当前索赔记录异常已处理");
					
				} else {
					wo.setWoNo(WorkOrderUtils.generateWorkOrderNo());
					//
					Map<String, Object> level= mapper.getInitialLevel(wo.getWoType());
					if(CommonUtils.checkExistOrNot(level)) {
						wo.setWoLevel(level.get("code").toString());
						wo.setWoLevelDisplay(level.get("name").toString());
						OperationResult or = WorkOrderUtils.addWorkOrder(wo, SessionUtils.getEMP(request));
						if(or.getResult()) {
							// 修改异常处理状态
							mapper.shiftProcessFlag(Integer.parseInt(wo.getClaimId()));
							// 返回结果
							result.put("result_code", "SUCCESS");
							result.put("result_content", "工单已生成");
							
						} else {
							result.put("result_content", or.getResultContent());
							
						}
						
					} else {
						result.put("result_content", "工单类型对应级别不存在");
						
					}
					
				}
				
			} catch (Exception e) {
				throw e;
				
			} finally {
				lock.unlock();
				
			}
			
		} else {
			result.put("result_content", "当前用户操作频繁，请稍后再试");
			
		}
		return result;
		
	}

	@Override
	public int updateById(String id) {
		return mapper.updateById(id);
	}

	@Override
	public int queryById(String id) {
		return mapper.queryEHById(id);
	}

}