package com.bt.lmis.service;

import com.bt.lmis.controller.form.FileImportTaskQueryParam;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.ExpressRawData;
import com.bt.lmis.model.FileImportTask;
import com.bt.lmis.page.QueryResult;

import java.util.List;
import java.util.Map;

public interface FileImportTaskService {

    int deleteByPrimaryKey(FileImportTask fileImportTask);

    int insert(FileImportTask record);

    int insertSelective(FileImportTask record);

    FileImportTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FileImportTask record);

    int updateByPrimaryKey(FileImportTask record);

	QueryResult<FileImportTaskQueryParam> findAll(FileImportTaskQueryParam queryParam);

	FileImportTaskQueryParam getByQueryParam(FileImportTaskQueryParam queryParam);

	int checkTaskCount();

	int updateJkSettleOrder(ExpressRawData expressRawData);

	void expressCoverImportExcel(List<String[]> list, FileImportTask fileImportTask, Employee user, 
			List<String> titleList, String string);

	int delImportTaskByIds(String ids, Employee user);
	
	/**
     * 退货匹配导入excel
     * */
	Map<String,Object> convertReturnExcel(List<String[]> list, FileImportTask fileImportTask, Employee user);

    /**
     * 全国报价新增订单导入excel
     * */
    Map<String,Object> convertOrderAddExcel(List<String[]> list, FileImportTask fileImportTask, Employee user, 
			List<String> titleList, String string);

	void expressinfoImportMatchExcel(List<String[]> list, FileImportTask fileImportTask, Employee user,
			List<String> titleList, String templeteName);

}
