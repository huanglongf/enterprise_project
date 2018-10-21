package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WxConfirmOrderQueue;

@Transactional
public interface WXConfirmOrderQueueDao extends GenericEntityDao<WxConfirmOrderQueue, Long> {

    @NamedQuery
    public List<WxConfirmOrderQueue> findExtOrder(@QueryParam("count") Long count);
    

    /**
     * 根据运单号 获取万象中间表数据
     * 
     * @param trackingNo
     * @return
     */
    @NamedQuery
    WxConfirmOrderQueue getWxOrderQueurLogByTrackingNo(@QueryParam("trackingNo") String trackingNo);

    // @NativeQuery
    // List<WxConfirmOrderQueue> findWxOrderByStaCode(@QueryParam("staCode") String staCode,
    // RowMapper<WxConfirmOrderQueue> r);

}
