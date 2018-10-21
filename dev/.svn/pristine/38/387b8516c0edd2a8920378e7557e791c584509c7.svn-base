package com.bt.orderPlatform.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.base.BaseConstant;
import com.bt.orderPlatform.controller.form.ExpressinfoMasterInputlistQueryParam;
import com.bt.orderPlatform.controller.form.WaybilMasterDetailQueryParam;
import com.bt.orderPlatform.model.WaybilMasterDetail;
import com.bt.orderPlatform.page.PageView;
import com.bt.orderPlatform.page.QueryResult;
import com.bt.orderPlatform.service.WaybilMasterDetailService;
import com.bt.sys.model.BusinessPower;
import com.bt.sys.util.SysUtil;
/**
 * 运单信息临时表控制器
 *
 */
@Controller
@RequestMapping("/control/orderPlatform/waybilMasterDetailController")
public class WaybilMasterDetailController {

	private static final Logger logger = Logger.getLogger(WaybilMasterDetailController.class);
	
	/**
	 * 运单信息临时表服务类
	 */
	@Resource(name = "waybilMasterDetailServiceImpl")
	private WaybilMasterDetailService<WaybilMasterDetail> waybilMasterDetailService;
	
	
	
	@RequestMapping("/updatewaybilMasterDetail")
	public String pagetest(WaybilMasterDetailQueryParam queryParam, HttpServletRequest request){
		BusinessPower power=SysUtil.getPowerSession(request);
		PageView<Map<String, Object>> pageView= new PageView<Map<String, Object>>(queryParam.getPageSize()==0?BaseConstant.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<Map<String, Object>> qr= waybilMasterDetailService.findAllWaybilMasterDetail(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "op/waybilMasterDetail";
	}
	
	
	
	@RequestMapping("/page")
	public String page(WaybilMasterDetailQueryParam queryParam, HttpServletRequest request){
		BusinessPower power=SysUtil.getPowerSession(request);
		PageView<Map<String, Object>> pageView= new PageView<Map<String, Object>>(queryParam.getPageSize()==0?BaseConstant.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<Map<String, Object>> qr= waybilMasterDetailService.findAllWaybilMasterDetail(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "op/waybilMasterDetail_page";
	}
	
}
