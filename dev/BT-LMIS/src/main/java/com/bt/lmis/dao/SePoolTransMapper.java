package com.bt.lmis.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;

/**
 * @Title:SePoolTransMapper
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年8月22日下午5:11:11
 */
public interface SePoolTransMapper<T> extends BaseMapper<T> {

	/**
	 * 
	 * @Description: TODO(查询汇总记录)
	 * @param con_id
	 * @param balance_month
	 * @return
	 * @return: Map<String, Object> 
	 * @author Ian.Huang 
	 * @date 2016年8月22日下午5:12:28
	 */
	public Map<String, Object> getSummary(@Param("con_id")Integer con_id, @Param("balance_month")String balance_month);
}
