package com.jumbo.dao.vmi.converseData;



import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.converseData.ConverseProductInfo;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface ConverseProductInfoDao extends GenericEntityDao<ConverseProductInfo, Long> {

    @NamedQuery
    ConverseProductInfo findBySkuCode(@QueryParam("SkuCode") String SkuCode);
}
