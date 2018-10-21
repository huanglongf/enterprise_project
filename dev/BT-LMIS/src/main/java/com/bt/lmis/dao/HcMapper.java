package com.bt.lmis.dao;

import java.util.ArrayList;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;

/**
 * 
* @ClassName: HcMapper 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author likun
* @date 2016年7月4日 上午11:18:42 
* 
* @param <T>
 */
public interface HcMapper<T> extends BaseMapper<T> {
	public void saveData(Map<String, Object> param) throws RuntimeException;
	
	public Integer checkData(Map<String, Object> param) throws RuntimeException;
	
	public void updateData(Map<String, Object> param) throws RuntimeException;
	
	public ArrayList<?> getTable(Map<String, Object> param) throws RuntimeException;
	
	public void delTable(Map<String, Object> param)throws RuntimeException;
}
