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
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.SkuType;
import com.jumbo.wms.model.baseinfo.InterfaceSecurityInfo;
import com.jumbo.wms.model.command.GiftLineCommand;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.warehouse.ExpressOrderCommand;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckLineCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.ReturnApplicationCommand;
import com.jumbo.wms.model.warehouse.ReturnPackageCommand;
import com.jumbo.wms.model.warehouse.ReturnPackageStatus;
import com.jumbo.wms.model.warehouse.SkuSn;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TransDeliveryType;

import loxia.support.excel.ReadStatus;



public interface WareHouseManagerExe extends BaseManager {
    
    void invChangeLogNoticePac(Long staId,Long stvId);

    /**
     * 解冻 换货出库（在换货入库单入库完成后就做换货出库单的解冻）
     * 
     * @param slipCode
     */
    void deblockingOutboundReturnRequest(String slipCode);


    void invChangeLongAddErrorCount(Long id);

    /**
     * 更新状态
     * 
     * @param sta
     */
    void updateReturnPackage(StockTransApplication sta, ReturnPackageStatus status);

    /**
     * 获取接口调用信息
     * 
     * @param str
     * @param date
     * @return
     */
    InterfaceSecurityInfo findUseringUserBySource(String str, Date date);

    /**
     * 相关收货凭证导入
     * 
     * @param file
     * @param staCode
     * @param fileName
     */
    void importInboundCertificateOfeceipt(File file, String staCode, String fileName);

    /**
     * 创建SN号
     * 
     * @param stv
     * @param stvLines
     */
    void createSN(StockTransVoucher stv, List<StvLine> stvLines);

    void updateSN(StockTransVoucher stv, List<StvLine> stvLines);

    /**
     * 出库SN占用
     * 
     * @param file
     * @param staId
     * @param ouid
     * @return
     */
    List<SkuSn> findEmploySkuSN(Long staId);

    /**
     * 特殊商品处理
     * 
     * @param giftLineList
     */
    void createGiftLine(List<GiftLineCommand> giftLineList);

    /**
     * 退换货入库
     * 
     * @param staId
     * @param listList
     */
    void inboundReturn(Long staId, String staCode, List<StvLine> listList, User user, Date inboundTime, String memo);

    /**
     * 验证渠道信息
     * 
     * @param stv
     */
    void validateBiChannelSupport(StockTransVoucher stv, String owner);

    /**
     * 获取批次信息
     * 
     * @param staType 作业单类型
     * @param staCode 作业单编码
     * @param slipCode 相关批次号
     * @param slipOcde1 相关批次1
     * @return
     */
    List<StvLineCommand> getOutBoundBachCode(StockTransApplicationType staType, String staCode, String slipCode, String slipCode1, String code);

    /**
     * 盘点调整作业单 库存占用
     * 
     * @param code
     */
    void occupyInventoryCheckSta(Long icId);

    /**
     * 计算盘点差异调整
     * 
     * @param icId
     */
    void calculationAdjustingFordifferences(Long icId, Long userId);

    /**
     * 库存占用
     * 
     * @param staId
     */
    void occupyInventoryCheckMethod(Long staId);

    /**
     * 盘点调整执行
     * 
     * @param icId
     */
    void exeAdjustingSta(Long icId, User user) throws Exception;

    /**
     * 盘点相关调整单取消
     * 
     * @param icId
     * @param user
     */
    void cancelAdjustingSta(Long icId, User user);

    /**
     * 盘点确认
     * 
     * @param icId
     * @param user
     */
    void invCheckDetermine(String code, User user);

    /**
     * 退换货包裹登记
     * 
     * @param rp
     */
    String returnPackageRegister(List<ReturnPackageCommand> rpList, OperationUnit ou, User user);

    /**
     * 校验批量导入的物流商是否有效
     * @return
     */
    boolean lpCodeWhetherValid(String code);
    
    void unfreezeByStaid(StockTransApplication sta, Long userId);
    
    void executeExtTransitInner(Long staId, Long operatorId, boolean isNotVolidateExpireDate, boolean isTransactionType) throws Exception;
    /**
     * O2OQS批量出库
     * 
     * @param staId
     * @param userId
     * @param ouid
     * @param trackingNo
     * @param weight
     * @param saddlines
     * @return
     */
    public Map<String, Object> o2oQsSalesStaOutBound(Long plpId, Long userId, Long ouid, String trackingNo, BigDecimal weight, List<StaAdditionalLine> saddlines);

    /**
     * 库存状态修改导入执行
     * 
     * @param staId
     * @param userId
     * @throws Exception
     */
    void executeInvStatusChangeForImpory(Long staId, Long userId, boolean isNotVolidateExpireDate, boolean isTransactionType) throws Exception;

