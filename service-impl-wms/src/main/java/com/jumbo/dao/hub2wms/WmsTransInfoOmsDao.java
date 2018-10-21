package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.WmsTransInfoOms;

@Transactional
public interface WmsTransInfoOmsDao extends GenericEntityDao<WmsTransInfoOms, Long> {
    @NativeQuery
    public List<WmsTransInfoOms> findOrderId(@QueryParam(value = "orderId") Long orderId, RowMapper<WmsTransInfoOms> rowMapper);

    @NativeUpdate
    void updateTransInfoByOrderStatus(@QueryParam(value = "orderStatus") Long orderStatus, @QueryParam(value = "newOrderStatus") Long newOrderStatus);

    @NativeUpdate
    void updatePreOrderTransInfoByOrderCode(@QueryParam(value = "orderCode") String orderCode, @QueryParam(value = "transNo") String transNo);

    @NativeQuery
    public List<WmsTransInfoOms> findOrderById(@QueryParam(value = "orderCode") String orderCode, RowMapper<WmsTransInfoOms> rowMapper);
}
