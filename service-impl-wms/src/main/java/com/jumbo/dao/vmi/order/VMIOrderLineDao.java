package com.jumbo.dao.vmi.order;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.order.VMIOrderLine;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface VMIOrderLineDao extends GenericEntityDao<VMIOrderLine, Long> {

    @NamedQuery
    public List<VMIOrderLine> findByCode(@QueryParam("code") String code);
}
