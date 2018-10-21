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
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.commandMapper.SkuStoreModeRowMapper;
import com.jumbo.wms.model.pda.InboundOnShelvesDetailCommand;
import com.jumbo.wms.model.pda.InboundOnShelvesSkuCommand;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BaseRowMapper;
import loxia.dao.support.BeanPropertyRowMapperExt;

@Transactional
public interface StvLineDao extends GenericEntityDao<StvLine, Long> {

    @NativeUpdate
    void deleteByPickinglist(@QueryParam(value = "plId") Long plId);

    @NativeUpdate
    void createByStaId(@QueryParam(value = "staId") Long staId);

    @NativeUpdate
    void createTIOutByStaId(@QueryParam(value = "staId") Long staId, @QueryParam(value = "stvId") Long stvId);

    @NativeUpdate
    void createPurchaseOutByStaId(@QueryParam(value = "staId") Long staId);

    @NativeUpdate
    void createInBoundStvLineByStaId(@QueryParam(value = "staId") Long staId, @QueryParam(value = "addiowner") String addiowner, @QueryParam(value = "ttid") Long ttid);

    @NativeUpdate
    void deleteByStvId(@QueryParam(value = "stvId") Long stvId);

    @NativeUpdate
    void createForCrossByStaId(@QueryParam(value = "staId") Long staId);

    @NativeUpdate
    void createForCrossByStaId2(@QueryParam(value = "staId") Long staId);

    @NativeUpdate
    void createForCrossByStaIdNoOwner(@QueryParam(value = "staId") Long staId);

    @NamedQuery
    StvLine findConfirmDiversityStvLine(@QueryParam(value = "stvId") Long stvId, @QueryParam(value = "skuId") Long skuId, @QueryParam(value = "statusName") String statusName, @QueryParam(value = "owner") String owner);

    @NamedQuery
    List<StvLine> findStvLinesByStaLineId(@QueryParam(value = "staLineId") Long staLineId);

    @NativeQuery
    List<StvLineCommand> findStvLinesByStaLineIdGroupBy(@QueryParam(value = "staLineId") Long staLineId, RowMapper<StvLineCommand> map);

    @NativeQuery
    List<StvLineCommand> findStvLineQtyByStaId(@QueryParam(value = "staId") Long staId, RowMapper<StvLineCommand> map);

    /**
     * 根据sta id 列表查询stv line
     * 
     * @param staId
     * @param direction 事物方向
     * @param sorts
     * @param map
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findByDirection(@QueryParam(value = "staId") Long staId, @QueryParam(value = "direction") Integer direction, Sort[] sorts, RowMapper<StvLineCommand> map);

    @NativeQuery(pagable = true)
    Pagination<StvLineCommand> findByDirectionByPage(int start, int pageSize, @QueryParam(value = "staId") Long staId, @QueryParam(value = "direction") Integer direction, Sort[] sorts, RowMapper<StvLineCommand> map);

    @NativeQuery
    List<StvLineCommand> findPoConfirmStvLineBySta(@QueryParam(value = "staId") Long staId, RowMapper<StvLineCommand> map);

    /**
     * 根据sta id 列表查询stv line
     * 
     * @param staIds
     * @param sorts
     * @param map
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findByStaId(@QueryParam(value = "staIds") List<Long> staIds, Sort[] sorts, RowMapper<StvLineCommand> map);

    @NamedQuery
    List<StvLine> findStvLineListByStvId(@QueryParam(value = "stvId") Long stvId);

    @NativeQuery
    List<StvLineCommand> findInboundTimeByStvId(@QueryParam(value = "stvId") Long stvId, RowMapper<StvLineCommand> beanPropertyRowMapper);

    @NativeQuery(withGroupby = true)
    List<StvLineCommand> findStvLineListByStvIdGroupBySku(@QueryParam(value = "stvId") Long stvId, RowMapper<StvLineCommand> beanPropertyRowMapper);


    @NativeQuery
    List<StvLineCommand> findStvLinesListByStvId(@QueryParam(value = "stvId") Long stvId, RowMapper<StvLineCommand> beanPropertyRowMapper);
    
    
    @NativeQuery
    List<StvLineCommand> findAgvStvLinesListByStvId(@QueryParam(value = "stvId") Long stvId, RowMapper<StvLineCommand> beanPropertyRowMapper);

    
    @NativeQuery
    List<StvLineCommand> findAgvTransitInnerByStvId(@QueryParam(value = "stvId") Long stvId, RowMapper<StvLineCommand> beanPropertyRowMapper);
    
    /**
     * 带有staLine信息的StvLine
     * 
     * @param stvId
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findStvLineCommandListByStvId(@QueryParam(value = "stvId") Long stvId, RowMapper<StvLineCommand> rowMapper);

    @NativeUpdate
    void deleteById(@QueryParam(value = "stvLineId") Long stvLineId);

    /**
     * 采购上架sku信息打印
     * 
     * @param stvid
     * @param ouid
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findPurchaseSkuInfo(@QueryParam(value = "stvid") Long stvid, @QueryParam(value = "ouid") Long ouid, BeanPropertyRowMapper<StvLineCommand> beanPropertyRowMapper);

    /**
     * STV不包含空库位的stvLine,说明已经做过了库位建议
     * 
     * @param stvId
     * @return
     */
    @NamedQuery
    List<StvLine> findStvLineLocationIsNullByStvId(@QueryParam(value = "stvId") Long stvId);



