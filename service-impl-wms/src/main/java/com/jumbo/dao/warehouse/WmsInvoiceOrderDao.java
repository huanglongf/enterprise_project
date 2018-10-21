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

import java.util.Date;
import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrderCommand;

@Transactional
public interface WmsInvoiceOrderDao extends GenericEntityDao<WmsInvoiceOrder, Long> {

    @NativeQuery
    List<Long> findAllWmsInvoiceOrderByListId(@QueryParam("batchFlag") String batchFlag, @QueryParam("wioId") List<Long> wioId, SingleColumnRowMapper<Long> singleColumnRowMapper);


    @NativeQuery
    String findTheMaxBatchNoByToday(@QueryParam("dateStr") String dateStr, SingleColumnRowMapper<String> r);

    @NativeQuery
    List<String> findDistinctBatchNoExist(@QueryParam("wioId") List<Long> wioId, RowMapper<String> rowMapper);

    @NativeQuery
    List<WmsInvoiceOrderCommand> findWmsInvoiceOrderByWioIdlist(@QueryParam("wioId") List<Long> wioId, RowMapper<WmsInvoiceOrderCommand> r);

    @NativeUpdate
    void updateWmsInvoiceOrderByWioId(@QueryParam("wioId") Long wioId, @QueryParam("batchNo") String batchNo, @QueryParam("pgIndex") Long pgIndex);

    @NativeQuery
    List<WmsInvoiceOrderCommand> findWmsInvoiceOrderBySlipCode(@QueryParam("ouId") Long ouId, @QueryParam("slipCode") String slipCode, RowMapper<WmsInvoiceOrderCommand> r);

    @NativeQuery
    String findExportFileNameByBatchNo(@QueryParam(value = "batchNo") String batchNo, @QueryParam(value = "wioIdList") List<Long> wioIdList, SingleColumnRowMapper<String> s);

    @NativeQuery
    List<WmsInvoiceOrderCommand> findWmsInvoiceOrderBillData(@QueryParam(value = "isOnlyParent") Boolean isOnlyParent, @QueryParam(value = "batchNo") String batchNo, @QueryParam(value = "wioId") Long wioId, RowMapper<WmsInvoiceOrderCommand> r);

    @NativeQuery
    List<WmsInvoiceOrderCommand> findWmsInvoiceOrderIsExist(@QueryParam(value = "orderCode") String orderCode, RowMapper<WmsInvoiceOrderCommand> r);

    @NativeUpdate
    void updateWmsInvoiceOrderStatusByWioIdList(@QueryParam("wioId") Long wioId, @QueryParam("status") Integer status);

    /**
     * 
     * @param start
     * @param pageSize
     * @param createTime
     * @param endCreateTime
     * @param finishTime
     * @param endFinishTime
     * @param systemKey
     * @param orderCode
     * @param owner
     * @param intStatus
     * @param batchCode
     * @param pgIndex
     * @param lpCode
     * @param transNo
     * @param beanPropertyRowMapperExt
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<WmsInvoiceOrder> findWmsInvoiceOrderList(int start, int pageSize, @QueryParam("createTime") Date createTime, @QueryParam("endCreateTime") Date endCreateTime, @QueryParam("finishTime") Date finishTime,
            @QueryParam("endFinishTime") Date endFinishTime, @QueryParam("systemKey") String systemKey, @QueryParam("orderCode") String orderCode, @QueryParam("owner") String owner, @QueryParam("intStatus") Integer intStatus,
            @QueryParam("batchCode") String batchCode, @QueryParam("pgIndex") Integer pgIndex, @QueryParam("lpCode") String lpCode, @QueryParam("transNo") String transNo, BeanPropertyRowMapper<WmsInvoiceOrder> beanPropertyRowMapper, Sort[] sorts);

    /**
     * 获取补寄发票批次ID
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Long getSequenceIdForBatchCode(SingleColumnRowMapper<Long> singleColumnRowMapper);
    
    @NativeQuery
    List<Long> getAllInvoiceOrderForId(SingleColumnRowMapper<Long> singleColumnRowMapper);
}
