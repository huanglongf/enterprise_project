package com.bt.common.dao;

import com.bt.common.model.ShardTest;

/** 
 * @ClassName: ShardTestMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年6月28日 下午3:48:23 
 * 
 * @param <T>
 */
public interface ShardTestMapper<T> {

	/**
	 * @Title: insert
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param st
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年6月28日 下午3:54:21
	 */
	void insert(ShardTest st);
	
}