    /**
     * 根据id查询库内移动数据
     * 
     * @param staId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findStvLineByStaId(@QueryParam(value = "staId") Long staId, RowMapper<StvLineCommand> rowMapper);

    @NativeQuery
    List<StvLine> findStvLineListForTranCossByStaLineIdSql(@QueryParam(value = "stalineid") Long stalineid, @QueryParam(value = "staid") Long staid, RowMapper<StvLine> rowMapper, Sort[] sorts);

    @NativeQuery(withGroupby = true)
    List<StvLineCommand> findByStvIdGroupBySkuLocationOwner(@QueryParam(value = "stvId") Long stvId, RowMapper<StvLineCommand> r);

    /**
     * 根据条件STVID 删除stvline
     * 
     * @param stvId
     */
    @NativeUpdate
    void deleteStvLineByStvId(@QueryParam(value = "stvId") Long stvId);


    @NativeQuery
    List<StvLineCommand> findStvLineByPdaPostLog(@QueryParam(value = "stvId") Long staId, @QueryParam(value = "code") String code, @QueryParam(value = "pdaCode") String pdaCode, RowMapper<StvLineCommand> rowMapper);


    @NativeQuery
    List<StvLineCommand> findSnSkuByStvId(@QueryParam(value = "stvId") Long stvId, RowMapper<StvLineCommand> r);


    @NativeQuery
    List<StvLineCommand> findExpDateByStvId(@QueryParam(value = "stvId") Long stvId, RowMapper<StvLineCommand> r);



    @NativeQuery
    List<StvLineCommand> findNikeRfidByStvId(@QueryParam(value = "stvId") Long stvId, RowMapper<StvLineCommand> r);

    @NativeQuery
    List<StvLineCommand> findExpDateByStvId1(@QueryParam(value = "stvId") Long stvId, RowMapper<StvLineCommand> r);
    /**
     * 根据sta 和 作业方向查询stvLine
     * 
     * @param staId
     * @param direction
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findStvLineByStaIdAndDirection(@QueryParam(value = "staId") Long staId, @QueryParam(value = "direction") Integer direction, RowMapper<StvLineCommand> rowMapper);

    /**
     * 更新库区
     * 
     * @param stvId
     */
    @NativeUpdate
    void updateDistrict(@QueryParam(value = "stvId") Long stvId);

    /**
     * 查询作业单中商品上架模式
     * 
     * @param stvId
     * @param rm
     * @return Map<SKU_ID, InboundStoreMode>
     */
    @NativeQuery
    Map<Long, InboundStoreMode> findSkuStoreMode(@QueryParam(value = "stvid") Long stvId, SkuStoreModeRowMapper rm);



    /**
     * 查询禁止入库库位
     * 
     * @param stvId
     * @param ouid
     * @param r
     * @return
     */
    @NativeQuery
    List<String> findInboundErrorLocation(@QueryParam(value = "stvid") Long stvId, @QueryParam(value = "ouid") Long ouid, SingleColumnRowMapper<String> r);

