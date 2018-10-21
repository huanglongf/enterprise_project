package com.bt.lmis.service;

import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.OriginDesitinationParamlist;


public interface OriginDesitinationParamlistService<T> extends BaseService<T> {

	
	public Map<String,Object> selectCounts(Map<String, Object> param);
	
	public Map<String,Object>  getInfoByNm(Map<String, Object> param);
	
	public  Map<String, Object> findTableIdByTBNm(Map tbn);
	
	public  String  saveExcels(Workbook workbook,boolean isCheck,String userName,int sheetCount)throws Exception;
	
	public void saveAndprocessData(String userId) throws Exception;
	
	public void removePamaterList(String id) throws Exception;
	
	public String insertCheckExelSame(String log)throws Exception;

	//批量删除地址参数信息（主次表） 
	public void removePamaterListbatch(String parameter) throws Exception;
	
}
