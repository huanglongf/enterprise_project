package com.jumbo.dao.command;

import java.math.BigDecimal;
import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.ReplenishSkuCommand;

@Transactional
public interface ReplenishSkuCommandDao extends GenericEntityDao<ReplenishSkuCommand, Long> {
    /**
     * 查询配货失败缺货商品信息
     * 
     * @param ouId
     * @param isShare
     * @param beanPropertyRowMapper
     */
    @NativeQuery
    List<ReplenishSkuCommand> findRePlenishSkuByOuid(@QueryParam("ouId") Long ouId, @QueryParam("isShare") boolean isShare, BeanPropertyRowMapper<ReplenishSkuCommand> beanPropertyRowMapper);

    /**
     * 查询库内补货缺货商品信息
     * 
     * @param ouId
     * @param b
     * @param bigDecimal 
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<ReplenishSkuCommand> findNeedReplenishSku(@QueryParam("ouId") Long ouId, @QueryParam("isShare") boolean b, @QueryParam("warnPre")BigDecimal bigDecimal, BeanPropertyRowMapper<ReplenishSkuCommand> beanPropertyRowMapper);


}
