package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.StaInvoice;
import com.jumbo.wms.model.warehouse.StaInvoiceCommand;

@Transactional
public interface StaInvoiceDao extends GenericEntityDao<StaInvoice, Long> {

    /**
     * 根据作业单查询发票
     * 
     * @param staId
     * @return
     */
    @NamedQuery
    List<StaInvoice> getBySta(@QueryParam("staId") Long staId);

    /**
     * 重置发票导出次数
     * 
     * @param staId
     */
    @NativeUpdate
    void resetExecuteCountByPlid(@QueryParam("plid") Long plid);

    /**
     * 重置发票导出次数
     * 
     * @param staId
     */
    @NativeUpdate
    void resetExecuteCountByStaid(@QueryParam("staid") Long staId);

    /**
     * 按配货清单更新导出次数
     * 
     * @param plid
     */
    @NativeUpdate
    void addExecuteCountByPlId(@QueryParam("plid") Long plid);

    /**
     * 按配货清单更新导出次数
     * 
     * @param plid
     */
    @NativeUpdate
    void addExecuteCountByPlIds(@QueryParam("plIds") List<Long> plIds);

    /**
     * 按配货清单更新导出次数
     * 
     * @param plid
     */
    @NativeUpdate
    void addExecuteCountBySta(@QueryParam("staid") Long staid);

    /**
     * 发票通用模板-根据配货清单查询发票信息
     * 
     * @param plid
     * @return
     */
    @NativeQuery(model = StaInvoiceCommand.class)
    List<StaInvoiceCommand> findInvoiceByPlid(@QueryParam("plid") Long plid);

    /**
     * 发票通用模板-根据配货清单查询发票信息
     * 
     * @param plid
     * @return
     */
    @NativeQuery(model = StaInvoiceCommand.class)
    List<StaInvoiceCommand> findInvoiceByPlids(@QueryParam("plIds") List<Long> plids);

    /**
     * 发票通用模板-根据配货清单查询发票信息
     * 
     * @param plid
     * @return
     */
    @NativeQuery(model = StaInvoiceCommand.class)
    List<StaInvoiceCommand> findBurberryInvoiceByPlid(@QueryParam("plid") Long plid);

    /**
     * 发票Burberry模板-根据配货清单查询发票信息
     * 
     * @param plid
     * @return
     */
    @NativeQuery(model = StaInvoiceCommand.class)
    List<StaInvoiceCommand> findBurberryInvoiceByStaid(@QueryParam("staid") Long staid);

    /**
     * 发票Burberry模板-根据配货清单查询发票信息
     * 
     * @param plid
     * @return
     */
    @NativeQuery(model = StaInvoiceCommand.class)
    List<StaInvoiceCommand> findInvoiceByStaid(@QueryParam("staid") Long staid);

    /**
     * 发票coach模板-根据配货清单查询发票信息
     * 
     * @param plid
     * @return
     */
    @NativeQuery(model = StaInvoiceCommand.class)
    List<StaInvoiceCommand> findCoachInvoice(@QueryParam("plid") Long plid, @QueryParam("staid") Long staid);

    /**
     * 销售|换货出库发票导出
     */
    @NativeQuery(model = StaInvoiceCommand.class)
    List<StaInvoiceCommand> findInvoicImportByOuId(@QueryParam("ouid") Long ouid, @QueryParam(value = "fromDate") String fromDate, @QueryParam(value = "endDate") String endDate);

    /**
     * 发票通用模板-根据BatchNo单查询发票信息
     * 
     * @return
     */
    @NativeQuery(model = StaInvoiceCommand.class)
    List<StaInvoiceCommand> findReplenishBurberryInvoice(@QueryParam("batchNo") String batchNo, @QueryParam("wioIdList") List<Long> wioIdList);

    /**
     * 发票通用模板-根据BatchNo单查询发票信息
     * 
     * @return
     */
    @NativeQuery(model = StaInvoiceCommand.class)
    List<StaInvoiceCommand> findReplenishCoachInvoice(@QueryParam("batchNo") String batchNo, @QueryParam("wioIdList") List<Long> wioIdList);

    /**
     * 发票通用模板-根据BatchNo单查询发票信息
     * 
     * @return
     */
    @NativeQuery(model = StaInvoiceCommand.class)
    List<StaInvoiceCommand> findDefaultInvoice(@QueryParam("batchNo") String batchNo, @QueryParam("wioIdList") List<Long> wioIdList);

    /**
     * 按batchNo更新导出次数
     * 
     * @param plid
     */
    @NativeUpdate
    void addExecuteCountByBatchNo(@QueryParam("batchNo") String batchNo, @QueryParam("wioIdList") List<Long> wioIdList);

    @NativeQuery
    int findInvoiceBySlipCode(@QueryParam("slipcode1") String slipcode1,SingleColumnRowMapper<Integer> r);
}
