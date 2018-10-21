package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.OfferRedisBean;
import com.bt.lmis.model.OriginationDestinationParam;

/**
 * @Title:OriginDestParamMapper(始发地目的地参数DAO)
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年7月13日下午2:49:38
 */
public interface OriginDestParamMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO(获取报价)
	 * @param key
	 * @return: OriginationDestinationParam 
	 * @author Ian.Huang 
	 * @date 2016年11月30日上午10:38:19
	 */
	public OriginationDestinationParam get(@Param("key")String key);
	
	/**
	 * 
	 * @Description: TODO((加载报价-0:物流报价/1:快递报价))
	 * @param transport_type
	 * @return: List<OfferRedisBean>  
	 * @author Ian.Huang 
	 * @date 2016年11月28日下午4:14:26
	 */
	public List<OfferRedisBean> loadOffer(@Param("transport_type")int transport_type);
	
	/**
	 * 
	 * @Description: TODO(查询记录)
	 * @param param
	 * @return: List<OriginationDestinationParam> 
	 * @author Ian.Huang 
	 * @date 2016年7月13日下午3:55:07
	 */
	public List<OriginationDestinationParam> findRecord(Map<String, Object> param);
	
	/**
	 * 
	 * @Description: TODO(查询地址等级)
	 * @param area_name
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月13日下午3:54:14
	 */
	public Integer selectLevel(@Param("area_name")String area_name);
}
