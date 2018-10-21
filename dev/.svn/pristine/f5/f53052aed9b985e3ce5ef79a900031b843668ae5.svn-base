package com.bt.workOrder.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.bt.common.CommonUtils;
import com.bt.common.controller.model.TableFunctionConfig;
import com.bt.common.controller.param.Parameter;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.BaseConst;
import com.bt.workOrder.service.FailureReasonService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.common.controller.BaseController;
import com.bt.common.service.TempletService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/control/FailureReasonController")
public class FailureReasonController extends BaseController {
	@Resource(name = "templetServiceImpl")
	private TempletService<T> templetService;
	@Resource(name = "failureReasonServiceImpl")
	private FailureReasonService<T> failureReasonService;

	@RequestMapping("/toFailurePage")
	public String toCompareDataPage(Parameter parameter, HttpServletRequest req) {
		// 参数构造
		parameter = generateParameter(parameter, req);

		String tableName = CommonUtils.checkExistOrNot(parameter.getParam().get("tableName"))
				? parameter.getParam().get("tableName").toString()
				: "";
		// 配置表头 参数 tableName columnName
		req.setAttribute("tableColumnConfig", templetService.loadingTableColumnConfig(parameter));
		// 表格功能配置
		req.setAttribute("tableFunctionConfig",
				JSONObject.toJSONString(new TableFunctionConfig(tableName, true, null)));
		String pageName = CommonUtils.checkExistOrNot(parameter.getParam().get("pageName"))
				? parameter.getParam().get("pageName").toString()
				: "";
		// 分页控件
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				parameter.getPageSize() == 0 ? BaseConst.pageSize : parameter.getPageSize(), parameter.getPage());
		parameter.setFirstResult(pageView.getFirstResult());
		parameter.setMaxResult(pageView.getMaxresult());

		String url ="";
		if("firstPage".equals(pageName)){
			url="/work_order/wo_management/failure_reason";
			pageView.setQueryResult(new QueryResult<Map<String, Object>>(),parameter.getPage());
		}else{
			pageView.setQueryResult(failureReasonService.queryFailureReasonData(parameter), parameter.getPage());
			url="/templet/table";
		}

		req.setAttribute("pageView", pageView);

		// 返回页面
		return url;
	}

	@ResponseBody
	@RequestMapping("/deleteById")
	public String deleteCompareTaskById(@RequestParam String id) {
		Map<String, Object> result = new HashMap<>();
		int i = failureReasonService.deleteById(id);
		if (i > 0) {
			result.put("msg", "删除成功");
			result.put("code", "1");
		} else {
			result.put("msg", "删除失败");
			result.put("code", "0");
		}
		return JSONObject.toJSONString(result);
	}

	@ResponseBody
	@RequestMapping("/addFailReason")
	public String addFailReason(@RequestParam String reason) {
		Map<String, Object> result = new HashMap<>();
		int i = failureReasonService.addFailureReason(reason);
		if (i > 0) {
			result.put("msg", "新增成功");
			result.put("code", "1");
		} else {
			result.put("msg", "新增失败");
			result.put("code", "0");
		}
		return JSONObject.toJSONString(result);
	}
}
