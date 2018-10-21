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
package com.jumbo.wms.manager.warehouse.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import com.jumbo.rmi.warehouse.Order;
import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.lf.StaLfCommand;
import com.jumbo.wms.model.warehouse.ImportFileLog;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.ReturnPackage;
import com.jumbo.wms.model.warehouse.SkuSnLogCommand;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StaSnImportCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

import loxia.support.excel.ReadStatus;

public interface ExcelReadManager extends BaseManager {
    int SHEET_0 = 1;
    int SHEET_1 = 2;
    int SHEET_INBOUND_START_ROW = 3;
    int SHEET_INBOUND_START_COL = 0;
    String LOCATION_CODE = "CODE";
    String LOCATION_BAR_CODE = "BARCODE";
    String SKU_CODE = "CODE";
    String SKU_JM_CODE = "JMCODE";
    String SKU_BAR_CODE = "BARCODE";
    String SKU_KEYPROPERTIES = "KEYPROPERTIES";
    String SKU_NAME = "NAME";
    String SKU_ID = "ID";


    ReadStatus channelSkuSpTypeImport(File file) throws Exception;


    void locationImport(Long districtId, File file);

    ReadStatus importwhReplenishStock(File file, Long whouid);

    void invChangeNoticPac(Long staId);

    /**
     * 出库sn号导入
     * 
     * @param file
     * @param staId
     * @param ouid
     * @return
     */
    ReadStatus outboundSnImportByStv(File file, Long staId, Long ouid);

    ReadStatus pdaPurchaseSnImport(File file, Long staId, Long ouid);

    /**
     * 导入盘盈处理结果
     * 
     * @param file
     * @param invCkId
     * @return
     */
    ReadStatus importCheckOverage(File file, Long invCkId);

    ReadStatus predefinedOutImport(Long staId, File staFile, User creator, InboundStoreMode mode) throws Exception;

    /**
     * 带入SN号
     * 
     * @param file
     * @param staid
     * @param ouid
     * @return
     */
    ReadStatus snsImport(File file, Long staid, Long ouid);

    /**
     * 带入SN号
     * 
     * @param file
     * @param staid
     * @param ouid
     * @return
     */
    ReadStatus importSNnumber(File file, Long ouid);

    /**
     * 库间移动申请单导入excel
     * 
     * @param file
     * @return
     */
    Map<String, Object> importVMIFlittingApplication(File file, Long main_whouId, String owner);

    ReadStatus externalInImport(Long staId, File staFile, User creator, InboundStoreMode mode) throws Exception;

    ReadStatus packagingMaterialsImport(Long staId, File staFile, User creator) throws Exception;

    /**
     * 库间移动申请单导入excel
     * 
     * @param file
     * @return
     */
    Map<String, Object> importBetweenLibraryMoveApplication(File file, Long main_whouId);

    /**
     * 自定义出入库导入
     * 
     * @param file
     * @param ouid
     * @return
     */
    ReadStatus otherStaImport(String slipCode, File file, String owner, StaDeliveryInfo stadelivery, String memo, Long transactionId, Long ouid, Long userId);


    ReadStatus importInvoicePercentage(File file) throws Exception;

    ReadStatus generateVMIInventoryAdjust(String slipCode, String seasonCode, File file, Long ownerid, Long ouid, Long userid, InventoryCheck ic) throws Exception;

    /**
     * 库位导入-创建盘点表
     * 
     * @param file
     * @param id
     * @param cmpOuid
     * @param id2
     * @return
     */
    Map<String, Object> createInventoryCheckByImportLocation(File file, String remark, Long ouid, Long userid) throws Exception;

    ReadStatus createStaForVMITransfer(StockTransApplication sta, File file, Long ownerid, Long addiownerid, Long ouid, Long cmpOuid, Long userid, Long invstatusId) throws Exception;

    ReadStatus vmiAdjustmentImport(File file, Long invCkId, Long companyId, Long whOUID);

    /**
     * 导入盘点结果
     * 
     * @param file
     * @param invCkId
     * @return
     */
    ReadStatus inventoryCheckImport(File file, Long invCkId, Long ouId, Long whOUID, Long userId);

    ReadStatus importEspritPoType(File file) throws Exception;

    /**
     * 预定义出库
     * 
     * @param file
     * @param ouId
     * @param userId
     * @return
     */
    ReadStatus createOperationOut(File file, StockTransApplicationCommand staComd, Long ouId, Long userId) throws Exception;

    void createOperationOut(Order order);

    /**
     * 采购导入
     * 
     * @param stacode
     * @param file
     * @param ouId
     * @param cmpOuId
     * @param userId
     * @return
     */
    ReadStatus createPurchaseInboundImport(File file, Long staid, Long stvId, User operator);

