package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.WarehouseExpressDataStoreSettlementQueryParam;
import com.bt.lmis.model.PackageCharageSummary;
import com.bt.lmis.model.WarehouseExpressDataStoreSettlement;
import com.bt.lmis.model.YFSettlementVo;

/**
* @ClassName: WarehouseExpressDataStoreSettlementMapper
* @Description: TODO(WarehouseExpressDataStoreSettlementMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface WarehouseExpressDataStoreSettlementMapper<T> extends BaseMapper<T> {

	void delete_by_con(WarehouseExpressDataStoreSettlement settle);
	
	/** 
	* @Title: insertPGSummary 
	* @Description: TODO(插入汇总记录) 
	* @param @param pcs    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void insertPGSummary(PackageCharageSummary pcs);
	
	List<WarehouseExpressDataStoreSettlement> findAll(WarehouseExpressDataStoreSettlementQueryParam param);
	
	int count(WarehouseExpressDataStoreSettlementQueryParam param);

	/**
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryExport(YFSettlementVo map);

	/**
	 * @param map
	 * @return
	 */
	int getCount(YFSettlementVo map);
}
