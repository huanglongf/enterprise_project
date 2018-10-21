package com.bt.lmis.service;

import java.util.List;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.controller.form.StorageExpendituresDataSettlementQueryParam;
import com.bt.lmis.model.StorageExpendituresDataSettlement;
import com.bt.lmis.page.QueryResult;

public interface StorageExpendituresDataSettlementService<T> extends BaseService<T> {
	
	public List<StorageExpendituresDataSettlement> findBySEDS(StorageExpendituresDataSettlement seds);
	/** 
	* @Title: findAll 
	* @Description: TODO(根据条件查询合同分页) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return QueryResult<StorageExpendituresDataSettlement>    返回类型 
	* @throws 
	*/
	public QueryResult<StorageExpendituresDataSettlement> findAll(StorageExpendituresDataSettlementQueryParam queryParam);
}
