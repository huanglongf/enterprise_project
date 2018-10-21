package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.Client;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;

/**
 * @Title:ClientService
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年12月7日下午6:22:34
 */
public interface ClientService<T> extends BaseService<T> {
	
	public Client findByContractOwner(String contract_owner);
	
	public List<Map<String, Object>> findAll();
	
	public List<Client> findByClient(Client client);
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param client
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年12月7日下午4:19:58
	 */
	public JSONObject editClient(HttpServletRequest request, Client client, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param client_id
	 * @throws Exception
	 * @return: Client  
	 * @author Ian.Huang 
	 * @date 2016年12月8日下午3:27:00
	 */
	public Client getById(Integer client_id) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(删除客户)
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年12月6日下午1:35:59
	 */
	public JSONObject deleteClient(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(查询客户记录)
	 * @param queryParam
	 * @return: QueryResult<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年12月6日下午3:58:10
	 */
	public QueryResult<Map<String,Object>> query(QueryParameter queryParam);

	public boolean existClientCode(String client_code, int i);

	List<Map<String, Object>> getStoreByClient(Map map);
	
}
