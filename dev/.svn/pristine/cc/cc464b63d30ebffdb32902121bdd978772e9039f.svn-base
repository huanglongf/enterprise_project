package com.bt.radar.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.bt.EPlatform;
import com.bt.OSinfo;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.Employee;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.ExpressinfoMasterInputlistQueryParam;
import com.bt.radar.controller.form.ExpressinfoMasterQueryParam;
import com.bt.radar.controller.form.RoutecodeQueryParam;
import com.bt.radar.dao.ExpressinfoMasterMapper;
import com.bt.radar.dao.RouteInfoMapper;
import com.bt.radar.dao.RoutecodeMapper;
import com.bt.radar.model.Area;
import com.bt.radar.model.ExpressinfoMaster;
import com.bt.radar.model.ExpressinfoMasterInputlist;
import com.bt.radar.model.RouteInfo;
import com.bt.radar.model.Routecode;
import com.bt.radar.model.WarninginfoMaintainMaster;
import com.bt.radar.model.WaybillWarninginfoMaster;
import com.bt.radar.service.AreaRadarService;
import com.bt.radar.service.ExpressinfoMasterInputlistService;
import com.bt.radar.service.ExpressinfoMasterService;
import com.bt.radar.service.RoutecodeService;
import com.bt.radar.service.WarninginfoMaintainMasterService;
import com.bt.radar.service.WaybillWarninginfoMasterService;
import com.bt.utils.BaseConst;
import com.bt.utils.BigExcelExport;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;
import com.bt.utils.ZipUtils;
import com.csvreader.CsvWriter;

/**
 * 快速信息主表控制器
 *
 */
@Controller
@RequestMapping("/control/radar/expressinfoMasterController")
public class ExpressinfoMasterController extends BaseController {

	private static final Logger logger = Logger.getLogger(ExpressinfoMasterController.class);
	
	/**
	 * 快递状态代码服务类
	 */
	@Resource(name = "routecodeServiceImpl")
	private RoutecodeService<Routecode> routecodeServiceImpl;
	
	@Resource(name = "expressinfoMasterInputlistServiceImpl")
	private ExpressinfoMasterInputlistService<ExpressinfoMasterInputlist> expressinfoMasterInputlistServiceImpl;
	
	@Autowired
	private RouteInfoMapper<T> routeInfoMapper;
	
	
	@Autowired
	private RoutecodeMapper<T> routecodeMapper;
	
	/**
	 * 快速信息主表服务类
	 */
	@Resource(name = "expressinfoMasterServiceImpl")
	private ExpressinfoMasterService<ExpressinfoMaster> expressinfoMasterService;
	
	@Resource(name = "warninginfoMaintainMasterServiceImpl")
	private WarninginfoMaintainMasterService<WarninginfoMaintainMaster> warninginfoMaintainMasterService;
	
	@Resource(name = "waybillWarninginfoMasterServiceImpl")
	private WaybillWarninginfoMasterService<WaybillWarninginfoMaster> waybillWarninginfoMasterService;
	
	
	@Resource(name = "areaRadarServiceImpl")
	private AreaRadarService<Area> areaRadarService;
	
