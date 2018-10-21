package com.bt.lmis.service;

import java.util.Map;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.controller.form.DiffBilldeatilsQueryParam;
import com.bt.lmis.controller.form.ExpressbillDetailQueryParam;
import com.bt.lmis.model.ExpressbillDetail;

public interface ExpressbillDetailService<T> extends BaseService<T> {

	void deleteByMaster_id(String id);

	void deleteByBat_id(String batch_id);
	
	void diff(Map<String,String> param);

	int checkToDiff(ExpressbillDetailQueryParam query);

	void deleteVerification(ExpressbillDetailQueryParam query);


}
