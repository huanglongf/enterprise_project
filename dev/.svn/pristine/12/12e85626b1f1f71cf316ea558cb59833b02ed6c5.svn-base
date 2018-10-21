package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.model.TransportProductType;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;

/**
 * @Title:TransportProductTypeService
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年12月13日下午8:00:40
 */
public interface TransportProductTypeService<T> {
	
	/**
	 * 
	 * @Description: TODO
	 * @param vendor_code
	 * @return: List<TransportProductType>  
	 * @author Ian.Huang 
	 * @date 2016年12月19日上午11:00:14
	 */
	public List<TransportProductType> getProductTypeByTranportVendor(String vendor_code);
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年12月19日上午10:46:26
	 */
	public JSONObject getProductTypeByTranportVendor(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param transportProductType
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年12月14日下午4:13:39
	 */
	public JSONObject save(HttpServletRequest request, TransportProductType transportProductType, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年12月14日下午3:06:30
	 */
	public JSONObject getProductType(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年12月14日上午11:16:00
	 */
	public JSONObject deleteProductTypes(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParam
	 * @return: QueryResult<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年12月13日下午8:00:49
	 */
	public QueryResult<Map<String,Object>> query(QueryParameter queryParam) throws Exception;

	public TransportProductType selectByNameAndExpressCode(String productTypeName, String transportCode);

	public TransportProductType findByCode(String productTypeCode);
	
}
