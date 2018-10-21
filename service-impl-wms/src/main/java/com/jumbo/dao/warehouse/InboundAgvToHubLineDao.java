package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.InboundAgvToHubLine;

@Transactional
public interface InboundAgvToHubLineDao extends GenericEntityDao<InboundAgvToHubLine, Long> {


    @NamedQuery
    public List<InboundAgvToHubLine> findAllByInAgvId(@QueryParam("id") Long id);
}
