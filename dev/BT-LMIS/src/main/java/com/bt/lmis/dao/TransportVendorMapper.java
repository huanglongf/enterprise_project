package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.TransportVendor;
import com.bt.lmis.page.QueryParameter;

/**
 * @Title:TransportVendorMapper(承运商DAO)
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年6月27日下午3:28:57
 */
public interface TransportVendorMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO(查询所有承运商)
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年12月19日上午9:56:02
	 */
	public List<Map<String, Object>> findAllTransportVendor();
	
	/**
	 * 
	 * @Description: TODO(查询所有有效快递商)
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年7月13日上午10:07:43
	 */
	public List<Map<String, Object>> findExpress();
	
	/**
	 * 
	 * @Description: TODO(查询所有有效物流商)
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年7月13日上午10:07:58
	 */
	public List<Map<String, Object>> findPhysicaldistribution();
	
	/**
	 * 
	 * @Description: TODO
	 * @param transportVendor
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月13日下午12:04:23
	 */
	public Integer addTransportVendor(TransportVendor transportVendor);
	
	/**
	 * 
	 * @Description: TODO
	 * @param transportVendor
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月13日上午11:59:50
	 */
	public Integer updateTransportVendor(TransportVendor transportVendor);
	
	/**
	 * 
	 * @Description: TODO(根据承运商代码查询记录)
	 * @param validity
	 * @param transport_code
	 * @return: TransportVendor  
	 * @author Ian.Huang 
	 * @date 2016年12月13日上午11:49:20
	 */
	public TransportVendor findByCode(@Param("validity")Boolean validity, @Param("transport_code")String transport_code);
	
	/**
	 * 
	 * @Description: TODO(根据ID查询承运商)
	 * @param id
	 * @return: TransportVendor  
	 * @author Ian.Huang 
	 * @date 2016年7月13日上午10:08:29
	 */
	public TransportVendor getById(@Param("id") int id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param ids
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月12日下午8:39:01
	 */
	public Integer delTransportVendors(List<Integer> ids);
	
	/**
	 * 
	 * @Description: TODO
	 * @param vendor_code
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月19日上午10:12:34
	 */
	public Integer judgeTransportVendorContractExistOrNotByCode(@Param("vendor_code")String vendor_code);
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月12日下午8:35:33
	 */
	public Integer judgeTransportVendorContractExistOrNot(@Param("id")Integer id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParameter
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月12日下午5:24:53
	 */
	public Integer count(QueryParameter queryParameter);
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParameter
	 * @return: List<T>  
	 * @author Ian.Huang 
	 * @date 2016年12月12日下午5:24:33
	 */
	public List<T> query(QueryParameter queryParameter);

	public TransportVendor selectByName(String expressName);
	
}
