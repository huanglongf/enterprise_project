package com.bt.lmis.dao;

import java.util.List;

import com.bt.lmis.controller.form.WareImportTaskQueryParam;
import com.bt.lmis.model.WareImportTask;

public interface WareImportTaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(WareImportTask record);

    int insertSelective(WareImportTask record);

    WareImportTaskQueryParam selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WareImportTask record);

    int updateByPrimaryKey(WareImportTask record);

	List<WareImportTaskQueryParam> getList(WareImportTaskQueryParam wareImportTaskQueryParam);

	int getListCount(WareImportTaskQueryParam wareImportTaskQueryParam);
}