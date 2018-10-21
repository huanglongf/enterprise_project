package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.threepl.CnInvCountReturnOrderItem;

@Transactional
public interface CnInvCountReturnOrderItemDao extends GenericEntityDao<CnInvCountReturnOrderItem, Long> {
    /**
     * 通过仓储编码查询盘点单商品明细
     * 
     * @param invCountId
     * @return
     */
    @NamedQuery
    List<CnInvCountReturnOrderItem> getByInvCountId(@QueryParam(value = "invCountId") long invCountId);
}
