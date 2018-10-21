package com.jumbo.dao.vmi.philipsData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.philipsData.PhilipsOutboundDeliveryLine;

@Transactional
public interface PhilipsOutboundDeliveryLineDao extends GenericEntityDao<PhilipsOutboundDeliveryLine, Long> {

    @NativeQuery
    public List<PhilipsOutboundDeliveryLine> getOutboundLineByOutId(@QueryParam("outboundId") Long outboundId);
}
