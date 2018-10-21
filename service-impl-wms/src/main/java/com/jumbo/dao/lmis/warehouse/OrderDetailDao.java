package com.jumbo.dao.lmis.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.lmis.OrderDetail;

@Transactional
public interface OrderDetailDao extends GenericEntityDao<OrderDetail, Long> {
    @NativeQuery(model = OrderDetail.class)
    List<OrderDetail> findOrderDetailsByIds(@QueryParam("skuIds") List<Long> skuIds, @QueryParam("trackingNo") String trackingNo, @QueryParam("staId") Long staId);

    @NativeQuery(model = OrderDetail.class)
    List<OrderDetail> findCartonDetialOrderDetailsByIds(@QueryParam("trackingNo") String trackingNo, @QueryParam("staId") Long staId);
}
