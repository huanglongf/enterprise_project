/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.dao.warehouse;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.InventoryReport;
import com.jumbo.wms.model.warehouse.StockTransTxLog;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;
import com.jumbo.wms.model.warehouse.StvLineCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

@Transactional
public interface StockTransTxLogDao extends GenericEntityDao<StockTransTxLog, Long> {
    /**
     * 根据tran_time与sta_code获取仓库库存日志
     * 
     * @return
     */
    @NativeQuery
    List<StockTransTxLogCommand> findStockTransTxLogList2(BeanPropertyRowMapper<StockTransTxLogCommand> beanPropertyRowMapper);

    /**
     * 查询时间段内某sku库存日志
     * 
     * @param start
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param skuId
     * @param owner
     * @param whOuId
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StockTransTxLogCommand> findSkuInvLogPageByDate(int start, int pageSize, @QueryParam("startDate") Date startDate, @QueryParam("endDate") Date endDate, @QueryParam("skuId") Long skuId, @QueryParam("owner") String owner,
            @QueryParam("whOuId") String whOuId, @QueryParam("ouIds") List<String> ouIds, RowMapper<StockTransTxLogCommand> rowMapper, Sort[] sorts);

    /**
     * 库存操作日志查询
     * 
     * @param start
     * @param pageSize
     * @param icCode
     * @param districtCode
     * @param locationCode
     * @param stockStartTime
     * @param stockEndTime
     * @param transactionTypeid
     * @param staCode
     * @param operator
     * @param barCode
     * @param jmCode
     * @param skuCode
     * @param skuName
     * @param owner
     * @param ouid
     * @param companyid
     * @param refSlipCode
     * @param supplierCode
     * @param invStatus
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransTxLogCommand> findStockTransTxLogList(int start, int pageSize, @QueryParam("icCode") String icCode, @QueryParam("districtCode") String districtCode, @QueryParam("locationCode") String locationCode,
            @QueryParam("stockStartTime") Date stockStartTime, @QueryParam("stockEndTime") Date stockEndTime, @QueryParam("transactionTypeid") Long transactionTypeid, @QueryParam("staCode") String staCode, @QueryParam("operator") String operator,
            @QueryParam("barCode") String barCode, @QueryParam("jmCode") String jmCode, @QueryParam("skuCode") String skuCode, @QueryParam("skuName") String skuName, @QueryParam("owner") String owner, @QueryParam("ouid") Long ouid,
            @QueryParam("companyid") Long companyid, @QueryParam("refSlipCode") String refSlipCode, @QueryParam("supplierCode") String supplierCode, @QueryParam("invStatus") String invStatus, Sort[] sorts,
            BeanPropertyRowMapper<StockTransTxLogCommand> beanPropertyRowMapper);

    @NativeQuery
    String findOwnerBySku(@QueryParam("skuId") Long skuId, @QueryParam("ouid") Long ouid, SingleColumnRowMapper<String> r);

    @NativeQuery
    List<StockTransTxLogCommand> findLvsStockTransTxLogList(@QueryParam("ouid") Long ouid, @QueryParam("stockStartTime") Date stockStartTime, @QueryParam("stockEndTime") Date stockEndTime, @QueryParam("transactionTypeName") String transactionTypeName,
            @QueryParam("invStatus") Long invStatus, @QueryParam("supplierSkuCode") String supplierSkuCode, @QueryParam("productCategory") String productCategory, @QueryParam("productLine") String productLine,
            @QueryParam("consumerGroup") String consumerGroup, RowMapper<StockTransTxLogCommand> r);

    @NativeQuery
    BigDecimal findCountLvsStockTransTxLogList(@QueryParam("ouid") Long ouid, @QueryParam("stockStartTime") Date stockStartTime, @QueryParam("stockEndTime") Date stockEndTime, @QueryParam("transactionTypeName") String transactionTypeName,
            @QueryParam("invStatus") Long invStatus, @QueryParam("supplierSkuCode") String supplierSkuCode, @QueryParam("productCategory") String productCategory, @QueryParam("productLine") String productLine,
            @QueryParam("consumerGroup") String consumerGroup, SingleColumnRowMapper<BigDecimal> r);

    @NativeUpdate
    void generateLogForTransCrossThreePl(@QueryParam("inStvId") Long inStvId, @QueryParam("outStvId") Long outStvId, @QueryParam("addOuId") Long addOuId, @QueryParam("transctionId") Long transctionId);

    @NativeUpdate
    void generateOMSInvLogForTransCrossThreePl(@QueryParam("stvId") Long stvId);

    /**
     * 库存周报查询 KJL
     * 
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<InventoryReport> findStatisticsWeekInvLog2(BeanPropertyRowMapper<InventoryReport> beanPropertyRowMapper);

    /**
     * 推荐库位 原则2. 系统推荐7天内最近的销售相关出库库位 KJL
     * 
     * @param whId
     * @param skuId
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Long getRecommendLocationId(@QueryParam("whId") Long whId, @QueryParam("skuId") Long skuId, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 商品于特定拣货区三天内存在销售出库的库位
     * 
     * @param skuId
     * @param disId
     * @param ouId
     * @param innerShopCode
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<String> findInInventoryLoc(@QueryParam("skuId") Long skuId, @QueryParam("disId") Long disId, @QueryParam("ouId") Long ouId, @QueryParam("shop") String innerShopCode, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 根据盘点批次查询出入库明细
     * 
     * @param id
     * @param flag
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StockTransTxLogCommand> getDetailByInventoryCheckId(@QueryParam("invId") Long id, @QueryParam("flag") Boolean flag, BeanPropertyRowMapper<StockTransTxLogCommand> beanPropertyRowMapper);


    @NativeQuery
    List<StockTransTxLogCommand> findStaLogByStaId(@QueryParam("staid") Long staid, BeanPropertyRowMapper<StockTransTxLogCommand> beanPropertyRowMapper);

    @NativeQuery
    List<StockTransTxLogCommand> findStaLogByStvId(@QueryParam("stvid") Long stvid, BeanPropertyRowMapper<StockTransTxLogCommand> beanPropertyRowMapper);

    @NativeQuery
    List<StockTransTxLogCommand> findStaLogByStvId2(@QueryParam("staid") Long staid, BeanPropertyRowMapper<StockTransTxLogCommand> beanPropertyRowMapper);

    
    @NativeQuery
    List<StockTransTxLogCommand> findLocLogByStaId(@QueryParam("staId") Long staId, BeanPropertyRowMapper<StockTransTxLogCommand> beanPropertyRowMapper);

    @NativeUpdate
    void insertIncrementInv(@QueryParam("id") Long id);

    @NativeUpdate
    void insertIncrementInv2(@QueryParam("id") Long id);

    @NativeUpdate
    void insertIncrementInvWhenSalesQtyChange(@QueryParam("staId") Long staId);

    @NativeQuery
    List<StockTransTxLogCommand> findStLogByInventoryCheckId(@QueryParam("invId") Long invId, BeanPropertyRowMapper<StockTransTxLogCommand> beanPropertyRowMapper);

    @NativeQuery
    List<StockTransTxLogCommand> findEdwTwhStLog(RowMapper<StockTransTxLogCommand> rowMapper);

    @NativeQuery
    List<StockTransTxLogCommand> findEdwTwhStLogToSkuId(RowMapper<StockTransTxLogCommand> RowMapper);

    @NativeQuery
    StockTransTxLogCommand findOwnerByWHAndType(@QueryParam("ouId") Long ouId, @QueryParam("skuId") Long skuId, RowMapper<StockTransTxLogCommand> r);

    @NativeQuery
    List<StockTransTxLogCommand> findLogByStvId(@QueryParam("stvid") Long stvid, BeanPropertyRowMapper<StockTransTxLogCommand> beanPropertyRowMapper);

    @NativeQuery
    List<StockTransTxLogCommand> findLogPushOutboundData(@QueryParam("ownerList") List<String> ownerList, BeanPropertyRowMapper<StockTransTxLogCommand> r);

    @NativeQuery
    List<StockTransTxLogCommand> findLogPushInboundData(@QueryParam("ownerList") List<String> ownerList, BeanPropertyRowMapper<StockTransTxLogCommand> r);

    /**
     * 根据盘点单更新该单是否通知pacs库存共享刘水
     * 
     * @param inventoryCheckId
     */
    @NativeUpdate
    void updateOcpById(@QueryParam("invId") Long inventoryCheckId);

