package com.bt.radar.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import com.bt.lmis.model.Employee;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.RoutecodeQueryParam;
import com.bt.radar.controller.form.WarningRoutestatusListQueryParam;
import com.bt.radar.controller.form.WarninginfoMaintainMasterQueryParam;
import com.bt.radar.controller.form.WarninglevelListQueryParam;
import com.bt.radar.model.Routecode;
import com.bt.radar.model.WarningRoutestatusList;
import com.bt.radar.model.WarninginfoMaintainMaster;
import com.bt.radar.model.WarninglevelList;
import com.bt.radar.service.RoutecodeService;
import com.bt.radar.service.WarningRoutestatusListService;
import com.bt.radar.service.WarninginfoMaintainMasterService;
import com.bt.radar.service.WarninglevelListService;
import com.bt.utils.BaseConst;
import com.bt.utils.SessionUtils;

@Controller
@RequestMapping("/controller/express_alarm")
public class ExpressAlarmController {


	private static final Logger logger = Logger.getLogger(ExpressAlarmController.class);
	
	@Resource(name = "routecodeServiceImpl")
	private RoutecodeService<Routecode> routecodeServiceImpl;
	
	@Resource(name = "warninginfoMaintainMasterServiceImpl")
	private WarninginfoMaintainMasterService<WarninginfoMaintainMaster> warninginfoMaintainMasterService;
	
	@Resource(name = "warninglevelListServiceImpl")
	private WarninglevelListService<WarninglevelList> warninglevelListService;
	
	@Resource(name = "warningRoutestatusListServiceImpl")
	private WarningRoutestatusListService<WarningRoutestatusList> warningRoutestatusListService;
	/**
	 * 快递预警信息查询
	 *
	 */
	@RequestMapping("/query")
	public String ExpressAlarmQuery(WarninginfoMaintainMasterQueryParam queryParam, HttpServletRequest request){
		try{
			PageView<WarninginfoMaintainMaster> pageView = new PageView<WarninginfoMaintainMaster>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<WarninginfoMaintainMaster> qr=warninginfoMaintainMasterService.findAllData(queryParam);
		    List list=warninginfoMaintainMasterService.selectOption(null);
			pageView.setQueryResult( qr, queryParam.getPage());
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
			request.setAttribute("warningtype", list);
		}catch(Exception e){
			logger.error(e);
			}
		return "/radar/express_alarm_query";
	}
	
	
	@RequestMapping("/edit")
	public String ExpressAlarmEdit(){
		
		return "/radar/express_alarm_add";
	}
	
	@RequestMapping("/toadd")
	public String ExpressAlarmAdd(){
		
		return "/radar/express_alarm_add";
	}
	
