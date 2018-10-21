package com.bt.lmis.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.dao.DifferenceMatchingMapper;
import com.bt.lmis.model.DifferenceObj;
import com.bt.utils.BigExcelExport;

public class RobortD extends Thread{

	private DifferenceObj obj;
	
	public DifferenceObj getObj() {
		return obj;
	}

	public void setObj(DifferenceObj obj) {
		this.obj = obj;
	}

	public RobortD(DifferenceObj obj) {
		super();
		this.obj = obj;
	}

	public void run(){
		Map<String,String> param=new HashMap<String,String>();
		param.put("bat_id", obj.getBat_id());
		param.put("table_name", "df_sf_result1");
		DifferenceMatchingMapper<T> differenceMatchingMapper= (DifferenceMatchingMapper<T>)SpringUtils.getBean("differenceMatchingMapper");
		List<Map<String, Object>> tableContent= (List<Map<String, Object>>)differenceMatchingMapper.matchingSF(param);
		List<Map<String, Object>> tableContent0= (List<Map<String, Object>>)differenceMatchingMapper.matchingSFun(param);

		LinkedHashMap<String, String> tableHeader=this.obj.getTableHeader();
		try {
			if(tableContent0.size()!=0){
				File f=BigExcelExport.excelDownLoadDatab_Z(tableContent0, tableHeader, obj.getFileName(), "无差异"+"的.xlsx");	
			}
			if(tableContent.size()!=0){
				File f1=BigExcelExport.excelDownLoadDatab_Z(tableContent, tableHeader, obj.getFileName(), "有差异"+"的.xlsx");	
			}	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
