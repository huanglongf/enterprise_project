package com.bt.lmis.basis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.common.controller.param.Parameter;
import com.bt.lmis.basis.model.Store;

/** 
 * @ClassName: StoreManagerMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年7月31日 上午11:11:24 
 * 
 * @param <T>
 */
public interface StoreManagerMapper<T> {
	
	/**
	 * @Title: listStoreView
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param paremeter
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年7月31日 下午3:26:50
	 */
	List<Map<String, Object>> listStoreView(Parameter paremeter);
	
	/**
	 * @Title: countStoreView
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param paremeter
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年7月31日 下午3:27:24
	 */
	int countStoreView(Parameter paremeter);
	
	/**
	 * @Title: getStore
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param id
	 * @return: Store
	 * @author: Ian.Huang
	 * @date: 2017年8月1日 下午1:25:50
	 */
	Store getStore(@Param("id")int id);
	
	/**
	 * @Title: countStoreCode
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: List<Integer>
	 * @author: Ian.Huang
	 * @date: 2017年8月1日 下午5:20:48
	 */
	List<Integer> countStoreCode(Parameter parameter);
	
	/**
	 * @Title: countStoreName
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: List<Integer>
	 * @author: Ian.Huang
	 * @date: 2017年8月1日 下午5:20:53
	 */
	List<Integer> countStoreName(Parameter parameter);
	
	/**
	 * @Title: save
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月1日 下午4:12:54
	 */
	int insert(Parameter parameter);
	
	/**
	 * @Title: update
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月1日 下午5:30:52
	 */
	int update(Parameter parameter);
	
	/**
	 * @Title: del
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param ids
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月1日 下午7:05:37
	 */
	int del(String[] ids);

	/**
	 * 
	 * @param storeName
	 * @return
	 */
	Store selectByStroeName(String storeName);

	List<Map<String, Object>> findAll();

	Store selectByStoreCode(String storeCode);

	int addStoreTransport(List<Map<String,Object>> list);

	List<Map<String,Object>> selectTransportByStoreCode(String storeCode );

	int deleteByStoreCode(String storeCode );

	
}
