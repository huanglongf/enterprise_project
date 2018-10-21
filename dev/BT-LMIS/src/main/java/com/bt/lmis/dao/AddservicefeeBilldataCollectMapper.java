package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.AddservicefeeBilldataCollect;

/**
* @ClassName: AddservicefeeBilldataCollectMapper
* @Description: TODO(AddservicefeeBilldataCollectMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface AddservicefeeBilldataCollectMapper<T> extends BaseMapper<T> {

	public List<AddservicefeeBilldataCollect> findByList(AddservicefeeBilldataCollect addservicefeeBilldataCollect);
	public List<Map<String,String>> findGroupByServiceName();
	public void  delete_by_con(AddservicefeeBilldataCollect param);
	
}
