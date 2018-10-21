package com.bt.lmis.service;

import java.util.List;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.StorageExpendituresData;

public interface StorageExpendituresDataService<T> extends BaseService<T> {
	public List<StorageExpendituresData> findDate(StorageExpendituresData storageExpendituresData);

	public List<StorageExpendituresData> findDateSE(StorageExpendituresData storageExpendituresData);

	public Integer countDateSE(StorageExpendituresData storageExpendituresData);
}
