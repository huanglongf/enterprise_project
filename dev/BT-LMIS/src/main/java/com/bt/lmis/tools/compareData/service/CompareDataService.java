package com.bt.lmis.tools.compareData.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bt.lmis.tools.compareData.model.WhsTask;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.fastjson.JSONObject;
import com.bt.common.controller.param.Parameter;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.page.QueryResult;

import javax.servlet.http.HttpServletRequest;

public interface CompareDataService<T> extends BaseService<T> {

	List<Map<String, Object>> importPackData(Workbook wk);

	List<Map<String, Object>> importCollectData(Workbook wk);
	 /**
     * 根据条件分页查询
     * @return
     */
    QueryResult<Map<String, Object>> queryCompareData(Parameter parameter);

	String getSequence(String tlsWhsTaskCode);
    
    int deleteCompareTaskById(String id,String taskCode);

	JSONObject compared(String taskCode,String empName);

	ArrayList<Map<String, Object>> exportCollectPackData(String taskCode);

    Map<String,Object> creatCompareTask(HttpServletRequest req, WhsTask whsTask,String packPath, String collectPath);
}
