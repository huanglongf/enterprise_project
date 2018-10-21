package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.model.TransportProductType;
import com.bt.lmis.page.QueryParameter;

/**
 * @Title:TransportProductTypeMapper
 * @Description: TODO(物流商产品类型DAO)
 * @author Ian.Huang 
 * @date 2016年12月12日下午9:31:07
 */
public interface TransportProductTypeMapper<T> {
	
	/**
	 * 
	 * @Description: TODO(判断物流商下是否存在产品类型)
	 * @param status
	 * @param vendor_code
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月19日上午11:21:27
	 */
	public Integer judgeProductTypeExistOrNot(@Param("status")Boolean status, @Param("vendor_code")String vendor_code);
	
	/**
	 * 
	 * @Description: TODO
	 * @param status
	 * @param vendor_code
	 * @return: List<TransportProductType>  
	 * @author Ian.Huang 
	 * @date 2016年12月19日上午10:51:41
	 */
	public List<TransportProductType> findByTransportVendor(@Param("status")Boolean status, @Param("vendor_code")String vendor_code);
	
	/**
	 * 
	 * @Description: TODO
	 * @param transportProductType
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月14日下午4:27:22
	 */
	public Integer updateTransportProductType(TransportProductType transportProductType);
	
	/**
	 * 
	 * @Description: TODO
	 * @param transportProductType
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月14日下午4:27:22
	 */
	public Integer addTransportProductType(TransportProductType transportProductType);
	
	/**
	 * 
	 * @Description: TODO
	 * @param status
	 * @param product_type_code
	 * @return: TransportProductType  
	 * @author Ian.Huang 
	 * @date 2016年12月14日下午4:22:45
	 */
	public TransportProductType findByCode(@Param("status")Boolean status, @Param("product_type_code")String product_type_code);
	
	/**
	 * 
	 * @Description: TODO(根据ID查询物流商产品类型)
	 * @param id
	 * @return: TransportProductType  
	 * @author Ian.Huang 
	 * @date 2016年7月13日上午10:08:29
	 */
	public TransportProductType selectById(@Param("id") Integer id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param ids
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月12日下午8:39:01
	 */
	public Integer delTransportProductTypes(Integer[] ids);
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParameter
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月13日下午8:11:23
	 */
	public Integer count(QueryParameter queryParameter);
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParameter
	 * @return: List<Map<String, Object>>
	 * @author Ian.Huang 
	 * @date 2016年12月13日下午8:03:29
	 */
	public List<Map<String, Object>> query(QueryParameter queryParameter);
	
	/**
	 * 
	 * @Description: TODO(根据复数物流承运商ID批量删除其对应产品类型)
	 * @param transportVendorIds
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月12日下午9:32:23
	 */
	public Integer delByTransportVendor(List<Integer> transportVendorIds);

	/**
	 * 获取产品类型名称
	 */
	public Map<String, Object> getItemName(Map<String, String> param1);

	public TransportProductType selectByNameAndExpressCode(@Param("productTypeName")String productTypeName,@Param("transportCode")String transportCode);
	
}
