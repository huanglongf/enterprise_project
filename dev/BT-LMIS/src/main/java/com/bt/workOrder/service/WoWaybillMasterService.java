package com.bt.workOrder.service;

import java.util.List;
import java.util.Map;

import com.bt.common.controller.param.Parameter;
import com.bt.lmis.page.QueryResult;

public interface WoWaybillMasterService {

    QueryResult<Map<String, Object>> search(Parameter parameter);
    
    List<String> getWarehouseName();
    List<String> getStoreName();
    List<String> getTransportName();

}