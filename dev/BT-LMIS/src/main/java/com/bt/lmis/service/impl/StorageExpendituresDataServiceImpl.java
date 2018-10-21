package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.StorageExpendituresDataMapper;
import com.bt.lmis.model.StorageExpendituresData;
import com.bt.lmis.service.StorageExpendituresDataService;
@Service
public class StorageExpendituresDataServiceImpl<T> extends ServiceSupport<T> implements StorageExpendituresDataService<T> {

	@Autowired
    private StorageExpendituresDataMapper<T> mapper;

	public StorageExpendituresDataMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<StorageExpendituresData> findDate(StorageExpendituresData storageExpendituresData) {
		return mapper.findDate(storageExpendituresData);
	}

	@Override
	public List<StorageExpendituresData> findDateSE(StorageExpendituresData storageExpendituresData) {
		// TODO Auto-generated method stub
		return mapper.findDateSE(storageExpendituresData);
	}

	@Override
	public Integer countDateSE(StorageExpendituresData storageExpendituresData) {
		// TODO Auto-generated method stub
		return mapper.countDateSE(storageExpendituresData);
	}
		
	
}
