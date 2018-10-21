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

import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherCommand;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.StockTransVoucherType;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TransactionDirection;

import loxia.annotation.DynamicQuery;
import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

@Transactional
public interface StockTransVoucherDao extends GenericEntityDao<StockTransVoucher, Long> {
    /**
     * 当前申请单是否含有未上架的作业单,状态为Created
     * 
     * @param staId
     * @return
     */
    @NamedQuery
    StockTransVoucher findByStaAndTypeAndStatus(@QueryParam(value = "staId") Long staId, @QueryParam(value = "status") StockTransVoucherStatus status, @QueryParam(value = "type") StockTransVoucherType type);


    /**
     * 当前申请单是否含有未上架的作业单,状态为Created
     * 
     * @param staId
     * @return
     */
    @NamedQuery
    StockTransVoucher findStvCreatedByStaId(@QueryParam(value = "staId") Long staId);

    /**
     * 已转出 查询出 stv 完成数据 设置为取消
     * 
     * @param staId
     * @return
     */
    @NamedQuery
    StockTransVoucher findStvCreatedByStaId2(@QueryParam(value = "staId") Long staId);



    @NamedQuery
    List<StockTransVoucher> findStvCreatedByStaId3(@QueryParam(value = "staId") Long staId);



    /**
     * 当前申请单销售出 状态为10
     */

    /**
     * 更新Stv Version
     * 
     * @param staId
     * @return
     */
    @NativeUpdate
    int updateStvVersion(@QueryParam(value = "stvId") Long stvId, @QueryParam(value = "status") Integer status);

    /**
     * 当前申请单是否含有未上架的作业单,状态为Created
     * 
     * @param staId
     * @return
     */
    @NamedQuery
    List<StockTransVoucher> findStvCreatedListByStaId(@QueryParam(value = "staId") Long staId);

    /**
     * 当前申请单是否含有未上架的作业单,状态为Created
     * 
     * @param staId
     * @return
     */
    @NamedQuery
    List<StockTransVoucher> findStvFinishListByStaId(@QueryParam(value = "staId") Long staId);

    @NativeUpdate
    void createStv(@QueryParam(value = "code") String code, @QueryParam(value = "owner") String owner, @QueryParam(value = "sta_id") Long sta_id, @QueryParam(value = "status") Integer status, @QueryParam(value = "creator_id") Long creator_id,
            @QueryParam(value = "direction") Integer direction, @QueryParam(value = "wh_id") Long wh_id, @QueryParam(value = "transtype_id") Long transtype_id);

    /**
     * 获取BusinessSeqNo序号
     */
    @NativeQuery
    BigDecimal getBusinessSeqNo(RowMapper<BigDecimal> rowMapper);

    @NativeQuery
    Date findInboundDate(@QueryParam(value = "staId") Long staId, SingleColumnRowMapper<java.util.Date> singleColumnRowMapper);

    /**
     * 更具sta获取新stv code
     * 
     * @param staId
     * @return
     */
    @NativeQuery
    String getCode(@QueryParam(value = "staId") Long staId, SingleColumnRowMapper<String> rowMap);

    @NamedQuery
    List<StockTransVoucher> findByStaWithDirection(@QueryParam(value = "staId") Long staId, @QueryParam(value = "direction") TransactionDirection direction);

    @NamedQuery
    List<StockTransVoucher> findFinishByStaWithDirection(@QueryParam(value = "staId") Long staId, @QueryParam(value = "direction") TransactionDirection direction);

    /**
     * 根据采购的stvid确定该stv的批次
     * 
     * @param stvid
     * @param rowMapper
     * @return
     */
    @NativeQuery
    Integer findStvOrderByStvIdSql(@QueryParam(value = "stvid") Long stvid, RowMapper<Integer> rowMapper);

    @NativeUpdate
    void deleteByPikcingList(@QueryParam(value = "plId") Long plId);

    @NamedQuery
    StockTransVoucher findPdaStv(@QueryParam(value = "staId") Long staId);

    @NamedQuery
    StockTransVoucher findStvByStvId(@QueryParam(value = "stvId") Long stvId);

