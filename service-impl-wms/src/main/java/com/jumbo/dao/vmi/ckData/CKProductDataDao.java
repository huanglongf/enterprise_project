package com.jumbo.dao.vmi.ckData;



import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.ckData.CKProductData;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface CKProductDataDao extends GenericEntityDao<CKProductData, Long> {

    @NamedQuery
    CKProductData getCKProductDataBySkuCode(@QueryParam(value = "skuCode") String skuCode);
}
