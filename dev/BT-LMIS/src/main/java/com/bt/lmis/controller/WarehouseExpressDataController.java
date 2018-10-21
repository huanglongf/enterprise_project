package com.bt.lmis.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.OSinfo;
import com.bt.lmis.controller.form.WarehouseExpressDataQueryParam;
import com.bt.lmis.dao.WarehouseExpressDataMapper;
import com.bt.lmis.model.WarehouseExpressData;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ClientService;
import com.bt.lmis.service.StoreService;
import com.bt.lmis.service.TransportVendorService;
import com.bt.lmis.service.WarehouseExpressDataService;
import com.bt.lmis.service.WarehouseService;
import com.bt.utils.BaseConst;
import com.bt.utils.BigExcelExport;
import com.bt.utils.CommonUtils;
import com.bt.utils.ZipUtils;

@Controller
@RequestMapping("/control/warehouseExpressDataController")
public class WarehouseExpressDataController {
  
	private static final Logger logger = Logger.getLogger(WarehouseExpressDataController.class);
	
	@Resource(name = "storeServiceImpl")
	private StoreService<T> storeService;
	@Resource(name = "warehouseServiceImpl")
	private WarehouseService<T> wareshouseService;
	@Resource(name = "transportVendorServiceImpl")
	private TransportVendorService<T> transportVendorService;
	@Resource(name = "clientServiceImpl")
	private ClientService<T> clientService;
	@Resource(name = "warehouseExpressDataServiceImpl")
	private WarehouseExpressDataService<T> warehouseExpressDataService;
    @Autowired
    WarehouseExpressDataMapper warehouseExpressDataMapper;
	
	@RequestMapping("inite")
	public String inite(HttpServletRequest request, HttpServletResponse res){
		//店铺
		List storeList=storeService.findAll();
		//仓库
		List warehouses=wareshouseService.findAll();
		//快递公司
		List transportList=transportVendorService.getAllExpress();
		
		List clientList =clientService.findAll();
		
		
		
		request.setAttribute("stores", storeList);
		request.setAttribute("transportVenders", transportList);
		request.setAttribute("clientList", clientList);
		request.setAttribute("warehouseList", warehouses);
		return "lmis/warehouse_expressdata";
	}
	@ResponseBody
	@RequestMapping("download")
	public JSONObject download(WarehouseExpressDataQueryParam queryParam) throws Exception{
		JSONObject obj=new JSONObject();
		String	filePath=CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname());
		String str = (new SimpleDateFormat("yyyyMMdd-HHmmssSSS")).format(new Date()); 
		int pageCount=50000;	
		queryParam.setInClientParam(inClientParam(queryParam));
		int count =	warehouseExpressDataMapper.getCount(queryParam);
		    count = count%pageCount==0?count/pageCount:count/pageCount+1;
		//head
		    Map<String, String> map=new LinkedHashMap<String, String>();
		    map.put("express_number", "运单号");
		    map.put("transport_name", "快递公司");
		    map.put("itemtype_name", "产品类型");
		    map.put("cost_center", "成本中心");
		    map.put("store_name", "店铺");
		    map.put("warehouse", "系统仓");
		    map.put("weight", "重量");
		    map.put("length", "长");
		    map.put("width", "宽");
		    map.put("higth", "高");
		    map.put("volumn", "体积");
		    map.put("epistatic_order", "前置单据号");
		    map.put("delivery_order", "平台订单号");
		    map.put("order_amount", "订单金额");
		    map.put("collection_on_delivery", "代收货款金额");
		    map.put("order_type", "订单类型");map.put("transport_time", "出库时间");
		    map.put("delivery_address", "始发地");
		    map.put("province", "目的省");
		    map.put("city", "目的市");
		    map.put("state", "目的区");
		    map.put("insurance_flag", "是否保价(0:否;1:是)");
		    map.put("cod_flag", "是否COD(0:否;1:是)");
		    map.put("sku_number", "sku编码");
		//ExpressReturnStorageMapper.getPageData(queryParam);
			int j=0;
			for(;j<count;j++){
				queryParam.setFirstResult(j*pageCount);	
				queryParam.setMaxResult(pageCount);
				BigExcelExport.excelDownLoadDatab_Z(warehouseExpressDataMapper.findAllMap(queryParam),(LinkedHashMap<String, String>) map,str,"returnData_"+j+".xls");
			}
			
			ZipUtils.zip(filePath+"/"+str, filePath);
		    obj.put("data", (str+".zip"));
		
		return obj;
	}
	
	
	@RequestMapping("pageQuery")
	public String pageQuery(HttpServletRequest request, HttpServletResponse res,WarehouseExpressDataQueryParam queryParam){
		boolean flag=true;
		QueryResult<WarehouseExpressData> qr=null;
		if(queryParam.getClient_name()!=null&&!"".equals(queryParam.getClient_name()));
		queryParam.setInClientParam(inClientParam(queryParam));
		PageView<WarehouseExpressData> pageView = new PageView<WarehouseExpressData>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		flag=checkPage(queryParam);
		qr=warehouseExpressDataService.findAll(queryParam,flag);
		pageView.setQueryResult(qr, queryParam.getPage()); 
		request.setAttribute("pageView", pageView);
		request.setAttribute("totalSize", qr.getTotalrecord());
		request.setAttribute("queryParam", queryParam);
		
		return "lmis/warehouse_expressdata_page";
	}
	
	public  boolean  checkPage(WarehouseExpressDataQueryParam queryParam){
		int lastPage=queryParam.getLastPage()==null?BaseConst.pageSize:queryParam.getLastPage();
		int  nowpage=queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize();
		if(lastPage!=nowpage||1==queryParam.getPage())return false;
		return true;
	}
	
	public String inClientParam(WarehouseExpressDataQueryParam queryParam){
         String inParam="";
         Map map =new HashMap();
         map.put("client_code", queryParam.getClient_code());
        List<Map<String,Object>>  list= clientService.getStoreByClient(map);
        if(list==null||list.size()==0)return "";
        inParam="store_code in ( ";
         for(Map<String,Object> obj:list){
        	 inParam+="'"+obj.get("store_code")+"',";
         }
         inParam=inParam.substring(0, inParam.length()-1)+" ) ";
         return inParam;
	}
	
	
	public void inClientParamV(WarehouseExpressDataQueryParam queryParam){
        /*String inParam="";
        Map map =new HashMap();
        map.put("client_code", queryParam.getClient_code());
       List<Map<String,Object>>  list= clientService.getStoreByClient(map);
       if(list==null||list.size()==0)return;
       queryParam.setInParam(list);*/
        
	}
	

}
