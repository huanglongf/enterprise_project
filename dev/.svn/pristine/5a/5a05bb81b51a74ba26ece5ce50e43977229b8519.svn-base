/**
 * 
 */
package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.TransportVendor;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;

/**
 * @Title:TransportVendorService
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年12月12日下午5:35:18
 */
public interface TransportVendorService<T> extends BaseService<T> {

	/**
	 * 
	 * @Description: TODO
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年12月12日下午8:16:32
	 */
	public List<Map<String, Object>> getAllExpress();
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param transportVendor
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年12月13日上午11:39:52
	 */
	public JSONObject save(HttpServletRequest request, TransportVendor transportVendor, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @throws Exception
	 * @return: TransportVendor  
	 * @author Ian.Huang 
	 * @date 2016年12月13日上午11:38:41
	 */
	public TransportVendor getById(int id) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param req
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年12月12日下午8:16:24
	 */
	public JSONObject deleteTransportVendors(HttpServletRequest req, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param qr
	 * @return: QueryResult<T>  
	 * @author Ian.Huang 
	 * @date 2016年12月12日下午4:54:57
	 */
	public QueryResult<T> query(QueryParameter qr);

	public TransportVendor selectByName(String expressName);

	public TransportVendor findByCode(String productTypeCode);
	
}
