package com.bt.radar.dao;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
import com.bt.radar.controller.form.PhysicalWarehouseQueryParam;
import com.bt.radar.model.PhysicalWarehouse;

/**
* @ClassName: PhysicalWarehouseMapper
* @Description: TODO(物理仓DAO)
* @author Ian.Huang
* @date 2016年8月26日 上午12:05:49
*
* @param <T>
*/
public interface PhysicalWarehouseMapper<T> extends BaseMapper<T> {

	/**
	 * 
	 * @Description: TODO(总存在记录条数)
	 * @param physicalWarehouseQueryParam
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月31日下午1:17:55
	 */
	public Integer countAllExist(PhysicalWarehouseQueryParam physicalWarehouseQueryParam);
	
	/**
	 * 
	 * @Description: TODO(加载列表)
	 * @param physicalWarehouseQueryParam
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年8月31日下午1:09:15
	 */
	public List<Map<String, Object>> toList(PhysicalWarehouseQueryParam physicalWarehouseQueryParam);
	
	/**
	 * 
	 * @Description: TODO(按条件查询值)
	 * @param physicalWarehouse
	 * @return
	 * @return: PhysicalWarehouse  
	 * @author Ian.Huang 
	 * @date 2016年8月31日下午1:07:38
	 */
	public PhysicalWarehouse findAllExist(PhysicalWarehouse physicalWarehouse);
	
	/**
	 * 
	 * @Description: TODO(更新一条记录)
	 * @param physicalWarehouse
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月31日下午1:06:53
	 */
	public Integer updatePhysicalWarehouse(PhysicalWarehouse physicalWarehouse); 
	
	/**
	 * 
	 * @Description: TODO(插入一条记录)
	 * @param physicalWarehouse
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月31日下午1:06:15
	 */
	public Integer insertPhysicalWarehouse(PhysicalWarehouse physicalWarehouse);
	
	/**
	* @Title: getPhysicalWarehouse
	* @Description: TODO(获取所有存在物理仓)
	* @param @return    设定文件
	* @return List<PhysicalWarehouse>    返回类型
	* @throws
	*/ 
	public List<PhysicalWarehouse> getPhysicalWarehouse();

	public void pro_radar_refresh_warehouse_temp();
}
