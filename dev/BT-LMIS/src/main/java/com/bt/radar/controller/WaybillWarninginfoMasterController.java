package com.bt.radar.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.Employee;
import com.bt.radar.controller.form.WaybillWarninginfoMasterQueryParam;
import com.bt.radar.dao.WarninglevelListMapper;
import com.bt.radar.dao.WaybillWarninginfoDetailMapper;
import com.bt.radar.dao.WaybillWarninginfoMasterMapper;
import com.bt.radar.model.ExpressinfoMaster;
import com.bt.radar.model.WaybillWarninginfoDetail;
import com.bt.radar.model.WaybillWarninginfoMaster;
import com.bt.radar.service.ExpressinfoMasterService;
import com.bt.radar.service.WaybillWarninginfoMasterService;
import com.bt.utils.Constant;
import com.bt.utils.SessionUtils;
import com.bt.workOrder.service.WorkOrderLevelUpService;
import com.bt.workOrder.service.WorkOrderManagementService;
/**
 * 预警信息主表控制器
 *
 */
@Controller
@RequestMapping("/control/radar/waybillWarninginfoMasterController")
public class WaybillWarninginfoMasterController extends BaseController {

	private static final Logger logger = Logger.getLogger(WaybillWarninginfoMasterController.class);
	
	/**
	 * 预警信息主表服务类
	 */
	@Resource(name = "waybillWarninginfoMasterServiceImpl")
	private WaybillWarninginfoMasterService<WaybillWarninginfoMaster> waybillWarninginfoMasterService;
	@Resource(name = "workOrderManagementServiceImpl")
	private WorkOrderManagementService<T> workOrderManagementServiceImpl;
	@Resource(name = "workOrderLevelUpServiceImpl")
	private WorkOrderLevelUpService<T>  workOrderLevelUpService;
	@Autowired
    private WaybillWarninginfoMasterMapper<T> mapper;
	@Autowired
    private WaybillWarninginfoDetailMapper<T> Dmapper;
	@Resource(name = "expressinfoMasterServiceImpl")
	private ExpressinfoMasterService<ExpressinfoMaster> expressinfoMasterService;
	
	
	@Autowired  
	WarninglevelListMapper  levelMapper;
	//根据主表id 获得预警信息从表数据
	
	@ResponseBody
	@RequestMapping("/toDetails")
	public JSONObject toDetails(WaybillWarninginfoMasterQueryParam queryParam, HttpServletRequest request) throws Exception{
		JSONObject obj=new JSONObject();
		Map m=new HashMap();
		m.put("id", queryParam.getId());
   List <WaybillWarninginfoDetail> details=
	     waybillWarninginfoMasterService.findAlarmDetails(m);
   if(details!=null&&details.size()!=0){
//	 Map<String,String> detail=  (Map<String, String>) details.get(0);
//	   if("1".equals(detail.get("warning_category"))){
//		   if("0".equals(detail.get("end_levelup_flag"))){
//			//更新预警级别   
//			   Map
//  			 params=new HashMap();
//			   Object base=(Object)detail.get("efficient_time");
//			   java.sql.Timestamp basedate=(Timestamp) base;
//			   java.util.Date db=  new java.util.Date (basedate.getTime());
//			   Object d1=(Object)detail.get("route_time");
//			   java.sql.Timestamp d1date=(Timestamp) d1;
//			   java.util.Date route_time=  new java.util.Date (d1date.getTime());
//  			params.put("time",  getMinutesBetween(route_time,db));
//  			params.put("warningtype_code",detail.get("warningtype_code"));
//  			params=  levelMapper.analyzeLevel(params);
//                 if(params==null||params.get("max_num")==null){};
//                  if(params.get("min_num")==null){
//                   params.put("level", params.get("max_num"));
//                   detail.put("warning_level", params.get("level").toString());
//           	       Map<String,String> pp=new HashMap<String,String>();
//           	       pp.put("warning_level", params.get("level").toString());
//           	       pp.put("id",detail.get("id").toString());
//                   levelMapper.updateLevel(pp);
//                     }else{
//            	    params.put("level", params.get("min_num"));	
//            	    detail.put("warning_level", params.get("level").toString());
//            	    Map<String,String> pp=new HashMap<String,String>();
//            	       pp.put("warning_level", params.get("level").toString());
//            	       pp.put("id",detail.get("id").toString());
//            	    levelMapper.updateLevel(pp);
//            	 
//                  }   
//		   }
//	   }
//	 
	   obj.put("data", details) ;
   }
	  return obj;
	}
	public long getMinutesBetween(Date base,Date d1){
		if(base==null||d1==null)return -1;
		return 	(-d1.getTime()+
				base.getTime())/1000*60;
	}
	
