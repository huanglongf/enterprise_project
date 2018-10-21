package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.WarehouseExpressDataSettlementQueryParam;
import com.bt.lmis.model.WarehouseExpressDataSettlement;

/**
* @ClassName: WarehouseExpressDataSettlementMapper
* @Description: TODO(WarehouseExpressDataSettlementMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface WarehouseExpressDataSettlementMapper<T> extends BaseMapper<T> {
	
	List<WarehouseExpressDataSettlement> findAll(WarehouseExpressDataSettlementQueryParam param);
	
	int count(WarehouseExpressDataSettlementQueryParam param);

	List<Map<String, Object>> selectDateList(WarehouseExpressDataSettlementQueryParam query);

	int selectCountNum(WarehouseExpressDataSettlementQueryParam query);
	
}
