package com.jumbo.dao.vmi.warehouse;


import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutAdditionalLine;

@Transactional
public interface MsgRtnOutAdditionalLineDao extends GenericEntityDao<MsgRtnOutAdditionalLine, Long> {

    @NamedQuery
    List<MsgRtnOutAdditionalLine> getLineListByMsgRtnOutbound(@QueryParam("msgRtnOutboundId") Long msgRtnOutboundId);
}