	@Autowired
    private ExpressinfoMasterMapper<T> expressinfoMasterMapper;
	@RequestMapping("/query")
	public String toForm(ExpressinfoMasterQueryParam queryParam, HttpServletRequest request) throws Exception{
		List list=routecodeServiceImpl.selectTransport_vender();
		request.setAttribute("trans_names", list);
		List prodeuct_types=expressinfoMasterService.getProduct_type(null);
		request.setAttribute("prodeuct_type", prodeuct_types);
		List physical_warehouses=expressinfoMasterService.getPhysical_Warehouse(null);
		request.setAttribute("physical_warehouses", physical_warehouses);
		List warehouses=expressinfoMasterService.getWarehouse(null);
		request.setAttribute("warehouses", warehouses);
		List stores=expressinfoMasterService.getStore(null);
		request.setAttribute("stores", stores);
		Area area=new Area();
		area.setPid(1);
		List areas=areaRadarService.findArea(area);
		request.setAttribute("areas", areas);
		if(queryParam.getProvice_code()!=null&&!"".equals(queryParam.getProvice_code())){
			area.setPid(2);
			area.setArea_code(queryParam.getProvice_code());
			request.setAttribute("city", areaRadarService.findRecordsByP_code(area));	
		}
		if(queryParam.getCity_code()!=null&&!"".equals(queryParam.getCity_code())){
			area.setPid(3);
			area.setArea_code(queryParam.getCity_code());
			request.setAttribute("state", areaRadarService.findRecordsByP_code(area));	
		}
		List status=routecodeServiceImpl.getStatus(null);
		request.setAttribute("statuss", status);
		WarninginfoMaintainMaster  wm=new WarninginfoMaintainMaster();
		List<WarninginfoMaintainMaster>  wms=
		warninginfoMaintainMasterService.getWarninginfoMaintainMasterService(wm);
		request.setAttribute("wms", wms);
		QueryResult<ExpressinfoMaster> qr=null;
		PageView<ExpressinfoMaster> pageView = new PageView<ExpressinfoMaster>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
	    String 	flag="";
	    flag=getMethodName_f(queryParam);
	    
	    if("".equals(flag)||flag==""){
	    	//普通查询
	    	qr=expressinfoMasterService.findExpressByConditionUnion(queryParam);
	    }else{
	    	qr=expressinfoMasterService.findExpressByWarningUnion(queryParam);
	    }	
		pageView.setQueryResult(qr, queryParam.getPage()); 
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
	  return "/radar/express_info_query";
	}
	
	@RequestMapping("/queryInital")
	public String toFormQueryInital(ExpressinfoMasterQueryParam queryParam, HttpServletRequest request) throws Exception{
		List list=routecodeServiceImpl.selectTransport_vender();
		request.setAttribute("trans_names", list);
		List prodeuct_types=expressinfoMasterService.getProduct_type(null);
		request.setAttribute("prodeuct_type", prodeuct_types);
		List physical_warehouses=expressinfoMasterService.getPhysical_Warehouse(null);
		request.setAttribute("physical_warehouses", physical_warehouses);
		List warehouses=expressinfoMasterService.getWarehouse(null);
		request.setAttribute("warehouses", warehouses);
		List stores=expressinfoMasterService.getStore(null);
		request.setAttribute("stores", stores);
		Area area=new Area();
		area.setPid(1);
		List areas=areaRadarService.findArea(area);
		request.setAttribute("areas", areas);
		if(queryParam.getProvice_code()!=null&&!"".equals(queryParam.getProvice_code())){
			area.setPid(2);
			area.setArea_code(queryParam.getProvice_code());
			request.setAttribute("city", areaRadarService.findRecordsByP_code(area));	
		}
		if(queryParam.getCity_code()!=null&&!"".equals(queryParam.getCity_code())){
			area.setPid(3);
			area.setArea_code(queryParam.getCity_code());
			request.setAttribute("state", areaRadarService.findRecordsByP_code(area));	
		}
		List status=routecodeServiceImpl.getStatus(null);
		request.setAttribute("statuss", status);
		WarninginfoMaintainMaster  wm=new WarninginfoMaintainMaster();
		List<WarninginfoMaintainMaster>  wms=
		warninginfoMaintainMasterService.getWarninginfoMaintainMasterService(wm);
		request.setAttribute("wms", wms);
		QueryResult<ExpressinfoMaster> qr=new QueryResult<ExpressinfoMaster>();
		PageView<ExpressinfoMaster> pageView = new PageView<ExpressinfoMaster>(10,0);
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		qr.setTotalrecord(0);
		qr.setResultlist(null);
		pageView.setQueryResult(qr, 1); 
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
	  return "/radar/express_info_query";
	}
	
	
	
