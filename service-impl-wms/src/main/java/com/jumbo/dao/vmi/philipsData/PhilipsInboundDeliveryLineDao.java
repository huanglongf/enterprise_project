package com.jumbo.dao.vmi.philipsData;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.philipsData.PhilipsInboundDeliveryLine;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface PhilipsInboundDeliveryLineDao extends GenericEntityDao<PhilipsInboundDeliveryLine, Long> {

    @NativeQuery
    List<PhilipsInboundDeliveryLine> getLineByInboundDelId(@QueryParam("inboundId") Long inboundId, RowMapper<PhilipsInboundDeliveryLine> rowMapper);

    @NativeUpdate
    void updatePDLStaLine(@QueryParam("staLindId") Long staLindId, @QueryParam("inboundId") Long inboundId, @QueryParam("barCode") String barCode, @QueryParam("skuCode") String skuCode);

}