    @NativeQuery
    StockTransTxLogCommand findOwnerByStaCode(@QueryParam("slipCode") String slipCode, @QueryParam("skuId") Long skuId, BeanPropertyRowMapper<StockTransTxLogCommand> r);

    @NativeQuery
    List<StockTransTxLogCommand> findLogListByOwner(@QueryParam("direction") int direction, @QueryParam("staCode") String staCode, BeanPropertyRowMapper<StockTransTxLogCommand> r);

    @NativeQuery
    List<StockTransTxLogCommand> findLogListByStaCodeAndtransId(@QueryParam("transId") Long transId, @QueryParam("staCode") String staCode, BeanPropertyRowMapper<StockTransTxLogCommand> r);

    @NativeQuery
    List<String> findExpDateFromStockTransTxLog(@QueryParam(value = "staId") Long staId, @QueryParam(value = "skuId") Long skuId, SingleColumnRowMapper<String> singleColumnRowMapper);

    //
    @NativeQuery
    List<StockTransTxLog> findByOccupiedCodeLog(@QueryParam("staCode") String staCode, BeanPropertyRowMapper<StockTransTxLog> r);

    @NamedQuery
    List<StockTransTxLog> findByOccupiedCodeLog(@QueryParam("staCode") String staCode);
    
    @NativeQuery
    List<StockTransTxLogCommand> findLogListByStaCode(@QueryParam("direction") int direction, @QueryParam("staCode") String staCode, BeanPropertyRowMapper<StockTransTxLogCommand> r);

    @NativeQuery
    List<StvLineCommand> findLogGroupListByStaId(@QueryParam("direction") int direction, @QueryParam("staId") Long staId, BeanPropertyRowMapper<StvLineCommand> r);

}
