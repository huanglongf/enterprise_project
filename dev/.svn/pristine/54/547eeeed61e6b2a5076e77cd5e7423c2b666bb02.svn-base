package com.bt.workOrder.quartz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.bt.common.CommonUtils;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.utils.DateUtil;
import com.bt.workOrder.bean.ReportRegion;
import com.bt.workOrder.bean.WoPersonalReport;
import com.bt.workOrder.dao.ReportRegionMapper;
import com.bt.workOrder.service.WoPersonalReportService;

public class InsertWoPersonalReport {

	private static final Logger logger = Logger.getLogger(InsertWoPersonalReport.class);
	
	WoPersonalReportService woPersonalReportServiceImpl = (WoPersonalReportService) SpringUtils
			.getBean("woPersonalReportServiceImpl");
	
//	ReportRegionService reportRegionServiceImpl = (ReportRegionServiceImpl) SpringUtils
//	                .getBean("reportRegionServiceImpl");
	
	ReportRegionMapper reportRegionServiceImpl = (ReportRegionMapper) SpringUtils
	                .getBean("reportRegionMapper");

	public void insertWPR() throws Exception {
		logger.error("触发了insertWPR方法！！！！！！！！！！！！！！！");
		//当天日期
		String date = DateUtil.format(DateUtil.getYesterDay().getTime(),DateUtil.yyyy_MM_dd);
		List<Map<String,Object>> processPG = woPersonalReportServiceImpl.getProcessPG(date);
		List<Map<String,Object>> recievePG = woPersonalReportServiceImpl.getRecievePG(date);
		processPG.addAll(recievePG);
		Set<Map<String,Object>> pgSet = new HashSet<>(processPG);
		for(Map<String,Object> map:pgSet){
			if(!CommonUtils.checkExistOrNot(map)){
				continue;
			}
			String person = CommonUtils.checkExistOrNot(map.get("pid"))?map.get("pid").toString():"0";
			String group =  CommonUtils.checkExistOrNot(map.get("gid"))?map.get("gid").toString():"0";
			//组人数 
			int groupCount = woPersonalReportServiceImpl.getGroupCount(group);
			WoPersonalReport woPersonalReport = new WoPersonalReport();
			woPersonalReport.setCreateTime(new Date());
			woPersonalReport.setEmpId(Integer.valueOf(person));
			woPersonalReport.setEmpName(CommonUtils.checkExistOrNot(map.get("pname"))?map.get("pname").toString():"");
			woPersonalReport.setGroupId(Integer.valueOf(group));
			woPersonalReport.setGroupName(CommonUtils.checkExistOrNot(map.get("gname"))?map.get("gname").toString():"");
			woPersonalReport.setGroupCount(groupCount);
			woPersonalReport.setReportTime(date);
			//=======================================================================================================
			//个人工单接单量--------(个人获取的工单量+系统自动分配的工单量+被转发指定的工单量)-----------------------------------------------
			List<Map<String,Object>> personalGetWo = woPersonalReportServiceImpl.getWoCount(date,person,group,"OBTAIN",1);
			List<Map<String,Object>> autoAllocWo = woPersonalReportServiceImpl.getWoCount(date,person,group,"ALLOC",2);
			List<Map<String,Object>> forwordedWo = woPersonalReportServiceImpl.getWoCount(date,person,group,"FORWARD",2);
			woPersonalReport.setPersonalGetNum(personalGetWo.size()+autoAllocWo.size()+forwordedWo.size());
			//接单的总工单去重
			autoAllocWo.addAll(forwordedWo);
			Set<Map<String,Object>> woGetSet = new HashSet<>(autoAllocWo);
			//个人工单处理量--------(只有回复)-----------------------------------------------
			List<Map<String,Object>> personalProcessNum = woPersonalReportServiceImpl.getWoCount(date,person,group,"REPLY",1);;
			woPersonalReport.setPersonalProcessNum(personalProcessNum.size());
			//个人多次处理工单量------------------------------------------------------------
			int personalMultipleProcessingNum = woPersonalReportServiceImpl.getPersonalMultipleProcessingNum(date,person,group);
			woPersonalReport.setPersonalMultipleProcessingNum(personalMultipleProcessingNum);
			//个人工单转发量---------------------------------------------------------------
			List<Map<String,Object>> personalForwordNum = woPersonalReportServiceImpl.getWoCount(date,person,group,"FORWARD",1);
			woPersonalReport.setPersonalForwordNum(personalForwordNum.size());
			//个人工单当天未处理量-----------------------------------------------------------
			int personalUnprocessNum = woPersonalReportServiceImpl.getPersonalUnprocessNum(person,group);
			woPersonalReport.setPersonalUnprocessNum(personalUnprocessNum);
			//个人工单完结量-----------------------------------------------------------------
			int personalOverNum = 0;
			for(Map<String,Object> processWoMap :personalProcessNum){
				String woId = processWoMap.get("wo_store_id").toString();
				String createTime = processWoMap.get("create_time").toString();
				Map<String,Object> newLog = woPersonalReportServiceImpl.getNewLog(woId,createTime);
				if(CommonUtils.checkExistOrNot(newLog)&&"OVER".equals(newLog.get("action").toString())){
					personalOverNum++;
				}
			}
			woPersonalReport.setPersonalOverNum(personalOverNum);
			//个人工单响应时间----------------------------------------------------------------
			int totalResponseTime = 0;
			for(Map<String,Object> getWoMap:personalGetWo){
				String woId = getWoMap.get("wo_store_id").toString();
				String createTime = getWoMap.get("create_time").toString();
				Map<String,Object> newLog = woPersonalReportServiceImpl.getNewLog(woId,createTime);
				if(CommonUtils.checkExistOrNot(newLog)){
					String newTime = newLog.get("create_time").toString();
					Date createTimeDate = DateUtil.StrToTime(createTime);
					Date newTimeDate = DateUtil.StrToTime(newTime);
					totalResponseTime = (int) (totalResponseTime+(newTimeDate.getTime()-createTimeDate.getTime()));
				}
			}
			for(Map<String,Object> woGeted:woGetSet){
				String woId = woGeted.get("wo_store_id").toString();
				String createTime = woGeted.get("create_time").toString();
				Map<String,Object> newLog = woPersonalReportServiceImpl.getNewLog(woId,createTime);
				if(CommonUtils.checkExistOrNot(newLog)){
					String newTime = newLog.get("create_time").toString();
					Date createTimeDate = DateUtil.StrToTime(createTime);
					Date newTimeDate = DateUtil.StrToTime(newTime);
					totalResponseTime = (int) (totalResponseTime+(newTimeDate.getTime()-createTimeDate.getTime()));
				}
			}
			int size = (woGetSet.size()+personalGetWo.size());
			if(size!=0){
				int personalResponseTime = totalResponseTime/size/1000;
				woPersonalReport.setPersonalResponseTime(personalResponseTime);
			}else{
				woPersonalReport.setPersonalResponseTime(totalResponseTime);
			}
			//===================================================================================
			//插表
			woPersonalReportServiceImpl.insert(woPersonalReport);
		}
		logger.error("结束了insertWPR方法！！！！！！！！！！！！！！！");
	}
	
