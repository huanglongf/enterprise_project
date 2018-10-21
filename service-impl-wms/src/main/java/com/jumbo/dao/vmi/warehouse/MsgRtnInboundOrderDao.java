package com.jumbo.dao.vmi.warehouse;


import java.util.Date;
import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderCommand;

@Transactional
public interface MsgRtnInboundOrderDao extends GenericEntityDao<MsgRtnInboundOrder, Long> {

    @NativeUpdate
    void updateOrderStauts(@QueryParam("rtnOrderId") Long shopId, @QueryParam("whstatus") int status);

    @NativeUpdate
    void updateOrderStautsByStaCode(@QueryParam("staCode") String sCode, @QueryParam("status") int status);

    @NativeQuery
    List<MsgRtnInboundOrder> findInboundByStatus(@QueryParam("Source") String Source, RowMapper<MsgRtnInboundOrder> rowMapper);

    @NativeQuery
    MsgRtnInboundOrder findInboundByStaCode(@QueryParam("staCode") String staCode, RowMapper<MsgRtnInboundOrder> rowMapper);

    @NamedQuery
    MsgRtnInboundOrder findInboundBySlipCode(@QueryParam("slipCode") String slipCode);

    @NativeUpdate
    void updateIlcshErrorInbound();

    @NativeUpdate
    void saveByIDSREC(@QueryParam("recStatus") Integer recStatus, @QueryParam("status") Integer status, @QueryParam("source") String source);

    /**
     * 查询当前库存
     * 
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<MsgRtnInboundOrderCommand> findMsgRtnInboundByPage(int start, int pageSize, @QueryParam("staCode") String staCode, @QueryParam("slipCode") String slipCode, @QueryParam("status") int status, @QueryParam("startDate") Date startDate,
            @QueryParam("endDate") Date endDate, @QueryParam("whId") Long whId, RowMapper<MsgRtnInboundOrderCommand> rowMapper, Sort[] sorts);

    /**
     * 查询当前库存
     * 
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<MsgRtnInboundOrderCommand> findMsgRtnInboundByPageRoot(int start, int pageSize, @QueryParam("staCode") String staCode, @QueryParam("slipCode") String slipCode, @QueryParam("status") int status, @QueryParam("startDate") Date startDate,
            @QueryParam("endDate") Date endDate, @QueryParam("ouId") Long ouId, RowMapper<MsgRtnInboundOrderCommand> rowMapper, Sort[] sorts);

    @NativeQuery
    MsgRtnInboundOrderCommand getMsgRtnInboundDetailById(@QueryParam("id") Long id, RowMapper<MsgRtnInboundOrderCommand> rowMapper);


    @NativeUpdate
    void saveRtnInBoundFromTemplate(@QueryParam(value = "source") String source, @QueryParam(value = "rtnStatus") Integer rtnStatus, @QueryParam(value = "fbStatus") Integer fbStatus);

    @NativeQuery
    MsgRtnInboundOrderCommand findMsgRtnInfoByStatus(@QueryParam("staCode") String staCode, @QueryParam("skuId") Long skuId, @QueryParam("invStatusId") int status, RowMapper<MsgRtnInboundOrderCommand> rowMapper);

    @NativeUpdate
    void updateStaCodeToMsg(@QueryParam(value = "staCode") String staCode, @QueryParam(value = "msgId") Long msgId);

    @NativeUpdate
    void updateOrderStautsBySlipCode(@QueryParam("slipCode") String slipCode, @QueryParam("status") int status);

    @NativeQuery
    List<String> getMsgRtnInboundByStatus(@QueryParam("source") String source, SingleColumnRowMapper<String> rowMapper);

    @NativeQuery
    List<MsgRtnInboundOrder> findMsgRtnInboundOrders(@QueryParam("source") String source, @QueryParam("slipCode") String slipCode, RowMapper<MsgRtnInboundOrder> rowMapper);

    @NativeUpdate
    void insertIntoUAInvLog();

    @NativeQuery
    List<MsgRtnInboundOrder> findRtnInboundByStatus(@QueryParam(value = "sourceList") List<String> sourceList, RowMapper<MsgRtnInboundOrder> rowMap);

    @NamedQuery
    List<MsgRtnInboundOrder> getRtnInboundByErrorCount(@QueryParam(value = "errorCount") Long errorCount);

    @NativeUpdate
    void updateRtnInboundById(@QueryParam(value = "id") Long id);
}
