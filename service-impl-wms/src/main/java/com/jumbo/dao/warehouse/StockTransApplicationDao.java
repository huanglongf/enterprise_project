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
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.commandMapper.ImportEntryListRowMapper;
import com.jumbo.dao.commandMapper.ImportMacaoEntryListRowMapper;
import com.jumbo.dao.commandMapper.ImportMacaoHGDEntryListRowMapper;
import com.jumbo.wms.model.command.GiftLineCommand;
import com.jumbo.wms.model.command.SalesReportFormCommand;
import com.jumbo.wms.model.dms.StaInfoCommand;
import com.jumbo.wms.model.jasperReport.ImportEntryListObj;
import com.jumbo.wms.model.jasperReport.ImportHGDEntryListObj;
import com.jumbo.wms.model.jasperReport.InventoryOccupay;
import com.jumbo.wms.model.jasperReport.OutBoundPackingObj;
import com.jumbo.wms.model.jasperReport.OutBoundSendInfo;
import com.jumbo.wms.model.pda.PdaStockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.ExpressOrderCommand;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.PoCommand;
import com.jumbo.wms.model.warehouse.SkuSizeConfig;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationExtInfoCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StvLineCommand;

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
public interface StockTransApplicationDao extends GenericEntityDao<StockTransApplication, Long> {

    @NativeQuery(model = StockTransApplicationExtInfoCommand.class)
    StockTransApplicationExtInfoCommand findRtnInbountStaTransInfo(@QueryParam(value = "staid") Long staid);

    @NativeQuery(model = StockTransApplicationExtInfoCommand.class)
    StockTransApplicationExtInfoCommand findRtnInbountStaStvInfo(@QueryParam(value = "staid") Long staid);

    @NativeQuery
    List<StockTransApplicationCommand> findSkuByStaCode(@QueryParam("staCode") String staCode, RowMapper<StockTransApplicationCommand> rowMapper);


    /**
     * 查询退货入库作业单
     * 
     * @param slipCode3
     * 
     * @param type 申请单类型
     * @param whId 仓库
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findRtnInbountSta(int start, int pageSize, @QueryParam("slipCode3") String slipCode3, @QueryParam(value = "mainWarehouse") Long whId, Sort[] sorts, @QueryParam(value = "startTime") Date startTime,
            @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "code") String code, @QueryParam(value = "refcode") String refcode, @QueryParam(value = "owner") String owner, @QueryParam(value = "slipCode1") String slipCode1,
            @QueryParam(value = "slipCode2") String slipCode2, RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 根据slip_code,type和status不为17,
     */
    @NamedQuery
    StockTransApplication findStaBySlipCodeConfirmOrder(@QueryParam("slipCode") String slipCode, @QueryParam(value = "type") StockTransApplicationType type);

    @NativeUpdate
    void backUpStastatus(@QueryParam("code") String code, @QueryParam("owner") String owner, @QueryParam("ouNames") List<String> ouNames);

    @NativeUpdate
    void closeSta(@QueryParam("owner") String owner, @QueryParam("ouNames") List<String> ouNames);

    @NativeQuery
    List<StockTransApplicationCommand> importStaByOwnerAndOu(@QueryParam(value = "owner") String owner, @QueryParam("ouNames") List<String> ouNames, RowMapper<StockTransApplicationCommand> r);

    /**
     * 通过CODE查询作业单
     * 
     * @param code
     * @return
     */
    @NamedQuery
    StockTransApplication getByCode(@QueryParam("code") String code);

    @NamedQuery
    StockTransApplication getByCode1(@QueryParam("code") String code, @QueryParam("ouId") Long ouId);

    @NamedQuery
    StockTransApplication getByCodeAndOuID(@QueryParam("code") String code, @QueryParam("ouId") Long ouId);

    /**
     * 查询所有合并订单子订单
     * 
     * @param groupId 合并订单父订单ID
     * @return
     */
    @NamedQuery
    List<StockTransApplication> getChildStaByGroupId(@QueryParam("groupId") Long groupId);



    /**
     * 删除合并订单父子关系
     * 
     * @param groupId 合并订单ID
     */
    @NativeUpdate
    void removeMergeStaRef(@QueryParam("groupId") Long groupId);

    /**
     * 将作业单跟新为锁定状态
     * 
     * @param staId
     */
    @NativeUpdate
    void updateStaLocked(@QueryParam("staId") Long staId);

    /**
     * 将订单设置为秒杀订单
     * 
     * @param staId
     */
    @NativeUpdate
    void updateStaSedKill(@QueryParam("staId") Long staId, @QueryParam("isSedKill") Boolean isSedKill);

    @NativeQuery
    List<StockTransApplication> findStaByPickingListPartyFinish(@QueryParam(value = "plid") Long plid, @QueryParam(value = "ouid") Long ouid, RowMapper<StockTransApplication> rowMapper);

    @NativeQuery
    List<StockTransApplicationCommand> findStaByPickList(@QueryParam(value = "plid") Long plid, @QueryParam(value = "ouId") Long ouId, RowMapper<StockTransApplicationCommand> rowMapper);

    @NativeUpdate
    void remvePickingById(@QueryParam("id") Long id);

    @NativeUpdate
    void remveCanceledStaPickingByStaId(@QueryParam("staId") Long staId);

    @NamedQuery
    List<StockTransApplication> findFailedSendStaByPlId(@QueryParam(value = "plid") Long plid);

    @NativeQuery
    List<StockTransApplication> findStaByStatus(@QueryParam(value = "rownum") int rownum, RowMapper<StockTransApplication> rowMapper);

    @NativeUpdate
    void updateStaById(@QueryParam(value = "ids") List<Long> list);

    /**
     * 查询销售配货支持作业单类型
     * 
     * @param r
     * @return
     */
    @NativeQuery
    List<Integer> findSalesTypeList(SingleColumnRowMapper<Integer> r);

    @NativeQuery
    List<StockTransApplicationCommand> findStaNotFinishedListByTypeNoPage(@QueryParam(value = "type") int type, @QueryParam(value = "mainWarehouse") Long whId, Sort[] sorts, RowMapper<StockTransApplicationCommand> rowMapper);

    @NativeQuery
    List<StockTransApplicationCommand> findTranCossStaNotFinishedListByTypeNoPage(@QueryParam(value = "type") int type, @QueryParam(value = "addiWarehouse") Long whId, Sort[] sorts, RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 查询作业单根据库位拣货顺序排序
     * 
     * @param staListId
     * @return
     */
    @NativeQuery
    List<Long> findStaByStaListAndLocationSort(@QueryParam(value = "staListId") List<Long> staIdList, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 根据申请单类型获取列表
     * 
     * @param slipCode3
     * 
     * @param type 申请单类型
     * @param whId 仓库
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findStaNotFinishedListByTypeNew(int start, int pageSize, @QueryParam("slipCode3") String slipCode3, @QueryParam(value = "type") int type, @QueryParam(value = "mainWarehouse") Long whId, Sort[] sorts,
            @QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "arriveStartTime") Date arriveStartTime, @QueryParam(value = "arriveEndTime") Date arriveEndTime,
            @QueryParam(value = "code") String code, @QueryParam(value = "refcode") String refcode, @QueryParam(value = "owner") String owner, @QueryParam(value = "isNeedInvoice") Integer isNeedInvoice, @QueryParam(value = "lpcode") String lpcode,
            @QueryParam(value = "trackingNo") String trackingNo, @QueryParam(value = "slipCode1") String slipCode1, @QueryParam(value = "slipCode2") String slipCode2, RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 根据申请单类型获取列表
     * 
     * @param slipCode3
     * 
     * @param type 申请单类型
     * @param whId 仓库
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findStaNotFinishedListByType(int start, int pageSize, @QueryParam("slipCode3") String slipCode3, @QueryParam(value = "type") int type, @QueryParam(value = "mainWarehouse") Long whId, Sort[] sorts,
            @QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "arriveStartTime") Date arriveStartTime, @QueryParam(value = "arriveEndTime") Date arriveEndTime,
            @QueryParam(value = "code") String code, @QueryParam(value = "refcode") String refcode, @QueryParam(value = "owner") String owner, @QueryParam(value = "isNeedInvoice") Integer isNeedInvoice, @QueryParam(value = "lpcode") String lpcode,
            @QueryParam(value = "trackingNo") String trackingNo, @QueryParam(value = "slipCode1") String slipCode1, @QueryParam(value = "slipCode2") String slipCode2, RowMapper<StockTransApplicationCommand> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findStaNotFinishedListByTypeNoTransNo(int start, int pageSize, @QueryParam("slipCode3") String slipCode3, @QueryParam(value = "type") int type, @QueryParam(value = "mainWarehouse") Long whId, Sort[] sorts,
            @QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "arriveStartTime") Date arriveStartTime, @QueryParam(value = "arriveEndTime") Date arriveEndTime,
            @QueryParam(value = "code") String code, @QueryParam(value = "refcode") String refcode, @QueryParam(value = "owner") String owner, @QueryParam(value = "isNeedInvoice") Integer isNeedInvoice, @QueryParam(value = "slipCode1") String slipCode1,
            @QueryParam(value = "slipCode2") String slipCode2, RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 根据申请单类型获取列表
     * 
     * @param slipCode3
     * 
     * @param type 申请单类型
     * @param whId 仓库
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findStaNotFinishedImperfectListByType(int start, int pageSize, @QueryParam("slipCode3") String slipCode3, @QueryParam(value = "mainWarehouse") Long whId, Sort[] sorts,
            @QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "code") String code, @QueryParam(value = "refcode") String refcode, @QueryParam(value = "owner") String owner,
            @QueryParam(value = "slipCode1") String slipCode1, @QueryParam(value = "slipCode2") String slipCode2, RowMapper<StockTransApplicationCommand> rowMapper);


    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findlockedSta(int start, int pageSize, Sort[] sorts, @QueryParam("slipCode3") String slipCode3, @QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime,
            @QueryParam(value = "code") String code, @QueryParam(value = "refSlipCode") String refSlipCode, @QueryParam(value = "owner") String owner, @QueryParam(value = "lpcode") String lpcode, @QueryParam(value = "trackingNo") String trackingNo,
            @QueryParam(value = "mainWarehouse") Long whId, @QueryParam(value = "receiver") String receiver, @QueryParam(value = "receiverPhone") String receiverPhone, @QueryParam(value = "orderCode") String orderCode,
            @QueryParam(value = "taobaoOrderCode") String taobaoOrderCode, @QueryParam(value = "barCode") String barCode, RowMapper<StockTransApplicationCommand> rowMapper);

    @NativeUpdate
    void updateStaUnlocked(@QueryParam("staId") Long staId);

    @NativeUpdate
    void updateStaDeliveryInfoBySlipCode(@QueryParam("staId") Long staId, @QueryParam("lpCode") String lpCode, @QueryParam("trackingNo") String trackingNo);

    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findTranCossStaNotFinishedListByType(@QueryParam(value = "type") int type, @QueryParam(value = "addiWarehouse") Long whId, RowMapper<StockTransApplicationCommand> rowMapper,
            @QueryParam(value = "code") String code, @QueryParam(value = "refcode") String refcode, @QueryParam(value = "owner") String owner, @QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime,
            @QueryParam(value = "arriveStartTime") Date arriveStartTime, @QueryParam(value = "arriveEndTime") Date arriveEndTime, int start, int pageSize, @QueryParam(value = "slipCode1") String slipCode1,
            @QueryParam(value = "slipCode2") String slipCode2, Sort[] sorts);

    /**
     * @param slipCode
     * @return
     */
    @NamedQuery
    List<StockTransApplication> findBySlipCode(@QueryParam(value = "slipCode") String slipCode);
    
    @NamedQuery
    StockTransApplication findStaBySlipCodeStatus(@QueryParam(value = "slipCode") String slipCode);
    /**
     * @param slipCode
     * @return
     */
    @NamedQuery
    List<StockTransApplication> findBySlipCodeOuId(@QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "ouId") Long ouId);

    /**
     * @param slipCode
     * @return
     */
    @NamedQuery
    List<StockTransApplication> findBySlipCodeCancel(@QueryParam(value = "slipCode") String slipCode);

    @NamedQuery
    StockTransApplication findStaByReSlipCode(@QueryParam(value = "slipCode") String slipCode);

    @NamedQuery
    StockTransApplication findBySlipCodeBySlipCode(@QueryParam(value = "slipCode") String slipCode);

    /**
     * @param slipCode
     * @return
     */
    @NamedQuery
    List<StockTransApplication> findBySlipCodeVmi(@QueryParam(value = "slipCode") String slipCode);

    /**
     * @param slipCode
     * @return
     */
    @NamedQuery
    List<StockTransApplication> findBySlipCodeAndStatus(@QueryParam(value = "slipCode") String slipCode);

    /**
     * @param slipCode
     * @return
     */
    @NamedQuery
    List<StockTransApplication> findBySlipCodeAndStatusCheck(@QueryParam(value = "slipCode") String slipCode);

    @NamedQuery
    List<StockTransApplication> findBySlipCode1(@QueryParam(value = "slipCode1") String slipCode);

    @NamedQuery
    List<StockTransApplication> findADBySlipCode1(@QueryParam(value = "slipCode1") String slipCode);

    @NamedQuery
    List<StockTransApplication> findBySlipCode1Inbound(@QueryParam(value = "slipCode1") String slipCode);

    @NamedQuery
    List<StockTransApplication> findBySlipCode1AndStatus(@QueryParam(value = "slipCode1") String slipCode);

    @NamedQuery
    List<StockTransApplication> findBySlipCode1AndFinished(@QueryParam(value = "slipCode1") String slipCode);

    /**
     * @param slipCode
     * @return
     */
    @NamedQuery
    List<StockTransApplication> findBySlipCodeStatus(@QueryParam(value = "slipCode") String slipCode);

