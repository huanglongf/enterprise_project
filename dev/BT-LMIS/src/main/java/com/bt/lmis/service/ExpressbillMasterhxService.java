package com.bt.lmis.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.controller.form.ExpressbillMasterhxQueryParam;
import com.bt.lmis.model.ExpressbillMasterhx;
import com.bt.lmis.page.QueryResult;

public interface ExpressbillMasterhxService<T> extends BaseService<T> {

	void updateById(String id, String file_name);

	QueryResult<ExpressbillMasterhx> findAll(ExpressbillMasterhxQueryParam queryParam);

	void saveClose(Map<String, String> param) throws NumberFormatException, Exception;

	void deleteClose(Map<String, Object> param) throws NumberFormatException, Exception;

	ExpressbillMasterhx selectByExpressbillMasterhxId(String id);

}