	@RequestMapping("/page")
	public String page(ExpressinfoMasterQueryParam queryParam, HttpServletRequest request) throws Exception{
		/*List list=routecodeServiceImpl.selectTransport_vender();
		request.setAttribute("trans_names", list);
		List prodeuct_types=expressinfoMasterService.getProduct_type(null);
		request.setAttribute("prodeuct_type", prodeuct_types);
		List physical_warehouses=expressinfoMasterService.getPhysical_Warehouse(null);
		request.setAttribute("physical_warehouses", physical_warehouses);
		List warehouses=expressinfoMasterService.getWarehouse(null);
		request.setAttribute("warehouses", warehouses);
		List stores=expressinfoMasterService.getStore(null);
		request.setAttribute("stores", stores);
		Area area=new Area();
		area.setPid(1);
		List areas=areaRadarService.findArea(area);
		request.setAttribute("areas", areas);
		if(queryParam.getProvice_code()!=null&&!"".equals(queryParam.getProvice_code())){
			area.setPid(2);
			area.setArea_code(queryParam.getProvice_code());
			request.setAttribute("city", areaRadarService.findRecordsByP_code(area));	
		}
		if(queryParam.getCity_code()!=null&&!"".equals(queryParam.getCity_code())){
			area.setPid(3);
			area.setArea_code(queryParam.getCity_code());
			request.setAttribute("state", areaRadarService.findRecordsByP_code(area));	
		}
		List status=routecodeServiceImpl.getStatus(null);
		request.setAttribute("statuss", status);*/
		/*WarninginfoMaintainMaster  wm=new WarninginfoMaintainMaster();
		List<WarninginfoMaintainMaster>  wms=
		warninginfoMaintainMasterService.getWarninginfoMaintainMasterService(wm);
		request.setAttribute("wms", wms);*/
		QueryResult<ExpressinfoMaster> qr=null;
		PageView<ExpressinfoMaster> pageView = new PageView<ExpressinfoMaster>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
	    String 	flag="";
	    flag=getMethodName_f(queryParam);
	    if("".equals(flag)||flag==""){
	    	//普通查询
	    	qr=expressinfoMasterService.findExpressByConditionUnion(queryParam);
	    }else{
	    	qr=expressinfoMasterService.findExpressByWarningUnion(queryParam);
	    }
	    if(queryParam.getPage()!=1)qr.setTotalrecord(Integer.parseInt(request.getParameter("pageCountNo").toString()));
		pageView.setQueryResult(qr, queryParam.getPage()); 
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
	  return "/radar/express_info_page";
	}
	
	
	@ResponseBody
	@RequestMapping("/queryData")
	public JSONObject toFormData(ExpressinfoMasterQueryParam queryParam, HttpServletRequest request) throws Exception{
		JSONObject obj=new JSONObject();
		int page=queryParam.getPage();
		QueryResult<ExpressinfoMaster> qr=null;
		PageView<ExpressinfoMaster> pageView = new PageView<ExpressinfoMaster>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
	    String 	flag="";
	    flag=getMethodName_f(queryParam);
	   /* if("".equals(flag)||flag==""){
			//普通查询
			qr=expressinfoMasterService.findExpressByCondition(queryParam);
		}else{
			flag=getMethodName_B(queryParam);
			if("".equals(flag)||flag==""){
			qr=	expressinfoMasterService.findAllWarninData(queryParam);	
			}else{
			qr=expressinfoMasterService.findAllData(queryParam);	
			}
		}	*/
	    
	    if("".equals(flag)||flag==""){
	    	//普通查询
	    	qr=expressinfoMasterService.findExpressByConditionUnion(queryParam);
	    }else{
	    	qr=expressinfoMasterService.findExpressByWarningUnion(queryParam);
	    }
	    
	    	obj.put("qr", qr);	
	        obj.put("page",queryParam.getPage());
	  return obj;
	}
	
	
		
