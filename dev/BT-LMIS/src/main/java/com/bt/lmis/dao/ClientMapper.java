package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.Client;
import com.bt.lmis.page.QueryParameter;

/**
 * @Title:ClientMapper
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年12月7日下午6:14:38
 */
public interface ClientMapper<T> extends BaseMapper<T> {

	public List<Map<String, Object>> findAll();
	
	public List<Client> findByClient(Client client);
	
	/**
	 * 
	 * @Description: TODO
	 * @param client
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月7日下午5:09:24
	 */
	public Integer addClient(Client client);
	
	/**
	 * 
	 * @Description: TODO
	 * @param client
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月7日下午4:58:41
	 */
	public Integer updateClient(Client client);
	
	/**
	 * 
	 * @Description: TODO
	 * @param contract_owner
	 * @return: Client  
	 * @author Ian.Huang 
	 * @date 2016年12月7日下午4:46:08
	 */
	public Client findByContractOwner(@Param("contract_owner")String contract_owner);
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @return: Client  
	 * @author Ian.Huang 
	 * @date 2016年12月7日上午10:03:18
	 */
	public Client getById(@Param("id")Integer id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param ids
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月7日下午6:09:19
	 */
	public Integer delClients(List<Integer> ids);
	
	/**
	 * 
	 * @Description: TODO(判断客户是否已建合同)
	 * @param id
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月6日下午2:26:09
	 */
	public Integer judgeClientContractExistOrNot(@Param("id")Integer id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParam
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月6日下午4:05:29
	 */
	Integer count(QueryParameter queryParam);
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParam
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年12月6日下午3:51:03
	 */
	public List<Map<String, Object>> query(QueryParameter queryParam);

	public List<Map<String, Object>> getStoreByClient(Map map);
	
}
