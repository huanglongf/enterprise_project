package com.jumbo.dao.warehouse;

import java.util.List;
import java.util.Map;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WmsIntransitNoticeOms;
import com.jumbo.wms.model.warehouse.WmsIntransitNoticeOmsCommand;

/**
 * oms出库通知中间表
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
public interface WmsIntransitNoticeOmsDao extends GenericEntityDao<WmsIntransitNoticeOms, Long> {

    @NativeQuery
    List<String> findIntransit(SingleColumnRowMapper<String> r);

    @NativeQuery
    List<WmsIntransitNoticeOms> findPartIntransit(RowMapper<WmsIntransitNoticeOms> row);

    @NativeQuery
    List<WmsIntransitNoticeOms> findNoticeOrderList(RowMapper<WmsIntransitNoticeOms> row);

    @NativeQuery
    List<WmsIntransitNoticeOms> findPartByErCount(RowMapper<WmsIntransitNoticeOms> row);

    @NativeQuery
    List<WmsIntransitNoticeOms> findPartIntransitByCode(@QueryParam(value = "batchCode") String batchCode, RowMapper<WmsIntransitNoticeOms> row);

    @NativeUpdate
    void updateNoticeById(@QueryParam(value = "id") Long id, @QueryParam(value = "errorCount") Long errorCount, @QueryParam(value = "returnMsg") String returnMsg);

    @NativeUpdate
    void updateSendById(@QueryParam(value = "id") Long id);

    /**
     * 方法说明：(根据参数)查询 WmsIntransitNoticeOmsCommand
     * 
     * @author LuYingMing
     * @date 2016年8月15日 下午12:55:45
     * @param start
     * @param pageSize
     * @param m
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<WmsIntransitNoticeOmsCommand> findOutPacsByParams(int start, int pageSize, @QueryParam Map<String, Object> m, RowMapper<WmsIntransitNoticeOmsCommand> rowMapper);

    /**
     * 方法说明：重置为0
     * 
     * @author LuYingMing
     * @date 2016年8月15日 下午5:00:29
     * @param wmsIntransitNoticeOmsId
     */
    @NativeUpdate
    void resetZero(@QueryParam(value = "idList") List<Long> idList);

    /**
     * 
     * 方法说明：重置为100
     * 
     * @author LuYingMing
     * @date 2016年8月15日 下午5:00:50
     * @param queueStaIdwmsIntransitNoticeOmsId
     */
    @NativeUpdate
    void resetHundred(@QueryParam(value = "idList") List<Long> idList);


    @NativeUpdate
    void updateOrderByStaCode(@QueryParam(value = "staCode") String staCode);

}
