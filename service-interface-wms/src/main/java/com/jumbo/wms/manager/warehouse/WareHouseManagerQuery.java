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
package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuBarcode;
import com.jumbo.wms.model.baseinfo.TransSfInfo;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.GiftLineCommand;
import com.jumbo.wms.model.command.InventoryCheckMoveLineCommand;
import com.jumbo.wms.model.command.SkuBarcodeCommand;
import com.jumbo.wms.model.command.StaSpecialExecutedCommand;
import com.jumbo.wms.model.pda.PdaOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.DistributionRuleCommand;
import com.jumbo.wms.model.warehouse.GiftLine;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.InventoryCheckCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingMode;
import com.jumbo.wms.model.warehouse.ReturnPackageCommand;
import com.jumbo.wms.model.warehouse.SkuSnCommand;
import com.jumbo.wms.model.warehouse.SkuSnLogCommand;
import com.jumbo.wms.model.warehouse.SkuSnStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.WarehouseLocationCommand;

public interface WareHouseManagerQuery extends BaseManager {

    String STA_TEMPLATE_FOR_PURCHASE = "excel/tpl_sta_GI.xls";



    /**
     * 根据仓库组织ID获取可销售库存状态
     * 
     * @param whouid
     * @return
     */
    InventoryStatus getSalesInvStatusByWhou(Long whouid);


    /**
     * 
     */
    StaLine findBySkuSta(Long staId, Long skuId);

    /**
     * 根据类型查询组织节点数据
     * 
     * @param type
     * @return
     */
    List<OperationUnit> fandOperationUnitsByType(String type);


    List<StaLineCommand> findStaLineByPickingId2(Long pickinglistId, Long ouid, Sort[] sorts);


    public Warehouse queryTotalPickingList(Long ouId);

    /**
     * 二次分拣明细查询
     * 
     * @return
     * @throws JSONException
     */
    public Map<String, Object> findStaLineBySuggestion(Long pickinglistId, String pickingCode);

    /**
     * 分拣核对，查询订单核对明细信息：考虑合单情况 fanht
     * 
     * @param staId
     * @return
     */
    public List<StaLineCommand> findGiftStaLineListByStaId(Long staId);

    /**
     * 导入到收货暂存区
     * 
     * @param staId
     * @param stv
     * @param staFile
     * @param creator
     * @param mode
     * @return
     * @throws Exception
     */
    ReadStatus staImportForGI(Long staId, StockTransVoucher stv, File staFile, User creator, InboundStoreMode mode) throws Exception;

    /**
     * 根据仓库查询暂存区上存在商品的库位信息
     * 
     * @param start
     * @param pageSize
     * @param ouid
     * @param loc
     * @param sorts
     * @return
     */
    Pagination<WarehouseLocationCommand> findGILocation(int start, int pageSize, Long ouid, WarehouseLocationCommand loc, Sort[] sorts);

    /**
     * 根据库位查寻出库位的商品的库存信息
     * 
     * @param locId
     * @return
     */
    List<InventoryCommand> queryGILocSku(Long locId);

    /**
     * 根据商品ID查询SN
     * 
     * @param skuId
     * @param sn
     * @param status
     * @param ouid
     * @return
     */
    SkuSnCommand findSnBySkuId(Long skuId, String sn, SkuSnStatus status, Long ouid);

    /**
     * 查寻所有未完成的入库作业单
     * 
     * @param start
     * @param pageSize
     * @param ouId
     * @param commd
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> findInboundStas(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, StockTransApplicationCommand commd, Sort[] sorts);

    /**
     * 查寻所有未完成的入库作业单
     * 
     * @param start
     * @param pageSize
     * @param ouId
     * @param commd
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> findInboundSta(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, StockTransApplicationCommand commd, Sort[] sorts);

    /**
     * 查寻所有未完成的入库作业单
     * 
     * @param start
     * @param pageSize
     * @param ouId
     * @param commd
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> findInboundStaVmi(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, StockTransApplicationCommand commd, Sort[] sorts);


    Pagination<StockTransApplicationCommand> findInboundStaFinish(int start, int pageSize, Long ouId, StockTransApplicationCommand commd, Sort[] sorts);

    /**
     * 查寻所有入库作业单
     * 
     * @param start
     * @param pageSize
     * @param ouId
     * @param commd
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> findInboundStaQuery(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, Date finishTime, Date endfinishTime, StockTransApplicationCommand commd, Sort[] sorts);


    /**
     * 查寻所有确认后须要核对的作业单
     * 
     * @param start
     * @param pageSize
     * @param ouId
     * @param commd
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> findInboundConfirmSta(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, StockTransApplicationCommand commd, Sort[] sorts);


    /**
     * 查寻所有确认后须要核对的作业单
     * 
     * @param start
     * @param pageSize
     * @param ouId
     * @param commd
     * @param sorts
     * @return
     */
    Pagination<StvLineCommand> findInboundStvLine(int start, int pageSize, Long stvId, Sort[] sorts);

