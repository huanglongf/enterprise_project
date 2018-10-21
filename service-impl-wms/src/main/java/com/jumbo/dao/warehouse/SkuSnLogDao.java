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

import java.util.List;
import java.util.Map;

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

import com.jumbo.wms.model.warehouse.SkuSnLog;
import com.jumbo.wms.model.warehouse.SkuSnLogCommand;

@Transactional
public interface SkuSnLogDao extends GenericEntityDao<SkuSnLog, Long> {

    @NativeUpdate
    void createOutboundByStvIdSql(@QueryParam(value = "stvid") Long stvId);

    @NativeUpdate
    void insertOutboundByStaId(@QueryParam(value = "staid") Long staId);

    @NativeUpdate
    void createInboundLogByStvIdSql(@QueryParam(value = "stvid") Long stvId, @QueryParam(value = "batchCode") String batchCode);

    @NativeUpdate
    void createInboundLogByStvIdSql2(@QueryParam(value = "oldStvId") Long oldStvId, @QueryParam(value = "newStvId") Long newStvId, @QueryParam(value = "batchCode") String batchCode);

    @NativeUpdate
    void createTransitCrossLogByStvIdSql(@QueryParam(value = "stvid") Long stvId);

    @NativeUpdate
    void createTransitCrossLogByStvIdSql2(@QueryParam(value = "oldStvId") Long oldStvId, @QueryParam(value = "newStvId") Long newStvId);


    @NativeUpdate
    void createLogByIc(@QueryParam(value = "icid") Long icid, @QueryParam(value = "batchCode") String batchCode);

    @NativeQuery
    List<SkuSnLogCommand> findOutboundSnBySta(@QueryParam(value = "staid") Long staid, RowMapper<SkuSnLogCommand> r);

    @NativeQuery
    List<SkuSnLogCommand> findOutboundSnBySkuId(@QueryParam(value = "stvId") Long stvId, @QueryParam(value = "skuId") Long skuId, RowMapper<SkuSnLogCommand> r);

    @NativeQuery
    List<SkuSnLogCommand> findOutboundSnByStaSlipCode(@QueryParam(value = "slipCode") String slipCode, RowMapper<SkuSnLogCommand> r);

    @NativeQuery
    List<SkuSnLogCommand> findSNBySta(@QueryParam(value = "staid") Long staid, RowMapper<SkuSnLogCommand> r);

    @NativeQuery
    List<SkuSnLogCommand> findSNBySlipcode1(@QueryParam(value = "slipcode") String slipcode, RowMapper<SkuSnLogCommand> r);

    @NativeQuery(pagable = true)
    Pagination<SkuSnLogCommand> findSnPoSoLog(int start, int size, @QueryParam(value = "jmskucode") String jmskucode, @QueryParam(value = "barcode") String barcode, @QueryParam(value = "skuName") String skuName, @QueryParam(value = "sn") String sn,
            @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "cardStatus") Integer cardStatus, RowMapper<SkuSnLogCommand> r);

    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<SkuSnLogCommand> findSnLog(int start, int size, @QueryParam(value = "staCode") String staCode, @QueryParam(value = "direction") Integer direction, @QueryParam(value = "skuCode") String skuCode,
            @QueryParam(value = "barcode") String barcode, @QueryParam(value = "skuName") String skuName, @QueryParam(value = "sn") String sn, @QueryParam(value = "ouid") Long ouid, RowMapper<SkuSnLogCommand> r);

    /**
     * 根据Sta查找SNlog
     * 
     * @param start
     * @param size
     * @param staId
     * @param map
     * @param r
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<SkuSnLogCommand> findSnLogBySta(int start, int size, @QueryParam(value = "staId") Long staId, @QueryParam Map<String, Object> map, RowMapper<SkuSnLogCommand> r, Sort[] sorts);

    /**
     * 香港站新增
     * 
     * @param id
     */
    @NativeUpdate
    void createInboundLogByStvId(@QueryParam("stvId") Long id);

    /**
     * 根据STA查询代入SN号,通过slip code 1关联查询历史作业单
     * 
     * @param staid
     * @param r
     * @return
     */
    @NativeQuery
    List<SkuSnLogCommand> findToInboundSn(@QueryParam("outStv") List<Long> outStv, @QueryParam("inStv") List<Long> inStv, RowMapper<SkuSnLogCommand> r);

    @NamedQuery
    SkuSnLog findSkuSnLogBySn(@QueryParam(value = "snNumber") String snNumber);

    @NativeQuery
    List<String> findSnFromSnLog(@QueryParam(value = "staId") Long staId, @QueryParam(value = "skuId") Long skuId, SingleColumnRowMapper<String> singleColumnRowMapper);
}
