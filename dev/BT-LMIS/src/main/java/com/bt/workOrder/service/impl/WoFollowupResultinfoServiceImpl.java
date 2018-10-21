package com.bt.workOrder.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.common.controller.param.Parameter;
import com.bt.lmis.page.QueryResult;
import com.bt.workOrder.bean.WoFollowupResultinfo;
import com.bt.workOrder.dao.WoFollowupResultinfoMapper;
import com.bt.workOrder.service.WoFollowupResultinfoService;

@Service
public class WoFollowupResultinfoServiceImpl implements WoFollowupResultinfoService{
    
    @Autowired
    private WoFollowupResultinfoMapper mapper;
    
    public int deleteByPrimaryKey(Integer id){
        return mapper.deleteByPrimaryKey(id);
    }

    public int insert(WoFollowupResultinfo record){
        return mapper.insert(record);
    }
    
    public QueryResult<Map<String, Object>> query(Parameter parameter){
        QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
        qr.setResultlist(mapper.query(parameter));
        qr.setTotalrecord(mapper.count(parameter));
        return qr;
    }

    @Override
    public List<String> selectAll(String wkType,String errorType){
        return mapper.selectAll(wkType, errorType);
    }

}