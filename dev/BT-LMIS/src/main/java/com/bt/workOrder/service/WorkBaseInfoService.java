package com.bt.workOrder.service;
import java.util.ArrayList;
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
public interface WorkBaseInfoService<T> extends BaseService<T>{
	public QueryResult<T> findAllData(QueryParameter qr);
	public Map<String, Object> addType(Map<String,Object>param);
	public void updateType(Map<String,Object>param);
	public ArrayList<?> getTypeTab(Map<String,Object> param);
	public ArrayList<?> getReasonTab(Map<String,Object> param);
	public ArrayList<?> getErrorTab(Map<String,Object> param);
	public void add_type_kid(Map<String,Object>param);
	public void up_type_kid(Map<String,Object>param);
	public void add_reason_kid(Map<String,Object>param);
	public void up_reason_kid(Map<String,Object>param);
	public void add_error_kid(Map<String,Object>param);
	public void up_error_kid(Map<String,Object>param);
	public void batchUpdate(Map<String,Object>param);
	public Map<String,String>getMainInfo(Map<String,Object>param);
    public void delType(Map<String,Object>param);
    public void delReason(Map<String,Object>param);
    public void delError(Map<String,Object>param);
    public ArrayList<?> getAllType();
    public ArrayList<?> getAllLevel(Map<String,Object> param);
    Integer checkStatus(Integer[]ids) throws Exception;
	/**
	 * @param parameter
	 * @return
	 */
	public QueryResult<Map<String, Object>> findAllData2(Map<String, Object> map);
}
