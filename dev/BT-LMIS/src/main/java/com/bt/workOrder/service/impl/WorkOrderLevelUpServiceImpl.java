package com.bt.workOrder.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.page.QueryParameter;
import com.bt.radar.controller.form.ExpressinfoMasterQueryParam;
import com.bt.radar.dao.ExpressinfoMasterMapper;
import com.bt.radar.dao.WaybillWarninginfoDetailMapper;
import com.bt.radar.model.WaybillWarninginfoDetail;
import com.bt.utils.Constant;
import com.bt.utils.DateUtil;
import com.bt.utils.observer.Observers;
import com.bt.utils.observer.Visual;
import com.bt.workOrder.controller.form.GenerationRuleQueryParam;
import com.bt.workOrder.dao.GenerationRuleMapper;
import com.bt.workOrder.dao.WkLevelMapper;
import com.bt.workOrder.dao.WkTypeMapper;
import com.bt.workOrder.dao.WorkOrderManagementMapper;
import com.bt.workOrder.model.WorkOrder;
import com.bt.workOrder.model.WorkOrderEventMonitor;
import com.bt.workOrder.service.WorkOrderLevelUpService;
import com.bt.workOrder.service.WorkOrderManagementService;
import com.bt.workOrder.utils.WorkOrderUtils;


@Service
public class WorkOrderLevelUpServiceImpl <T> extends ServiceSupport<T>   implements WorkOrderLevelUpService<T> {

	public final Logger log = Logger.getLogger(this.getClass());
	@Autowired
	ExpressinfoMasterMapper expressinfoMasterMapper;
	@Autowired
	WaybillWarninginfoDetailMapper   waybillWarninginfoDetailMapper;
	@Autowired 
	WkLevelMapper wkLevelMapper;
	@Autowired 
	WkTypeMapper wkTypeMapper;
	@Autowired 
	GenerationRuleMapper  generationRuleMapper;
	@Autowired
    private WorkOrderManagementMapper<T> workOrderManagementMapper;

	@Override
	public void  LevelUp() throws Exception{
		//step 1 查询过时的工单记录
		Map param =new HashMap();
		param.put("watched", 1);  //1:工单升级监控中  0：工单升级监控停止
	List<Map<String,Object>> list=	workOrderManagementMapper.findNeedLevelUpData(param);
		if(list!=null&&list.size()!=0){
			for(Map<String, Object> m: list){
				LevelUp(m);
			}
		}
	}

	public  void  LevelUp(Map<String ,Object> obj) throws Exception{
      String   wk_type=obj.get("wo_type").toString();
      String   wk_level=obj.get("wo_level").toString();
      String   wk_level_new="";
      Map param=new HashMap();
          param.put("code", wk_level);
          param.put("flag", 1);
          param.put("wk_type", wk_type);
      //计算新的cal_date 
        Date cal_date=DateUtil.StrToTime(obj.get("cal_date").toString());
        Map<String,Object>  listlevel=   wkLevelMapper.findLevelUpLevel(param);
          if(listlevel==null||listlevel.size()==0){
        	  stopWatch(obj);
        	  return;
          }
        obj.put("level_starttime", cal_date); 
        obj.put("standardManhours", listlevel.get("wk_standard").toString());
        cal_date=DateUtil.MoveMin(cal_date, Integer.parseInt(listlevel.get("wk_timeout").toString()));
        obj.put("cal_date",DateUtil.format(cal_date));
        obj.put("wo_level_display",listlevel.get("level_name"));
		obj.put("wo_level", listlevel.get("level_code"));
		obj.put("carrier", obj.get("carrier").toString());
		updateLevel(obj);

    }
	

	
	public void stopWatch(Map<String ,Object> obj){
		//升级已经到达最高级  改变watched 状态 停止升级  1：监控中  0：取消升级监控
		workOrderManagementMapper.stopWatch(obj.get("id").toString());
	}
	
	public   void startLevelUp(){	
	//   sevice.LevelUp();
	}
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void parseCaldate() {
		// TODO Auto-generated method stub
		wkTypeMapper.parseCaldate();
	}