    @NamedQuery
    List<StockTransApplication> findBySlipCodeByType(@QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "type") StockTransApplicationType type);


    /**
     * 查询作业单
     * 
     * @param owner 店铺
     * @param slipCode 相关单据号
     * @param type 类型
     * @param status 状态
     * @return
     */
    @NamedQuery
    StockTransApplication getStaByOwner(@QueryParam(value = "owner") String owner, @QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "type") StockTransApplicationType type,
            @QueryParam(value = "status") StockTransApplicationStatus status);

    /**
     * 预定义查询- 未分页操作
     * 
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findPredefinedStaByType(@QueryParam(value = "types") List<Integer> types, @QueryParam(value = "mainWarehouse") Long whId, Sort[] sorts, RowMapper<StockTransApplicationCommand> rowMapper);

    /***
     * 预定义查询- 分页操作
     * 
     * @return
     */

    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findPredefinedStaByTypeBYPagination(int start, int size, @QueryParam(value = "types") List<Integer> types, @QueryParam(value = "status") List<Integer> status, @QueryParam(value = "locked") String locked,
            @QueryParam(value = "mainWarehouse") Long whId, @QueryParam(value = "code") String code, @QueryParam(value = "refcode") String refcode, @QueryParam(value = "owner") String owner, @QueryParam(value = "startTime") Date startTime,
            @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "arriveStartTime") Date arriveStartTime, @QueryParam(value = "arriveEndTime") Date arriveEndTime, Sort[] sorts, RowMapper<StockTransApplicationCommand> rowMapper);

    /***
     * 按箱收货查询- 分页操作
     * 
     * @return
     */

    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findFreezeCartonStaByTypeBYPagination(int start, int size, @QueryParam(value = "types") List<Integer> types, @QueryParam(value = "status") List<Integer> status, @QueryParam(value = "locked") String locked,
            @QueryParam(value = "mainWarehouse") Long whId, @QueryParam(value = "code") String code, @QueryParam(value = "refcode") String refcode, @QueryParam(value = "owner") String owner, @QueryParam(value = "startTime") Date startTime,
            @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "arriveStartTime") Date arriveStartTime, @QueryParam(value = "arriveEndTime") Date arriveEndTime, Sort[] sorts, RowMapper<StockTransApplicationCommand> rowMapper);


    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findPredeCancelStaByPagination(int start, int size, @QueryParam(value = "intStaStatus") Integer intStaStatus, @QueryParam(value = "intStaType") Integer intStaType,
            @QueryParam(value = "types") List<Integer> types, @QueryParam(value = "status") List<Integer> status, @QueryParam(value = "locked") String locked, @QueryParam(value = "mainWarehouse") Long whId, @QueryParam(value = "code") String code,
            @QueryParam(value = "refcode") String refcode, @QueryParam(value = "owner") String owner, @QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime,
            @QueryParam(value = "arriveStartTime") Date arriveStartTime, @QueryParam(value = "arriveEndTime") Date arriveEndTime, Sort[] sorts, RowMapper<StockTransApplicationCommand> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findPredefinedStaByTypeBYPaginationRoot(int start, int size, @QueryParam(value = "intStatus") List<Integer> intStatus, @QueryParam(value = "status") List<Integer> status,
            @QueryParam(value = "locked") String locked, @QueryParam(value = "mainWarehouse") Long whId, @QueryParam(value = "code") String code, @QueryParam(value = "refcode") String refcode, @QueryParam(value = "owner") String owner,
            @QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "arriveStartTime") Date arriveStartTime, @QueryParam(value = "arriveEndTime") Date arriveEndTime,
            @QueryParam(value = "ouId") Long ouId, Sort[] sorts, RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 根據Id修改作業單的status
     * 
     * @param staId
     */
    @NativeUpdate
    void modifyroleStatusById(@QueryParam("staId") Long staId);

    /**
     * 根据refSlipCode查找作业单
     * 
     * @param refSlipCode
     * @return
     */
    @NamedQuery
    List<StockTransApplication> findStaStautsBySlipCode(@QueryParam("refSlipCode") String refSlipCode);

    /**
     * 根据申型状态获取待配货列表
     * 
     * @param whId 仓库
     * @param statusList 状态列表
     * @param fromDate 创建起始时间
     * @param toDate 创建结束时间
     * @param code 编码
     * @param refSlipCode 前置单据便编码
     * @param typeList 类型列表
     * @param isNeedInvoice 是否需要发票
     * @param lpCode 物流供应商编码
     * @param shopId 店铺id
     * @param pickingListCode 配货清单编码
     * @param receiver 收货人
     * @param trackingNo 快递单号
     * @param checkPickingList 是否检查存在picking list
     * @param isCkSameSeqNoSta 是否检查同批次入库sta状态
     * @param isLike 是否使用模糊查询
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findSalesStaList(@QueryParam(value = "isSpPg") Boolean isSpPg, @QueryParam(value = "cities") List<String> cities, @QueryParam(value = "province") String province,
            @QueryParam(value = "isLpcodeNotNull") Boolean isLpcodeNotNull, @QueryParam(value = "isSnSta") Boolean isSnSta, @QueryParam(value = "pickingMode") Integer pickingMode, @QueryParam(value = "skus") String skus,
            @QueryParam(value = "mainWarehouse") Long whId, @QueryParam(value = "statusList") List<Integer> statusList, @QueryParam(value = "fromDate") Date fromDate, @QueryParam(value = "toDate") Date toDate, @QueryParam(value = "code") String code,
            @QueryParam(value = "refSlipCode") String refSlipCode, @QueryParam(value = "typeList") List<Integer> typeList,
            @QueryParam(value = "isNeedInvoice") Boolean isNeedInvoice,
            @QueryParam(value = "lpCode") String lpCode,
            @QueryParam(value = "shopInnerCodes") List<String> shopInnerCodes, // /
                                                                               // 16
            @QueryParam(value = "shopId") String shopId, @QueryParam(value = "pickingListCode") String pickingListCode, @QueryParam(value = "receiver") String receiver,
            @QueryParam(value = "trackingNo") String trackingNo, // 20
            @QueryParam(value = "checkPickingList") Boolean checkPickingList, @QueryParam(value = "isCkSameSeqNoSta") Boolean isCkSameSeqNoSta, @QueryParam(value = "isLike") Boolean isLike, @QueryParam(value = "skuQty") Long skuQty,
            @QueryParam(value = "skuCodeList") List<String> skuCodeList, @QueryParam Map<String, String> sku, Sort[] sorts, RowMapper<StockTransApplicationCommand> rowMapper);



    /**
     * 根据申型状态获取待配货列表
     * 
     * @param whId 仓库
     * @param statusList 状态列表
     * @param fromDate 创建起始时间
     * @param toDate 创建结束时间
     * @param code 编码
     * @param refSlipCode 前置单据便编码
     * @param typeList 类型列表
     * @param isNeedInvoice 是否需要发票
     * @param lpCode 物流供应商编码
     * @param shopId 店铺id
     * @param pickingListCode 配货清单编码
     * @param receiver 收货人
     * @param trackingNo 快递单号
     * @param checkPickingList 是否检查存在picking list
     * @param isCkSameSeqNoSta 是否检查同批次入库sta状态
     * @param isLpcodeNotNull 是否把没有物流商的单子过滤掉
     * @param isLike 是否使用模糊查询
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StockTransApplicationCommand> findSalesStaListPage(int start, int pageSzie, @QueryParam(value = "isSpPg") Boolean isSpPg, @QueryParam(value = "cities") List<String> cities, @QueryParam(value = "province") String province,
            @QueryParam(value = "isLpcodeNotNull") Boolean isLpcodeNotNull, @QueryParam(value = "isSnSta") Boolean isSnSta, @QueryParam(value = "pickingMode") Integer pickingMode, @QueryParam(value = "skus") String skus,
            @QueryParam(value = "mainWarehouse") Long whId, @QueryParam(value = "statusList") List<Integer> statusList, @QueryParam(value = "fromDate") Date fromDate, @QueryParam(value = "toDate") Date toDate, @QueryParam(value = "code") String code,
            @QueryParam(value = "refSlipCode") String refSlipCode, @QueryParam(value = "typeList") List<Integer> typeList, @QueryParam(value = "isNeedInvoice") Boolean isNeedInvoice, @QueryParam(value = "lpCode") String lpCode,
            @QueryParam(value = "shopInnerCodes") List<String> shopInnerCodes, @QueryParam(value = "shopId") String shopId, @QueryParam(value = "pickingListCode") String pickingListCode, @QueryParam(value = "receiver") String receiver,
            @QueryParam(value = "trackingNo") String trackingNo, @QueryParam(value = "checkPickingList") Boolean checkPickingList, @QueryParam(value = "isCkSameSeqNoSta") Boolean isCkSameSeqNoSta, @QueryParam(value = "isLike") Boolean isLike,
            @QueryParam(value = "skuQty") Long skuQty, @QueryParam(value = "skuCodeList") List<String> skuCodeList, @QueryParam Map<String, String> sku, Sort[] sorts, RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 通过pickingList查询sta
     * 
     * @param plId pickingList id
     * @return
     */
    @NamedQuery
    List<StockTransApplication> findStaByPickingList(@QueryParam(value = "plId") Long plId, @QueryParam(value = "ouid") Long ouid);

    /**
     * 根据配货单,查询配货中/已核对的作业申请单
     * 
     * @param plId
     * @param status
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findStaListForVerifyByPickingList(@QueryParam(value = "plcode") String plcode, @QueryParam(value = "plId") Long plId, @QueryParam(value = "status") Integer[] status,
            @QueryParam(value = "pickingMode") Integer pickingMode, @QueryParam(value = "refSlipCode") String refSlipCode, @QueryParam(value = "ouId") Long ouId, Sort[] sorts, RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 根据配货单,查询配货中/已核对的作业申请单
     * 
     * @param plId
     * @param status
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findO2OQSStaListForVerifyByPickingList(@QueryParam(value = "plcode") String plcode, @QueryParam(value = "plId") Long plId, @QueryParam(value = "status") Integer[] status,
            @QueryParam(value = "pickingMode") Integer pickingMode, @QueryParam(value = "refSlipCode") String refSlipCode, @QueryParam(value = "ouId") Long ouId, Sort[] sorts, RowMapper<StockTransApplicationCommand> rowMapper);


    /**
     * 根据配货单,查询配货中/已核对的作业申请单
     * 
     * @param plId
     * @param status
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findStaListForVerifyByPickingListopc(@QueryParam(value = "plcode") String plcode, @QueryParam(value = "plId") Long plId, @QueryParam(value = "status") Integer[] status,
            @QueryParam(value = "pickingMode") Integer pickingMode, @QueryParam(value = "refSlipCode") String refSlipCode, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "wids") List<Long> wids, Sort[] sorts,
            @QueryParam(value = "whStatus") Integer whStatus, RowMapper<StockTransApplicationCommand> rowMapper);


    /**
     * 根据配货单,查询大件奢侈品仓配货中/已核对的作业申请单
     * 
     * @param plId
     * @param status
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findStaListForVerifyByBigPickingList(@QueryParam(value = "plcode") String plcode, @QueryParam(value = "plId") Long plId, @QueryParam(value = "status") Integer[] status,
            @QueryParam(value = "pickingMode") Integer pickingMode, @QueryParam(value = "refSlipCode") String refSlipCode, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "wids") List<Long> wids, Sort[] sorts,
            @QueryParam(value = "whStatus") Integer whStatus, RowMapper<StockTransApplicationCommand> rowMapper);


    /**
     * 根据配货清单查询作业申请单
     * 
     * @param whId
     * @param pickingListId
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findStaByPickingList(@QueryParam(value = "mainWarehouse") Long whId, @QueryParam(value = "pickingListId") Long pickingListId, @QueryParam(value = "wids") List<Long> plist, Sort[] sorts,
            RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 根据配货清单查询作业申请单(运营中心)
     * 
     * @param whId
     * @param pickingListId
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findStaByPickingListopc(@QueryParam(value = "mainWarehouse") List<Long> whId, @QueryParam(value = "pickingListId") Long pickingListId, Sort[] sorts, RowMapper<StockTransApplicationCommand> rowMapper);

    @NativeUpdate
    void removeStaFromPickingListByStatus(@QueryParam(value = "statusList") List<Integer> statusList, @QueryParam(value = "plId") Long plId);

    /**
     * 获取BusinessSeqNo序号
     */
    @NativeQuery
    BigDecimal getBusinessSeqNo(SingleColumnRowMapper<BigDecimal> rowMap);

    /**
     * 根据作业单号查出关联快递单号
     * 
     * @param stacode
     * @return
     */
    @NativeQuery
    List<String> findRelevanceTrackingno(@QueryParam(value = "stacode") String stacode, RowMapper<String> rowMap);

    /**
     * 还原原始，添加新方法
     * 
     * @param start
     * @param pageSize
     * @param slipCode
     * @param whOuId
     * @param companyid
     * @param createDate
     * @param endCreateDate
     * @param startDate
     * @param offStartDate
     * @param code
     * @param owner
     * @param status
     * @param transaction
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findElseComeInAndGoOut(int start, int pageSize, @QueryParam("slipCode") String slipCode, @QueryParam("whOuId") Long whOuId, @QueryParam("createDate") Date createDate,
            @QueryParam("endCreateDate") Date endCreateDate, @QueryParam("startDate") Date startDate, @QueryParam("offStartDate") Date offStartDate, @QueryParam("code") String code, @QueryParam("owner") String owner, @QueryParam("status") Integer status,
            @QueryParam("transaction") Integer transaction, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sorts);

    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findVMITransferStaPage(int start, int pageSize, @QueryParam("whOuId") Long whOuId, @QueryParam("companyid") Long companyid, @QueryParam("createDate") Date createDate,
            @QueryParam("endCreateDate") Date endCreateDate, @QueryParam("startDate") Date startDate, @QueryParam("offStartDate") Date offStartDate, @QueryParam("code") String code, @QueryParam("owner") String owner,
            @QueryParam("creater") String creater, @QueryParam("operator") String operator, @QueryParam("status") Integer status, @QueryParam("staType") int staType, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sorts);

    /**
     * vmi 退仓
     * 
     * @param whOuId
     * @param companyid
     * @param startTime
     * @param endTime
     * @param code
     * @param owner
     * @param status
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findVMIReturnSta(int start, int pageSize, @QueryParam("whOuId") Long whOuId, @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime, @QueryParam("code") String code,
            @QueryParam("owner") String owner, @QueryParam("status") Integer status, @QueryParam("refSlipCode") String refSlipCode, @QueryParam("slipCode2") String slipCode2, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sorts);

    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findStaList(int start, int size, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "createTime") Date createTime, @QueryParam(value = "endCreateTime") Date endCreateTime,
            @QueryParam(value = "finishTime") Date finishTime, @QueryParam(value = "endFinishTime") Date endFinishTime, @QueryParam(value = "code") String code, @QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "owner") String owner,
            @QueryParam(value = "status") Integer status, @QueryParam(value = "type") Integer type, @QueryParam(value = "lpcode") String lpcode, @QueryParam(value = "operator") String operator, @QueryParam(value = "trackingNo") String trackingNo,
            @QueryParam(value = "statusList") List<Integer> statusList, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sorts);

    /**
     * 作业单查询 fanht
     * 
     * @param start
     * @param size
     * @param ouId
     * @param createTime
     * @param endCreateTime
     * @param finishTime
     * @param endFinishTime
     * @param code
     * @param slipCode
     * @param slipCode1
     * @param slipCode2
     * @param owner
     * @param status
     * @param type
     * @param lpcode
     * @param operator
     * @param trackingNo
     * @param supplierCode
     * @param skuCode
     * @param barCode
     * @param jmCode
     * @param statusList
     * @param isMorePackage 追加 是否分包 fanht
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findStaLists(int start, int size, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "createTime") Date createTime, @QueryParam(value = "endCreateTime") Date endCreateTime,
            @QueryParam(value = "finishTime") Date finishTime, @QueryParam(value = "endFinishTime") Date endFinishTime, @QueryParam("orderCreateTime") Date orderCreateTime, @QueryParam("toOrderCreateTime") Date toOrderCreateTime,
            @QueryParam(value = "code") String code, @QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "slipCode1") String slipCode1, @QueryParam(value = "slipCode2") String slipCode2, @QueryParam(value = "owner") String owner,
            @QueryParam(value = "status") Integer status, @QueryParam(value = "type") Integer type, @QueryParam(value = "lpcode") String lpcode, @QueryParam(value = "operator") String operator, @QueryParam(value = "trackingNo") String trackingNo,
            @QueryParam(value = "supplierCode") String supplierCode, @QueryParam(value = "skuCode") String skuCode, @QueryParam(value = "barCode") String barCode, @QueryParam(value = "jmCode") String jmCode,
            @QueryParam(value = "statusList") List<Integer> statusList, @QueryParam(value = "isMorePackage") String isMorePackage, @QueryParam(value = "isCod") String isCod, @QueryParam(value = "isQs") String isQs,
            @QueryParam(value = "o2oShop") String o2oShop, @QueryParam(value = "pickingTypeString") String pickingTypeString, @QueryParam(value = "transTimeType") String transTimeType, @QueryParam(value = "skuCategoriesName") String skuCategoriesName,
            @QueryParam(value = "isMerge") Integer isMerge, @QueryParam(value = "groupStaCode") String groupStaCode, @QueryParam(value = "pickingListCode") String pickingListCode, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sorts,
            @QueryParam(value = "isPickingList") String isPickingList);

    /**
     * 作业单查询 fanht
     * 
     * @param start
     * @param size
     * @param ouId
     * @param createTime
     * @param endCreateTime
     * @param finishTime
     * @param endFinishTime
     * @param code
     * @param slipCode
     * @param slipCode1
     * @param slipCode2
     * @param owner
     * @param status
     * @param type
     * @param lpcode
     * @param operator
     * @param trackingNo
     * @param supplierCode
     * @param skuCode
     * @param barCode
     * @param jmCode
     * @param statusList
     * @param isMorePackage 追加 是否分包 fanht
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findHistoricalStaLists(int start, int size, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "createTime") Date createTime, @QueryParam(value = "endCreateTime") Date endCreateTime,
            @QueryParam(value = "finishTime") Date finishTime, @QueryParam(value = "endFinishTime") Date endFinishTime, @QueryParam("orderCreateTime") Date orderCreateTime, @QueryParam("toOrderCreateTime") Date toOrderCreateTime,
            @QueryParam(value = "code") String code, @QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "slipCode1") String slipCode1, @QueryParam(value = "slipCode2") String slipCode2, @QueryParam(value = "owner") String owner,
            @QueryParam(value = "status") Integer status, @QueryParam(value = "type") Integer type, @QueryParam(value = "lpcode") String lpcode, @QueryParam(value = "operator") String operator, @QueryParam(value = "trackingNo") String trackingNo,
            @QueryParam(value = "supplierCode") String supplierCode, @QueryParam(value = "skuCode") String skuCode, @QueryParam(value = "barCode") String barCode, @QueryParam(value = "jmCode") String jmCode,
            @QueryParam(value = "statusList") List<Integer> statusList, @QueryParam(value = "isMorePackage") String isMorePackage, @QueryParam(value = "isCod") String isCod, @QueryParam(value = "isQs") String isQs,
            @QueryParam(value = "o2oShop") String o2oShop, @QueryParam(value = "pickingTypeString") String pickingTypeString, @QueryParam(value = "transTimeType") String transTimeType, @QueryParam(value = "skuCategoriesName") String skuCategoriesName,
            @QueryParam(value = "isMerge") Integer isMerge, @QueryParam(value = "groupStaCode") String groupStaCode, @QueryParam(value = "pickingListCode") String pickingListCode, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sorts,
            @QueryParam(value = "isPickingList") String isPickingList, @QueryParam(value = "isShowMerge") String isShowMerge, @QueryParam("cityList") List<String> cityList, @QueryParam("priorityList") List<String> priorityList,
            @QueryParam("flag") Boolean flag, @QueryParam("msg") Boolean msg);


    /**
     * 作业单查询 2 fanht (只查t_wh_sta)
     * 
     * @param start
     * @param size
     * @param ouId
     * @param createTime
     * @param endCreateTime
     * @param finishTime
     * @param endFinishTime
     * @param code
     * @param slipCode
     * @param slipCode1
     * @param slipCode2
     * @param owner
     * @param status
     * @param type
     * @param lpcode
     * @param operator
     * @param trackingNo
     * @param supplierCode
     * @param skuCode
     * @param barCode
     * @param jmCode
     * @param statusList
     * @param isMorePackage 追加 是否分包 fanht
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findHistoricalStaLists2(int start, int size, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "createTime") Date createTime, @QueryParam(value = "endCreateTime") Date endCreateTime,
            @QueryParam(value = "finishTime") Date finishTime, @QueryParam(value = "endFinishTime") Date endFinishTime, @QueryParam("orderCreateTime") Date orderCreateTime, @QueryParam("toOrderCreateTime") Date toOrderCreateTime,
            @QueryParam(value = "code") String code, @QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "slipCode1") String slipCode1, @QueryParam(value = "slipCode2") String slipCode2, @QueryParam(value = "owner") String owner,
            @QueryParam(value = "status") Integer status, @QueryParam(value = "type") Integer type, @QueryParam(value = "lpcode") String lpcode, @QueryParam(value = "operator") String operator, @QueryParam(value = "trackingNo") String trackingNo,
            @QueryParam(value = "supplierCode") String supplierCode, @QueryParam(value = "skuCode") String skuCode, @QueryParam(value = "barCode") String barCode, @QueryParam(value = "jmCode") String jmCode,
            @QueryParam(value = "statusList") List<Integer> statusList, @QueryParam(value = "isMorePackage") String isMorePackage, @QueryParam(value = "isCod") String isCod, @QueryParam(value = "isQs") String isQs,
            @QueryParam(value = "o2oShop") String o2oShop, @QueryParam(value = "pickingTypeString") String pickingTypeString, @QueryParam(value = "transTimeType") String transTimeType, @QueryParam(value = "skuCategoriesName") String skuCategoriesName,
            @QueryParam(value = "isMerge") Integer isMerge, @QueryParam(value = "groupStaCode") String groupStaCode, @QueryParam(value = "pickingListCode") String pickingListCode, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sorts,
            @QueryParam(value = "isPickingList") String isPickingList, @QueryParam(value = "isShowMerge") String isShowMerge, @QueryParam(value = "isPreSale") String isPreSale, @QueryParam("cityList") List<String> cityList,
            @QueryParam("priorityList") List<String> priorityList, @QueryParam("flag") Boolean flag, @QueryParam("msg") Boolean msg);


    /**
     * 根据条件查询特殊处理作业单作业单
     * 
     * @param start
     * @param size
     * @param ouId
     * @param createTime
     * @param endCreateTime
     * @param code
     * @param slipCode
     * @param slipCode1
     * @param slipCode2
     * @param owner
     * @param status
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findEspStaLists(int start, int size, @QueryParam(value = "createTime") Date createTime, @QueryParam(value = "endCreateTime") Date endCreateTime, @QueryParam(value = "code") String code,
            @QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "slipCode1") String slipCode1, @QueryParam(value = "slipCode2") String slipCode2, @QueryParam(value = "owner") String owner, @QueryParam(value = "status") Integer status,
            @QueryParam(value = "ouId") Long ouId, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sorts);


    /**
     * 退换货税控发票导出数据查寻
     * 
     * @param start
     * @param size
     * @param params
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findExcStaList(int start, int size, @QueryParam Map<String, Object> params, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sorts);

    /**
     * 退换货税控发票导出数据查寻
     * 
     * @param start
     * @param size
     * @param params
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> queryPredefinedOutStaList(int start, int size, @QueryParam Map<String, Object> params, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sorts);

    /**
     * 获取税控发票导出文件名
     * 
     * @param ouid
     * @return
     */
    @NativeQuery
    String findExportFileNameBySta(@QueryParam(value = "staId") Long staId, SingleColumnRowMapper<String> s);


    @NativeQuery
    String findGroupStaCode(@QueryParam(value = "slipCode1") String slipCode1, SingleColumnRowMapper<String> s);


    /**
     * 查询同批次出库sta id
     * 
     * @param staId
     * @param rowMap
     * @return
     */
    @NativeQuery
    BigDecimal findSameSeqNoOutBoundStaByStaId(@QueryParam("staId") Long staId, RowMapper<BigDecimal> rowMap);

    @NativeUpdate
    void updatePOLifeByStaRefCodeSql(@QueryParam("code") String code, @QueryParam("status") Integer status);

    /**
     * 库内移动查寻
     * 
     * @param ouid
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findExecuteTransitInnerSta(@QueryParam("createTime") Date createTime, @QueryParam("finishTime") Date finishTime, @QueryParam("code") String code, @QueryParam("creater") String creater, @QueryParam("ouId") Long ouId,
            @QueryParam("isExpIn") Boolean isExpIn, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sort);

    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findExecuteTransitInnerStaByPage(int start, int pageSize, @QueryParam("createTime") Date createTime, @QueryParam("finishTime") Date finishTime, @QueryParam("code") String code,
            @QueryParam("fileStatus") String fileStatus, @QueryParam("creater") String creater, @QueryParam("ouId") Long ouId, @QueryParam("isExpIn") Boolean isExpIn, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sort);

    /**
     * 查询库存锁定作业
     * 
     * @param start @param pageSize @param createTime @param finishTime @param code @param creater @param
     *        ouId @param rowMapper @param sort @return Pagination<StockTransApplicationCommand> @throws
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findInventoryLockStaByPage(int start, int pageSize, @QueryParam("createTime") Date createTime, @QueryParam("finishTime") Date finishTime, @QueryParam("code") String code, @QueryParam("creater") String creater,
            @QueryParam("lockType") Integer lockType, @QueryParam("ouId") Long ouId, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sort);


    /**
     * 库存状态修改sta查寻
     * 
     * @param ouid
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findExecuteInvStatusChangeSta(@QueryParam("createTime") Date createTime, @QueryParam("finishTime") Date finishTime, @QueryParam("code") String code, @QueryParam("creater") String creater,
            @QueryParam("ouId") Long ouId, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sort);


    /**
     * 待处理移动sta查寻
     * 
     * @param ouid
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findExecutMoveStaWithInStv(@QueryParam("createTime") Date createTime, @QueryParam("finishTime") Date finishTime, @QueryParam("type") Integer type, @QueryParam("code") String code,
            @QueryParam("creater") String creater, @QueryParam("ouId") Long ouId, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sort);


    /**
     * createTimeFrom,createTimeTo,status,code,userName,mainWhId
     * 
     * @param params
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findStaForTransitCrossByModelSql(int start, int pageSize, @QueryParam Map<String, Object> params, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sorts);

    /**
     * 根据id获取sta信息
     * 
     * @param staId
     * @return
     */
    @NativeQuery
    StockTransApplicationCommand findStaByStaId(@QueryParam("staId") Long staId, RowMapper<StockTransApplicationCommand> rowMapper);


    @NativeQuery
    List<StockTransApplicationCommand> findOutOfCossStaNotFinishedListByType(@QueryParam(value = "type") int type, @QueryParam(value = "addiWarehouse") Long whId, Sort[] sorts, RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 
     * @param code
     * @return
     */
    @NamedQuery
    StockTransApplication findStaByCode(@QueryParam("code") String code);

    @NativeQuery
    BigDecimal findUnCancelStaBySlipCode(@QueryParam("slipCode") String slipCode, SingleColumnRowMapper<BigDecimal> r);

    @NativeUpdate
    void updateStaSkus(@QueryParam("skuSlipChar") String skuSlipChar, @QueryParam("staId") Long staId);

    @NativeUpdate
    void updateIsSnSta(@QueryParam("staId") Long staId);

    /**
     * 
     * @param staid
     * @param ouid
     * @return
     */
    @NativeQuery
    // List<InventoryOccupay> findOutOfCossStaNotFinishedListByStaId(@QueryParam(value="staid") Long
    // staid, @QueryParam(value="ouid") Long ouid);
    InventoryOccupay findOutOfCossStaNotFinishedListByStaId(@QueryParam(value = "staid") Long staid, @QueryParam(value = "ouid") Long ouid, RowMapper<InventoryOccupay> rowMapper);

    /**
     * 
     * @param staid
     * @param ouid
     * @return
     */
    @NativeQuery
    // List<InventoryOccupay> findOutOfCossStaNotFinishedListByStaId(@QueryParam(value="staid") Long
    // staid, @QueryParam(value="ouid") Long ouid);
    InventoryOccupay findOutOfAddWhIdNotFinishedListByStaId(@QueryParam(value = "staid") Long staid, @QueryParam(value = "ouid") Long ouid, RowMapper<InventoryOccupay> rowMapper);

    /**
     * 快递单修改>>作业单查询
     * 
     * @param start
     * @param pageSize
     * @param cmdMap
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<ExpressOrderCommand> findExpressOrderSta(int start, int pageSize, @QueryParam Map<String, Object> cmdMap, Sort[] sorts, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "wids") List<Long> plList,
            RowMapper<ExpressOrderCommand> rowMapper);

    @NativeQuery
    PoCommand findPoInfo(@QueryParam(value = "staId") Long staid, RowMapper<PoCommand> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findVMIFlittingStas(int start, int pageSize, @QueryParam(value = "code") String code, @QueryParam(value = "refcode") String refcode, @QueryParam(value = "owner") String owner,
            @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "statype") int statype, @QueryParam(value = "status") int status, BeanPropertyRowMapperExt<StockTransApplicationCommand> beanPropertyRowMapperExt, Sort[] sorts);

    // pda 收货作业单
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findPdaPurchaseStas(int start, int pageSize, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "createDate") Date createDate, @QueryParam(value = "endCreateDate") Date endCreateDate,
            @QueryParam(value = "code") String code, @QueryParam(value = "refCode") String refCode, RowMapper<StockTransApplicationCommand> beanPropertyRowMapper, Sort[] sorts);

    @NativeQuery
    Integer findIsNeedSnByStaId(@QueryParam(value = "staid") Long staid, @QueryParam(value = "ouid") Long ouid, SingleColumnRowMapper<Integer> singleColumnRowMapper);

    /**
     * 查询前一天的出库作业单
     * 
     * @return
     */
    @NativeQuery
    List<StvLineCommand> findStockTransByslipcode1(@QueryParam("slipCode") String slipCode, RowMapper<StvLineCommand> beanPropertyRowMapper);

    @NativeUpdate
    void updateStaIsPDAByStv(@QueryParam("stvId") Long stvId);

    @NativeQuery
    InventoryOccupay findVmiReturnOccupyInventoryByStaId(@QueryParam(value = "staid") Long staid, @QueryParam(value = "ouid") Long ouid, RowMapper<InventoryOccupay> rowMapper);


    /**
     * 
     * @param code
     * @return
     */
    @NamedQuery
    StockTransApplication findStaBySlipCode(@QueryParam("slipCode") String slipCode);

    /**
     * 根据slipCode查找类型为退货入库的作业单
     * 
     * @param slipCode
     * @return
     */
    @NamedQuery
    StockTransApplication findRntStaBySlipCode(@QueryParam("slipCode") String slipCode);

    /**
     * 
     * @param code
     * @return
     */
    @NamedQuery
    List<StockTransApplication> findStaListBySlipCode(@QueryParam("slipCode") String slipCode);

    @NativeUpdate
    void updateStaStatusById(@QueryParam("status") int status, @QueryParam("slipCode") String slipCode);

    @NativeUpdate
    void createRtnFeedbackForNike(@QueryParam("staid") Long staid, @QueryParam("nike") String nike, @QueryParam("referenceNo") String referenceNo, @QueryParam("receiveDate") String receiveDate, @QueryParam("fromLocation") String fromLocation,
    // @QueryParam("toLocation") String toLocation,
            @QueryParam("status") Integer status, @QueryParam("type") Integer type);

    @NativeUpdate
    void createRtnFeedbackForConverse(@QueryParam("staid") Long staid, @QueryParam("fromLocation") String fromLocation, @QueryParam("toLocation") String toLocation, @QueryParam("receiveDate") String receiveDate, @QueryParam("carton") String carton,
            @QueryParam("cartonNumber") String cartonNumber, @QueryParam("status") int status, @QueryParam("ReceiveType") int ReceiveType);

    @NativeQuery
    StockTransApplication findWHAndRefCodeStaType(@QueryParam("refCode") String refCode, @QueryParam("staType") int staType, BeanPropertyRowMapperExt<StockTransApplication> beanPropertyRowMapper);

    @NativeQuery
    BigDecimal findNikeSEQ(SingleColumnRowMapper<BigDecimal> rowMap);

    @NativeQuery
    BigDecimal findLevisSEQ(SingleColumnRowMapper<BigDecimal> rowMap);

    @NativeQuery
    BigDecimal findConverseSEQ(SingleColumnRowMapper<BigDecimal> rowMap);

    @NativeQuery
    BigDecimal findEspritRDSEQ(SingleColumnRowMapper<BigDecimal> rowMap);

    @NativeQuery
    BigDecimal findEspritIF2RDSEQ(SingleColumnRowMapper<BigDecimal> rowMap);

    @NativeQuery
    BigDecimal findCathKidstonRDSEQ(SingleColumnRowMapper<BigDecimal> rowMap);

    @NativeUpdate
    void createTransferOutFeedbackForNike(@QueryParam("staid") Long id, @QueryParam("nike") String nike, @QueryParam("referenceNo") String referenceNo, @QueryParam("receiveDate") String receiveDate, @QueryParam("status") Integer status,
            @QueryParam("type") Integer type, @QueryParam("toLocation") String toLocation, @QueryParam("fromLocation") String fromLocation);

    @NamedQuery
    StockTransApplication findOutboundReturnRequest(@QueryParam("slipCode") String slipCode);

    @NativeQuery
    OutBoundSendInfo findVmiReturnOutBoundSendInfoByStaId(@QueryParam("staid") Long staid, BeanPropertyRowMapperExt<OutBoundSendInfo> beanPropertyRowMapperExt);

    @NativeQuery
    OutBoundSendInfo findOutOfCossOutBoundSendInfoByStaId(@QueryParam("staid") Long staid, BeanPropertyRowMapperExt<OutBoundSendInfo> beanPropertyRowMapperExt);

    @NativeQuery
    Map<String, Integer> findUndoAndHasExportpackingObjs(@QueryParam("plid") Long plid, @QueryParam("staid") Long staid, BaseRowMapper<Map<String, Integer>> baseRowMapper);

    @NativeQuery
    List<StockTransApplication> findBySlipCodeAndType(@QueryParam("slipCode") String slipCode, @QueryParam("type") int type, BeanPropertyRowMapperExt<StockTransApplication> beanPropertyRowMapperExt);

    @NativeUpdate
    void updateSkuQtyById(@QueryParam("id") Long id);

    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findOutboundPackageStaListPage(int start, int pageSize, @QueryParam("startCreateTime") Date startCreateTime, @QueryParam("endCreateTime") Date endCreateTime, @QueryParam("intStatus") Integer intStatus,
            @QueryParam("code") String code, @QueryParam("refSlipCode") String refSlipCode, @QueryParam("slipCode2") String slipCode2, @QueryParam("staTypeList") List<Integer> staTypeList, @QueryParam("ouid") Long ouid, Sort[] sorts,
            RowMapper<StockTransApplicationCommand> beanPropertyRowMapper);



    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findOutboundPackageByStaListPage(int start, int pageSize, @QueryParam("startCreateTime") Date startCreateTime, @QueryParam("endCreateTime") Date endCreateTime, @QueryParam("owner") String owner,
            @QueryParam("slipCode2") String slipCode2, @QueryParam("slipCode1") String slipCode1, @QueryParam("refSlipCode") String refSlipCode, @QueryParam("staTypeList") List<Integer> staTypeList, @QueryParam("ouid") Long ouid, Sort[] sorts,
            RowMapper<StockTransApplicationCommand> beanPropertyRowMapper);

    @NativeQuery
    List<StockTransApplicationCommand> findNikeCRWCartonLine(@QueryParam("startCreateTime") Date startCreateTime, @QueryParam("endCreateTime") Date endCreateTime, @QueryParam("owner") String owner, @QueryParam("slipCode2") String slipCode2,
            @QueryParam("slipCode1") String slipCode1, @QueryParam("refSlipCode") String refSlipCode, @QueryParam("ouid") Long ouid, RowMapper<StockTransApplicationCommand> rowMapper);


    @NativeQuery
    List<StockTransApplicationCommand> findNikeCRWCartonLine1(@QueryParam("startCreateTime") Date startCreateTime, @QueryParam("endCreateTime") Date endCreateTime, @QueryParam("owner") String owner, @QueryParam("slipCode2") String slipCode2,
            @QueryParam("slipCode1") String slipCode1, @QueryParam("refSlipCode") String refSlipCode, @QueryParam("ouid") Long ouid, RowMapper<StockTransApplicationCommand> rowMapper);

    @NativeQuery
    List<OutBoundPackingObj> findOutBoundPackageByStaid(@QueryParam("staid") Long staid, RowMapper<OutBoundPackingObj> beanPropertyRowMapper);

    /**
     * 查询退大仓指令
     * 
     * @param slipCode
     * @return
     */
    @NativeQuery
    StockTransApplication findReturnMaxWarehouseOrder(@QueryParam("refSlipCode") String refSlipCode, RowMapper<StockTransApplication> rowMapper);

    @NativeQuery
    StockTransApplication findReturnExecuteSuccess(@QueryParam("code") String code, RowMapper<StockTransApplication> rowMapper);

    @NativeQuery
    StockTransApplication findNotExecuteReturnOrder(@QueryParam("staCode") String staCode, RowMapper<StockTransApplication> rowMapper);

    @NativeUpdate
    void updatePLExputCountByPlId(@QueryParam("plId") Long plId);

    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StockTransApplicationCommand> findCreateOutOrder(int start, int size, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "createTime") Date createTime, @QueryParam(value = "endCreateTime") Date endCreateTime,
            @QueryParam(value = "code") String code, @QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "lpcode") String lpcode, @QueryParam(value = "owner") String owner, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sorts,
            @QueryParam(value = "olist") List<Long> lists);

    @NativeUpdate
    void updateLpcodeByForm(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "updateLpcode") String updateLpcode, @QueryParam(value = "createTime") Date createTime, @QueryParam(value = "endCreateTime") Date endCreateTime,
            @QueryParam(value = "code") String code, @QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "lpcode") String lpcode, @QueryParam(value = "owner") String owner);

    @DynamicQuery
    List<StockTransApplication> queryLpcodeByForm(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "updateLpcode") String updateLpcode, @QueryParam(value = "createTime") Date createTime, @QueryParam(value = "endCreateTime") Date endCreateTime,
            @QueryParam(value = "code") String code, @QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "lpcode") String lpcode, @QueryParam(value = "owner") String owner, @QueryParam(value = "wids") List<Long> wids);

    @NativeUpdate
    void updateLpcodeById(@QueryParam(value = "staId") Long staId, @QueryParam(value = "lpcode") String lpcode);

    @NativeUpdate
    void removeTransFailedStaFromPickingList(@QueryParam(value = "plId") Long plId);

    @NativeUpdate
    int staRemoveFromPickingList(@QueryParam(value = "staId") Long staId, @QueryParam(value = "intVer") int version);

    @NativeQuery
    List<SalesReportFormCommand> findSalesReportForm(@QueryParam(value = "outboundTime") Date outboundTime, @QueryParam(value = "endOutboundTime") Date endOutboundTime, RowMapper<SalesReportFormCommand> rowMapper);

    @NativeUpdate
    void updateHOListByHOLint(@QueryParam(value = "hoListId") Long hoListId);

    @NamedQuery
    StockTransApplication finrefSlipCode(@QueryParam("refSlipCode") String refSlipCode);

    @NamedQuery
    StockTransApplication findStaByslipCodeandShopId(@QueryParam("slipCode") String slipCode, @QueryParam("owner") String owner);

    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StockTransApplicationCommand> findInBoundStaAll(int start, int size, @QueryParam(value = "ouId") Long ouId, @QueryParam Map<String, Object> map, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sorts);


    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StockTransApplicationCommand> findInBoundSta(int start, int size, @QueryParam(value = "ouId") Long ouId, @QueryParam Map<String, Object> map, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sorts);

    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StockTransApplicationCommand> findInBoundStaVmi(int start, int size, @QueryParam(value = "ouId") Long ouId, @QueryParam Map<String, Object> map, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sorts);

    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StockTransApplicationCommand> findInboundStaFinish(int start, int size, @QueryParam(value = "ouId") Long ouId, @QueryParam Map<String, Object> map, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sorts);

    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StockTransApplicationCommand> findInBoundStas(int start, int size, @QueryParam(value = "ouId") Long ouId, @QueryParam Map<String, Object> map, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sorts);

    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StockTransApplicationCommand> findInBoundAllSta(int start, int size, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "stvtype") Integer stvtype, @QueryParam(value = "status") Integer status,
            @QueryParam(value = "isPda") Boolean isPda, @QueryParam Map<String, Object> map, RowMapper<StockTransApplicationCommand> rowMapper);

    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StockTransApplicationCommand> findPdaShelvesSta(int start, int size, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "stvtype") Integer stvtype, @QueryParam(value = "status") Integer status,
            @QueryParam(value = "isPda") Boolean isPda, @QueryParam Map<String, Object> map, RowMapper<StockTransApplicationCommand> rowMapper);

    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StockTransApplicationCommand> checkPdaShelvesStaLine(int start, int size, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "stvtype") Integer stvtype, @QueryParam(value = "status") Integer status,
            @QueryParam(value = "staId") Long staId, @QueryParam Map<String, Object> map, RowMapper<StockTransApplicationCommand> rowMapper);


    @NativeQuery
    List<PdaStockTransApplicationCommand> selectPdaSta(@QueryParam("code") String code, @QueryParam("uniqCode") String uniqCode, BeanPropertyRowMapper<PdaStockTransApplicationCommand> beanPropertyRowMapper);

    @NamedQuery
    StockTransApplication findStaByCodeAndWhId(@QueryParam("code") String code, @QueryParam("uniqueCode") Long uniqCode);

    /**
     * 检查给定的库内移动作业单是否存在
     * 
     * @param code
     * @param parseLong
     * @return
     */
    @NamedQuery
    StockTransApplication findLMStaByCodeAndWhId(@QueryParam("code") String code, @QueryParam("uniqueCode") Long uniqCode);

    /**
     * Pda 获取退仓单数据检验退仓单是否存在
     * 
     * @param code
     * @param uniqCode
     * @return
     */
    @NamedQuery
    StockTransApplication findIfExistReturnOrder(@QueryParam("code") String code, @QueryParam("uniqueCode") Long uniqCode);

    /**
     * 更新STA最大长度 fanht
     * 
     * @param id
     */
    @NativeUpdate
    void updateSkuMaxLength(@QueryParam("id") Long id);

    /**
     * 更新STA信息 fanht
     * 
     * @param id
     * @param parcelSortingMode
     */
    @NativeUpdate
    void updateOrderInfo(@QueryParam("id") Long id, @QueryParam("parcelSortingMode") Integer parcelSortingMode, @QueryParam("isGroupBuying") Boolean isGroupBuying);

    /**
     * 根据申型状态获取待配货列表，创建配货批，fanht
     * 
     * @param whId 仓库
     * @param statusList 状态列表
     * @param fromDate 创建起始时间
     * @param toDate 创建结束时间
     * @param code 编码
     * @param refSlipCode 前置单据便编码
     * @param typeList 类型列表
     * @param isNeedInvoice 是否需要发票
     * @param lpCode 物流供应商编码
     * @param shopId 店铺id
     * @param pickingListCode 配货清单编码
     * @param receiver 收货人
     * @param trackingNo 快递单号
     * @param checkPickingList 是否检查存在picking list
     * @param isCkSameSeqNoSta 是否检查同批次入库sta状态
     * @param isLike 是否使用模糊查询
     * @param skuMaxLength 商品体积单边最长值（用于区分大小件） fanht
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findSalesStaList(@QueryParam(value = "isSpPg") Boolean isSpPg, @QueryParam(value = "cities") List<String> cities, @QueryParam(value = "province") String province,
            @QueryParam(value = "isLpcodeNotNull") Boolean isLpcodeNotNull, @QueryParam(value = "isSnSta") Boolean isSnSta, @QueryParam(value = "pickingMode") Integer pickingMode, @QueryParam(value = "skus") String skus,
            @QueryParam(value = "mainWarehouse") Long whId, @QueryParam("idList") List<Long> idList, @QueryParam(value = "statusList") List<Integer> statusList, @QueryParam(value = "fromDate") Date fromDate, @QueryParam(value = "toDate") Date toDate,
            @QueryParam(value = "code") String code, @QueryParam(value = "refSlipCode") String refSlipCode, @QueryParam(value = "typeList") List<Integer> typeList,
            @QueryParam(value = "isNeedInvoice") Boolean isNeedInvoice,
            @QueryParam(value = "lpCode") String lpCode,
            @QueryParam(value = "shopInnerCodes") List<String> shopInnerCodes, // /
                                                                               // 16
            @QueryParam(value = "shopId") String shopId, @QueryParam(value = "pickingListCode") String pickingListCode, @QueryParam(value = "receiver") String receiver,
            @QueryParam(value = "trackingNo") String trackingNo, // 20
            @QueryParam(value = "checkPickingList") Boolean checkPickingList, @QueryParam(value = "isCkSameSeqNoSta") Boolean isCkSameSeqNoSta, @QueryParam(value = "isLike") Boolean isLike, @QueryParam(value = "skuQty") Long skuQty,
            @QueryParam(value = "skuCodeList") List<String> skuCodeList, @QueryParam Map<String, String> sku, @QueryParam(value = "skuMaxLength") BigDecimal skuMaxLength, Sort[] sorts, RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 根据申型状态获取待配货列表，创建配货批，fanht
     * 
     * @param whId 仓库
     * @param statusList 状态列表
     * @param fromDate 创建起始时间
     * @param toDate 创建结束时间
     * @param code 编码
     * @param refSlipCode 前置单据便编码
     * @param typeList 类型列表
     * @param isNeedInvoice 是否需要发票
     * @param lpCode 物流供应商编码
     * @param shopId 店铺id
     * @param pickingListCode 配货清单编码
     * @param receiver 收货人
     * @param trackingNo 快递单号
     * @param checkPickingList 是否检查存在picking list
     * @param isCkSameSeqNoSta 是否检查同批次入库sta状态
     * @param isLpcodeNotNull 是否把没有物流商的单子过滤掉
     * @param isLike 是否使用模糊查询
     * @param skuMaxLength 商品体积单边最长值（用于区分大小件） fanht
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StockTransApplicationCommand> findSalesStaListPage(int start, int pageSzie, @QueryParam(value = "isSpPg") Boolean isSpPg, @QueryParam(value = "cities") List<String> cities, @QueryParam(value = "province") String province,
            @QueryParam(value = "isLpcodeNotNull") Boolean isLpcodeNotNull, @QueryParam(value = "isSnSta") Boolean isSnSta, @QueryParam(value = "pickingMode") Integer pickingMode, @QueryParam(value = "skus") String skus,
            @QueryParam(value = "mainWarehouse") Long whId, @QueryParam(value = "statusList") List<Integer> statusList, @QueryParam(value = "fromDate") Date fromDate, @QueryParam(value = "toDate") Date toDate, @QueryParam(value = "code") String code,
            @QueryParam(value = "refSlipCode") String refSlipCode, @QueryParam(value = "typeList") List<Integer> typeList, @QueryParam(value = "isNeedInvoice") Boolean isNeedInvoice, @QueryParam(value = "lpCode") String lpCode,
            @QueryParam(value = "shopInnerCodes") List<String> shopInnerCodes, @QueryParam(value = "shopId") String shopId, @QueryParam(value = "pickingListCode") String pickingListCode, @QueryParam(value = "receiver") String receiver,
            @QueryParam(value = "trackingNo") String trackingNo, @QueryParam(value = "checkPickingList") Boolean checkPickingList, @QueryParam(value = "isCkSameSeqNoSta") Boolean isCkSameSeqNoSta, @QueryParam(value = "isLike") Boolean isLike,
            @QueryParam(value = "skuQty") Long skuQty, @QueryParam(value = "skuCodeList") List<String> skuCodeList, @QueryParam Map<String, String> sku, @QueryParam(value = "skuMaxLength") BigDecimal skuMaxLength, Sort[] sorts,
            RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 查询可用作业单生成配货清单 KJL
     * 
     * @param start
     * @param pageSize
     * @param barCode
     * @param packageSkuId
     * @param ssList
     * @param isSn
     * @param skus
     * @param isSedKill
     * @param skuQty
     * @param shopInnerCodes
     * @param cityList
     * @param date
     * @param date2
     * @param orderCreateTime
     * @param toOrderCreateTime
     * @param code
     * @param refSlipCode
     * @param isNeedInvoice
     * @param lpCode
     * @param isSpecialPackaging
     * @param categoryId
     * @param status
     * @param id
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findStaForPickingByModel(int start, int pageSize, @QueryParam("areaList") String areaList, @QueryParam("whAreaList") String whAreaList, @QueryParam("mergePickZoon") Integer mergePickZoon,
            @QueryParam("mergeWhZoon") Integer mergeWhZoon, @QueryParam("isMergeInt") Integer isMergeInt, @QueryParam("isMargeZoon") Integer isMargeZoon, @QueryParam("transTimeType") Integer transTimeType, @QueryParam("barCode") String barCode,
            @QueryParam("code1") String code1, @QueryParam("code2") String code2, @QueryParam("code3") String code3, @QueryParam("code4") String code4, @QueryParam("packageSkuId") Long packageSkuId, @QueryParam("ssList") List<SkuSizeConfig> ssList,
            @QueryParam("isCod") Integer isCod, @QueryParam("isSn") Integer isSn, @QueryParam("skus") String skus, @QueryParam("pickingType") Integer pickingType, @QueryParam("skuQty") Long skuQty, @QueryParam("shopList") List<String> shopInnerCodes,
            @QueryParam("zoneList") List<String> zoneList, @QueryParam("whZoneList") List<String> whZoneList, @QueryParam("cityList") List<String> cityList, @QueryParam("fromDate") Date date, @QueryParam("toDate") Date date2,
            @QueryParam("orderCreateTime") Date orderCreateTime, @QueryParam("toOrderCreateTime") Date toOrderCreateTime, @QueryParam("code") String code, @QueryParam("refCode") String refSlipCode, @QueryParam("isNeedInvoice") Integer isNeedInvoice,
            @QueryParam("lpCode") List<String> lpCode, @QueryParam("specialType") Integer specialType, @QueryParam("isSpecialPackaging") Integer isSpecialPackaging, @QueryParam("categoryId") Long categoryId, @QueryParam("status") Integer status,
            @QueryParam("packingType") Integer packingType, @QueryParam("id") Long id, @QueryParam("toLocation") String toLocation, @QueryParam("pickSubType") Long pickSubType, @QueryParam("flag") Boolean flag, @QueryParam("isPreSale") String isPreSale,
            @QueryParam("orderType2") Integer orderType2, @QueryParam("isMacaoOrder") Integer isMacaoOrder, @QueryParam("isPrintMaCaoHGD") Integer isPrintMaCaoHGD, Sort[] sorts, @QueryParam("msg") Boolean msg,
            @QueryParam("priorityList") List<String> priorityList, @QueryParam("lg") Boolean lg, @QueryParam("opposite") Boolean opposite, @QueryParam("otoList") List<String> otoList, @QueryParam("cp") Integer cp,
            BeanPropertyRowMapper<StockTransApplicationCommand> beanPropertyRowMapper);


    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findPickFailedByWhId(int start, int pageSize, @QueryParam("owner") String owner, @QueryParam("transTimeType") Integer transTimeType, @QueryParam("lpCode") List<String> lpCode,
            @QueryParam("cityList") List<String> cityList, @QueryParam("isCod") Integer isCod, @QueryParam("pickStatus") Integer pickStatus, @QueryParam("pickSort") Long pickSort, @QueryParam("categoryId") Long categoryId,
            @QueryParam("isNeedInvoice") Integer isNeedInvoice, @QueryParam("fromDate") Date date, @QueryParam("toDate") Date date2, @QueryParam("orderCreateTime") Date orderCreateTime, @QueryParam("toOrderCreateTime") Date toOrderCreateTime,
            @QueryParam("code") String code, @QueryParam("slipCode") String slipCode, @QueryParam("ouId") Long ouId, @QueryParam("isPreSale") String isPreSale, Sort[] sorts, BeanPropertyRowMapper<StockTransApplicationCommand> beanPropertyRowMapper);

    @NativeUpdate
    void updatePickFailedByWhId(@QueryParam("ocpSort") Integer ocpSort, @QueryParam("owner") String owner, @QueryParam("transTimeType") Integer transTimeType, @QueryParam("lpCode") List<String> lpCode, @QueryParam("cityList") List<String> cityList,
            @QueryParam("isCod") Integer isCod, @QueryParam("pickStatus") Integer pickStatus, @QueryParam("pickSort") Long pickSort, @QueryParam("categoryId") Long categoryId, @QueryParam("isNeedInvoice") Integer isNeedInvoice,
            @QueryParam("fromDate") Date date, @QueryParam("toDate") Date date2, @QueryParam("orderCreateTime") Date orderCreateTime, @QueryParam("toOrderCreateTime") Date toOrderCreateTime, @QueryParam("code") String code,
            @QueryParam("slipCode") String slipCode, @QueryParam("ouId") Long ouId, @QueryParam("isPreSale") String isPreSale, BeanPropertyRowMapper<StockTransApplicationCommand> beanPropertyRowMapper);

    @NativeUpdate
    void failedReusltAgainPick(@QueryParam("ouId") Long ouId, @QueryParam("errorCount") Integer errorCount);

    /**
     * 查询合单情况下的子单 fanht
     * 
     * @param groupStaId
     * @return
     */
    @NamedQuery
    List<StockTransApplication> findGroupStaList(@QueryParam(value = "groupStaId") Long groupStaId);

    /**
     * 根据Staid查看其是否有子Sta 有则返回staList KJL
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplication> getSonSta(@QueryParam("id") Long id, BeanPropertyRowMapper<StockTransApplication> beanPropertyRowMapper);

    /**
     * 根据申型状态获取待配货列表 新修改只针对，模式一 KJL
     * 
     * @param whId 仓库
     * @param statusList 状态列表
     * @param fromDate 创建起始时间
     * @param toDate 创建结束时间
     * @param code 编码
     * @param refSlipCode 前置单据便编码
     * @param typeList 类型列表
     * @param isNeedInvoice 是否需要发票
     * @param lpCode 物流供应商编码
     * @param shopId 店铺id
     * @param pickingListCode 配货清单编码
     * @param receiver 收货人
     * @param trackingNo 快递单号
     * @param checkPickingList 是否检查存在picking list
     * @param isCkSameSeqNoSta 是否检查同批次入库sta状态
     * @param isLpcodeNotNull 是否把没有物流商的单子过滤掉
     * @param isLike 是否使用模糊查询
     * @param skuMaxLength 商品体积单边最长值（用于区分大小件） fanht
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StockTransApplicationCommand> findSalesStaListPageNewModel1(int start, int pageSzie, @QueryParam(value = "isSpPg") Boolean isSpPg, @QueryParam(value = "cities") List<String> cities, @QueryParam(value = "province") String province,
            @QueryParam(value = "isLpcodeNotNull") Boolean isLpcodeNotNull, @QueryParam(value = "isSnSta") Boolean isSnSta, @QueryParam(value = "pickingMode") Integer pickingMode, @QueryParam(value = "skus") String skus,
            @QueryParam(value = "mainWarehouse") Long whId, @QueryParam(value = "statusList") List<Integer> statusList, @QueryParam(value = "fromDate") Date fromDate, @QueryParam(value = "toDate") Date toDate, @QueryParam(value = "code") String code,
            @QueryParam(value = "refSlipCode") String refSlipCode, @QueryParam(value = "typeList") List<Integer> typeList, @QueryParam(value = "isNeedInvoice") Boolean isNeedInvoice, @QueryParam(value = "lpCode") String lpCode,
            @QueryParam(value = "shopInnerCodes") List<String> shopInnerCodes, @QueryParam(value = "shopId") String shopId, @QueryParam(value = "pickingListCode") String pickingListCode, @QueryParam(value = "receiver") String receiver,
            @QueryParam(value = "trackingNo") String trackingNo, @QueryParam(value = "checkPickingList") Boolean checkPickingList, @QueryParam(value = "isCkSameSeqNoSta") Boolean isCkSameSeqNoSta, @QueryParam(value = "isLike") Boolean isLike,
            @QueryParam(value = "skuQty") Long skuQty, @QueryParam(value = "skuCodeList") List<String> skuCodeList, @QueryParam Map<String, String> sku, @QueryParam(value = "skuMaxLength") BigDecimal skuMaxLength, Sort[] sorts,
            RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 根据申型状态获取待配货列表，创建配货批，fanht
     * 
     * @param whId 仓库
     * @param statusList 状态列表
     * @param fromDate 创建起始时间
     * @param toDate 创建结束时间
     * @param code 编码
     * @param refSlipCode 前置单据便编码
     * @param typeList 类型列表
     * @param isNeedInvoice 是否需要发票
     * @param lpCode 物流供应商编码
     * @param shopId 店铺id
     * @param pickingListCode 配货清单编码
     * @param receiver 收货人
     * @param trackingNo 快递单号
     * @param checkPickingList 是否检查存在picking list
     * @param isCkSameSeqNoSta 是否检查同批次入库sta状态
     * @param isLike 是否使用模糊查询
     * @param skuMaxLength 商品体积单边最长值（用于区分大小件） fanht
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findSalesStaListForModel1(@QueryParam(value = "isSpPg") Boolean isSpPg, @QueryParam(value = "cities") List<String> cities, @QueryParam(value = "province") String province,
            @QueryParam(value = "isNotGroup") Boolean isNotGroup, @QueryParam(value = "isLpcodeNotNull") Boolean isLpcodeNotNull, @QueryParam(value = "isSnSta") Boolean isSnSta, @QueryParam(value = "pickingMode") Integer pickingMode,
            @QueryParam(value = "skus") String skus, @QueryParam(value = "mainWarehouse") Long whId, @QueryParam(value = "wids") List<Long> plist, @QueryParam(value = "statusList") List<Integer> statusList, @QueryParam(value = "fromDate") Date fromDate,
            @QueryParam(value = "toDate") Date toDate, @QueryParam(value = "code") String code, @QueryParam(value = "refSlipCode") String refSlipCode, @QueryParam(value = "typeList") List<Integer> typeList,
            @QueryParam(value = "isNeedInvoice") Boolean isNeedInvoice,
            @QueryParam(value = "lpCode") String lpCode,
            @QueryParam(value = "shopInnerCodes") List<String> shopInnerCodes, // /
                                                                               // 16
            @QueryParam(value = "shopId") String shopId, @QueryParam(value = "pickingListCode") String pickingListCode, @QueryParam(value = "receiver") String receiver,
            @QueryParam(value = "trackingNo") String trackingNo, // 20
            @QueryParam(value = "checkPickingList") Boolean checkPickingList, @QueryParam(value = "isCkSameSeqNoSta") Boolean isCkSameSeqNoSta, @QueryParam(value = "isLike") Boolean isLike, @QueryParam(value = "skuQty") Long skuQty,
            @QueryParam(value = "skuCodeList") List<String> skuCodeList, @QueryParam Map<String, String> sku, @QueryParam(value = "skuMaxLength") BigDecimal skuMaxLength, Sort[] sorts, RowMapper<StockTransApplicationCommand> rowMapper);


    /**
     * 根据申型状态获取待配货列表，创建配货批，fanht by page
     * 
     * @param whId 仓库
     * @param statusList 状态列表
     * @param fromDate 创建起始时间
     * @param toDate 创建结束时间
     * @param code 编码
     * @param refSlipCode 前置单据便编码
     * @param typeList 类型列表
     * @param isNeedInvoice 是否需要发票
     * @param lpCode 物流供应商编码
     * @param shopId 店铺id
     * @param pickingListCode 配货清单编码
     * @param receiver 收货人
     * @param trackingNo 快递单号
     * @param checkPickingList 是否检查存在picking list
     * @param isCkSameSeqNoSta 是否检查同批次入库sta状态
     * @param isLike 是否使用模糊查询
     * @param skuMaxLength 商品体积单边最长值（用于区分大小件） fanht
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findSalesStaListForModel1ByPage(int start, int pagesize, @QueryParam(value = "isSpPg") Boolean isSpPg, @QueryParam(value = "cities") List<String> cities, @QueryParam(value = "province") String province,
            @QueryParam(value = "isNotGroup") Boolean isNotGroup, @QueryParam(value = "isLpcodeNotNull") Boolean isLpcodeNotNull, @QueryParam(value = "isSnSta") Boolean isSnSta, @QueryParam(value = "pickingMode") Integer pickingMode,
            @QueryParam(value = "skus") String skus, @QueryParam(value = "mainWarehouse") Long whId, @QueryParam(value = "wids") List<Long> plist, @QueryParam(value = "statusList") List<Integer> statusList, @QueryParam(value = "fromDate") Date fromDate,
            @QueryParam(value = "toDate") Date toDate, @QueryParam(value = "code") String code, @QueryParam(value = "refSlipCode") String refSlipCode, @QueryParam(value = "typeList") List<Integer> typeList,
            @QueryParam(value = "isNeedInvoice") Boolean isNeedInvoice,
            @QueryParam(value = "lpCode") String lpCode,
            @QueryParam(value = "shopInnerCodes") List<String> shopInnerCodes, // /
                                                                               // 16
            @QueryParam(value = "shopId") String shopId, @QueryParam(value = "pickingListCode") String pickingListCode, @QueryParam(value = "receiver") String receiver,
            @QueryParam(value = "trackingNo") String trackingNo, // 20
            @QueryParam(value = "checkPickingList") Boolean checkPickingList, @QueryParam(value = "isCkSameSeqNoSta") Boolean isCkSameSeqNoSta, @QueryParam(value = "isLike") Boolean isLike, @QueryParam(value = "skuQty") Long skuQty,
            @QueryParam(value = "skuCodeList") List<String> skuCodeList, @QueryParam Map<String, String> sku, @QueryParam(value = "skuMaxLength") BigDecimal skuMaxLength, Sort[] sorts, RowMapper<StockTransApplicationCommand> rowMapper);


    /**
     * 根据type和Code查询订单
     * 
     * @param orderCode
     * @param type
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findStaBySlipCodeAndType(@QueryParam("orderCode") String orderCode, @QueryParam("type") Integer type, BeanPropertyRowMapper<StockTransApplicationCommand> beanPropertyRowMapper);

    /**
     * 根据条件更新秒杀订单为非秒杀
     * 
     * @param skus
     * @param id
     */
    @NativeUpdate
    void updateStaTypeSecKill(@QueryParam("skus") String skus, @QueryParam("ouId") Long id, @QueryParam("isSedKill") Boolean isSedKill);

    /**
     * 根据配货清单id查询出来所有的sta
     * 
     * @param start
     * @param pageSize
     * @param pId
     * @param ouId
     * @param sorts
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> getAllStaByPickingListId(int start, int pageSize, @QueryParam("plId") Long pId, @QueryParam("ouId") Long ouId, Sort[] sorts, BeanPropertyRowMapperExt<StockTransApplicationCommand> beanPropertyRowMapperExt);

    /**
     * 根据相关单据号 和 配货清单编号查询Sta
     * 
     * @param id pickingList ID
     * @param code slipCode
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    StockTransApplication findStaBySlipCodeAndPid(@QueryParam("pId") Long id, @QueryParam("slipCode") String code, BeanPropertyRowMapper<StockTransApplication> beanPropertyRowMapper);

    /**
     * 根据配货清单id查询所有staId 秒杀使用 KJL
     * 
     * @param pId
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findStaByPid(@QueryParam("pId") Long pId, SingleColumnRowMapper<Long> singleColumnRowMapper);


    /**
     * 查询作业单类型
     * 
     * @param pId
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    StockTransApplicationCommand findStaById(@QueryParam("staId") Long staId, BeanPropertyRowMapperExt<StockTransApplicationCommand> beanPropertyRowMapper);

    /**
     * 新建秒杀商品时 更新原始非秒杀订单为秒杀订单
     * 
     * @param substring
     */
    @NativeUpdate
    void updateSomeStaToSecKillOrder(@QueryParam("skus") String substring, @QueryParam("ouId") Long ouId);

    /**
     * 根据交接清单号查询全部交接的作业单号 KJL
     * 
     * @param id
     * @param status
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplication> findAllHandStaByHandList(@QueryParam("handId") Long id, @QueryParam("status") int status, BeanPropertyRowMapper<StockTransApplication> beanPropertyRowMapper);

    /**
     * 根据交接清单号查询全部交接的作业单号 KJL（原始逻辑）
     * 
     * @param id
     * @param status
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplication> findAllHandStaByHandList2(@QueryParam("handId") Long id, @QueryParam("status") int status, BeanPropertyRowMapper<StockTransApplication> beanPropertyRowMapper);


    /*
     * 根据交接清单号查询全部交接的作业单号 O2O
     */
    @NativeQuery
    List<StockTransApplication> findAllHandStaByHandListO2O(@QueryParam("handId") Long id, @QueryParam("status") int status, BeanPropertyRowMapper<StockTransApplication> beanPropertyRowMapper);

    /**
     * 根据物流单号查询作业单 KJL
     * 
     * @param checkMode
     * 
     * @param trackingNo
     * @param statusList
     * @param whOuId
     * @param idList
     * @param status
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findSalesStaByTrackingNo(@QueryParam("checkMode") int checkMode, @QueryParam("trackingNo") String trackingNo, @QueryParam("statusList") List<Integer> statusList, @QueryParam("ouId") Long whOuId,
            @QueryParam("idList") List<Long> idList, @QueryParam("status") int status, BeanPropertyRowMapper<StockTransApplicationCommand> beanPropertyRowMapper);

    /**
     * 根据配货清单查询该配货清单下的取消的作业单列表 KJL
     * 
     * @param id
     * @param statusList
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> getCancelStaListPgIndex(@QueryParam("id") Long id, @QueryParam("statusList") List<Integer> statusList, Sort[] sorts, BeanPropertyRowMapper<StockTransApplicationCommand> beanPropertyRowMapper);

    /**
     * 根据staId删除对应的pickingList关联
     * 
     * @param staId
     */
    @NativeUpdate
    void updatePickingListIdById(@QueryParam("staId") Long staId);

    /**
     * 获取pickingList是否包含取消作业单 KJL
     * 
     * @param id
     * @param statusList
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Boolean findIfExistsCancelSta(@QueryParam("plId") Long id, @QueryParam("statusList") List<Integer> statusList, SingleColumnRowMapper<Boolean> singleColumnRowMapper);


    /**
     * 查询导出销售出库信息
     * 
     * @param ouId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findExportSalesSendOutInfo(@QueryParam("plId") Long plId, BeanPropertyRowMapperExt<StockTransApplicationCommand> beanPropertyRowMapper);

    /**
     * 查询导出退货原因退款信息
     * 
     * @param ouId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findExportreturnRegisterInfo(@QueryParam("ouId") Long ouId, BeanPropertyRowMapperExt<StockTransApplicationCommand> beanPropertyRowMapper);

    /**
     * 查询所有可创建的配货清单的作业单
     * 
     * @param ouId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findCreatePKListAllSta(@QueryParam("ouId") Long ouId, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 更新多件为套装组合商品 KJL
     * 
     * @param split
     * 
     * @param psId
     * @param staTotalSkuQty
     * @param skuQty
     * @param skus1
     * @param skus2
     * @param skus3
     * @param id
     * @param type
     * @param statusList
     * @param packageType
     */
    @NativeUpdate
    void updateSomeStaToPackageSku(@QueryParam("split") String split, @QueryParam("psId") Long psId, @QueryParam("staTotalSkuQty") Long staTotalSkuQty, @QueryParam("skuQty") Long skuQty, @QueryParam("skus1") String skus1,
            @QueryParam("skus2") String skus2, @QueryParam("skus3") String skus3, @QueryParam("ouId") Long id, @QueryParam("pickingType") int type, @QueryParam("statusList") List<Integer> statusList, @QueryParam("packageType") int packageType);

    /**
     * 根据套装组合商品Id更新对应的作业单为普通多件作业单，删除套装组合商品关联 KJL
     * 
     * @param packageSkuId
     * @param packageType
     * @param manyType
     * @param statusList
     * @param ouId
     */
    @NativeUpdate
    void updateStaPackageToMany(@QueryParam("psId") Long packageSkuId, @QueryParam("packageType") int packageType, @QueryParam("manyType") int manyType, @QueryParam("statusList") List<Integer> statusList, @QueryParam("ouId") Long ouId);

    /**
     * 
     * @param skus1
     * @param staTotalSkuQty
     * @param id
     * @param skuQty
     * @param statusList
     * @param split
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplication> selectStaByPackageSku(@QueryParam("skus1") String skus1, @QueryParam("staTotalSkuQty") Long staTotalSkuQty, @QueryParam("ouId") Long id, @QueryParam("skuQty") Long skuQty,
            @QueryParam("statusList") List<Integer> statusList, @QueryParam("split") String split, BeanPropertyRowMapper<StockTransApplication> beanPropertyRowMapper);

    /**
     * 跟新作业单为套装组合商品
     * 
     * @param idList
     * @param psId
     * @param value
     */
    @NativeUpdate
    void updateStaToPackageSkuByIdList(@QueryParam("idList") List<Long> idList, @QueryParam("psId") Long psId, @QueryParam("pickingType") int value);

    /**
     * 查询满足条件的秒杀staList
     * 
     * @param ouId
     * @param skus
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplication> getSecKillStaList(@QueryParam("ouId") Long ouId, @QueryParam("skus") String skus, BeanPropertyRowMapper<StockTransApplication> beanPropertyRowMapper);

    /**
     * 查询staSlipCODE
     * 
     * @param plId
     * @param staId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplication> findStaSlipCode(@QueryParam("plId") Long plId, @QueryParam("staId") Long staId, BeanPropertyRowMapperExt<StockTransApplication> beanPropertyRowMapper);


    /**
     * coach 单据查询
     * 
     * @param start
     * @param pageSize
     * @param code
     * @param slipCode
     * @param status
     * @param type
     * @param createTime
     * @param endCreateTime
     * @param ouId
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> queryCoachSta(int start, int pageSize, @QueryParam("code") String code, @QueryParam("slipCode") String slipCode, @QueryParam("status") Integer status, @QueryParam("type") Integer type,
            @QueryParam("createTime") Date createTime, @QueryParam("endCreateTime") Date endCreateTime, @QueryParam("ouId") Long ouId, BeanPropertyRowMapperExt<StockTransApplicationCommand> beanPropertyRowMapper);

    /**
     * 根据idList更新作业单为秒杀订单
     * 
     * @param idList
     * @param pickingType
     * @param isSedKill
     */
    @NativeUpdate
    void updateStaToSecKillByIdList(@QueryParam("idList") List<Long> idList, @QueryParam("isSedKill") int isSedKill, @QueryParam("pickingType") int pickingType);

    /**
     * 根据状态和仓库Id 查询作业单列表 KJL
     * 
     * @param whId
     * @param lpList
     * @param value
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findStaByOuIdAndStatus(@QueryParam("ouId") Long whId, @QueryParam("lpList") List<String> lpList, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 根据状态和仓库Id 查询作业单列表 KJL
     * 
     * @param whId
     * @param lpList
     * @param value
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findStaByOwnerAndStatus(@QueryParam("owner") String owner, @QueryParam("lpList") List<String> lpList, @QueryParam("status") int value, SingleColumnRowMapper<Long> singleColumnRowMapper);


    /**
     * 
     * @param packageSkuId
     * @param ssList
     * @param isSn
     * @param skus
     * @param pickingType
     * @param skuQty
     * @param shopInnerCodes
     * @param cityList
     * @param fromDate
     * @param toDate
     * @param code
     * @param refSlipCode
     * @param isNeedInvoice
     * @param lpCode
     * @param isSpecialPackaging
     * @param categoryId
     * @param status
     * @param ouId
     * @param rowNum
     * @param sorts
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findStaForPickingByModelListNew(@QueryParam("pickSubType") Long pickSubType, @QueryParam("whZoonId") String whZoonId, @QueryParam("whZoonList") String whZoonList, @QueryParam("mergeWhZoon") Integer mergeWhZoon,
            @QueryParam("isMargeWhZoon") Integer isMargeWhZoon, @QueryParam("zoonId") String zoonId, @QueryParam("zoonList") String zoonList, @QueryParam("mergePickZoon") Integer mergePickZoon, @QueryParam("isMergeInt") Integer isMergeInt,
            @QueryParam("barCode") String barCode, @QueryParam("packageSkuId") Long packageSkuId, @QueryParam("ssList") List<SkuSizeConfig> ssList, @QueryParam("isCod") Integer isCod, @QueryParam("isSn") Integer isSn, @QueryParam("skus") String skus,
            @QueryParam("pickingType") Integer pickingType, @QueryParam("skuQty") Long skuQty, @QueryParam("shopList") List<String> shopInnerCodes, @QueryParam("cityList") List<String> cityList, @QueryParam("fromDate") Date date,
            @QueryParam("toDate") Date date2, @QueryParam("orderCreateTime") Date orderCreateTime, @QueryParam("toOrderCreateTime") Date toOrderCreateTime, @QueryParam("code") String code, @QueryParam("refCode") String refSlipCode,
            @QueryParam("isNeedInvoice") Integer isNeedInvoice, @QueryParam("lpCode") List<String> lpCode, @QueryParam("specialType") Integer specialType, @QueryParam("isSpecialPackaging") Integer isSpecialPackaging,
            @QueryParam("categoryId") Long categoryId, @QueryParam("status") Integer status, @QueryParam("packingType") Integer packingType, @QueryParam("id") Long id, @QueryParam("transTimeType") Integer transTimeType,
            @QueryParam("rowNum") Integer rowNum, @QueryParam("sumCount") Integer sumCount, @QueryParam("toLocation") String toLocation, @QueryParam("flag") Boolean flag, @QueryParam("isPreSale") String isPreSale,
            @QueryParam("orderType2") Integer orderType2, @QueryParam("otoList") List<String> otoList, @QueryParam("cp") Integer cp, @QueryParam("pickZoneList") List<String> pickZoneList, SingleColumnRowMapper<Long> singleColumnRowMapper);


    @NativeQuery
    List<Long> findStaForPickingByModelListNew1(@QueryParam("zoonId") String zoonId, @QueryParam("isMergeInt") Integer isMergeInt, @QueryParam("barCode") String barCode, @QueryParam("packageSkuId") Long packageSkuId,
            @QueryParam("ssList") List<SkuSizeConfig> ssList, @QueryParam("isCod") Integer isCod, @QueryParam("isSn") Integer isSn, @QueryParam("skus") String skus, @QueryParam("pickingType") Integer pickingType, @QueryParam("skuQty") Long skuQty,
            @QueryParam("shopList") List<String> shopInnerCodes, @QueryParam("cityList") List<String> cityList, @QueryParam("fromDate") Date date, @QueryParam("toDate") Date date2, @QueryParam("orderCreateTime") Date orderCreateTime,
            @QueryParam("toOrderCreateTime") Date toOrderCreateTime, @QueryParam("code") String code, @QueryParam("refCode") String refSlipCode, @QueryParam("isNeedInvoice") Integer isNeedInvoice, @QueryParam("lpCode") List<String> lpCode,
            @QueryParam("specialType") Integer specialType, @QueryParam("isSpecialPackaging") Integer isSpecialPackaging, @QueryParam("categoryId") Long categoryId, @QueryParam("status") Integer status, @QueryParam("id") Long id,
            @QueryParam("transTimeType") Integer transTimeType, @QueryParam("rowNum") Integer rowNum, @QueryParam("sumCount") Integer sumCount, @QueryParam("toLocation") String toLocation, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    String getSkusByStaId(@QueryParam("staId") Long id, @QueryParam("split") String string, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    Long getSkuQtyByStaId(@QueryParam("staId") Long id, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    Integer getSkuWeightIsNull(@QueryParam("staId") Long id, SingleColumnRowMapper<Integer> singleColumnRowMapper);

    @NativeQuery
    BigDecimal getStaAllSkuWeight(@QueryParam("staId") Long id, SingleColumnRowMapper<BigDecimal> singleColumnRowMapper);

    @NativeQuery
    Boolean getIsSnByStaId(@QueryParam("staId") Long id, SingleColumnRowMapper<Boolean> singleColumnRowMapper);

    @NativeQuery
    Boolean getIsSnOrExpByStaId(@QueryParam("staId") Long id, SingleColumnRowMapper<Boolean> singleColumnRowMapper);

    @NativeQuery
    Boolean getIsSnOrExpByStaId1(@QueryParam("staId") Long id, SingleColumnRowMapper<Boolean> singleColumnRowMapper);

    @NativeQuery
    Boolean getIsRFIDBySkuId(@QueryParam("staId") Long id, SingleColumnRowMapper<Boolean> singleColumnRowMapper);

    @NativeQuery
    BigDecimal getSkuMaxLengthByStaId(@QueryParam("staId") Long id, SingleColumnRowMapper<BigDecimal> singleColumnRowMapper);

    @NativeQuery
    Integer getIsRailWayByStaId(@QueryParam("staId") Long id, SingleColumnRowMapper<Integer> singleColumnRowMapper);

    @NativeQuery
    List<Long> findIfExistsStaNotPick(@QueryParam("psId") Long packageSku, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 外包仓解锁 查询锁定单据 代替msgOutboundOrderDao.findLockedMsgOrder() KJL
     * 
     * @param rownum
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findLockedOrder(@QueryParam("rowNum") int rownum, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 根据前置单据查询非取消单据
     * 
     * @param code
     * @return
     */
    @NamedQuery
    StockTransApplication findStaBySlipCodeNotCancel(@QueryParam("code") String code);

    /**
     * 根据前置单据查询非取消单据
     * 
     * @param code
     * @return
     */
    @NamedQuery
    List<StockTransApplication> findStaByCodeNotCancel(@QueryParam("code") String code);

    /**
     * 根据前置单据查询非取消已完成单据
     * 
     * @param code
     * @return
     */
    @NamedQuery
    List<StockTransApplication> findStaByCodeNotCancel1(@QueryParam("code") String code);


    /**
     * 根据slipcode1查询样品领用出库
     * 
     * @param slipCode1
     * @param type
     * @return
     */
    @NamedQuery
    StockTransApplication getStaBySlipCode1(@QueryParam("slipCode1") String slipCode1);


    /**
     * 根据slipcode1查询样品领用出库
     * 
     * @param slipCode1
     * @param type
     * @return
     */
    @NamedQuery
    List<StockTransApplication> getStaListBySlipCode1(@QueryParam("slipCode1") String slipCode1);

    @NamedQuery
    List<StockTransApplication> getStasBySlipCode2(@QueryParam("slipCode2") String slipCode2);

    @NativeQuery
    Date getMaxTransactionTime(@QueryParam("staId") Long staId, SingleColumnRowMapper<Date> singleColumnRowMapper);

    /**
     * 香港站新增
     * 
     * @param id
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Boolean getIsSnById(@QueryParam("staId") Long id, SingleColumnRowMapper<Boolean> singleColumnRowMapper);


    /**
     * NIKE打印礼品卡
     * 
     * @param start
     * @param size
     * @param createTime
     * @param endCreateTime
     * @param finishTime
     * @param endFinishTime
     * @param code
     * @param slipCode
     * @param status
     * @param SlipCode1
     * @param SlipCode2
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findPrintGift(int start, int size, @QueryParam("ouId") Long whId, @QueryParam(value = "createTime") Date createTime, @QueryParam(value = "endCreateTime") Date endCreateTime,
            @QueryParam(value = "finishTime") Date finishTime, @QueryParam(value = "endFinishTime") Date endFinishTime, @QueryParam(value = "code") String code, @QueryParam(value = "slipCode") String slipCode,
            @QueryParam(value = "status") Integer status, @QueryParam(value = "slipCode1") String SlipCode1, @QueryParam(value = "slipCode2") String SlipCode2, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sorts);

    /**
     * 获取配货清单下所有的作业单
     * 
     * @param plid
     * @return
     */
    @NamedQuery
    List<StockTransApplication> findWhStaByPlId(@QueryParam(value = "plid") Long plid);


    @NativeQuery
    List<Long> findTrasnNullStaByWH(@QueryParam("whId") Long whId, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    String findOrderBySlipCode(@QueryParam("slipCode") String slipCode1, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 获取合并订单子订单信息
     */
    @NamedQuery
    List<StockTransApplication> findStaByNewStaId(@QueryParam(value = "id") Long id);

    /**
     * 获取合并订单子订单信息
     */
    @NamedQuery
    List<StockTransApplication> findStaBySlipCode1(@QueryParam(value = "slipCode") String slipCode);

    /**
     * 通过STAID释放库存
     */
    @NativeUpdate
    void updateReleaseInventoryByStaId(@QueryParam(value = "staid") Long staid);

    @NativeUpdate
    void deleteStvLineByStaId(@QueryParam(value = "staid") Long staid);

    @NativeUpdate
    void deleteStvByStaId(@QueryParam(value = "staid") Long staid);

    @NativeUpdate
    void updateStaToStatusByStaId(@QueryParam(value = "staid") Long staid);

    @NativeUpdate
    void updateStaToPickingListByPickingListId(@QueryParam(value = "plid") Long plid);

    @NativeUpdate
    void updateStaDeTrackByStaId(@QueryParam(value = "staId") Long staId);

    @NativeUpdate
    void updatePgIndex(@QueryParam("pId") Long pickingListId);

    @NativeQuery
    List<Long> findAllStaByPickingList(@QueryParam("id") Long id, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 获取配货批次中所有作业单和运单信息
     * 
     * @param plId @param ouid @param beanPropertyRowMapper @return
     *        List<StockTransApplicationCommand> @throws
     */
    @NativeQuery
    List<StockTransApplicationCommand> findAllStaAndDeliveryInfoByPickingList(@QueryParam(value = "plId") Long plId, @QueryParam(value = "ouId") Long ouId, BeanPropertyRowMapperExt<StockTransApplicationCommand> beanPropertyRowMapper);

    /**
     * 根据单据号修改 作业单的状态和完成时间
     */
    @NativeUpdate
    void updateTypeAndFinishTimeByid(@QueryParam("id") Long id);

    /**
     * 查询7天之前未完成的单据
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findNeedCancelOrderIdList(SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    Integer findVmiAsnBySlipCode(@QueryParam(value = "ordercode") String ordercode, @QueryParam(value = "owen") String owen, @QueryParam(value = "type") Integer type, SingleColumnRowMapper<Integer> singleColumnRowMapper);

    @NativeQuery
    BigDecimal findVMIRDSEQ(SingleColumnRowMapper<BigDecimal> rowMap);

    @NativeQuery
    List<StockTransApplicationCommand> findNikeAsnStaByRsnPo(BeanPropertyRowMapperExt<StockTransApplicationCommand> rowMap);

    /**
     * @param slipCode
     * @return
     */
    @NamedQuery
    List<StockTransApplication> findBySlipCodeVmiConverse(@QueryParam(value = "slipCode1") String slipCode, @QueryParam(value = "owner") String owner);

    @NamedQuery
    StockTransApplication findBySlipCodeOwner(@QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "owner") String owner);

    /**
     * t_wh_sta edw
     */
    @NativeQuery
    List<StockTransApplicationCommand> findEdwTwhSta(RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 查询状态为新建的 入库 物流宝订单
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findwlbInOrderCodeByStatus(RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 查询状态为新建 出库 物流宝订单
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findwlbOutOrderCodeByStatus(RowMapper<StockTransApplicationCommand> rowMapper);


    /**
     * 查询最近执行时间
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Date findInboundQueryDate(@QueryParam(value = "type") int type, SingleColumnRowMapper<Date> singleColumnRowMapper);

    /**
     * 根据物流宝单号查找作业单
     * 
     * @param orderCode
     * @return
     */
    @NamedQuery
    StockTransApplication getStaByWlbCode(@QueryParam(value = "orderCode") String orderCode, @QueryParam(value = "code") String code);

    @NamedQuery
    List<StockTransApplication> findAllStaByPickingListPackageId(@QueryParam(value = "plpId") Long plpId);

    /**
     * 获取万象中间表相关的信息
     * 
     * @param staId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    StockTransApplicationCommand getWxconfirmOrderQueue(@QueryParam(value = "staId") Long staId, RowMapper<StockTransApplicationCommand> rowMapper);

    @NativeQuery
    Long getPackageSkuIdBySta(@QueryParam("staId") Long id, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    List<StockTransApplicationCommand> findStaForPickingCount(@QueryParam("transTimeType") Integer transTimeType, @QueryParam("barCode") String barCode, @QueryParam("code1") String code1, @QueryParam("code2") String code2,
            @QueryParam("code3") String code3, @QueryParam("code4") String code4, @QueryParam("packageSkuId") Long packageSkuId, @QueryParam("ssList") List<SkuSizeConfig> ssList, @QueryParam("isCod") Integer isCod, @QueryParam("isSn") Integer isSn,
            @QueryParam("skus") String skus, @QueryParam("pickingType") Integer pickingType, @QueryParam("skuQty") Long skuQty, @QueryParam("shopList") List<String> shopInnerCodes, @QueryParam("cityList") List<String> cityList,
            @QueryParam("fromDate") Date date, @QueryParam("toDate") Date date2, @QueryParam("orderCreateTime") Date orderCreateTime, @QueryParam("toOrderCreateTime") Date toOrderCreateTime, @QueryParam("code") String code,
            @QueryParam("refCode") String refSlipCode, @QueryParam("isNeedInvoice") Integer isNeedInvoice, @QueryParam("lpCode") List<String> lpCode, @QueryParam("specialType") Integer specialType,
            @QueryParam("isSpecialPackaging") Integer isSpecialPackaging, @QueryParam("categoryId") Long categoryId, @QueryParam("status") Integer status, @QueryParam("id") Long id, @QueryParam("toLocation") String toLocation,
            BeanPropertyRowMapper<StockTransApplicationCommand> beanPropertyRowMapper);

    /**
     * 查询待执行的库存调整作业单
     * 
     * @param plid
     * @param ouid
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findInvStatusChangeStaBySource(@QueryParam(value = "sourceList") List<String> sourceList, @QueryParam(value = "version") int version, RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 通过skuSn查询操作采购出库的作业单
     * 
     * @param skuSn
     * @return
     */
    @NativeQuery
    List<Long> findStaOutboundPurchaseBySkuSn(@QueryParam(value = "skuSn") String skuSn, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 通过skuSn查询操作反向NP调整入库的作业单
     * 
     * @param skuSn
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<Long> findStaRNPAdjustmentInboundBySkuSn(@QueryParam(value = "skuSn") String skuSn, SingleColumnRowMapper<Long> singleColumnRowMapper);


    @NativeQuery
    List<StockTransApplicationCommand> findSnCardErrorList(@QueryParam(value = "plCode") String plCode, @QueryParam(value = "staCode") String staCode, RowMapper<StockTransApplicationCommand> rowMapper);


    @NativeQuery
    StockTransApplicationCommand findSnCardErrorByStaCode(@QueryParam(value = "ouid") Long ouid, @QueryParam(value = "staCode") String staCode, RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 查询订单 用于创建占用批
     * 
     * @param whId
     * @param ocpSort
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findStasByOcpOrder(int start, int pageSize, Sort[] sorts, @QueryParam(value = "ocpBatchCode") String ocpBatchCode, @QueryParam(value = "mainWhId") Long mainWhId, @QueryParam(value = "autoOcp") Boolean autoOcp,
            RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 根据占用批ID查找订单
     * 
     * @param ocpId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findStasByOcpOrderId(@QueryParam(value = "ocpId") Long ocpId, RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 将订单设置为占用批占用中
     * 
     * @param batchCode
     * @param staBatchLimit
     */
    @NativeUpdate
    Integer updateStaOcpBatchCode(@QueryParam(value = "batchCode") String batchCode, @QueryParam(value = "staBatchLimit") Long staBatchLimit);

    /**
     * 统计此批次订单中有多少仓库
     * 
     * @param batchCode
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<Long> findOuIdByOcpBatchCode(@QueryParam(value = "batchCode") String batchCode, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 查看是否有需要创建批次的订单
     * 
     * @param rowMapper
     * @return
     */
    @NativeQuery
    Long findStaIdListByOcpOrder(SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 查询1W单
     * 
     * @param mainWhId
     * @param autoOcp
     * @param ocpStaLimit
     */
    @NativeQuery
    List<Long> ocpStaByOcpCode(@QueryParam(value = "mainWhId") Long mainWhId, @QueryParam(value = "autoOcp") Boolean autoOcp, @QueryParam(value = "ocpStaLimit") Integer ocpStaLimit, @QueryParam(value = "ocpErrorQty") Integer ocpErrorQty,
            @QueryParam(value = "saleModle") String saleModle, @QueryParam(value = "isYs") String isYs, @QueryParam(value = "areaCode") String areaCode, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 根据占用批编码处理异常占用批
     * 
     * @param ocpCode
     */
    @NativeUpdate
    void updateStaByOcpCode(@QueryParam(value = "ocpCode") String ocpCode, @QueryParam(value = "staId") Long staId);

    /**
     * 根据占用批编码和失败次数修改作业单状态
     * 
     * @param ocpCode
     */
    @NativeUpdate
    void updateStaStatusByOcpCode(@QueryParam(value = "ocpCode") String ocpCode);

    /**
     * 根据占用批编码和失败次数修改作业单状态
     * 
     * @param ocpCode
     */
    @NativeUpdate
    void updateStaStatusByStaId(@QueryParam(value = "staId") Long staId);

    /**
     * 根据占用批编码设置ocpid
     * 
     * @param ocpCode
     * @param ocpId
     * @return
     */
    @NativeUpdate
    Integer updateStaOcpIdByOcpCode(@QueryParam(value = "ocpCode") String ocpCode, @QueryParam(value = "ocpId") Long ocpId);

    @NativeUpdate
    void updateStaIsNeedOcpByOcpId(@QueryParam(value = "isNeed") Long isNeed, @QueryParam(value = "ocpId") Long ocpId);

    @NativeUpdate
    void updateStaIsNeedOcpByOcpCode(@QueryParam(value = "isNeed") Long isNeed, @QueryParam(value = "ocpCode") String ocpCode);


    @NativeUpdate
    Integer updateStaOcpCodeById(@QueryParam(value = "ocpCode") String ocpCode, @QueryParam(value = "idList") List<Long> idList);

    /**
     * 根据订单ID删除訂單占用批ID
     * 
     * @param staId
     * @param createErrorNumber
     * @return
     */
    @NativeUpdate
    Integer updateStaOcpIdByStaId(@QueryParam(value = "staId") Long staId, @QueryParam(value = "createErrorNumber") Integer createErrorNumber);


    @NativeQuery
    List<StockTransApplicationCommand> getIsBkCheckStaByStaId(@QueryParam(value = "staidlist") List<Long> staidlist, RowMapper<StockTransApplicationCommand> rowMapper);


    @NativeQuery
    List<StockTransApplicationCommand> findWltjSta(@QueryParam("msg") String msg, RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 查询待升订单
     * 
     * @param start
     * @param pageSize
     * @param transTimeType
     * @param id
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findStaLsingle(int start, int pageSize, @QueryParam("transTimeType") Integer transTimeType, @QueryParam("id") Long id, @QueryParam("fromDate") Date fromDate, @QueryParam("toDate") Date toDate,
            @QueryParam("code") String code, @QueryParam("slipCode") String slipCode, @QueryParam("slipCode1") String slipCode1, @QueryParam("slipCode2") String slipCode2, Sort[] sorts,
            BeanPropertyRowMapper<StockTransApplicationCommand> beanPropertyRowMapper);

    /**
     * 查询尚未完成升单单据发送邮件
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> queryUpgrade(SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 根据占用批ID增加订单占用错误次数
     * 
     * @param ocpId
     * @return
     */
    @NativeUpdate
    Integer updateStaOcpErrorQty(@QueryParam(value = "ocpId") Long ocpId);

    @NativeQuery
    Map<Long, ImportEntryListObj> printImportEntryList(@QueryParam(value = "id") Long id, ImportEntryListRowMapper ie);

    @NativeQuery
    Map<Long, ImportEntryListObj> printImportMacaoEntryList(@QueryParam(value = "id") Long id, ImportMacaoEntryListRowMapper ie);

    @NativeQuery
    StockTransApplicationCommand findPackageCount(@QueryParam(value = "id") Long id, RowMapper<StockTransApplicationCommand> row);

    /**
     * 校验退换货作业单是否存在
     * 
     * @return
     */
    @NativeQuery
    StockTransApplication findStaByTypeAndPro(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "type") Long type, @QueryParam(value = "code") String code, @QueryParam(value = "slipCode") String slipCode,
            @QueryParam(value = "slipCode1") String slipCode1, @QueryParam(value = "slipCode2") String slipCode2, RowMapper<StockTransApplication> row);

    /**
     * 
     * @param ouId2
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findNeedOcpOrderByOuId(@QueryParam("ouId") Long ouId2, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    Long findSurplusPlanByStaId(@QueryParam("staId") Long staId, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    List<StockTransApplication> findCartonStaByStaIdAndStatus(@QueryParam("staId") Long staId, @QueryParam("status") String status, @QueryParam("ouId") Long ouId, RowMapper<StockTransApplication> rowMapper);

    /**
     * 获取按箱收货作业单
     * 
     * @param staId
     * @param isNotFinish
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findCartonStaListByStaIdSql(@QueryParam(value = "staId") Long staId, RowMapper<StockTransApplicationCommand> rowMapper);

    @NativeUpdate
    void updateStaStatusByStaIdAndStatus(@QueryParam(value = "staId") Long staId, @QueryParam(value = "status") Integer status);



    @NativeUpdate
    void updateByStaWhPickZoonId(@QueryParam(value = "staCode") String staCode);

    @NativeQuery
    List<Long> findPickZoonIdByStaCode(@QueryParam(value = "staCode") String staCode, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    List<Long> findWhZoonIdByPickZoon(@QueryParam(value = "zoonIdList") List<Long> pickZoonId, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 查询采购退货入库
     * 
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findStaProcurementReturnInbound(int start, int pageSize, @QueryParam(value = "isStvId") Boolean isStvId, @QueryParam(value = "mainWarehouse") Long whId, Sort[] sorts,
            @QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "staCode") String code, @QueryParam(value = "slipCode2") String slipCode2, @QueryParam(value = "refSlipCode") String refcode,
            @QueryParam(value = "owner") String owner, RowMapper<StockTransApplicationCommand> rowMapper);


    List<StockTransApplication> getMergeStaById(@QueryParam("id") Long id);

    /**
     * 获取未解锁的单据
     * 
     * @return
     */
    @NamedQuery
    List<StockTransApplication> getdeblocking();

    /**
     * 查询订单 用于创建占用批
     * 
     * @param whId
     * @param ocpSort
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findImperfectListByType(int start, int pageSize, Sort[] sorts, @QueryParam(value = "mainWhId") Long mainWhId, RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 行特殊处理
     * 
     * @param staId
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<GiftLineCommand> selectSpecialLog(int start, int pageSize, Sort[] sorts, @QueryParam(value = "staId") Long staId, RowMapper<GiftLineCommand> rowMapper);



    @NamedQuery
    StockTransApplication findGroupIdByStuId(@QueryParam("staId") Long staId, BeanPropertyRowMapperExt<StockTransApplication> beanPropertyRowMapperExt);

    @NamedQuery
    StockTransApplication findStaNeedBind(@QueryParam("slipCode") String slipCode, @QueryParam("ouId") Long ouId, @QueryParam("pId") Long pId);

    /**
     * 根据配货清单id 和小批次查询所有作业单
     * 
     * @param id
     * @param string
     * @return
     */
    @NamedQuery
    List<StockTransApplication> getStaListByPickingListAndBatchIndex(@QueryParam("pId") Long id, @QueryParam("idx1") String string);

    /**
     * 根据配货清单id 和ruleCode
     * 
     * @param id
     * @param ruleCode
     * @return
     */
    @NamedQuery
    StockTransApplication getStaListByPickingListAndRuleCode(@QueryParam("pId") Long id, @QueryParam("ruleCode") String ruleCode);


    @NamedQuery
    List<StockTransApplication> getStaListByPickingBatch(@QueryParam("pbId") Long id);

    @NamedQuery
    StockTransApplication getByContainCode(@QueryParam("code") String code, @QueryParam("ouid") Long ouId);

    @NativeQuery
    StockTransApplication queryStaByCode(@QueryParam("code") String code, BeanPropertyRowMapperExt<StockTransApplication> beanPropertyRowMapperExt);

    /**
     * 根据作业单staCode查询出下线包裹快递单号的相关信息
     * 
     * @param whId
     * @param pickingListId
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery
    StockTransApplicationCommand findStaByOffLine(@QueryParam("code") String code, @QueryParam("whId") long whId, RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 保存快递订单en
     * 
     * @param whId
     * @param pickingListId
     * @param sorts
     * @param rowMapper
     * @return
     */
    // @NativeQuery
    // void saveTransOrder(TransOrderCommand transOrder);

    /**
     * 将作业单跟新关联快递单的TO_ID
     * 
     * @param staId
     */
    // @NativeUpdate
    // void updateStaToId(@QueryParam("code") String code, @QueryParam("toId") Long toId);
    @NamedQuery
    List<StockTransApplication> findByTransOrder(@QueryParam("transOrderId") Long transOrderId);

    @NamedQuery
    StockTransApplication findStaCodeBySlipCode(@QueryParam("slipCode") String slipCode);

    @NativeQuery
    List<StockTransApplicationCommand> findSalesOrderByTrackingNo(@QueryParam("trackingNo") String trackingNo, BeanPropertyRowMapper<StockTransApplicationCommand> beanPropertyRowMapper);

    @NativeQuery
    List<Long> getCancelStaByLpcode(@QueryParam("lpcode") String lpcode, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * @param orderCode
     * @param beanPropertyRowMapper
     * @param ouId
     * @return
     */
    @NativeQuery
    StockTransApplication getCheckOrderByCode(@QueryParam("orderCode") String orderCode, @QueryParam("ouId") Long ouId, BeanPropertyRowMapper<StockTransApplication> beanPropertyRowMapper);

    /**
     * 根据小批次查询是否存在未核对的单子
     * 
     * @param id
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Integer getUnCheckStaByWhPB(@QueryParam("id") Long id, SingleColumnRowMapper<Integer> singleColumnRowMapper);

    /**
     * 根据id更新作业单状态
     * 
     * @param staId
     */
    @NativeUpdate
    void updateStaStatusByid(@QueryParam("staId") Long staId, @QueryParam("status") int status);

    /**
     * 根据staId查找一个完整的作业单信息
     * 
     * @param staId
     * @return
     */
    @NativeQuery
    StockTransApplicationCommand findStaByid1(@QueryParam("staId") Long staId, BeanPropertyRowMapperExt<StockTransApplicationCommand> beanPropertyRowMapper);


    @NativeQuery
    StockTransApplication findStaByid2(@QueryParam("staId") Long staId, BeanPropertyRowMapperExt<StockTransApplication> beanPropertyRowMapper);

    @NativeQuery
    List<Long> findStaByOuIdAndStatusForRFD(@QueryParam("ouId") Long whId, @QueryParam("lpList") List<String> lpList, SingleColumnRowMapper<Long> singleColumnRowMapper);


    /**
     * 获取此配合批下还有多少个配货中的作业单
     * 
     * @param pickId
     * @return
     */
    @NamedQuery
    List<StockTransApplication> getOccupiedStaByPickId(@QueryParam("pickId") Long pickId);

    @NativeQuery
    StockTransApplication findStaByOrderCode(@QueryParam(value = "orderCode") String orderCode, @QueryParam(value = "type") List<Integer> type, RowMapper<StockTransApplication> beanPropertyRowMapper);


    @NativeQuery
    StockTransApplication findSta1ByOrderCode(@QueryParam(value = "orderCode") String orderCode, @QueryParam(value = "type") List<Integer> type, RowMapper<StockTransApplication> beanPropertyRowMapper);

    /**
     * test，查询所有占用作业单ID
     * 
     * @return
     */
    @NativeQuery
    List<BigDecimal> findOcpStaIds(SingleColumnRowMapper<BigDecimal> sg);

    @NativeQuery
    StockTransApplicationCommand findOmsTamllStaCodeBySlipCode(@QueryParam("slipCode") String slipCode, BeanPropertyRowMapperExt<StockTransApplicationCommand> beanPropertyRowMapper);

    /**
     * 方法说明：根据配货清单查询作业申请单状态
     * 
     * @author LuYingMing
     * @date 2016年7月12日 下午12:47:10
     * @param ouId
     * @param pickingListId
     * @param plist
     * @param sorts
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Integer> findStaStatusByPickingList(@QueryParam(value = "mainWarehouse") Long ouId, @QueryParam(value = "pickingListId") Long pickingListId, @QueryParam(value = "wids") List<Long> plist, SingleColumnRowMapper<Integer> singleColumnRowMapper);

    @NativeUpdate
    void updateStaSkuQty(@QueryParam("staId") Long staId, @QueryParam("skuQty") Long skuQty);

    @NativeQuery
    StockTransApplicationCommand findStaTypeBycode(@QueryParam("stacode") String stacode, BeanPropertyRowMapperExt<StockTransApplicationCommand> beanPropertyRowMapper);

    @NativeQuery
    StockTransApplication findStaByPo(@QueryParam("slipCode") String slipCode, BeanPropertyRowMapperExt<StockTransApplication> beanPropertyRowMapper);

    /**
     * 
     * @param staId,订单解锁同时记录解锁人账号
     */
    @NativeUpdate
    void updateStaUnlocked1(@QueryParam("staId") Long staId, @QueryParam("unlockUser") String unlockUser);

    @NativeQuery
    StockTransApplication findStaBySlipCodeOrCode(@QueryParam("slipCode") String slipCode, @QueryParam("ouId") Long ouId, BeanPropertyRowMapperExt<StockTransApplication> beanPropertyRowMapper);


    @NativeQuery
    StockTransApplication findStaByCode1(@QueryParam("code") String slipCode, BeanPropertyRowMapperExt<StockTransApplication> beanPropertyRowMapper);

    @NativeQuery
    List<StockTransApplicationCommand> getNoTransSta(@QueryParam("rn") Long count, BeanPropertyRowMapper<StockTransApplicationCommand> beanPropertyRowMapper);

    /**
     * 查询饭
     * 
     * @param start
     * @param pageSize
     * @param beginTime
     * @param endTime
     * @param intStatus
     * @param owner
     * @param slipCode
     * @param beanPropertyRowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> getAllOccupiedFailureOrder(int start, int pageSize, @QueryParam("ouId") Long ouId, @QueryParam("beginTime") Date beginTime, @QueryParam("endTime") Date endTime, @QueryParam("intStatus") Integer intStatus,
            @QueryParam("owner") String owner, @QueryParam("slipCode") String slipCode, Sort[] sorts, BeanPropertyRowMapper<StockTransApplicationCommand> beanPropertyRowMapper);

    /**
     * @param staCode
     */
    @NativeUpdate
    void releaseInventory(@QueryParam("staCode") String staCode);

    /**
     * @param id
     */
    @NativeUpdate
    void updateStaToPartySend(@QueryParam("staId") Long id);

    /**
     * 方法说明：查询关于菜鸟仓SN号的订单集
     * 
     * @author LuYingMing
     * @date 2016年9月19日 下午7:11:09
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<StockTransApplication> findStaByStatusAndType(@QueryParam("ouId") Long ouId, BeanPropertyRowMapperExt<StockTransApplication> beanPropertyRowMapperExt);

    /**
     * 子订单从主订单中剔除
     */
    @NativeUpdate
    void updateGroupStaById(@QueryParam("staId") Long id);

    @NativeQuery
    StockTransApplication findByQtyByStaId(@QueryParam("staId") Long id, BeanPropertyRowMapperExt<StockTransApplication> beanPropertyRowMapper);

    @NativeQuery
    String queryPickingUser(SingleColumnRowMapper<String> s);

    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findpdaQueryLists(int start, int size, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "createTime") Date createTime, @QueryParam(value = "endCreateTime") Date endCreateTime,
            @QueryParam(value = "finishTime") Date finishTime, @QueryParam(value = "endFinishTime") Date endFinishTime, @QueryParam(value = "code") String code, @QueryParam(value = "slipCode") String slipCode,
            @QueryParam(value = "slipCode1") String slipCode1, @QueryParam(value = "owner") String owner, @QueryParam(value = "status") Integer status, @QueryParam(value = "intType") Integer intType, Sort[] sorts,
            RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 查询单天是否存在重复订单
     * 
     * @param s
     * @return
     */
    @NativeQuery
    List<String> checkStaRepetitive(SingleColumnRowMapper<String> s);

    /**
     * @param slipCode
     */
    @NativeUpdate
    void updateResetToCreateFlag(@QueryParam("slipCode") String slipCode);

    @NativeQuery
    List<StockTransApplicationCommand> findRuleCodeByPickingId(@QueryParam("pickinglistId") Long pickinglistId, RowMapper<StockTransApplicationCommand> rowMapper);

    @NativeQuery
    List<StockTransApplicationCommand> findByplIdAndStaId(@QueryParam(value = "plId") Long plId, @QueryParam(value = "staId") Long staId, RowMapper<StockTransApplicationCommand> rowMapper);

    /**
     * 品牌未完成的入库指令
     */
    @NamedQuery(pagable = true)
    Pagination<StockTransApplication> findAllUnFinishedBrandIns(int start, int size, @QueryParam(value = "owner") String owner, Sort[] sorts);


    @NativeQuery
    List<Long> findNotGiftSkuByStaId(@QueryParam("staId") Long id, SingleColumnRowMapper<Long> s);

    @NativeQuery
    List<StockTransApplicationCommand> findStarbucksCustom(RowMapper<StockTransApplicationCommand> rowMapper);

    @NativeUpdate
    void updateStaOcpResult(@QueryParam("staId") Long staId, @QueryParam("areaOcpStatus") Integer areaOcpStatus, @QueryParam("areaOcpMemo") String areaOcpMemo);

    @NativeUpdate
    void updateStaOcpResultSec(@QueryParam("staId") Long staId, @QueryParam("areaOcpStatus") Integer areaOcpStatus, @QueryParam("areaOcpMemo") String areaOcpMemo);

    @NativeQuery
    List<Long> findNoTransStaByMq(@QueryParam("orderCount") Long orderCount, SingleColumnRowMapper<Long> s);

    @NativeQuery
    Long findStaStatusByPickingListId(@QueryParam("piId") Long pId, SingleColumnRowMapper<Long> s);

    @NativeQuery
    Long findMacaoHGDByPickinglistId(@QueryParam("piId") Long pId, SingleColumnRowMapper<Long> s);

    @NativeQuery
    Long findStaByPickingListId(@QueryParam("piId") Long pId, @QueryParam("ouId") Long ouId, SingleColumnRowMapper<Long> s);

    /**
     * 根据批次号查询取消单据列表
     * 
     * @return
     */
    @NativeQuery
    List<StockTransApplicationCommand> findCancelStaByPickingCode(@QueryParam("pickingListCode") String pickingListCode, @QueryParam("ouId") Long ouid, RowMapper<StockTransApplicationCommand> rowMapper);

    @NativeQuery
    Long getPickingListIdByCode(@QueryParam("code") String code, SingleColumnRowMapper<Long> s);

    @NativeQuery
    Map<Long, ImportHGDEntryListObj> printImportMacaoHGDEntryList(@QueryParam(value = "id") Long staid, ImportMacaoHGDEntryListRowMapper importMacaoHGDEntryListRowMapper);

    @NativeQuery
    List<StockTransApplication> getListWmsZhanYongLastTime(@QueryParam("num") BigDecimal num, BeanPropertyRowMapper<StockTransApplication> beanPropertyRowMapper);

    @NativeQuery
    Long findAdCheckSta(@QueryParam("staId") Long staId, SingleColumnRowMapper<Long> s);

    @NativeUpdate
    void saveAdCheck(@QueryParam("staId") Long staId, @QueryParam("msg") String msg, @QueryParam("isBeforePrint") Integer isBeforePrint);

    @NativeQuery
    Long findStaLinePrintBeforeCancel(@QueryParam("staId") Long staId, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    Long findStaLineCancel(@QueryParam("staId") Long staId, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findlockedStaNoSku(int start, int pageSize, Sort[] sorts, @QueryParam("slipCode3") String slipCode3, @QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime,
            @QueryParam(value = "code") String code, @QueryParam(value = "refSlipCode") String refSlipCode, @QueryParam(value = "owner") String owner, @QueryParam(value = "lpcode") String lpcode, @QueryParam(value = "trackingNo") String trackingNo,
            @QueryParam(value = "mainWarehouse") Long whId, @QueryParam(value = "receiver") String receiver, @QueryParam(value = "receiverPhone") String receiverPhone, @QueryParam(value = "orderCode") String orderCode,
            @QueryParam(value = "taobaoOrderCode") String taobaoOrderCode, RowMapper<StockTransApplicationCommand> rowMapper);


    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findOutboundPickingTaskStaListPage(int start, int pageSize, @QueryParam("startCreateTime") Date startCreateTime, @QueryParam("endCreateTime") Date endCreateTime, @QueryParam("owner") String owner,
            @QueryParam("intStatype") Integer intStaType, @QueryParam("intStatus") Integer intStatus, @QueryParam("code") String code, @QueryParam("refSlipCode") String refSlipCode, @QueryParam("ouid") Long ouid, Sort[] sorts,
            BeanPropertyRowMapperExt<StockTransApplicationCommand> beanPropertyRowMapperExt);


    @NativeQuery
    List<StockTransApplication> findSlipCodeBySkuOuIdOwner(@QueryParam("skuId") Long skuId, @QueryParam("ouId") Long ouId, @QueryParam("owner") String owner, @QueryParam("type") int type, RowMapper<StockTransApplication> rowMapper);


    @NativeQuery
    StockTransApplication findByStaCodeByArc(@QueryParam("staCode") String staCode, BeanPropertyRowMapperExt<StockTransApplication> beanPropertyRowMapper);

    @NativeQuery(model = StaInfoCommand.class)
    StaInfoCommand findStaInfoByCode(@QueryParam("staCode") String staCode, RowMapper<StaInfoCommand> rowMapper);

    @NativeQuery
    OutBoundSendInfo findReturnOutWhPickingInfoByStaId(@QueryParam("staId") Long staid, BeanPropertyRowMapperExt<OutBoundSendInfo> beanPropertyRowMapperExt);

    /**
     * @param slipCode
     * @return
     */
    @NamedQuery
    List<StockTransApplication> findBySlipCodeOuIdType(@QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "ouId") Long ouId, @QueryParam("type") StockTransApplicationType type);


    @NativeQuery
    List<StockTransApplication> findIsFinishedByStaId(@QueryParam(value = "sta_id") Long staId, BeanPropertyRowMapperExt<StockTransApplication> s);

    @NativeQuery
    StockTransApplicationCommand findStarbucksDetail(@QueryParam(value = "code") String code, BeanPropertyRowMapperExt<StockTransApplicationCommand> beanPropertyRowMapperExt);


    @NativeQuery
    List<StockTransApplication> findIsNotFinishedByStaId(@QueryParam(value = "sta_id") Long staId, BeanPropertyRowMapperExt<StockTransApplication> s);

    /**
     * zdh还原单据状态
     * 
     * @param staId
     */
    @NativeUpdate
    void reStoreStaStatus(@QueryParam("owner") String owner, @QueryParam("ouId") Long ouId);
    
    /**
     * 查询PO按箱收货列表
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findPoStaList(int start, int size,@QueryParam("owner") String owner,@QueryParam("code") String code,@QueryParam("slipCode") String slipCode,@QueryParam("slipCode1") String slipCode1,@QueryParam("ouId") Long ouId,RowMapper<StockTransApplicationCommand> rowMapper,Sort[] sorts);

    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findThreeDimensional(int start, int pageSize, @QueryParam("staId") Set<Long> staId, @QueryParam("staCode") String staCode, @QueryParam("slipCode") String slipCode,
            @QueryParam("slipCode1") String slipCode1, @QueryParam("staType") Integer staType, @QueryParam("owner") String owner, @QueryParam("staStatus") Integer staStatus, BeanPropertyRowMapperExt<StockTransApplicationCommand> beanPropertyRowMapperExt);

    //压测S
    
    @NativeQuery
    StockTransApplicationCommand findStaByStaIdByTest(@QueryParam(value = "staId") Long staId, BeanPropertyRowMapperExt<StockTransApplicationCommand> s); 
    @NativeQuery
    List<StaLineCommand> findStaLineByStaIdByTest(@QueryParam(value = "staId") Long staId, BeanPropertyRowMapperExt<StaLineCommand> s);
    @NativeQuery
    PickingListCommand findPickByPidByTest(@QueryParam(value = "pId") Long pId, BeanPropertyRowMapperExt<PickingListCommand> s); 
    @NativeQuery
    List<StockTransApplicationCommand> findStaByPidByTest(@QueryParam(value = "pId") Long pId, BeanPropertyRowMapperExt<StockTransApplicationCommand> s);
    
    //压测E
    
    /***
     * 查询所有未完成的纸箱维护入库作业单
     * 
     * @param start
     * @param size
     * @param ouId
     * @param map
     * @param rowMapper
     * @param sorts
     * @return
     * @author weiwei.wu@baozun.com
     * @version 2018年8月16日 下午6:01:39
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StockTransApplicationCommand> findInBoundCartonSta(int start, int size, @QueryParam(value = "ouId") Long ouId, @QueryParam Map<String, Object> map, RowMapper<StockTransApplicationCommand> rowMapper, Sort[] sorts);

}
