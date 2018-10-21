package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.StorageExpendituresOriginalDataQueryParam;
import com.bt.lmis.dao.StorageExpendituresOriginalDataMapper;
import com.bt.lmis.model.StorageExpendituresOriginalData;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.StorageExpendituresOriginalDataService;
@Service
public class StorageExpendituresOriginalDataServiceImpl<T> extends ServiceSupport<T> implements StorageExpendituresOriginalDataService<T> {

	@Autowired
    private StorageExpendituresOriginalDataMapper<T> storageExpendituresOriginalDataMapper;

	public StorageExpendituresOriginalDataMapper<T> getMapper() {
		return storageExpendituresOriginalDataMapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return storageExpendituresOriginalDataMapper.selectCount(param);
	}

	@Override
	public QueryResult<StorageExpendituresOriginalData> getPageData(StorageExpendituresOriginalDataQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<StorageExpendituresOriginalData> qr=new QueryResult<StorageExpendituresOriginalData>();
		qr.setResultlist(storageExpendituresOriginalDataMapper.getPageData(queryParam));
		qr.setTotalrecord(storageExpendituresOriginalDataMapper.getCount(queryParam));
		return qr;
	}

	@Override
	public List<Map<String, Object>> getListMap(StorageExpendituresOriginalDataQueryParam queryParam) {
		// TODO Auto-generated method stub
		return storageExpendituresOriginalDataMapper.getListMap(queryParam);
	}
		
	
}
