package com.jumbo.dao.vmi.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderLineCommand;

@Transactional
public interface MsgOutboundOrderLineDao extends GenericEntityDao<MsgOutboundOrderLine, Long> {


    @NativeQuery
    List<MsgOutboundOrderLineCommand> findeMsgOutLintBymsgOrderId(@QueryParam("msgOrderId") Long msgOrderId, RowMapper<MsgOutboundOrderLineCommand> rowMapper);

    @NamedQuery
    List<MsgOutboundOrderLine> findeMsgOutLintBymsgOrderId2(@QueryParam("msgOrderId") Long msgOrderId);


    @NamedQuery
    List<Object[]> findeMsgOutLintBymsgOrderId3(@QueryParam("msgOrderId") Long msgOrderId);

    @NativeQuery
    List<MsgOutboundOrderLineCommand> findMsgoutLineByMsgId(@QueryParam("msgId") Long msgId, RowMapper<MsgOutboundOrderLineCommand> rowMapper);

    /**
     * @param ids
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<MsgOutboundOrderLineCommand> findMsgDataByOrderIds(@QueryParam("ids") List<Long> ids, RowMapper<MsgOutboundOrderLineCommand> rowMapper);
}