    /**
     * 更新库位
     * 
     * @param stvId
     */
    @NativeUpdate
    void updateInboundLocation(@QueryParam(value = "stvId") Long stvId);

    /**
     * 查询建议库位stv line
     * 
     * @param staId
     * @param skuBarcode
     * @return
     */
    @NamedQuery
    StvLine findBySkuBarcode(@QueryParam(value = "staId") Long staId, @QueryParam(value = "skuBarcode") String skuBarcode);

    @NativeQuery
    List<StvLineCommand> findByPdaLog(@QueryParam(value = "staId") Long staId, @QueryParam(value = "stvId") Long stvId, @QueryParam(value = "code") String code, @QueryParam(value = "pdaCode") String pdaCode, RowMapper<StvLineCommand> r);

    @NativeQuery
    List<StvLineCommand> findErrorSkuQty(@QueryParam(value = "stvid") Long stvId, @QueryParam(value = "code") String code, @QueryParam(value = "pdaCode") String pdaCode, RowMapper<StvLineCommand> r);

    @NativeQuery
    List<StvLineCommand> findErrorSnSkuQty(@QueryParam(value = "stvid") Long stvId, @QueryParam(value = "code") String code, @QueryParam(value = "pdaCode") String pdaCode, RowMapper<StvLineCommand> r);

    @NativeQuery
    List<StvLineCommand> findPlanExeQtyByPda(@QueryParam(value = "stvid") Long stvId, @QueryParam(value = "code") String code, RowMapper<StvLineCommand> r);

