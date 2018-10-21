package com.bt.lmis.dao;
import java.util.HashMap;
import java.util.List;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.CustomserTransSettleBean;
import com.bt.lmis.model.StorageDataGroup;

/**
* @ClassName: StorageDataGroupMapper
* @Description: TODO(StorageDataGroupMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface StorageDataGroupMapper<T> extends BaseMapper<T> {

	public List<StorageDataGroup> findBySDG(StorageDataGroup sdg);
	//客户运费结算
	public List<CustomserTransSettleBean> findByCSP(HashMap<String,Object>param);
	
	public void save(StorageDataGroup sdg);
	
	public void delete_readyForResettle(StorageDataGroup sdg);
}