    /**
     * 入库上架(手动)
     * 
     * @param start
     * @param pageSize
     * @param ouId
     * @param commd
     * @param sorts
     * @return
     */
    List<StvLineCommand> findInboundStvLineHand(Long stvId);

    /**
     * 查寻合并发货的单据
     * 
     * @param start
     * @param pageSize
     * @param ouId
     * @param commd
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> findMergeInboundStvLine(int start, int pageSize, Long ouId, List<Long> stvList, Sort[] sorts);



    /* *//**
     * 商品基础信息导出
     * 
     * @param outputStream
     * @param sta
     * @return
     */
    /*
     * WriteStatus exportSKUinfo(OutputStream outputStream, StockTransApplicationCommand sta);
     */

    /**
     * 查询所有已经核对的单据
     * 
     * @param start
     * @param pageSize
     * @param ouId
     * @param commd
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> findInboundShelvesSta(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, StockTransApplicationCommand commd, Sort[] sorts);


    /**
     * 查询所有已经核对的单据(PDA上架审核)
     * 
     * @param start
     * @param pageSize
     * @param ouId
     * @param commd
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> findPdaShelvesSta(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, StockTransApplicationCommand commd, Sort[] sorts);

    /**
     * PDA上架审核明细
     * 
     * @param start
     * @param pageSize
     * @param ouId
     * @param commd
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> checkPdaShelvesStaLine(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, StockTransApplicationCommand commd, Sort[] sorts);

    /**
     * 审核上架，更新sta状态，删除缓存
     */
    Map<String, Object> checkShelves(Long staId, OperationUnit op, User user);


    /**
     * 查询所有已核对并且存在PDA上架记录的数据
     * 
     * @param start
     * @param pageSize
     * @param ouId
     * @param createTime
     * @param endCreateTime
     * @param commd
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> findPdaInboundShelves(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, StockTransApplicationCommand commd);

    /**
     * 查找入库明细
     * 
     * @param start
     * @param pageSize
     * @param staId
     * @param commd
     * @param sorts
     * @return
     */
    Pagination<StaLineCommand> findInboundLine(int start, int pageSize, Long staId, Sort[] sorts);

    /**
     * PDA上架明细
     * 
     * @param start
     * @param pageSize
     * @param staId
     * @param isFindDif 是否查询差异
     * @param sorts
     * @return
     */
    Pagination<StvLineCommand> findPdaInboundLine(int start, int pageSize, StvLineCommand comd, Boolean isFindDif, Sort[] sorts);


    /**
     * 查询PDA上架
     * 
     * @param stvId
     * @return
     */
    List<PdaOrder> findPdaUserName(String staCode);

    /**
     * 查询配货清单是否支持COD
     * 
     * @param plid
     * @return
     */
    boolean isCodPickingList(Long plid);

    /**
     * 查询作业单中特殊处理的信息
     * 
     * @param plid
     * @return
     */
    List<GiftLineCommand> findGiftBySta(Long staId, Integer giftType);

    /**
     * 查询作业单中特殊处理的信息行
     * 
     * @param plid
     * @return
     */
    GiftLineCommand findGiftByStaLine(Long staLineId, Integer giftType);


    /**
     * 查找入库SN
     * 
     * @param start
     * @param pageSize
     * @param staId
     * @param commd
     * @param sorts
     * @return
     */
    Pagination<SkuSnLogCommand> findInBoundSN(int start, int pageSize, Long staId, Sort[] sorts);

    /**
     * 查询作业单中特殊处理信息
     * 
     * @param staId
     * @return
     */
    List<StaSpecialExecutedCommand> queryStaSpecialExecute(Long staId);

    /**
     * coach 订单查询
     * 
     * @param ouId
     * @param sta
     * @return
     */
    Pagination<StockTransApplicationCommand> queryCoachSta(int start, int pageSize, Long ouId, StockTransApplicationCommand sta);


    /**
     * 查询包裹
     * 
     * @param start
     * @param pageSize
     * @param ouId
     * @param comm
     * @return
     */
    Pagination<ReturnPackageCommand> findReturnPackage(int start, int pageSize, Long ouId, ReturnPackageCommand comm, Sort[] sorts);

    /**
     * 根据批次查询
     * 
     * @param batchCode
     * @return
     */
    List<ReturnPackageCommand> findReturnPackageList(String batchCode);

    String checkSalesSum();

    GiftLine getGiftLineByStaLineIdAndType(Long staLineId, Integer type);

    TransSfInfo findTransSfInfoDefault();

    List<GiftLineCommand> getGiftLineByPackingIdAndStaId(Long plid, Long staid);

    List<StaLineCommand> findGiftMemoByPkId(Long pkId);

    List<StaLineCommand> findStaLineByGiftMemo(String slipCode, Long staLineId);

