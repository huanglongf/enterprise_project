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

import com.jumbo.wms.model.command.RtwDiekingCommend;
import com.jumbo.wms.model.mongodb.MongoDBWhPicking;
import com.jumbo.wms.model.warehouse.RtwDieking;
import com.jumbo.wms.model.warehouse.RtwDiekingLine;

@Transactional
public interface RtwDieKingDao extends GenericEntityDao<RtwDieking, Long> {

    @NativeQuery(pagable = true)
    Pagination<RtwDiekingCommend> getRtwDiekingList(int start, int pageSize, @QueryParam("startCreateTime") Date startCreateTime, @QueryParam("endCreateTime") Date endCreateTime, @QueryParam("batchCode") String batchCode,
            @QueryParam("staType") Integer staType, @QueryParam("shopInnerCodes") List<String> shopInnerCodes, @QueryParam("staCode") String staCode, @QueryParam("staRefSlipCode") String staRefSlipCode, Sort[] sorts, @QueryParam("ouid") Long ouid,
            @QueryParam("shortPickStatus") String shortPickStatus, @QueryParam("skuCode") String skuCode, RowMapper<RtwDiekingCommend> beanPropertyRowMapper);



    @NativeQuery
    Long getRtwDiekingBatchCode(RowMapper<Long> rowMap);


    /**
     * 根据出库单编码 获取对应出库单按照拣货区域需要拣货的库位列表
     * 
     * @param staCode
     * @param ouid
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<Long> getLocationIdByStaCodePickZoon(@QueryParam("staCode") String staCode, @QueryParam("ouid") Long ouid, SingleColumnRowMapper<Long> rowMap);

    /**
     * 根据出库单编码 获取对应出库单按照仓库区域需要拣货的库位列表
     * 
     * @param staCode
     * @param ouid
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<Long> getLocationIdByStaCodeZoon(@QueryParam("staCode") String staCode, @QueryParam("ouid") Long ouid, SingleColumnRowMapper<Long> rowMap);

    /**
     * 查询拣货明细数据
     * 
     * @param staCode
     * @param zoonid
     * @param ouid
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<RtwDiekingLine> getRtwDiekingLineByStaCodeAndLoctionIds(@QueryParam("staCode") String staCode, @QueryParam("zoonid") Long zoonid, @QueryParam("ouid") Long ouid, @QueryParam("supplierCode") String supplierCode,
            @QueryParam("pzoonid") Long pzoonid, @QueryParam("staLineId") List<Long> staLineId, RowMapper<RtwDiekingLine> rowMap);

    /**
     * 根据出库单编码 查找所有拣货单ID
     * 
     * @param staCode
     * @param ouid
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<Long> getRtwDiekingIdsByStaCode(@QueryParam("staCode") String staCode, @QueryParam("ouid") Long ouid, RowMapper<Long> rowMap);

    @NativeUpdate
    void updateRtwDiekingPlanQtyById(@QueryParam("id") Long id);

    @NativeQuery
    Long findIdByBatchCode(@QueryParam(value = "batchCode") String batchCode, RowMapper<Long> rowMapper);

    /**
     * 根据id更新实际拣货数量
     * 
     * @param realityQuantity
     * @param Id
     */
    @NativeUpdate
    void updateRealityQuantityById(@QueryParam("realityQuantity") Long realityQuantity, @QueryParam("id") Long Id);

    @NativeUpdate
    void updateStartTimeAndUser(@QueryParam("batchCode") String batchCode, @QueryParam("userId") Long userId, @QueryParam("createUser") String createUser);

    /**
     * 初始化mongodb数据
     * 
     * @param batchCode
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<MongoDBWhPicking> findMongodbInfoByBatchCode(@QueryParam("batchCode") String batchCode, RowMapper<MongoDBWhPicking> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<RtwDiekingCommend> getOutboundDickingTaskDetailList(int start, int pageSize, Sort[] sorts, @QueryParam("ouid") Long ouid, @QueryParam("staid") Long staid, RowMapper<RtwDiekingCommend> beanPropertyRowMapper);

    @NamedQuery
    RtwDieking findRtwDiekingListByCode(@QueryParam("batchCode") String batchCode);

    /**
     * 根据出库单编码 获取对应出库单按照商品货号列表
     * 
     * @param staCode
     * @param ouid
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<String> getSkuIdByStaCodeSkuSupplierCode(@QueryParam("staCode") String staCode, @QueryParam("ouid") Long ouid, RowMapper<String> rowMap);

    /**
     * 查询拣货任务重复明细行数据
     * 
     */
    @NativeQuery
    List<String> getRepeatRtwDiekingLineByRtwDiekingId(@QueryParam("id") Long id, RowMapper<String> rowMap);
}
