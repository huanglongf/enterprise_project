package com.jumbo.dao.warehouse;

import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.PackageSkuLog;

/**
 * 套装组合商品数据库操作
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface PackageSkuLogDao extends GenericEntityDao<PackageSkuLog, Long> {
    /**
     * 根据packageSKuId插入新的packageSkuLog
     * 
     * @param packageSkuId
     */
    @NativeUpdate
    void newPackageSkuLog(@QueryParam("psId") Long packageSkuId);

}
