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
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelCommand;


@Transactional
public interface MsgOutboundOrderCancelDao extends GenericEntityDao<MsgOutboundOrderCancel, Long> {
    @NativeQuery
    List<MsgOutboundOrderCancel> findVmiMsgRtnOutboundByStaCode(@QueryParam("staCode") String staCode, @QueryParam("sta") int sta, RowMapper<MsgOutboundOrderCancel> rowMapper);

    @NativeUpdate
    void updateStaById(@QueryParam("staCode") String staCode, @QueryParam("sta") int sta);

    @NativeUpdate
    void updateOrderById(@QueryParam("id") Long id, @QueryParam("sta") int sta);

    @NamedQuery
    List<MsgOutboundOrderCancel> findOneVimCancelInfoByStatus(@QueryParam("source") String source);

    @NamedQuery
    List<MsgOutboundOrderCancel> findByStaCode(@QueryParam("staCode") String staCode);

    @NativeQuery
    MsgOutboundOrderCancel findVmiMsgRtnOutboundCancelInfo(@QueryParam("staCode") String staCode, @QueryParam("sta") int sta, RowMapper<MsgOutboundOrderCancel> rowMapper);

    @NativeQuery
    Long findoutBoundCancleBatchNo(RowMapper<Long> batchNo);

    @NamedQuery
    List<MsgOutboundOrderCancel> findMsgOutboundOrderCancelList(@QueryParam("source") String source);

    @NamedQuery
    List<MsgOutboundOrderCancel> findMsgOutboundOrderCancelBatch(@QueryParam("batchId") Long batchId);

    @NamedQuery
    List<MsgOutboundOrderCancel> findMsgOutboundOrderCancelMail();

    @NamedQuery
    MsgOutboundOrderCancel getByStaCode(@QueryParam("staCode") String staCode);

    @NamedQuery
    MsgOutboundOrderCancel getMsgCancelByStaCode(@QueryParam("staCode") String staCode);

    @NativeUpdate
    void updateOuOrderStatusById(@QueryParam("staCode") String staCode, @QueryParam("sta") int sta);

    @NativeUpdate
    void updateOuOrderStatus(@QueryParam("msgId") Long msgId, @QueryParam("sta") int sta);

    @NativeUpdate
    void updateMsgOrderCancelById(@QueryParam("msgId") Long msgId, @QueryParam("sta") int sta);

    @NativeUpdate
    void updateCancleOrderBachIdById(@QueryParam("id") Long id, @QueryParam("batchId") Long batchId, @QueryParam("sta") int sta);

    @NativeQuery
    List<Long> findMsgOutboundOrderCancelIdList(SingleColumnRowMapper<Long> rowMapper);


    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<MsgOutboundOrderCancelCommand> findOutboundOrderCancelListInfo(int start, int pageSize, @QueryParam("source") String source, @QueryParam("staCode") String code, @QueryParam("startDate") Date starteDate, @QueryParam("endDate") Date endDate,
            @QueryParam("slipCode") String slipCode, @QueryParam("statusId") int statusId, RowMapper<MsgOutboundOrderCancelCommand> rowMapper, Sort[] sorts);

    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<MsgOutboundOrderCancelCommand> findOutboundOrderCancelListInfoRoot(int start, int pageSize, @QueryParam("source") String source, @QueryParam("staCode") String code, @QueryParam("startDate") Date starteDate,
            @QueryParam("endDate") Date endDate, @QueryParam("slipCode") String slipCode, @QueryParam("statusId") int statusId, RowMapper<MsgOutboundOrderCancelCommand> rowMapper, Sort[] sorts);

    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<MsgOutboundOrderCancelCommand> findOutboundOrderCancelList(int start, int pageSize, @QueryParam("source") String source, @QueryParam("startDate") Date starteDate, @QueryParam("endDate") Date endDate, @QueryParam("status") int status,
            Sort[] sorts, RowMapper<MsgOutboundOrderCancelCommand> rowMapper);


    @NativeQuery
    List<MsgOutboundOrderCancel> getMsgOutboundByStatus(@QueryParam(value = "code") String code, RowMapper<MsgOutboundOrderCancel> rowMap);

    @NamedQuery
    List<MsgOutboundOrderCancel> getOrderCancelByErrorCount(@QueryParam(value = "errorCount") Long errorCount);

    @NativeUpdate
    void updateMsgCancelById(@QueryParam(value = "id") Long id);

    @NativeQuery
    List<MsgOutboundOrderCancelCommand> findMsgCancelListByDate(@QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "source") String source,
            BeanPropertyRowMapperExt<MsgOutboundOrderCancelCommand> beanPropertyRowMapper);

    @NativeQuery
    List<MsgOutboundOrderCancel> findMsgCancelListKey(BeanPropertyRowMapperExt<MsgOutboundOrderCancel> beanPropertyRowMapper);

    @NativeUpdate
    void updateOrderCancelById(@QueryParam(value = "id") Long id, @QueryParam(value = "time") Date time, @QueryParam(value = "status") Integer status);
}
