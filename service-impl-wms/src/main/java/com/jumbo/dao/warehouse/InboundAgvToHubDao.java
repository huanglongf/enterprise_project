package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.InboundAgvToHub;

@Transactional
public interface InboundAgvToHubDao extends GenericEntityDao<InboundAgvToHub, Long> {

    @NamedQuery
    public List<InboundAgvToHub> findAllByErrorCount();

    @NativeQuery
    List<InboundAgvToHub> inboundAgvToHubByApiName(@QueryParam("staId") Long staId, @QueryParam("apiName") String apiName, RowMapper<InboundAgvToHub> beanPropertyRowMapper);

}