    List<SkuBarcodeCommand> findAddSkuBarcodeByMainBarcode(List<String> barcodes, Long whouid);

    Long getCustomerByWarehouse(Warehouse wh);

    Long getCustomerByWhouid(Long whouid);

    /**
     * 获取SKU
     * 
     * @param barcode
     * @param jmcode
     * @param keyProperties
     * @return
     */
    Sku findSkuByParameter(String barcode, String jmcode, String keyProperties, Long whouId);

    /**
     * 根据商品ID查询扩展条形码
     * 
     * @param mainBarcode
     * @return
     */
    List<SkuBarcode> findSkuBarcodeBySkuId(String mainBarcode, Long whouid);

    /**
     * 查询配货清单配货中的STA LINE
     * 
     * @param plid
     * @return
     */
    List<StaLineCommand> findOccpiedStaLineByPlId(Long plid, Sort[] sorts);

    /**
     * 查询配货清单
     * 
     * @param pickingMode
     * @param ouId
     * @param sorts
     * @return
     */
    List<PickingList> findAfDeLiveryList(PickingMode pickingMode, Long ouId, Sort[] sorts);

    /**
     * 配货规则维护
     */
    Pagination<DistributionRuleCommand> findAllDistributionRule(int start, int pageSize, Long ouid, String ruleName, Sort[] sorts);

    /**
     * 根据仓库查询所有库存
     * 
     * @param start
     * @param pageSize
     * @param whOuId
     * @param cmpOuId
     * @return
     */
    Pagination<InventoryCommand> findAllInventory(int start, int pageSize, Long whOuId, Sort[] sorts);

    /**
     * 二次分拣意见，查询配货详情 fanht
     * 
     * @param pickinglistId
     * @param ouid
     * @param sorts
     * @return
     */
    List<StaLineCommand> findStaLineByPickingId(Long pickinglistId, Long ouid, Sort[] sorts);

    List<MsgRtnOutbound> findAllMsgRtnOutbound(String source);

    Pagination<InventoryCheckCommand> findAllVMIInventoryCheckList(int start, int pageSize, InventoryCheckCommand iccommand, Long ouid, Sort[] sorts);

    /**
     * 根据条件查询盘点批信息
     * 
     * @param start
     * @param pageSize
     * @param iccommand
     * @param id
     * @param sorts
     * @return
     */
    Pagination<InventoryCheckCommand> findAllInventoryCheckList(int start, int pageSize, InventoryCheckCommand iccommand, Long id, Sort[] sorts);

    /**
     * 根据盘点批查询盘点差异
     * 
     * @param invCkId
     * @return
     */
    Pagination<InventoryCheckMoveLineCommand> findInvCheckMoveLineId(int start, int pageSize, Long invCkId, Sort[] sort);


    /**
     * 分页查询当前库存
     * 
     * @param start
     * @param pageSize
     * @param inv
     * @param whOuId
     * @param srots
     * @return
     */
    Pagination<InventoryCommand> findCurrentInventoryByPage(int start, int pageSize, InventoryCommand inv, Long whOuId, Long companyid, Sort[] sorts);

    /**
     * 查询库区库位上的库存
     * 
     * @param skuId
     * @param owner
     * @param whOuId
     * @param sorts
     * @return
     */
    List<InventoryCommand> findInventoryBySku(Long skuId, String owner, Long whOuId, Sort[] sorts);

    /**
     * 库存明细查寻
     * 
     * @param start
     * @param pageSize
     * @param inv
     * @param whOuId
     * @param sorts
     * @return
     */
    Pagination<InventoryCommand> findDetailsInventoryByPage(int start, int pageSize, InventoryCommand inv, Long whOuId, Long companyid, Sort[] sorts);

    /**
     * 根据条件查询库位信息
     * 
     * @param loc
     * @param ouid
     * @param sort
     * @return
     */
    Pagination<WarehouseLocationCommand> findLocationList(int start, int pageSize, WarehouseLocationCommand loc, Long ouid, Sort[] sorts);


    public InventoryCommand findQtyOccupiedInv(String ouId, Long skuId, String owner);

    public InventoryCommand findSalesQtyInv(String ouId, Long skuId, String owner);

    /**
     * 判断是否允许打印澳门件海关单
     */
    boolean checkIsAllowPrintMacaoHGD(Long staId, Long pickinglistId);
    
    

    /***
     * 查询所有未完成的纸箱维护入库作业单
     * @param start
     * @param pageSize
     * @param ouId
     * @param createTime
     * @param endCreateTime
     * @param commd
     * @param sorts
     * @return
     * @author weiwei.wu@baozun.com
     * @version 2018年8月16日 下午5:59:14
     */
    Pagination<StockTransApplicationCommand> findInBoundCartonSta(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, StockTransApplicationCommand commd, Sort[] sorts);


}
