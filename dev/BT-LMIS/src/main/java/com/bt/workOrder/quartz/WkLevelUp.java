package com.bt.workOrder.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.radar.dao.WaybillWarninginfoDetailMapper;
import com.bt.radar.model.WaybillWarninginfoDetail;
import com.bt.workOrder.service.WorkOrderLevelUpService;

public class WkLevelUp {

	WorkOrderLevelUpService sevice = (WorkOrderLevelUpService)SpringUtils.getBean("workOrderLevelUpServiceImpl");
	WaybillWarninginfoDetailMapper   waybillWarninginfoDetailMapper=(WaybillWarninginfoDetailMapper)SpringUtils.getBean("waybillWarninginfoDetailMapper");
	
	public   void startLevelUp() throws Exception{	
	   sevice.LevelUp();
		/*Map param =new HashMap();
		param.put("id", "ac142149-8dc3-41da-8bdd-eb5d794ba206");
		param.put("wk_level", "test");
		sevice.updateLevel(param);*/
	}
	
	
	public   void parseCaldate() throws Exception{	
		   sevice.parseCaldate();
		
		}
	
	

	public   void   startGenWkleve() throws Exception{
		/*WaybillWarninginfoDetail param=new WaybillWarninginfoDetail();
	   //获取未被取消    以及 数据表er_waybill_warninginfo_detail 中 wk_flag=0(未生成工单) 的数据
		param.setDel_flag("0");
		param.setWk_flag("0");
		param.setStop_watch("0");
		List<WaybillWarninginfoDetail> list=
		                       wwdMapper.findRecords(param);
		
		for(WaybillWarninginfoDetail detail:list){
			try {
				sevice.RadarGenWk(detail);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}	*/
		Map  param =new HashMap();
		param.put("del_flag", "0");
		param.put("stop_watch",0);
		param.put("first", 0);
		param.put("last",500);
		List<WaybillWarninginfoDetail> list=null;
		while(true){
		 list=
				 waybillWarninginfoDetailMapper.findRecordsPageData(param);	
	     if(list==null||list.size()==0)return;	 
	     for(WaybillWarninginfoDetail detail:list){
			
					sevice.RadarGenWk(detail); 
	     
		}
		}
	}
}
