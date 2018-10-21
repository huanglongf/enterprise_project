package com.bt.lmis.dao;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.LogisticsStandardData;

/**
 * @Title:LogisticsStandardDataMapper
 * @Description: TODO(物流原始运单数据DAO)
 * @author Ian.Huang 
 * @date 2016年9月14日下午2:36:01
 */
public interface LogisticsStandardDataMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO(插入原始数据)
	 * @param logisticsStandardData
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年9月14日下午2:37:57
	 */
	public Integer insertRawData(LogisticsStandardData logisticsStandardData);

}