	public  String  getMethodName_f(ExpressinfoMasterQueryParam q){
		if(q.getWarning_category()!=null&&!"".equals(q.getWarning_category()))return "B";
		if(q.getDel_flag()!=null&&!"".equals(q.getDel_flag())) return "B";
		if(q.getWarning_level()!=null&&!"".equals(q.getWarning_level()))   return "B";
		if(q.getWarningtype_code()!=null&&!"".equals(q.getWarningtype_code())) return "B";
		return "";
	}
	public  String  getMethodName_B(ExpressinfoMasterQueryParam q){
			if(q.getAddress()!=null&&!"".equals(q.getAddress()))return "C";
			if(q.getCheck_time()!=null&&!"".equals(q.getCheck_time()))return "C";
			if(q.getCity_code()!=null&&!"".equals(q.getCity_code()))return "C";
			if(q.getExpress_code()!=null&&!"".equals(q.getExpress_code()))return "C";
			if(q.getPlatform_no()!=null&&!"".equals(q.getPlatform_no()))return "C";
			if(q.getProducttype_code()!=null&&!"".equals(q.getProducttype_code()))return "C";
			if(q.getProvice_code()!=null&&!"".equals(q.getProvice_code()))return "C";
			if(q.getStore_code()!=null&&!"".equals(q.getStore_code()))return "C";
			if(q.getEnd_time()!=null&&!"".equals(q.getEnd_time()))return "C";
			if(q.getStart_time()!=null&&!"".equals(q.getStart_time()))return "C";
			if(q.getStreet_code()!=null&&!"".equals(q.getStreet_code()))return "C";
			if(q.getWork_no()!=null&&!"".equals(q.getWork_no()))return "C";
			if(q.getWarehouse_code()!=null&&!"".equals(q.getWarehouse_code()))return "C";
			return "";
		}
	
	
	@ResponseBody
	@RequestMapping("/getArea")
	public  JSONObject  getArea(Area entity, HttpServletRequest request){
		JSONObject obj =new JSONObject();
		List<Area> areas=  areaRadarService.getArea(entity.getArea_code());
		if(areas!=null&&areas.size()!=0){
			obj.put("area", areas);
			obj.put("code", 1);
			
		}else{
			
			obj.put("code", 0);
		}
		return obj;
	}
	@RequestMapping("/toDetails")
	public String toDetails(ExpressinfoMasterQueryParam queryParam, HttpServletRequest request) throws Exception{
		PageView<ExpressinfoMaster> pageView = new PageView<ExpressinfoMaster>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		String url=(String) request.getAttribute("url");
		QueryResult<ExpressinfoMaster> qr=expressinfoMasterService.findExpressByConditionUnionNotCount(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		RouteInfo f=new RouteInfo();
		//(ExpressinfoMaster)qr.getResultlist().get(0)=null;
		Map<String,Object> em=new HashMap();
		em.put("waybill",queryParam.getWaybill() );
		f.setWaybill(queryParam.getWaybill());
		List<RouteInfo> listr= routeInfoMapper.findRecords(f);
		if(qr.getResultlist()==null||qr.getResultlist().size()==0){
			request.setAttribute("queryParam", queryParam);
			  return "/radar/express_info_details";
		}else{
			request.setAttribute("queryParam", qr.getResultlist().get(0));
		}
		List  details=expressinfoMasterService.findDetailsByOrderNO(em);	
		List  alarms=expressinfoMasterService.findAlarmDetailsByOrderNO_ADV(em);
		
		if(listr!=null&&listr.size()!=0)
		request.setAttribute("route", listr);
	//	if(details!=null&&details.size()!=0)
		request.setAttribute("wraps",  details);
		if(qr.getResultlist()!=null&qr.getResultlist().size()!=0)
		request.setAttribute("alarms", alarms);
		Map mq=new HashMap();
		mq.put("warning_category", "0");
		List  warn_type=
		warninginfoMaintainMasterService.selectWarn_type(mq);
		if(warn_type!=null&&warn_type.size()!=0)
			request.setAttribute("warn_type", warn_type);	
	  return "/radar/express_info_details";
	}
	@ResponseBody
	@RequestMapping("/download1")
	public JSONObject updownload1(ExpressinfoMasterQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) throws Exception{
		ExpressinfoMasterQueryParam qr0=queryParam;
		//expressinfoMasterService.downLoad(queryParam);
		JSONObject obj= new JSONObject();
		int pageCount= 20000;
		String filePath= CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname());
		String str= (new SimpleDateFormat("yyyyMMdd-HHmmssSSS")).format(new Date()); 
		File file= new File(filePath + str);
		if(!file.exists())file.mkdirs();
		int j= 0;
		int i= 0; 
		for(;;j++){
			qr0.setFirstResult(i*pageCount);
			qr0.setMaxResult(1);               
			List list0=expressinfoMasterMapper.downLoad(qr0);
			if(list0==null||list0.size()==0)break;
	   CsvWriter write=	new CsvWriter(filePath+str+"/"+str+"Document_"+j+".csv",',',Charset.forName("gb2312"));
	   String head[]=new String[]{"ID","运单号","作业单号","平台订单号","收件人","电话","地址","复合时间","称重时间","新运单号",
			   "物流商","产品类型","系统仓","物理仓","店铺名","目的地省","目的地市","目的地区",
			 //  "最新路由状态","预警状态","预警名称","预警级别","预警类型",
			   "揽件时间","实际签收时间","理论签收时间","系统调用时间","","","","","","","","","","","","","","","","","",""
			   ,"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""}; 
	  
	   String keys[]=new String []{"id","waybill","work_no","platform_no","shiptoname","phone","address","check_time","weight_time","new_waybill",
		   "express_name","producttype_name","warehouse_name","physical_name","store_name","provice_name","city_name","state_name"
		  // ,"status","warning_status","warningtype_name","warning_level","warning_category"
		   ,"embrance_time","receive_time","standard_receive_time","run_time"};
	     write.writeRecord(head); 
	      QueryResult<ExpressinfoMaster> qr=null;	
	      int ii=i+10;
	   for(;i<ii;i++){
		   queryParam.setFirstResult(i*pageCount);
		   queryParam.setMaxResult(pageCount);
		   List<Map<String, Object>> list= expressinfoMasterMapper.downLoad(queryParam);
		   if(list==null||list.size()==0)break;
				  for(Map<String, Object> map :list){
					  Map <String,Object> mapObj=(Map <String,Object>) map;
					  write.writeRecord(mapToStringList(map,keys));
			          }		
	          }
	         write.close();
		}
		
		ZipUtils.zip(filePath+str, filePath);
	    obj.put("data", (str+".zip"));
		return  obj;
	}
	
	
	@ResponseBody
	@RequestMapping("/download")
	public JSONObject updownload(ExpressinfoMasterQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) throws Exception{
		ExpressinfoMasterQueryParam qr0=queryParam;
		//expressinfoMasterService.downLoad(queryParam);
		JSONObject obj =new JSONObject();
		int  pageCount=20000;
		String filePath="";
		filePath=CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname());
		String str = (new SimpleDateFormat("yyyyMMdd-HHmmssSSS")).format(new Date()); 
		File file=new File(filePath+str);
		if(!file.exists())file.mkdirs();
		int  j=0; 
		Map map =new LinkedHashMap<String,String>();
		map.put("id", "ID");
		map.put("waybill", "运单号");
		map.put("work_no", "作业单号");
		map.put("platform_no", "平台订单号");
		map.put("shiptoname", "收件人");
		map.put("phone", "电话");
		map.put("address", "地址");
		map.put("check_time", "复合时间");
		map.put("weight_time", "称重时间");
		map.put("new_waybill", "新运单号");
		map.put("embrance_time", "揽件时间");
		map.put("receive_time", "实际签收时间");
		map.put("standard_receive_time","理论签收时间");
		map.put("run_time", "系统调用时间");
		map.put("route_time", "最新路由时间");
		map.put("route_info", "最新路由明细");
		for(;;j++){
			qr0.setFirstResult(j*pageCount);
			qr0.setMaxResult(1);               
			List list0=expressinfoMasterMapper.downLoad(qr0);
			if(list0==null||list0.size()==0)break;
			queryParam.setFirstResult(j*pageCount);
			queryParam.setMaxResult(pageCount);
			
			BigExcelExport.excelDownLoadDatab_Z(expressinfoMasterMapper.downLoad(queryParam),(LinkedHashMap<String, String>) map,str,"express_data_"+j+".xls");
			}
		
		ZipUtils.zip(filePath+str, filePath);
	    obj.put("data", (str+".zip"));
		return  obj;
	}
	
	
	
