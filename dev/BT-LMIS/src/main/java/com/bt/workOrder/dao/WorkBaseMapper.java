package com.bt.workOrder.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bt.common.controller.param.Parameter;
import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.page.QueryParameter;
/**
 * 
* @ClassName: WorkBaseMapper 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author likun
* @date 2017年1月4日 下午2:06:29 
* 
* @param <T>
 */
public interface WorkBaseMapper<T> extends BaseMapper<T>{
	public List<T> getOrderBaseInfo(QueryParameter queryParameter);
	public Integer getOrderBaseInfoCount(QueryParameter queryParameter);
	public void addType(Map<String,Object> param);
	public void updateType(Map<String,Object> param);
	public ArrayList<?> getTypeInfo(Map<String,Object> param);
	public ArrayList<?> getReasonInfo(Map<String,Object> param);
	public ArrayList<?> getErrorInfo(Map<String,Object> param);
	public void addwkType(Map<String,Object> param);
	public void addwkReason(Map<String,Object> param);
	public void UpdateStatus(Map<String,Object> param);
	public Map<String,String> getMainInfo(Map<String,Object> param);
	public void delType(Map<String,Object>param);
	public void delReason(Map<String,Object>param);
	public void delError(Map<String,Object>param);
	public void up_type_kid(Map<String,Object>param);
	public void up_reason_kid(Map<String,Object>param);
	public Integer checkCount(Map<String,Object>param);
	public Integer getId(Map<String,Object>param);
	public ArrayList<?>getAllType();
	public ArrayList<?>getAllLevel(Map<String,Object>param);
	public ArrayList<Map<String,Object>>getTimePram(Map<String,Object>param);
	Integer checkStatus(Integer[]ids) throws Exception;
	public void insert_column(List<Map<String, String>> list);
	public void up_error_kid(Map<String,Object>param);
	public void addwkError(Map<String,Object> param);
	/**
	 * @param parameter
	 * @return
	 */
	public List<Map<String, Object>> getOrderBaseInfo2(Map<String,Object> parameter);
	public Integer getOrderBaseInfoCount2(Map<String,Object> queryParameter);
}
