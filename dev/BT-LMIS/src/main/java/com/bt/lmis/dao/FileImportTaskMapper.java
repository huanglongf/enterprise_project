package com.bt.lmis.dao;

import java.util.HashMap;
import java.util.List;

import com.bt.lmis.api.model.Order_test;
import com.bt.lmis.controller.form.FileImportTaskQueryParam;
import com.bt.lmis.controller.form.WareParkQueryParam;
import com.bt.lmis.model.ExpressRawData;
import com.bt.lmis.model.FileImportTask;

public interface FileImportTaskMapper {
    int deleteByPrimaryKey(FileImportTask fileImportTask);

    int insert(FileImportTask record);

    int insertSelective(FileImportTask record);

    FileImportTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FileImportTask record);

    int updateByPrimaryKey(FileImportTask record);

	FileImportTaskQueryParam getByQueryParam(FileImportTaskQueryParam queryParam);

	List<FileImportTaskQueryParam> getList(FileImportTaskQueryParam queryParam);

	int getListCount(FileImportTaskQueryParam queryParam);

	int checkTaskCount();

	int updateJkSettleOrder(ExpressRawData expressRawData);

	int convertOrderAddExcel(Order_test order_test);

	HashMap<String,String> callConvertOrder(HashMap<String,Object> paramMap);

	List<Order_test> findOrderConvertResultList(HashMap<String, Object> paramMap);
}