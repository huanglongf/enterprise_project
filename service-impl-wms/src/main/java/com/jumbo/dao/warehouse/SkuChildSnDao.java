package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.SkuChildSn;
import com.jumbo.wms.model.warehouse.SkuSnStatus;

@Transactional
public interface SkuChildSnDao extends GenericEntityDao<SkuChildSn, Long> {
    /**
     * 根据商品ID和SN号查询
     * 
     * @param skuId
     * @param sn
     * @return
     */
    @NamedQuery
    public List<SkuChildSn> getbySkuId(@QueryParam(value = "skuId") Long skuId, @QueryParam(value = "sn") String sn);

    @NamedQuery
    public SkuChildSn getbyStatusSn(@QueryParam(value = "sn") String sn, @QueryParam(value = "skuId") Long skuId, @QueryParam(value = "status") SkuSnStatus status);

}