	@Override
	public void updateLevel(Map obj) throws Exception {
		// TODO Auto-generated method stub
		WorkOrderManagementService  workOrderManagementServiceImpl=
				((WorkOrderManagementService<T>)SpringUtils.getBean("workOrderManagementServiceImpl"));
		Map param=new HashMap();
		workOrderManagementMapper.updateWorkOrder_se(obj);
		if(obj.get("processor")==null||"".equals(obj.get("processor"))){
			 WorkOrderEventMonitor woem= new WorkOrderEventMonitor();
				woem.setCreate_by("0");
				woem.setWo_id(obj.get("id").toString());
				woem.setId(UUID.randomUUID().toString());
				woem.setEvent(Constant.WO_EVENT_UPDATE);
				woem.setEvent_description("工单升级--"+obj.get("wo_level").toString());
				woem.setRemark("工单升级--"+obj.get("wo_level").toString()+"工单时效--"+obj.get("cal_date").toString());
				woem.setEvent_status(true);
				woem.setSort(workOrderManagementMapper.getSort(obj.get("id").toString())+1);
				workOrderManagementMapper.addWorkOrderEvent(woem);
				return;
		};
		
		//检测该员工是否有处理升级后的全权限
	  Integer  power=	wkLevelMapper.hasPowerToOpera(obj);
	  if(power==null||power==0){
		  param.put("event", Constant.WO_EVENT_CANCEL_ALLOC);
		  param.put("wo_id", obj.get("id"));
		  WorkOrderEventMonitor woem= new WorkOrderEventMonitor();
			woem.setCreate_by("0");
			woem.setWo_id(obj.get("id").toString());
			woem.setId(UUID.randomUUID().toString());
			woem.setEvent(Constant.WO_EVENT_UPDATE);
			woem.setEvent_description("工单升级");
			woem.setRemark("工单升级--"+obj.get("wo_level").toString()+"工单时效--"+obj.get("cal_date").toString());
			woem.setEvent_status(true);
			woem.setSort(
			workOrderManagementMapper.getSort(obj.get("id").toString())+1);
			workOrderManagementMapper.addWorkOrderEvent(woem);
		  workOrderManagementServiceImpl.operate(null, param);
	  }else{
		  WorkOrderEventMonitor woem= new WorkOrderEventMonitor();
			woem.setCreate_by("0");
			woem.setWo_id(obj.get("id").toString());
			woem.setId(UUID.randomUUID().toString());
			woem.setEvent(Constant.WO_EVENT_UPDATE);
			woem.setEvent_description("工单升级");
			woem.setRemark("工单升级--"+obj.get("wo_level").toString()+"工单时效--"+obj.get("cal_date").toString());
			woem.setEvent_status(true);
			woem.setSort(
			workOrderManagementMapper.getSort(obj.get("id").toString())+1);
			workOrderManagementMapper.addWorkOrderEvent(woem);
	  }
	}

	public boolean ticketForEvent(String waybill){
	List<Map<String,Object>>list=	
		workOrderManagementMapper.getTicketForWorkOrder(waybill);
		
	if(list==null||list.size()==0)return false;	
	Map<String,Object> map=list.get(0);
	boolean result=true;
		
	try{
			result=map.get("store_flag").toString().equals("1")&&map.get("warehouse_flag").toString().equals("1")?true:false;	
		}catch(Exception e){
			
			return false;
		};
		
		return result;
	}
	
	

