package com.bt.lmis.dao;
import java.util.List;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.StorageExpendituresDataSettlementQueryParam;
import com.bt.lmis.model.CustomserTransSettleBean;
import com.bt.lmis.model.StorageExpendituresDataSettlement;

/**
* @ClassName: StorageExpendituresDataSettlementMapper
* @Description: TODO(StorageExpendituresDataSettlementMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface StorageExpendituresDataSettlementMapper<T> extends BaseMapper<T> {

	public List<StorageExpendituresDataSettlement> findBySEDS(StorageExpendituresDataSettlement seds);
	/** 
	* @Title: findSEDS 
	* @Description: TODO(根据条件查询[分页]内容) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<ContractBasicinfo>    返回类型 
	* @throws 
	*/
	public List<StorageExpendituresDataSettlement> findSEDS(StorageExpendituresDataSettlementQueryParam queryParam);
	/** 
	* @Title: countSEDSRecords 
	* @Description: TODO(根据条件查询[分页]条数) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public int countSEDSRecords(StorageExpendituresDataSettlementQueryParam queryParam);
	
	
	public void updateSEDS(StorageExpendituresDataSettlement seds);
	
	public void delete_readyForResettle(StorageExpendituresDataSettlement storageExpendituresDataSettlement);
	
}
