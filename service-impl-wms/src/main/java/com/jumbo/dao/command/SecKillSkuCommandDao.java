package com.jumbo.dao.command;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.SecKillSkuCommand;
/**
 * 
 * @author jinlong.ke
 *
 */
@Transactional
public interface SecKillSkuCommandDao extends GenericEntityDao<SecKillSkuCommand, Long> {
    /**
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<SecKillSkuCommand> selectAllSecKillSkuByOuId(@QueryParam("id") Long id,Sort[] sorts, BeanPropertyRowMapper<SecKillSkuCommand> beanPropertyRowMapper);
}
