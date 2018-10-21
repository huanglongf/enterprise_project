package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.NextPriceInternalRedisBean;

/**
 * @Title:NextPriceInternalMapper(续费区间续费价格查询)
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年7月14日下午2:54:38
 */
public interface NextPriceInternalMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO
	 * @param redis_key
	 * @return: List<NextPriceInternalRedisBean>  
	 * @author Ian.Huang 
	 * @date 2016年11月30日下午5:31:26
	 */
	public List<NextPriceInternalRedisBean> get(@Param("redis_key")String redis_key);
	
	/**
	 * 
	 * @Description: TODO(加载续重区间)
	 * @return
	 * @return: List<NextPriceInternalRedisBean>  
	 * @author Ian.Huang 
	 * @date 2016年11月29日上午11:04:04
	 */
	public List<NextPriceInternalRedisBean> loadNextPriceInternal();
	
	/**
	 * 
	 * @Description: TODO(查询续重区间续重价格)
	 * @param table_id
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年7月14日下午2:56:16
	 */
	public List<Map<String, Object>> selectNextPriceInternal(@Param("table_id")int table_id);
}
