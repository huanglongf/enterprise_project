package com.jumbo.dao.command;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.StaDeliverCommand;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface StaDeliverCommandDao extends GenericEntityDao<StaDeliverCommand, String> {
    /**
     * 查询该配货批次下面的相关单据与物流面单对应关系
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StaDeliverCommand> findAllStaByPickingListId(@QueryParam("id") Long id, BeanPropertyRowMapper<StaDeliverCommand> beanPropertyRowMapper);

}
