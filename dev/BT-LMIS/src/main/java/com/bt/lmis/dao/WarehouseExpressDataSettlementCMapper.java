package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import com.bt.lmis.model.WarehouseExpressDataSettlementC;
import com.bt.lmis.model.YFSettlementVo;

public interface WarehouseExpressDataSettlementCMapper {

	/**
	 * @param queryParam
	 * @return
	 */
	List<WarehouseExpressDataSettlementC> findAll(WarehouseExpressDataSettlementC queryParam);

	/**
	 * @param queryParam
	 * @return
	 */
	int getCount(WarehouseExpressDataSettlementC queryParam);

	/**
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryExport(YFSettlementVo map);

	/**
	 * @param map
	 * @return
	 */
	int count(YFSettlementVo map);
}