	/**
	 * 定时器不能有参数 所以只能另外写一个
	 * @throws Exception
	 */
	public void planInsertWPR() throws Exception {
	    logger.error("触发了planInsertWPR方法！！！！！！！！！！！！！！！");
	    
	    //获取日期区间
	    ReportRegion reRe = reportRegionServiceImpl.selectByPrimaryKey(1);
        Calendar start = Calendar.getInstance();
        //2017 12 1
        start.set(reRe.getFromYear(), reRe.getFromMonth(), reRe.getFromDay());  
        Long startTime = start.getTimeInMillis();  
        Calendar end = Calendar.getInstance();  
        //2018 3 8
        end.set(reRe.getToYear(), reRe.getToMonth(), reRe.getToDay());  
        Long endTime = end.getTimeInMillis();  
        Long oneDay = 1000 * 60 * 60 * 24l;  
        Long time = startTime;  
        while (time <= endTime) {  
            Date d = new Date(time);  
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
            String date = df.format(d);
            System.out.println("date="+date);
            time += oneDay;  
            
            logger.error("删除"+date+"这天的报表，以便重新生成！！！！！！！！！！！！！！！");
            woPersonalReportServiceImpl.deleteByReportTime(date);
            
            List<Map<String,Object>> processPG = woPersonalReportServiceImpl.getProcessPG(date);
            List<Map<String,Object>> recievePG = woPersonalReportServiceImpl.getRecievePG(date);
            processPG.addAll(recievePG);
            Set<Map<String,Object>> pgSet = new HashSet<>(processPG);
            for(Map<String,Object> map:pgSet){
                if(!CommonUtils.checkExistOrNot(map)){
                    continue;
                }
                String person = CommonUtils.checkExistOrNot(map.get("pid"))?map.get("pid").toString():"0";
                String group =  CommonUtils.checkExistOrNot(map.get("gid"))?map.get("gid").toString():"0";
                //组人数 
                int groupCount = woPersonalReportServiceImpl.getGroupCount(group);
                WoPersonalReport woPersonalReport = new WoPersonalReport();
                woPersonalReport.setCreateTime(new Date());
                woPersonalReport.setEmpId(Integer.valueOf(person));
                woPersonalReport.setEmpName(CommonUtils.checkExistOrNot(map.get("pname"))?map.get("pname").toString():"");
                woPersonalReport.setGroupId(Integer.valueOf(group));
                woPersonalReport.setGroupName(CommonUtils.checkExistOrNot(map.get("gname"))?map.get("gname").toString():"");
                woPersonalReport.setGroupCount(groupCount);
                woPersonalReport.setReportTime(date);
                //=======================================================================================================
                //个人工单接单量--------(个人获取的工单量+系统自动分配的工单量+被转发指定的工单量)-----------------------------------------------
                List<Map<String,Object>> personalGetWo = woPersonalReportServiceImpl.getWoCount(date,person,group,"OBTAIN",1);
                List<Map<String,Object>> autoAllocWo = woPersonalReportServiceImpl.getWoCount(date,person,group,"ALLOC",2);
                List<Map<String,Object>> forwordedWo = woPersonalReportServiceImpl.getWoCount(date,person,group,"FORWARD",2);
                woPersonalReport.setPersonalGetNum(personalGetWo.size()+autoAllocWo.size()+forwordedWo.size());
                //接单的总工单去重
                autoAllocWo.addAll(forwordedWo);
                Set<Map<String,Object>> woGetSet = new HashSet<>(autoAllocWo);
                //个人工单处理量--------(只有回复)-----------------------------------------------
                List<Map<String,Object>> personalProcessNum = woPersonalReportServiceImpl.getWoCount(date,person,group,"REPLY",1);;
                woPersonalReport.setPersonalProcessNum(personalProcessNum.size());
                //个人多次处理工单量------------------------------------------------------------
                int personalMultipleProcessingNum = woPersonalReportServiceImpl.getPersonalMultipleProcessingNum(date,person,group);
                woPersonalReport.setPersonalMultipleProcessingNum(personalMultipleProcessingNum);
                //个人工单转发量---------------------------------------------------------------
                List<Map<String,Object>> personalForwordNum = woPersonalReportServiceImpl.getWoCount(date,person,group,"FORWARD",1);
                woPersonalReport.setPersonalForwordNum(personalForwordNum.size());
                //个人工单当天未处理量-----------------------------------------------------------
                int personalUnprocessNum = woPersonalReportServiceImpl.getPersonalUnprocessNum(person,group);
                woPersonalReport.setPersonalUnprocessNum(personalUnprocessNum);
                //个人工单完结量-----------------------------------------------------------------
                int personalOverNum = 0;
                for(Map<String,Object> processWoMap :personalProcessNum){
                    String woId = processWoMap.get("wo_store_id").toString();
                    String createTime = processWoMap.get("create_time").toString();
                    Map<String,Object> newLog = woPersonalReportServiceImpl.getNewLog(woId,createTime);
                    if(CommonUtils.checkExistOrNot(newLog)&&"OVER".equals(newLog.get("action").toString())){
                        personalOverNum++;
                    }
                }
                woPersonalReport.setPersonalOverNum(personalOverNum);
                //个人工单响应时间----------------------------------------------------------------
                int totalResponseTime = 0;
                for(Map<String,Object> getWoMap:personalGetWo){
                    String woId = getWoMap.get("wo_store_id").toString();
                    String createTime = getWoMap.get("create_time").toString();
                    Map<String,Object> newLog = woPersonalReportServiceImpl.getNewLog(woId,createTime);
                    if(CommonUtils.checkExistOrNot(newLog)){
                        String newTime = newLog.get("create_time").toString();
                        Date createTimeDate = DateUtil.StrToTime(createTime);
                        Date newTimeDate = DateUtil.StrToTime(newTime);
                        totalResponseTime = (int) (totalResponseTime+(newTimeDate.getTime()-createTimeDate.getTime()));
                    }
                }
                for(Map<String,Object> woGeted:woGetSet){
                    String woId = woGeted.get("wo_store_id").toString();
                    String createTime = woGeted.get("create_time").toString();
                    Map<String,Object> newLog = woPersonalReportServiceImpl.getNewLog(woId,createTime);
                    if(CommonUtils.checkExistOrNot(newLog)){
                        String newTime = newLog.get("create_time").toString();
                        Date createTimeDate = DateUtil.StrToTime(createTime);
                        Date newTimeDate = DateUtil.StrToTime(newTime);
                        totalResponseTime = (int) (totalResponseTime+(newTimeDate.getTime()-createTimeDate.getTime()));
                    }
                }
                int size = (woGetSet.size()+personalGetWo.size());
                if(size!=0){
                    int personalResponseTime = totalResponseTime/size/1000;
                    woPersonalReport.setPersonalResponseTime(personalResponseTime);
                }else{
                    woPersonalReport.setPersonalResponseTime(totalResponseTime);
                }
                //===================================================================================
                //插表
                woPersonalReportServiceImpl.insert(woPersonalReport);
            }
        }
	    logger.error("结束了planInsertWPR方法！！！！！！！！！！！！！！！");
	}
	
