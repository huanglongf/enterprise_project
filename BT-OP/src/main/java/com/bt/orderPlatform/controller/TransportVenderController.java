package com.bt.orderPlatform.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bt.orderPlatform.page.PageView;
import com.bt.orderPlatform.page.QueryResult;
import com.alibaba.fastjson.JSONObject;
import com.bt.base.BaseConstant;
import com.bt.base.session.SessionUtil;
import com.bt.orderPlatform.controller.form.TransportVenderQueryParam;
import com.bt.orderPlatform.model.TransportVender;
import com.bt.orderPlatform.service.TransportVenderService;
import com.bt.sys.model.Account;
import com.bt.sys.util.SysUtil;
/**
 * 快递信息表控制器
 *
 */
@Controller
@RequestMapping("/control/orderPlatform/transportVenderController")
public class TransportVenderController {

	private static final Logger logger = Logger.getLogger(TransportVenderController.class);
	
	/**
	 * 快递信息表服务类
	 */
	@Resource(name = "transportVenderServiceImpl")
	private TransportVenderService<TransportVender> transportVenderService;
	
	@RequestMapping("/initail")
	public String initail(TransportVenderQueryParam queryParam, HttpServletRequest request) throws Exception{
	List<TransportVender> list=	transportVenderService.getVender(queryParam);
	request.setAttribute("venders", list);
	QueryResult<TransportVender> qr=new QueryResult<TransportVender>();
	PageView<TransportVender> pageView = new PageView<TransportVender>(queryParam.getPageSize()==0?BaseConstant.pageSize:queryParam.getPageSize(), queryParam.getPage());
	queryParam.setFirstResult(pageView.getFirstResult());
	queryParam.setMaxResult(pageView.getMaxresult());
	qr.setResultlist(transportVenderService.pageData(queryParam));
	qr.setTotalrecord(list.size());
	pageView.setQueryResult(qr, queryParam.getPage()); 
	request.setAttribute("pageView", pageView);
	request.setAttribute("queryParam", queryParam);
	
	return  "/orderPlatform/express";
	}
	
	
	@RequestMapping("/page")
	public String page(TransportVenderQueryParam queryParam, HttpServletRequest request) throws Exception{
	QueryResult<TransportVender> qr=new QueryResult<TransportVender>();
	PageView<TransportVender> pageView = new PageView<TransportVender>(queryParam.getPageSize()==0?BaseConstant.pageSize:queryParam.getPageSize(), queryParam.getPage());
	queryParam.setFirstResult(pageView.getFirstResult());
	queryParam.setMaxResult(pageView.getMaxresult());
	List<TransportVender> list=	transportVenderService.getVender(queryParam);
	qr.setResultlist(transportVenderService.pageData(queryParam));
	qr.setTotalrecord(list.size());
	pageView.setQueryResult(qr, queryParam.getPage()); 
	request.setAttribute("pageView", pageView);
	request.setAttribute("queryParam", queryParam);
	
	return  "/orderPlatform/express_page";
	}
	
	@ResponseBody
	@RequestMapping("/toUpdate")
	public  JSONObject toUpdate(TransportVenderQueryParam queryParam, HttpServletRequest request){
		JSONObject obj=new JSONObject();
		List<TransportVender> list=	transportVenderService.getVender(queryParam);
		obj.put("vender", list.get(0));
		return  obj;
	}
	
	
	@ResponseBody
	@RequestMapping("/add")
	public  JSONObject add(TransportVender queryParam, HttpServletRequest request){
		JSONObject obj=new JSONObject();
		Account temp = SysUtil.getAccountInSession(request);
		queryParam.setCreate_user(temp.getCreateUser());
		queryParam.setUpdate_user(temp.getUpdateUser());
		queryParam.setUpdate_time(new Date());
		queryParam.setCreate_time(new Date());
		queryParam.setStatus(BaseConstant.status0);
		try{
			transportVenderService.insert(queryParam);
			obj.put("code", 1);
		}catch(Exception e){
			obj.put("code", 0);
		}
		return  obj;
	}
	
	
	@ResponseBody
	@RequestMapping("/Update")
	public  JSONObject Update(TransportVenderQueryParam queryParam, HttpServletRequest request){
		JSONObject obj=new JSONObject();
		queryParam.setUpdate_time(new Date());
		Account temp = SysUtil.getAccountInSession(request);
		queryParam.setUpdate_user(temp.getUpdateUser());
		try{
			transportVenderService.updateTransportVender(queryParam);
			obj.put("data", 1);
		}catch(Exception e){
			obj.put("data", 0);
		}
		return  obj;
	}
	
	@ResponseBody
	@RequestMapping("/delte")
	public  JSONObject delte(HttpServletRequest request){
		JSONObject obj=new JSONObject();
		String ids=request.getParameter("ids");
		List idslist=Arrays.asList(ids.split(";"));
		
		try{
			transportVenderService.deleteBatch(idslist);
			obj.put("data", 1);
		}catch(Exception e){
			e.printStackTrace();
			obj.put("data", 0);
		}
		return  obj;
	}
	
	
}
