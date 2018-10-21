package com.jumbo.dao.hub2wms;

import java.util.Date;
import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.WmsOrderStatusOms;

@Transactional
public interface WmsOrderStatusOmsDao extends GenericEntityDao<WmsOrderStatusOms, Long> {
    @NativeQuery(pagable = true)
    public Pagination<WmsOrderStatusOms> wmsOrderConfirm(int start, int size, @QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "systemKey") String systemKey, Sort[] sorts,
            RowMapper<WmsOrderStatusOms> rowMapper);

    @NativeQuery
    List<WmsOrderStatusOms> wmsOrderConfirmPac(@QueryParam(value = "systemKey") String systemKey, RowMapper<WmsOrderStatusOms> rowMapper);

    @NativeQuery
    List<WmsOrderStatusOms> wmsOrderConfirmPac2(@QueryParam(value = "systemKey") String systemKey, RowMapper<WmsOrderStatusOms> rowMapper);


    @NativeQuery
    List<Long> wmsOrderCreateTimeIsNull(RowMapper<Long> rowMapper);

    @NativeUpdate
    void updateWmsOrderCreateTime(@QueryParam(value = "ids") List<Long> ids);

    @NativeQuery(pagable = true)
    Pagination<WmsOrderStatusOms> queryWmsOrder(int start, int size, @QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "codeList") String[] codeList, Sort[] sorts,
            RowMapper<WmsOrderStatusOms> rowMapper);


    @NativeQuery(pagable = true)
    public Pagination<WmsOrderStatusOms> wmsOrderCancel(int start, int size, @QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "systemKey") String systemKey,
            @QueryParam(value = "type") List<Integer> type, Sort[] sorts, RowMapper<WmsOrderStatusOms> rowMapper);

    @NativeQuery(pagable = true)
    public Pagination<WmsOrderStatusOms> wmsOrderConfirmByType(int start, int size, @QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "systemKey") String systemKey,
            @QueryParam(value = "type") int type, Sort[] sorts, RowMapper<WmsOrderStatusOms> rowMapper);

    @NativeQuery(pagable = true)
    public Pagination<WmsOrderStatusOms> wmsOrderFinishByType(int start, int size, @QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "systemKey") String systemKey,
            @QueryParam(value = "type") List<Integer> type, Sort[] sorts, RowMapper<WmsOrderStatusOms> rowMapper);

    @NativeQuery
    List<WmsOrderStatusOms> wmsOutOrderConfirmPac(@QueryParam(value = "sum") int sum, RowMapper<WmsOrderStatusOms> rowMapper);


    /**
     * 获取出库通知oms数据 排除（adidas）
     */
    @NativeQuery
    List<WmsOrderStatusOms> findNoticeOrderList(RowMapper<WmsOrderStatusOms> row);

    @NativeUpdate
    void updateWmsOrderByStaCode(@QueryParam(value = "staCode") String staCode);

    /**
     * 查询出库未推送数量
     */
    @NativeQuery
    Long getUnpushedOutboundOrderCount(SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 更新出库未推送is_mq标识
     */
    @NativeUpdate
    void updateOutboundOrderFlag();
}
