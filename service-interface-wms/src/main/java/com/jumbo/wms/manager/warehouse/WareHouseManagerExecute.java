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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import loxia.support.excel.ReadStatus;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.BetweenLabaryMoveCommand;
import com.jumbo.wms.model.warehouse.HandOverList;
import com.jumbo.wms.model.warehouse.HandOverListCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

public interface WareHouseManagerExecute extends BaseManager {

    /**
     * 商品拆分/组合 执行操作
     * 
     * @param userid
     * @param inviCheckId
     */
    void skuGroupExecution(Long userId, Long inventoryCheckId) throws CloneNotSupportedException;

    /**
     * 更新作业单快递单号
     * 
     * @param staCode
     * @param transNo
     */
    void updateStaTransNoForOccupied(String staCode, String transNo);

    /**
     * 更新仓库
     * 
     * @param staCode
     * @param transNo
     */
    void updateStaOuId(String staCode, String name);

    /**
     * 商品拆分/组合 取消
     */
    void skuGroupCancel(Long invCheckId, User user);

    /**
     * 增加通知OMS记录
     * 
     * @param staId
     */
    void addStaCancelNoticeOmsCount(Long staId);

    /**
     * 退仓执行部分出库
     * 
     * @param staId
     * @param userId
     */
    void partlyOutbound(Long staId, Long userId);

    /**
     * 创建库间移动申请单
     * 
     * @param sta
     * @param user
     * @param ou
     * @return
     * @throws Exception
     */
    void createBetweenMoveSta(BetweenLabaryMoveCommand betwenMoveCmd, User user, OperationUnit ou, List<BetweenLabaryMoveCommand> staLineCmd) throws Exception;

    /**
     * 库间移动移动出库
     * 
     * @param staId
     * @param userId
     */
    void transitCrossStaOutBound(Long staId, Long userId);

    /**
     * 创建交货清单，删除中间表数据 fanht
     * 
     * @param lpcode
     * @param packageIds
     * @param whOuId
     * @param userId return
     */
    HandOverList createHandOverListDelete(String lpcode, List<Long> packageIds, Long whOuId, Long userId, boolean msg);

    /**
     * 创建交货清单
     * 
     * @param lpcode
     * @param packageIds
     * @param whOuId
     * @param userId return
     */
    HandOverList createHandOverList(String lpcode, List<Long> packageIds, Long whOuId, Long userId, boolean msg);

    /**
     * 物流交接单 保存
     * 
     * @param handOverList
     * @param ouid
     * @return
     */
    HandOverListCommand saveHandOverListInfo(HandOverList handOverList, Long userId);

    /**
     * 新建状态交接清单保存
     * 
     * @param handOverList
     * @param userId
     * @return
     */
    HandOverList saveHandOverList(HandOverList handOverList, Long userId);

    /**
     * 验证出库商品是否是暂存区的商品
     * 
     * @param stv
     */
    void valdateOutBoundLocationIsGI(StockTransVoucher stv);

    /**
     * 创建暂存区商品上架入库单
     * 
     * @param locId
     * @param userId
     * @param ouId
     */
    void createGIInboundByLoc(Long locId, User user, OperationUnit ou);

    /**
     * 暂存区上架
     * 
     * @param sta
     */
    void executeGIout(StockTransApplication sta, Long inStvId, Long creatorID) throws CloneNotSupportedException;


    /**
     * 入库数量确认导入
     * 
     * @param staId
     * @param staFile
     * @param creator
     * @return
     * @throws Exception
     */
    ReadStatus importStaInbound(Long staId, File staFile, User creator) throws Exception;

    /**
     * 收货转仓导入
     * 
     * @param staId
     * @param staFile
     * @param creator
     * @return
     * @throws Exception
     */
    ReadStatus importStaInboundTurn(String mainWarehouseName, File staFile, User creator, Long ouId) throws Exception;

    /**
     * 导入SN和子SN对应关系
     * 
     * @param staId
     * @param staFile
     * @param creator
     * @return
     * @throws Exception
     */
    ReadStatus importSeedSn(File staFile) throws Exception;


    /**
     * 入库数量确认
     * 
     * @param staId
     * @param user
     */
    void inBoundAffirm(Long staId, User user);

    /**
     * 无条件确认收货
     * 
     * @param staId
     * @param user
     */
    void noConinBoundAffirm(Long staId, User user);

    /**
     * 手动收货确认
     * 
     * @param staId
     * @param user
     */
    Map<String, Object> inBoundAffirmHand(Long staId, List<StvLineCommand> list, List<StvLineCommand> lists, User user, Long ouId, Boolean flag);


    /**
     * 自动化仓收货设置货箱号
     * 
     * @param staId
     * @return
     */
    public String containerCodeSetting(Long staId);

    void closeInBoundFinish(Long staId, User user);

    /**
     * 取消入库核对
     * 
     * @param stvId
     * @param user
     */
    void cancelInBoundConfirm(Long stvId, User user, Long staId);

    /**
     * 确认核对
     * 
     * @param stvId
     * @param user
     */
    void confirmInBoundSta(Long stvId, String invoiceNumber, Double dutyPercentage, Double miscFeePercentage, User user);


    /**
     * 入库数量确认导入
     * 
     * @param staId
     * @param staFile
     * @param creator
     * @return
     * @throws Exception
     */
    ReadStatus importConfirmDiversity(Long stvId, File staFile, User creator) throws Exception;

    /**
     * 入库数量确认导入
     * 
     * @param staId
     * @param staFile
     * @param creator
     * @return
     * @throws Exception
     */
    ReadStatus importInboundShelves(Long stvId, File staFile, User creator) throws Exception;

