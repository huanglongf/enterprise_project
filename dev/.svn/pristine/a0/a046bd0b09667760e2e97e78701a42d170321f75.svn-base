package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.controller.form.WarehouseExpressDataStoreSettlementQueryParam;
import com.bt.lmis.model.PackageCharageSummary;
import com.bt.lmis.model.WarehouseExpressDataStoreSettlement;
import com.bt.lmis.model.YFSettlementVo;
import com.bt.lmis.page.QueryResult;

public interface WarehouseExpressDataStoreSettlementService<T> extends BaseService<T> {
	/** 
	* @Title: insertPGSummary 
	* @Description: TODO(插入汇总记录) 
	* @param @param pcs    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void insertPGSummary(PackageCharageSummary pcs);
	
	public QueryResult<WarehouseExpressDataStoreSettlement> findAll(WarehouseExpressDataStoreSettlementQueryParam queryParam);

	/**
	 * @param map
	 * @return
	 */
	public int getCount(YFSettlementVo map);

	/**
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryExport(YFSettlementVo map);
}
