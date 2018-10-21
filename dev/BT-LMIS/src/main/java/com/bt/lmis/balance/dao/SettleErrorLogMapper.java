package com.bt.lmis.balance.dao;

import com.bt.lmis.balance.model.SettleErrorLog;

/** 
 * @ClassName: SettleErrorLogMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年7月4日 下午2:30:22 
 * 
 * @param <T>
 */
public interface SettleErrorLogMapper<T> {
	
	/**
	 * @Title: insert
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param settleErrorLog
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年7月4日 下午4:01:06
	 */
	void insert(SettleErrorLog settleErrorLog);
	
}
