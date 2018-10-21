package com.bt.lmis.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.excel.XLSXCovertCSVReader;
import com.bt.lmis.controller.form.ExpressbillBatchmasterQueryParam;
import com.bt.lmis.dao.ExpressbillBatchmasterMapper;
import com.bt.lmis.model.ExpressbillBatchmaster;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ExpressbillBatchmasterService;
import com.bt.lmis.thread.ExpressBillInput;
import com.bt.lmis.thread.ExpressBillTransfer;
import com.bt.utils.ThreadManageUtil;

@Service
public class ExpressbillBatchmasterServiceImpl<T> extends ServiceSupport<T> implements ExpressbillBatchmasterService<T> {

	@Autowired
    private ExpressbillBatchmasterMapper<T> mapper;

	public ExpressbillBatchmasterMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public void deleteByMaster_id(String id) {
		// TODO Auto-generated method stub
		mapper.deleteByMaster_id(id);
	}

	@Override
	public QueryResult<ExpressbillBatchmaster> selectExpressBillBatch(ExpressbillBatchmasterQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<ExpressbillBatchmaster> qr=new QueryResult<ExpressbillBatchmaster>();
		qr.setResultlist(mapper.selectExpressBillBatch(queryParam));
		qr.setTotalrecord(mapper.Count(queryParam));
		return qr;
	}

	@Override
	public void insertDetail() {
		// TODO Auto-generated method stub
		//step 1 查找需要插入的文件
		ExpressbillBatchmasterQueryParam queryParam=new ExpressbillBatchmasterQueryParam();
		queryParam.setStatus("0");//初始值
		queryParam.setFirstResult(0);
		queryParam.setMaxResult(1);
		List<ExpressbillBatchmaster> list=null;
		while(true){
		list=mapper.selectExpressBillBatch(queryParam);
		if(list==null||list.size()==0)return;
		ExpressbillBatchmaster eb=list.get(0);
		eb.setStatus("8");
		mapper.update(eb);
		  try{
			  //step 2 开始导入
			  insertDetailTemp(list.get(0));
		  }catch(Exception e){
			  list.get(0).setStatus("3");//error;
			  mapper.update(list.get(0));
			  e.printStackTrace();
			  
		  }
		}

	}
		
	public void insertDetailTemp(ExpressbillBatchmaster batchmaster){
		String filePath=batchmaster.getLocal_address();
		File   f =new File(filePath);
		try{
		List<String[]> list= XLSXCovertCSVReader.readerExcel(filePath, null, 100);  
		list.remove(list.get(0));
		int  flag_no=4000;
		int  size_no=list.size();
	    int  for_no=size_no%flag_no==0?size_no/flag_no:size_no/flag_no+1;
	    int size=0;
	    for(int i=0;i<for_no;i++){
		  List<String []> sflist=null;
		  sflist =new ArrayList<String []>();
		  if(i==for_no-1){
			  for(int j=i*flag_no;j<list.size();j++){
				  list.get(j)[17]=batchmaster.getBatch_id();
				  list.get(j)[18]=batchmaster.getMaster_id();
				  sflist.add(list.get(j));
				  size= size + 1;
			  } 
		  }else{
			  for(int j=i*flag_no;j<i*flag_no+flag_no;j++){
				  list.get(j)[17]=batchmaster.getBatch_id();
				  list.get(j)[18]=batchmaster.getMaster_id();
				  sflist.add(list.get(j));
				  size= size + 1;
			  } 
		  }
		  mapper.insertDetailTemp(sflist);
	  }	
	    //更批次表状态
	    batchmaster.setStatus("1");//上传成功
	    batchmaster.setTotal_num(size);
	    mapper.update(batchmaster);
		}catch(IOException io){
			io.printStackTrace();
			return;}
		 catch(OpenXML4JException ox){
			ox.printStackTrace(); 
			 return;}
		 catch(ParserConfigurationException pf){
			 pf.printStackTrace();
			 return;}
		 catch(SAXException se){
			 se.printStackTrace();
			 return;}finally{
				 f.delete();
				 }
	    
	}

	@Override
	public void transfer(ExpressbillBatchmasterQueryParam queryParam) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkTemp(ExpressbillBatchmasterQueryParam qureyParam) {
		// TODO Auto-generated method stub
		List<ExpressbillBatchmaster> list=
				mapper.selectExpressBillBatch(qureyParam);
		list.get(0).setStatus("8");//处理中
		mapper.update(list.get(0));
		try{
			qureyParam.setBatch_id(list.get(0).getBatch_id());
			mapper.checkTemp(qureyParam);
			mapper.insertDetail(qureyParam);
			int success_num=mapper.selectSuccess(qureyParam);
			int fail_num=mapper.selectFail(qureyParam);
			list.get(0).setSuccess_num(success_num);
			list.get(0).setFail_num(fail_num);
			list.get(0).setStatus("3");
			mapper.update(list.get(0));
		}catch(Exception e){
			list.get(0).setStatus("4");//转换错误
			mapper.update(list.get(0));
			e.printStackTrace();
		}
		
	}

	@Override
	public JSONObject checkFlow(ExpressbillBatchmasterQueryParam queryParam, String action) {
		// TODO Auto-generated method stub
		JSONObject obj =new JSONObject();
		List<ExpressbillBatchmaster> list=
				mapper.selectExpressBillBatch(queryParam);
    try{	
		if("transfer".equals(action)){
			 if(!"1".equals(list.get(0).getStatus())&&!"4".equals(list.get(0).getStatus())){
				 obj.put("code", "0");
				 return obj;
			 }
		}
		else if("diff".equals(action)){
			if(!"3".equals(list.get(0).getStatus())&&!"7".equals(list.get(0).getStatus())){
				 obj.put("code", 0);
				 return obj;
			 }
		}
    }catch(Exception e){
    	obj.put("code", "0");
    	e.printStackTrace();
    	return  obj;
    }	
		obj.put("code", 1);
		return obj;
	}	
		
		
	
}
	