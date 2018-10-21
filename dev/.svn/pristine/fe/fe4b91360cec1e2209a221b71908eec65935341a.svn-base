package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.Store;
import com.bt.lmis.page.QueryParameter;

/**
 * @ClassName: StoreMapper
 * @Description: TODO(StoreMapper)
 * @author Yuriy.Jiang
 *
 * @param <T>
 */
public interface StoreMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO
	 * @param cost_center
	 * @return
	 * @return: List<Store>  
	 * @author Ian.Huang 
	 * @date 2016年10月12日下午12:20:10
	 */
	public List<Store> getStoresByCostCenter(@Param("cost_center")String cost_center);
	
	/**
	 * 
	 * @Description: TODO(按成本中心查询店铺)
	 * @param cost_center
	 * @return
	 * @return: List<String>  
	 * @author Ian.Huang 
	 * @date 2016年7月28日上午11:21:01
	 */
	public List<String> findStoresByCostCenter(@Param("cost_center")String cost_center);
	
	/**
	 * 
	 * @Description: TODO(按店铺名查成本中心)
	 * @param store_name
	 * @return
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年7月28日上午11:18:26
	 */
	public String findCostCenterByStoreName(@Param("store_name")String store_name);
	
	public List<Map<String,Object>> StoreByStoreName(@Param("store_name")String store_name);
	
	public List<Map<String, Object>> findAll();
	
	/**
	 * 
	 * @Description: TODO
	 * @param store
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月9日下午6:29:23
	 */
	public Integer addStore(Store store);
	
	/**
	 * 
	 * @Description: TODO
	 * @param store
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月9日下午6:05:28
	 */
	public Integer updateStore(Store store);
	
	/**
	 * 
	 * @Description: TODO
	 * @param contract_owner
	 * @return: Store  
	 * @author Ian.Huang 
	 * @date 2016年12月9日下午5:56:41
	 */
	public Store findByContractOwner(@Param("contract_owner")String contract_owner);
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @return: Store  
	 * @author Ian.Huang 
	 * @date 2016年12月9日下午2:20:26
	 */
	public Store selectById(@Param("id") int id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param ids
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月8日下午6:15:19
	 */
	public Integer delStores(List<Integer> ids);
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月8日下午6:12:00
	 */
	public Integer judgeStoreContractExistOrNot(@Param("id")Integer id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParam
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月8日下午3:48:42
	 */
	public Integer count(QueryParameter queryParam);
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParam
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年12月8日下午3:48:09
	 */
	public List<Map<String, Object>> query(QueryParameter queryParam);
	
	/**
	 * 
	 * @Description: TODO
	 * @param client_id
	 * @return: List<Store>  
	 * @author Ian.Huang 
	 * @date 2016年12月6日下午5:02:18
	 */
	public List<Store> selectByClient(@Param("client_id")Integer client_id);

	public List existRecord(Map<String, String> param);

	public Store selectBySelective(Store store);
	
	
	
}
