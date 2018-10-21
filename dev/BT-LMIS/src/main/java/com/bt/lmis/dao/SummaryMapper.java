package com.bt.lmis.dao;
import java.util.List;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.StorageExpendituresDataSettlementQueryParam;
import com.bt.lmis.model.StorageExpendituresDataSettlement;

/**
* @ClassName: SummaryMapper
* @Description: TODO(SummaryMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface SummaryMapper<T> extends BaseMapper<T> {

	public List<StorageExpendituresDataSettlement> find(StorageExpendituresDataSettlementQueryParam queryParam);
	public int countRecords(StorageExpendituresDataSettlementQueryParam queryParam);
}
