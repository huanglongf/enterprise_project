package com.bt.lmis.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.OSinfo;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.WarehouseQueryParam;
import com.bt.lmis.model.Warehouse;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.WarehouseService;
import com.bt.utils.BaseConst;
import com.bt.utils.BigExcelExport;
import com.bt.utils.CommonUtils;
import com.bt.utils.Constant;
import com.bt.utils.DateUtil;
@Controller
@RequestMapping("/control/warehouseController")
public class WarehouseController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(WarehouseController.class);
	
	@Resource(name = "warehouseServiceImpl")
	private WarehouseService<T> warehouseServiceImpl;
	
	/**
	 * 
	 * @Description: TODO
	 * @param warehouse
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年9月25日下午6:09:08
	 */
	@RequestMapping("save")
	@ResponseBody
	public String save(Warehouse warehouse, HttpServletRequest request, HttpServletResponse res) {
		JSONObject result = null;
		try {
			result = warehouseServiceImpl.save(warehouse, request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("failure_reason", CommonUtils.getExceptionStack(e));	
		}
		return result.toString();
	}
	
	@RequestMapping("/form")
	public String form(Warehouse warehouse, HttpServletRequest request) throws Exception{
		try{
			request = warehouseServiceImpl.form(warehouse, request);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/lmis/warehouse_management/warehouse_form";
	}
	
	@RequestMapping("/list")
	public String list(WarehouseQueryParam warehouseQueryParam, HttpServletRequest request) throws Exception{
		try{
			//根据条件查询合同集合
			PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(warehouseQueryParam.getPageSize() == 0? BaseConst.pageSize:warehouseQueryParam.getPageSize(), warehouseQueryParam.getPage());
			warehouseQueryParam.setFirstResult(pageView.getFirstResult());
			warehouseQueryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<Map<String, Object>> qr = warehouseServiceImpl.findAllWareHouse(warehouseQueryParam);
			pageView.setQueryResult(qr, warehouseQueryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			request.setAttribute("warehouseQueryParam", warehouseQueryParam);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/lmis/warehouse_management/warehouse_list";
	}
	
	
	@ResponseBody
	@RequestMapping("/download")
	public JSONObject download(WarehouseQueryParam warehouseQueryParam, HttpServletRequest request) throws Exception{
		JSONObject obj =new JSONObject();
		try{
			
			//根据条件查询合同集合
			LinkedHashMap<String, String> tableHeader= new LinkedHashMap<String, String>();
			tableHeader.put("warehouse_code", "仓库code");
			tableHeader.put("warehouse_name", "仓库名称");
			
			tableHeader.put("province", "省");
			tableHeader.put("city", "市");
			tableHeader.put("state", "区");
			tableHeader.put("street", "街道");
			tableHeader.put("address", "详细地址");
			
			tableHeader.put("contact", "联系人");
			tableHeader.put("phone", "联系电话");
			tableHeader.put("warehouse_type", "仓库类型");
			tableHeader.put("need_balance", "是否结算");
			List<Map<String, Object>> list = warehouseServiceImpl.download(warehouseQueryParam);
			File f=BigExcelExport.excelDownLoadDatab_Z(list, tableHeader,"", DateUtil.formatSS (new Date())+"导出仓库信息.xlsx");
			
			obj.put("msg","操作成功！");
			obj.put("code", 1);
			obj.put("path",  f.getName());
		}catch(Exception e){
			obj.put("msg","执行失败！"+e.getMessage());
			obj.put("code", 0);
			e.printStackTrace();
		}
		return obj;
	}
	
	
	
}
