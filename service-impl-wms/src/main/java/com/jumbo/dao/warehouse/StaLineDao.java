/**
 * \ * Copyright (c) 2010 Jumbomart All Rights Reserved.
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
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.commandMapper.MapRowMapper;
import com.jumbo.dao.commandMapper.PackingObjRowMapper;
import com.jumbo.wms.model.MongoDBInventoryOcp;
import com.jumbo.wms.model.jasperReport.PackingObj;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.VMIBackOrder;

import loxia.annotation.DynamicQuery;
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
public interface StaLineDao extends GenericEntityDao<StaLine, Long> {

    /**
     * 查询打印装箱清单数据（显示库位）
     * 
     * @param pickingId
     * @param staid
     * @param packingObjRowMapper
     * @return
     */
    @NativeQuery
    Map<Long, PackingObj> findPackingPageShowLocation(@QueryParam(value = "plId") Long pickingId, @QueryParam(value = "staId") Long staid, PackingObjRowMapper packingObjRowMapper);

    /**
     * 查询打印装箱清单数据（显示库位）
     * 
     * @param pickingId
     * @param staid
     * @param packingObjRowMapper
     * @return
     */
    @NativeQuery
    Map<Long, PackingObj> findPackingPageShowLocationNike(@QueryParam(value = "plId") Long pickingId, @QueryParam(value = "staId") Long staid, PackingObjRowMapper packingObjRowMapper);

    /**
     * 查询打印装箱清单数据（显示库位）
     * 
     * @param pickingId
     * @param staid
     * @param packingObjRowMapper
     * @return
     */
    @NativeQuery
    Map<Long, PackingObj> findPackagedPageShowLocation(@QueryParam(value = "plId") Long pickingId, @QueryParam(value = "ppId") Long ppId, @QueryParam(value = "staId") Long staid, PackingObjRowMapper packingObjRowMapper);

    /**
     * 查询打印装箱清单数据（不显示库位）
     * 
     * @param pickingId
     * @param staid
     * @param packingObjRowMapper
     * @return
     */
    @NativeQuery
    Map<Long, PackingObj> findPackingPageNoLocation(@QueryParam(value = "plId") Long pickingId, @QueryParam(value = "staId") Long staid, @QueryParam(value = "sort") String sort, PackingObjRowMapper packingObjRowMapper);

    @NativeQuery
    List<StaLineCommand> findStalinelist(@QueryParam(value = "staId") Long staId, Sort[] sort, RowMapper<StaLineCommand> rowMapper);

    @NativeQuery
    List<StaLineCommand> findStaLineByStaId5(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> rowMapper);

    @NativeQuery
    List<StaLineCommand> findSnOrExpDateSkuByStaId(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> rowMapper);

    @NativeUpdate
    void updateStaLineIsCancel(@QueryParam(value = "isCancel") Boolean isCancel, @QueryParam(value = "qty") Long qty, @QueryParam(value = "lineNo") String lineNo, @QueryParam(value = "id") Long id);



    /**
     * 查询打印装箱清单数据（不显示库位）
     * 
     * @param pickingId
     * @param staid
     * @param packingObjRowMapper
     * @return
     */
    @NativeQuery
    Map<Long, PackingObj> findPackagedPageNoLocation(@QueryParam(value = "plId") Long pickingId, @QueryParam(value = "ppId") Long ppId, @QueryParam(value = "staId") Long staid, PackingObjRowMapper packingObjRowMapper);


    /**
     * 查询是否行取消
     * 
     * @param barCode
     * @param staId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    StaLine findStaLineIsCancel(@QueryParam(value = "isCancel") Boolean isCancel, @QueryParam(value = "lineNo") String lineNo, @QueryParam("staId") Long staId, RowMapper<StaLine> rowMapper);


    @NativeQuery
    List<StaLineCommand> findLineAndSkuByStaId(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> rowMapper);

    /**
     * 查寻
     * 
     * @param start
     * @param pageSize
     * @param staId
     * @param sorts
     * @param map
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StaLineCommand> findStaLineSnByStaId(int start, int pageSize, @QueryParam(value = "staId") Long staId, Sort[] sorts, RowMapper<StaLineCommand> rowMapper);

    // @NativeQuery
    // Map<String, List<StaLineCommand>> findSoInfoBySoCodeNoShare(@QueryParam("soCodes")
    // List<String> soCodes, StaLineForSoRowMapper r);

    // @NativeQuery
    // Map<String, List<StaLineCommand>> findSoInfoBySoCodeWithShare(@QueryParam("soCodes")
    // List<String> soCodes, StaLineForSoRowMapper r);

    @NativeQuery
    List<StaLineCommand> findOccupySkuForPda(@QueryParam("plcode") String plcode, RowMapper<StaLineCommand> rowMapper);

    /**
     * 指定作业单的行列表
     * 
     * @param staId 作业单ID
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findStaLineListByStaIdSql(@QueryParam(value = "staId") Long staId, @QueryParam(value = "isNotFinish") boolean isNotFinish, Sort[] sorts, RowMapper<StaLineCommand> rowMapper);


    @NativeUpdate
    void updateStaLineOrderQtyIsCancel(@QueryParam(value = "qty") Long qty, @QueryParam(value = "lineNo") String lineNo, @QueryParam(value = "id") Long id);

    

    @NativeUpdate
    void updateStaLineQtyIsCancel(@QueryParam(value = "qty") Long qty, @QueryParam(value = "lineNo") String lineNo, @QueryParam(value = "id") Long id);

    
    /**
     * 指定作业单的行列表
     * 
     * @param staId 作业单ID
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findStaLineListByStaIdSql2(@QueryParam(value = "staId") Long staId, @QueryParam(value = "isNotFinish") boolean isNotFinish, Sort[] sorts, RowMapper<StaLineCommand> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<StaLineCommand> findStaLineListByStaIdSqlByPage(int start, int pageSize, @QueryParam(value = "staId") Long staId, @QueryParam(value = "isNotFinish") Boolean isNotFinish, Sort[] sorts, RowMapper<StaLineCommand> rowMapper);



    @NativeQuery
    List<StaLineCommand> findStaLineByOrderCode(@QueryParam(value = "id") Long id, RowMapper<StaLineCommand> beanPropertyRowMapper);

    /**
     * 查询手动收货明细数据
     * 
     * @param staId
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findLineListByStaId(@QueryParam(value = "staId") Long staId, Sort[] sorts, RowMapper<StaLineCommand> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<StaLineCommand> findStaLineByStaFinish(int start, int pageSize, @QueryParam(value = "staId") Long staId, Sort[] sorts, RowMapper<StaLineCommand> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<StaLineCommand> findStaLinePageListByStaId(int start, int pageSize, @QueryParam(value = "staId") Long staId, Sort[] sorts, RowMapper<StaLineCommand> rowMapper);

    @NativeQuery
    List<StaLineCommand> findVMIFlittingEnterLine(@QueryParam(value = "staId") Long staId, @QueryParam(value = "isNotFinish") Boolean isNotFinish, Sort[] sorts, RowMapper<StaLineCommand> rowMapper);

    /**
     * 指定作业单的行列表
     * 
     * @param staId 作业单ID
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findStaLineListByStaIdWithSn(@QueryParam(value = "staId") Long staId, @QueryParam(value = "isNotFinish") Boolean isNotFinish, @QueryParam(value = "isSnSku") boolean isSnSku,
            @QueryParam(value = "isDistinguishSnSku") boolean isDistinguishSnSku, Sort[] sorts, RowMapper<StaLineCommand> rowMapper);

    /**
     * 查询配货中的作业单
     * 
     * @param plId
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findOccpiedStaLineByPlId(@QueryParam(value = "plid") Long plId, RowMapper<StaLineCommand> r, Sort[] sorts);

    @NativeQuery
    List<StaLineCommand> findOccpiedStaLineForPgSkuByPlId(@QueryParam(value = "plid") Long plId, @QueryParam(value = "pgskuid") Long pgSkuId, RowMapper<StaLineCommand> r, Sort[] sorts);


    @NativeQuery
    Map<String, Long> findStalAddBarcode(@QueryParam(value = "staId") Long staId, MapRowMapper r);

    @NativeQuery
    Map<String, Long> findStalAddBarcodeB(@QueryParam(value = "staId") Long staId, MapRowMapper r);

    /**
     * 
     * @param staId
     * @param isNotFinish
     * @param isSnSku
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findkuByStaIdAndIsSn(@QueryParam(value = "staId") Long staId, @QueryParam(value = "isSnSku") boolean isSnSku, RowMapper<StaLineCommand> rowMapper);


    @NativeQuery
    List<StaLineCommand> findStaLineByStaIdAndNotSNSku(@QueryParam(value = "staId") Long staId, @QueryParam(value = "isSnSku") boolean isSnSku, RowMapper<StaLineCommand> rowMapper);

    @NativeQuery
    List<StaLineCommand> findPredefinedOutCreateInv(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> rowMapper);



    /**
     * 指定作业单的行列表
     * 
     * @param staId
     * @param sorts
     * @param map
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StaLineCommand> findListByStaId(int start, int pageSize, @QueryParam(value = "staId") Long staId, Sort[] sorts, RowMapper<StaLineCommand> map);

    /**
     * 指定作业单的行列表
     * 
     * @param staId
     * @param sorts
     * @param map
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StaLineCommand> findHistoricalOrderDetailListByStaId(int start, int pageSize, @QueryParam(value = "staId") Long staId, Sort[] sorts, RowMapper<StaLineCommand> map);

    /**
     * 根据staId查询作业单占用明细
     * 
     * @param staId
     * @param map
     * @return List<StaLineCommand>
     * @throws
     */
    @NativeQuery
    List<StaLineCommand> findOccupiedStaLineByStaId(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> map);

    @NativeQuery
    Integer checkPickingSkuByStaId(@QueryParam(value = "staId") Long staId, SingleColumnRowMapper<Integer> s);

    /**
     * 分拣核对，查询订单核对明细信息：考虑合单情况 fanht
     * 
     * @param staId
     * @param map
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findStaLineInfoByStaId(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> map);

    /**
     * 分拣核对，查询订单核对明细信息：考虑合单情况 fanht
     * 
     * @param staId
     * @param map
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findStaLineGiftByStaId(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> map);

    @NativeUpdate
    void updateCompleteQuantity(@QueryParam(value = "id") Long id, @QueryParam(value = "completeQuantity") Long completeQuantity);

    @NativeUpdate
    void updateCompleteQuantityByStaId(@QueryParam(value = "staId") Long staId);

    /**
     * 查询状态列表中的所有的sta执行商品总数
     * 
     * @param staList
     * @param statusList
     * @param s
     * @return
     */
    @NativeQuery
    BigDecimal findTotalSkuCountByStaList(@QueryParam(value = "staList") List<Long> staList, @QueryParam(value = "statusList") List<Integer> statusList, SingleColumnRowMapper<BigDecimal> s);

    @NativeQuery
    List<StaLine> findStaLineListByStvId(@QueryParam(value = "stvId") Long stvId, RowMapper<StaLine> rowMapper);

    @NamedQuery
    StaLine findStaLineBySkuId(@QueryParam("skuId") Long skuId, @QueryParam("staId") Long staId);

    /**
     * 主要用于采购上架Excel导入,根据sku的条码/编码+扩展属性查询StaLine
     * 
     * @param barCode
     * @param jmCode
     * @param keyProp
     * @param staId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    StaLine findStaLineByBarCodeOrCodePropsSql(@QueryParam(value = "barCode") String barCode, @QueryParam(value = "jmCode") String jmCode, @QueryParam(value = "keyProp") String keyProp, @QueryParam("staId") Long staId, RowMapper<StaLine> rowMapper);

    /**
     * 主要用于采购上架Excel导入,根据sku的扩展条码查询StaLine
     * 
     * @param barCode
     * @param staId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    StaLine findStaLineByAddBarCodeSql(@QueryParam(value = "barCode") String barCode, @QueryParam("staId") Long staId, RowMapper<StaLine> rowMapper);

    /**
     * 根据Sta查询是否所有商品有足够库存
     * 
     * @param staId
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findNotEnoughtSalesAvailInvBySta(@QueryParam("staId") Long staId, RowMapper<StaLineCommand> rowMapper);

    /**
     * 根据staID查询
     * 
     * @param staIdff
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StaLineCommand> findStaLineOthersOperate(int start, int pageSize, @QueryParam(value = "staId") Long staId, Sort[] sorts, RowMapper<StaLineCommand> rowMapper);

    /**
     * id
     */
    @NativeQuery
    List<StaLineCommand> operationOthersOperateQueryDetails2(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> rowMapper);

    /**
     * 查询staId
     * 
     * @param staIdff
     * @return
     */
    @NativeQuery
    Pagination<StaLineCommand> findStaLineOthersOperate2(@QueryParam(value = "staId") Long staId, Sort[] sorts, RowMapper<StaLineCommand> rowMapper);

    @NativeQuery
    List<StaLineCommand> findStaLinesVmiTransDetails2(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> rowMapper);

    @NativeQuery
    List<StaLineCommand> findStaLinesVmiTransDetails(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> rowMapper);

    @NativeQuery
    List<StaLineCommand> findStaLinesVmiReturnDetails(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> rowMapper);

    
    @NativeQuery
    List<StaLineCommand> findByStaIdByBatis(@QueryParam(value = "id") Long staId, RowMapper<StaLineCommand> rowMapper);

    

    /**
     * 获取sta line
     * 
     * @param staId
     * @return
     */
    @NamedQuery
    List<StaLine> findByStaId(@QueryParam(value = "staId") Long staId);


    /**
     * 
     * @param staId
     * @param owner
     * @param status_id
     * @return
     */
    @DynamicQuery
    List<StaLine> findByOwnerAndStatus(@QueryParam(value = "staId") Long staId, @QueryParam(value = "skuId") Long skuId, @QueryParam(value = "owner") String owner, @QueryParam(value = "status_id") Long status_id);

    /**
     * 
     * @param staId
     * @param owner
     * @param invStatusId
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findByOwnerAndStatusSql(@QueryParam(value = "staId") Long staId, @QueryParam(value = "skuId") Long skuId, @QueryParam(value = "owner") String owner, @QueryParam(value = "status_id") Long status_id,
            RowMapper<StaLineCommand> rowMapper);

    /**
     * 
     * @param staId
     * @param skuId
     * @param owner
     * @param status_id
     * @param rowMapper
     * @return 根据sta 店铺查询 有sn商品的staLine
     */
    @NativeQuery
    List<StaLineCommand> findByStatusAndSn(@QueryParam(value = "staId") Long staId, @QueryParam(value = "skuId") Long skuId, @QueryParam(value = "status_id") Long status_id, RowMapper<StaLineCommand> rowMapper);

    @NativeQuery
    List<StaLineCommand> findLineSkuBySta(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> rowMapper);

    /**
     * 根据Sta查询是否所有商品有足够库存
     * 
     * @param staId
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findNotFoundSalesAvailInvBySta(@QueryParam("staId") Long staId, @QueryParam("mainOwner") String mainOwner, @QueryParam("isMainOwner") Boolean isMainOwner, RowMapper<StaLineCommand> rowMapper);

    @NativeQuery
    List<StaLineCommand> findStaLinesQtyByStaId(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> rowMapper);

    /**
     * 更新sta line sku cost
     * 
     * @param staId
     * @param ouCompId
     */
    @NativeUpdate
    void updateSkuCostBySta(@QueryParam(value = "staId") Long staId, @QueryParam(value = "ouCompId") Long ouCompId);

    /**
     * 根据条件STaID 删除staline
     * 
     * @param stvId
     */
    @NativeUpdate
    void deleteStaLineByStaId(@QueryParam(value = "staId") Long staId);

    @NativeUpdate
    void createByStaId(@QueryParam(value = "staId") Long staId);

    @NativeUpdate
    void updateStvLineOwnerBySta(@QueryParam("staId") Long staId);

    @NativeQuery
    Map<String, Long> findIsSnSkuQtyByStaId(@QueryParam("staid") Long staid, @QueryParam("ouid") Long ouid, MapRowMapper mapRowMapper);


    @NativeQuery
    Map<String, Long> findIsSnSkuCodeQtyByStaId(@QueryParam("staid") Long staid, @QueryParam("ouid") Long ouid, @QueryParam("type") Boolean type, MapRowMapper mapRowMapper);

    @NativeQuery
    Map<String, Long> findIsSnSkuCodeUnCompleteQtyByStaId(@QueryParam("staid") Long staid, @QueryParam("ouid") Long ouid, @QueryParam("type") Boolean type, MapRowMapper mapRowMapper);



    @NativeQuery
    Integer findSkuTypeNum(@QueryParam("staId") Long staId, SingleColumnRowMapper<Integer> singleColumnRowMapper);


    @NativeQuery
    Integer findTotalSkuCountByHoId(@QueryParam("hoid") Long hoid, SingleColumnRowMapper<Integer> singleColumnRowMapper);


    @NativeQuery
    Long findTotalQtyByStaId(@QueryParam("staId") Long staId, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    List<StaLineCommand> findByStaIdGroupSkuAnd(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> r);


    @NativeQuery
    List<StaLineCommand> findByPredefinedOutByStaId(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> r);

    @NativeQuery
    List<StaLineCommand> findBySkuStorMode(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> r);


    @NativeQuery
    Integer findStaCountForMetroShop(@QueryParam(value = "plid") Long plid, @QueryParam(value = "owner") String owner, SingleColumnRowMapper<Integer> singleColumnRowMapper);

    @NativeQuery
    List<StaLineCommand> findVmiUnfreezeDetails(@QueryParam(value = "staid") Long staid, BeanPropertyRowMapper<StaLineCommand> beanPropertyRowMapper);

    /**
     * 
     * @param staID
     * @param id
     * @param skuid
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    StaLine findByInvStatusSkuSta(@QueryParam(value = "staId") Long staId, @QueryParam(value = "invStatusId") Long invStatusId, @QueryParam(value = "skuId") Long skuId, BeanPropertyRowMapper<StaLine> beanPropertyRowMapper);

    /**
     * ad
     * 
     * @param staId
     * @param skuId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    StaLine findBySkuSta(@QueryParam(value = "staId") Long staId, @QueryParam(value = "skuId") Long skuId, BeanPropertyRowMapper<StaLine> beanPropertyRowMapper);


    @NativeQuery
    List<StaLineCommand> findStaLineAndEspPO(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> rowMapper);

    @NativeUpdate
    void updateStaInvStruts(@QueryParam Long staLineId, @QueryParam(value = "invStatus") Long invStatus);

    @NativeUpdate
    void updateStaLineCompleteQuantity(@QueryParam(value = "id") Long id, @QueryParam(value = "completeQuantity") Long completeQuantity);

    @NativeUpdate
    void updateStaLineCompleteQuantityByStv(@QueryParam(value = "staid") Long staid);

    @NativeQuery
    List<StaLineCommand> findStalineByStaId(@QueryParam(value = "staId") Long staId, @QueryParam(value = "staCode") String staCode, RowMapper<StaLineCommand> rowMapper);

    @NativeQuery
    List<StaLineCommand> findStalineByStaIdCheck(@QueryParam(value = "staId") Long staId, @QueryParam(value = "staCode") String staCode, @QueryParam(value = "msgId") Long msgId, @QueryParam(value = "source") String source,
            RowMapper<StaLineCommand> rowMapper);

    @NativeQuery
    List<StaLineCommand> findStalineByStaId2(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> rowMapper);

    @NativeQuery
    List<StaLineCommand> findStalineByStaId3(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> rowMapper);

    @NativeQuery
    List<StaLineCommand> findStaLineListByStaId(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> rowMapper);



    @NamedQuery
    List<StaLineCommand> findStaLineListByStaId2(@QueryParam(value = "staId") Long staId);

    /**
     * 查询STA明细，考虑合单情况 fanht
     * 
     * @param staId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findStaLineAndSkuByStaId(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> rowMapper);

    @NativeQuery
    List<StaLineCommand> findPickingSku(Sort[] sorts, @QueryParam(value = "plid") Long plid, @QueryParam(value = "ouid") Long ouid, BeanPropertyRowMapperExt<StaLineCommand> beanPropertyRowMapperExt);

    @NativeQuery
    StaLineCommand findStaLineSkuByStaId(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> rowMapper);

    /**
     * 二次分拣意见，查询配货详情 fanht
     * 
     * @param pickinglistId
     * @param ouid
     * @param sorts
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findStaLineByPickingId(Sort[] sorts, @QueryParam(value = "plid") Long plid, @QueryParam(value = "ouid") Long ouid, BeanPropertyRowMapperExt<StaLineCommand> beanPropertyRowMapperExt);


    @NativeQuery
    List<StaLineCommand> findStaLineByPickingId2(Sort[] sorts, @QueryParam(value = "plid") Long plid, @QueryParam(value = "ouid") Long ouid, BeanPropertyRowMapperExt<StaLineCommand> beanPropertyRowMapperExt);


    @NativeQuery
    List<StaLineCommand> findStaLinePickingList(@QueryParam(value = "plid") Long plid, @QueryParam(value = "ouid") Long ouid, BeanPropertyRowMapperExt<StaLineCommand> beanPropertyRowMapperExt);

    @NativeQuery
    List<StaLineCommand> findStaLineByPickingIdDiek(Sort[] sorts, @QueryParam(value = "plid") Long plid, @QueryParam(value = "ouid") Long ouid, BeanPropertyRowMapperExt<StaLineCommand> beanPropertyRowMapperExt);

    /**
     * 二次分拣意见，查询配货详情 无仓库
     * 
     * @param pickinglistId
     * @param ouid
     * @param sorts
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findStaLineByPickingIdFast(Sort[] sorts, @QueryParam(value = "plid") Long plid, BeanPropertyRowMapperExt<StaLineCommand> beanPropertyRowMapperExt);

    @NativeQuery
    List<StaLineCommand> findInBoundStaLine(@QueryParam(value = "staId") Long staId, @QueryParam(value = "condition") Object condition, RowMapper<StaLineCommand> r);

    @NativeQuery
    List<StaLineCommand> findInBoundStaLineForPrint(@QueryParam(value = "staId") Long staId, @QueryParam(value = "condition") Object condition, RowMapper<StaLineCommand> r);

    @NativeQuery
    List<StaLineCommand> findIsSufficientInventory(@QueryParam(value = "staId") Long staId, @QueryParam(value = "startOuid") Long startOuid, RowMapper<StaLineCommand> r);

    @NativeQuery
    Map<Long, Long> findSkuIdStalineIdByStaId(@QueryParam(value = "staId") Long staId, BaseRowMapper<Map<Long, Long>> baseRowMapper);

    @NativeQuery
    List<StaLineCommand> findStaLineDetailByStaId(@QueryParam("staId") Long staId, BeanPropertyRowMapperExt<StaLineCommand> beanPropertyRowMapperExt);


    /**
     * 根据GI库区库位简历sta明细
     * 
     * @param locId
     */
    @NativeUpdate
    void createByGILocId(@QueryParam(value = "locId") Long locId, @QueryParam(value = "staId") Long staId);

    @NativeQuery
    Map<String, Long> findMapBySta(@QueryParam(value = "staId") Long staId, MapRowMapper r);

    /**
     * 查询作业单明细
     * 
     * @param start
     * @param pageSize
     * @param id
     * @param sorts
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StaLineCommand> findPickingStaLine(int start, int pageSize, @QueryParam("staId") Long id, Sort[] sorts, BeanPropertyRowMapperExt<StaLineCommand> beanPropertyRowMapperExt);

    /**
     * 查询 作业单 中SN号商品的数量
     * 
     * @param staId
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Integer findSkuSN(@QueryParam("staId") Long staId, SingleColumnRowMapper<Integer> singleColumnRowMapper);

    /**
     * 入库单明细查寻
     * 
     * @param plId
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StaLineCommand> findInboundLineByStaId(int start, int pageSize, @QueryParam(value = "staId") Long staId, @QueryParam Map<String, Object> map, RowMapper<StaLineCommand> rowMapper, Sort[] sorts);

    /**
     * 根据配货清单选择一个sta进行秒杀核对
     * 
     * @param id
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StaLineCommand> getStaPickingListPgIndex(@QueryParam("id") Long id, Sort[] sorts, BeanPropertyRowMapper<StaLineCommand> beanPropertyRowMapper);


    /**
     * 根据配货清单选择一个sta进行秒杀核对
     * 
     * @param id
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findSkuAndQty(@QueryParam("staId") Long staId, BeanPropertyRowMapperExt<StaLineCommand> beanPropertyRowMapper);

    /**
     * 根据StaId 更新staLine 计划量=执行量 秒杀核对 KJL
     * 
     * @param staId
     */
    @NativeUpdate
    void updateCQEqualQByStaId(@QueryParam("staId") Long staId);

    /**
     * 根据StaId,snList 判断当前导入的sn号是否符合出库条件
     * 
     * @param id
     * @param snList
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findErrorSnInfo(@QueryParam("staId") Long id, @QueryParam("snList") List<String> snList, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    String findByInnerShopCode(@QueryParam("staId") Long id, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    String findByInnerShopCode2(@QueryParam("staId") Long id, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 根据staCode和skuId查询商品是否为当前计划内商品，如果是同时计算剩余执行量 KJL
     * 
     * @param staCode 作业单编码
     * @param id skuId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    StaLine getPlanStaLineBySkuIdAndStaCode(@QueryParam("staCode") String staCode, @QueryParam("skuId") Long id, BeanPropertyRowMapper<StaLine> beanPropertyRowMapper);

    @NativeQuery
    StaLine getPartStaLineBySkuIdAndStaCode(@QueryParam("staCode") String staCode, @QueryParam("skuId") Long id, BeanPropertyRowMapper<StaLine> beanPropertyRowMapper);

    /**
     * 判断当前作业单是否是特殊处理作业单
     * 
     * @param id
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Boolean checkIsSpecailSku(@QueryParam("staId") Long id, SingleColumnRowMapper<Boolean> singleColumnRowMapper);

    @NativeQuery
    Boolean findStaIsSpecailSku(@QueryParam("slipCode1") String slipCode1, @QueryParam("skuId") Long skuId, SingleColumnRowMapper<Boolean> singleColumnRowMapper);

    /**
     * 状态是取消未处理的作业单直接修改核对量
     * 
     * @param id
     */
    @NativeUpdate
    void updateCompQtyByStaId(@QueryParam("staLineId") Long id);

    @NamedQuery
    StaLine findStaLineQtyById(@QueryParam("staLineId") Long id);

    /**
     * 查询手动收货明细数据 (星巴克)
     * 
     * @param staId
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findHandStaLineStarbucksBySta(@QueryParam(value = "staId") Long staId, Sort[] sorts, RowMapper<StaLineCommand> rowMapper);

    /**
     * Edw
     */
    @NativeQuery
    List<StaLineCommand> findEdwTwhStaLine(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> rowMapper);

    /**
     * 根据作业单和条码更新默认收货状态标识
     * 
     * @param staCode
     * @param barCode
     */
    @NativeUpdate
    void updateDefaultStatus(@QueryParam("staCode") String staCode, @QueryParam("barCode") String barCode);

    /**
     * Edw
     */
    @NativeQuery
    List<StaLineCommand> findStaLineBystaCode(@QueryParam(value = "code") String code, RowMapper<StaLineCommand> rowMapper);

    @NativeQuery
    List<StaLineCommand> findSnCardCheckSta(@QueryParam(value = "id") Long id, RowMapper<StaLineCommand> rowMapper);

    /**
     * 根据占用批编码获取订单明细
     * 
     * @param ocpCode
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findStaLineByOcpCode(@QueryParam(value = "ocpCode") String ocpCode, @QueryParam(value = "wooId") Long wooId, @QueryParam(value = "status") List<Integer> status, RowMapper<StaLineCommand> rowMapper);

    @NativeQuery
    StaLineCommand findTotalQtyByStaIdAndSkuId(@QueryParam("staid") Long staid, @QueryParam("skuid") Long skuid, RowMapper<StaLineCommand> rowMapper);

    /**
     * 根据订单明细创建占用批明细
     * 
     * @param ocpCode
     * @param ocpId
     */
    void createOcpLineByStaLine(@QueryParam(value = "ocpCode") String ocpCode, @QueryParam(value = "ocpId") Long ocpId);

    @NativeQuery
    List<StaLineCommand> findStaLinesByStaId(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> rowMapper);

    /**
     * 根据订单查询商品列表
     * 
     * @param id
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findLineSkuByOrderId(@QueryParam("id") Long id, BeanPropertyRowMapperExt<StaLineCommand> beanPropertyRowMapperExt);


    /**
     * 根据订单查询商品列表
     * 
     * @param id
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findStaLineByStaId(@QueryParam("staId") Long staId, BeanPropertyRowMapperExt<StaLineCommand> beanPropertyRowMapperExt);


    @NativeQuery
    Map<String, Long> findStalAndBarcodeByStaid(@QueryParam(value = "staId") Long staId, MapRowMapper r);

    @NativeQuery
    Boolean findIsOccupationInvForSales(@QueryParam("staId") Long id, SingleColumnRowMapper<Boolean> singleColumnRowMapper);

    /**
     * 根据订单查询商品列表
     * 
     * @param id
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findstaLineStatus(@QueryParam("staCode") String staCode, BeanPropertyRowMapperExt<StaLineCommand> beanPropertyRowMapperExt);

    @NativeQuery
    StaLine findStaLineByStaIdCard(@QueryParam(value = "id") Long id, BeanPropertyRowMapperExt<StaLine> beanPropertyRowMapperExt);

    /**
     * 
     * @param id
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findBoxReceiveStaLine(@QueryParam("id") Long id, Sort[] sorts, BeanPropertyRowMapper<StaLineCommand> beanPropertyRowMapper);

    /**
     * 查询是否存在行取消情况AD定制
     * 
     * @param staId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findCancelLineBySta(@QueryParam("staId") Long staId, BeanPropertyRowMapper<StaLineCommand> beanPropertyRowMapper);

    /**
     * 根据批次查找没有部分取消并且没有核对的单据
     * 
     * @param pId
     * @param columnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findNotCheckStaIdByCancelLine(@QueryParam("pId") Long pId, SingleColumnRowMapper<Long> columnRowMapper);


    @NativeQuery
    StaLine findCancelLineByStaIdAndLineNo(@QueryParam("staId") Long staId, @QueryParam("lineNo") String lineNo, BeanPropertyRowMapper<StaLine> beanPropertyRowMapper);

    /**
     * 查询取消明细行占用库存进行后续操作
     * 
     * @param staId
     * @param staCode
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findCancelDetailBySta(@QueryParam("staId") Long staId, @QueryParam("staCode") String staCode, BeanPropertyRowMapper<StaLineCommand> beanPropertyRowMapper);

    /**
     * @param lId
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Integer findResultForOperation(@QueryParam("lId") Long lId, SingleColumnRowMapper<Integer> singleColumnRowMapper);

    /**
     * @param staId
     * @param code
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Integer findIfExistsErrorLine(@QueryParam("staId") Long staId, @QueryParam("staCode") String code, SingleColumnRowMapper<Integer> singleColumnRowMapper);

    /**
     * @param staId
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    String findLineNOStringById(@QueryParam("staId") Long staId, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    String findSkuCodeStringById(@QueryParam("staId") Long staId, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    List<StaLineCommand> findGiftMemoByPkId(@QueryParam("pkId") Long pkId, BeanPropertyRowMapper<StaLineCommand> beanPropertyRowMapper);

    @NativeQuery
    List<StaLineCommand> findStaLineByGiftMemo(@QueryParam("slipCode") String slipCode, @QueryParam("staLineId") Long staLineId, BeanPropertyRowMapper<StaLineCommand> beanPropertyRowMapper);

    @NativeQuery
    Map<Long, PackingObj> getAdidasSpecialCustomValue(@QueryParam("plId") Long plId, @QueryParam("staId") Long staId, PackingObjRowMapper packingObjRowMapper);

    /**
     * @param id
     * @param staCode
     */
    @NativeUpdate
    void updateLineToPartySend(@QueryParam("staId") Long id, @QueryParam("staCode") String staCode);

    @NativeQuery
    StaLineCommand findLineQtyListByStaId(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> rowMapper);

    /**
     * @param id
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findShowOccDetail(@QueryParam("staId") Long id, Sort[] sorts, BeanPropertyRowMapper<StaLineCommand> beanPropertyRowMapper);



    @NativeQuery
    List<StaLineCommand> findStaLineSkuInfoByStaId(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> map);


    @NativeQuery
    List<StaLineCommand> findStaLineByStaId(@QueryParam(value = "staId") Long staId, RowMapper<StaLineCommand> row);

    /**
     * 获取mk打印定制数据
     * 
     * @param plId
     * @param staId
     * @param packingObjRowMapper
     * @return
     */
    @NativeQuery
    Map<Long, PackingObj> getMkPackingPageNoLocation(@QueryParam("plId") Long plId, @QueryParam("staId") Long staId, PackingObjRowMapper packingObjRowMapper);

    /**
     * 
     * 方法说明：锐步定制
     * 
     * @author LuYingMing
     * @param plId
     * @param staId
     * @param packingObjRowMapper
     * @return
     */
    @NativeQuery
    Map<Long, PackingObj> getReebokSpecialCustomValue(@QueryParam("plId") Long plId, @QueryParam("staId") Long staId, PackingObjRowMapper packingObjRowMapper);

    /**
     * 
     * 方法说明：GoPro装箱单数据定制
     * 
     * @author LuYingMing
     * @param plId
     * @param staId
     * @param packingObjRowMapper
     * @return
     */
    @NativeQuery
    Map<Long, PackingObj> getGoProSpecialCustomValue(@QueryParam("plId") Long plId, @QueryParam("staId") Long staId, PackingObjRowMapper packingObjRowMapper);

    /**
     * 方法说明：根据staId和orderLineNo查询staLine
     * 
     * @param staId
     * @param orderLineNo
     */
    @NamedQuery
    StaLine findByStaAndOrderLineNo(@QueryParam("staId") Long staId, @QueryParam("orderLineNo") String orderLineNo);

    /**
     * 验证AD定制库存占用是否正确
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StaLine> findIfExistsDiffer(@QueryParam("staId") Long id, BeanPropertyRowMapper<StaLine> beanPropertyRowMapper);

    /**
     * 获取vmi退仓拣货单
     */
    @NativeQuery
    List<VMIBackOrder> findBackListByStaId(@QueryParam("staid") String staid, BeanPropertyRowMapper<VMIBackOrder> beanPropertyRowMapper);

    @NativeQuery
    StaLine findListPriceByStaIdAndSkuId(@QueryParam("staId") Long staid, @QueryParam("skuId") Long skuId, RowMapper<StaLine> r);

    /**
     * 获取vmi退仓拣货单头信息
     */
    @NativeQuery
    VMIBackOrder findBackPrintHanderInfo(@QueryParam("staid") String staid, BeanPropertyRowMapper<VMIBackOrder> beanPropertyRowMapper);

    /**
     * 获取staId头信息
     */
    @NativeQuery
    Long findStaId(@QueryParam("staLineId") Long staLineId, SingleColumnRowMapper<Long> singleColumnRowMapper);


    /**
     * 更新作业单明细的区域
     * 
     * @param ocpAreaCode
     * @param memo
     * @param staId
     */

    @NativeUpdate
    void updateStaLineOcpArea(@QueryParam(value = "skuId") Long skuId, @QueryParam(value = "ocpAreaCode") String ocpAreaCode, @QueryParam(value = "ocpAreaMemo") String memo, @QueryParam(value = "staId") Long staId);

    @NativeUpdate
    void updateStaLineOcpAreaNull(@QueryParam(value = "skuId") Long skuId, @QueryParam(value = "ocpAreaCode") String ocpAreaCode, @QueryParam(value = "ocpAreaMemo") String memo, @QueryParam(value = "staId") Long staId);


    @NativeUpdate
    void updateStaLineOcpAreaById(@QueryParam(value = "staId") Long staId);

    @NativeQuery
    List<StaLineCommand> findStaLineByOcp(@QueryParam("staId") Long id, BeanPropertyRowMapper<StaLineCommand> beanPropertyRowMapper);

    @NativeQuery
    List<StaLineCommand> findStaLineGroupByOcp(@QueryParam("staId") Long id, BeanPropertyRowMapper<StaLineCommand> beanPropertyRowMapper);

    @NativeQuery
    Long findUnHandledOrFailedAreaSkuOcpQty(@QueryParam("ouId") Long ouId, @QueryParam("zoonCode") String zoonCode, @QueryParam("owner") String owner, @QueryParam("skuId") Long skuId, SingleColumnRowMapper<Long> singleColumnRowMapper);
    @NativeQuery
    List<StaLineCommand> findByStaId(@QueryParam ("staId") Long id,BeanPropertyRowMapper<StaLineCommand> beanPropertyRowMapper);

    @NativeQuery
    List<MongoDBInventoryOcp> findMongDbInvListMinus(@QueryParam("areaCode") String areaCode, @QueryParam("ouId") Long ouId, BeanPropertyRowMapper<MongoDBInventoryOcp> beanPropertyRowMapper);

    @NativeQuery(pagable = true)
    Pagination<StaLineCommand> getOutboundDetailList(int start, int pageSize, @QueryParam("staid") Long staid, Sort[] sorts, BeanPropertyRowMapperExt<StaLineCommand> beanPropertyRowMapperExt);
    
    /**
     * 查询po按箱收货明细
     * @param staId
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findPoLineStaList(@QueryParam("staId") Long staId,BeanPropertyRowMapperExt<StaLineCommand> beanPropertyRowMapperExt);
}
