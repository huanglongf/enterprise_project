package com.jumbo.dao.hub2wms;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.threepl.WmsOrderStatusUpload;

@Transactional
public interface WmsOrderStatusUploadDao extends GenericEntityDao<WmsOrderStatusUpload, Long> {
    /**
     * 通过订单号查询订单相关状态，并做状态映射转换
     * 
     * @param orderCode
     * @return
     */
    @NativeQuery
    WmsOrderStatusUpload getStatusByOrderCode(@QueryParam(value = "orderCode") String orderCode);
}
