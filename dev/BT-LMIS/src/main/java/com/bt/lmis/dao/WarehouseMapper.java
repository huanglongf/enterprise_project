package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.WarehouseQueryParam;
import com.bt.lmis.model.Warehouse;

/**
* @ClassName: WarehouseMapper
* @Description: TODO(WarehouseMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface WarehouseMapper<T> extends BaseMapper<T> {
	
	/**
	* @Title: add
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param warehouse
	* @param @return    设定文件
	* @return Integer    返回类型
	* @throws
	*/ 
	public Integer add(Warehouse warehouse);
	
	/**
	 * 
	 * @Description: TODO
	 * @param warehouse
	 * @return
	 * @return: Warehouse  
	 * @author Ian.Huang 
	 * @date 2016年9月25日下午8:01:55
	 */
	public Warehouse getByCondition(Warehouse warehouse);
	
	/**
	 * 
	 * @Description: TODO
	 * @param warehouse
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年9月25日下午6:52:17
	 */
	public Integer update(Warehouse warehouse);
	
	/**
	 * 
	 * @Description: TODO
	 * @param warehouseQueryParam
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年9月25日下午4:23:47
	 */
	public Integer countList(WarehouseQueryParam warehouseQueryParam);
	
	/**
	 * 
	 * @Description: TODO
	 * @param warehouseQueryParam
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年9月25日下午4:23:44
	 */
	public List<Map<String, Object>> listAllWarehouse(WarehouseQueryParam warehouseQueryParam);
	
	/**
	 * 
	 * @Description: TODO
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年9月25日下午4:45:27
	 */
	public List<Map<String, Object>> findAll();

	public Warehouse selectByWarehouseName(String warehouseName);

	public Warehouse selectByWarehouseCode(String warehouseCode);

	public List<Map<String, Object>> download(WarehouseQueryParam warehouseQueryParam);

}
