package com.bt.workOrder.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;
import com.bt.workOrder.dao.WorkBaseMapper;
import com.bt.workOrder.service.WorkBaseInfoService;


/**
 * 
* @ClassName: WorkBaseInfoServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author likun
* @date 2017年1月18日 上午11:23:29 
* 
* @param <T>
 */
@Service
public class WorkBaseInfoServiceImpl<T> extends ServiceSupport<T> implements WorkBaseInfoService<T> {
	@Autowired
    private WorkBaseMapper<T> mapper;
	public WorkBaseMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return null;
	}

	@Override
	public QueryResult<T> findAllData(QueryParameter queryParameter) {
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.getOrderBaseInfo(queryParameter));
		queryResult.setTotalrecord(mapper.getOrderBaseInfoCount(queryParameter));
		return queryResult;
	}
	@Override
	public QueryResult<Map<String, Object>> findAllData2(Map<String, Object> parameter) {
		QueryResult<Map<String,Object>> qr= new QueryResult<Map<String,Object>>();
		qr.setResultlist(mapper.getOrderBaseInfo2(parameter));
		qr.setTotalrecord(mapper.getOrderBaseInfoCount2(parameter));
		return qr;
	}
	@Override
	public Map<String, Object> addType(Map<String, Object> param) {
		// TODO Auto-generated method stub
		int zerIndex=10000000;
		String codeStr="";
		Map<String,String>args1=new HashMap<String,String>();
//		Map<String,String>args2=new HashMap<String,String>();
		Map<String,String>args3=new HashMap<String,String>();
		Map<String,String>args4=new HashMap<String,String>();
		Map<String,String>args5=new HashMap<String,String>();
		Map<String,String>args6=new HashMap<String,String>();
		Map<String,String>args7=new HashMap<String,String>();
		Map<String,String>args8=new HashMap<String,String>();
		
		List<Map<String,String>>list=new ArrayList<Map<String,String>>();
		int count=mapper.checkCount(param);

		if(count>=1){
			throw new RuntimeException("工单类型重复");
		}
		int cs=mapper.getId(new HashMap<String,Object>());
		codeStr=String.valueOf(codeStr+cs+1);
		param.put("code","WTC".concat(codeStr));
		mapper.addType(param);
		args1.put("current",(String)param.get("code"));
		args1.put("column_code","orderNum");
		args1.put("column_name","退货/转寄运单号");
		args1.put("control_type","INPUT");	
		
//		args2.put("current",(String)param.get("code"));
//		args2.put("column_code","sendTime");
//		args2.put("column_name","下次派送时间");
//		args2.put("control_type","INPUT");	
		
		args3.put("current",(String)param.get("code"));
		args3.put("column_code","resultInfo");
		args3.put("column_name","跟进结果");
		args3.put("control_type","SELECT");	
		
		args4.put("current",(String)param.get("code"));
		args4.put("column_code","fileName");
		args4.put("column_name","附件列表");
		args4.put("control_type","INPUT");
		
		args5.put("current",(String)param.get("code"));
		args5.put("column_code","remark");
		args5.put("column_name","备注");
		args5.put("control_type","INPUT");
		
		args6.put("current",(String)param.get("code"));
		args6.put("column_code","result");
		args6.put("column_name","判定结果");
		args6.put("control_type","INPUT");
		
		args7.put("current",(String)param.get("code"));
		args7.put("column_code","dutyBelong");
		args7.put("column_name","责任归属");
		args7.put("control_type","INPUT");
		
		args8.put("current",(String)param.get("code"));
		args8.put("column_code","resultPart");
		args8.put("column_name","结果细分");
		args8.put("control_type","INPUT");
		
		
		list.add(args1);
//		list.add(args2);
		list.add(args3);
		list.add(args4);
		list.add(args5);
		list.add(args6);
		list.add(args7);
		list.add(args8);
		
		mapper.insert_column(list);
		return param;
	}

	@Override
	public ArrayList<?> getTypeTab(Map<String, Object> param) {
		// TODO Auto-generated method stub
		ArrayList<?> list=mapper.getTypeInfo(param);
		if(list==null){
			list=new ArrayList();
		}
		return list;
	}

	@Override
	public ArrayList<?> getReasonTab(Map<String, Object> param) {
		// TODO Auto-generated method stub
		ArrayList<?> list=mapper.getReasonInfo(param);
		if(list==null){
			list=new ArrayList();
		}
		return list;
		
		
	}

	@Override
	public void add_type_kid(Map<String, Object> param) {
		// TODO Auto-generated method stub
		//工时和级别必须依次递增
		checkLevel(param,"add");
		mapper.addwkType(param);
	}

	
	
	public  void checkLevel(Map<String,Object>param,String type){
		ArrayList<Map<String,Object>>resultList=mapper.getTimePram(param);
		Integer standard=Integer.valueOf((String)param.get("standard_time_form"));
		Integer plan=Integer.valueOf((String)param.get("plan_time_form"));
		Integer timeout=Integer.valueOf((String)param.get("timeout_time_form"));
		for(int i=0;i<resultList.size();i++){
			Map<String,Object> result=resultList.get(i);
			 if(Integer.valueOf((String)param.get("sort"))>(Integer)result.get("sort")){
				//标准工时:查询上一个级别的工时，工时和级别必须依次递增
                 if(standard<=(Integer)result.get("wk_standard")){
                	 throw new RuntimeException("标准工时必须依次递增");
                 }
                 if(plan<=(Integer)result.get("wk_plan")){
                	 throw new RuntimeException("计划完成工时必须依次递增");
                 }
                 if(timeout<=(Integer)result.get("wk_timeout")){
                	 throw new RuntimeException("超时工时必须依次递增");
                 }
				//计划工时:
				//超时工时:
			 }
			 else if(Integer.valueOf((String)param.get("sort"))<(Integer)(result.get("sort"))){
                 if(standard>=(Integer)result.get("wk_standard")){
                	 throw new RuntimeException("标准工时必须依次递增");
                 }
                 if(plan>=(Integer)(result.get("wk_plan"))){
                	 throw new RuntimeException("计划完成工时必须依次递增");
                 }
                 if(timeout>=(Integer)(result.get("wk_timeout"))){
                	 throw new RuntimeException("超时工时必须依次递增");
                 }
			 }
			 else if(Integer.valueOf((String)param.get("sort"))==(Integer)result.get("sort") && "add".equals(type)){
				 throw new RuntimeException("该工单的级别类型已经存在，无法重复添加");
			 }
		}
		
	}
	@Override
	public void add_reason_kid(Map<String, Object> param) {
		// TODO Auto-generated method stub
       mapper.addwkReason(param);
	}

	@Override
	public void batchUpdate(Map<String,Object> param) {
		// TODO Auto-generated method stub
		mapper.UpdateStatus(param);
	}

	@Override
	public Map<String, String> getMainInfo(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.getMainInfo(param);
	}

	@Override
	public void updateType(Map<String, Object> param) {
		// TODO Auto-generated method stub
		mapper.updateType(param);
	}

	@Override
	public void delType(Map<String, Object> param) {
		// TODO Auto-generated method stub
	  mapper.delType(param);
	}


	@Override
	public void delReason(Map<String, Object> param) {
		// TODO Auto-generated method stub
		mapper.delReason(param);
	}

	@Override
	public void up_type_kid(Map<String, Object> param) {
		// TODO Auto-generated method stub
		checkLevel(param,"");
		mapper.up_type_kid(param);
	}

	@Override
	public void up_reason_kid(Map<String, Object> param) {
		// TODO Auto-generated method stub
		mapper.up_reason_kid(param);
	}

	@Override
	public ArrayList<?> getAllType() {
		// TODO Auto-generated method stub
		return mapper.getAllType();
	}

	@Override
	public ArrayList<?> getAllLevel(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.getAllLevel(param);
	}

	@Override
	public Integer checkStatus(Integer[] ids) throws Exception {
		// TODO Auto-generated method stub
		return mapper.checkStatus(ids);
	}

	@Override
	public ArrayList<?> getErrorTab(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.getErrorInfo(param);
	}

	@Override
	public void add_error_kid(Map<String, Object> param) {
		// TODO Auto-generated method stub
		 mapper.addwkError(param);
	}

	@Override
	public void up_error_kid(Map<String, Object> param) {
		// TODO Auto-generated method stub
		mapper.up_error_kid(param);
	}

	@Override
	public void delError(Map<String, Object> param) {
		// TODO Auto-generated method stub
		mapper.delError(param);
	}


}
