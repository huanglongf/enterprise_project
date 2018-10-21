package com.bt.lmis.service;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.functions.T;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.controller.form.AddressQueryParam;
import com.bt.lmis.controller.form.ErrorAddressQueryParam;
import com.bt.lmis.controller.form.JkErrorQuery;
import com.bt.lmis.model.ErrorAddress;
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
public interface AddressService<T> extends BaseService<T>{
	public ArrayList<?> getTabData(Map param);
	public void updateXzMainData(Map param);
	public void updateTable(Map param);
	public void delTabData(Map param);
	public void add(Map param);
	public QueryResult<T> findAllErrorData(QueryParameter qr);
	public Map<String,String> updateErrorData(Map<String,Object>param);
	public List<Map<String, Object>>query_export(JkErrorQuery queryParam);
	public List<Map<String, Object>>exportData(JkErrorQuery queryParam);
	
	public QueryResult<T> getImportMainInfo(ErrorAddressQueryParam queryParam);
	public void insertOrder(List<ErrorAddress>sub_list);
	public void checkImport(String param);
	public Map<String,String> transDataInfo(Map<String,String> param);
	public void deleteImport(String bat_id);
	public List<ErrorAddress> getImportInfo(String bat_id);
	public QueryResult<T> getImportDetailInfo(JkErrorQuery queryParam);
	public void anysisData(Map<String,String>param);
	public void add_error_address(Map<String,Object>param);
	public ArrayList<Map<String,String>> get_system_address(Map<String,Object>param);
	public Map<String,String> refresh_tranaction(Map<String,String>param);
	/**
	 * 
	 * @Description: TODO
	 * @param queryParam
	 * @param request
	 * @throws Exception
	 * @return: File  
	 * @author Ian.Huang 
	 * @date 2016年12月19日下午7:53:34
	 */
	public File exportAddress(AddressQueryParam queryParam, HttpServletRequest request) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @return
	 * @return: Map<String,Object>  
	 * @author Ian.Huang 
	 * @date 2016年12月19日下午4:31:01
	 */
	public Map<String, Object> getAddress(HttpServletRequest request);
	
	/**
	 * 
	 * @Description: TODO
	 * @param param
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月19日下午4:30:57
	 */
	public void updateMainData(Map<String, Object> param);
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年12月19日下午3:45:03
	 */
	public JSONObject delete(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param qr
	 * @return: QueryResult<T>  
	 * @author Ian.Huang 
	 * @date 2016年12月16日上午11:10:31
	 */
	public QueryResult<T> query(QueryParameter qr) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @throws Exception
	 * @return: HttpServletRequest  
	 * @author Ian.Huang 
	 * @date 2016年12月16日下午2:18:37
	 */
	public HttpServletRequest queryParam(HttpServletRequest request) throws Exception;
	
	public String get_business_code();
	
}
