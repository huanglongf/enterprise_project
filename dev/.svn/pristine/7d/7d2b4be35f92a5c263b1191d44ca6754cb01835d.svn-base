package com.bt.workOrder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.common.controller.BaseController;
import com.bt.common.controller.model.TableFunctionConfig;
import com.bt.common.controller.param.Parameter;
import com.bt.common.service.TempletService;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.BaseConst;
import com.bt.workOrder.controller.param.OMSInterfaceExcpeitonHandlingParam;
import com.bt.workOrder.model.WorkOrder;
import com.bt.workOrder.service.OMSInterfaceExceptionHandlingService;

/**
 * @Title:OMSInterfaceExcpeitonHandlingController
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年4月10日下午5:46:56
 */
@Controller
@RequestMapping("/control/omsInterfaceExcpeitonHandlingController")
public class OMSInterfaceExceptionHandlingController extends BaseController {

	private static final Logger logger = Logger.getLogger(OMSInterfaceExceptionHandlingController.class);
	
	@Resource(name = "omsInterfaceExceptionHandlingServiceImpl")
	private OMSInterfaceExceptionHandlingService service;
	@Resource(name="templetServiceImpl")
	private TempletService<T> templetService;
	
	/**
	 * 
	 * @Description: TODO
	 * @param omsInterfaceExcpeitonHandlingParam
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年4月11日上午11:01:13
	 */
	@RequestMapping("/load")
	public String load(OMSInterfaceExcpeitonHandlingParam param, HttpServletRequest request) {
		String url= null;
		Parameter parameter = new Parameter();
		parameter = generateParameter(parameter, request);
		try{
			PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(param.getPageSize() == 0 ? BaseConst.pageSize : param.getPageSize() , param.getPage());
			param.setFirstResult(pageView.getFirstResult());
			param.setMaxResult(pageView.getMaxresult());
			url = "data".equals(parameter.getParam().get("pageName")) ? "/templet/table" : "/work_order/oms_interface_exception_handling/" + parameter.getParam().get("pageName");
			QueryResult<Map<String, Object>> data= null;
			if(param.getIsQuery()) {
				//根据条件查询合同集合
				data = service.query(param);
			} else {
				data = new QueryResult<Map<String, Object>>();
			}
			pageView.setQueryResult(data, param.getPage()); 
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", param);
			// 表格功能配置
			Map<String, Object> config = new HashMap<String, Object>();
			config.put("param1", "传参样例1");
			request.setAttribute("tableFunctionConfig", JSONObject.toJSONString(new TableFunctionConfig(parameter.getParam().get("tableName").toString(), true, config)));
			request.setAttribute("tableColumnConfig", templetService.loadingTableColumnConfig(parameter));
			request.setAttribute("tableName", parameter.getParam().get("tableName"));
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return url;
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年4月12日上午10:21:13
	 */
	@RequestMapping("/getData.do")
	public void getData(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= service.getData(request, result);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result= new JSONObject();
			result.put("result_code", "ERROR");
			result.put("result_content", "操作失败,失败原因:"+ e.getMessage());
			
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
	
	@RequestMapping("/createOMSWorkOrder.do")
	public void createOMSWorkOrder(WorkOrder wo, HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= service.createOMSWorkOrder(wo, request, result);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result= new JSONObject();
			result.put("result_code", "ERROR");
			result.put("result_content", "操作失败,失败原因:"+ e.getMessage());
			
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
	@ResponseBody
	@RequestMapping("/update.do")
	public JSONObject update(HttpServletRequest request, HttpServletResponse response){
		String idStr = request.getParameter("ids");
		String ids[] = idStr.substring(1,idStr.length()).split(",");
		int num = 0;
		JSONObject result= new JSONObject();
		try {
			for(String id:ids){
				int res = service.updateById(id);
				if(res==1){
					num++;
				}
			}
			result.put("result_code", "SUCCESS");
			result.put("result_content", "有"+num+"条数据操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result_code", "ERROR");
			result.put("result_content", "操作失败,失败原因:"+ e.getMessage());
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/judgeFlag.do")
	public JSONObject judgeFlag(HttpServletRequest request, HttpServletResponse response){
		JSONObject result= new JSONObject();
		try {
			int eh  = service.queryById(request.getParameter("id"));
			if(eh==0){
				result.put("result_code", "unprocess");
			}else{
				result.put("result_code", "processed");
				result.put("result_content", "数据索赔状态已处理或者已反馈，不能生成工单");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result_code", "error");
			result.put("result_content", "操作失败,失败原因:"+ e.getMessage());
		}
		return result;
	}
	
}