    /**
     * 导入sn数据商品至前台判断
     * 
     * @param file
     * @return
     */
    List<StaSnImportCommand> importsnToWeb(File file, Long ouId, List<SkuSnLogCommand> snList, String snBarCode);

    /**
     * 库存初始化导入
     * 
     * @param stacode
     * @param file
     * @param ouId
     * @param cmpOuId
     * @param userId
     * @return
     */
    ReadStatus createInitializeInventoryImport(StockTransApplication stacode, File file, Long ouId, Long cmpOuId, Long userId);

    /**
     * 库内移动导入
     * 
     * @param file
     * @param ouId
     * @param userId
     * @return
     */
    ReadStatus transitInnverImport(File file, String remork, Long ouId, Long userId, Long fileId) throws Exception;


    ReadStatus importSkuProvideMaxMaintain(File file, Long ouid);

    /**
     * 库存状态修改导入
     * 
     * @param file
     * @param ouId
     * @param userId
     * @return
     */
    ReadStatus inventoryStatusChangeImport(File file, Long shopId, String remork, Long ouId, Long userId) throws Exception;

    /**
     * STA确认收货导入
     * 
     * @param staFile
     */
    ReadStatus staImportForPurchase(Long staId, StockTransVoucher stv, File staFile, User creator, InboundStoreMode mode) throws Exception;

    /**
     * 送修导入
     * 
     * @param staFile
     */
    ReadStatus staImportForRepair(Long staId, StockTransVoucher stv, File staFile, User creator, InboundStoreMode mode) throws Exception;

    /**
     * 收货-数量确认导入
     * 
     * @param id
     * @param goodsInfoFile
     * @param user
     * @return
     */
    Map<String, Object> inboundAmountConfirmImport(Long id, File goodsInfoFile, User user) throws Exception;

    /**
     * 预定义入库导入
     * 
     * @param file
     * @param ouid
     * @return
     */
    ReadStatus predefinedStaImport(File file, String memo, String owner, Long invStatus, Long transactionType, Long ouid, Long userId);

    ReadStatus importRefreshPickingList(File file, Long plId, Long ouId, Long userId) throws FileNotFoundException;

    ReadStatus importProductFoxBoxOperate(File file, Long userid);

    public ReadStatus vmiReturnValidate(ReadStatus rs, List<StaLineCommand> stalinecmds, List<StaLine> stalines, Map<String, InventoryStatus> invmap, Map<String, WarehouseLocation> locationmap, Long cmpOuid, Long ouid, String type, String biCode,
            TransactionType transactionType, StockTransApplication sta, BiChannel shop);

    ReadStatus createStaForVMIReturn(StockTransApplication sta, StaDeliveryInfo stadelivery, File file, String innerShopCode, String toLocation, Long ouid, Long cmpOuid, Long userid, String reasonCode, boolean espritFlag, boolean espritTransferFlag)
            throws Exception;

    ReadStatus createStaForVMIReturnPl(String owner, StaDeliveryInfo stadelivery, File file, String innerShopCode, String toLocation, Long ouid, Long cmpOuid, Long userid, String reasonCode, boolean espritFlag, boolean espritTransferFlag)
            throws Exception;

    ReadStatus zdhPiciImport(File file, Long ouid, Long userId) throws Exception;

    // 执行esprit数据 行
    ReadStatus creStaForVMIReturnEsToLine(List<StaLineCommand> stalinecmds, ReadStatus rs, String type, BiChannel shop, StockTransApplication sta, StaDeliveryInfo stadelivery, File file, String innerShopCode, String toLocation, Long ouid, Long cmpOuid,
            Long userid, String reasonCode, boolean espritFlag, boolean espritTransferFlag, boolean isNikeRetrun, StaLfCommand staLfCommand, Map<String, StaLfCommand> mapStaUpcEn) throws Exception;

    // 封装esprit 数据头
    ReadStatus creStaForVMIReturnEs(String owner, String lpCode, File file, String innerShopCode, String toLocation, Long ouid, Long cmpOuid, Long userid, String reasonCode, boolean espritFlag, boolean espritTransferFlag) throws Exception;

    /**
     * 根据导入excel返回导入快递单号
     * 
     * @param trackingNoFile
     * @return
     * @throws Exception
     */
    List<String> hoListImport(File trackingNoFile) throws Exception;

    /**
     * 变私有方法为公用 KJL ADD
     * 
     * @param ic
     * @param importData
     */
    void margenIncentoryCheckDifLineMode2(InventoryCheck ic, Map<String, Long> importData);

    /**
     * 变私有方法为公用 KJL ADD
     * 
     * @param ic
     * @param importData
     */
    void margenIncentoryCheckDifLine(InventoryCheck ic, Map<String, Long> importData);

