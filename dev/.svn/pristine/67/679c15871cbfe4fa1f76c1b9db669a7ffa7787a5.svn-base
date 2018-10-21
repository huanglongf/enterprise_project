package com.bt.workOrder.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.model.Employee;
import com.bt.lmis.page.QueryResult;
import com.bt.workOrder.controller.param.AccountParam;
import com.bt.workOrder.controller.param.ManhoursParam;
import com.bt.workOrder.controller.param.WoHourInterimParam;
import com.bt.workOrder.model.Manhours;
import com.bt.workOrder.model.woHourInterim;

/**
 * @Title:WorkTimeService
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年1月5日下午8:00:39
 */
public interface ManhoursService<T> {
	
	/**
	 * 
	 * @Description: TODO
	 * @param accountParam
	 * @throws Exception
	 * @return: QueryResult<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年4月19日上午11:15:36
	 */
	QueryResult<Map<String,Object>> getWorkOrderAccounts(AccountParam accountParam) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param workTime
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年1月9日下午4:01:21
	 */
	public JSONObject update(Manhours workTime, HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年1月9日下午4:01:01
	 */
	public JSONObject add(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(获取所有下级组对应账号)
	 * @param accountParam
	 * @param request
	 * @throws Exception
	 * @return: QueryResult<Map<String,Object>> 
	 * @author Ian.Huang 
	 * @date 2017年1月7日下午5:58:17
	 */
	public QueryResult<Map<String,Object>> getSubordinates(AccountParam accountParam, HttpServletRequest request) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年1月6日下午12:03:56
	 */
	public JSONObject delWorkTime(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年1月6日上午11:24:47
	 */
	public JSONObject shiftStatus(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParam
	 * @return: QueryResult<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年1月5日下午8:15:49
	 */
	public QueryResult<Map<String,Object>> query(ManhoursParam queryParam);


	public void insertManhours(String import_status, String error_info, String work_number, String name,
			String data_time, String man_hour,String to_delete);

	public QueryResult<woHourInterim> queryHoursList(WoHourInterimParam woHourInterimPar);

	public void updateWoHourInterimToDelete();

	public List<woHourInterim> queryHourInterim();


	public Employee findsEmployeeId(String workNumber);

	public void deleteWoHourInterim(String id);

	public void addWoHourInterim(Map<String, Object> paramMap);

	public void saveWoManhours(String uuid, String createTime, String updateTime, String ids, String dataTime,
			String manHour, String status, String userName, String update_by);

	public void updateWoHourInterim(String id, String import_status, String error_info, String work_number, String name,
			String data_time, String man_hour);
	
}
