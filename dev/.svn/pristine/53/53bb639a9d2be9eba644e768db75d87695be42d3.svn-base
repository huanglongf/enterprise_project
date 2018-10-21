package com.bt.lmis.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.ContractBasicinfoQueryParam;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ContractBasicinfoService;
import com.bt.utils.BaseConst;

@Controller
@RequestMapping("/control/vaServiceFeeRawDataController")
public class VaServiceFeeRawDataController extends BaseController {

	private static final Logger logger = Logger.getLogger(EmployeeController.class);
	
	@Resource(name="contractBasicinfoServiceImpl")
	private ContractBasicinfoService<ContractBasicinfo> contractBasicinfoServiceImpl;
	
	@RequestMapping("/list")
	public String getTestList(ContractBasicinfoQueryParam queryParam, ModelMap map, HttpServletRequest request){
		try{
			//根据条件查询合同集合
			PageView<ContractBasicinfo> pageView = new PageView<ContractBasicinfo>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<ContractBasicinfo> qr = contractBasicinfoServiceImpl.findAll(queryParam);
			pageView.setQueryResult(qr, queryParam.getPage());
			request.setAttribute("pageView", pageView);
		}catch(Exception e){
			logger.error(e);
		}
		return "/lmis/vaServiceFeeRawData_list";
	}
	
}
