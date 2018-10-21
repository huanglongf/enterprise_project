package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.BalanceSummaryUsedEx;

/**
 * @Title:BalanceSummaryUsedExMapper
 * @Description: TODO(被使用快递结算汇总) 
 * @author Ian.Huang 
 * @date 2016年7月29日下午1:47:22
 */
public interface BalanceSummaryUsedExMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO
	 * @param con_id
	 * @param balance_month
	 * @param type/0-统一流程;1-特殊流程
	 * @return List<Map<String, Object>>  
	 * @author Ian.Huang 
	 * @date 2016年10月19日下午1:52:47
	 */
	public List<Map<String, Object>> getSummary(@Param("con_id")Integer con_id, @Param("balance_month")String balance_month, @Param("type")int type);
	
	/**
	 * 
	 * @Description: TODO(按月份结算主体合同查询记录)
	 * @param con_id
	 * @param balance_subject
	 * @param balance_month
	 * @return: List<Map<String, Object>>  
	 * @author Ian.Huang 
	 * @date 2016年8月1日上午10:41:34
	 */
	public List<Map<String, Object>> selectRecordsByConId(@Param("con_id")Integer con_id, @Param("balance_subject")String balance_subject, @Param("balance_month")String balance_month);

	/**
	 * 
	 * @Description: TODO
	 * @param balance_month
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月22日下午1:09:17
	 */
	public Integer judgeSummaryExistOrNot(@Param("balance_month")String balance_month);
	
	/**
	 * 
	 * @Description: TODO(批量插入)
	 * @param bSUEs
	 * @return
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年10月19日上午11:35:02
	 */
	public void insertBatch(List<BalanceSummaryUsedEx> bSUEs);
}
