package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.threepl.CnSkuInfo;

@Transactional
public interface CnSkuInfoDao extends GenericEntityDao<CnSkuInfo, Long> {

    @NamedQuery
    List<CnSkuInfo> getCnSkuInfoListByWaiting();

    @NamedQuery
    CnSkuInfo getCnSkuInfoListByVersion(@QueryParam("ownerUserId") String ownerUserId, @QueryParam("itemId") String itemId, @QueryParam("itemVersion") Long itemVersion);

    @NamedQuery
    List<CnSkuInfo> getCnSkuInfoListByItemid(@QueryParam("ownerUserId") String ownerUserId, @QueryParam("itemId") String itemId);

    @NamedQuery
    CnSkuInfo getCnSkuInfoBySkuCode(@QueryParam("skuCode") String skuCode);
}
