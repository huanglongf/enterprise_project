package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.Store;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;

public interface StoreService<T> extends BaseService<T> {
	/** 
	* @Title: findAll 
	* @Description: TODO(查询所有店铺) 
	* @param @return    设定文件 
	* @return List<Store>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> findAll();
	/** 
	* @Title: selectById 
	* @Description: TODO(根据ID获取店铺) 
	* @param @param id
	* @param @return    设定文件 
	* @return Store    返回类型 
	* @throws 
	*/
	public Store selectById(@Param("id")int id);
	
	public List<Store> selectByClient(@Param("client_id")Integer client_id);
	
	public Store findByContractOwner(@Param("contract_owner")String contract_owner);
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param store
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年12月9日下午5:53:59
	 */
	public JSONObject save(HttpServletRequest request, Store store, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年12月9日下午2:19:04
	 */
	public JSONObject getStore(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年12月8日下午6:01:55
	 */
	public JSONObject deleteStores(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParam
	 * @return: QueryResult<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年12月8日下午3:45:04
	 */
	public QueryResult<Map<String,Object>> query(QueryParameter queryParam);
	public Store selectBySelective(Store store);
	
}