	@Override
	public void RadarGenWk(WaybillWarninginfoDetail detail) throws Exception {
		// TODO Auto-generated method stub
		//查询规则
		Map<String,Object> rule=getRule(detail);
		boolean  outOfWarehouse=ticketForEvent(detail.getWaybill());
		    if(rule==null||!outOfWarehouse){
		    	UpdateWaybilldetail(detail);
		    	/*UpLog up=new UpLog();
		    	up.setWaybill(detail.getWaybill());
		    	up.setCreate_time(new Date());
		    	up.setMsg("未生成工单 原因 没有对应生成规则或仓库店铺不符合要求");
		    	UpLogMapper.add(up);*/
		    	return;
		    }
		    
		//查找该运的此工单类型的工单是否存在    存在 ：工单升级    / 不存在 : 新建工单
		Map param= new HashMap();
		ExpressinfoMasterQueryParam qr=null;
		param.put("express_number",detail.getWaybill());
		param.put("warning_type", detail.getWarningtype_code());
		param.put("id", detail.getWk_id());
		List  list=	workOrderManagementMapper.findNeedLevelUpData_ADV(param); 
		
		
	    WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		try{
		 if(list==null||list.size()==0){
		   //新增工单	 
			 RadarAddWK(detail,rule);
			
		 }else{
		// 工单升级
			 Map <String ,Object> womaster=	(Map<String, Object>) list.get(0);
			    Integer wk_level=Integer.parseInt(womaster.get("sort").toString());
			    Integer wk_level_new=Integer.parseInt(rule.get("sort").toString());
			    if(wk_level_new>wk_level){
		    		//需要升级
		    		womaster.put("update_time", new Date());
		    		womaster.put("wo_level", rule.get("wk_level_code").toString());
		    		womaster.put("standardManhours", rule.get("wk_standard").toString());
		    		womaster.put("wo_level_display", rule.get("wk_level").toString());
		    		Date startTime=new Date();
		    		womaster.put("level_starttime", womaster.get("cal_date").toString());
		    	Date cal_date=DateUtil.MoveMin(DateUtil.StrToTime(womaster.get("cal_date").toString()),Integer.parseInt(rule.get("wk_timeout").toString()));
		    		womaster.put("cal_date", cal_date);
			 updateLevel(womaster);
			 Visual visual=new Visual();
 			 visual.addObserver(Observers.getInstance());
 			 visual.setData(womaster.get("id").toString());		 }
		 }  
		 UpdateWaybilldetail(detail);
			transactionManager.commit(status);
		 }catch(Exception e){
			   transactionManager.rollback(status);
			} 
	}
	