    /**
     * 根据销售单据好查询当前配货批中对应的商品退货入库
     * 
     * @param slipCode 前置单据号
     * @param r
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findExpireDateByPickingList(@QueryParam(value = "groupStaCode") String groupStaCode, @QueryParam(value = "slipCode") String slipCode, RowMapper<StvLineCommand> r);


    @NativeUpdate
    void updateStvLineQuantity(@QueryParam(value = "stvId") Long stvId, @QueryParam(value = "qty") Long qty);

    /**
     * 指定作业单的行列表
     * 
     * @param staId
     * @param sorts
     * @param map
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StvLineCommand> findListByStaId(int start, int pageSize, @QueryParam(value = "staId") Long staId, @QueryParam Map<String, Object> m, Sort[] sorts, RowMapper<StvLineCommand> map);

    /**
     * 指定作业单的行列表
     * 
     * @param staId
     * @param sorts
     * @param map
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StvLineCommand> findHistoricalOrderDetailOperateListByStaId(int start, int pageSize, @QueryParam(value = "staId") Long staId, @QueryParam Map<String, Object> m, Sort[] sorts, RowMapper<StvLineCommand> map);

    @NativeUpdate
    void createPDAInBoundStvLine(@QueryParam(value = "staid") Long id, @QueryParam(value = "batchCode") String batchCode, @QueryParam(value = "ttid") Long ttid);

    @NativeUpdate
    void updateStvInvStruts(@QueryParam List<Long> stvId, @QueryParam(value = "invStatus") Long invStatus);

    @NativeUpdate
    void deleteByStaId(@QueryParam(value = "staID") Long staID);


    @NativeUpdate
    void deleteStvLineOver(@QueryParam(value = "stvId") Long stvId, @QueryParam(value = "staId") Long staId);

    @NativeQuery
    List<StvLine> findAllByStaId(@QueryParam(value = "staid") Long staid, BeanPropertyRowMapperExt<StvLine> beanPropertyRowMapperExt);

    @NativeQuery
    List<StvLine> findAllByStaIdSort(@QueryParam(value = "staid") Long staid, RowMapper<StvLine> r);

    @NativeQuery
    List<StvLineCommand> findStvLineListByStaId(@QueryParam(value = "staId") Long staId, RowMapper<StvLineCommand> rowMapper);

    @NativeQuery
    List<StvLineCommand> findOutboundStvLinesByStaId(@QueryParam(value = "staId") Long staId, RowMapper<StvLineCommand> rowMapper);

    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StvLineCommand> findCancelDetailInfoPage(int start, int pageSize, @QueryParam(value = "staid") Long staid, RowMapper<StvLineCommand> beanPropertyRowMapperExt, Sort[] sorts);

    @NativeQuery
    List<StvLineCommand> findOutBoundPackageLineByStaid(@QueryParam(value = "staid") Long staid, BeanPropertyRowMapperExt<StvLineCommand> beanPropertyRowMapperExt);


    /**
     * 查询出库商品是否存在暂存区的商品
     * 
     * @param ouId
     * @param r
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findGILocationByStvId(@QueryParam(value = "stvId") Long ouId, RowMapper<StvLineCommand> r);

    /**
     * 查询拣货的商品存放数是否为0
     * 
     * @param ouId
     * @param skuId
     * @param r
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findPickingReplenishQtyBySku(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "skuId") Long skuId, RowMapper<StvLineCommand> r);

    /**
     * 根据商品查询存货区存放库位
     * 
     * @param ouId
     * @param skuId
     * @param r
     * @return
     */
    @NativeQuery
    StvLineCommand findStockSuggestBySku(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "skuId") Long skuId, RowMapper<StvLineCommand> r);

    /**
     * 根据产品查询存货区存放库位
     * 
     * @param ouId
     * @param skuId
     * @param r
     * @return
     */
    @NativeQuery
    StvLineCommand findStockSuggestByProduct(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "skuId") Long skuId, RowMapper<StvLineCommand> r);

    @NativeQuery
    List<StvLineCommand> findRecevingMoveOutboundSuggest(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "districtCode") String districtCode, @QueryParam(value = "locationCode") String locationCode,
            @QueryParam(value = "skuCode") String skuCode, @QueryParam(value = "barCode") String barCode, @QueryParam(value = "supplierCode") String supplierCode, RowMapper<StvLineCommand> r);

    @NativeQuery(pagable = true)
    Pagination<StvLineCommand> findSkuCollectInfoPage(int start, int pageSize, @QueryParam(value = "invcheckid") Long invcheckid, Sort[] sorts, BeanPropertyRowMapperExt<StvLineCommand> beanPropertyRowMapperExt);

    @NativeQuery(pagable = true)
    Pagination<StvLineCommand> findSkuAdjustDetailInfo(int start, int pageSize, @QueryParam(value = "invcheckid") Long invcheckid, Sort[] sorts, BeanPropertyRowMapperExt<StvLineCommand> beanPropertyRowMapperExt);

    @NativeQuery
    Map<Long, Long> findCompleteStvLineByStaId(@QueryParam("staid") Long staid, BaseRowMapper<Map<Long, Long>> baseRowMapper);

    @NativeUpdate
    void updateQuantityById(@QueryParam("stvlId") Long stvlId, @QueryParam("qty") long qty);

    /***
     * 删除原来新建的-非取消的stvline信息
     * 
     * @param staId
     */
    @NativeUpdate
    void deleteRawStvlineByStaId(@QueryParam("staId") Long staId);

    @NativeQuery
    List<StvLineCommand> findByStaIdAndSkuIdInvStatusId(@QueryParam("stvId") Long stvId, @QueryParam("skuId") Long skuId, @QueryParam("invStatusId") Long invStatusId, RowMapper<StvLineCommand> r);

    @NativeUpdate
    void generateByStvId(@QueryParam("inStvId") Long inStvId, @QueryParam("outStvId") Long outStvId, @QueryParam("direction") Integer direction, @QueryParam("addOuId") Long addOuId, @QueryParam("transctionId") Long transctionId);


    /**
     * 根据GI库区库位简历stv明细
     * 
     * @param locId
     */
    @NativeUpdate
    void createByGILocId(@QueryParam(value = "locId") Long locId, @QueryParam(value = "stvId") Long stvId);

    /**
     * 查询剩余数量
     * 
     * @param outStvId
     * @param inStvId
     * @param r
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findOutQtyIsInQty(@QueryParam("outStvId") Long outStvId, @QueryParam("inStvId") Long inStvId, RowMapper<StvLineCommand> r);


    /**
     * 查询入库收货商品
     * 
     * @param start
     * @param pageSize
     * @param stvId
     * @param sorts
     * @param map
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StvLineCommand> findInboundStvLine(int start, int pageSize, @QueryParam(value = "stvId") Long stvId, Sort[] sorts, RowMapper<StvLineCommand> map);

    /**
     * 查询入库收货商品(入库上架-手动)
     * 
     * @param stvId
     * @param map
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findInboundStvLineHand(@QueryParam(value = "stvId") Long stvId, RowMapper<StvLineCommand> map);

    /**
     * 查询存在差异的数据
     * 
     * @param stvId
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findConfirmDiversity(@QueryParam(value = "stvId") Long stvId, RowMapper<StvLineCommand> map);

    /**
     * 查询存在差异的数据
     * 
     * @param stvId
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findInBoundIsSN(@QueryParam(value = "stvId") Long stvId, @QueryParam(value = "stvListId") List<Long> stvList, @QueryParam(value = "isSkuSn") Boolean isSkuSn, RowMapper<StvLineCommand> map);

    /**
     * 查询上架数据
     * 
     * @param stvId
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findInBound(@QueryParam(value = "stvId") Long stvId, @QueryParam(value = "stvListId") List<Long> stvList, RowMapper<StvLineCommand> map);

    /**
     * 虚拟仓收货 查询存在差异的数据
     * 
     * @param stvId
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findInventInBoundIsSN(@QueryParam(value = "staId") Long staId, @QueryParam(value = "staListId") List<Long> staList, @QueryParam(value = "isSkuSn") Boolean isSkuSn, RowMapper<StvLineCommand> map);


    /**
     * 查询存在差异的数据
     * 
     * @param stvId
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findConfirmDiversityError(@QueryParam(value = "stvId") Long stvId, RowMapper<StvLineCommand> map);

    /**
     * 查询明细
     * 
     * @param start
     * @param pageSize
     * @param staId
     * @param map
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StvLineCommand> findPdaInboundLine(int start, int pageSize, @QueryParam(value = "stvId") Long stvId, @QueryParam Map<String, Object> map, RowMapper<StvLineCommand> rowMapper, Sort[] sorts);


    /**
     * 查询PDA上架明细
     * 
     * @param start
     * @param pageSize
     * @param staId
     * @param map
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StvLineCommand> findPdaInboundNotDifLine(int start, int pageSize, @QueryParam(value = "stvId") Long stvId, @QueryParam Map<String, Object> map, RowMapper<StvLineCommand> rowMapper, Sort[] sorts);

    /**
     * 查询PDA上架明细
     * 
     * @param start
     * @param pageSize
     * @param staId
     * @param map
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StvLineCommand> findPdaInboundDifLine(int start, int pageSize, @QueryParam(value = "stvId") Long stvId, @QueryParam Map<String, Object> map, RowMapper<StvLineCommand> rowMapper, Sort[] sorts);


    /**
     * 查询Pda接口上架数据Deatail
     * 
     * @param code
     * @param uniqCode
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<InboundOnShelvesDetailCommand> findInboundOnShelvesDetail(@QueryParam("code") String code, @QueryParam("uniqCode") String uniqCode, BeanPropertyRowMapper<InboundOnShelvesDetailCommand> beanPropertyRowMapper);

    /**
     * 查询PDA接口上架数据SKU
     * 
     * @param code
     * @param uniqCode
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<InboundOnShelvesSkuCommand> findInboundOnShelvesSku(@QueryParam("code") String code, @QueryParam("uniqCode") String uniqCode, BeanPropertyRowMapper<InboundOnShelvesSkuCommand> beanPropertyRowMapper);


    /**
     * 根据上架单更新上架数量
     * 
     * @param locId
     */
    @NativeUpdate
    void updateAddedQtyByShelves(@QueryParam(value = "stvId") Long stvId, @QueryParam(value = "shelvesStvId") Long shelvesStvId);


    /**
     * 查询未完成的商品
     * 
     * @param stvId
     * @param map
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findNotFinishedStvLine(@QueryParam(value = "stvId") Long stvId, RowMapper<StvLineCommand> map);

    /**
     * 根据事务方向查看库内移动单据明细
     * 
     * @param code
     * @param i
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<InboundOnShelvesDetailCommand> findLineByDirection(@QueryParam(value = "code") String code, @QueryParam(value = "direction") int i, BeanPropertyRowMapper<InboundOnShelvesDetailCommand> beanPropertyRowMapper);

    /**
     * Pda接口获取拣货单数据detail
     * 
     * @param code
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<InboundOnShelvesDetailCommand> findPickDetail(@QueryParam("code") String code, BeanPropertyRowMapper<InboundOnShelvesDetailCommand> beanPropertyRowMapper);

    /**
     * 查寻收货时的Error
     * 
     * @param stvId
     * @param shelvesStvId
     * @param map
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findInboundError(@QueryParam(value = "stvId") Long stvId, RowMapper<StvLineCommand> map);

    /**
     * 本次收货明细与计划收货明细差异
     * 
     * @param stvId
     * @param map
     * @return List<StvLineCommand>
     * @throws
     */
    @NativeQuery
    List<StvLineCommand> findStvLineDifferenceWithPlanInbound(@QueryParam(value = "stvId") Long stvId, RowMapper<StvLineCommand> map);

    /**
     * 创建新的取消stv明细行 KJL
     * 
     * @param id
     * @param typeId
     * @param invId
     */
    @NativeUpdate
    void insertNewStvLine(@QueryParam("stvId") Long id, @QueryParam("typeId") Long typeId, @QueryParam("invId") Long invId);


    /**
     * 查询 移库 出库和入库是不相等数据
     * 
     * @param id
     * @param typeId
     * @param invId
     */
    @NativeQuery
    List<StvLineCommand> findNotEqualSku(@QueryParam("staId") Long id, RowMapper<StvLineCommand> map);


    /**
     * 查询相关出库的批次以及批次时间
     * 
     * @param staType
     * @param staCode
     * @param slipCode
     * @param slipCode1
     * @param map
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findOutBatchCode(@QueryParam("groupStaCode") String groupStaCode, @QueryParam("staCode") String staCode, @QueryParam("slipCode") String slipCode, @QueryParam("slipCode1") String slipCode1, RowMapper<StvLineCommand> map);


    /**
     * 查询库间移动出库数据
     * 
     * @param staId
     * @param map
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findTRansitCrossOutList(@QueryParam("staId") Long staId, RowMapper<StvLineCommand> map);

    /**
     * 查询退货批次
     * 
     * @param stvId
     * @param map
     * @return
     */
    @NativeQuery
    Date findInboundTimeByBatchCode(@QueryParam("batchCode") String batchCode, SingleColumnRowMapper<Date> rowMap);

    /**
     * 查询Stv对应StvLine 的执行时间
     * 
     * @param stvId
     * @param map
     * @return
     */
    @NativeQuery
    Long findStvSkuNumByStvId(@QueryParam("stvId") Long stvId, SingleColumnRowMapper<Long> rowMap);

    /**
     * 查询出库单的批次号
     * 
     * @param staId
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findBatchCodeByStaId(@QueryParam("staId") Long staId, RowMapper<StvLineCommand> map);

    @NativeQuery
    List<StvLineCommand> findOutBoundStvLine(@QueryParam("staId") Long staId, RowMapper<StvLineCommand> map);

    /**
     * 查询出库的StvLine明细 合并反馈OMS KJL
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StvLineCommand> getFinishedStvLineByStaId(@QueryParam("staId") Long id, BeanPropertyRowMapper<StvLineCommand> beanPropertyRowMapper);


    @NativeQuery
    List<StvLineCommand> findQtySkuByStaId(@QueryParam("staId") Long id, BeanPropertyRowMapper<StvLineCommand> beanPropertyRowMapper);

    @NamedQuery
    List<StockTransVoucher> findStvByStaId(@QueryParam("staId") Long id);

    /**
     * 查询待入库的明细批次号入库时间 店铺 KJL
     * 
     * @param id
     * @param object
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findStvLineWithBatchCodeAndInboundTime(@QueryParam("staId") Long id, @QueryParam("slipCode1") String slipCode1, BeanPropertyRowMapper<StvLineCommand> beanPropertyRowMapper);


    /**
     * 查询待入库的明细批次号入库时间 店铺 KJL 带商品信息
     * 
     * @param id
     * @param object
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findStvLineBatchCodeAndInboundTime(@QueryParam("staId") Long id, @QueryParam("slipCode1") String slipCode1, BeanPropertyRowMapper<StvLineCommand> beanPropertyRowMapper);

    /**
     * 查询方向采购退货入库待上架列表
     * 
     * @param stvList
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findProcuremetReturnInboundByStvListId(@QueryParam("stvListId") List<Long> stvListId, RowMapper<StvLineCommand> map);

    /**
     * 查询采购出库过期时间
     * 
     * @param slipCode
     * @param r
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findExpireDateByStaSlipCode(@QueryParam(value = "slipCode") String slipCode, RowMapper<StvLineCommand> r);

    /**
     * 查询已完成的作业单明细 合并商品，库存状态，店铺
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StvLineCommand> getAllFinishedSku(@QueryParam("staId") Long id, BeanPropertyRowMapper<StvLineCommand> beanPropertyRowMapper);

    /**
     * 退仓占用库存后删除原始stvLine 重建新的stvLine 记录批次号入库时间 KJL
     * 
     * @param id
     */
    @NativeUpdate
    void deleteLineById(@QueryParam("staId") Long id);

    /**
     * t_wh_stv edw 数据表
     */
    @NativeQuery
    List<StvLineCommand> findEdwTwhStvLine(@QueryParam(value = "stvId") Long stvId, RowMapper<StvLineCommand> rowMapper);

    /**
     * 转店- 根据stv查询明细数据 优化
     * 
     * @param stvId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findInfoByStvId(@QueryParam(value = "stvId") Long stvId, RowMapper<StvLineCommand> rowMapper);

    @NativeQuery
    List<StvLine> findSkuQtybystaId(@QueryParam("staId") Long id, RowMapper<StvLine> beanPropertyRowMapperExt);

    /**
     * @param id
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findOnShelvesByStaId(@QueryParam("staId") Long id, BeanPropertyRowMapperExt<StvLineCommand> beanPropertyRowMapperExt);

    /**
     * AD行取消未打印拣货单之前释放库存删除占用明细
     * 
     * @param lineNo
     * @param id
     */
    @NativeUpdate
    void deleteInvForAdLineCancel(@QueryParam("lineNo") String lineNo, @QueryParam("staId") Long id);

    /**
     * @param id
     */
    @NativeUpdate
    void deleteStvLineBySta(@QueryParam("staId") Long id);

    /**
     * 通过skv和sku查询对应stvLine
     * 
     * @param stvId
     * @param skuId
     * @return
     */
    @NamedQuery
    StvLine findStvLineByStvAndSku(@QueryParam(value = "stvId") Long stvId, @QueryParam(value = "skuId") Long skuId);

    /**
     * 通过skv和sku查询对应stvLines
     * 
     * @param stvId
     * @param skuId
     * @return
     */
    @NamedQuery
    List<StvLine> findStvLinesByStvAndSku(@QueryParam(value = "stvId") Long stvId, @QueryParam(value = "skuId") Long skuId);

    /**
     * 通过stvId和staLineId查询对应的stvLine
     * 
     * @param stvId
     * @param staLineId
     * @return
     */
    @NamedQuery
    List<StvLine> findStvLinesByStvAndStaLine(@QueryParam(value = "stvId") Long stvId, @QueryParam(value = "staLineId") Long staLineId);

    /**
     * 根据stvLineID获取店铺
     * 
     * @param id
     * @param rowMap
     * @return
     */
    @NativeQuery
    String findOwnerByStvLineId(@QueryParam("id") Long id, SingleColumnRowMapper<String> rowMap);
    
    /**
     * 查询相关出库的批次以及批次时间（从备份表查询）
     * 
     * @param staType
     * @param staCode
     * @param slipCode
     * @param slipCode1
     * @param map
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findOutBatchCodeBackups(@QueryParam("groupStaCode") String groupStaCode, @QueryParam("staCode") String staCode, @QueryParam("slipCode") String slipCode, @QueryParam("slipCode1") String slipCode1, RowMapper<StvLineCommand> map);

}