    @NativeQuery
    BigDecimal getSequence(SingleColumnRowMapper<BigDecimal> singleColumnRowMapper);

    @NativeUpdate
    void generateCancelStatusStv(@QueryParam(value = "staid") Long staid, @QueryParam(value = "newStvId") BigDecimal newStvId, @QueryParam(value = "intStatus") int intStatus);

    @NativeUpdate
    void createGIOutStv(@QueryParam(value = "stvId") Long stvId, @QueryParam(value = "code") String code, @QueryParam(value = "creatorId") Long creator_id);

    @NamedQuery
    StockTransVoucher findByStvCode(@QueryParam(value = "code") String code);

    @DynamicQuery
    List<StockTransVoucher> findByStvIds(@QueryParam(value = "ids") List<Long> ids);

    @NativeQuery
    List<StvLineCommand> findStvLineByStaMap(@QueryParam("staId") Long staId, BeanPropertyRowMapper<StvLineCommand> beanPropertyRowMapper);

    @NativeQuery
    List<StockTransVoucher> findStvByStaSlipCode1(@QueryParam("staSlipCode1") String staSlipCode1, BeanPropertyRowMapper<StockTransVoucher> beanPropertyRowMapper);

    @NativeQuery
    List<StockTransVoucher> findStvsByStaSlipCode1(@QueryParam("staSlipCode1") String staSlipCode1, BeanPropertyRowMapper<StockTransVoucher> beanPropertyRowMapper);

    @NativeQuery
    List<StockTransVoucher> findOutboundStvByStaSlipCode(@QueryParam("staSlipCode") String staSlipCode, BeanPropertyRowMapper<StockTransVoucher> beanPropertyRowMapper);

    @NativeQuery
    String findStvLineBrathCodeBySta(@QueryParam("staId") Long staId, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NamedQuery
    StockTransVoucher findStvByStaId(@QueryParam(value = "staId") Long staId);

    @NativeQuery
    StockTransVoucher findStockTransVoucherByStaId(@QueryParam("staId") Long StaId, BeanPropertyRowMapperExt<StockTransVoucher> beanPropertyRowMapper);

    @NativeQuery
    StockTransVoucher findStvBySta(@QueryParam("staId") Long id, BeanPropertyRowMapper<StockTransVoucher> beanPropertyRowMapper);

    /**
     * 通过stvid和skuid查询库存状态
     */
    @NativeQuery
    Long getInvStatus(@QueryParam(value = "stvId") Long stvId, @QueryParam(value = "skuId") Long skuId, SingleColumnRowMapper<Long> rowMap);

    /**
     * t_wh_stv edw 数据表
     */
    @NativeQuery
    List<StockTransVoucherCommand> findEdwTwhStv(@QueryParam(value = "staId") Long staId, RowMapper<StockTransVoucherCommand> rowMapper);

    @NativeQuery
    StockTransVoucherCommand findTwhStvById(@QueryParam(value = "stvid") Long stvid, RowMapper<StockTransVoucherCommand> rowMapper);

    @NativeQuery
    StockTransVoucherCommand findIsSnByStvId(@QueryParam(value = "stvId") Long stvId, RowMapper<StockTransVoucherCommand> rowMapper);

    @NamedQuery
    StockTransVoucher getCanceledStvByStaId(@QueryParam("staId") Long id);


    /**
     * @param id
     */
    @NativeUpdate
    void deleteStvBySta(@QueryParam("staId") Long id);

    /**
     * 方法说明：通过作业单,事务方向,SKU和状态查询明细单
     * 
     * @author LuYingMing
     * @date 2016年9月19日 上午11:38:51
     * @param staId
     * @param direction
     * @param skuId
     * @return
     */
    @NativeQuery
    List<StockTransVoucher> findStvIdByStaWithSkuAndDirection(@QueryParam(value = "staCode") String staCode, @QueryParam(value = "type") Long type, RowMapper<StockTransVoucher> rowMapper);

    @NativeQuery
    Long findStvIdByStaIdUnique(@QueryParam(value = "staCode") String staCode, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    Integer findProcessStatus(@QueryParam(value = "staId") Long staId, SingleColumnRowMapper<Integer> singleColumnRowMapper);



}
