package com.jumbo.dao.warehouse;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.QueueStaDeliveryInfo;

@Transactional
public interface QueueStaDeliveryInfoDao extends GenericEntityDao<QueueStaDeliveryInfo, Long> {
    @NativeQuery
    public QueueStaDeliveryInfo queryDeliveryInfo(@QueryParam("staid") Long staid, RowMapper<QueueStaDeliveryInfo> beanPropertyRowMapper);

    /**
     * 删除数据
     * 
     * @param id
     */
    @NativeUpdate
    void cleanDataByStaId(@QueryParam("id") Long id);

}
