package com.bt.workOrder.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.common.controller.BaseController;
import com.bt.common.controller.model.TableFunctionConfig;
import com.bt.common.controller.param.Parameter;
import com.bt.common.service.TempletService;
import com.bt.lmis.page.PageView;
import com.bt.utils.BaseConst;
import com.bt.workOrder.bean.WorkBaseInfoBean;
import com.bt.workOrder.service.WorkBaseInfoService;
@Controller
@RequestMapping("control/OrderTypeController2")
public class OrderTypeController2 extends BaseController {

	@Resource(name="templetServiceImpl")
	private TempletService<T> templetService;
	@Resource(name="workBaseInfoServiceImpl")
	private WorkBaseInfoService<WorkBaseInfoBean> workBaseInfoServiceImpl;
	
	//menu main
	@RequestMapping("/typelist.do")
	public String search(Parameter parameter, HttpServletRequest req) {
		parameter = generateParameter(parameter, req);
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(parameter.getPageSize() == 0 ? BaseConst.pageSize : parameter.getPageSize(), parameter.getPage());
		Map<String,Object> map = parameter.getParam();
		map.put("firstResult", pageView.getFirstResult());
		map.put("maxResult", pageView.getMaxresult());
		pageView.setQueryResult(workBaseInfoServiceImpl.findAllData2(map), parameter.getPage()); 
		req.setAttribute("pageView", pageView);
		List<Map<String,Object>> tableColumnConfig = templetService.loadingTableColumnConfig(parameter);
		ArrayList<?>list=workBaseInfoServiceImpl.getAllType();
		req.setAttribute("tableColumnConfig", tableColumnConfig);
		req.setAttribute("tableFunctionConfig", JSONObject.toJSONString(new TableFunctionConfig(parameter.getParam().get("tableName").toString(), false, null)));
		req.setAttribute("allType", list);
		return "work_order/"+parameter.getParam().get("pageName");
	}
	//table
	@RequestMapping("/typelist2.do")
	public String search2(Parameter parameter, HttpServletRequest req) {
		parameter = generateParameter(parameter, req);
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(parameter.getPageSize() == 0 ? BaseConst.pageSize : parameter.getPageSize(), parameter.getPage());
		Map<String,Object> map = parameter.getParam();
		map.put("firstResult", pageView.getFirstResult());
		map.put("maxResult", pageView.getMaxresult());
		pageView.setQueryResult(workBaseInfoServiceImpl.findAllData2(map), parameter.getPage()); 
		req.setAttribute("pageView", pageView);
		List<Map<String,Object>> tableColumnConfig = templetService.loadingTableColumnConfig(parameter);
		req.setAttribute("tableColumnConfig", tableColumnConfig);
		req.setAttribute("tableFunctionConfig", JSONObject.toJSONString(new TableFunctionConfig(parameter.getParam().get("tableName").toString(), false, null)));
		return "work_order/work_type_table";
	}
}