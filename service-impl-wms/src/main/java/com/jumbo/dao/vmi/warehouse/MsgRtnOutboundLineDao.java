package com.jumbo.dao.vmi.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundLineCommand;

@Transactional
public interface MsgRtnOutboundLineDao extends GenericEntityDao<MsgRtnOutboundLine, Long> {
    @NativeUpdate
    void updateMsgIdById(@QueryParam("msgId") long msgId, @QueryParam("msgLineId") long lineId);

    @NativeQuery
    MsgRtnOutboundLine findMsgOutBoundLineByStaCodeAndSkuCode(@QueryParam("staCode") String staCode, @QueryParam("skuCode") String skuCode, RowMapper<MsgRtnOutboundLine> beanPropertyRowMapper);

    @NativeQuery
    List<MsgRtnOutboundLineCommand> findByMsgRtnOutboundId(@QueryParam("msgid") Long msgid, RowMapper<MsgRtnOutboundLineCommand> r);

    @NativeQuery
    List<MsgRtnOutboundLineCommand> findByMsgRtnOutboundByStaCode(@QueryParam("staCode") String staCode, RowMapper<MsgRtnOutboundLineCommand> r);

    @NamedQuery
    List<MsgRtnOutboundLine> findMsgRtnOutboundLines(@QueryParam("msgid") Long msgid);

}
