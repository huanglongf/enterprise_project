package com.bt.workOrder.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.common.controller.param.Parameter;
import com.bt.workOrder.bean.WoFollowupResultinfo;

public interface WoFollowupResultinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WoFollowupResultinfo record);

    List<String> selectAll(@Param("wkType")String wkType,@Param("errorType")String errorType);
    /**
     * @param parameter
     * @return
     */
    List<Map<String, Object>> query(Parameter parameter);

    /**
     * @param parameter
     * @return
     */
    int count(Parameter parameter);
}