package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.AddservicefeeBilldataCollect;

public interface AddservicefeeBilldataCollectService<T> extends BaseService<T> {
	public List<AddservicefeeBilldataCollect> findByList(AddservicefeeBilldataCollect addservicefeeBilldataCollect);
	public List<Map<String,String>> findGroupByServiceName();
}