	/*public static void main(String[] args) {
		0	系统分配	0	系统分配
		530	JMSH2000	146	销售运营测试
		531	JMSH2001	146	销售运营测试
		
		
		530	JMSH2000	146	销售运营测试
		531	JMSH2001	146	销售运营测试
		
		
		List<Map<String,Object>> processPG = woPersonalReportServiceImpl.getProcessPG(date);
		List<Map<String,Object>> recievePG = woPersonalReportServiceImpl.getRecievePG(date);
		processPG.addAll(recievePG);
		Set<Map<String,Object>> pgSet = new HashSet<>(processPG);
		
		Map map1 = new HashMap<>();
		Map map2 = new HashMap<>();
		Map map3 = new HashMap<>();
		Map map4 = new HashMap<>();
		
		map1.put("p", 1);
		map2.put("p", 2);
		map3.put("p", 1);
		map4.put("p", 4);
		
		map1.put("g", 11);
		map2.put("g", 12);
		map3.put("g", 11);
		map4.put("g", 14);
		
		List<Map> list1 = new ArrayList<>();
		list1.add(map1);
		list1.add(map2);
		List<Map> list2 = new ArrayList<>();
		list2.add(map3);
		list2.add(map4);
		
		list1.addAll(list2);
		
		Set set = new HashSet<>(list1);
		
		List list = new ArrayList<>();
		System.out.println(list.size());
		list = null;
		System.out.println(list.size());
	}*/

}
