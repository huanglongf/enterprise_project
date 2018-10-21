package com.bt.workOrder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.common.base.LoadingType;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.Constant;
import com.bt.utils.UUIDUtils;
import com.bt.utils.cache.CacheManager;
import com.bt.workOrder.controller.param.WorkOrderParam;
import com.bt.workOrder.model.Operation;
import com.bt.workOrder.model.OperationResult;
import com.bt.workOrder.model.WorkOrder;
import com.bt.workOrder.service.WorkOrderManagementService;

@Controller
@RequestMapping("/control/workOrderController")
public class WorkOrderController extends BaseController{

	private static final Logger logger = Logger.getLogger(WorkOrderController.class);
	
	@Resource(name= "workOrderManagementServiceImpl")
	private WorkOrderManagementService<T> workOrderManagementServiceImpl;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年3月16日上午10:36:22
	 */
	@RequestMapping("/loadingEmployeeInGroupProcessChart.do")
	public void loadingEmployeeInGroupProcessChart(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= workOrderManagementServiceImpl.loadingEmployeeInGroupProcessChart(request, result);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
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
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年3月15日下午6:15:39
	 */
	@RequestMapping("/loadingGroupProcessChart.do")
	public void loadingGroupProcessChart(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= workOrderManagementServiceImpl.loadingGroupProcessChart(request, result);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
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
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年3月15日下午3:25:21
	 */
	@RequestMapping("/loadingWorkOrderProcessStatusChart.do")
	public void loadingWorkOrderProcessStatusChart(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= workOrderManagementServiceImpl.loadingWorkOrderProcessStatusChart(request, result);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
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
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年3月14日下午1:47:33
	 */
	@RequestMapping("/loadingUntreatedWorkOrderChart.do")
	public void loadingUntreatedWorkOrderChart(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= workOrderManagementServiceImpl.loadingUntreatedWorkOrderChart(request, result);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
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
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年3月14日下午1:00:42
	 */
	@RequestMapping("/toChart")
	public String loadingChart(HttpServletRequest request) {
		try {
			request= workOrderManagementServiceImpl.toCharts(request);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		return "/work_order/wo_management/wo_summary_chart";
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param op
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年1月25日下午1:33:51
	 */
	@RequestMapping("/toOperateForm.do")
	public void toOperateForm(Operation op, HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= workOrderManagementServiceImpl.toOperateForm(op, result);
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
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年2月21日下午4:05:27
	 */
	@RequestMapping("/startWorkOrder.do")
	public void startWorkOrder(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= new JSONObject();
			Map<String, Object> param= new HashMap<String, Object>();
			param.put("event", Constant.WO_EVENT_START);
			param.put("wo_id", request.getParameter("wo_id"));
			OperationResult or= workOrderManagementServiceImpl.operate(request, param);
			if(or.getResult()) {
				result.put("result_code", "SUCCESS");
				
			} else {
				result.put("result_code", "FAILURE");
				result.put("result_content", or.getResultContent());
				
			}
			
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
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年1月24日上午9:46:23
	 */
	@RequestMapping("/toProcessForm")
	public String toProcessForm(HttpServletRequest request) {
		try{
			request= workOrderManagementServiceImpl.toProcessForm(request);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			
		}
		return "/work_order/wo_management/"+request.getParameter("return_page");
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年1月22日下午1:14:25
	 */
	@RequestMapping("/shiftStatus.do")
	public void shiftStatus(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= workOrderManagementServiceImpl.shiftStatus(request, result);
			
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
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年1月19日下午4:26:14
	 */
	@RequestMapping("/alloc.do")
	public void alloc(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= workOrderManagementServiceImpl.alloc(request, result);
			
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
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年1月19日下午3:45:38
	 */
	@RequestMapping("/getEmployeeInGroup.do")
	public void getEmployeeInGroup(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= new JSONObject();
		try {
			result.put("employee", workOrderManagementServiceImpl.getEmployeeInGroup(request)) ;
			
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
	
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年1月19日下午3:44:14
	 */
	@RequestMapping("/toAllocForm.do")
	public void toAllocForm(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= workOrderManagementServiceImpl.toAllocForm(request, result);
			
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
	
	/**
	 * 
	 * @Description: TODO
	 * @param wo
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年1月16日下午5:08:46
	 */
	@RequestMapping("/add.do")
	public void add(WorkOrder wo, HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= workOrderManagementServiceImpl.add(wo, request, result);
			
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
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年1月16日上午11:43:54
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
			result= workOrderManagementServiceImpl.getData(request, result);
			
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
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年1月16日上午11:43:48
	 */
	@RequestMapping("/toAddForm.do")
	public void toAddForm(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= workOrderManagementServiceImpl.toAddForm(request, result);
			
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
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年1月16日上午11:43:27
	 */
	@RequestMapping("/getLevelAndException.do")
	public void getLevelAndException(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= workOrderManagementServiceImpl.getLevelAndException(request, result);
			
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
	
	/**
	 * 
	 * @Description: TODO
	 * @param workOrderParam
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年1月10日下午8:50:27
	 */
	@RequestMapping("/query")
	public String query(WorkOrderParam param, HttpServletRequest request) {
		String url=null;
		try{
			//
			PageView<Map<String, Object>> pageView=new PageView<Map<String, Object>>(param.getPageSize()==0?BaseConst.pageSize:param.getPageSize(),param.getPage());
			param.setFirstResult(pageView.getFirstResult());
			param.setMaxResult(pageView.getMaxresult());
			//
			if(param.getLoadingType().equals(LoadingType.MAIN)) {
				// 工单管理查询条件初始化
				request=workOrderManagementServiceImpl.initialize(request);
				//
				url="/work_order/wo_management/wo_management";
				
			} else if(param.getLoadingType().equals(LoadingType.DATA)) {
				url="/work_order/wo_management/wo_management_list";
				
			}
			//
			QueryResult<Map<String, Object>> result=null;
			if(param.getIsQuery()) {
				//根据条件查询合同集合
				result=workOrderManagementServiceImpl.query(param);
				
			} else {
				//
				result=new QueryResult<Map<String, Object>>();
				
			}
			pageView.setQueryResult(result,param.getPage());
			request.setAttribute("pageView",pageView);
			// 缓存查询条件
			if(!CommonUtils.checkExistOrNot(param.getUuid())) {
				param.setUuid("QP-"+new SimpleDateFormat("yyMMddHHmmss").format(new Date())+"-"+UUIDUtils.getUUID8L());
				
			}
            // SessionUtils.setAttr(request,param.getUuid(),param,1800);
			CacheManager.putValue(param.getUuid(), param, 1800);
			//
			request.setAttribute("queryParam",param);
			// 区分查询页面与处理页面
			request.setAttribute("role","query");
			
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		return url;
		
	}
	
	/** 
	 * @Title: back 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param request
	 * @return String 返回类型
	 * @author Ian.Huang
	 * @throws
	 * @date 2017年4月25日 下午1:48:18
	 */
	@RequestMapping("/back")
	public String back(HttpServletRequest request) {
        // WorkOrderParam param=(WorkOrderParam)SessionUtils.getAttr(request, request.getParameter("uuid").toString());
		WorkOrderParam param=(WorkOrderParam)CacheManager.getValue(request.getParameter("uuid").toString());
		// 返回为全局加载
		param.setLoadingType(LoadingType.MAIN);
		return query(param, request);
		
	}
	
	/**
	 * @Title: ensureWorkOrderDetail
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @param response
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年6月7日 上午10:55:59
	 */
	@RequestMapping("/ensureWorkOrderDetail.do")
	public void ensureWorkOrderDetail(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= workOrderManagementServiceImpl.ensureWorkOrderDetail(request, result);
			
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
	
}