package com.bt.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.common.controller.model.TableFunctionConfig;
import com.bt.common.controller.param.Parameter;
import com.bt.common.controller.result.Result;
import com.bt.common.service.TempletService;
import com.bt.lmis.page.PageView;
import com.bt.utils.BaseConst;

/** 
 * @ClassName: TempletController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年8月17日 下午4:00:07 
 * 
 */
@Controller
@RequestMapping("control/templetController")
public class TempletController extends BaseController {

	@Resource(name="templetServiceImpl")
	private TempletService<T> templetService;
	
	@RequestMapping("/search")
	public String search(Parameter parameter, HttpServletRequest req) {
		parameter = generateParameter(parameter, req);
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(parameter.getPageSize() == 0 ? BaseConst.pageSize : parameter.getPageSize(), parameter.getPage());
		parameter.setFirstResult(pageView.getFirstResult());
		parameter.setMaxResult(pageView.getMaxresult());
		pageView.setQueryResult(templetService.searchTempletTest(parameter), parameter.getPage()); 
		// 分页控件
		req.setAttribute("pageView", pageView);
		// 配置表头
		req.setAttribute("tableColumnConfig", templetService.loadingTableColumnConfig(parameter));
		// 表格功能配置
		Map<String, Object> config = new HashMap<String, Object>();
		config.put("param1", "传参样例1");
		req.setAttribute("tableFunctionConfig", JSONObject.toJSONString(new TableFunctionConfig(parameter.getParam().get("tableName").toString(), true, config)));
		// 返回页面
		return "templet/"+parameter.getParam().get("pageName");
	}
	
	@RequestMapping("/listTableColumnConfig")
	public String listTableColumnConfig(HttpServletRequest req) {
		req.setAttribute("tableColumnConfig", templetService.listTableColumnConfig(generateParameter(req)));
		return "/templet/table_column_config";
	}
	
	@RequestMapping("/listTableColumn")
	public String listTableColumn(HttpServletRequest req) {
		req.setAttribute("tableColumn", templetService.listTableColumn(generateParameter(req)));
		return "/templet/table_column";
	}
	
	@RequestMapping("/saveTableColumnConfig")
	@ResponseBody
	public Result saveTableColumnConfig(HttpServletRequest req) {
		return templetService.saveTableColumnConfig(generateParameter(req));
		
	}
	
}