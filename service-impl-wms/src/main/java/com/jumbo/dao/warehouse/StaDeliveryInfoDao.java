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
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.pda.GetTransNoCommand;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaDeliveryInfoCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;


@Transactional
public interface StaDeliveryInfoDao extends GenericEntityDao<StaDeliveryInfo, Long> {

    /**
     * 根据pickingList id查询物流信息
     * 
     * @param plid
     * @return
     */
    @NativeQuery
    List<StaDeliveryInfoCommand> findStaDeliveryInfoListByPlid(@QueryParam(value = "plid") Long plid, @QueryParam(value = "ouid") Long ouid, RowMapper<StaDeliveryInfoCommand> rowMapper);

    /**
     * 
     * @param lpCode
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    String findExpNameByLpcode(@QueryParam(value = "lpCode") String lpCode, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    String findexpNameByPlid(@QueryParam(value = "plid") Long plid, RowMapper<String> rowmap);

    @NamedQuery
    StaDeliveryInfo findStaDeliveryInfoByTrackingNo(@QueryParam(value = "trackingNo") String trackingNo);

    @NativeQuery
    String findExportDispatchListInvoiceFileName(@QueryParam(value = "pickingListId") Long pickingListId, SingleColumnRowMapper<String> s);

    /**
     * 
     * @param deliveryid
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Long findTransportatorId(@QueryParam(value = "deliveryid") Long deliveryid, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    String findLpCodeByPickingListId(@QueryParam(value = "plid") Long plid, @QueryParam(value = "staid") Long staid, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    String findLpCodeByStaCode(@QueryParam(value = "staCode") String staCode, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 查询快递面单打印数据
     * 
     * @param isOnlyParent //是否只打印主单
     * @param plid
     * @param staid
     * @param r
     * @return
     */
    @NativeQuery
    List<StaDeliveryInfoCommand> findPrintExpressBillData(@QueryParam(value = "isOnlyParent") Boolean isOnlyParent, @QueryParam(value = "plid") Long plid, @QueryParam(value = "staid") Long staid, RowMapper<StaDeliveryInfoCommand> r);

    /**
     * 查询快递面单打印数据
     * 
     * @param plid
     * @param staid
     * @param trankNo
     * @param r
     * @return
     */
    @NativeQuery
    List<StaDeliveryInfoCommand> findPrintExpressBillData2(@QueryParam(value = "plid") Long plid, @QueryParam(value = "staid") Long staid, @QueryParam(value = "packId") String packId, RowMapper<StaDeliveryInfoCommand> r);

    @NamedQuery
    StaDeliveryInfo findByExtTransOrderId(@QueryParam(value = "orderid") String orderId);

    /**
     * KJL PDA接口，获取物流单号与物流商关系
     * 
     * @param startTime
     * @param endTime
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<GetTransNoCommand> getTransNoForPda(@QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime, BeanPropertyRowMapper<GetTransNoCommand> beanPropertyRowMapper);

    /**
     * 物流面单维护
     * 
     * @param sc
     */
    @NativeUpdate
    void updateTransNoBySlipCode(@QueryParam("slipCode") String slipCode, @QueryParam("transNo") String transNo);

    /**
     * 根据id 查询物流面单 秒杀使用 KJL
     * 
     * @param id
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    String getTransNoById(@QueryParam("id") Long id, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 根据id,更新deliveryInfo信息 KJL
     * 
     * @param id
     * @param lpcode
     * @param trackingNo
     */
    @NativeUpdate
    void updateByPrimaryKey(@QueryParam("staId") Long id, @QueryParam("lpcode") String lpcode, @QueryParam("transNo") String trackingNo);


    @NativeQuery
    StaDeliveryInfoCommand findTheStaDeliveryInfoByStaId(@QueryParam("staId") Long staId, RowMapper<StaDeliveryInfoCommand> rowMapper);

    @NativeUpdate
    void updateStaDeliveryByStaId(@QueryParam("staId") Long staId, @QueryParam("lpcode") String lpcode, @QueryParam("trackingNo") String trackingNo, @QueryParam("returnReasonType") Long returnReasonType,
            @QueryParam("returnReasonMemo") String returnReasonMemo);

    @NativeUpdate
    void insertStaDeliveryByStaId(@QueryParam("staId") Long staId, @QueryParam("lpcode") String lpcode, @QueryParam("trackingNo") String trackingNo, @QueryParam("returnReasonType") Long returnReasonType,
            @QueryParam("returnReasonMemo") String returnReasonMemo);



    /**
     * t_wh_sta_delivery_info edw 数据表
     */
    @NativeQuery
    StaDeliveryInfoCommand findEdwTwhStaDeliveryInfo(@QueryParam(value = "staId") Long staId, RowMapper<StaDeliveryInfoCommand> rowMapper);

    @NativeQuery
    List<StaDeliveryInfoCommand> printSingleVmiDelivery(@QueryParam(value = "staid") Long staid, @QueryParam(value = "cartonId") Long cartonId, RowMapper<StaDeliveryInfoCommand> r);

    /**
     * 取得HUB所需数据
     * 
     * @param staCode
     * @param mailno
     * @param r
     * @return
     */
    @NativeQuery
    StaDeliveryInfoCommand getWxData(@QueryParam(value = "staCode") String staCode, @QueryParam(value = "mailno") String mailno, RowMapper<StaDeliveryInfoCommand> r);

    /**
     * SF定制子母单逻辑 母单运单号,字段运单号
     * 
     * @param id
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    String getParentAndSonMailNoById(@QueryParam("staId") Long id, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    List<StaDeliveryInfoCommand> printCategeryAndQtyDeliveryByStaId(@QueryParam(value = "staId") Long staId, RowMapper<StaDeliveryInfoCommand> r);

    @NativeQuery
    List<StaDeliveryInfoCommand> findWmsInvoiceOrderBillDataByInvoiceId(@QueryParam("id") Long id, BeanPropertyRowMapperExt<StaDeliveryInfoCommand> beanPropertyRowMapperExt);

    /**
     * @param ouId
     * @param userId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StaDeliveryInfo> findNeedHandOverOrderSummary(@QueryParam("ouId") Long ouId, @QueryParam("userId") Long userId, BeanPropertyRowMapper<StaDeliveryInfo> beanPropertyRowMapper);

    @NativeQuery
    StaDeliveryInfo findDeliveryInfoByTrNo(@QueryParam(value = "trackingNo") String trackingNo, BeanPropertyRowMapper<StaDeliveryInfo> beanPropertyRowMapper);

    /**
     * 根据id更新快递单号
     * 
     * @param Id
     * @param trackingNo
     */
    @NativeUpdate
    void updateDeliveryTrackingNoById(@QueryParam(value = "Id") Long Id, @QueryParam(value = "trackingNo") String trackingNo,@QueryParam("lpCode")String lpCode);

    /**
     * SF定制子母单逻辑 母单运单号,字段运单号(同getParentAndSonMailNoById,为避免PL/SQL: 数字或值错误 : 字符串缓冲区太小)
     * 
     * @param id
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<String> getParentAndSonMailNoById1(@QueryParam("staId") Long id, SingleColumnRowMapper<String> singleColumnRowMapper);
	
	@NativeQuery
    String queryTotalCatrgories(@QueryParam("slipCode") String slipCode, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    StockTransApplicationCommand findDeliveryInfoByStaRtn(@QueryParam("staId") Long staId, RowMapper<StockTransApplicationCommand> rowMapper);
}
