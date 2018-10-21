package com.bt.lmis.service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.controller.form.ExpressbillBatchmasterQueryParam;
import com.bt.lmis.model.ExpressbillBatchmaster;
import com.bt.lmis.page.QueryResult;


public interface ExpressbillBatchmasterService<T> extends BaseService<T> {


	void deleteByMaster_id(String id);

	QueryResult<ExpressbillBatchmaster> selectExpressBillBatch(ExpressbillBatchmasterQueryParam queryParam);

	void insertDetail();

	void transfer(ExpressbillBatchmasterQueryParam queryParam);

	void checkTemp(ExpressbillBatchmasterQueryParam qureyParam);

	JSONObject checkFlow(ExpressbillBatchmasterQueryParam queryParam, String string);

}
