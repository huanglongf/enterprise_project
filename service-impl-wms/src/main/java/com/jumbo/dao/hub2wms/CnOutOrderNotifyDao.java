package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.threepl.CnOutOrderNotify;

@Transactional
public interface CnOutOrderNotifyDao extends GenericEntityDao<CnOutOrderNotify, Long> {

    @NamedQuery
    List<CnOutOrderNotify> getNewCnOutOrderNotify();

    @NamedQuery
    CnOutOrderNotify getCnOutOrderNotifyByOrderCode(@QueryParam("orderCode") String orderCode);
}
