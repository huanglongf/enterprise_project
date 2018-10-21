package com.bt.workOrder.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.common.base.LoadingType;
import com.bt.common.controller.BaseController;
import com.bt.common.controller.model.TableFunctionConfig;
import com.bt.common.controller.param.Parameter;
import com.bt.common.service.TempletService;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.UUIDUtils;
import com.bt.utils.cache.CacheManager;
import com.bt.workOrder.controller.param.WorkOrderParam;
import com.bt.workOrder.service.WorkOrderManagementService;

@Controller
@RequestMapping("/control/workOrderController2")
public class WorkOrderController2 extends BaseController{

	private static final Logger logger = Logger.getLogger(WorkOrderController2.class);
	
	@Resource(name= "workOrderManagementServiceImpl")
	private WorkOrderManagementService<T> workOrderManagementServiceImpl;
	@Resource(name="templetServiceImpl")
	private TempletService<T> templetService;
	
	///control/workOrderController2/query.do?pageName=table&tableName=wo_master&isQuery=false&isCollapse=true
	@RequestMapping("/query")
	public String query(Parameter parameter, HttpServletRequest request) {
		if(parameter.getParam()==null){
			parameter = generateParameter(parameter, request);
		}
		String url=null;
		try{
			PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(parameter.getPageSize() == 0 ? BaseConst.pageSize : parameter.getPageSize(), parameter.getPage());
			Map<String,Object> map = parameter.getParam();
			map.put("firstResult", pageView.getFirstResult());
			map.put("maxResult", pageView.getMaxresult());
			QueryResult<Map<String, Object>> result=new QueryResult<Map<String, Object>>();
			
			if(map.get("pageName").toString().equals("wo_management")) {
				if(Boolean.parseBoolean(map.get("isQuery").toString())){
					result=workOrderManagementServiceImpl.query2(map);
				}
				// 工单管理查询条件初始化
				request=workOrderManagementServiceImpl.initialize(request);
				url="/work_order/wo_management/wo_management";
			} else if(map.get("pageName").toString().equals("table")) {
				result=workOrderManagementServiceImpl.query2(map);
				url="/templet/table";
			}
			pageView.setQueryResult(result,parameter.getPage());
			request.setAttribute("pageView",pageView);
			// 缓存查询条件
			if(!CommonUtils.checkExistOrNot(map.get("uuid"))) {
				map.put("uuid","QP-"+new SimpleDateFormat("yyMMddHHmmss").format(new Date())+"-"+UUIDUtils.getUUID8L());
			}
			parameter.getParam().put("pageName", "wo_management");
			CacheManager.putValue(map.get("uuid").toString(), parameter, 1800);
			request.setAttribute("queryParam",map);
			List<Map<String,Object>> tableColumnConfig = templetService.loadingTableColumnConfig(parameter);
			request.setAttribute("tableColumnConfig", tableColumnConfig);
			request.setAttribute("tableFunctionConfig", JSONObject.toJSONString(new TableFunctionConfig(parameter.getParam().get("tableName").toString(), true, null)));
			// 区分查询页面与处理页面
			request.setAttribute("role","query");
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return url;
	}
	/** 
	 * @Title: back 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param request
	 * @return String 返回类型
	 * @author Ian.Huang
	 * @throws
	 * @date 2017年4月25日 下午1:48:18
	 */
	@RequestMapping("/back")
	public String back(HttpServletRequest request) {
		Parameter parameter=(Parameter)CacheManager.getValue(request.getParameter("uuid").toString());
		return query(parameter, request);
	}
}