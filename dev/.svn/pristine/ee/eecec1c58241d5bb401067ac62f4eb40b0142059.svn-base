package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.StorageLocationQueryParam;
import com.bt.lmis.model.StorageLocation;

/**
* @ClassName: StorageLocationMapper
* @Description: TODO(StorageLocationMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface StorageLocationMapper<T> extends BaseMapper<T> {

	List<StorageLocation> findRecords(Map<String, String> param);

	void addBatch(List<StorageLocation> storageLocationList);

	List<StorageLocation> findAll(StorageLocationQueryParam queryParam);

	int Count(StorageLocationQueryParam queryParam);

	void deleteListId(List<String> idList);

	int checkUpdateBf(StorageLocationQueryParam queryParam);

	void updateById(StorageLocation entity);

	int checkBtachinsertBf(StorageLocation storageLocation);

	void truncat();

	void deleteListIdRepeat(List<StorageLocation> storageLocationList);

	List<Map<String,Object>> getSame();
	
	
}
