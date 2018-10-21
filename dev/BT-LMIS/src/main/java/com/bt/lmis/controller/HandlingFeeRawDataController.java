package com.bt.lmis.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.HandlingFeeRawDataQueryParam;
import com.bt.lmis.model.HandlingFeeRawData;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.HandlingFeeRawDataService;
import com.bt.utils.BaseConst;

@Controller
@RequestMapping("/control/handlingFeeRawDataController")
public class HandlingFeeRawDataController extends BaseController {

	private static final Logger logger = Logger.getLogger(EmployeeController.class);
	
	@Resource(name="handlingFeeRawDataServiceImpl")
	private HandlingFeeRawDataService<HandlingFeeRawData> handlingFeeRawDataServiceImpl;
	
	@RequestMapping("/list")
	public String getTestList(HandlingFeeRawDataQueryParam queryParam, ModelMap map, HttpServletRequest request){
		try{
			//根据条件查询合同集合
			PageView<HandlingFeeRawData> pageView = new PageView<HandlingFeeRawData>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			queryParam.setGoodsName(request.getParameter("goodsName"));
			queryParam.setStoreName(request.getParameter("storeName"));
			QueryResult<HandlingFeeRawData> qr = handlingFeeRawDataServiceImpl.findAll(queryParam);
			pageView.setQueryResult(qr, queryParam.getPage());
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			logger.error(e);
		}
		return "/lmis/handlingFeeRawData_list";
	}
	
}
