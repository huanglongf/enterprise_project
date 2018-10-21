package com.bt.radar.dao;
import java.util.List;

import com.bt.lmis.code.BaseMapper;
import com.bt.radar.model.LogicWarehouse;

/**
 * @Title:LogicWarehouseMapper
 * @Description: TODO(逻辑仓DAO)
 * @author Ian.Huang 
 * @date 2016年9月1日上午10:33:56
 */
public interface LogicWarehouseMapper<T> extends BaseMapper<T> {

	/**
	 * 
	 * @Description: TODO(查询逻辑仓记录)
	 * @param List<logicWarehouse>
	 * @return
	 * @return: LogicWarehouse  
	 * @author Ian.Huang 
	 * @date 2016年9月1日上午10:36:40
	 */
	public List<LogicWarehouse> selectRecords(LogicWarehouse logicWarehouse);
}
