package com.bt.lmis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.Store;

public interface HcService<T> extends BaseService<T> {
	public void saveData(Map<String, Object> param) throws RuntimeException;
	
	public ArrayList<?>getTable(Map<String, Object> param) throws RuntimeException;
	
	public void delTable(Map<String, Object> param)throws RuntimeException;
}