    /**
     * 销售导入
     * 
     * @param file
     * @param ouid
     * @return map key {"ReadStatus","snList"}
     */
    Map<String, Object> salesOutSnImport(File file, Long staId, Long ouid);

    /**
     * 保质期商品导入
     */
    ReadStatus createSkuForShelfLife(File file) throws Exception;

    void margenIncentoryCheckDifLinePC(InventoryCheck ic, Map<String, Long> importData, Map<String, String> importDataToSM) throws Exception;

    /**
     * 导入模板批量收货
     */
    public ReadStatus importForBatchReceiving(String staCode, List<StvLineCommand> stvLines, User creator) throws Exception;


    /**
     * 保质期导入模板批量收货
     */
    public ReadStatus findShelfLifeSkuCountByStaCode(String staCode, List<StvLineCommand> stvLines, User creator) throws Exception;


    ReadStatus importStaDeliveryInfo(File file) throws Exception;

    /**
     * 商品标签关联Sku导入
     */
    ReadStatus importRefSku(File file, Long tagId) throws Exception;

    /**
     * 负向采购退货批量入库上架
     * 
     * @param staCode
     * @param stvLines
     * @param creator
     * @return
     * @throws Exception
     */
    ReadStatus importBatchProcucrementInbound(String staCode, List<StvLineCommand> stvLines, User creator, Long ouId) throws Exception;

    /**
     * 拣货区域导入
     * 
     * @param file
     * @return
     * @throws Exception
     */
    ReadStatus importPickZoneInfo(File file, Long userId, Long ouId, Integer flag) throws Exception;

    void incrementInv(Long staId);


    /**
     * 导入货箱定位规则
     * 
     * @param file
     * @param ouId
     * @return
     * @throws Exception
     */
    public ReadStatus importInboundRole(File file, Long ouId) throws Exception;

    /**
     * 批量维护商品的上架类型
     * 
     * @param skuTypeImportFile
     * @return
     */
    ReadStatus importSkuType(File skuTypeImportFile) throws Exception;



    ReadStatus importSkuSpType(File skuTypeImportFile) throws Exception;

    /**
     * 导入商品sku码
     * 
     * @param file
     * @return
     */
    List<String> importParticularcommdySku(File file);

    /**
     * 批量导入退换货登记
     * 
     * @param file
     * @return
     */
    List<ReturnPackage> importReturnPackage(File file);

    /**
     * 
     * 方法说明：菜鸟仓发货SN号(菜鸟仓)
     * 
     * @author LuYingMing
     * @date 2016年9月12日 上午10:03:05
     * @param file
     * @param ouid
     * @return
     */
    ReadStatus outOfStorageSnImport(File file, Long ouid);

    /**
     * converse转店退仓批量创建指令
     * 
     * @param file
     * @return
     */
    ReadStatus vmiReturnImportConverse(String owner, String returnReason, File file, String innerShopCode, String toLocation, Long ouid, Long cmpOuid, Long userid, String reasonCode, boolean converseFlag, boolean converseTransferFlag) throws Exception;

    void insertImprotFileLog(Long whId, String userName, String memo, Long createId, File file, String fileName);


    List<Long> findAllTodowhId(Integer type, Integer status);



    /**
     * 执行exl导入文件
     * 
     * @param url
     * @return
     */
    public String exeFileInput(ImportFileLog files);

    /**
     * 查找未执行的文件
     * 
     * @param type
     * @param status
     * @return
     */
    List<ImportFileLog> findAllToDeleteFile();

    /**
     * 执行删除文件
     * 
     * @param file
     */
    void exeDeleteFileTask(ImportFileLog file);

    /**
     * 导入出库单信息
     * 
     * @param file
     * @return
     */
    public Map<String, Object> orderOutBoundInfoImport(File file);

    /**
     * gucci 退大仓的库存状态
     * 
     * @param file
     * @param rtoId
     * @return
     * @throws Exception
     */
    public ReadStatus gucciRtoInvStatus(File file, Long rtoId, Long ouId) throws Exception;



    /**
     * NIKE收货-导入箱号关系
     * 
     * @param file
     * @return
     */
    ReadStatus nikeRelationImport(File file, Long ouid, String userName);

    /**
     * 导入临时库间移动
     * 
     * @param file
     * @param mainWhouId
     * @param targetOuId
     * @param owner
     * @param userId
     * @return
     */
    public String importOdoLine(File file, Long mainWhouId, Long targetOuId, String owner, Long userId, Long invStatusId, Long odoId);
    
    /**
     * 供应商编码导入
     * @param file
     * @return
     */
    List<String> importsupplierCode(File file);



    /**
     * 保税仓-商品模块-商品主档导入
     * 
     * @param file
     * @return
     */
    ReadStatus goodsImport(File file);

}
