package com.bt.workOrder.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.workOrder.controller.param.OMSInterfaceExcpeitonHandlingParam;

/**
 * @Title:OMSInterfaceExceptionHandlingMapper
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年4月11日上午11:45:22
 */
public interface OMSInterfaceExceptionHandlingMapper<T> {
	/**
	 * 
	 * @Description: TODO
	 * @param param
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年4月11日上午11:46:23
	 */
	List<Map<String, Object>> query(OMSInterfaceExcpeitonHandlingParam param);
	
	/**
	 * 
	 * @Description: TODO
	 * @param param
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年4月11日上午11:47:00
	 */
	Integer count(OMSInterfaceExcpeitonHandlingParam param);
	
	/**
	 * 
	 * @Description: TODO
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年4月12日上午10:29:34
	 */
	List<Map<String, Object>> getCarriers();
	
	/**
	 * 
	 * @Description: TODO
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年4月12日上午10:29:31
	 */
	List<Map<String, Object>> getWarehouses();
	
	/**
	 * 
	 * @Description: TODO
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年4月12日上午10:29:03
	 */
	List<Map<String, Object>> getStores();
	
	/**
	 * 
	 * @Description: TODO
	 * @param express_number
	 * @return: Map<String,Object>  
	 * @author Ian.Huang 
	 * @date 2017年4月12日上午10:51:34
	 */
	Map<String, Object> getByExpressNumber(@Param("express_number")String express_number);
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @return: Boolean  
	 * @author Ian.Huang 
	 * @date 2017年4月12日下午3:51:07
	 */
	Boolean judgeExceptionHandling(@Param("id")Integer id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param wk_type
	 * @return: Map<String,Object>  
	 * @author Ian.Huang 
	 * @date 2017年4月12日下午4:21:01
	 */
	Map<String, Object> getInitialLevel(@Param("wk_type")String wk_type);
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年4月12日下午9:20:35
	 */
	Integer shiftProcessFlag(@Param("id")Integer id);

	Map<String, Object> queryById(@Param("id")String id);

	/**
	 * @param id
	 * @return
	 */
	int updateById(@Param("id")String id);

	/**
	 * @return
	 */
	int queryEHById(@Param("id")String id);
	
}