	@ResponseBody
	@RequestMapping("/download0")
	public JSONObject updownload0(ExpressinfoMasterQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) throws Exception{
		//PrintWriter out = null;
		JSONObject obj =new JSONObject();
		//out = response.getWriter();
		/*String str = (new SimpleDateFormat("yyyyMMdd-HHmmssSSS")).format(new Date()); 
		Properties prop = new Properties();
		prop.load(this.getClass().getClassLoader().getResourceAsStream("/config.properties"));
		String downloadpath=prop.getProperty("download");
		File file=new File(downloadpath+str);
		if(!file.exists())file.mkdirs();
		String filepath=file.getPath();
		queryParam.setFirstResult(0);
		queryParam.setMaxResult(1);
		int i=0;
		while(true){
			List list=expressinfoMasterMapper.findExpressByWarningUnion(queryParam);
			if(list==null||list.size()==0)break;
			//
			queryParam.setFirstResult(200000*i);
			queryParam.setMaxResult(2000000);
			expressinfoMasterMapper.downloadCsv(queryParam);
			i++;
			queryParam.setFirstResult(2000000*i);
			queryParam.setMaxResult(2000000*i+1);
		}
		String  filepath0=doZip(file.getPath());
	    obj.put("data", prop.get("nginx_path")+"\\"+filepath0);*/
		expressinfoMasterMapper.downloadCsv(null);
		return  obj;
	}
	public String  doZip(String path){
		String  zipPath=path+".zip";
		String path0="";
		if(OSinfo.getOSname().equals(EPlatform.Mac_OS_X)){
			path0=CommonUtils.getAllMessage("config", "FileUpload");
		}else{
			path0=CommonUtils.getAllMessage("config", "WINDOWS_excel_prefix");
		}
		try {
			ZipUtils.zip(path, path0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return zipPath;
	}
	public  String []  mapToStringList(Map<String,Object> param,String[] keys){
		if(param==null||param.size()==0)return null;
		String[] result=new String[keys.length];
		int i=0;
		for(String key:keys){
		String value="";	
		value=param.get(key)==null?"":param.get(key).toString().replaceAll(",", "");
		result[i]=value;
		i++;
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/getRouteStatus")
	public JSONObject getRouteStatus(RoutecodeQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject obj=new JSONObject();
		List<T> list=routecodeMapper.findAll(queryParam);
		Map param =new HashMap();
		param.put("vendor_code", queryParam.getTransport_code());
		List prodeuct_types=expressinfoMasterService.getProduct_type(param);
		obj.put("data", list);
		obj.put("products", prodeuct_types);
		return obj;
	
	}
	
	
	@RequestMapping("/waybillUploadPage")
	public String waybillUploadPage(ExpressinfoMasterInputlistQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) throws Exception{
		QueryResult<ExpressinfoMasterInputlist> qr=null;
		PageView<ExpressinfoMasterInputlist> pageView = new PageView<ExpressinfoMasterInputlist>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		qr=expressinfoMasterInputlistServiceImpl.findAll(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage()); 
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "/radar/waybill_upload";
	
	}
	public static void main(String args[]) throws Exception{
		//ZipUtils.zip("C:\\lmis_export\\20170323-130228419", "C:\\lmis_export");
		String  ss="replaceAll(fsfsfsfsddf,,,,dsfdsfsfdsfs)";
		System.out.println(ss.replaceAll(",", ""));
	}
	
}
