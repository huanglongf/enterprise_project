package com.jumbo.dao.vmi.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrderLineCommand;

@Transactional
public interface MsgInboundOrderLineDao extends GenericEntityDao<MsgInboundOrderLine, Long> {

    @NativeQuery
    List<MsgInboundOrderLineCommand> findVmiMsgInboundOrderLine(@QueryParam(value = "msgId") Long msgId, RowMapper<MsgInboundOrderLineCommand> rowMapper);

    @NamedQuery
    List<MsgInboundOrderLine> fomdMsgInboundOrderLineByOId(@QueryParam(value = "msgLineId") Long msgLineId);


}
