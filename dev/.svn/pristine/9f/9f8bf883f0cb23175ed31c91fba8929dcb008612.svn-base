package com.bt.lmis.service;

import java.util.List;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.controller.form.StorageLocationQueryParam;
import com.bt.lmis.model.StorageLocation;
import com.bt.lmis.page.QueryResult;

public interface StorageLocationService<T> extends BaseService<T> {

	boolean checkWarehouse(String cellValue,StorageLocation storageLocation);

	void addBatch(List<StorageLocation> storageLocationList);

	QueryResult<StorageLocation> findRecord(StorageLocationQueryParam queryParam);

	void deleteListId(List<String> idList);

	boolean checkUpdateBf(StorageLocationQueryParam queryParam);

	void update(StorageLocation entity);

	boolean checkBtachinsertBf(StorageLocation storageLocation);

	String checkRepeat(List<StorageLocation> storageLocationList);

	QueryResult<StorageLocation> findRecordNoCount(StorageLocationQueryParam queryParam);
}
