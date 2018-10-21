package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;
import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.WarehouseExpressDataParam;
import com.bt.lmis.controller.form.WarehouseExpressDataQueryParam;
import com.bt.lmis.model.WarehouseExpressData;

/**
* @ClassName: WarehouseExpressDataMapper
* @Description: TODO(WarehouseExpressDataMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface WarehouseExpressDataMapper<T> extends BaseMapper<T> {
	
	/** 
	* @Title: queryAll 
	* @Description: TODO(查询快递原始数据) 
	* @param @param data
	* @param @return    设定文件 
	* @return List<WarehouseExpressData>    返回类型 
	* @throws 
	*/
	public List<WarehouseExpressData> queryAll(WarehouseExpressData data);

	public int countAllGroup(WarehouseExpressDataParam warehouseExpressDataPar);

	public List<Map<String, Object>> queryExpressAll(WarehouseExpressDataParam warehouseExpressDataPar);

	public int countQueryExpressAll(WarehouseExpressDataParam warehouseExpressDataPar);

	public void update_Hcf_settle_flag(WarehouseExpressData data);
	
	public  List<WarehouseExpressData> findAll(WarehouseExpressDataQueryParam queryParam);
	
	public  List<WarehouseExpressData> findSection(Map<String, Object> data);

	public int getCount(WarehouseExpressDataQueryParam queryParam);

	public List<Map<String,Object>> findAllMap(WarehouseExpressDataQueryParam queryParam);

	public List<WarehouseExpressData> queryAllSE(WarehouseExpressData data);

	public int countByEntitySE(WarehouseExpressData data);

	public int countAllSE(WarehouseExpressData data);

	public int countExpressAllSE(WarehouseExpressData data);

}
