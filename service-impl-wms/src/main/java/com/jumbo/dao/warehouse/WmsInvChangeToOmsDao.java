package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WmsInvChangeToOms;

/**
 * wms库存变更处理数据库接口
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface WmsInvChangeToOmsDao extends GenericEntityDao<WmsInvChangeToOms, Long> {
    /**
     * 查询需要执行的数据列表
     * 
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<WmsInvChangeToOms> findAllNeedDataList(BeanPropertyRowMapper<WmsInvChangeToOms> beanPropertyRowMapper);


}
