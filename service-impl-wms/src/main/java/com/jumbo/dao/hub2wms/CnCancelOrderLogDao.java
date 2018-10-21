package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.threepl.CnCancelOrderLog;

@Transactional
public interface CnCancelOrderLogDao extends GenericEntityDao<CnCancelOrderLog, Long> {

    @NamedQuery
    List<CnCancelOrderLog> getCnCancelOrderLogByOrderCode(@QueryParam("orderCode") String orderCode);
}
