package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.WmsSalesOrderLog;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderLogCommand;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface WmsSalesOrderLogDao extends GenericEntityDao<WmsSalesOrderLog, Long> {

    @NativeQuery
    public List<WmsSalesOrderLog> findWmsSalesOrder(@QueryParam("rowNum") Long rownum, BeanPropertyRowMapper<WmsSalesOrderLog> beanPropertyRowMapper);

    @NativeQuery
    public WmsSalesOrderLogCommand findWmsSalesOrderCount(BeanPropertyRowMapper<WmsSalesOrderLogCommand> beanPropertyRowMapper);

    @NativeUpdate
    public void deleteWmsSalesOrderLogById();
}
