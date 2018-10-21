package com.jumbo.dao.command;

import java.util.Date;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.ConsumptiveMaterialUseQueryCommand;

/**
 * 耗材使用情况查询DAO
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface ConsumptiveMaterialUseQueryCommandDao extends GenericEntityDao<ConsumptiveMaterialUseQueryCommand, Long> {
    /**
     * 根据条件查询耗材使用情况
     * 
     * @param start
     * @param pageSize
     * @param date
     * @param date2
     * @param slipCode
     * @param recommandSku
     * @param usedSku
     * @param isMatch
     * @param checkUser
     * @param outboundUser
     * @param id
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<ConsumptiveMaterialUseQueryCommand> findCmUseList(int start, int pageSize, @QueryParam("fromDate") Date date, @QueryParam("endDate") Date date2, @QueryParam("slipCode") String slipCode, @QueryParam("recommandSku") String recommandSku,
            @QueryParam("usedSku") String usedSku, @QueryParam("isMatch") String isMatch, @QueryParam("checkUser") String checkUser, @QueryParam("outboundUser") String outboundUser, @QueryParam("ouId") Long id, Sort[] sorts,
            BeanPropertyRowMapper<ConsumptiveMaterialUseQueryCommand> beanPropertyRowMapper);

}
