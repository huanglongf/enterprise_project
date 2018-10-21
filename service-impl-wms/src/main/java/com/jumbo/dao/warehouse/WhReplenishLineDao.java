package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WhReplenishLine;
import com.jumbo.wms.model.warehouse.WhReplenishLineCommand;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface WhReplenishLineDao extends GenericEntityDao<WhReplenishLine, Long> {
    /**
     * 根据补货申请id查看补货报表明细
     * 
     * @param wrId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<WhReplenishLineCommand> findWhReplenishLienById(@QueryParam("wrId") Long wrId, BeanPropertyRowMapper<WhReplenishLineCommand> beanPropertyRowMapper);

}
