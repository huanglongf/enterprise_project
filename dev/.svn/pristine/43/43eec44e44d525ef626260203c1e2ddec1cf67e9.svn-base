package com.bt.workOrder.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.common.CommonUtils;
import com.bt.common.controller.param.Parameter;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.DateUtil;
import com.bt.workOrder.bean.WoPersonalReport;
import com.bt.workOrder.dao.WoPersonalReportMapper;
import com.bt.workOrder.service.WoPersonalReportService;

@Service
public class WoPersonalReportServiceImpl implements WoPersonalReportService{
    
	@Autowired
	private WoPersonalReportMapper woPersonalReportMapper;
	@Override
    public int insert(WoPersonalReport record){
    	return woPersonalReportMapper.insert(record);
    }
    @Override
    public WoPersonalReport selectByPrimaryKey(Integer id){
    	return woPersonalReportMapper.selectByPrimaryKey(id);
    }
    @Override
    public List<WoPersonalReport> selectAll(){
    	return woPersonalReportMapper.selectAll();
    }

	@Override
	public List<Map<String, Object>> getProcessPG(String date) {
		return woPersonalReportMapper.getProcessPG(date);
	}

	@Override
	public List<Map<String, Object>> getRecievePG(String date) {
		return woPersonalReportMapper.getRecievePG(date);
	}
	
	@Override
	public int getGroupCount(String group) {
		return woPersonalReportMapper.getGroupCount(group);
	}
	
	@Override
	public List<Map<String,Object>> getWoCount(String date, String person, String group, String action,int type) {
		return woPersonalReportMapper.getWoCount(date,person,group,action,type);
	}

	@Override
	public int getPersonalMultipleProcessingNum(String date, String person, String group) {
		return woPersonalReportMapper.getPersonalMultipleProcessingNum(date,person,group);
	}

	@Override
	public int getPersonalUnprocessNum(String person, String group) {
		return woPersonalReportMapper.getPersonalUnprocessNum(person,group);
	}

	@Override
	public Map<String, Object> getNewLog(String woId, String createTime) {
		return woPersonalReportMapper.getNewLog(woId,createTime);
	}
	
	@Override
	public QueryResult<Map<String, Object>> search(Parameter parameter) {
		String[] temp = null;
		int timeLine = 1;
		if(CommonUtils.checkExistOrNot(parameter.getParam().get("create_time"))) {
			temp = parameter.getParam().get("create_time").toString().split(" - ");
			parameter.getParam().put("create_time_start", temp[0]);
			parameter.getParam().put("create_time_end", temp[1]);
			timeLine = DateUtil.getDaySub(temp[0],temp[1])+1;
		}
		parameter.getParam().put("timeLine", timeLine);
		return new QueryResult<Map<String,Object>>(woPersonalReportMapper.search(parameter), woPersonalReportMapper.countSearch(parameter));
	}

	@Override
	public List<Map<String, Object>> exportReport(Parameter parameter) {
		String[] temp = null;
		int timeLine = 1;
		if(CommonUtils.checkExistOrNot(parameter.getParam().get("create_time"))) {
			temp = parameter.getParam().get("create_time").toString().split(" - ");
			parameter.getParam().put("create_time_start", temp[0]);
			parameter.getParam().put("create_time_end", temp[1]);
			timeLine = DateUtil.getDaySub(temp[0],temp[1])+1;
		}
		parameter.getParam().put("timeLine", timeLine);
		return woPersonalReportMapper.exportReport(parameter);
	}

	@Override
    public void deleteByReportTime(String date){
	    woPersonalReportMapper.deleteByReportTime(date);
    }
}