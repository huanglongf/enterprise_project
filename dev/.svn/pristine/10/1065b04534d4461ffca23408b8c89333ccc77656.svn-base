package com.bt.lmis.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.page.QueryParameter;

/** 
* @ClassName: EmployeeMapper 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Yuriy.Jiang
* @date 2016年5月23日 上午10:50:11 
* 
* @param <T> 
*/
public interface TransPoolDetailMapper<T> extends BaseMapper<T>{
	public List<Map<String,Object>>getContract(Map<String, Object>  param);
	public List<T> findAllData(QueryParameter queryParameter);
	public Integer selectCount(QueryParameter queryParameter);
	public HashMap getObject(HashMap param);
	public List<T> findAllDataByGt(QueryParameter queryParameter);
	public Integer selectCountByGt(QueryParameter queryParameter);
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
