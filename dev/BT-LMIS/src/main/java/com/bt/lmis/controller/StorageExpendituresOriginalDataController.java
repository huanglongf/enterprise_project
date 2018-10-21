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

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.StorageExpendituresOriginalDataQueryParam;
import com.bt.lmis.dao.WarehouseMapper;
import com.bt.lmis.model.ExpressReturnStorage;
import com.bt.lmis.model.StorageExpendituresOriginalData;
import com.bt.lmis.model.Warehouse;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.StorageExpendituresOriginalDataService;
import com.bt.lmis.service.StoreService;
import com.bt.utils.BaseConst;
import com.bt.utils.BigExcelExport;
import com.bt.utils.DateUtil;
/**
 * storageExpendituresOriginalData控制器
 *
 */
@Controller
@RequestMapping("/control/storageExpendituresController")
public class StorageExpendituresOriginalDataController extends BaseController {

	private static final Logger logger = Logger.getLogger(StorageExpendituresOriginalDataController.class);
	
	/**
	 * storageExpendituresOriginalData服务类
	 */
	@Resource(name = "storageExpendituresOriginalDataServiceImpl")
	private StorageExpendituresOriginalDataService<StorageExpendituresOriginalData> storageExpendituresOriginalDataService;
	@Autowired WarehouseMapper warehouseMapper;
	@Resource(name = "storeServiceImpl")
	private StoreService<T> storeService;
	
	
	@RequestMapping("toList")
	public String toList(StorageExpendituresOriginalDataQueryParam queryParam, HttpServletRequest request, HttpServletResponse res) {
        //仓库
		List warehouseList=warehouseMapper.findAll();		
		//店铺
		List storeList=storeService.findAll();

		request.setAttribute("warehouses", warehouseList);
		request.setAttribute("stores", storeList);
			return "/lmis/storage_expenditures";
		
		}
		
	
	@RequestMapping("page")
	public String page(StorageExpendituresOriginalDataQueryParam queryParam, HttpServletRequest request, HttpServletResponse res) {
	
			PageView<StorageExpendituresOriginalData> pageView = new PageView<StorageExpendituresOriginalData>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<StorageExpendituresOriginalData> qr=null;
			qr=storageExpendituresOriginalDataService.getPageData(queryParam);
			pageView.setQueryResult(qr, queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			//request.setAttribute("queryParam", queryParam);
			return "/lmis/storage_expenditures_page";
		
		}
		
	@ResponseBody
	@RequestMapping("download")
	public JSONObject download(StorageExpendituresOriginalDataQueryParam queryParam, HttpServletRequest request, HttpServletResponse res) {
		    JSONObject  obj=new JSONObject();
	        Map<String,String> map=new LinkedHashMap<String,String>();
	        map.put("start_time", "库存日期");
	        map.put("store_name", "店铺");
	        map.put("warehouse_name", "仓库");
	        map.put("sku", "SKU编码");
	        map.put("qty_size", "数量");
	        Date d=new Date();
	        String dstr=DateUtil.formatSS(d);
			List<Map<String,Object>> list=storageExpendituresOriginalDataService.getListMap(queryParam);
			try {
				File  f=BigExcelExport.excelDownLoadDatab_Z(list,(LinkedHashMap<String, String>) map,"库存",dstr+"库存.xls");
				obj.put("code", 1);
				obj.put("url", "/库存/"+dstr+"库存.xls");
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
