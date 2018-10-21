package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.controller.form.AreaQueryParam;
import com.bt.lmis.model.Area;

public interface AreaService<T> extends BaseService<T> {
	
	public  List<Map<String, Object>>  findChildren(Map<String,String> param);
	
	public void deleteTree(int id) throws Exception;
	
	public  void updateTree(AreaQueryParam area) throws Exception;
	
	public  void addTree(Area param) throws Exception;
	
	public Object selectOne(int id) throws Exception;

}
