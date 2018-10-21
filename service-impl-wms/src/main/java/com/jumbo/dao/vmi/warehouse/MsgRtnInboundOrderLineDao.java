package com.jumbo.dao.vmi.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderLineCommand;

@Transactional
public interface MsgRtnInboundOrderLineDao extends GenericEntityDao<MsgRtnInboundOrderLine, Long> {

    @NativeUpdate
    void updateSkuId(@QueryParam(value = "rtnInId") Long rtnInId, @QueryParam(value = "skuId") Long skuId);

    @NamedQuery
    List<MsgRtnInboundOrderLine> findRtnOrderLineByRId(@QueryParam(value = "msgRtnLineId") Long rtnInId);

    @NativeUpdate
    void updateRtnMsgIdById(@QueryParam(value = "msgId") Long msgId, @QueryParam(value = "msgLineId") Long msgLineId);

    @NativeUpdate
    void saveByIDSRECLine(@QueryParam(value = "status") Integer status);

    @NativeQuery
    List<MsgRtnInboundOrderLineCommand> findstaLinBymsgInboundId(@QueryParam("stacode") String stacode,@QueryParam("source") String source, @QueryParam(value = "msgid") Long msgid, RowMapper<MsgRtnInboundOrderLineCommand> rowMapper);

    @NativeQuery
    MsgRtnInboundOrderLine findRtnOrderLineByStaCodeAndSkuCode(@QueryParam(value = "staCode") String staCode, @QueryParam(value = "skuCode") String skuCode, RowMapper<MsgRtnInboundOrderLine> rowMapper);

    @NativeQuery
    MsgRtnInboundOrderLine findRtnOrderLineByStaCodeAndSkuCode2(@QueryParam(value = "staCode") String staCode, @QueryParam(value = "skuCode") String skuCode, RowMapper<MsgRtnInboundOrderLine> rowMapper);

    @NativeQuery
    List<MsgRtnInboundOrderLine> findrtnLinByRtnInId(@QueryParam("rtnInId") Long rtnInId, Sort[] sorts, RowMapper<MsgRtnInboundOrderLine> rowMapper);


    @NativeUpdate
    void saveRtnInBoundLineFromTemplate(@QueryParam(value = "fbStatus") Integer fbStatus, @QueryParam(value = "goodInvStatus") Long goodInvStatus, @QueryParam(value = "defectiveInvStatus") Long defectiveInvStatus);

    @NamedQuery
    MsgRtnInboundOrderLine findRtnLineBySlipCodeAndSku(@QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "skuBarCode") String skuBarCode, @QueryParam(value = "invStatus") String invStatus);

    @NativeUpdate
    void updateSkuIdandCode(@QueryParam(value = "rtnInId") Long rtnInId, @QueryParam(value = "skuCode") String skuCode, @QueryParam(value = "skuId") Long skuId);

    @NativeQuery
    List<MsgRtnInboundOrderLine> findMsgRtnLinSkuisNull(RowMapper<MsgRtnInboundOrderLine> rowMapper);

    @NativeQuery
    List<MsgRtnInboundOrderLine> findInboundOrderLines(@QueryParam(value = "rtnInId") Long rtnInId, RowMapper<MsgRtnInboundOrderLine> rowMapper);

    @NativeQuery
    MsgRtnInboundOrderLine findMsgInLineBySkuCode(@QueryParam("batchId") String batchId, @QueryParam("skuCode") String skuCode, RowMapper<MsgRtnInboundOrderLine> rowMapper);
}
