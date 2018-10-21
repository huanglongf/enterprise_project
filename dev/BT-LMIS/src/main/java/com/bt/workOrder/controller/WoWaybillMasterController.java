package com.bt.workOrder.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.common.CommonUtils;
import com.bt.common.controller.BaseController;
import com.bt.common.controller.model.TableFunctionConfig;
import com.bt.common.controller.param.Parameter;
import com.bt.common.service.TempletService;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.BaseConst;
import com.bt.workOrder.service.WoWaybillMasterService;

@Controller
@RequestMapping("/control/woWaybillMasterController")
public class WoWaybillMasterController  extends BaseController{

	@Resource(name = "woWaybillMasterServiceImpl")
	private WoWaybillMasterService woWaybillMasterService;
	@Resource(name="templetServiceImpl")
	private TempletService<T> templetService;
	
	/**
	 * &tableName=wo_waybill_master
	 */
	@RequestMapping("/queryWoWaybillMaster")
	public String queryWoWaybillMaster(Parameter parameter, HttpServletRequest req,HttpServletResponse res) {
		// 参数构造
		parameter = generateParameter(parameter, req);
		String url = null;
		
		String tableName = CommonUtils.checkExistOrNot(parameter.getParam().get("tableName"))?parameter.getParam().get("tableName").toString():"";
		// 配置表头 参数 tableName columnName
		req.setAttribute("tableColumnConfig", templetService.loadingTableColumnConfig(parameter));
		// 表格功能配置
		req.setAttribute("tableFunctionConfig", JSONObject.toJSONString(new TableFunctionConfig(tableName, true, null)));
		String pageName = CommonUtils.checkExistOrNot(parameter.getParam().get("pageName"))?parameter.getParam().get("pageName").toString():"";
		// 分页控件
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(parameter.getPageSize() == 0 ? BaseConst.pageSize : parameter.getPageSize(), parameter.getPage());
		parameter.setFirstResult(pageView.getFirstResult());
		parameter.setMaxResult(pageView.getMaxresult());
		if("table".equals(pageName) ){
			url = "/templet/table2";
			pageView.setQueryResult(woWaybillMasterService.search(parameter), parameter.getPage()); 
		}else{
			url = "/work_order/wo_platform_store/waybill_search";
			QueryResult<Map<String, Object>> qr = new QueryResult<>();
			pageView.setQueryResult(qr, parameter.getPage()); 
			req.setAttribute("stores", woWaybillMasterService.getStoreName());
			req.setAttribute("transports", woWaybillMasterService.getTransportName());
			req.setAttribute("warehouses", woWaybillMasterService.getWarehouseName());
		}
		
		req.setAttribute("pageView", pageView);
		// 返回页面
		return  url;
	}
	
//	@RequestMapping("exportReport")
//	public void exportReport(HttpServletRequest request, HttpServletResponse response) {
//		Parameter parameter = generateParameter(request);
//		List<Map<String, Object>> titleList = templetService.loadingTableColumnConfig(parameter);
//		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
//		for(int i = 0; i < titleList.size(); i++) {
//			title.put(titleList.get(i).get("column_code").toString(), titleList.get(i).get("column_name").toString());
//		}
//		try {
//			ExcelExportUtil.exportExcelByStream("报表导出"+ "_" + parameter.getCurrentAccount().getName() + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), ExcelExportUtil.OFFICE_EXCEL_2010_POSTFIX, Constant.EXPORT_NAME, title, woWaybillMasterService.exportReport(parameter), response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
}
