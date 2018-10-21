package com.bt.workOrder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.workOrder.bean.ReportRegion;
import com.bt.workOrder.dao.ReportRegionMapper;
import com.bt.workOrder.service.ReportRegionService;
@Service
public class ReportRegionServiceImpl implements ReportRegionService {

	@Autowired
    private ReportRegionMapper mapper;

    @Override
    public ReportRegion selectByPrimaryKey(){
        int id = 1;
        return mapper.selectByPrimaryKey(id);
    }

}
