package com.jumbo.dao.warehouse;

import java.util.Date;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.DeliveryChanngeLog;
import com.jumbo.wms.model.warehouse.DeliveryChanngeLogCommand;

@Transactional
public interface DeliveryChanngeLogDao extends GenericEntityDao<DeliveryChanngeLog, Long> {

    /**
     * 根据新快递单号查找物流变更日志
     * 
     * @param trackingNo
     * @return
     */
    @NativeQuery
    DeliveryChanngeLog getDeliveryChanngeLogByTrackingNo(@QueryParam("trackingNo") String trackingNo, BeanPropertyRowMapper<DeliveryChanngeLog> beanPropertyRowMapper);

    /**
     * 根据原始快递单号查询物流变更日志
     * 
     * @param trackingNo
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    DeliveryChanngeLog getDeliveryChanngeLogByTrackingNo1(@QueryParam("trackingNo") String trackingNo, BeanPropertyRowMapper<DeliveryChanngeLog> beanPropertyRowMapper);

    /**
     * 
     * @param start
     * @param pageSize
     * @param sorts
     * @param channel
     * @param staCode
     * @param slipCode
     * @param slipCode1
     * @param lpCode
     * @param trackingNo
     * @param newLpcode
     * @param newTrackingNo
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<DeliveryChanngeLogCommand> getDeliveryChanngeLogList(int start, int pageSize,@QueryParam("ouId") Long ouId, Sort[] sorts, @QueryParam("channel") String channel, @QueryParam("staCode") String staCode, @QueryParam("slipCode") String slipCode,
            @QueryParam("slipCode1") String slipCode1, @QueryParam("lpCode") String lpCode, @QueryParam("trackingNo") String trackingNo, @QueryParam("newLpcode") String newLpcode, @QueryParam("newTrackingNo") String newTrackingNo,
            @QueryParam("startDate") Date startDate, @QueryParam("endDate") Date endDate, RowMapper<DeliveryChanngeLogCommand> rowMapper);
}
