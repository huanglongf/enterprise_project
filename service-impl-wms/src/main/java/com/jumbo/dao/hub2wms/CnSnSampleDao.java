package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.threepl.CnSnSample;

@Transactional
public interface CnSnSampleDao extends GenericEntityDao<CnSnSample, Long> {
    @NamedQuery
    List<CnSnSample> getCnSnSampleByCnSkuInfo(@QueryParam(value = "skuInfoId") Long skuInfoId);
}
