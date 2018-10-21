package com.bt.lmis.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.ExpressBalanceDetialQueryParam;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ExpressContractService;
import com.bt.lmis.service.ExpressUsedBalanceService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;

/**
 * @Title:ExpressUsedBalanceController
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年11月27日下午12:30:06
 */
@Controller
@RequestMapping("/control/expressUsedBalanceController")
public class ExpressUsedBalanceController extends BaseController {

	private static final Logger logger = Logger.getLogger(ExpressUsedBalanceController.class);
	
	@Resource(name = "expressUsedBalanceServiceImpl")
	private ExpressUsedBalanceService expressUsedBalanceServiceImpl;
	
	@Resource(name = "expressContractServiceImpl")
	private ExpressContractService<?> expressContractServiceImpl;
	
	/**
	 * 
	 * @Description: TODO(导出异常运单)
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月20日下午9:11:42
	 */
	@RequestMapping("/exportErrorWaybill")
	public void exportErrorWaybill(ExpressBalanceDetialQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out= null;
		JSONObject result= null;
		try {
			result= expressUsedBalanceServiceImpl.exportErrorWaybill(queryParam, request, result);
			
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
	
	@RequestMapping("/exportUsedDetailExcel")
	public void exportUsedDetailExcel(ExpressBalanceDetialQueryParam queryParam, HttpServletRequest request, HttpServletResponse response){
		PrintWriter out = null;
		File excel= null;
		try {
			out = response.getWriter();
			excel= expressUsedBalanceServiceImpl.exportUsedDetailExcel(queryParam, request);
			out.write(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP")+ excel.getName());
			out.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		} finally {
			out.close();
			
		}
		
	}
	
	@RequestMapping("/balanceDetailList")
	public String balanceDetailList(ExpressBalanceDetialQueryParam queryParam, HttpServletRequest request){
		try{
			//根据条件查询合同集合
			PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<Map<String, Object>> qr = expressUsedBalanceServiceImpl.findAllBalanceDetail(queryParam, request);
			pageView.setQueryResult(qr, queryParam.getPage());
			request.setAttribute("pageView", pageView);
			request.setAttribute("con_id", queryParam.getCon_id());
			request.setAttribute("express_code", queryParam.getExpress_code());
			request.setAttribute("express_name", queryParam.getExpress_name());
			request.setAttribute("product_type_code", queryParam.getProduct_type_code());
			request.setAttribute("product_type_name", queryParam.getProduct_type_name());
			request.setAttribute("balance_month", request.getParameter("balance_month"));
			if(CommonUtils.checkExistOrNot(request.getParameter("clientid"))){
				request.setAttribute("clientid", request.getParameter("clientid"));
				
			}
			request.setAttribute("client_name", request.getParameter("client_name"));
			if(CommonUtils.checkExistOrNot(request.getParameter("storeid"))){
				request.setAttribute("storeid", request.getParameter("storeid"));
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			
		}
		return "/lmis/express_contract/express_used_balance_detail";
		
	}
	
}
