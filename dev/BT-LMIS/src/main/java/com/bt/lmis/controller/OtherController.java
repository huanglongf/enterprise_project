package com.bt.lmis.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.ContractBasicinfoQueryParam;
import com.bt.lmis.controller.form.TransPoolQueryParam;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ContractBasicinfoService;
import com.bt.lmis.service.OtherService;
import com.bt.utils.BaseConst;

@Controller
@RequestMapping("/control/otherController")
public class OtherController extends BaseController{

	@Resource(name="contractBasicinfoServiceImpl")
	private ContractBasicinfoService<ContractBasicinfo> contractBasicinfoServiceImpl;
	
	@Resource(name="otherServiceImpl")
	private OtherService otherServiceImpl;
	
	@RequestMapping("/daterangepicker")
	public String getTestList(ModelMap map, HttpServletRequest request){
		return "/lmis/daterangepicker_demo";
	}
	
	@RequestMapping("/pagination")
	public String paginationDemo(ModelMap map, HttpServletRequest request){
		return "/lmis/pagination_example";
		
	}	

	@RequestMapping("/validator")
	public String validator(ModelMap map, HttpServletRequest request){
		return "/lmis/validator_demo";
	}

	@RequestMapping("/reportForm")
	public String reportForm(ModelMap map, HttpServletRequest request){
		return "/lmis/report_form";
	}

	@RequestMapping("/settlementForm")
	public String settlementForm(ContractBasicinfoQueryParam queryParam,ModelMap map, HttpServletRequest request){
		PageView<ContractBasicinfo> pageView = new PageView<ContractBasicinfo>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<ContractBasicinfo> qr = contractBasicinfoServiceImpl.findAll(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		return "/lmis/settlement_form";
	}
	
	
	/**
	 * 弹出悬浮对话框
	 */
	
	@RequestMapping("/dialog_example.do")
	public String dialog_example(TransPoolQueryParam queryParam, HttpServletRequest request) throws Exception{
		try{
		}catch(Exception e){
		}
		return "/lmis/test";
	}
	
	@RequestMapping("/dialog.do")
	public String dialog(TransPoolQueryParam queryParam, HttpServletRequest request) throws Exception{
		try{
		}catch(Exception e){
		}
		return "/lmis/dialog_example";
	}
}
