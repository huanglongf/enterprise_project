package com.bt.lmis.controller;

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
import com.bt.lmis.controller.form.SummaryQueryParam;
import com.bt.lmis.model.ExpressSummary;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ExpressBalanceService;
import com.bt.lmis.service.ExpressContractService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.Constant;

/**
 * @Title:ExpressBalanceController
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年11月27日下午12:32:18
 */
@Controller
@RequestMapping("/control/expressBalanceController")
public class ExpressBalanceController extends BaseController {

	private static final Logger logger = Logger.getLogger(ExpressBalanceController.class);
	
	@Resource(name = "expressContractServiceImpl")
	private ExpressContractService<?> expressContractServiceImpl;
	@Resource(name = "expressBalanceServiceImpl")
	private ExpressBalanceService expressBalanceServiceImpl;
	
	/**
	 * 
	 * @Description: TODO(异常运单明细导出)
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月20日下午8:17:39
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
			result= expressBalanceServiceImpl.exportErrorWaybill(queryParam, request, result);
			
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
	 * @Description: TODO(导出报表)
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年11月7日下午2:46:09
	 */
	@RequestMapping("/exportReport")
	public void exportReport(HttpServletRequest request,HttpServletResponse response){
		PrintWriter out= null;
		try {
			out= response.getWriter();
			out.write(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP")+ request.getParameter("express_name")+ request.getParameter("balance_month")+ "月结算报表"+ Constant.SEPARATE_POINT+ Constant.SUF_XLSX);
			out.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		} finally {
			out.close();
			
		}
		
	}
	
	/**
	 * 
	 * @Description: TODO(查询结算明细)
	 * @param queryParam
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年11月7日下午5:29:18
	 */
	@RequestMapping("/balanceDetailList")
	public String balanceDetailList(ExpressBalanceDetialQueryParam queryParam, HttpServletRequest request) {
		try{
			//根据条件查询合同集合
			PageView<Map<String, Object>> pageView= new PageView<Map<String, Object>>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<Map<String, Object>> qr= expressBalanceServiceImpl.findAllBalanceDetail(queryParam, request);
			pageView.setQueryResult(qr, queryParam.getPage());
			request.setAttribute("pageView", pageView);
			request.setAttribute("con_id", queryParam.getCon_id());
			request.setAttribute("express_code", queryParam.getExpress_code());
			request.setAttribute("express_name", queryParam.getExpress_name());
			request.setAttribute("balance_month", request.getParameter("balance_month"));
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			
		}
		return "/lmis/express_contract/express_balance_detail";
		
	}
	
	/**
	 * 
	 * @Description: TODO(查询结算汇总报表)
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年11月7日下午2:39:53
	 */
	@RequestMapping("/balanceSummaryList")
	public String balanceSummaryList(HttpServletRequest request){
		try{
			// 查询结算主体该月汇总数据
			ExpressSummary expressSummary= expressBalanceServiceImpl.findAllSummary(request);
			request.setAttribute("expressSummary", expressSummary);
			request.setAttribute("con_id", expressSummary.getCon_id());
			request.setAttribute("express_code", request.getParameter("express_code"));
			request.setAttribute("express_name", request.getParameter("express_name"));
			request.setAttribute("balance_month", request.getParameter("balance_month"));
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			
		}
		return "/lmis/express_contract/express_balance_summary";
		
	}
	
	/**
	 * 
	 * @Description: TODO(各快递个月汇总页面)
	 * @param queryParam
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年7月25日上午10:53:09
	 */
	@RequestMapping("/summaryList")
	public String summaryList(SummaryQueryParam queryParam, HttpServletRequest request) {
		try{
			//根据条件查询合同集合
			PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			pageView.setQueryResult(expressBalanceServiceImpl.findAllByMonth(queryParam), queryParam.getPage());
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			
		}
		return "/lmis/express_contract/summary_list";
		
	}
	
}