    /**
     * 
     * 虚拟仓收货导入
     * 
     * @param staId
     * @param staFile
     * @param creator
     * @return
     * @throws Exception
     */
    ReadStatus importStaInboundShelves(Long staId, File staFile, User creator, Long ouId) throws Exception;

    /**
     * 判断商品基本信息是否维护过
     * 
     * @param sku
     * @return
     */
    boolean isSkuEssential(Sku sku);


    /**
     * 执行上架单
     * 
     * @param stv
     * @param inboundId
     * @param list
     * @param creator
     */
    void executeInBoundShelves(StockTransVoucher shelvesStv, StockTransVoucher inboundStv, User creator, boolean isCheckDate);


    /**
     * 校验入库数量是否与STVLine数量一致
     * 
     * @param errors
     * @param staId
     * @param qtyMap
     */
    void stvQtyIsStaQty(List<BusinessException> errors, Long staId, Map<Long, Long> qtyMap);


    /**
     * 
     * 入库上架到GI (暂存区)
     * 
     * @param stvId
     * @param user
     */
    void inboundShelvesGI(Long stvId, User user) throws CloneNotSupportedException;

    /**
     * 更具批次入库
     * 
     * @param stv 收货STV
     * @param receiveLine 商品收货STV line,待拆分批次
     * @param unInboundBactchCodes 未入库 SKU的批次及数量
     * @param isCheckInvStatus 是否不需要判断库存状态
     * @return List<StvLine>入库待上架STV line
     */
    List<StvLine> createStvLineByDate(StockTransVoucher stv, StvLine receiveLine, List<StvLineCommand> unInboundBatchCodes, boolean isCheckDate);

    /**
     * 创建STVLINE
     * 
     * @param batchCode 批次
     * @param direction 作业方向
     * @param inBoundTime 入库时间
     * @param invStatus 库存状态
     * @param location 库位
     * @param owner 店铺
     * @param quantity 数量
     * @param sku 商品
     * @param skuCost
     * @param sns
     * @param productionDate
     * @param validDate
     * @param expireDate
     * @param staLine
     * @param transactionType
     * @param stv
     * @return
     */

    /**
     * 创建STVLINE
     * 
     * @param batchCode 批次
     * @param direction 作业方向
     * @param inBoundTime 入库时间
     * @param invStatus 库存状态
     * @param location 库位
     * @param owner 店铺
     * @param remainPlanQty 剩余计划量
     * @param receiptQty 收货量
     * @param differenceQty 差异量
     * @param quantity 数量
     * @param addedQty 上架数量
     * @param sku 商品
     * @param skuCost 商品成本
     * @param sns SN号
     * @param productionDate 生成日期
     * @param validDate 保质时间
     * @param expireDate 过期时间
     * @param staLine 对应的StaLine
     * @param stv 作业明细单
     * @return
     */
    StvLine createStvLine(String batchCode, TransactionDirection direction, Date inBoundTime, InventoryStatus invStatus, WarehouseLocation location, String owner, Long remainPlanQty, Long receiptQty, Long differenceQty, Long quantity, Long addedQty,
            Sku sku, BigDecimal skuCost, String sns, Date productionDate, Integer validDate, Date expireDate, StaLine staLine, StockTransVoucher stv);

    /**
     * 合并上架
     * 
     * @param staId
     * @param staFile
     * @param creator
     * @return
     * @throws Exception
     */
    ReadStatus importMergeInboundShelves(String ids, File staFile, User creator) throws Exception;


    /**
     * 无差异明细执行
     * 
     * @param staCode
     * @param creator
     */
    void executeNotDifPDALine(String staCode, User creator);


    /**
     * 调整保存
     * 
     * @param staCode
     * @param creator
     */
    void savePdaAddedQty(StvLineCommand saveInfo, User creator);



    /**
     * 调整保存执行
     * 
     * @param staCode
     * @param creator
     */
    void executePda(String staCode, StvLineCommand pda, User creator);


    /**
     * 库位推荐 KJL
     * 
     * @param stvId
     * @param id
     * 
     */
    void locationRecommend(Long stvId, Long id);

    /**
     * 调用OMS取消结构
     * 
     * @param staId
     * @return
     */
    boolean httpPostOmsOrderCancel(Long staId);

    /**
     * 上架手动
     * 
     * @param staId
     * @param staFile
     * @param creator
     * @return
     * @throws Exception
     */
    void inboundShelvesHand(Long staId, List<StvLineCommand> list, User creator) throws Exception;

    void occpuyForTransitCross(StockTransApplication sta);

    void failedToGetTransno(Long id);

    /*
     * 获取一键交接出库实体list
     */
    Map<String, Object> getOneHandOutPack(Long userId, Long wId, String lpCodes);

    /**
     * 自动化仓交接
     * 
     * @param userId
     * @param lpcode
     * @return
     */
    public Map<String, Object> getAutoOneHandOutPack(Long userId, String lpcode);

    void updateNextGetTransTime(Long staid);

    /**
     * @param transNo
     */
    void checkTrackingNoForHoList(String transNo);

    /**
     * 根据运单号来获取包裹信息判断物流商
     */
    String checkTrackingNo(String transNo, String lpCode);

    public void msgUnLockedError(Long id);
    
    /**
     * 纸箱数量导入
     * 
     * @param file
     * @param creator 操作人
     * @param ouId
     * @return
     * @throws Exception
     * @author weiwei.wu@baozun.com
     * @version 2018年8月20日 下午7:48:54
     */
    ReadStatus importCartonNum(File file,User creator, Long ouId);
}
