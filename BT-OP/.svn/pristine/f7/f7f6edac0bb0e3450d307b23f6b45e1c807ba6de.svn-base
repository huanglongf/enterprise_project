package com.bt.orderPlatform.service;

import java.io.Serializable;
import java.util.Map;

/**
 * 映射器
 * @author Yuriy.Jiang
 *
 * @param <T>
 */
public interface BaseMapper<T> {
	
	/**
	 * 插入一条记录
	 * @param entity
	 * @throws Exception
	 */
	int insert(T entity) throws Exception;
	
	/**
	 * 更新一条记录
	 * @param entity
	 * @throws Exception
	 */
	int update(T entity) throws Exception;
	
	/**
	 * 删除
	 * @param id
	 * @throws Exception
	 */
	void delete(Serializable id) throws Exception;
	
	/**
	 * 批量删除
	 * @param ids
	 * @throws Exception
	 */
	void batchDelete(Integer[]ids) throws Exception;
	
	/**
	 * 依据id查询记录
	 * @param id
	 * @return
	 * @throws Exception
	 */
	T selectById(Integer id) throws Exception;
	
	Long selectCount(Map<String, Object> param) throws Exception;
}
