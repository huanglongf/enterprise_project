package com.bt.lmis.balance.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.balance.service.RecoverSettlementDataService;
import com.bt.lmis.balance.util.CommonUtil;

@Controller
@RequestMapping("/control/recoverSettlementDataController")
public class RecoverSettlementDataController {

	private static final Logger logger = Logger.getLogger(RecoverSettlementDataController.class);
	
	@Resource(name= "recoverSettlementDataServiceImpl")
	private RecoverSettlementDataService<T> service;
	
	/**
	 * @Title: toProcessForm
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年7月13日 下午8:00:40
	 */
	@RequestMapping("/toRecoverSettlementLog")
	public String toRecoverSettlementLog(HttpServletRequest request) {
		try{
			request.setAttribute("recoverProcess", service.ensureRecoverProcess());
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			
		}
		return "/lmis/balance/recover_settlement_log";
		
	}
	
	/**
	 * @Title: addRecoverTask
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @param response
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年7月14日 上午10:41:43
	 */
	@RequestMapping("/addRecoverTask.do")
	public void addRecoverTask(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		Lock lock=new ReentrantLock(true);
		try {
			if(lock.tryLock(15, TimeUnit.SECONDS)) {
				result=service.addRecoverTask(CommonUtil.generateParameter(request));
			} else {
				throw new Exception("当前用户操作频繁，请稍后再试");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result=new JSONObject();
			result.put("result_code","ERROR");
			result.put("result_content","操作失败,失败原因:"+ e.getMessage());
			
		} finally {
			lock.unlock();
			
		}
		try {
			out= response.getWriter();
			out.write(result.toString());
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
			
		} finally {
			out.close();
			
		}
		
	}
	
}
