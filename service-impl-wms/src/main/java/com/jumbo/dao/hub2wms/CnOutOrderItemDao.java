package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.threepl.CnOutOrderItem;

@Transactional
public interface CnOutOrderItemDao extends GenericEntityDao<CnOutOrderItem, Long> {

    @NamedQuery
    List<CnOutOrderItem> getCnOutOrderItemByNotifyId(@QueryParam(value = "notifyId") Long notifyId);
}
