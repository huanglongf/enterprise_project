package com.bt.lmis.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.common.controller.BaseController;
import com.bt.common.controller.model.TableFunctionConfig;
import com.bt.common.controller.param.Parameter;
import com.bt.common.service.TempletService;
import com.bt.lmis.model.Group;
import com.bt.lmis.model.StoreBean;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.TransPoolService;
import com.bt.utils.BaseConst;
import com.bt.workOrder.service.GroupService;

@Controller
@RequestMapping("/control/groupMessageController2")
public class GroupMessageController2 extends BaseController {

	private static final Logger logger = Logger.getLogger(GroupMessageController2.class);

	@Resource(name="templetServiceImpl")
	private TempletService<T> templetService;
	
	@Resource(name = "transPoolServiceImpl")
	private TransPoolService<Group> transPoolServiceImpl;
	
	@Resource(name = "groupServiceImpl")
	private GroupService<StoreBean> groupServiceImpl;
	/***
	 * 组别信息查询列表
	 * @param groupPar
	 * @param request
	 * @return
	 */
	@RequestMapping("listGroup")
	public String listGroup(Parameter parameter, HttpServletRequest request) {
		parameter = generateParameter(parameter, request);
		PageView<Group> pageView = new PageView<Group>(parameter.getPageSize() == 0 ? BaseConst.pageSize : parameter.getPageSize(), parameter.getPage());
		Map<String,Object> map = parameter.getParam();
		map.put("firstResult", pageView.getFirstResult());
		map.put("maxResult", pageView.getMaxresult());
		map.put("group_code", request.getParameter("group_code"));
		map.put("group_name", request.getParameter("group_name"));
		if ("".equals(request.getParameter("status")) || null == request.getParameter("status")) {

		} else {
			map.put("status", request.getParameter("status"));
		}
		if ("".equals(request.getParameter("superior")) || null == request.getParameter("superior")) {

		} else {
			map.put("superior", request.getParameter("superior"));
		}
		map.put("if_allot", request.getParameter("if_allot"));
		QueryResult<Group> qrRaw = transPoolServiceImpl.querysGroup2(map);
		ArrayList<?> seniorQuery = transPoolServiceImpl.querySeniorQueryGroupSup();
		pageView.setQueryResult(qrRaw, parameter.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("groupPar", map);
		request.setAttribute("groupCode", request.getParameter("group_code"));
		request.setAttribute("groupName", request.getParameter("group_name"));
		request.setAttribute("status", request.getParameter("status"));
		request.setAttribute("superior", request.getParameter("superior"));
		request.setAttribute("if_allot", request.getParameter("if_allot"));
		request.setAttribute("seniorQuery", seniorQuery);
		// 配置表头
		request.setAttribute("tableColumnConfig", templetService.loadingTableColumnConfig(parameter));
		// 表格功能配置
		Map<String, Object> config = new HashMap<String, Object>();
		config.put("param1", "传参样例1");
		request.setAttribute("tableFunctionConfig", JSONObject.toJSONString(new TableFunctionConfig(parameter.getParam().get("tableName").toString(), true, config)));
		return "/work_order/"+parameter.getParam().get("pageName");
	}
}