	@ResponseBody
	@RequestMapping("/del")
	public JSONObject del(WaybillWarninginfoMasterQueryParam queryParam, HttpServletRequest request) throws Exception {
		JSONObject obj=new JSONObject();
		Map m=new HashMap();
		m.put("id", queryParam.getId());
		WaybillWarninginfoDetail  detail=	waybillWarninginfoMasterService.findAlarmDetails(m).get(0);
		WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		// 事物隔离级别，开启新事。
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		// 获得事务状态
		TransactionStatus status = transactionManager.getTransaction(def);
  try{
	  if(detail.getWk_id()!=null&&!"".equals(detail.getWk_id())){
		  Map param=new HashMap();
		  param.put("operation","cancel");
		  param.put("wo_id", detail.getWk_id());
		  param.put("event", Constant.WO_EVENT_CANCEL);
		 //param.put("powerFlag", false);
		  workOrderManagementServiceImpl.operate(null,param);
		  
	  }
	  Dmapper.updateDel(m);
	  m=new HashMap();
	  m.put("waybill",request.getParameter("waybill") );
	  obj.put("data_list", waybillWarninginfoMasterService.findAlarmDetails(m));
	  obj.put("code", "1");
	  transactionManager.commit(status);
  }catch(Exception e){
	  logger.error(e);
	  obj.put("code", "0");
	  obj.put("msg", "操作失败");
	  transactionManager.rollback(status);
	  throw e;
  }
 
  
	  return obj;
	}
	
	@ResponseBody
	@RequestMapping("/addWarning")
	public  JSONObject  saveEvent(WaybillWarninginfoMaster entity,HttpServletRequest request)throws Exception{
		JSONObject obj =new JSONObject();
		//判断从表是否有信息
		WaybillWarninginfoDetail detail=new WaybillWarninginfoDetail();
		detail.setWarningtype_code(request.getParameter("warningtype_code"));
		detail.setDel_flag("0");
		detail.setWaybill(request.getParameter("waybill"));
		List<WaybillWarninginfoDetail> listd=
		Dmapper.findRecords(detail);
		if(listd==null||listd.size()==0){
			Date d=new Date();
			Employee user = SessionUtils.getEMP(request);
			detail.setCreate_time(d);
			detail.setUpdate_time(d);
			detail.setUpdate_user(user.getName());
			detail.setCreate_user(user.getName());
			detail.setHappen_time(d);
			String idd=UUID.randomUUID().toString();
			detail.setWarning_category(request.getParameter("warning_category"));
			detail.setWarningtype_code(request.getParameter("warningtype_code"));
			detail.setId(idd);
			detail.setDel_flag("0");
			detail.setWaybill(request.getParameter("waybill"));
			detail.setExpress_code(request.getParameter("express_code"));
			detail.setReason(request.getParameter("remark"));
			detail.setSource(user.getName());
			detail.setWarning_level(request.getParameter("warning_level"));
			Dmapper.insertWaybillWarninginfoDetail(detail);
			//workOrderLevelUpService.RadarGenWk(detail);
			obj.put("code", 1);
			//WaybillWarninginfoDetail m=new WaybillWarninginfoDetail();
			//m.setWaybill(request.getParameter("waybill"));
			//obj.put("data_list", Dmapper.findRecords(m));
			Map em=new HashMap();
			em.put("waybill", detail.getWaybill());
			obj.put("data_list", expressinfoMasterService.findAlarmDetailsByOrderNO_ADV(em));
		}else{
			obj.put("code", 0);
			obj.put("msg", "不能重复添加");	
		}
		return obj;
	}	
	
	
}
