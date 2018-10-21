package com.bt.workOrder.dao;

import com.bt.workOrder.bean.ReportRegion;

public interface ReportRegionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReportRegion record);

    int insertSelective(ReportRegion record);

    ReportRegion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReportRegion record);

    int updateByPrimaryKey(ReportRegion record);
}