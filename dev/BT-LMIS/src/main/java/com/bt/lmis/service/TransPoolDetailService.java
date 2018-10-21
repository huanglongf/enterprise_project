package com.bt.lmis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;

/** 
* @ClassName: EmployeeService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Yuriy.Jiang
* @date 2016年5月23日 上午10:51:15 
* 
* @param <T> 
*/
public interface TransPoolDetailService<T> extends BaseService<T>{
	public QueryResult<T> findAllData(QueryParameter qr);
	public HashMap getObject(HashMap param);
	public QueryResult<T> findAllDataByGt(QueryParameter qr);
	public List<T>getExclData(Map<String, Object> param);
	public List<T>getDetailData(Map<String, Object> param);
	
	
	public List<T> getExclInvitation(Map<String, Object> param);
	public List<T> getExeclOperator(Map<String, Object> param);
	public List<T> getExeclOperatorTwo(Map<String, Object> param);
	
	public List<T> getExeclOrder(Map<String, Object> param);
	public List<T> getExeclOrderDetail(Map<String, Object> param);
	public List<T> getExeclStorage(Map<String, Object> param);
	
	public List<T> getDetailUse(Map<String, Object> param);
	
}

