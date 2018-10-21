package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import com.bt.lmis.model.WarehouseExpressDataSettlementC;
import com.bt.lmis.model.YFSettlementVo;
import com.bt.lmis.page.QueryResult;

public interface WarehouseExpressDataSettlementCService {

	public QueryResult<WarehouseExpressDataSettlementC> findAll(WarehouseExpressDataSettlementC queryParam);

	/**
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryExport(YFSettlementVo map);

	/**
	 * @param map
	 * @return
	 */
	public int getCount(YFSettlementVo map);
}