	private void insertDetailBf(WaybillWarninginfoDetail detail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RadarCancelWk() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public void  UpdateWaybilldetail(WaybillWarninginfoDetail detail){
		    Map paramwk=new HashMap();
		    paramwk.put("id", detail.getId());
		    paramwk.put("wk_id",detail.getWk_id());
		    paramwk.put("wk_flag", detail.getWk_flag());
		    waybillWarninginfoDetailMapper.updatWkflag(paramwk);
	}
	
	public Map<String,Object> getRule(WaybillWarninginfoDetail detail){
		GenerationRuleQueryParam qr1=new  GenerationRuleQueryParam();
		qr1.setEw_flag("1");
		qr1.setEw_level(detail.getWarning_level());
		qr1.setEw_type_code(detail.getWarningtype_code());
	    List<Map<String, Object>>  rulelist=generationRuleMapper.findAllData(qr1);
		return rulelist==null||rulelist.size()==0?null:rulelist.get(0);
	}
	
	public  void RadarAddWK(WaybillWarninginfoDetail detail,Map<String,Object> rule){
		ExpressinfoMasterQueryParam
		 qr=new ExpressinfoMasterQueryParam();
		    qr.setWaybill(detail.getWaybill());
		    qr.setFirstResult(0);
		    qr.setMaxResult(10);
		    List<Map<String,Object>>  ExpressinfoMasterList=  expressinfoMasterMapper.findBaseInfo(qr);
		    if(ExpressinfoMasterList==null||ExpressinfoMasterList.size()==0){
		    	UpdateWaybilldetail(detail);return;
		    }
		    Map<String,Object>  em=ExpressinfoMasterList.get(0);
           String  id=UUID.randomUUID().toString();
	    	WorkOrderEventMonitor woem= new WorkOrderEventMonitor();
			woem.setCreate_by("0");
			woem.setWo_id(id);
			woem.setId(UUID.randomUUID().toString());
			woem.setEvent(Constant.WO_EVENT_CREATED);
			woem.setEvent_description("工单创建");
			woem.setSort(1);
			woem.setEvent_status(true);
			woem.setRemark("工单创建");
			workOrderManagementMapper.addWorkOrderEvent(woem);
	    	//新建工单
	    	Date d=new Date();
	    	WorkOrder wo =new WorkOrder();
	    	wo.setWoNo(WorkOrderUtils.generateWorkOrderNo());
	    	wo.setTransportTime(em.get("weight_time").toString());
	    	wo.setId(id);
	    	wo.setAddress(em.get("address").toString());
	    	wo.setCarriers(em.get("express_code").toString());
	    	wo.setCarriersDisplay(em.get("express_name").toString());
	    	wo.setCreateTime(DateUtil.format(d));
	    	wo.setUpdateBy(DateUtil.format(d));
	    	wo.setCreateBy("0");
	    	wo.setUpdateBy("0");
	    	wo.setRecipient(em.get("shiptoname").toString());
	    	wo.setExpressNumber(detail.getWaybill());
	    	wo.setLogisticsStatus(detail.getRoutestatus_code());
	    	wo.setPhone(em.get("phone").toString());
	    	wo.setPlatformNumber(em.get("platform_no").toString());
	    	wo.setStores(em.get("store_code").toString());
	    	wo.setWarehouses(em.get("warehouse_code").toString());
	    	wo.setRelatedNumber("无");
	    	wo.setWoProcessStatus(Constant.WO_PROCESS_STATUS_NEW);
	    	wo.setWoProcessStatusDisplay(WorkOrderUtils.endueDisplay(Constant.WO_PROCESS_STATUS_NEW));
	    	wo.setWoAllocStatus(Constant.WO_ALLOC_STATUS_WAA);//WAA-待自动分配
	    	wo.setWoAllocStatusDisplay(WorkOrderUtils.endueDisplay(Constant.WO_ALLOC_STATUS_WAA));
	    	wo.setWarningType(detail.getWarningtype_code());
	    	wo.setWarningLevel(detail.getWarning_level());
	    	wo.setWoLevel(rule.get("wk_level_code").toString());
	    	wo.setWoType(rule.get("wk_type_code").toString());
	    	wo.setLevel_starttime(DateUtil.format(d));
	    	wo.setStoresDisplay(em.get("store_name").toString());
	    	wo.setWarehousesDisplay(em.get("warehouse_name").toString());
	    	wo.setWoTypeDisplay(rule.get("wk_type_name").toString());
	    	wo.setWoLevelDisplay(rule.get("wk_level").toString());
	    	wo.setCreateByDisplay("system");
	    	wo.setOrderAmount(em.get("order_amount")==null?"0":em.get("order_amount").toString());
	    	wo.setRelatedNumber(em.get("related_number")==null?"":em.get("related_number").toString());
	        //List<Map<String,Object>> orderAcount= 	workOrderManagementMapper.getByExpressNumber(detail.getWaybill());
	    	wo.setWoAllocStatusDisplay(WorkOrderUtils.endueDisplay(Constant.WO_ALLOC_STATUS_WAA));
	    	wo.setStandardManhours(new BigDecimal(rule.get("wk_standard").toString()));
	    	Integer standardtime=Integer.parseInt(rule.get("wk_standard").toString());
	    	Date EstimatedTimeOfCompletion=DateUtil.MoveMin(d, standardtime);
	    	wo.setEstimatedTimeOfCompletion(DateUtil.format(EstimatedTimeOfCompletion));
	    	wo.setCal_date(DateUtil.format(DateUtil.MoveMin(d, Integer.parseInt(rule.get("wk_timeout").toString()))));
	    	wo.setWoSource(Constant.WO_SOURCE_ER);
	    	int add_flag=workOrderManagementMapper.add(wo);
	    	detail.setWk_flag("1");
	    	if(add_flag==1)detail.setWk_id(id);
	    	Visual visual=new Visual();
			visual.addObserver(Observers.getInstance());
			visual.setData(wo.getId());
	}
	
	
	public static void main(String agrs[]){
		List<Map<String,Object>>list=new ArrayList<Map<String,Object>>();
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("store_flag",null);
				map.put("warehouse_flag", null);
				
				System.out.println(map.get("store_flag").toString().equals("1")&&map.get("warehouse_flag").toString().equals("1")?true:false) ;
		
	}
}
