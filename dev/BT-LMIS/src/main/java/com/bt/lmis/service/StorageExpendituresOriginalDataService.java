package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.controller.form.StorageExpendituresOriginalDataQueryParam;
import com.bt.lmis.model.ExpressReturnStorage;
import com.bt.lmis.model.StorageExpendituresOriginalData;
import com.bt.lmis.page.QueryResult;

public interface StorageExpendituresOriginalDataService<T> extends BaseService<T> {

	QueryResult<StorageExpendituresOriginalData> getPageData(StorageExpendituresOriginalDataQueryParam queryParam);

	List<Map<String, Object>> getListMap(StorageExpendituresOriginalDataQueryParam queryParam);

}
