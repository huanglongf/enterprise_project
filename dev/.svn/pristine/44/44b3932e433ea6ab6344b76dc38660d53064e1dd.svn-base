package com.bt.workOrder.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.common.CommonUtils;
import com.bt.common.controller.param.Parameter;
import com.bt.lmis.page.QueryResult;
import com.bt.workOrder.dao.WoWaybillMasterMapper;
import com.bt.workOrder.service.WoWaybillMasterService;

@Service
public class WoWaybillMasterServiceImpl implements WoWaybillMasterService{

    @Autowired
    private WoWaybillMasterMapper woWaybillMasterMapper;
    
    @Override
    public QueryResult<Map<String, Object>> search(Parameter parameter) {
        String[] temp = null;
        if(CommonUtils.checkExistOrNot(parameter.getParam().get("create_time"))) {
            temp = parameter.getParam().get("create_time").toString().split(" - ");
            parameter.getParam().put("create_time_start", temp[0]);
            parameter.getParam().put("create_time_end", temp[1]);
        }
        return new QueryResult<Map<String,Object>>(woWaybillMasterMapper.search(parameter), woWaybillMasterMapper.countSearch(parameter));
    }

    @Override
    public List<String> getWarehouseName(){
        return woWaybillMasterMapper.getWarehouseName();
    }

    @Override
    public List<String> getStoreName(){
        return woWaybillMasterMapper.getStoreName();
    }

    @Override
    public List<String> getTransportName(){
        return woWaybillMasterMapper.getTransportName();
    }

//    @Override
//    public List<Map<String, Object>> exportReport(Parameter parameter) {
//        String[] temp = null;
//        int timeLine = 1;
//        if(CommonUtils.checkExistOrNot(parameter.getParam().get("create_time"))) {
//            temp = parameter.getParam().get("create_time").toString().split(" - ");
//            parameter.getParam().put("create_time_start", temp[0]);
//            parameter.getParam().put("create_time_end", temp[1]);
//            timeLine = DateUtil.getDaySub(temp[0],temp[1])+1;
//        }
//        parameter.getParam().put("timeLine", timeLine);
//        return woPersonalReportMapper.exportReport(parameter);
//    }
}