package com.jumbo.dao.vmi.cch;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.cch.CacheProductData;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface CacheProductDataDao extends GenericEntityDao<CacheProductData, Long> {

    @NamedQuery
    CacheProductData getCCHCPBySkuCode(@QueryParam("skuCode") String skuCode);

    @NativeUpdate
    int updateCchCPStatusByBarCode(@QueryParam("newStatus") Integer newStatus, @QueryParam("skuCode") String skuCode);
}
