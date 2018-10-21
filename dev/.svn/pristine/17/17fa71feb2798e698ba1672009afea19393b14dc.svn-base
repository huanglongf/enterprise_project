package com.bt.lmis.tools.whsTempData.dao;

import com.bt.common.controller.param.Parameter;
import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.tools.whsTempData.model.WhsTempData;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Repository
public interface WhsTempDataMapper<T> extends BaseMapper<T>{

    public List<Map<String, Object>> queryWhsTempData(Parameter parameter);

    public int countWhsTempData(Parameter parameter);

    int deleteBybatchId (String batchId);
    int selectBybatchId(String batchId);
    
    int createBatch (List<WhsTempData> WTDList);

	public ArrayList<Map<String, Object>> exportWhsTempData(Parameter parameter);
    
    
}
