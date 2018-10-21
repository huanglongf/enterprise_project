package com.bt.lmis.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bt.lmis.dao.ExpressReturnStorageMapper;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.bt.OSinfo;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.ExpressReturnStorageQueryParam;
import com.bt.lmis.dao.WarehouseMapper;
import com.bt.lmis.model.ExpressReturnStorage;
import com.bt.lmis.model.WarehouseArea;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ExpressReturnStorageService;
import com.bt.lmis.service.StoreService;
import com.bt.lmis.service.TransportVendorService;
import com.bt.lmis.service.WarehouseAreaService;
import com.bt.utils.BaseConst;
import com.bt.utils.BigExcelExport;
import com.bt.utils.CommonUtils;
import com.bt.utils.ZipUtils;

@Controller
@RequestMapping("/control/expressReturnStorageController")
public class ExpressReturnStorageController extends BaseController {
	@Resource(name = "warehouseAreaServiceImpl")
	private WarehouseAreaService<WarehouseArea> warehouseAreaService;
	@Autowired WarehouseMapper warehouseMapper;
	@Resource(name = "storeServiceImpl")
	private StoreService<T> storeService;
	@Resource(name = "transportVendorServiceImpl")
	private TransportVendorService<T> transportVendorService;
	@Resource(name = "expressReturnStorageServiceImpl")
	private ExpressReturnStorageService<T> expressReturnStorageService;
	@Autowired 
	private  ExpressReturnStorageMapper ExpressReturnStorageMapper;
	/*
	 * 查询页面跳转
	 */
	@RequestMapping("/initial")
	public  String initial(HttpServletRequest request, HttpServletResponse res){
		//系统仓
		List warehouseList=warehouseMapper.findAll();
		
		//店铺
		List storeList=storeService.findAll();
		
		//快递公司
		List transportList=transportVendorService.getAllExpress();
		
		request.setAttribute("warehouses", warehouseList);
		request.setAttribute("stores", storeList);
		request.setAttribute("transportVenders", transportList);
	
		return "/lmis/return_storeage";
	}
	
	
	/*
	 * 查询页面跳转
	 */
	@RequestMapping("/query")
	public  String query(HttpServletRequest request, HttpServletResponse res,ExpressReturnStorageQueryParam queryParam){
		//系统仓
	/*	String store_code=request.getParameter("storeCode")==null?"":request.getParameter("storeCode").toString();
		String warehouse_code=request.getParameter("warehouseCode")==null?"":request.getParameter("warehouseCode").toString();   
		String  re_epistatic_order=request.getParameter("reEpistaticOrder")==null?"":request.getParameter("reEpistaticOrder").toString();        
		String  transport_code=request.getParameter("transportCode")==null?"":request.getParameter("transportCode").toString();
		String itemtype_code=request.getParameter("producttypeCode")==null?"":request.getParameter("producttypeCode").toString();
		String waybill=request.getParameter("waybill")==null?"":request.getParameter("waybill").toString();
		String related_no=waybill=request.getParameter("relatedNo")==null?"":request.getParameter("relatedNo").toString();*/
		//快递公司
		PageView<ExpressReturnStorage> pageView = new PageView<ExpressReturnStorage>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<ExpressReturnStorage> qr=null;
		qr=expressReturnStorageService.getPageData(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage()); 
		request.setAttribute("pageView", pageView);
		//request.setAttribute("queryParam", queryParam);
		return "/lmis/return_storeage_page";
	}
	
	@ResponseBody
	@RequestMapping("/download")
	public JSONObject download(HttpServletRequest request, HttpServletResponse res,ExpressReturnStorageQueryParam queryParam) throws Exception{
		JSONObject obj=new JSONObject();
	String	filePath=CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname());
	String str = (new SimpleDateFormat("yyyyMMdd-HHmmssSSS")).format(new Date()); 
	int pageCount=50000;	
	int count =	ExpressReturnStorageMapper.getPageDataCount(queryParam);
	    count = count%pageCount==0?count/pageCount:count/pageCount+1;
	//head
	    Map<String, String> map=new LinkedHashMap<String, String>();
	    map.put("id", "id");
	    map.put("warehouse_code", "仓库编码");
	    map.put("warehouse_name", "仓库名称");
	    map.put("job_no", "作业单号");
	    map.put("job_type", "作业单类型");
	    map.put("store_code", "店铺编码");
	    map.put("store_name", "店铺名称");
	    map.put("related_no", "PACS退货相关单号");
	    map.put("waybill", "运单号");
	    map.put("re_epistatic_order", "PACS销售单号");
	    map.put("re_province", "省");
	    map.put("re_city", "市");
	    map.put("re_state", "区");
	    map.put("re_weight", "重量");
	    map.put("re_length", "长");
	    map.put("re_width", "宽");
	    map.put("re_higth", "高");
	    map.put("re_volumn", "体积");map.put("create_time", "作业单创建时间");
	    map.put("finish_time", "作业单完成时间");
	    map.put("plan_qty", "计划执行量");
	    map.put("actual_qty", "实际执行量");
	    map.put("batch_number", "批次号");
	    map.put("state", "state");
	    map.put("state", "state");
	    map.put("transport_code", "物流商编码");
	    map.put("transport_name", "物流商名称");
	    map.put("itemtype_code", "产品类型编码");
	    map.put("itemtype_name", "产品类型名称");
	    map.put("refuse_reason", "拒绝原因");
	    map.put("physical_warehouse", "物理仓");
	    map.put("return_order", "出库订单金额");
	    map.put("create_user", "创建人");
	    map.put("deblock_user", "解锁人");
	    map.put("remark", "备注");
	//ExpressReturnStorageMapper.getPageData(queryParam);
		int j=0;
		for(;j<count;j++){
			queryParam.setFirstResult(j*pageCount);	
			queryParam.setMaxResult(pageCount);
			BigExcelExport.excelDownLoadDatab_Z(ExpressReturnStorageMapper.getPageData(queryParam),(LinkedHashMap<String, String>) map,str,"returnData_"+j+".xls");
		}
		
		ZipUtils.zip(filePath+"/"+str, filePath);
	    obj.put("data", (str+".zip"));
		return obj;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
