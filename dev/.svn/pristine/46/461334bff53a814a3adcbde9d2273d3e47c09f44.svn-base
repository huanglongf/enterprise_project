package com.bt.lmis.service;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.controller.form.WarehouseExpressDataQueryParam;
import com.bt.lmis.model.WarehouseExpressData;
import com.bt.lmis.page.QueryResult;

/** 
* @ClassName: WarehouseExpressDataService 
* @Description: TODO(快递原始数据服务类) 
* @author Yuriy.Jiang
* @date 2016年7月27日 下午2:18:38 
* 
* @param <T> 
*/
public interface WarehouseExpressDataService<T> extends BaseService<T> {
	
	/** 
	* @Title: queryAll 
	* @Description: TODO(查询快递原始数据) 
	* @param @param data
	* @param @return    设定文件 
	* @return List<WarehouseExpressData>    返回类型 
	* @throws 
	*/
	public List<WarehouseExpressData> queryAll(WarehouseExpressData data);
	
	public List<WarehouseExpressData> querySection(Map<String, Object> data);

	public List<WarehouseExpressData> queryExpressAll(WarehouseExpressData data);

	public QueryResult<WarehouseExpressData> findAll(WarehouseExpressDataQueryParam queryParam,boolean hasCount);

	public List<WarehouseExpressData> queryAllSE(WarehouseExpressData data);

	public List<WarehouseExpressData> queryExpressAllSE(WarehouseExpressData data);

	public int countByEntitySE(WarehouseExpressData data);

	public int countAllSE(WarehouseExpressData data);

	public int countExpressAllSE(WarehouseExpressData data);

	/**
	 * @param data
	 * @return
	 */
	public int countSection(WarehouseExpressDataQueryParam data);
}
