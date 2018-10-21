package com.bt.lmis.balance.dao;
import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.PackageCharageSummary;
import com.bt.lmis.model.WarehouseExpressDataStoreSettlement;

/**
* @ClassName: WarehouseExpressDataStoreSettlementMapper
* @Description: TODO(WarehouseExpressDataStoreSettlementMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface WarehouseExpressDataStoreSettlementEstimateMapper<T> extends BaseMapper<T> {

	void delete_by_con(WarehouseExpressDataStoreSettlement settle);
	
	/** 
	* @Title: insertPGSummary 
	* @Description: TODO(插入汇总记录) 
	* @param @param pcs    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void insertPGSummary(PackageCharageSummary pcs);
}
