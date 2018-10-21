package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.OrderWarehousePriority;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface OrderWarehousePriorityDao extends GenericEntityDao<OrderWarehousePriority, Long> {

    /**
     * 根据订单查询仓库优先级
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<OrderWarehousePriority> getWarehouseByOrder(@QueryParam("orderId") Long id, BeanPropertyRowMapper<OrderWarehousePriority> beanPropertyRowMapper);

    /**
     * 根据订单更新默认仓游标
     * 
     * @param id
     */
    @NativeUpdate
    void updateFlagById(@QueryParam("orderId") Long id);

    /**
     * 删除数据
     * 
     * @param id
     */
    @NativeUpdate
    void cleanDataByOrderId(@QueryParam("id") Long id);

    /**
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<OrderWarehousePriority> getWarehouseAndLpByOrder(@QueryParam("id") Long id, BeanPropertyRowMapper<OrderWarehousePriority> beanPropertyRowMapper);

}
