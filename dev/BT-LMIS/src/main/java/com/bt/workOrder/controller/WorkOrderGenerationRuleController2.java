package com.bt.workOrder.controller;

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
import com.bt.lmis.page.QueryResult;
import com.bt.utils.BaseConst;
import com.bt.workOrder.model.GenerationRule;
import com.bt.workOrder.service.GenerationRuleService;

@Controller
@RequestMapping("/controller/workOder2")
public class WorkOrderGenerationRuleController2 extends BaseController {
	@Resource(name = "generationRuleServiceImpl")
	private GenerationRuleService<GenerationRule> generationRuleService;
	@Resource(name="templetServiceImpl")
	private TempletService<T> templetService;
    
	//  /controller/workOder2/workOrderRulQuery.do?tableName=wk_generation_rule&pageName=work_order_rule_page
	@RequestMapping("/workOrderRulQueryPage")
	public String workOrderRulQueryPage(Parameter parameter,HttpServletRequest req){
		// 参数构造
		parameter = generateParameter(parameter, req);
		// 配置表头
		req.setAttribute("tableColumnConfig", templetService.loadingTableColumnConfig(parameter));
		// 表格功能配置
		req.setAttribute("tableFunctionConfig", JSONObject.toJSONString(new TableFunctionConfig(parameter.getParam().get("tableName").toString(), true, null)));
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(parameter.getPageSize()==0?BaseConst.pageSize:parameter.getPageSize(), parameter.getPage());
		QueryResult<Map<String, Object>> qr=generationRuleService.findAllData2(parameter);
		pageView.setQueryResult( qr, parameter.getPage());
		req.setAttribute("pageView", pageView);
		req.setAttribute("queryParam", parameter.getParam());
		return "table".equals(parameter.getParam().get("pageName")) ? "/templet/table" : "work_order/"+parameter.getParam().get("pageName");
	}
	
}
