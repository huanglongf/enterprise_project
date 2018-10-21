package com.bt.workOrder.dao;

import java.util.List;
import java.util.Map;

import com.bt.common.controller.param.Parameter;

public interface WoWaybillMasterMapper {

    List<Map<String, Object>> search(Parameter parameter);

    int countSearch(Parameter parameter);

//    List<Map<String, Object>> exportReport(Parameter parameter);
    
    List<String> getWarehouseName();
    List<String> getStoreName();
    List<String> getTransportName();

}