    void updateSkuInfoByJmCode(String packageBarCode, String jmCode, BigDecimal length, BigDecimal width, BigDecimal height, BigDecimal weight, String categorieName, TransDeliveryType deliveryType, SkuType skuType);

    /**
     * 执行库内移动
     * 
     * @param isExcel true 不做导入数量校验
     * @param staId
     * @param stvlineList 必填owner、skuid、intInvStatusId、locationId
     * @param operatorId
     */
    void executeInvStatusChange(boolean isExcel, Long staId, List<StvLineCommand> stvlineList, Long operatorId) throws Exception;

    void executeLocationByStaId(Long staid, String code);
    
    String updateStaLpcode(Long ouid, String lpcode, Date createTime, Date endCreateTime, StockTransApplicationCommand sta, List<Long> idsList, Boolean isSelectAll, List<Long> wids);

    StockTransVoucher executePdaPurchase(Long staid, Long ouid, Long userid);
    
    void executeProcurementReturnPutaway(StockTransVoucher stv, List<StvLineCommand> stvlineList, Boolean finish, User operator, Boolean isForced);

    void editSkuByCode(SkuCommand proCmd);
    
    /**
     * 创建盘点批
     * 
     * @param remoak
     * @param ouId
     * @param list
     * @param userId
     */
    InventoryCheck createInventoryCheck(String remork, Long ouId, List<InventoryCheckLineCommand> list, Long userId, Boolean daily);

    /**
     * 仓库经理确认流程
     * 
     * @param code
     * @param username
     */
    void managerConfirmCheck(Long Id, String code, User usermanager, boolean check);

    /**
     * CK 同步库存 创盘点批
     * 
     * @param locIds
     * @param ouId
     * @param remork
     * @return
     */
    InventoryCheck createCKInventoryCheck(Long ouId, String remork);
    
    /**
     * 
     * 方法说明：(在库商品效期修改) 计算效期调整后的日期
     * 
     * @author LuYingMing
     * @param inventoryCommand
     * @return
     * @throws ParseException
     */
    Map<String, Object> operateDate(InventoryCommand inventoryCommand, InventoryCommand inv) throws ParseException;

    /**
     * 
     * 方法说明：在库商品效期修改
     * 
     * @author LuYingMing
     * @param whOuId
     * @param locationCode
     * @param skuId
     * @param inventoryStatusId
     * @param invOwner
     * @param pDate
     * @param eDate
     * @return
     * @throws ParseException
     */
    String validityAdjustModify(Long whOuId, InventoryCommand inv) throws ParseException;
    
    /**
     * 执行库内移动
     * 
     * @param isExcel true 不做导入数量校验
     * @param staId
     * @param stvlineList 必填owner、skuid、intInvStatusId、locationId
     * @param operatorId
     */
    void executeTransitInner(boolean isExcel, Long staId, List<StvLineCommand> stvlineList, Long operatorId);

    void executeVmiReturnOutBound(Long staID, Long userid, Long ouid);
    
    /**
     * 修改 不影响原始逻辑 新加方法 修改VMI库存调整执行！
     * 
     * @param ic
     */
    void confirmVMIInvCKAdjustmentNew(InventoryCheck ic);
    
    InventoryCheck createInventoryCheckMode2(String remork, Long ouId, List<InventoryCheckLineCommand> list, Long userId);

    /**
     * 确认盘点差异
     * 
     * @param confirmUser
     * @param invCkId
     */
    void confirmInventoryCheck(String confirmUser, Long invCkId);
    
    /**
     * 库间移动sta 库存占用
     * 
     * @param staId
     * @param invStatusId
     */
    void transitCrossStaOccupaid(Long staId, Long invStatusId, Long userId);
    
    void transactionTypeTransitCross(Long staId, List<StaLine> stalineList) throws Exception;
    
    String addReturnApplication(Long ouId, User user, ReturnApplicationCommand app);

    /**
     * 根据作业sta获取关系物流包裹快递单号
     * 
     * @param expressOrderCmd
     * @return
     */
    void updatePackageInfoTrackingNo(ExpressOrderCommand expressOrderCmd, List<ExpressOrderCommand> expLineCmd);

    /**
     * 批量执行上架
     */
    ReadStatus purchaseSetImperfect(File file, User operator);
    
    void editAllSkuByCode(SkuCommand proCmd, Long userId);
    
    /**
     * 修改纸箱数量
     * 
     * @param staId T_WH_STA表的ID
     * @param cartonNum 纸箱数
     * @author weiwei.wu@baozun.com
     * @version 2018年8月17日 下午4:21:27
     */
    void editCartonNumByStaId(Long staId,Integer cartonNum);
}
