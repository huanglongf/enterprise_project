package com.jumbo.dao.expressRadar;


import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.expressRadar.RadarTimeRuleCommand;
import com.jumbo.wms.model.expressRadar.RadarTimeRule;


@Transactional
public interface RadarTimeRuleDao extends GenericEntityDao<RadarTimeRule, Long> {

    @NativeQuery
    List<RadarTimeRuleCommand> findPhyWarehouseByLpcode(@QueryParam(value = "id") Long id, RowMapper<RadarTimeRuleCommand> rowMapper);

    /*@NativeQuery
    RadarTimeRule findRadarTimeRuleByPwhIdAreaId(@QueryParam(value = "expressPwhId") Long expressPwhId, @QueryParam(value = "sraId") Long sraId, @QueryParam(value = "tsId") Long tsId, @QueryParam(value = "timeType") Integer timeType,
            RowMapper<RadarTimeRule> rowMapper);*/
    
    @NativeQuery
    List<RadarTimeRule> findRadarTimeRuleByPwhIdAreaId(@QueryParam(value = "expressPwhId") Long expressPwhId, @QueryParam(value = "sraId") Long sraId, @QueryParam(value = "tsId") Long tsId, @QueryParam(value = "timeType") Integer timeType,
            RowMapper<RadarTimeRule> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<RadarTimeRuleCommand> findRadarTimeRule(int start, int pagesize, @QueryParam(value = "lpcode") String lpcode, @QueryParam(value = "wlService") Integer wlService, @QueryParam(value = "dateTimeType") Integer dateTimeType,
            @QueryParam(value = "fjWh") Long fjWh, @QueryParam(value = "province") String province, @QueryParam(value = "city") String city, RowMapper<RadarTimeRuleCommand> r, Sort[] sorts);

    @NativeQuery
    RadarTimeRuleCommand getRadarTimeRuleById(@QueryParam(value = "id") Long id, RowMapper<RadarTimeRuleCommand> rowMapper);

    @NativeQuery
    RadarTimeRuleCommand checkRadarTimeRule(@QueryParam(value = "pwhid") Long pwhid, @QueryParam(value = "sraid") Long sraid, @QueryParam(value = "tsid") Long tsid, RowMapper<RadarTimeRuleCommand> rowMapper);
}
