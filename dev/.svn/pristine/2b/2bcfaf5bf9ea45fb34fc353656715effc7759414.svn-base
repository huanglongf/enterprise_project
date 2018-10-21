package com.bt.lmis.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.balance.model.OperationfeeDataDetail;
import com.bt.lmis.balance.service.OperationfeeDataDetailService;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.OperationfeeDataDetailQueryParam;
import com.bt.lmis.controller.form.StorageExpendituresOriginalDataQueryParam;
import com.bt.lmis.dao.WarehouseMapper;
import com.bt.lmis.model.StorageExpendituresOriginalData;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.StorageExpendituresOriginalDataService;
import com.bt.lmis.service.StoreService;
import com.bt.utils.BaseConst;
import com.bt.utils.BigExcelExport;
import com.bt.utils.DateUtil;

@Controller
@RequestMapping("/control/OperationfeeDataDetailController")



public class OperationfeeDataDetailController extends BaseController {

	
	@Resource(name = "operationfeeDataDetailServiceImpl")
	private OperationfeeDataDetailService<OperationfeeDataDetail> operationfeeDataDetailService;
	@Autowired WarehouseMapper warehouseMapper;
	@Resource(name = "storeServiceImpl")
	private StoreService<T> storeService;
	@RequestMapping("toList")
	public String toList(OperationfeeDataDetail queryParam, HttpServletRequest request, HttpServletResponse res) {
        //仓库
		List warehouseList=warehouseMapper.findAll();		
		//店铺
		List storeList=storeService.findAll();

		request.setAttribute("warehouses", warehouseList);
		request.setAttribute("stores", storeList);
			return "/lmis/operation_data";
		
		}
		
	
	@RequestMapping("page")
	public String page(OperationfeeDataDetailQueryParam queryParam, HttpServletRequest request, HttpServletResponse res) {
	
			PageView<Map<String,String>> pageView = new PageView<Map<String,String>>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<Map<String,String>> qr=null;
			qr=operationfeeDataDetailService.getPageData(queryParam);
			pageView.setQueryResult(qr, queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			//request.setAttribute("queryParam", queryParam);
			return "/lmis/operation_data_page";
		
		}
		
	@ResponseBody
	@RequestMapping("download")
	public JSONObject download(OperationfeeDataDetailQueryParam queryParam, HttpServletRequest request, HttpServletResponse res) {
		    JSONObject  obj=new JSONObject();
	        Map<String,String> map=new LinkedHashMap<String,String>();
	        map.put("operation_time", "操作时间");
	        map.put("order_type", "订单类型");
	     
	        
	        map.put("store_name", "店铺");
	        map.put("warehouse_name", "仓库");
	        map.put("related_orderno", "wms单号");
	        map.put("epistatic_order", "前置单据号");

	        map.put("platform_order", "平台订单号");
	        map.put("job_type", "作业类型");
	        map.put("in_num", "入库数量");
	        map.put("out_num", "出库数量");
	        map.put("out_num", "出库数量");
	        
	        map.put("item_number", "商品编码");
	        map.put("sku", "SKU编码");
	        map.put("art_no", "货号");
	        map.put("item_name", "商品名称"); 
	        Date d=new Date();
	        String dstr=DateUtil.formatSS(d);
			List<Map<String,Object>> list=
					operationfeeDataDetailService.getListMap(queryParam);
			try {
				File  f=BigExcelExport.excelDownLoadDatab_Z(list,(LinkedHashMap<String, String>) map,"操作费",dstr+"操作费.xls");
				obj.put("code", 1);
				obj.put("url", "/操作费/"+dstr+"操作费.xls");
			} catch (IOException e) {
				// TODO Auto-generated catch block
			   obj.put("code", 0);
			   obj.put("msg", e.getMessage());
			   e.printStackTrace();
			   return obj;
			}
			return obj;
		
		}
	
	
	
}
