package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import com.bt.lmis.balance.controller.form.BalanceErrorLogParam;
import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.BalanceErrorLog;
import com.bt.lmis.model.YFSettlementVo;

/**
 * @Title:BalanceErrorLogMapper
 * @Description: TODO(结算异常日志DAO)
 * @author Ian.Huang 
 * @date 2016年7月19日下午4:14:56
 */
public interface BalanceErrorLogMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO(新增结算异常记录)
	 * @param bEL
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月19日下午4:16:00
	 */
	public Integer addBalanceErrorLog(BalanceErrorLog bEL);
	
	public List<Map<String,Object>> selectByPram(BalanceErrorLogParam map);

	/**
	 * @param map
	 * @return
	 */
	public int totalCount(BalanceErrorLogParam map);

	/**
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryExport(YFSettlementVo map);

	/**
	 * @param map
	 * @return
	 */
	public int getCount(YFSettlementVo map);
	
}
