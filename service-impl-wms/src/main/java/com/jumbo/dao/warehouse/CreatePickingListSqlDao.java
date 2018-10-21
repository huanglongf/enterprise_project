package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.CreatePickingListSql;

@Transactional
public interface CreatePickingListSqlDao extends GenericEntityDao<CreatePickingListSql, Long> {

    @NativeQuery
    List<CreatePickingListSql> findRuleNameByOuId(@QueryParam("ouId") Long ouId, @QueryParam("ruleName") String ruleName, BeanPropertyRowMapper<CreatePickingListSql> beanPropertyRowMapper);

    @NativeQuery
    List<CreatePickingListSql> getAllRuleName(@QueryParam("ouId") Long ouId, @QueryParam("modeName") String modeName, BeanPropertyRowMapper<CreatePickingListSql> beanPropertyRowMapper);
}
