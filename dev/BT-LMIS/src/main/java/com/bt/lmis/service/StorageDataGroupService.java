package com.bt.lmis.service;

import java.util.HashMap;
import java.util.List;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.CustomserTransSettleBean;
import com.bt.lmis.model.StorageDataGroup;
import com.bt.lmis.model.StorageExpendituresDataSettlement;

public interface StorageDataGroupService<T> extends BaseService<T> {
	public List<StorageDataGroup> findBySDG(StorageDataGroup sdg);
	public List<CustomserTransSettleBean> findByCSP(HashMap<String,Object>param);
	public void saveAUpdate(StorageDataGroup sdg,List<StorageExpendituresDataSettlement> list) throws Exception;

}
