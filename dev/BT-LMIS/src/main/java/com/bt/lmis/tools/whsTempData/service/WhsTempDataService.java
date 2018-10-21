package com.bt.lmis.tools.whsTempData.service;

import com.bt.common.controller.param.Parameter;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.Employee;
import com.bt.lmis.page.QueryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

public interface WhsTempDataService<T> extends BaseService<T> {
    /**
     * 根据条件分页查询
     * @return
     */
    QueryResult<Map<String, Object>> queryWhsTempData(Parameter parameter);

    /**
     * 跟据批次ID删除数据（物理删除）
     * @param batchId
     * @return
     */
    int deleteBybatchId(String batchId);
    
    int selectBybatchId(String batchId);

	List<Map<String, Object>> importWhsTempData(Workbook wk, Employee user, String batchId);

	void addSequence(String string);
	
	String getSequence(String date);

	ArrayList<Map<String, Object>> exportWhsTempData(Parameter parameter);
}
