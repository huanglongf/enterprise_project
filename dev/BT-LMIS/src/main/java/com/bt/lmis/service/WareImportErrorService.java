package com.bt.lmis.service;

import com.bt.lmis.controller.form.WareImportErrorQueryParam;
import com.bt.lmis.model.WareImportError;
import com.bt.lmis.page.QueryResult;

public interface WareImportErrorService {
    int deleteByPrimaryKey(Integer id);

    int insert(WareImportError record);

    int insertSelective(WareImportError record);

    WareImportError selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WareImportError record);

    int updateByPrimaryKey(WareImportError record);

	QueryResult<WareImportErrorQueryParam> getList(WareImportErrorQueryParam wareImportErrorQueryParam);
}