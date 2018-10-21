package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.StorageExpendituresOriginalDataQueryParam;
import com.bt.lmis.model.StorageExpendituresOriginalData;

/**
* @ClassName: StorageExpendituresOriginalDataMapper
* @Description: TODO(StorageExpendituresOriginalDataMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface StorageExpendituresOriginalDataMapper<T> extends BaseMapper<T> {

	int getCount(StorageExpendituresOriginalDataQueryParam queryParam);

	List<StorageExpendituresOriginalData> getPageData(StorageExpendituresOriginalDataQueryParam queryParam);

	List<Map<String, Object>> getListMap(StorageExpendituresOriginalDataQueryParam queryParam);
	
	
}
