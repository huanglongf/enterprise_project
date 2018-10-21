package com.bt.radar.dao;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
import com.bt.radar.controller.form.WarehouseRelationQueryParam;
import com.bt.radar.model.WarehouseRelation;

/**
 * @Title:WarehouseRelationMapper
 * @Description: TODO(物理仓逻辑仓关系DAO)  
 * @author Ian.Huang 
 * @date 2016年8月31日下午2:59:55
 */
public interface WarehouseRelationMapper<T> extends BaseMapper<T> {

	/**
	 * 
	 * @Description: TODO(总存在记录条数)
	 * @param warehouseRelationQueryParam
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月31日下午1:17:55
	 */
	public Integer countAllExist(WarehouseRelationQueryParam warehouseRelationQueryParam);
	
	/**
	 * 
	 * @Description: TODO(加载列表)
	 * @param warehouseRelationQueryParam
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年8月31日下午1:09:15
	 */
	public List<Map<String, Object>> toList(WarehouseRelationQueryParam warehouseRelationQueryParam);
	
	/**
	 * 
	 * @Description: TODO(按条件查询值)
	 * @param warehouseRelation
	 * @return
	 * @return: PhysicalWarehouse  
	 * @author Ian.Huang 
	 * @date 2016年8月31日下午1:07:38
	 */
	public WarehouseRelation findAllExist(WarehouseRelation warehouseRelation);
	
	/**
	 * 
	 * @Description: TODO(更新一条记录)
	 * @param warehouseRelation
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月31日下午1:06:53
	 */
	public Integer updateWarehouseRelation(WarehouseRelation warehouseRelation); 
	
	/**
	 * 
	 * @Description: TODO(插入一条记录)
	 * @param warehouseRelation
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月31日下午1:06:15
	 */
	public Integer insertWarehouseRelation(WarehouseRelation warehouseRelation);
	
}
