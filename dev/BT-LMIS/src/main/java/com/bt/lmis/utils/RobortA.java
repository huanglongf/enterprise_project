package com.bt.lmis.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.bt.OSinfo;
import com.bt.lmis.base.TABLE_ROLE;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.dao.DifferenceMatchingMapper;
import com.bt.lmis.model.DifferenceObj;
import com.bt.lmis.model.SimpleMasterSlaveReport;
import com.bt.lmis.model.SimpleTable;
import com.bt.utils.BigExcelExport;
import com.bt.utils.CommonUtils;
import com.bt.utils.Constant;
import com.bt.utils.FileUtil;
import com.bt.utils.ReportUtils;

public class RobortA extends Thread{
	//结算机器人
	private DifferenceObj obj;
	private  int no;
	private  int flag_no; 
	public int getFlag_no() {
		return flag_no;
	}
	public void setFlag_no(int flag_no) {
		this.flag_no = flag_no;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public DifferenceObj getObj() {
		return obj;
	}
	public RobortA(DifferenceObj obj) {
		super();
		this.obj = obj;
	}
	public void setObj(DifferenceObj obj) {
		this.obj = obj;
	}
	public void run(){
		DifferenceMatchingMapper<T> differenceMatchingMapper= (DifferenceMatchingMapper<T>)SpringUtils.getBean("differenceMatchingMapper");
		Map param=new HashMap();
		param.put("bat_id",obj.getBat_id());
	    switch (flag_no){
	    case 0:prcess0(param,differenceMatchingMapper);break;
	    case 1:prcess1(param,differenceMatchingMapper);break;
	    case 2:prcess2(param,differenceMatchingMapper);break;
	    case 3:prcess3(param,differenceMatchingMapper);break;
	    case 4:prcess4(param,differenceMatchingMapper);break;
	    }

		System.out.println("A done");
	}
	private void prcess4(Map param, DifferenceMatchingMapper<T> differenceMatchingMapper) {
		// TODO Auto-generated method stub
		differenceMatchingMapper.deleteResult4(param.get("bat_id").toString());
		for(int i=0;i<4;i++){
	    	 param.put("Min",no+i*50000);
	 	     param.put("Max", no+i*50000+50000);
	 	     param.put("table_name", "df_sf_result5");
	 	    differenceMatchingMapper.matchingSFDifferenceSettle_insert2(param);
	    }
		
	}
	private void prcess3(Map param, DifferenceMatchingMapper<T> differenceMatchingMapper) {
		// TODO Auto-generated method stub
		differenceMatchingMapper.deleteResult3(param.get("bat_id").toString());
		for(int i=0;i<4;i++){
	    	 param.put("Min",no+i*50000);
	 	     param.put("Max", no+i*50000+50000);
	 	    param.put("table_name", "df_sf_result4");
	 	    differenceMatchingMapper.matchingSFDifferenceSettle_insert2(param);
	    }
	}
	private void prcess2(Map param, DifferenceMatchingMapper<T> differenceMatchingMapper) {
		// TODO Auto-generated method stub
		differenceMatchingMapper.deleteResult2(param.get("bat_id").toString());
		for(int i=0;i<4;i++){
	    	 param.put("Min",no+i*50000);
	 	     param.put("Max", no+i*50000+50000);
	 	     param.put("table_name", "df_sf_result3");
	 	    differenceMatchingMapper.matchingSFDifferenceSettle_insert2(param);
	    }
	}
	private void prcess0(Map param, DifferenceMatchingMapper<T> differenceMatchingMapper) {
		// TODO Auto-generated method stub
		differenceMatchingMapper.deleteResult00(param.get("bat_id").toString());
		for(int i=0;i<4;i++){
	    	 param.put("Min",no+i*50000);
	 	     param.put("Max", no+i*50000+50000);
	 	     param.put("table_name", "df_sf_result1");
	 	    differenceMatchingMapper.matchingSFDifferenceSettle_insert2(param);
	    }
	}
	private void prcess1(Map param, DifferenceMatchingMapper<T> differenceMatchingMapper) {
		// TODO Auto-generated method stub
		differenceMatchingMapper.deleteResult1(param.get("bat_id").toString());
		for(int i=0;i<4;i++){
	    	 param.put("Min",no+i*50000);
	 	     param.put("Max", no+i*50000+50000);
	 	     param.put("table_name", "df_sf_result2");
	 	    differenceMatchingMapper.matchingSFDifferenceSettle_insert2(param);
	    }
	}
	public RobortA(DifferenceObj obj, int no,int flag_no) {
		super();
		this.obj = obj;
		this.no = no;
		this.flag_no = flag_no;
	}
}
