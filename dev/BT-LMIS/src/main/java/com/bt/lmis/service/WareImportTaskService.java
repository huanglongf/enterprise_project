package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import com.bt.lmis.controller.form.WareImportTaskQueryParam;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.WareImportTask;
import com.bt.lmis.page.QueryResult;

public interface WareImportTaskService {
    int deleteByPrimaryKey(String id);

    int insert(WareImportTask record);

    int insertSelective(WareImportTask record);

    WareImportTaskQueryParam selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WareImportTask record);

    int updateByPrimaryKey(WareImportTask record);

	QueryResult<WareImportTaskQueryParam> getList(WareImportTaskQueryParam wareImportTaskQueryParam);

	int delByIds(String ids);

	Map<String, String> imoprtWarePark(List<String[]> list, WareImportTask wareImportTask, Employee user);
}