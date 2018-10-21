package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;

import com.jumbo.wms.model.hub2wms.WmsOrderInvoiceOms;

public interface WmsOrderInvoiceOmsDao extends GenericEntityDao<WmsOrderInvoiceOms, Long> {
    @NativeQuery
    public List<WmsOrderInvoiceOms> findOrderId(@QueryParam(value = "orderId") Long orderId, RowMapper<WmsOrderInvoiceOms> rowMapper);

    @NativeUpdate
    void updateOrderInvoiceByOrderStatus(@QueryParam(value = "orderStatus") Long orderStatus, @QueryParam(value = "newOrderStatus") Long newOrderStatus);
}
