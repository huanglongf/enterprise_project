package com.bt.orderPlatform.controller;

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

import com.alibaba.fastjson.JSONObject;
import com.bt.base.BaseConstant;
import com.bt.orderPlatform.controller.form.TransportProductTypeQueryParam;
import com.bt.orderPlatform.controller.form.TransportVenderQueryParam;
import com.bt.orderPlatform.model.TransportProductType;
import com.bt.orderPlatform.model.TransportVender;
import com.bt.orderPlatform.page.PageView;
import com.bt.orderPlatform.page.QueryResult;
import com.bt.orderPlatform.service.TransportProductTypeService;
import com.bt.orderPlatform.service.TransportVenderService;
import com.bt.sys.model.Account;
import com.bt.sys.model.BusinessPower;
import com.bt.sys.util.SysUtil;
/**
 * 快递业务明细表控制器
 *
 */
@Controller
@RequestMapping("/control/orderPlatform/transportProductTypeController")
public class TransportProductTypeController {

	private static final Logger logger = Logger.getLogger(TransportProductTypeController.class);
	
	/**
	 * 快递业务明细表服务类
	 */
	@Resource(name = "transportProductTypeServiceImpl")
	private TransportProductTypeService<TransportProductType> transportProductTypeService;
	@Resource(name = "transportVenderServiceImpl")
	private TransportVenderService<TransportVender> transportVenderService;
	
	@RequestMapping("/initail")
	public String initail(TransportProductTypeQueryParam queryParam, HttpServletRequest request) throws Exception{
	List<TransportVender> list=	transportVenderService.getVender(null);
	request.setAttribute("venders", list);
	QueryResult<TransportProductType> qr=new QueryResult<TransportProductType>();	
	PageView<TransportProductType> pageView = new PageView<TransportProductType>(queryParam.getPageSize()==0?BaseConstant.pageSize:queryParam.getPageSize(), queryParam.getPage());
	queryParam.setFirstResult(pageView.getFirstResult());
	queryParam.setMaxResult(pageView.getMaxresult());
	qr=transportProductTypeService.findAll(queryParam);
	pageView.setQueryResult(qr, queryParam.getPage()); 
	request.setAttribute("pageView", pageView);
	request.setAttribute("queryParam", queryParam);
	return  "/orderPlatform/product_type";
	}
	
	
	@RequestMapping("/page")
	public String page(TransportProductTypeQueryParam queryParam, HttpServletRequest request) throws Exception{
	QueryResult<TransportProductType> qr=new QueryResult<TransportProductType>();
	PageView<TransportProductType> pageView = new PageView<TransportProductType>(queryParam.getPageSize()==0?BaseConstant.pageSize:queryParam.getPageSize(), queryParam.getPage());
	queryParam.setFirstResult(pageView.getFirstResult());
	queryParam.setMaxResult(pageView.getMaxresult());
	qr=transportProductTypeService.findAll(queryParam);
	pageView.setQueryResult(qr, queryParam.getPage()); 
	request.setAttribute("pageView", pageView);
	request.setAttribute("queryParam", queryParam);
	return  "/orderPlatform/product_type_page";
	}
	
	@ResponseBody
	@RequestMapping("/toUpdate")
	public  JSONObject toUpdate(TransportProductTypeQueryParam queryParam, HttpServletRequest request){
		JSONObject obj=new JSONObject();
		PageView<TransportProductType> pageView = new PageView<TransportProductType>(queryParam.getPageSize()==0?BaseConstant.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<TransportProductType> qr=
		transportProductTypeService.findAll(queryParam);
		obj.put("product", qr.getResultlist().get(0));
		return  obj;
	}
	
	@ResponseBody
	@RequestMapping("/getProductTypeCode")
	public  JSONObject getProductTypeCode(TransportProductTypeQueryParam queryParam, HttpServletRequest request){
		BusinessPower power=SysUtil.getPowerSession(request);
		JSONObject obj=new JSONObject();
		List<TransportProductType> qr=
				transportProductTypeService.findByExpresscodeAndcarrier_type(queryParam.getExpress_code(),power.carrier_type);
		obj.put("product", qr);
		return  obj;
	}
	
	
	@ResponseBody
	@RequestMapping("/add")
	public  JSONObject add(TransportProductType queryParam, HttpServletRequest request){
		JSONObject obj=new JSONObject();
		Account temp = SysUtil.getAccountInSession(request);
		queryParam.setCreate_user(temp.getCreateUser());
		queryParam.setUpdate_user(temp.getUpdateUser());
		queryParam.setUpdate_time(new Date());
		queryParam.setCreate_time(new Date());
		queryParam.setStatus(BaseConstant.status0);
		try{
			transportProductTypeService.insert(queryParam);
			obj.put("code", 1);
		}catch(Exception e){
			obj.put("code", 0);
		}
		return  obj;
	}
	

	@ResponseBody
	@RequestMapping("/Update")
	public  JSONObject Update(TransportProductTypeQueryParam queryParam, HttpServletRequest request){
		JSONObject obj=new JSONObject();
		queryParam.setUpdate_time(new Date());
		Account temp = SysUtil.getAccountInSession(request);
		queryParam.setUpdate_user(temp.getUpdateUser());
		try{
			transportProductTypeService.updateTransportProduct(queryParam);
			obj.put("data", 1);
		}catch(Exception e){
			obj.put("data", 0);
			e.printStackTrace();
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
			transportProductTypeService.deleteBatch(idslist);
			obj.put("data", 1);
		}catch(Exception e){
			e.printStackTrace();
			obj.put("data", 0);
		}
		return  obj;
	}
	
}
