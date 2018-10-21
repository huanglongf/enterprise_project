package com.bt.workOrder.service;

import java.util.List;
import java.util.Map;

import com.bt.common.controller.param.Parameter;
import com.bt.lmis.page.QueryResult;
import com.bt.workOrder.bean.WoFollowupResultinfo;

public interface WoFollowupResultinfoService {
    int deleteByPrimaryKey(Integer id);

    int insert(WoFollowupResultinfo record);

    QueryResult<Map<String, Object>> query(Parameter parameter);
    
    List<String> selectAll(String wkType,String errorType);
}