package com.bt.lmis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.controller.form.ExpressReturnStorageQueryParam;
import com.bt.lmis.model.ExpressReturnStorage;
import com.bt.lmis.model.ExpressReturnStorageExample;
import com.bt.lmis.page.QueryResult;

@Service
public interface ExpressReturnStorageService <T> extends BaseService<T>{
	
	QueryResult<ExpressReturnStorage> getPageData(ExpressReturnStorageQueryParam queryParam);

	
}