	/**
	 * 快递预警信息修改
	 *
	 */
	@RequestMapping("/toUpdate")
	public String ExpressAlarmUpdate(WarninginfoMaintainMasterQueryParam queryParam, HttpServletRequest request){
		PageView<WarninginfoMaintainMaster> pageView = new PageView<WarninginfoMaintainMaster>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		//根据id 获得WarninginfoMaintainMaster 对象
		List<?> Master=
		 warninginfoMaintainMasterService.selectByParam(queryParam);
		if(Master.size()!=0&&Master!=null)
		request.setAttribute("queryParam", Master.get(0));
		//根据关联 获得预警状态维护列表
		//warningRoutestatusListService
//		WarningRoutestatusListQueryParam  statusQuery=new WarningRoutestatusListQueryParam();
//		statusQuery.setDl_flag(1);
//		statusQuery.setWarningtype_code(Master.get(0).getWarningtype_code());
		//根据关联 获得预警级别升级维护列表
		WarninglevelListQueryParam wlq=new WarninglevelListQueryParam();
		Map<String,Object> obj=(Map<String,Object>)Master.get(0);
		System.out.println(obj.get("warningtype_code").toString());
		wlq.setWarningtype_code(obj.get("warningtype_code").toString());
		wlq.setDl_flag(1);
		List wll=
		warninglevelListService.findByWType(wlq);
		if(wll!=null&&wll.size()!=0)request.setAttribute("wll", wll);
		Map param=new HashMap();
		param.put("id",queryParam.getId());
	    List routes= routecodeServiceImpl.selectRecordsByWID(param);
	    if(routes.size()!=0&&routes!=null)request.setAttribute("routes",routes);
		//查询条件
		List transport_venders=routecodeServiceImpl.getTransport_vender();
		request.setAttribute("transport_venders", transport_venders);
		
		return "/radar/express_alarm_update";
	}
	/**
	 * 快递预警信息修改
	 *
	 */
	@ResponseBody
	@RequestMapping("/updateSubmit")
	public  JSONObject  updateSubmit(WarninginfoMaintainMaster entity, HttpServletRequest request){
		JSONObject obj =new JSONObject();
		
		Employee user = SessionUtils.getEMP(request);
		entity.setUpdate_user(user.getName());
		entity.setUpdate_time(new Date());
		entity.setDl_flag(1);
		try {
			warninginfoMaintainMasterService.update(entity);
			obj.put("code", 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			obj.put("code", 0);
			logger.error(e);
		}
		return obj;
	}
	
	

	@ResponseBody
	@RequestMapping("/addSubmit")
	public  JSONObject  add(WarninginfoMaintainMaster entity, HttpServletRequest request){
		JSONObject obj =new JSONObject();
		entity.setId(UUID.randomUUID().toString());
		Date d=new Date();
		entity.setCreate_time(d);
		entity.setUpdate_time(d);                
		entity.setDl_flag(1);
		Employee user = SessionUtils.getEMP(request);
		entity.setCreate_user(user.getName());
		entity.setUpdate_user(user.getName());
		//检验是否重复 
		//WarninginfoMaintainMasterQueryParam  param=new  WarninginfoMaintainMasterQueryParam();
		//param.setWarningtype_code(entity.getWarningtype_code());
		//param.setWarningtype_name(entity.getWarningtype_name());
	    Map param=new HashMap();
	    param.put("warningtype_code", entity.getWarningtype_code());
	    param.put("warningtype_name", entity.getWarningtype_name());
		List<WarninginfoMaintainMaster> list=
		warninginfoMaintainMasterService.checkCode_name(param);
		if(list!=null&&list.size()!=0){
			obj.put("code", 0);
			obj.put("msg", "预警类型代码或名称数据库已存在！");
			return  obj;
		}
		    WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
			DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
			TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		try {
			warninginfoMaintainMasterService.save(entity);
			WarninglevelList wll=new WarninglevelList();
			wll.setWarningtype_code(entity.getWarningtype_code());
			wll.setCreate_time(d);
			wll.setUpdate_time(d);
			wll.setCreate_user(user.getName());
			wll.setUpdate_user(user.getName());
			wll.setDl_flag(1);
			wll.setId(UUID.randomUUID().toString());
			wll.setLevelup_level(entity.getInitial_level());
			wll.setLevelup_time("0");
			warninglevelListService.save(wll);
			obj.put("code", 1);
			transactionManager.commit(status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			obj.put("code", 0);
			logger.error(e);
			transactionManager.rollback(status);
		}
		return obj;
	}
	
	//删除主表
	@ResponseBody
	@RequestMapping("/del")
	public  JSONObject  del(WarningRoutestatusList entity, HttpServletRequest request){
		JSONObject obj =new JSONObject();
		Integer  del_no=	warninginfoMaintainMasterService.checkDel(entity.getId());
		if(del_no!=0){obj.put("code", 0);  obj.put("msg", "您要删除的信息与已经被时效绑定！");return obj;}
		WarninginfoMaintainMasterQueryParam  wqr=new WarninginfoMaintainMasterQueryParam();
		wqr.setId(entity.getId());
		QueryResult<WarninginfoMaintainMaster>  qs=	warninginfoMaintainMasterService.findAllData(wqr);
		Map<String,Object>  wmater=(Map<String, Object>) qs.getResultlist().get(0);
		Map dparam =new HashMap();
		dparam.put("warningtype_code", wmater.get("warningtype_code").toString());
		 WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
			DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
			TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		try {
		
			warninglevelListService.deleteByCon(dparam);
			warninginfoMaintainMasterService.delete(entity.getId());
			obj.put("code", 1);
			transactionManager.commit(status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			obj.put("code", 0);
			logger.error(e);
			transactionManager.rollback(status);
		}
		return obj;
	}
	
	@ResponseBody
	@RequestMapping("/getStatus_code")
	public  JSONObject  getStatus_code(WarninginfoMaintainMaster entity, HttpServletRequest request){
		JSONObject obj =new JSONObject();
         String transport_code=request.getParameter("transport_code");
         RoutecodeQueryParam  q=new RoutecodeQueryParam();
         q.setTransport_code(transport_code);
         List status_codes=
         routecodeServiceImpl.findAllRecord(q);
          obj.put("records", status_codes);
         return obj;
	}
	
	//添加预警状态维护列表
	@ResponseBody
	@RequestMapping("/addWarning_routestatus_list")
	public  JSONObject  addWarning_routestatus_list(WarningRoutestatusListQueryParam query,WarningRoutestatusList entity, HttpServletRequest request){
		JSONObject obj =new JSONObject();
		entity.setId(UUID.randomUUID().toString());
		Date d=new Date();
		entity.setCreate_time(d);
		entity.setUpdate_time(d);                
		entity.setDl_flag(1);
		Employee user = SessionUtils.getEMP(request);
		entity.setCreate_user(user.getName());
		entity.setUpdate_user(user.getName());;
		//check 是否已经存在记录  
		int flag=warningRoutestatusListService.checkExisit(query);
		if(flag>0){
			obj.put("code", 2);	
			return obj;
		}
		try {
			warningRoutestatusListService.save(entity);
			obj.put("code", 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			obj.put("code", 0);
			logger.error(e);
		}
		return obj;
	}
	
	
	//删除预警升级维护列表信息
	@ResponseBody
	@RequestMapping("/delWRList")
	public  JSONObject  delWRList(WarningRoutestatusList entity, HttpServletRequest request){
		JSONObject obj =new JSONObject();
		
		try {
			warningRoutestatusListService.delete(entity.getId());
			obj.put("code", 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			obj.put("code", 0);
			logger.error(e);
		}
		return obj;
	}
	
	//删除预警状态维护列表信息
		@ResponseBody
		@RequestMapping("/delwll")
		public  JSONObject  delwll(WarningRoutestatusList entity, HttpServletRequest request){
			JSONObject obj =new JSONObject();
			try {
				warninglevelListService.delete(entity.getId());
				obj.put("code", 1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				obj.put("code", 0);
				logger.error(e);
			}
			return obj;
		}
	
		
		//修改预警状态维护列表信息
			@ResponseBody
			@RequestMapping("/upwll")
			public  JSONObject  upwll(WarninglevelListQueryParam query, HttpServletRequest request){
				JSONObject obj =new JSONObject();
				Employee user = SessionUtils.getEMP(request);
				query.setUpdate_user(user.getName());
				query.setUpdate_time(new Date());
				try {
				int flag=	warninglevelListService.updateWll(query);
					obj.put("code", flag);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					obj.put("code", 0);
					logger.error(e);
				}
				return obj;
			}
			
			
			@ResponseBody
			@RequestMapping("/addwll")
			public  JSONObject  addwll(WarninglevelList entity, HttpServletRequest request){
				JSONObject obj =new JSONObject();
				entity.setId(UUID.randomUUID().toString());
				Date d=new Date();
				entity.setCreate_time(d);
				entity.setUpdate_time(d);                
				entity.setDl_flag(1);
				Employee user = SessionUtils.getEMP(request);
				entity.setCreate_user(user.getName());
				entity.setUpdate_user(user.getName());
				try {
					warninglevelListService.save(entity);
					obj.put("code", 1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					obj.put("code", 0);
					logger.error(e);
				}
				return obj;
			}			
}
