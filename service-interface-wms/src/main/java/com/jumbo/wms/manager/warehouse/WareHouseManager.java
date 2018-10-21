package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.WmsOtherOutBoundInvNoticeOmsStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.Zoon;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuBarcode;
import com.jumbo.wms.model.baseinfo.SkuModifyLog;
import com.jumbo.wms.model.baseinfo.SkuRfid;
import com.jumbo.wms.model.baseinfo.TransSfInfo;
import com.jumbo.wms.model.baseinfo.TransportatorWeigth;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.EspritStoreCommand;
import com.jumbo.wms.model.command.GiftLineCommand;
import com.jumbo.wms.model.command.InventoryStatusCommand;
import com.jumbo.wms.model.command.SalesReportFormCommand;
import com.jumbo.wms.model.command.SkuBarcodeCommand;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.command.SkuCountryOfOriginCommand;
import com.jumbo.wms.model.command.SkuWarehouseRefCommand;
import com.jumbo.wms.model.command.StaDeliverCommand;
import com.jumbo.wms.model.lf.StaLfCommand;
import com.jumbo.wms.model.lf.ZdhPiciCommand;
import com.jumbo.wms.model.lf.ZdhPiciLineCommand;
import com.jumbo.wms.model.mongodb.TwicePickingBarCode;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.Default.TransferOwnerSource;
import com.jumbo.wms.model.vmi.Default.TransferOwnerSourceCommand;
import com.jumbo.wms.model.vmi.Default.TransferOwnerTargetCommand;
import com.jumbo.wms.model.vmi.Default.VmiRtoCommand;
import com.jumbo.wms.model.vmi.Default.VmiRtoLineCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrderCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgInvoice;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundCommand2;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundLine;
import com.jumbo.wms.model.warehouse.*;
import com.jumbo.wms.model.warehouse.baoShui.CustomsDeclarationDto;
import com.jumbo.wms.model.warehouse.baoShui.CustomsDeclarationLineCommand;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONArray;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;


public interface WareHouseManager extends BaseManager {
    //
    String LOCATION_CODE = "CODE";
    String LOCATION_BAR_CODE = "BARCODE";
    String SKU_CODE = "CODE";
    String SKU_JM_CODE = "JMCODE";
    String SKU_BAR_CODE = "BARCODE";
    String SKU_KEYPROPERTIES = "KEYPROPERTIES";
    String SKU_NAME = "NAME";
    String SKU_ID = "ID";
    int SHEET_0 = 1;
    int SHEET_1 = 2;
    int SHEET_INBOUND_START_ROW = 3;
    int SHEET_INBOUND_START_COL = 0;

    void autoOutBoundByAll(String message);


    public StaLfCommand findOutBound(Long staId);

    void vmiReturnFeedbackConverse(StockTransApplication sta, String source);


    String findSkuById(Long skuId);


    String downloadBackUpInv(Long id);

    String checkStatusById(Long id);

    public Boolean findCrwStaByStaId(Long staId);


    public Boolean saveCrwStaByStaId(Long staid, Long lfId, String crd, String nfsStoreCode, String city, String zip, String address1, String address2, String address3, String address4);

    String backUpStaStatus(Long id);


    List<StockTransApplicationCommand> importStaByOwner(Long id);



    List<StockTransApplicationCommand> findNikeCRWCartonLine1(StockTransApplicationCommand staCmd, Long ouId);


    BiChannelSkuSupplies findpaperSkuByBarCode(String barCode, Long staId, Long ouId);

    String backUpSta(Long id);

    String cleanInvByCode(Long id);

    String backUpInv(Long id);

    String createInvTxt(Long id);

    String insertToIMById(Long id);

    Pagination<ZdhPiciLineCommand> getHistoricalCodeListLine(int start, int size, Sort[] sorts, Long id);

    Pagination<ZdhPiciCommand> getHistoricalCodeList(int start, int size, Sort[] sorts);

    List<StockTransApplicationCommand> findNikeCRWCartonLine(StockTransApplicationCommand staCmd, Long ouId);

    Pagination<StockTransApplicationCommand> getoutboundpackageByStalist(int start, int pageSize, StockTransApplicationCommand staCmd, Long ouid, Sort[] sorts);

    String findPistListStatus(Long ouId, String pistListCode);

    String outBoundByPistList(String pickListCode, Long ouId, Long userId, String barCode, BigDecimal weigth);

    MsgRtnOutbound saveMsgRtnOut(MsgRtnOutbound msg);

    // /**
    // * 创建销售出库作业单
    // *
    // * @param sta
    // * @param whOuId
    // * @param isOutWh
    // * @return 是否创建
    // */
    // boolean createStaForSales(Order order, Long whOuId, boolean isOutWh);

    void autoGdvChangeOwner();

    void sfOrderFilterAccept(SfOrderFilterLog lg);

    String checkPickingSkuByStaId(String st, Long id, Long ouId, Long userId);


    // /**
    // * 获取Sta code
    // *
    // * @param sta
    // * @return
    // */
    // String getStaCode(StockTransApplication sta);

    // InventoryStatus
    /**
     * 库存状态列表列表
     * 
     * @param isSystem 是否是系统内置
     * @param ou 公司
     * @param sorts
     * @return
     */
    List<InventoryStatusCommand> findInventoryStatusList(boolean isSystem, OperationUnit ou, Sort[] sorts);

    /**
     * 创建
     * 
     * @param entity
     * @return
     */
    InventoryStatus createInventoryStatus(InventoryStatus entity);

    /**
     * 保存出库通知记录， 必须已经存在单据的出库通知记录，该方法为复制原记录，明细为剩余入库数量
     * 
     * @param staCode
     */
    void saveMsgInboundForBatchCode(String staCode);

    /**
     * 更新
     * 
     * @param entity
     * @return
     */
    InventoryStatus updaetInventoryStatus(InventoryStatus entity);

    /**
     * /** 创建
     * 
     * @param entity
     * @return
     */
    WarehouseDistrict createWarehouseDistrict(WarehouseDistrict entity);

    Pagination<TransportatorWeigth> findTransportatorListByWeight(int start, int pagesize, long ouId, String expCode, Sort[] sorts);

    void disableLocations(List<WarehouseLocation> locations);


    void updateTransportatorWeigth(String expCode, String maxWeight, String minWeight, String weightDifferencePercent, String lpCodeWeigthId, long ouId);

    /**
     * 查询当前公司的物流商
     * 
     * @param whouid
     * @return
     */
    List<TransDeliveryCfgCommand> findPredefinedStaByType(Long whouid);

    void updateTransDeliveryCfg(Long whOuId, Long transId, Long qty);

    /**
     * 根据组织为仓库的组织id查找相关的仓库列表，或未找到仓库的本组织列表
     * 
     * @param operationUnitList
     * @return
     */
    Map<String, List<Object>> findOperationUnitOrWarehouseMap(List<OperationUnit> operationUnitList);

    /**
     * 查找仓库的附加信息
     * 
     * @param start
     * @param pageSize
     * @param operationUnitList
     * @param sorts
     * @return
     */
    Pagination<Warehouse> findWarehouseDetailList(int start, int pageSize, List<OperationUnit> operationUnitList, Sort[] sorts);


    Boolean isCodByStaId(Long staId);


    Pagination<PackageInfoCommand> findByTrackingNoAndLpCode(int start, int pageSize, String trackingNo, String lpCode, Sort[] sorts);

    /**
     * 批量设置仓库共享性
     * 
     * @param ids
     * @param flag
     */
    void updateInventoryShare(List<Long> ids, int flag);

    /**
     * 根据类型查找STA
     * 
     * @param type
     * @param wh 仓库
     * @return
     */
    Pagination<StockTransApplicationCommand> findStaNotFinishedListByType(StockTransApplication sta, Integer isNeedInvoice, String lpcode, String trackingNo, OperationUnit currentOu, Date startTime, Date endTime, Date arriveStartTime, Date arriveEndTime,
            int start, int size, String slipCode1, String slipCode2, Sort[] sorts);

    /**
     * 根据类型查找STA
     * 
     * @param type
     * @param wh 仓库
     * @return
     */
    Pagination<StockTransApplicationCommand> findStaNotFinishedListByTypeNew(StockTransApplication sta, Integer isNeedInvoice, String lpcode, String trackingNo, OperationUnit currentOu, Date startTime, Date endTime, Date arriveStartTime,
            Date arriveEndTime, int start, int size, String slipCode1, String slipCode2, Sort[] sorts);

    /**
     * 根据类型查找STA
     * 
     * @param type
     * @param wh 仓库
     * @return
     */
    Pagination<StockTransApplicationCommand> findStaNotFinishedImperfectListByType(StockTransApplication sta, OperationUnit currentOu, Date startTime, Date endTime, int start, int size, String slipCode1, String slipCode2, Sort[] sorts);

    Pagination<StockTransApplicationCommand> findlockedSta(StockTransApplication sta, String barCode, Date startTime, Date endTime, String lpcode, String trackingNo, OperationUnit wh, String receiver, String receiverPhone, String orderCode,
            String taobaoOrderCode, int start, int size, Sort[] sorts);

    void updateStaUnlocked(Long staId, String lpcode, String trackingNo, Long returnReasonType, String returnReasonMemo, Long ouId, String loginName);

    /**
     * 根据类型查找预定义入库STA
     * 
     * @param type
     * @param wh 仓库
     * @return
     */
    List<StockTransApplicationCommand> findPredefinedStaByType(List<Integer> types, OperationUnit wh, Sort[] sorts);


    void saveTransportatorWeight(String expCode, String maxWeight, String minWeight, String weightDifferencePercent, String lpCodeWeigthId, long ouId);


    Pagination<StaOpLogCommand> showShelvesSNDetail(int start, int size, Long staId, Sort[] sorts);

    /**
     * 查找预定义入库STA
     * 
     * @param type
     * @param wh 仓库
     * @return 未分页
     */
    List<StockTransApplicationCommand> findPredefinedSta(OperationUnit wh, Sort[] sorts);

    /**
     * 查找预定义入库STA
     * 
     * @return 分页操作
     */
    Pagination<StockTransApplicationCommand> findPredefinedStaByPagination(StockTransApplication sta, OperationUnit wh, Date startTime, Date endTime, Date arriveStartTime, Date arriveEndTime, boolean isVMI, boolean SAMPLE, int types, int status,
            int start, int size, Sort[] sorts);

    Pagination<StockTransApplicationCommand> findPredeCancelStaByPagination(StockTransApplication sta, OperationUnit wh, Date startTime, Date endTime, Date arriveStartTime, Date arriveEndTime, boolean isVMI, boolean SAMPLE, Integer intStatus,
            Integer intStaType, int types, int status, int start, int size, Sort[] sorts);

    Pagination<StockTransApplicationCommand> findPredefinedStaByPaginationRoot(StockTransApplication sta, OperationUnit wh, Date startTime, Date endTime, Date arriveStartTime, Date arriveEndTime, boolean isVMI, boolean SAMPLE, int types, int status,
            int start, int size, Long ouId, Sort[] sorts);

    void modifyroleStatus(Long StaId, Long userId);

    List<StockTransApplicationCommand> findStaNotFinishedListByTypeNoPage(StockTransApplicationType type, OperationUnit wh, Sort[] sorts);

    List<StockTransApplicationCommand> findTranCossStaNotFinishedListByTypeNoPage(StockTransApplicationType type, OperationUnit wh, Sort[] sorts);

    Map<Integer, StockTransApplicationStatus> selectStatusByStaid();

    Map<Integer, StockTransApplicationType> selectTypesByStaid();

    String findAllOptionListByOptionKey(String optionkey, String categoryCode);

    /**
     * 根据类型查找库间移入STA
     * 
     * @param type
     * @param wh 仓库
     * @return
     */
    Pagination<StockTransApplicationCommand> findTranCossStaNotFinishedListByType(StockTransApplication sta, OperationUnit wh, Date startTime, Date endTime, Date arriveStartTime, Date arriveEndTime, int start, int size, String slipCode1,
            String slipCode2, Sort[] sorts);

    /**
     * 根据类型查找库间移入STA
     * 
     * @param type
     * @param wh 仓库
     * @return
     */
    Pagination<StockTransApplicationCommand> findImperfectListByType(OperationUnit wh, int start, int size, Sort[] sorts);


    /**
     * 查找退货申请列表
     * 
     * @param app
     * @param startTime
     * @param endTime
     * @param start
     * @param size
     * @param sorts
     * @return
     */
    Pagination<ReturnApplicationCommand> findReturnAppList(Long ouId, ReturnApplicationCommand app, Date startTime, Date endTime, int start, int size, Sort[] sorts, Date feedBackstartTime, Date feedBackendTime);

    /**
     * 根据运单号查找退货申请
     * 
     * @param ouId
     * @param trackNo
     * @return
     */
    ReturnApplicationCommand findReturnAppByTrackNo(Long ouId, String trackNo);

    /**
     * 查询负向采购退货入的单据
     * 
     * @param stacmd
     * @param startTime
     * @param endTime
     * @param start
     * @param size
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> findStaProcurementReturnInboundList(StockTransApplicationCommand stacmd, Boolean isStvId, Long whId, Date startTime, Date endTime, int start, int size, Sort[] sorts);

    List<StaLineCommand> findStalinelist(Long staId, Sort[] sorts);


    /**
     * 负向采购退货入库确认
     * 
     * @param sta
     * @param stvLineList
     * @param creator
     */
    void procurementReturnInboundReceiptConfirm(Long staId, List<StvLine> stvLineList, User creator);

    /**
     * 查询负向采购退货入库待上架列表
     * 
     * @return
     */
    List<StvLineCommand> findProcurementReturnInboundByStvListId(List<Long> stvListId);

    /**
     * 上传退货文档
     * 
     * @param file
     */
    void updateReturnDoc(File file, String code);



    /**
     * 
     * @param staId
     * @return
     */
    StockTransApplicationCommand findStaById(Long staId);

    /**
     * 
     * @param staId
     * @return
     */
    StockTransApplicationCommand findStaCmdById(Long staId);

    // ====================================================
    // StaLine
    // ====================================================
    /**
     * 获取未完成收货STA作业单的明细行
     * 
     * @param staId
     * @param sorts
     * @return
     */
    List<StaLineCommand> findStaLineListByStaId(Long staId, Sort[] sorts);

    /**
     * 获取按箱收货作业单
     * 
     * @param staId
     * @param sorts
     * @return
     */
    List<StockTransApplicationCommand> findCartonStaByGroupStaId(Long staId);

    Pagination<StaLineCommand> findStaLineListByStaIdByPage(int start, int pageSize, Long staId, Sort[] sorts);

    /**
     * 获取未完成收货STA作业单的明细行(手动收货)
     * 
     * @param staId
     * @param sorts
     * @return
     */
    List<StaLineCommand> findStaLineListByStaIdHand(Long staId, Sort[] sorts);


    /**
     * 手动收货 明细（星巴克）
     * 
     * @param staId
     * @param sorts
     * @return
     */
    List<StaLineCommand> findHandStaLineStarbucksBySta(Long staId, Sort[] sorts);

    Pagination<StaLineCommand> findStaLineByStaFinish(int start, int pageSize, Long staId, Sort[] sorts);

    /**
     * 查寻vmi调拨的入库明细
     * 
     * @param staId
     * @param sorts
     * @return
     */
    List<StaLineCommand> findVMIFlittingEnterLine(Long staId, Sort[] sorts);

    /**
     * 获取STA作业单的明细行,包含sku的相关信息
     * 
     * @param staId
     * @param sorts
     * @return
     */
    Pagination<StaLineCommand> findStaLineCommandListByStaId(int start, int pageSize, Long staId, Sort[] sorts);

    /**
     * 获取STA作业单的明细行,包含sku的相关信息
     * 
     * @param staId
     * @param sorts
     * @return
     */
    Pagination<StaLineCommand> findHistoricalOrderDetailList(int start, int pageSize, Long staId, Sort[] sorts);

    /**
     * 根据barCode更新Nike商品信息
     */
    void updateNikeSkuInfoByBarCode(String packageBarCode, String barcode, String supplierCode, String keyProperties, String name, BigDecimal length, BigDecimal width, BigDecimal height, BigDecimal weight, String categorieName, String countryOfOrigin,
            String htsCode, String unitName);

    /**
     * 获取STA作业单的明细行,包含sku的相关信息
     * 
     * @param staId
     * @param sorts
     * @return
     */
    Pagination<StvLineCommand> findStvLineCommandListByStaId(int start, int pageSize, Long staId, Date createTime, Date endCreateTime, Date finishTime, Date endFinishTime, StvLineCommand stvLineCommand, Sort[] sorts);

    /**
     * 获取STA作业单的操作明细行,包含sku的相关信息
     * 
     * @param staId
     * @param sorts
     * @return
     */
    Pagination<StvLineCommand> findHistoricalOrderDetailOperateList(int start, int pageSize, Long staId, Date createTime, Date endCreateTime, Date finishTime, Date endFinishTime, StvLineCommand stvLineCommand, Sort[] sorts);

    /**
     * 获取STA作业单的明细行,包含skuSn的相关信息
     * 
     * @param staId
     * @param sorts
     * @return
     */
    Pagination<StaLineCommand> findStvLineSnListByStaId(int start, int pageSize, Long staId, Sort[] sorts);


    /**
     * 获取作业单操作日志
     * 
     * @param refSlipCode
     * @param sorts
     * @return
     */
    Pagination<WhInfoTimeRefCommand> findWhInfoTimeListBySlipCode(int start, int pageSize, String refSlipCode, String code, Long ouId, Sort[] sorts);

    /**
     * 行特殊处理
     */

    Pagination<GiftLineCommand> selectSpecialLog(int start, int pageSize, Long staId, Sort[] sorts);


    // ====================================================
    // StockTransVoucher
    // ====================================================
    /**
     * 创建新的上架作业单
     * 
     * @param staId 作业申请单Id
     * @param stvLineList 实际收货情况，如果该列表为null，则说明采用无条件全部收货
     * @param creator 当前用户
     * @return
     */
    StockTransVoucher purchaseReceiveStep1(Long staId, List<StvLine> stvLineList, List<GiftLineCommand> giftLineList, User creator, String memo, Boolean isPda, boolean isExport, Integer snType);

    StockTransVoucher purchaseReceiveStep1(StockTransApplication sta, List<StvLine> stvLineList, List<GiftLineCommand> giftLineList, User creator, String memo, Boolean isPda, boolean isExport);

    void suggestInboundLocation(Long stvId, boolean isSuggest);

    /**
     * load带有建议库位的StvLine
     * 
     * @param stvId
     * @return
     * @throws Exception
     */
    Map<String, List<StvLineCommand>> loadStvLineByStvId(Long stvId) throws Exception;

    JSONArray loadStvLineByStvIdGroupBatchCode(Long stvId) throws Exception;

    /**
     * 采购入库上架
     * 
     * @param stvId 待上架Stv
     * @param stvlineList stvLineList实际上架情况
     * @param finish 是否关闭PO
     * @param isCheckLocation 是否检查库位容积、支持作业类型
     * @param operator 当前用户
     */
    void purchaseReceiveStep2(Long stvId, List<StvLine> stvlineList, boolean finish, User operator, boolean isNotVolidateExpireDate, boolean isTransactionType);

    /**
     * 采购入库上架前设置发票号
     */
    void purchaseSetInvoiceNumber(StockTransVoucher stv, List<StvLineCommand> stvlineList, boolean finish, User operator, Boolean isForced);



    /**
     * 通过仓库组织id查找当前公司下有效的库存状态
     * 
     * @param whOuId
     * @return
     */
    List<InventoryStatus> findInvStatusListByCompany(Long companyOuId);

    /**
     * 已占用库存量列表查询
     * 
     * @param queryParams
     * @return
     */
    List<OccupationInfoCommand> findOccupationInfo(Map<String, Object> queryParams);

    /**
     * 查询未完成sta
     * 
     * @param shopId
     * @param sta
     * @param wh_ou_id
     * @param sorts
     * @return
     */
    // List<StockTransApplicationCommand> findSalesCanCancelStaList(String
    // shopId,StockTransApplication sta,Long wh_ou_id,Sort[] sorts);
    Pagination<StockTransApplicationCommand> findSalesCanCancelStaList(int start, int pageSize, String shopId, StockTransApplication sta, Long wh_ou_id, Sort[] sorts);

    /**
     * 根据运单号查询sta
     * 
     * @param trackingNo 运单号
     * @param wh_ou_id
     * @return
     */
    StockTransApplicationCommand findSalesStaByTrackingNo1(String trackingNo, Long wh_ou_id, List<Long> idList);

    /**
     * 根据运单号查询sta
     * 
     * @param mode3 运单号
     * @param trackingNo
     * @return
     */
    StockTransApplicationCommand findSalesStaByTrackingNo(PickingMode mode3, String trackingNo, Long wh_ou_id, List<Long> plists);


    /**
     * O2OQS出库根据快递单号获取批次单号
     * 
     * @param trackingNo
     * @param wh_ou_id
     * @return
     */
    PickingListPackageCommand findPickingListPackageByTrackingNo(String trackingNo, Long wh_ou_id);

    /**
     * 查询待配货类表
     * 
     * @param fromDate
     * @param toDate
     * @param shopId
     * @param isNeedInvoice
     * @param sta
     * @param wh_ou_id
     * @param sorts
     * @return
     */
    List<StockTransApplicationCommand> findSalesPendingStaList(List<String> cities, Boolean isSkuSn, Date fromDate, Date toDate, List<String> innerCoders, String shopId, Integer isNeedInvoice, StockTransApplication sta, Sku sku, Long wh_ou_id,
            List<String> skuCodeList, Sort[] sorts);

    Pagination<StockTransApplicationCommand> findSalesPendingStaListPage(String shoplist, List<String> cities, int start, int pageSzie, Boolean isSkuSn, Date fromDate, Date toDate, String shopId, Integer isNeedInvoice, StockTransApplication sta, Sku sku,
            Long wh_ou_id, List<String> skuCodeList, Sort[] sorts);

    /**
     * 返回时间范围内所有状态新建的sta
     * 
     * @param typeList 类别列表
     * @param statusList 状态列表
     * @param shopId
     * @param sta
     * @param fromDate
     * @param toDate
     * @param ouid
     * @param checkPickingList
     * @param isCheckInBoundSta 是否检查退货入库sta状态
     * @param isLike 是否模糊查询
     * @param sorts 如果为空，则按照trackingNo精确查找
     * @return
     */
    List<StockTransApplicationCommand> findSalesStaList(List<String> cities, String province, Boolean isNotGroup, Boolean isLpCode, Boolean isSnSku, List<Integer> typeList, List<Integer> statusList, List<String> innerCoders, String shopId,
            StockTransApplication sta, Sku sku, Date fromDate, Date toDate, Long ouid, List<Long> plist, Boolean checkPickingList, Boolean isCheckInBoundSta, Boolean isLike, List<String> skuCodeList, Sort[] sorts);

    /**
     * 返回时间范围内所有状态新建的sta by page
     * 
     * @param typeList 类别列表
     * @param statusList 状态列表
     * @param shopId
     * @param sta
     * @param fromDate
     * @param toDate
     * @param ouid
     * @param checkPickingList
     * @param isCheckInBoundSta 是否检查退货入库sta状态
     * @param isLike 是否模糊查询
     * @param sorts 如果为空，则按照trackingNo精确查找
     * @return
     */
    Pagination<StockTransApplicationCommand> findSalesStaListByPage(int start, int pagesize, List<String> cities, String province, Boolean isNotGroup, Boolean isLpCode, Boolean isSnSku, List<Integer> typeList, List<Integer> statusList,
            List<String> innerCoders, String shopId, StockTransApplication sta, Sku sku, Date fromDate, Date toDate, Long ouid, List<Long> plist, Boolean checkPickingList, Boolean isCheckInBoundSta, Boolean isLike, List<String> skuCodeList, Sort[] sorts);


    List<StockTransApplicationCommand> findToSupportTransSalesPendingStaList(String province, Boolean isSkuSn, Date fromDate, Date toDate, String shopId, Integer isNeedInvoice, StockTransApplication sta, Long wh_ou_id, Sort[] sorts);

    /**
     * 创建拣货单
     * 
     * @param staIds
     * @param mode
     * @param ouId
     * @param creatorId
     * @param pickingMode
     * @return
     */
    void createPickingList(Boolean isSpecialPackaging, List<Long> staIds, ParcelSortingMode mode, PickingMode pickingMode, Long ouId, Long creatorId, Boolean isMqInvoice, Boolean isBigBox);

    void genPickingListNative(boolean isSpecialPackaging, String shoplist, List<String> cities, ParcelSortingMode mode, PickingMode pickingMode, Long creatorId, Integer minLimit, Integer limit, Integer count, Boolean isSkuSn, Date fromDate, Date toDate,
            String shopId, Integer isNeedInvoice, StockTransApplication sta, Sku sku, Long wh_ou_id, List<String> skuCodeList, Boolean isMqInvoice, Boolean isBigBox, Boolean isCod);

    /**
     * 模式2-创建拣货单
     * 
     * @param limit
     * @param ids
     * @param ouId
     * @param creatorId
     */
    void createPickingListMode2(boolean isSpecialPackaging, Integer minLimit, Integer limit, List<Long> ids, Long ouId, Long creatorId, Boolean isMqInvoice);


    /**
     * 查询配货清单
     * 
     * @param pickingMode
     * @param ouId
     * @param sorts
     * @return
     */
    Pagination<AllocateCargoOrderCommand> queryPickingList(int start, int pageSize, AllocateCargoOrderCommand comd, Long ouId, Sort[] sorts);

    /**
     * 查询配货清单
     * 
     * @param pickingMode
     * @param status
     * @param ouId
     * @param sorts
     * @return
     */
    List<PickingList> findPickingListByStatus(PickingMode pickingMode, PickingListStatus status, Long ouId, Sort[] sorts);

    /**
     * 确认配货清单，如果存在占用库存失败则pickinglist为失败状态，从新计算待处理sta去除取消数量
     * 
     * @param pickingListId
     * @param operatorId
     */
    void confirmPickingList(Long pickingListId, Long operatorId, Long ouid);

    /**
     * 根据配货清单查询申请单
     * 
     * @param pickingListId 配货清单ID
     * @param ouid 仓库
     * @param sorts
     * @return
     */
    List<StockTransApplicationCommand> findStaListByPickingList(Long pickingListId, Long ouid, List<Long> plist, Sort[] sorts);

    /**
     * 根据配货清单查询申请单(运营中心)
     * 
     * @param pickingListId 配货清单ID
     * @param ouid 仓库
     * @param sorts
     * @return
     */
    List<StockTransApplicationCommand> findStaListByPickingListopc(Long pickingListId, List<Long> ouid, Sort[] sorts);

    /**
     * 根据配货清单查询未核对申请单
     * 
     * @param plId
     * @param warehouseId
     * @param sorts
     * @return
     */
    List<StockTransApplicationCommand> findStaOccupiedListByPickingList(PickingMode pickingMode, Long plId, Long warehouseId, Sort[] sorts);

    /**
     * 根据配货清单查询核对申请单
     * 
     * @param plId
     * @param warehouseId
     * @param sorts
     * @return
     */
    List<StockTransApplicationCommand> findStaCheckedListByPickingList(PickingMode pickingMode, Long plId, Long warehouseId, Sort[] sorts);

    /**
     * 根据相关单据查询作业单
     * 
     * @param pickingMode 配货模式
     * @param refSlipCode 相关单据号
     * @param warehouseId 仓库
     * @param plcode 配货批编码
     * 
     * @return
     */
    StockTransApplicationCommand findStaForVerifyByRefSlipCode(String plcode, PickingMode pickingMode, String refSlipCode, Long warehouseId, List<Long> wids, Integer whStatus);

    /**
     * 根据相关单据查询大件奢侈品仓作业单
     * 
     * @param pickingMode 配货模式
     * @param refSlipCode 相关单据号
     * @param warehouseId 仓库
     * @param plcode 配货批编码
     * 
     * @return
     */
    StockTransApplicationCommand findStaForVerifyByBigRefSlipCode(String plcode, PickingMode pickingMode, String refSlipCode, Long ouId, List<Long> wids, Integer whStatus);

    /**
     * 根据相关单据查询作业单
     * 
     * @param pickingMode 配货模式
     * @param refSlipCode 相关单据号
     * @param warehouseId 仓库
     * @param plcode 配货批编码
     * 
     * @return
     */
    PickingListCommand findPLForVerifyByPLCode(PickingListCommand plCmd);


    /**
     * 核对配货清单
     * 
     * @param ouId
     * @param sorts
     * @return
     */
    Pagination<PickingListCommand> findPickingListForVerifyByCmd(int start, int pageSize, PickingListCommand cmd, Sort[] sorts);

    /**
     * 核对配货清单
     * 
     * @param ouId
     * @param sorts
     * @return
     */
    Pagination<PickingListCommand> findPickingListForVerifyByCmd1(int start, int pageSize, PickingListCommand cmd, Sort[] sorts);


    /**
     * 核对配货清单
     * 
     * @param ouId
     * @param sorts
     * @return
     */
    Pagination<PickingListCommand> findPickingListForVerifyByCmd2(int start, int pageSize, PickingListCommand cmd, Sort[] sorts);

    /**
     * 多件核对(后置送货单)
     */
    PickingListCommand findPickingListForVerifyByCodeId(PickingListCommand cmd, String iptPlCode);

    /**
     * 核对配货清单
     * 
     * @param ouId
     * @param sorts
     * @return
     */
    Pagination<PickingListCommand> findPickingListForVerifyByCmd3(int start, int pageSize, PickingListCommand cmd, Sort[] sorts);


    /**
     * 配货清单Json,大件、奢侈品仓订单
     * 
     * @param start
     * @param pageSize
     * @param plCmd
     * @param sorts
     * @return
     */
    Pagination<PickingListCommand> findPickingListForVerifyByBig(int start, int pageSize, PickingListCommand plCmd, Sort[] sorts);

    /**
     * 核对配货清单
     * 
     * @param ouId
     * @param sorts
     * @return
     */
    Pagination<PickingListCommand> findPickingListForVerifyByCmd4(int start, int pageSize, PickingListCommand cmd, Sort[] sorts);

    /**
     * 核对配货清单
     * 
     * @param ouId
     * @param sorts
     * @return
     */
    Pagination<PickingListCommand> findPickingListForVerifyByCmd1B(int start, int pageSize, PickingListCommand cmd, Sort[] sorts, List<Long> wids);

    /**
     * 核对配货清单
     * 
     * @param ouId
     * @param sorts
     * @return
     */
    Pagination<PickingListCommand> findPickingListForVerifyByCmd1opc(int start, int pageSize, PickingListCommand cmd, Sort[] sorts, List<Long> wids);

    /**
     * 根据批次号查找相关操作人和创建人
     * 
     * @return @throws
     */
    PickingListCommand findPackingByBatchCode(Long id);

    PickingListCommand findPackinglistByCode(PickingListCommand pl);

    /**
     * 配货清单查询
     * 
     * @param allocateCargoCmd
     * @param sorts
     * @return
     */
    Pagination<AllocateCargoOrderCommand> findPickingListForModel(int start, int pageSize, AllocateCargoOrderCommand allocateCargoCmd, Sort[] sorts, List<Long> plList, String ouname);

    /**
     * 快递单修改>>作业单查询
     * 
     * @param start
     * @param pageSize
     * @param expressOrderCmd
     * @param sorts
     * @return
     */
    Pagination<ExpressOrderCommand> findExpressOrderSta(int start, int pageSize, ExpressOrderCommand expressOrderCmd, Sort[] sorts, List<Long> plList, String ouname);

    /**
     * 销售出库核对
     * 
     * @param staId
     * @param trackingNo
     */
    void checkSta(Long staId, String trackingNo, String lineNo, Long userId);

    /**
     * 单件核对 fanht
     * 
     * @param staId
     * @param trackingNo
     * @param lineNo
     * @param sn
     */
    void checkSingleSta(Long staId, String trackingNo, String lineNo, String sn, Long userId);

    /**
     * 通过sta创建出库stv，占用库存
     * 
     * @param staId
     * @param creatorId
     * @return
     */
    void transChannelOcpInv(Long staId, Long creatorId);

    /**
     * 设置sta库存占用失败
     * 
     * @param staId
     */
    void setStaOccupaidFailed(Long staId);

    /**
     * 根据配货清单查询申请单
     * 
     * @param pickingListId
     * @return
     */
    List<StockTransApplication> findStaByPickingList(Long pickingListId, Long ouid);

    /**
     * 根据sta ID 查询stv line
     * 
     * @param staIds
     * @param sorts
     * @return
     */
    List<StvLineCommand> findStvLineByStaIds(List<Long> staIds, Sort[] sorts);

    void checkDistributionList(PickingMode mode, List<PackageInfo> packageInfos, Long staId, List<StaLine> staLineList, String lineNo, Long userId);

    /**
     * 初始化未交接单，check fanht
     * 
     * @param userId
     * @param ouid
     * @return
     */
    Object initOutBoundPackCheck(Long userId, Long ouid);

    // /**
    // * 初始化未交接单，check fanht
    // *
    // * @param userId
    // * @param ouid
    // * @return
    // */
    // OperationCenter initOutBoundPackCheck1(Long userId, Long ouid);

    /**
     * sta转出，扣减库存，插入中间表，返回物流主键 fanht
     * 
     * @param staId
     * @param userId
     * @param weight 重量
     * @param trackingNo
     * @param operatorId return true成功执行；false包裹更新检查，sta其他不操作
     */
    Map<String, Object> salesStaOutBoundHand(Long staId, Long userId, Boolean isHandover, Long ouid, String trackingNo, BigDecimal weight, List<StaAdditionalLine> saddlines, Boolean allOutBound,String isSkipWeight);



    /**
     * sta转出，扣减库存
     * 
     * @param staId
     * @param userId
     * @param weight 重量
     * @param trackingNo
     * @param operatorId return true成功执行；false包裹更新检查，sta其他不操作
     */
    boolean salesStaOutBound(Long staId, Long userId, Long ouid, String trackingNo, BigDecimal weight, List<StaAdditionalLine> saddlines, Boolean allOutBound,String isSkipWeight);

    /**
     * 获取未出库包裹数
     * 
     * @param staId
     */
    void findUnCheckedPackageBySta(Long staId);

    /**
     * 物流单号验证
     * 
     * @param trackingNo
     * @param staId
     * @return
     */
    Boolean checkTrackingNo(String trackingNo, Long staId);

    /**
     * 检查配货批是否完成拣货
     * 
     * @param code
     * @return
     */
    Boolean checkPickingisOver(String code, String slipCode, Long ouId);

    /**
     * 物流单号验证
     * 
     * @param lpcode
     * @param trackingNo
     * @return
     */
    Boolean checkTrackingNoByLpcode(String lpcode, String trackingNo);

    /**
     * 根据作业单查询关联快递单号
     * 
     * @param stacode
     * @return
     */
    List<String> findRelevanceTrackingno(String stacode);

    // JasperPrint printPickingListNewMode1(Long plid, Long warehouseOuid, String psize, Long
    // userId) throws JasperPrintFailureException, JRException, JasperReportNotFoundException;

    PickingListCommand getPickingListById(Long pickingListId);

    List<PickingListCommand> findPickingListByPickingId(Long plCmdId, Integer pickZoneId, Long warehouseOuid);

    Long findPickingListCheckmodeByPickId(Long plCmdId);

    VMIBackOrder findBackPrintHanderInfo(String staid);

    Long findPickingListSpecialTypeByPickId(Long plCmdId);

    String findPickingListPSizesByPickId(Long plCmdId);

    SkuSizeConfig getSkuSizeConfigById(Long id);

    String findSkuCategoriesNameById(Long id);

    String findSkuBarcodeById(Long id);

    OperationUnit findOperationUnitById(Long id);

    PickingList findPickingListById(Long id);

    List<Long> findStaListByPkId(Long pkId);

    WhInfoTimeRef findWhInfoTimeRefById(String plCode, WhInfoTimeRefBillType type);

    /**
     * 根据sku code查询库存操作记录
     * 
     * @param start
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param skuId
     * @param owner
     * @param whOuId
     * @param sorts
     * @return
     */
    Pagination<StockTransTxLogCommand> findStTxLogPageBySku(int start, int pageSize, Date startDate, Date endDate, Long skuId, String owner, InventoryCommand inventory, Long whOuId, Sort[] sorts);

    /**
     * 查询时间段库存快照记录，计算变化量
     * 
     * @param start
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param inv
     * @param srots
     * @return
     */
    Pagination<InventoryCommand> findSnapShotPageByDate(int start, int pageSize, Date startDate, Date endDate, InventoryCommand inv, Long whOuId, Long companyid, Sort[] sorts);



    /**
     * 查询某一时刻库存快照记录
     * 
     * @param start
     * @param pageSize
     * @param date
     * @param inv
     * @param whOuId
     * @param sorts
     * @return
     */
    Pagination<InventoryCommand> findSnapShotPageByPreciseTime(int start, int pageSize, Date date, InventoryCommand inv, Long whOuId, Long companyid, Sort[] sorts);

    /**
     * 打印采购上架sku信息
     * 
     * @param stv
     * @param id
     * @return
     */
    // JasperPrint printPurchaseInfo(StockTransVoucher stv, Long id);

    /**
     * 根据交货清单Id 查询交货清单明细
     * 
     * @param hoListId
     * @param sorts
     * @return
     */
    List<HandOverListLineCommand> findHoLineByHoListId(Long hoListId, Sort[] sorts);

    /**
     * 根据Id获取交接清单
     * 
     * @param id
     * @return
     */
    HandOverList getHandOverListById(Long id);

    /**
     * 
     * @param trackingNoList
     * @param ouid
     * @param idList
     * @return
     */
    Map<String, Object> hoListImportCheckTrackingNo(List<String> trackingNoList, Long ouid, List<Long> idList);

    /**
     * 检查导入快递单号的物流商是否匹配
     * 
     * @param pgList
     * @param lpcode
     * @return key : removeTrackingNo 被溢出的快递单号 ;key : package 成功导入快递单
     */
    Map<String, Object> hoListImportCheckLpcode(List<PackageInfoCommand> pgList, String lpcode);

    /**
     * 预售
     * 
     */
    Map<String, Object> hoListImportCheckPre(List<PackageInfoCommand> pgList, String lpcode);

    /**
     * 检查导入快递单号检查是否 被拆包sta一同交接
     * 
     * @param pgList
     * @param whOuId
     * @return key : removeTrackingNo 被溢出的快递单号 ;key : package 成功导入快递单
     */
    Map<String, Object> hoListImportCheckByStaStatus(List<PackageInfoCommand> pgList);

    /**
     * 物流交接单 打印
     * 
     * @param handOverList
     * @param ouid
     * @return
     */
    // JasperPrint printHandOverList(Long holid, Long ouid);

    /**
     * 根据快递单号查询sta
     * 
     * @param trackingNo
     * @return
     */
    List<StockTransApplicationCommand> findStaListByTrackingNo(Long whId, List<Long> idList, String trackingNo, Sort[] sorts);

    /**
     * 物流交接清单交接
     * 
     * @param handOverList
     * @param userId
     * @return
     */
    HandOverList handoverListhandOver(HandOverList handOverList, Long userId);

    /**
     * 自动化仓完成交接
     */
    public HandOverList autoWhHandoverListhandOver(HandOverList handOverList, Long userId);

    /**
     * 组织ouid,获取组织仓库信息
     * 
     * @param ouId
     * @return
     */
    List<WarehouseDataCommand> getWarehouseData(Long ouId);

    /**
     * 物流交接清单分页查询
     * 
     * @param start
     * @param pageSize
     * @param handOverList
     * @param id
     * @param sorts
     * @return
     */
    Pagination<HandOverListCommand> findHandOverListByPage(int start, int pageSize, Long ouid, HandOverListCommand handOverList, Sort[] sorts);

    /**
     * 运营中心——>公司
     * 
     * @param whId
     * @return
     */
    OperationUnit findCompanyOUByOperationsCenterId(Long opId);

    /**
     * 仓库——>运营中心——>公司
     * 
     * @param whId
     * @return
     */
    OperationUnit findCompanyOUByWarehouseId(Long whId);

    /**
     * 根据仓库查询库存状态列表
     * 
     * @param ouid
     * @return
     */
    List<InventoryStatus> findInvStatusByOuid(Long ouid);

    /**
     * 根据库位、sku、出入库类型查询库位上的库存状态
     * 
     * @param ttId
     * @param ouid
     * @param locationCode
     * @param skuId
     * @return
     */
    List<InventoryStatus> findInvStatusByInventory(Long ttId, Long ouid, String locationCode, Long skuId, String owner);

    /**
     * 库存操作日志查询
     * 
     * @param start
     * @param pageSize
     * @param stock
     * @param sorts
     * @return
     */
    Pagination<StockTransTxLogCommand> findStockTransTxLogByPage(int start, int pageSize, StockTransTxLogCommand stock, Long whouid, Long companyid, Sort[] sorts);

    /**
     * 创作自定义出库作业单
     * 
     * @param ttId TransactionType id
     * @param user 用户
     * @param wh 组织
     * @param owner 货主
     * @param memo 备注
     * @param stvLineCmd
     * @param execute 执行动作true false
     * @return
     */
    StockTransApplication createOthersInOrOutBoundSta(String slipCode, boolean isExcel, Long ttId, User user, StaDeliveryInfo stadelivery, OperationUnit wh, String owner, String memo, List<StvLineCommand> stvLineCmd, boolean execute, String sns)
            throws Exception;

    /**
     * 预定义入库创建
     * 
     * @param isExcel
     * @param transactionCode
     * @param user
     * @param wh
     * @param owner
     * @param memo
     * @param stvLineCmd
     * @return
     * @throws Exception
     */
    StockTransApplication createPredefinedIn(boolean isExcel, Long transactionType, User user, OperationUnit ou, String owner, String memo, List<StaLineCommand> staLineCmd) throws Exception;

    /**
     * 获取导出名称
     * 
     * @param pickingListId
     * @return
     */
    String getExportDispatchListInvoiceFileName(Long pickingListId);

    /**
     * 自定义出库出入库操作
     * 
     * @param staId
     * @param userId
     */
    void othersStaInOutbound(Long staId, Long userId, Long ouid, String sns) throws Exception;



    List<StockTransApplicationCommand> findRuleCodeByPickingId(Long pickinglistId);

    /**
     * 仓库其他出入库作业查询
     * 
     * @param start
     * @param pageSize
     * @param type
     * @param ouid
     * @param createDate
     * @param endCreateDate
     * @param startDate
     * @param offStartDate
     * @param staCommand
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> findElseComeInAndGoOut(int start, int pageSize, Long companyid, Date createDate, Date endCreateDate, Date startDate, Date offStartDate, StockTransApplicationCommand staCommand, Sort[] sorts);

    /**
     * 库位编码是否有效
     * 
     * @param locationCode
     * @param id
     * @return
     */
    WarehouseLocation findLocationByCode(String locationCode, Long ouid);

    /**
     * 仓库其他出入库详细作业查询 ff
     * 
     * @param whOuId
     * @return
     */
    Pagination<StaLineCommand> findStaLineOthersOperate(int start, int pageSize, Long staId, Sort[] sorts);

    /**
     * id
     * 
     * @return
     */
    List<StaLineCommand> operationOthersOperateQueryDetails2(Long staId);

    WarehouseLocationCommand checkLocationTranstypeByCode(String locationCode, Long ouid);

    /**
     * 根据条件查询作业单
     */
    Pagination<StockTransApplicationCommand> findStaList(int start, int size, Long ouId, Date createTime, Date endCreateTime, Date finishTime, Date endFinishTime, Date orderCreateTime, Date toOrderCreateTime, StockTransApplicationCommand sta, Sort[] sorts);

    /**
     * 根据条件查询作业单
     */
    Pagination<StockTransApplicationCommand> findHistoricalOrderList(int start, int size, Long ouId, Date createTime, Date endCreateTime, Date finishTime, Date endFinishTime, Date orderCreateTime, Date toOrderCreateTime, StockTransApplicationCommand sta,
            Sort[] sorts, Boolean noLike, List<String> cityList, List<String> priorityList);

    /**
     * 根据条件查询作业单2
     */
    Pagination<StockTransApplicationCommand> findHistoricalOrderList2(int start, int size, Long ouId, Date createTime, Date endCreateTime, Date finishTime, Date endFinishTime, Date orderCreateTime, Date toOrderCreateTime,
            StockTransApplicationCommand sta, List<String> cityList, List<String> priorityList, Sort[] sorts);

    /**
     * 根据条件查询特殊处理作业单作业单
     */
    Pagination<StockTransApplicationCommand> findEspStaList(int start, int size, Date createTime, Date endCreateTime, Long ouId, StockTransApplicationCommand sta, Sort[] sorts);

    Pagination<StockTransApplicationCommand> findCreateOutOrder(int start, int size, Long ouId, Date createTime, Date endCreateTime, StockTransApplicationCommand sta, Sort[] sorts, List<Long> list);


    /**
     * 根据条件查询作业单
     */
    Pagination<StockTransApplicationCommand> queryPredefinedOutStaList(int start, int size, Long ouId, StockTransApplicationCommand sta, Sort[] sorts);

    /**
     * 保税仓出库查询
     */
    Pagination<CustomsDeclarationDto> queryBaoShuiOutStaList(int start, int size, Long ouId, StockTransApplicationCommand sta, Sort[] sorts);

    
    /**
     * 该仓库库位是否有此作业类型
     * 
     * @param locationId
     * @param transtypeId
     * @return
     */
    public Integer findLocationTranstype(Long locationId, Long transtypeId);

    Pagination<StockTransApplicationCommand> findToTransSupportSalesPendingStaListPage(int start, int pageSzie, String province, Boolean isSkuSn, Date fromDate, Date toDate, String shopId, Integer isNeedInvoice, StockTransApplication sta, Long wh_ou_id,
            Sort[] sorts);

    boolean isSTAFinishedForPurchase(Long staId);

    /**
     * 获取SKU数量
     * 
     * @param barcode
     * @param jmcode
     * @param keyProperties
     * @return
     */
    int findCountSkuByParameter(String barcode, String jmcode, String keyProperties, Long customerId);



    /**
     * 根据sta 出入库方向查询 stv line
     * 
     * @param staId
     * @param direction
     * @param sorts
     * @return
     */
    List<StvLineCommand> findStvLineByDirection(Long staId, Integer direction, Sort[] sorts);

    Pagination<StvLineCommand> findStvLineByDirectionByPage(int start, int pageSize, Long staId, Integer direction, Sort[] sorts);

    /**
     * 查询待处理库内移动作业单
     * 
     * @param ouId
     * @param sort
     * @return
     */
    List<StockTransApplicationCommand> findExecuteTransitInnerSta(StockTransApplicationCommand staCom, Long ouId, Boolean isExpIn, Sort[] sort);

    Pagination<StockTransApplicationCommand> findExecuteTransitInnerStaByPage(int start, int pageSize, StockTransApplicationCommand staCom, Long ouId, Boolean isExpIn, Sort[] sort);

    /**
     * 查询待处理库存个状态修改作业单
     * 
     * @param ouId
     * @param sort
     * @return
     */
    List<StockTransApplicationCommand> findExecuteInvStatusChangeSta(StockTransApplicationCommand staCom, Long ouId, Sort[] sort);

    /**
     * 查询待处理库存个状态修改作业单
     * 
     * @param ouId
     * @param sort
     * @return
     */
    List<StockTransApplicationCommand> findExecuteMoveSta(StockTransApplicationCommand staCom, Integer type, Long ouId, Sort[] sort);

    /**
     * 创建库内移动作业单，并占用库存
     * 
     * @param userId
     * @param ouid
     * @param list
     */
    StockTransApplication createTransitInnerSta(boolean isExcelImport, String slipCode, String memo, Long userId, Long ouid, List<StvLine> list);

    /**
     * 创建库存状态修改作业单，并占用库存
     * 
     * @param userId
     * @param ouid
     * @param list
     */
    StockTransApplication createInvStatusChangeSta(boolean isExcelImport, String memo, Long userId, Long ouid, List<StvLine> list);

    /**
     * 根据staid获得 staline列表
     * 
     * @param staId
     * @return
     */
    List<StvLineCommand> findStvLineByStaId(Long staId);

    /**
     * 获取库位信息
     * 
     * @param locationCode
     * @param ouId
     * @return
     */
    WarehouseLocationCommand findLocationByLocationCode(String locationCode, Long ouId);

    /**
     * 根据sta获取库内移动的信息
     * 
     * @param staId
     * @return
     */
    StockTransApplicationCommand findStaByStaId(Long staId);


    /**
     * 库位查询 联想
     * 
     * @param code
     * @param ouId
     */
    List<WarehouseLocation> findLocationByLikeCode(String code, Long ouId);

    /**
     * 商品查询 联想
     * 
     * @param code
     * @param ouId
     */
    List<Sku> findSkuByLikeCode(String code, Long ouId);

    /**
     * 根据库位查询商品列表
     * 
     * @param locationId
     * @return
     */
    List<InventoryCommand> findByLocationCode(String locationCode, Long shopId, Long ouid);

    void removeInventory(StockTransApplication sta, StockTransVoucher stv);

    /**
     * 根据条件获取商品数量
     * 
     * @param locationCode
     * @param skuId
     * @param statusId
     * @param shopId
     * @param qty
     * @return
     */
    List<InventoryCommand> findByLocationAdnSku(String locationCode, String skuCode, Long skuId, Long statusId, Long shopId, Long qty, Long ouid);

    /**
     * 根据条件获取商品数量，不打店铺分组
     * 
     * @param locationCode
     * @param skuId
     * @param statusId
     * @param shopId
     * @param qty
     * @return
     */
    List<InventoryCommand> findByLocationAdnSku(String locationCode, String skuCode, Long statusId, Long shopId, Long qty, Long ouid);

    /**
     * 根据skuCode获取 库位 和商品数量
     * 
     * @param skuCode
     * @param shopId
     * @param ouid
     * @return
     */
    List<InventoryCommand> findBySkuCode(String skuCode, Long shopId, Long ouid);

    /**
     * 根据库上的商品查询库存状态
     * 
     * @param skuId
     * @param locationId
     * @return
     */
    List<InventoryCommand> findByLocAndSku(String locationCode, String skuCode, Long shopId, Long skuId, Long ouid, Long inventoryStatusId, String productionDate, String expireDate);

    /**
     * 库位数量验证
     * 
     * @param locationCode
     * @param shopId
     * @param skuId
     * @param statusId
     * @param qty
     * @param ouid
     * @return
     */
    Boolean checkTransitInnerQty(String locationCode, Long shopId, Long skuId, Long statusId, Long qty, Long ouid);



    /**
     * 根据盘点批查询盘点差异
     * 
     * @param invCkId
     * @return
     */
    Pagination<InventoryCheckDifferenceLineCommand> findInvCkDifLineByInvCkId(int start, int pageSize, Long invCkId, Sort[] sort);


    /**
     * 确认盘点差异
     * 
     * @param confirmUser
     * @param invCkId
     */
    void confirmInventoryCheckEbs(String confirmUser, Long invCkId, User user, boolean checkUser) throws Exception;

    /**
     * 盘点库位列表
     * 
     * @param invcheckid
     * @return
     */
    Pagination<InventoryCheckLineCommand> findinvCheckLineDetial(int start, int pageSize, Long invcheckid, Sort[] sorts);

    Pagination<InventoryCheckDifTotalLineCommand> findVMIinvCheckLineDetial(int start, int pageSize, Long invcheckid, Sort[] sorts);

    Pagination<InventoryCheckDifferenceLineCommand> findInvCheckCountNoLocation(int start, int pageSize, Long invCkId, Sort[] sort);

    /**
     * 
     * @param invcheckid
     * @return
     */
    InventoryCheckCommand findInventoryCheckById(Long invcheckid);


    Pagination<PackageInfo> findByTrackingNoAndLpCode1(int start, int pageSize, String trackingNo, String lpCode, Long ouId, Sort[] sorts);

    List<BetweenLabaryMoveCommand> mergeEqualsMoveSku(List<BetweenLabaryMoveCommand> result, Map<String, Sku> skuMap);

    /**
     * VMI调拨申请单
     * 
     * @param sta
     * @param user
     * @param ou
     * @return
     * @throws Exception
     */
    StockTransApplication createVMIFlittingSta(StockTransApplication stacode, VMIFlittingCommand comd, User user, OperationUnit ou, List<VMIFlittingCommand> staLineCmd) throws Exception;

    void saveInventoryForOccupy(InventoryOccupyCommand cmd, Long qty, String wooCode);

    // 验证占用量
    void validateOccupy(Long staId);

    /**
     * 根据类型查找STA
     * 
     * @param type
     * @param wh 仓库
     * @return
     */
    List<StockTransApplicationCommand> findOutOfCossStaNotFinishedListByType(StockTransApplicationType type, OperationUnit wh, Sort[] sorts);

    void sfCancelOrder(SfOrderCancelQueue q, TransSfInfo t, Long ouid);

    void sfCancelOrderAddCount(SfOrderCancelQueue q);

    /**
     * 盘点批列表（新建和差异未处理状态的）
     * 
     * @param id
     * @param sorts
     * @return
     */
    List<InventoryCheck> findInventoryCheckList(Long id, Sort[] sorts);

    List<InventoryCheckCommand> findInventoryCheckListByType(Long ouid, Integer type, Sort[] sorts);

    List<InventoryCheck> findInventoryCheckListByTypes(Long ouid, Sort[] sorts);

    List<InventoryCheck> findInventoryCheckListManager(Long ouid, InventoryCheck invcheck, Sort[] sorts);

    /**
     * 获取作业类型
     * 
     * @param ouid
     * @return
     */
    List<TransactionType> findTransactionListByOu(Long ouid);

    /**
     * 库间移动作业单查询
     * 
     * @param sta
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> findStaForTransitCrossByModel(int start, int pageSize, StockTransApplication sta, Sort[] sorts);

    /**
     * 根据staid获取stvLind
     * 
     * @param staId
     * @return
     */
    List<StvLine> findStvLineListByStaId(Long staId);

    public List<StvLineCommand> findStvLinesListByStaId(Long staId);

    /**
     * 释放库间移动库存占用
     * 
     * @param staId
     */
    void releaseLibaryInventoryByStaId(Long staId, Long userId);

    /**
     * 
     * @param ouid
     * @param sorts
     * @return
     */
    List<HandOverListCommand> findHandOverListTotal(Long ouid, Sort[] sorts);

    /**
     * 查询须要核对的交接清单
     * 
     * @param start
     * @param pageSize
     * @param ouid
     * @param sorts
     * @return
     */
    List<HandOverListCommand> queryCheckHandOverList(int start, int pageSize, Long ouid, Sort[] sorts);

    /**
     * 根据商品编码查询库存成本
     * 
     * @param code
     * @param cmpId
     * @return
     */
    SkuCommand findSkuCostByCode(String code, Long cmpId);

    InventoryCheck createSkuGroup(String skuCode, Long qty, Long shopId, Long invStatusId, String locCode, BigDecimal skuCost, List<SkuCommand> skuList, User user, Long ouId, boolean isGroup);

    List<InventoryCommand> findOwnerListByInv(Long ouid);

    List<InventoryCommand> findBrandListByInv(Long ouid);

    /**
     * 
     * @param ouId
     * @param locationCode
     * @return
     */
    List<WarehouseLocation> findAvailLocations(Long ouId, String locationCode);

    /**
     * 
     * 模糊查找配对的仓库code
     * 
     * @param ouId 当前仓库
     * @param type 绑定采购入库作业类型的库位
     * @param code 主要用来做校验时的code参数
     * @param rowMapper
     * @return
     */
    List<WarehouseLocation> findMateWLListbyCode(Long ouId, Long tractiontype, String locationCode);

    /**
     * 
     * 模糊查找配对的仓库code
     * 
     * @param ouId 当前仓库
     * @param type 绑定采购入库作业类型的库位
     * @param code 主要用来做校验时的code参数
     * @param owner 店铺
     * @param rowMapper
     * @return
     */
    List<WarehouseLocation> findMateWLListbyOwnerCode(Long ouId, Long tractiontype, String locationCode, String owner);

    /**
     * 
     * @param code
     * @return
     */
    StockTransApplication findStaByCode(String code);

    /**
     * 获取商品信息
     * 
     * @param start
     * @param pageSize
     * @param skuCom
     * @param sorts
     * @return
     */
    Pagination<SkuCommand> findSkuAll(int start, int pageSize, SkuCommand skuCom, Sort[] sorts);

    /**
     * 根据 skuid 获取sku
     * 
     * @param skuId
     * @return
     */
    Sku findSkuBySkuId(Long skuId);

    /**
     * 设置sku 条码
     * 
     * @param skuId
     * @return
     */
    Sku updateSkuBarCode(Long skuId, String barCode);

    /**
     * 设置sku 条码
     * 
     * @param skuId
     * @return
     */
    void updateSkuBarCodes(Long skuId, List<SkuBarcode> skuBarcode);

    /**
     * 根据 skuid 获取sku
     * 
     * @param skuId
     * @return
     */
    List<SkuBarcodeCommand> findSkuBarcodeBySkuId(Long skuId);

    /**
     * 库间移动 占用打印
     * 
     * @param staid
     * @param ouid
     * @return
     */
    // JasperPrint PrintInventoryOccupay(Long staid, Long ouid, boolean other);

    // /**
    // * 过仓更新前置单据状态
    // *
    // * @param soCode
    // */
    // void salesStaCreateUpdateSlipCode(String soCode, Long whId);

    /**
     * 商品条码打印
     * 
     * @param skuId
     * @param id
     * @return
     * @throws JasperReportNotFoundException
     * @throws JasperPrintFailureException
     */
    // JasperPrint printSkuBarcode(Long skuId) throws JasperReportNotFoundException,
    // JasperPrintFailureException;

    // List<JasperPrint> printSkuBarcode(Long skuId,Long count) throws
    // JasperReportNotFoundException, JasperPrintFailureException;

    /**
     * 查询指定库存状态是否有库存
     * 
     * @param invStatusId
     * @return
     */
    Integer findInventoryCountByStatusId(Long invStatusId);

    /**
     * 查询销售作业单
     * 
     * @param start
     * @param pageSize
     * @param sta
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> queryStaExport(int start, int pageSize, Date createTime, Date endCreateTime, Date finishTime, Date endFinishTime, StockTransApplicationCommand sta, Long ouId, Sort[] sorts);

    /**
     * 查询批量发票信息和物流打印
     * 
     * @param start
     * @param pageSize
     * @param sta
     * @param sorts
     * @return
     */
    Pagination<WmsInvoiceOrder> queryInvoiceOrderExport(int start, int pageSize, Date createTime, Date endCreateTime, Date finishTime, Date endFinishTime, WmsInvoiceOrder sta, Sort[] sorts);

    /**
     * 判断销售单是否被导出过税控发票
     * 
     * @param staId
     * @return
     */
    boolean staIsExport(Long staId);

    /**
     * 查询盘点SN号差异
     * 
     * @param icid
     * @return
     */
    Pagination<InventoryCheckDifferenceSnLineCommand> findSnDiffLineByIC(int start, int pageSize, Long icid, Sort[] sorts);

    /**
     * 查询盘点SN号商品差异
     * 
     * @param icid
     * @return
     */
    Pagination<InventoryCheckDifferenceSnLineCommand> findSnDiffLineSkuChangeByIc(int start, int pageSize, Long icid, Sort[] sorts);

    /**
     * 根据条件查询sn的 sku
     * 
     * @param sn
     * @param status
     * @param ouid
     * @return
     */
    SkuSnCommand findSn(String sn, SkuSnStatus status, Long ouid);

    /**
     * 根据sta插入包裹信息
     * 
     * @param sta
     */
    String insertPackageBySta(StockTransApplication sta);

    /**
     * 查询退换货作业单
     * 
     * @param start
     * @param pageSize
     * @param sta
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> queryExcStaExport(int start, int pageSize, Date createTime, Date endCreateTime, Date finishTime, Date endFinishTime, StockTransApplicationCommand sta, Long ouId, Sort[] sorts);

    Pagination<SkuSnLogCommand> findSnPoSoLog(int start, int page, SkuSnLogCommand cmd, Long ouid);

    Pagination<SkuSnLogCommand> findSnLog(int start, int page, SkuSnLogCommand cmd, Long ouid);

    /**
     * 获取sta税控发票导出文件名
     * 
     * @param staId
     * @return
     */
    String findExportFileNameBySta(Long staId);

    /**
     * 查询供应商名称
     * 
     * @param plid
     * @return
     */
    String findexpNameByPlid(Long plid);

    /**
     * 外部订单创建sta
     * 
     * @param sta
     * @param stalines
     */
    StockTransApplication createStaForOutSalesOrder(StockTransApplication sta, List<StaLine> stalines);

    /**
     * 通过id查询配货批次号
     * 
     * @param plid
     * @return
     */
    PickingList getPickingListByid(Long plid);

    /**
     * 根据作业sta获取关系物流包裹快递单号
     * 
     * @param staId
     * @return
     */
    List<PackageInfo> findPackageInfoListByStaId(Long staId);


    /**
     * 根据sta查询出库sn号
     * 
     * @param staid 本次入库STA id
     * @return
     */
    List<SkuSnLogCommand> findOutboundSnBySta(Long staid);

    /**
     * 是否已经导入SN号
     * 
     * @param stvId
     * @return
     */
    boolean isImportSnByStv(Long staId);

    /***
     * 根据sta查询sn表中的sn记录数量
     * 
     * @param staID
     * @return
     */
    Long findSnSkuQtyByStaId(Long staID, Long ouid);

    /**
     * 查询配货批
     * 
     * @param id
     * @return
     */
    PickingList findPackinglistById(Long id);

    /**
     * 库间移动确认收货
     * 
     * @param staId
     * @param userId
     */
    StockTransVoucher createTransCrossInboundStv(Long staId, Long userId, StockTransVoucher stv, Long ouId);

    /**
     * PDA 日志查询
     * 
     * @param staId
     * @param cmd
     * @param sorts
     * @return
     */
    Pagination<PdaPostLogCommand> findPdaPostLogBySta(int start, int pageSize, Long staId, PdaPostLogCommand cmd, Sort[] sorts);

    /**
     * PDA通过编码查询单据
     * 
     * @param code
     * @return
     */
    StockTransApplication pdaFindByCode(String code);

    int predefinedOutExecution(Long staId, User creator);

    void predefinedOutOccupation(Long staId, User creator);

    void predefinedOutOccupationInv(StockTransApplication sta, StockTransVoucher stv, boolean isAuto);

    /**
     * PDA查询单据中商品
     * 
     * @param code
     * @param skucode
     * @return
     */
    String pdaFindBySku(String code, String skubarcode);

    Inventory getAddlineSkuCache(String barcode, Long companyid, Long customerId);

    /**
     * 记录PDA日志
     * 
     * @param locationCode
     * @param skuBarCode
     * @param code
     * @param pdaCode
     * @param createTime
     * @param type
     */
    void createPdaPostLog(String locationCode, String skuBarCode, String code, String pdaCode, Date createTime, PdaPostLogType type, Long qty);

    void deleteAllPdaPostLog(String code, Long userid);

    void updatePickinglistToFinish(Long id, Long ouid, Long userId);

    void updatePickinglistToFinish2(Long staid, Long plid, Long ouid);

    public void exportDailyClosingStock(Long shopId, boolean isShowZero, String station, Date date, String dir, String locko);

    WarehouseLocationCommand checkLocationTranstypeByCode(String locationCode, Long stvid, Long ouid);

    /**
     * PDA上架
     * 
     * @param staId
     * @param stvId
     * @param userId
     * @param isFinish
     */
    void pdaInbound(Long staId, Long userId, boolean isFinish);

    /**
     * get stv by PrimaryKey
     * 
     * @param stvid
     * @return
     */
    StockTransVoucher findStvById(Long stvid);

    InventoryCheckCommand findVMIICById(Long invcheckid);

    /**
     * 销售出库发送短信
     * 
     * @param sta
     */
    void sendSms(StockTransApplication sta);


    void confirmVMIInvCKAdjustment(InventoryCheck ic);

    List<ChooseOption> queryPadcodeOperate(String code);

    void deletePadcodeOperate(List<Long> ids);

    void resetInvoiceExecuteCount(Long plid, Long staid);

    void savePadcodeOperate(String code);

    Pagination<StockTransApplicationCommand> findVMITransferStaPage(int start, int pagesize, Long id, Long companyid, Date createDate, Date endCreateDate, Date startDate, Date offStartDate, StockTransApplicationCommand staCommand, Sort[] sorts);

    void executeVmiTransferOutBound(Long staID, Long userid, Long ouid) throws Exception;

    List<StaLineCommand> findVmiTransDetails(Long staID);

    Pagination<StockTransApplicationCommand> findVMIFlittingStas(StockTransApplication sta, int start, int pagesize, Long ouid, int type, int status, Sort[] sorts);

    List<StaLineCommand> findVmiUnfreezeDetails(Long staid);

    void executePostPdaLog(Long staid, Long ouid);

    void deletePostPdaErrorLog(Long staid, Long ouid, Long userid);

    Pagination<StockTransApplicationCommand> findPdaPurchaseStas(int start, int pageSize, Long ouid, Date createDate, Date endCreateDate, StockTransApplicationCommand staCommand, Sort[] sorts);

    Pagination<StaLineCommand> findPdaPurchaseDetailByStaId(int start, int pageSize, Long staId, Sort[] sorts);


    Long pdaInboundPurchase(Long staId, String invoiceNumber, Double dutyPercentage, Double miscFeePercentage, String pdaCode, Long userId, Long comId, boolean isFinish);

    List<PdaPostLogCommand> queryPdaCodeByStaId(Long staId);

    /**
     * 根据staid 查询当前作业单需要sn的sku数量
     * 
     * @param staid
     * @param ouid
     * @return
     */
    Integer findIsNeedSnByStaId(Long staid, Long ouid);

    // /**
    // * 定时刷新耗材商品缓存
    // */
    // public void refushAddLineSkuMap();

    void vmiReturnFeedback(StockTransApplication sta);

    void vmiReturnFeedbackPf(StockTransApplication sta);

    /**
     * IDS NIKE 利丰推送
     * 
     * @param sta
     * @param string
     */
    void vmiReturnFeedbackNike(StockTransApplication sta, String string);

    Pagination<StockTransApplicationCommand> findVMIReturnSta(int start, int pageSize, Long ouid, Date startTime, Date endTime, StockTransApplicationCommand staCommand, Sort[] sorts);

    List<StaLineCommand> findVmiReturnDetails(Long staID);



    /**
     * 外包仓出库反馈VMI退大仓，VMI转店退仓修改允许部分退IDS除外 KJL
     * 
     * @param staId 作业单ID
     * @param ouId 仓库组织ID
     * @param msgId 反馈数据ID
     */
    void executeVmiReturnOutBoundAllAndPart(Long staId, Long ouId, Long msgId);

    void vmiSalesCreatePage(Long ouid, String trackingNo, BigDecimal weight, Long staId, Long creatorId, String lpCode, MsgRtnOutboundCommand msg);

    MsgRtnOutbound saveMsgRtnOutbound(MsgRtnOutbound rtnOutBound);

    MsgRtnOutboundLine saveMsgRtnOutboundLine(MsgRtnOutboundLine rtnOutBound);

    List<MsgRtnOutbound> findVmiMsgOutboundByStaCode(String staCode);



    void updateMsgRtnOutbound(Long id, int status);

    void updateMsgRtnOutboundMq(Long id, int status);

    /**
     * 更新错误次数
     * 
     * @param msgId
     */
    public void updateMsgRtnOutBoundErrorCount(Long msgId);


    void transactionTypeInboundReturn(Long StaId, List<StaLine> staLine, MsgRtnInboundOrder inOrder) throws Exception;

    void purchaseReceiveStep(Long staId, List<StaLine> stalineList, MsgRtnInboundOrder inOrder);

    MsgInboundOrder msgInorder(StockTransApplication sta, Warehouse wh);

    /**
     * 保存VMI外包仓出库反馈信息
     * 
     * @param msg
     * @param source
     */
    void saveVmiWhRtnOutboundMsg(MsgRtnOutbound msg, String source);

    void generateSkuByPo(String poNum, Long shopId);


    void transactionsalesStaOutBound(Long staId);

    void vimIdsCreateCancelOrder(String staCode);

    void vmiSalesStaOutBound(Long ouid, String trackingNo, BigDecimal weight, Long staId, Long creatorId, String lpCode, MsgRtnOutboundCommand msg);

    Warehouse getWareHouseByVmiSource(String vmiSouce);

    boolean updateStaLineQuantityForPurchase(Long staId, List<StvLine> stvLines);

    StockTransApplicationStatus updateSTAForPurchase(Long staId, User operator, boolean finish);

    /**
     * 装箱清单打印 - 店铺定制 update 2012.5.31
     * 
     * @param plCmd
     * @param id
     * @return
     * @throws Exception
     */
    List<String> warehosueIsRelateShopForPrint(Long ouid);

    /**
     * 加入配货批的装箱清单配置模板是否相同判断
     * 
     * @param ouid
     * @param shoplist
     */
    List<String> warehosueShopForPrintIsSame(Long ouid, String shoplist);

    Pagination<MsgOutboundOrderCommand> findCurrentMsgOutboundOrderByPage(int start, int pageSize, MsgOutboundOrderCommand msg, Long whId, Sort[] sorts);

    Pagination<MsgOutboundOrderCommand> findCurrentMsgOutboundOrderByPageRoot(int start, int pageSize, MsgOutboundOrderCommand msg, Long ouId, Sort[] sorts);

    Pagination<MsgRtnOutboundCommand2> findCurrentMsgRtnOutboundByPage(int start, int pageSize, MsgRtnOutboundCommand2 msg, Long whId, Sort[] sorts);

    Pagination<MsgRtnOutboundCommand2> findCurrentMsgRtnOutboundByPageRoot(int start, int pageSize, MsgRtnOutboundCommand2 msg, Long ouId, Sort[] sorts);

    Pagination<MsgOutboundOrderCancelCommand> findOutboundOrderCancelList(Long ouid, MsgOutboundOrderCancelCommand recs, int start, int pageSize, Sort[] sorts);

    Pagination<MsgOutboundOrderCancelCommand> findOutboundOrderCancelListRoot(Long ouId, MsgOutboundOrderCancelCommand recs, int start, int pageSize, Sort[] sorts);

    Pagination<MsgInboundOrderCommand> findCurrentMsgInboundOrderByPage(int start, int pageSize, MsgInboundOrderCommand msg, Long whId, Sort[] sorts);

    Pagination<MsgInboundOrderCommand> findCurrentMsgInboundOrderByPageRoot(int start, int pageSize, MsgInboundOrderCommand msg, Long ouId, Sort[] sorts);

    Pagination<MsgRtnInboundOrderCommand> findCurrentMsgRtnInboundByPage(int start, int pageSize, MsgRtnInboundOrderCommand msg, Long whId, Sort[] sorts);

    Pagination<MsgRtnInboundOrderCommand> findCurrentMsgRtnInboundByPageRoot(int start, int pageSize, MsgRtnInboundOrderCommand msg, Long ouId, Sort[] sorts);

    /**
     * 根据Id获取入库反馈单
     * 
     * @param id
     * @return
     */
    MsgRtnInboundOrderCommand getMsgRtnInboundOrder(Long id);

    /**
     * 根据rtnId获取入库反馈line
     * 
     * @param id
     * @return
     */
    List<MsgRtnInboundOrderLine> findRtnLineByRtnListId(Long rtnListId, Sort[] sorts);

    // JasperPrint printOutBoundSendInfo(Long id, Long id2);

    Long warehosueIsRelateMulitShop(String shoplist, Long ouid);

    List<String> customiztationTemplShop(Long ouid);

    Pagination<WarehouseLocation> findLocationsListByOuId(int start, int pageSize, Long ouid, String locationCode, String districtCode, Sort[] sorts);


    List<String> pdaFindLocationListBySku(String code, String skucode);

    void whReplenishmentTask();

    /**
     * 退仓装箱打包--保存明细与包材
     * 
     * @param cartonId
     * @param lines 装箱明细
     * @param addls 包材
     */
    Carton packageCartonLine(Long cartonId, List<CartonLineCommand> lines, List<StaAdditionalLine> addls, BigDecimal weight, Long whouid);

    /**
     * 退仓装箱打包--保存明细与包材
     * 
     * @param cartonId
     * @param lines 装箱明细
     * @param addls 包材
     */
    Carton packageCartonLine(Long cartonId, String defectCode, List<CartonLineCommand> lines, List<StaAdditionalLine> addls, BigDecimal weight, Long whouid);

    /**
     * 更新箱状态，新建->处理中,如处理中不更新，完成抛出异常
     * 
     * @param cartonId
     */
    void updateCartonPacking(Long cartonId);

    /**
     * 查询装箱中sta的所有未完成箱
     * 
     * @param start
     * @param pageSize
     * @param cmd
     * @param sorts
     * @return
     */
    Pagination<CartonCommand> findPackingStaCartonList(int start, int pageSize, Long ouId, CartonCommand cmd, Sort[] sorts);

    /**
     * 手工录入创建交接清单
     * 
     * @param transNo
     * @param lpcode
     * @param x
     * @return
     */
    Map<String, Object> hoListCreateByHandStep1(List<String> transNo, String lpcode, Long ouid, List<Long> idList);

    void createInventoryCheckLineNative(Collection<Long> locList, InventoryCheck invCk);

    InventoryCheck createInventoryCheck(User user, OperationUnit ou, String remork, Boolean daily);

    Pagination<StockTransApplicationCommand> findOutboundPackageStaList(int start, int pageSize, StockTransApplicationCommand staCmd, Long ouid, Sort[] sorts);

    List<CartonCommand> findTrunkDetailInfoNoPage(Long staid, Long ouid, Sort[] sorts);

    Pagination<SkuCommand> findPlanExecuteDetailInfo(int start, int pageSize, Long staid, Long ouid, Sort[] sorts);

    Pagination<SkuCommand> findCompleteDetailInfo(int start, int pageSize, Long staid, SkuCommand skuCmd, Long ouid, Sort[] sorts);

    Pagination<StvLineCommand> findCancelDetailInfo(int start, int pageSize, Long staid, Long ouid, Sort[] sorts);

    Carton generateCartonByStaId(Long staid);

    // JasperPrint printOutBoundPackageInfo(Long staid) throws Exception;

    void updateCartonCreate(Long cartonId);

    void vmiReturnPacking(Long staID);

    Pagination<WarehouseLocation> findValidLocationsByouid(int start, int pageSize, String code, Long ouid, Sort[] sorts);

    Pagination<WarehouseDistrict> findValidDistrictByouid(int start, int pageSize, String code, Long ouid, Sort[] sorts);

    Pagination<InventoryCheckCommand> findSkuGroupInvCheckList(int start, int pageSize, InventoryCheckCommand invCheck, Long ouid, Sort[] sorts);

    Pagination<StvLineCommand> findSkuCollectInfo(int start, int pageSize, Long invcheckid, Long ouid, Sort[] sorts);

    Pagination<StvLineCommand> findSkuAdjustDetailInfo(int start, int pageSize, Long invcheckid, Long ouid, Sort[] sorts);



    /**
     * 
     * @param sta
     */
    void occupyInventoryForSales(StockTransApplication sta, String wooCode, boolean isNew);

    Pagination<Sku> findProductForBoxByPage(int start, int pageSize, Sku product, Sort[] sorts);

    public Pagination<StockTransApplicationCommand> pdaQueryOrderList(int start, int size, Long ouId, Date createTime, Date endCreateTime, Date finishTime, Date endFinishTime, Date orderCreateTime, Date toOrderCreateTime,
            StockTransApplicationCommand sta, Sort[] sorts);

    List<PickingListCommand> findPickingListInfo(PickingListCommand plCmd, Long ouid, int diekNo, Sort[] sorts);

    List<PickingListCommand> findPickingListInfoFast(PickingListCommand plCmd, Sort[] sorts);

    List<StaLineCommand> findPickingSku(Long pickinglistId, Long ouid, Sort[] sorts);

    List<String> validateIsSameBatch(StvLineCommand cmd);

    /**
     * 二次分拣意见 fanht
     * 
     * @param start
     * @param pageSize
     * @param plCmd
     * @param ouid
     * @param sorts
     * @return
     */
    Pagination<PickingListCommand> findPickingListInfo(int start, int pageSize, PickingListCommand pickingList, Long ouid, Sort[] sorts);


    /**
     * 配货清单拣货&分拣
     * 
     * @param start
     * @param pageSize
     * @param pickingList
     * @param ouid
     * @param sorts
     * @return
     */
    Pagination<PickingListCommand> findPickingListDiekingSeparation(int start, int pageSize, int whStatus, PickingListCommand pickingList, Long ouid, Sort[] sorts);

    /**
     * 二次分拣意见 fanht
     * 
     * @param start
     * @param pageSize
     * @param plCmd
     * @param ouid
     * @param sorts
     * @return
     */
    Pagination<PickingListCommand> findPickingListInfoB(int start, int pageSize, PickingListCommand pickingList, Long ouid, List<Long> lists, Long test, Sort[] sorts);

    /**
     * 二次分拣意见 运营中心
     * 
     * @param start
     * @param pageSize
     * @param plCmd
     * @param ouid
     * @param sorts
     * @return
     */
    Pagination<PickingListCommand> findPickingListInfoopc(int start, int pageSize, PickingListCommand plCmd, List<Long> ouid, Sort[] sorts);


    List<StaLineCommand> findStaLineByPickingIdDiek(Long pickinglistId, Long ouid, Sort[] sorts);

    /**
     * 二次分拣意见，查询配货详情 无仓库
     * 
     * @param pickinglistId
     * @param ouid
     * @param sorts
     * @return
     */
    List<StaLineCommand> findStaLineByPickingIdFast(Long pickinglistId, Sort[] sorts);

    void transitCrossPurchaseForThreePl(Long staId, List<StaLine> stalines);

    // void transitCrossPurchaseForThreePl(Long staId);

    void exeSfConfirmOrder(Long qId);

    void updateStaPickingListStatus(Long plId, PickingListStatus status);

    PickingListCommand removeTransFialedSalesSta(Long pickingListId);

    PickingList chgLpcodeByPicking(Long pickingListId, String lpcode);

    List<StaLineCommand> findStaLineDetailByStaId(Long staId, Sort[] sorts);

    void updateLineNoByOuId(List<WorkLineNo> lineNoList, Long ouid);

    void isInventoryNumber(Long staId);

    StaLine findStaLineByBarCodeOrCodeProps(Sku sku, Long staId);

    WarehouseLocation checkLocationByOuid(Sku sku, Long ouid, String code, Map<String, WarehouseLocation> locationCache);

    /**
     * 销售出库库存占用 fanht isVMITransfer false
     */
    public void createStvByStaId(Long staId, Long creatorId, String wooCode, boolean isNew);

    /**
     * 转店 fanht isVMITransfer true
     */
    public void createTransferByStaId(Long staId, Long creatorId);

    /**
     * 分拣核对详情列表查询 fanht
     * 
     * @param plId
     * @param warehouseId
     * @param status
     * @return
     */
    List<StockTransApplicationCommand> getPickCheckList(Long plId, Long warehouseId, Integer[] status);

    /**
     * O2OQS订单批量核对详情列表查询
     * 
     * @param plId
     * @param warehouseId
     * @param status
     * @return
     */
    List<StockTransApplicationCommand> getO2OQSPickCheckList(Long plId, Long warehouseId, Integer[] status);

    /**
     * O2OQS订单批量核对装箱列表查询
     * 
     * @param plId
     * @param warehouseId
     * @param status
     * @return
     */
    List<PickingListPackageCommand> getO2OQSPackCheckList(Long plId, Long warehouseId, Integer[] status);

    /**
     * 分拣核对，查询订单核对明细信息：考虑合单情况 fanht
     * 
     * @param staId
     * @return
     */
    public List<StaLineCommand> findStaLineCommandListByStaId(Long staId);

    public StaDeliveryInfoCommand findTrankNoByStaId(Long staId);

    /**
     * 分拣核对，执行核对
     * 
     * @param snlist
     * @param packageInfos
     * @param staId
     * @param ouid
     * @param lineNo
     */
    void staSortingCheck(List<String> snlist, List<GiftLine> glList, List<PackageInfo> packageInfos, Long staId, Long ouid, String lineNo, Long userId);

    /**
     * O2OQS批次分拣核对，执行核对
     * 
     * @param snlist
     * @param packageInfos
     * @param staId
     * @param ouid
     * @param lineNo
     */
    void o2oqsSortingCheck(Long plpId, List<PackageInfo> packageInfos, Long plId, List<StockTransApplication> staList, Long ouid, String lineNo, Long userId);

    /**
     * 取消已处理的作业单，直接修改核对数量
     * 
     * @param Code
     */
    void updateisCheckQty(Long staId);

    void checkisCheckQty(Long staId);

    /**
     * 二次分拣扫描商品
     * 
     * @param barcode
     */
    public Map<String, Object> twicePickingByBarcode(Long pickingId, Long staLineId, String skuBarCode, Long userId, Boolean isNeedCheck);

    /**
     * 获取二次分拣记录
     * 
     * @param pickingId
     * @return
     */
    public List<TwicePickingBarCode> findTwicePickingInfoByPickingId(Long pickingId);

    /**
     * 重置集货库位和周转箱
     * 
     * @param pId
     */
    public void resetBoxAndCollection(Long pId, Long userId,Long ouId);


    void salesCreatePage(Long ouid, Long userId, String trackingNo, BigDecimal weight, Long staId, String lpCode, String sns, String barCode);

    // /**
    // * 根据店铺查询默认仓库
    // *
    // * @param shop
    // * @return
    // */
    // Warehouse getVMIDefaultWarehouseByShop(CompanyShop shop);

    void staInboundBySlipCode(String slipCode, String locationCode);

    // vmi 退仓-占用库存 - 根据库位占用库存（没有库存状态）
    void occupyInventoryByStaId(Long staId, Long creatorId, BiChannel bi);

    // vmi 退仓-占用库存 - 根据库位占用库存（没有库存状态）支持部分占用
    void occupyInventoryByStaIdPartial(Long staId, Long creatorId, BiChannel bi, OperationUnit ou, TransactionType transactionType, StockTransVoucher stv, boolean isPartial);

    void createSnImport(String sn, int type, Long ouid, Long skuid);

    void createSnImportByBarcode(String sn, int type, String barcode, Long whouid);

    Sku checkSnBybarcode(String barCode, Long uid) throws JSONException;

    JSONObject snsimportHand(List<SkuSn> skuSns, Long uid) throws JSONException;

    /**
     * 
     */
    void updatepredefinedOutOccupation(StockTransApplication sta, List<StaLineCommand> commands);

    void managerCheckforoms(String code);

    List<StaLineCommand> findOccpiedStaLineForSkuPackageByPlId(Long plid, Long skuId, Sort[] sorts);

    Pagination<PickingListCommand> findSkuPackingPickingList(int start, int pageSize, PickingListCommand plCmd, Sort[] sorts);

    /**
     * 查询配货批次号
     * 
     * @param object
     * @param idList
     * @param code
     * @return
     */
    PickingListCommand getSingleCheckOrder(Long ouId, List<Long> idList, String code);


    /**
     * 转店
     * 
     * @param sta
     * @return
     */
    String createOwnerTransFerSta(StockTransApplication sta);

    /**
     * 出库占用SN
     * 
     * @param id
     */
    void snOccupiedForRtnOutbound(Long id);

    /**
     * 根据条件查询NIKE礼品卡作业单
     */
    Pagination<StockTransApplicationCommand> findPrintGift(int start, int size, Long ouId, Date createTime, Date endCreateTime, Date finishTime, Date endFinishTime, StockTransApplicationCommand sta, Sort[] sorts);

    /**
     * 根据staId查询NIKE礼品卡
     * 
     * @param staId
     * @param sorts
     * @return
     */
    Pagination<GiftLine> findPrintGiftLine(int start, int pageSize, Long staId, Sort[] sorts);

    void exeZtoConfirmOrder(ZtoConfirmOrderQueue q);

    /**
     * 库存明细查寻(新增保质期条件) bin.hu
     * 
     * @param start
     * @param pageSize
     * @param inv
     * @param whOuId
     * @param sorts
     * @return
     */
    Pagination<InventoryCommand> findDetailsInventoryByPageShelfLife(int start, int pageSize, InventoryCommand inv, Long whOuId, Long companyid, Sort[] sorts);

    List<InventoryCommand> findDetailsInventorySkuSum(InventoryCommand inv, Long whOuId, Long companyid);

    List<InventoryCommand> findDetailsInventoryLocationSum(InventoryCommand inv, Long whOuId, Long companyid);

    InventoryCommand findDetailsInventorySkuQty(InventoryCommand inv, Long whOuId, Long companyid);


    public ReadStatus checkPoductionDateAndExpireDate(String poductionDate, String expireDate, ReadStatus rs, String code, Sku sku, Date errorLog, int type) throws Exception;

    /**
     * 导入模板批量收货 STA
     * 
     * @return 分页操作
     */
    Pagination<StockTransApplicationCommand> findBatchStaByPagination(StockTransApplication sta, OperationUnit wh, Date startTime, Date endTime, Date arriveStartTime, Date arriveEndTime, int types, int status, int start, int size, Sort[] sorts);

    /**
     * 导入按箱收货
     * 
     * @return 分页操作
     */
    Pagination<StockTransApplicationCommand> findCartonStaByPagination(StockTransApplication sta, OperationUnit wh, Date startTime, Date endTime, Date arriveStartTime, Date arriveEndTime, int types, int status, int start, int size, Sort[] sorts);

    /**
     * 通用占用库存方法
     * 
     * @param staId
     */
    void occupyInventoryCommonMethod(Long staId);

    void creatStvCheckImg(String staCode, String fileUrl, String fileName);

    /**
     * 获取配货批次中所有作业单和运单信息
     * 
     * @param pickingListId
     * @return
     */
    List<StockTransApplicationCommand> findAllStaAndDeliveryInfoByPickingList(Long pickingListId, Long ouId);

    /**
     * 根据slip_Code查询
     * 
     * @param id
     * @return
     */
    List<AllocateCargoOrderCommand> getByslipCode(String slipCode, Sort[] sorts);

    /**
     * 查询配货批次号
     * 
     * @param object
     * @param idList
     * @param code
     * @return
     */
    PickingListCommand findSlipCodeByid(Long ouId, String code);

    /**
     * 根据作业单号修改状态和完成时间
     * 
     * @param id
     */
    void updateTypeAndFinishTimeByid(Long id, Long userId);

    /**
     * 根据组织id查找仓库的附加信息
     * 
     * @param ouid
     * @return
     */
    Warehouse getByOuId(Long ouid);

    public List<Warehouse> getIsAreaOcpInvAll();

    public InventoryCheck findInventoryCheckByCode(String code);

    /**
     * 仓库经理确认检验（是否已导入）
     * 
     * @param code
     * @param singleColumnRowMapper
     * @return
     */
    public Long managerchecknumber(String code);

    public List<SkuBarcodeCommand> findSkuBarcodeForPda(String plcode);

    public List<StaLineCommand> findOccupySkuForPda(String plcode);

    /**
     * 根据条码查找sku
     * 
     * @param barCode
     * @return
     */
    public Sku getSkuByBarcode(String barCode, Long customerId);

    /**
     * 根据传入的分类Id查看该分类下的所有商品 KJL
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    public List<Sku> getProductByCategoryId(Long id);

    /**
     * 查询给定SKU查询Sn号
     * 
     * @param string
     * 
     * @param parseLong
     * @param beanPropertyRowMapperExt
     * @return
     */
    public List<SkuSnCommand> getSkuSnBySku(String string, Long whId);

    public Sku getSkuByBarcode1(String barCode);

    public ReturnApplication getReturnByCode(String code);

    public Long findCancelCountByPickId(Long pickId);

    public List<ReturnApplicationCommand> fingReturnSkuByRaId(Long raId);

    public List<WorkLineNo> queryWorkLineNoByOuid(Long ouid);

    BiChannel getChannelVmiCode(String code);

    /**
     * 查询配货失败作业单的数据
     * 
     * @param ouid
     * @param skuInfoMap
     * @param rowMapper
     * @return
     */
    List<SkuReplenishmentCommand> findReplenishSummaryForPickingFailed(Long ouId, Map<String, Object> skuInfoMap, Long terms);

    /**
     * 通过CODE查询作业单
     * 
     * @param code
     * @return
     */
    StockTransApplication getByCode(String code);

    /**
     * 指定作业单的行列表
     * 
     * @param staId 作业单ID
     * @return
     */
    List<StaLineCommand> findStaLineListByStaIdWithSn(Long staId, Boolean isNotFinish, boolean isSnSku, boolean isDistinguishSnSku);

    void updatePLExputCountByPlId(Long plId);

    List<StvLineCommand> findRecevingMoveOutboundSuggest(Long ouId, String districtCode, String locationCode, String skuCode, String barCode, String supplierCode);

    /**
     * 查询拣货的商品存放数是否为0
     * 
     * @param ouId
     * @param skuId
     * @param r
     * @return
     */
    List<StvLineCommand> findPickingReplenishQtyBySku(Long ouId, Long skuId);

    /**
     * 根据商品查询存货区存放库位
     * 
     * @param ouId
     * @param skuId
     * @param r
     * @return
     */
    StvLineCommand findStockSuggestBySku(Long ouId, Long skuId);

    /**
     * 根据产品查询存货区存放库位
     * 
     * @param ouId
     * @param skuId
     * @param r
     * @return
     */
    StvLineCommand findStockSuggestByProduct(Long ouId, Long skuId);

    List<MsSnReportCommand> findYesterdayData();

    List<StaLineCommand> findStaLineByStaIdAndNotSNSku(Long staId, boolean isSnSku);

    List<InventoryCommand> findFlittingOutInfoByStaId(Long staId);

    /**
     * 查询单据PDA扫描统计结果
     * 
     * @param code
     * @return
     */
    List<PdaPostLogCommand> findPdaLogwithGroup(String code);

    /**
     * 当前申请单是否含有未上架的作业单,状态为Created
     * 
     * @param staId
     * @return
     */
    StockTransVoucher findStvCreatedByStaId(Long staId);

    List<StvLineCommand> findPlanExeQtyByPda(Long stvId, String code);

    InventoryCheck getInventoryCheckById(Long invCheckId);

    List<InventoryCheckDifferenceLineCommand> findCheckOverageByInvCk(Long invCkId, Long ouId, Long compId);

    List<StaLineCommand> findkuByStaIdAndIsSn(Long staId, boolean isSnSku);

    List<SkuSnLogCommand> findSNBySta(Long staid);

    /**
     * 根据pickingList id查询物流信息
     * 
     * @param plid
     * @return
     */
    List<StaDeliveryInfoCommand> findStaDeliveryInfoListByPlid(Long plid, Long ouid);

    List<StvLineCommand> findStvLineCmdByStaId(Long staId);

    List<MsgInvoice> findVMIInvoice(Date fromDate, Date endDate, String source);

    // 交接清单导出
    List<HandOverListLineCommand> findHandOverListExport(Date fromDate, Date endDate, String lpCode, List<Long> idList, Long ouId);

    // 交接清单导出原始
    List<HandOverListLineCommand> findHandOverListExport2(Date fromDate, Date endDate, String lpCode, List<Long> idList, Long ouId);

    /**
     * 物流对账信息导出
     * 
     * @param deliveryid
     * @param ouid
     * @param beanPropertyRowMapperExt
     * @return
     */
    List<PackageInfoCommand> findDeliveryInfoList(List<Long> oulist, Long deliveryid, Long ouid, Date starttime, Date endtime);

    PoCommand findPoInfo(Long staid);

    List<StvLineCommand> findPoConfirmStvLineBySta(Long staId);

    List<HandOverListLineCommand> findExportInfoByHoListId(Long hoListId, Integer status);

    List<HandOverListLineCommand> findExportInfoByHoListId2(Long hoListId, Integer status);

    StockTransApplication getStaByPrimaryKey(Long id);

    // 导出
    List<PdaPostLog> findPdaErrorLogByStaCode(String staCode);

    List<InventoryCheckDifTotalLineCommand> findvmiicLineByInvCheckIdAndQty(Long invcheckid, boolean qtyStatus);


    List<InventoryCommand> findByInventoryCheckId(Long invCkId, Boolean isSnSku);

    /**
     * 查询导出销售出库信息
     * 
     * @param ouId
     * @param beanPropertyRowMapper
     * @return
     */
    List<StockTransApplicationCommand> findExportSalesSendOutInfo(Long plId);

    /**
     * 查询导出退货原因退款信息
     * 
     * @param ouId
     * @param beanPropertyRowMapper
     * @return
     */
    List<StockTransApplicationCommand> findExportreturnRegisterInfo(Long ouId);

    List<SkuSnLogCommand> findOutboundSnByStaSlipCode(String slipCode);

    /**
     * 仓库库存报表导出
     * 
     * @param ouId
     * @return
     */
    List<InventoryCommand> findInventoryReportk(Long ouId);

    /**
     * 销售订单报表
     */
    List<SalesReportFormCommand> findSalesReportForm(Date outboundTime, Date endOutboundTime);

    /**
     * 查询该配货批次下面的相关单据与物流面单对应关系
     * 
     */
    List<StaDeliverCommand> findAllStaByPickingListId(Long id);

    /**
     * 指定作业单的行列表
     */
    List<StaLineCommand> findStaLineListByStaIdWithSn(Long staId, Boolean isNotFinish, boolean isSnSku, boolean isDistinguishSnSku, Sort[] sorts);

    List<StaLineCommand> findInBoundStaLine(Long staId, Object condition);

    /**
     * 查询存在差异的数据
     */
    List<StvLineCommand> findConfirmDiversity(Long stvId);

    /**
     * 查询上架数据
     * 
     */
    List<StvLineCommand> findInBound(Long stvId, List<Long> stvList);

    /**
     * 查询存在差异的数据
     * 
     */
    List<StvLineCommand> findInBoundIsSN(Long stvId, List<Long> stvList, Boolean isSkuSn);

    /**
     * 虚拟仓收货 查询存在差异的数据
     * 
     */
    List<StvLineCommand> findInventInBoundIsSN(Long staId, List<Long> staList, Boolean isSkuSn);

    /**
     * 销售|换货出库发票导出
     */
    List<StaInvoiceCommand> findSalesOutBoundInvoiceimport(Long ouid, String fromDate, String endDate);

    /**
     * 便利店自提订单信息
     * 
     * @param fromDate
     * @param endDate
     * @param vmiSource
     * @return
     */
    List<ConvenienceStoreOrderInfo> findConvenienceStoreOrderInfo(Date fromDate, Date endDate, Long ouId);

    /**
     * 获取所有外包仓商品关联信息
     * 
     * @param brandId
     * @param source
     * @param wId
     * @return
     */
    Pagination<SkuWarehouseRefCommand> findSkuWarehouseRefListAll(int start, int size, Long brandId, String source, String sourcewh, Long channelId, Sort[] sorts);

    SkuWarehouseRefCommand findSkuWarehouseRefList(String sourcewh);

    /**
     * 加载 品牌下拉列表 用于新建
     * 
     * @param rowMap
     * @return
     */
    public List<SkuWarehouseRefCommand> findBrandNameAll();

    /**
     * 加载店铺下拉列表 用于新建
     * 
     * @param rowMap
     * @return
     */
    public List<BiChannel> findChannelNameAll();

    /**
     * 获取品牌 （下拉框）
     * 
     * @return
     */
    public List<SkuWarehouseRefCommand> findByBrandName();

    /**
     * 获取店铺（下拉框）
     * 
     * @return
     */
    public List<SkuWarehouseRefCommand> findByChannelName();

    /**
     * 插入外包仓品牌与仓库关联数据
     * 
     * @param brandId
     * @param source
     * @param whid
     */
    void insertSkuWarehouseRef(Long brandId, String source, String sourcewh, Long channelId);

    /**
     * 获取 配货失败 缺货sku导出
     */
    List<StaErrorLineCommand> findStaFailure(Long ouid);

    /**
     * 获取相关单据号和配货清单编码
     * 
     * @param ouId
     * @param code
     * @param beanPropertyRowMapper
     * @return
     */
    PickingListCommand findSlipCodeAndPickingListCodeByStaCode(Long ouId, String code);

    /**
     * 根据SKU查询可用库存
     * 
     * @param file
     * @param ouid
     * @return
     */
    Map<String, Object> findSkuInventory(File file, Long ouid, String invowner);

    StaDeliveryInfo getVmiReturnAddress(Long id);

    void editVmiReturnAddress(Long staid, Long ouid, Long uid, StaDeliveryInfo s);

    Integer checkCartonstatus(Long staid);

    Long getVmiReturnStaIdByCarton(Long staid);

    Customer getCustomerIsAdidas(SkuCommand proCmd);

    Sku judgeSkuBarCode(SkuCommand proCmd);

    Map<String, String> judgeSkuCodeOrCustomerSkuCode(SkuCommand proCmd);

    void deleteDistributionRuleById(Long ruleId);

    List<DistributionRuleCondition> getDistributionRuleConditionList();

    List<DistributionRuleConditionCommand> getDistributionRuleConditionDetail(String groupCode);

    Integer checkRuleNameIsExist(String ruleName);

    void newDistributionRuleAndDetail(String ruleName, Long ouId, Long createId, List<Long> idList, String remark);

    void updateDistributionRuleAndDetail(String ruleName, Long ouId, Long createId, List<Long> idList, String remark);

    List<DistributionRuleDetailCommand> getDistributionRuleConditionCurrentDetail(Long ouid, Long ruleId);

    Pagination<DistributionRuleDetailCommand> getDistributionRuleConditionCurrentDetail(int start, int pageSize, Long ouid, Long ruleId, Sort[] sorts);

    /**
     * 平台对接编码下拉列表
     * 
     * @param ouid
     * @return
     */
    List<InventoryCommand> findExtCode1(Long ouid);

    /**
     * 获取可分批的订单
     * 
     * @param start
     * @param pageSize
     * @param whId
     * @param ocpSort
     */
    Pagination<StockTransApplicationCommand> findStasByOcpOrder(int start, int pageSize, String ocpBatchCode, Long whId, Boolean autoOcp);

    /**
     * 将订单设置为占用批占用中
     * 
     * @param batchCode
     * @param staBatchLimit
     */
    Integer updateStaOcpBatchCode(String batchCode, Long staBatchLimit);

    /**
     * 统计此批次订单中有多少仓库
     * 
     * @param batchCode
     * @return
     */
    List<Long> findOuIdByOcpBatchCode(String batchCode);

    /**
     * 根据参数将一定数量的订单按占用批编码设为一个批次
     * 
     * @param ocpBatchCode
     * @param ocpCode
     * @param mainWhId
     * @param autoOcp
     * @param ocpStaLimit
     * @param isAreaOcp 查询区域占用为空的数据， 用于发送mq
     * @param areaOcp 查询区域状态为3的数据，用于批次占用
     */
    List<Long> ocpStaByOcpCode(Long mainWhId, Boolean autoOcp, Integer ocpStaLimit, Integer ocpErrorQty, String areaOcp, String isYs, String areaCode);

    /**
     * 根据占用批编码处理异常占用批
     * 
     * @param ocpCode
     */
    void updateStaByOcpCode(String ocpCode, Long staId);

    BiChannel getChannelVmiCodeById(Long id);

    StockTransVoucherCommand findIsSnByStvId(Long stvId);

    /**
     * 判断订单是否有装箱
     * 
     * @param staid
     * @return
     */
    boolean judgeCarton(Long staid);

    void createWmsOtherOutBoundInvNoticeOms(Long staId, Long status, WmsOtherOutBoundInvNoticeOmsStatus wstatus);

    void updateWmsOtherOutBoundInvNoticeOms(Long staId, Long status, WmsOtherOutBoundInvNoticeOmsStatus wstatus);



    /**
     * 校验rootsta的计划量与实际收货量是否一样
     * 
     * @param id
     */
    boolean checkingRootStaCompleteQty(Long id);

    /**
     * 判断该仓库是否需要复核称重
     * 
     * @param staid
     * @return
     */
    boolean checkWeigh(Long ouId);

    /**
     * 判断所有发票是否已经批次操作
     * 
     * @return
     */
    Map<String, String> checkAllWmsInvoiceOrder(List<Long> wioIdlist, Long ouId);

    /**
     * @return
     */
    List<WmsInvoiceOrderCommand> findWmsInvoiceOrderByWioIdlist(List<Long> wioIdlist);

    /**
     * 根据完成的作业单slipcode查找是否有对应补打的发票信息
     * 
     * @param slipCode
     * @return
     */
    List<WmsInvoiceOrderCommand> findWmsInvoiceOrderBySlipCode(String slipCode, Long ouId);

    /**
     * 更新发票信息状态
     * 
     * @return
     */
    void updateWmsInvoiceOrderStatus(List<Long> wioIdlist, Long ouId);

    /**
     * 根据批次查询对应开票公司
     * 
     * @param plId
     * @return
     */
    Map<String, List<Long>> findInvoiceStaId(String[] plId);

    /**
     * 大件复核称重 /单件
     * 
     * @param staId
     * @param trackingNo
     * @param lineNo
     * @param sn
     */
    void checkSingleStaAndSalesStaOutbound(Long staId, String trackingNo, String lineNo, String sn, Long userId, Long ouId, BigDecimal weight, List<StaAdditionalLine> saddlines);

    void staSortingCheckAndsalesStaOutBoundHand(List<String> snlist, List<GiftLine> glList, String trans_no, List<PackageInfo> packageInfos, Long staId, Long ouid, String lineNo, Long userId, BigDecimal weight, List<StaAdditionalLine> saddlines);

    void vmiReturnOrderBinding(String staCode, String slipCode);

    void vmireturnRemoreOrderbinding(String staCode, String slipCode);

    /**
     * 获取顺丰确认队列
     * 
     * @param count
     * @return
     */
    List<SfConfirmOrderQueue> findExtOrder(Long count);

    void deletePackageInfo(String slipCode);

    Warehouse operationUnitQuery(Long ouId);

    Long getTranNoNumberByLpCode();

    List<Long> getAllSTOWarehouse();

    Warehouse getWarehouseByOuId(Long whId);

    List<Long> findStaByOuIdAndStatus(Long whId, List<String> lpList);

    List<Long> getAllSFWarehouse();

    /**
     * 获取顺丰确认队列优化
     * 
     * @param count
     * @return
     */
    List<Long> findExtOrderIdSeo(Long count);

    /**
     * 根据STAID获取商品条码及数量
     * 
     * @param staId
     * @return
     */
    Map<String, Long> findStalAndBarcodeByStaid(Long staId);

    /**
     * 获取批次号
     * 
     * @param plLists
     * @return
     */
    List<PickingList> queryPickingLists(String[] plLists);

    /**
     * 用户绑定仓库
     */
    void saveUserWhRef(Long userId, Long whId);

    /**
     * 根据运单号查找物流商
     * 
     * @param trackNo
     * @return
     */
    String findLpCodeByTrackNo(String trackNo);

    /**
     * 修改快递登录的状态
     */
    void updateReTrackNoStatus(Long raId, Long typeId);

    /**
     * 创建新的上架作业单
     * 
     * @param staId 作业申请单Id
     * @param stvLineList 实际收货情况，如果该列表为null，则说明采用无条件全部收货
     * @param creator 当前用户
     * @return
     */
    StockTransVoucher purchaseReceiveStepImperfect(Long staId, List<StvLine> stvLineList, List<GiftLineCommand> giftLineList, User creator, String memo, Boolean isPda, boolean isExport, Integer snType);

    /**
     * 根据类型查找STA
     * 
     * @param type
     * @param wh 仓库
     * @return
     */
    Pagination<SkuImperfectCommand> findSkuImperfect(int start, int pageSize, OperationUnit operationUnit, SkuImperfect skuImperfect, StockTransApplication sta, Sku sku, String createDate, String endDate, Sort[] sorts);

    /**
     * 残次库存查询
     * 
     * @param type
     * @param wh 仓库
     * @return
     */
    Pagination<InventoryCommand> inventoryImperfect(int start, int pageSize, OperationUnit operationUnit, StockTransApplication sta, Sku sku, Sort[] sorts);

    List<PickingListCommand> queryStaStatusByCode(String code);

    StockTransApplication queryStaByCode(String code);


    /**
     * 根据作业单staCode查询出下线包裹快递单号的相关信息
     * 
     * @param whId
     * @param pickingListId
     * @param sorts
     * @param rowMapper
     * @return
     */
    StockTransApplicationCommand findStaByOffLine(String code, long whId);

    /**
     * 保存快递单实体
     * 
     * @param whId
     * @param pickingListId
     * @param sorts
     * @param rowMapper
     * @return
     */
    Map<String, Object> saveTransOrder(TransOrder transOrder, String staIds, Long whOuId);

    /**
     * 保存与跟新包裹实体
     * 
     * @param whId
     * @param pickingListId
     * @param sorts
     * @param rowMapper
     * @return
     */

    TransPackage saveOffLineTransPackage(TransPackage transPackageDao, String brand, String skuId);

    /**
     * 分页查询线下包裹
     * 
     * @param whId
     * @param pickingListId
     * @param sorts
     * @param rowMapper
     * @return
     */
    Pagination<TransPackageCommand> getTransPackagePage(int start, int pagesize, long ouId, TransPackageCommand packageCommand, Sort[] sorts);


    /**
     * 分页查询qs商品
     */
    Pagination<QsSkuCommand> getQsSkuPage(int start, int pagesize, long ouId, QsSkuCommand QsSkuCommand, Sort[] sorts);

    /**
     * 删除商品
     */
    String delQsSkuBinding(List<String> ids, User u, OperationUnit op);


    /**
     * 查看指令明细
     */
    List<StockTransApplicationCommand> queryStas(long id, long orderId, String transNo, Sort[] sorts);

    /**
     * getOneTransPackage
     */
    TransPackageCommand getOneTransPackage(long id, String transNo);

    /**
     * getOneTransPackage明细
     */
    TransPackageCommand getOneTransPackageDetail(long id, String transNo);

    /**
     * 
     * 跟新包裹实体
     */
    void updateTransPackage(TransPackage transPackage, String skuId);


    /**
     * 根据仓库id查询出月结账号
     * 
     * @param whOuId
     * @return
     */
    StaDeliveryInfoCommand getStaDeliveryInfoCommand(long whOuId);

    /**
     * 通过编码获取店铺
     * 
     * @param whouid
     * @return
     */
    BiChannel getByCode2(String code);

    Long getCompanyIdByWarehouseOuId2(Long whId);

    TransSfInfo findTransSfInfo(Long cmpOuId);

    /**
     * 根据耗材barCode来获取体积（长*宽*高）
     */
    public Sku getVolumnByBarCode(String skuId);

    /**
     * 根据code查询店铺信息
     */
    public BiChannel getChannelByCode(String code);

    /**
     * 根据作业code查询出数量
     */
    public TransStaRecord getOneStaRecord(String code);

    /**
     * jinggang.chen 把sta状态改为 ：取消已处理/未处理 20160511
     */
    String staStateUpdate(StockTransApplicationCommand sta, Long createId, Long ouId);

    /**
     * jinggang.chen 根据staid查找一个完整的作业单信息
     */
    StockTransApplicationCommand getStaById(Long staid);

    Map<String, Object> getNum(Long userId, Long wId);

    /**
     * O2O核对配货清单
     * 
     * @param ouId
     * @param sorts
     * @return
     */
    Pagination<PickingListCommand> findPickingListForVerifyByCmdOtwoo(int start, int pageSize, PickingListCommand cmd, Sort[] sorts);

    /**
     * O2O单件核对 fanht
     * 
     * @param staId
     * @param trackingNo
     * @param lineNo
     * @param sn
     */
    Map<String, Object> checkSingleStaOtwoo(String slipCodes, Long ouId, Long userId);

    /**
     * 出库&交接，初始化未交接单 fanht
     * 
     * @param userId
     * @param ouid
     * @return
     */
    List<OutBoundPack> initOutBoundPack(Long userId, Long ouid, String lpCode);

    /**
     * getOneTransPackage2
     */
    TransPackageCommand getOneTransPackage2(TransPackage pack);

    /**
     * 查询快递单号(非完成，或不存在)
     */
    Map<String, Object> hoListImportCheckTrackingNo2(List<String> trackingNoList, Long ouid, List<Long> idList);

    /**
     * 根据运单号查询sta
     * 
     * @param trackingNo 运单号
     * @param wh_ou_id
     * @return
     */
    List<StockTransApplicationCommand> findSalesStaByTrackingNos(String trackingNo, Long wh_ou_id, List<Long> idList);

    /**
     * 方法说明：通过barCode 和 是否更新到货号 分页查询
     * 
     * @author LuYingMing
     * @date 2016年6月14日 下午8:35:47
     * @param start
     * @param pageSize
     * @param product
     * @param sorts
     * @return ProductThreeDimensionalCommand
     */
    Pagination<ProductThreeDimensionalCommand> findProductForThreeDimensionalData(int start, int pageSize, ProductThreeDimensionalCommand product, Sort[] sorts);

    /**
     * 方法说明：更新商品三维数据
     * 
     * @author LuYingMing
     * @date 2016年6月17日 上午11:43:18
     * @param id
     * @param user
     * @return
     */
    void updateThreeDimensionalData(Long skuId, ProductThreeDimensionalCommand product, User user);

    /**
     * 方法说明：根据查询货号选择及条码 查询商品三维数据
     * 
     * @author LuYingMing
     * @date 2016年6月17日 下午2:37:54
     * @return
     */
    List<ProductThreeDimensionalCommand> findProductByBarCodeWithCondition(String barCode, String isSupplierCode);

    /**
     * 方法说明：根据配货清单查询申请单状态
     * 
     * @author LuYingMing
     * @date 2016年7月12日 下午12:36:50
     * @param pickingListId
     * @param ouid
     * @param plist
     * @param sorts
     * @return
     */
    List<Integer> findStaStatusByPickingList(Long pickingListId, Long ouid, List<Long> plist);

    /**
     * 测试自动出库
     * 
     * @param staId
     * @return
     */
    boolean testAutoSalesStaOutBound(Long staId);

    /**
     * 测试查询占用作业单ID
     * 
     * @return
     */
    List<BigDecimal> testFindOcpStaIds();

    /**
     * 方法说明：校验配货清单待打印状态
     * 
     * @author LuYingMing
     * @date 2016年7月12日 下午2:43:26
     * @param ids
     * @param pickingListId
     * @param ouid
     * @param plist
     * @param sorts
     * @return
     */
    boolean verifyStatusByPickingList(String[] ids, Long ouid, List<Long> plist);

    boolean findInvoiceBySlipCode(String slipcode);

    List<StockTransApplicationCommand> getNoTransSta(Long orderCount);

    /**
     * 查询非外包仓仓库
     * 
     * @return
     */
    List<Long> findAllWarehouseByExcludeVmi();

    /**
     * 查询非外包仓仓库,且非单独的定时任务的仓库
     * 
     * @return
     */
    List<Long> findAllWarehouseByExcludeVmiNotTask();

    /**
     * 查询非外包仓、且非占用库存中的仓库
     * 
     * @return
     */
    List<Long> findAllWarehouseByStatus();

    /**
     * 修改仓库占用信息
     * 
     * @return
     */
    Integer updateWarehouseOpcStatus(Long status);

    /**
     * 判断当前时间-2分钟，是否在执行时间之前。 是：则跳出等待， 否则继续执行
     * 
     * @param mine
     * @return
     */
    Boolean ocpIsWaitByOcpStatus(Integer mine, Date exeTime);

    /**
     * 标记仓库占用信息
     * 
     * @param status
     * @param ouId
     */
    void updateIsOcpByOuId(Integer status, Long ouId);

    void updateStaDeliveryInfo(StaDeliveryInfoCommand staDeliveryInfo);

    /**
     * 根据extCode2,客户Id,店铺下面的品牌获取商品
     * 
     * @param extCode2
     * @param customerId
     * @param shopId
     * @return
     */
    Sku getByExtCode2AndCustomerAndShopId(String extCode2, Long customerId, Long shopId);


    boolean checkExpCode(String expCode);

    /**
     * 
     * 方法说明：通过sta和指定库位释放库存
     * 
     * @author LuYingMing
     * @date 2016年8月31日 下午8:40:06
     * @param staId
     * @param userId
     * @param appointStorage
     */
    void releaseInventoryByStaIdAndLocationCode(Long staId, Long userId, String appointStorage, Long ouId, String slipCode);

    /**
     * 根据物流商code查找配置信息
     * 
     * @param lpcode
     * @param newLpcode
     * @return
     */
    List<DeliveryChangeConfigure> findDCCByLpcode(DeliveryChangeConfigure changeConfigure);

    public DeliveryChangeConfigure insertDeliveryChConfing(DeliveryChangeConfigure changeConfigure);

    /**
     * 根据id删除物流变更配置
     * 
     * @param id
     */
    void deleteDeliveryChConfing(Long id);

    /**
     * 根据快递单号获取物流信息
     * 
     * @param trackingno
     * @return
     */
    StaDeliveryInfo findStaDeliveryInfoByTrackingNo(String trackingno);

    /**
     * 根据快递单号获取耗材信息
     * 
     * @param trackingNo
     * @return
     */
    Sku findSkuConsumptiveByTrNo(String trackingNo);

    /**
     * 根据快递单号获取物流变更日志
     * 
     * @param trackingNo
     * @return
     */
    DeliveryChanngeLog findDeliveryChanngeLogByTrNo(String trackingNo);

    /**
     * 根据快递单号获取包裹信息
     * 
     * @param trackingNo
     * @return
     */
    PackageInfo findPackByTrackingNo(String trackingNo);

    /**
     * 根据id获取新的运单号
     * 
     * @param id
     * @return
     */
    String getDeliveryTrackingNo(Long id);

    /**
     * 根据原始物流商获取配置信息
     * 
     * @param changeConfigure
     * @return
     */
    DeliveryChangeConfigure findDCCByLpcode1(String lpcode);

    /**
     * 保存物流变更信息
     * 
     * @param channgeLog
     * @return
     */
    DeliveryChanngeLog insertDeliverChangeLog(DeliveryChanngeLog channgeLog);

    /**
     * 根据id更新物流信息
     * 
     * @param string
     */
    StaDeliveryInfo updateStaDeliveryById(Long id, String newTrackingNo, String lpcode);


    /**
     * 根据id更新包裹信息
     * 
     * @param id
     * @param trackingNo
     * @return
     */
    PackageInfo updatePackageInfoById(Long id, String newTrackingNo);

    /**
     * 根据快递单号查询sta
     * 
     * @param trackingNo
     * @return
     */
    List<StockTransApplicationCommand> findStaListByTrackingNo1(Long whId, List<Long> idList, String trackingNo, Sort[] sorts);

    /**
     * 查找仓库区域
     * 
     * @param zoonId
     * @return
     */
    Zoon findZoonById(Long zoonId);

    /**
     * 
     * 方法说明：在库商品效期(条件)查询
     * 
     * @author LuYingMing
     * @param start
     * @param pageSize
     * @param inv
     * @param whOuId
     * @param sorts
     * @return
     */
    Pagination<InventoryCommand> findValidityAdjustByPage(int start, int pageSize, InventoryCommand inv, Long whOuId, Sort[] sorts);

    Pagination<StaCartonCommand> getPdaDetail(int start, int size, Long staId, Sort[] sorts);

    Pagination<StaOpLogCommand> getPdaSNDetail(int start, int size, Long staId, Sort[] sorts);

    Pagination<StaOpLogCommand> showShelvesDetail(int start, int size, Long staId, Sort[] sorts);

    // 分页查询 esprit 转店
    Pagination<EspritStoreCommand> findEspritStoreByParams(int start, int size, Long ouId, EspritStoreCommand esprit, Sort[] sorts);

    // 保存esprit
    String saveEsprit(EspritStoreCommand esprit);

    // 保存esprit
    String getStaDel(Long staId);



    // 删除 esprit
    String delEsprit(List<Long> ids);



    SkuModifyLog refreshSkuModifyLog(Sku sku);

    StvLine createStvLine(Sku sku, String owner, WarehouseLocation loc, InventoryStatus invStatus, TransactionType tranType, Long quantity, String batchCode, Date inboundTime, Date productionDate, Integer validDate, Date expireDate, StaLine staLine,
            StockTransVoucher stv);

    PackageInfo getPackInfoById(Long id);

    /**
     * 揽件后变更物流通知物流商
     * 
     * @param lpcode
     * @param whOuId
     * @param sta
     */
    void synchroDeliveryInfo(String lpcode, String newTransNo, Long whOuId, StockTransApplicationCommand sta);

    /**
     * 导入转店商品目标店铺
     */
    public ReadStatus importSkuTargetOwner(File file, Long ouId) throws Exception;

    /**
     * 获取所有的来源店铺
     * 
     * @return
     */
    public List<TransferOwnerTargetCommand> findAllSourceOwner(Long ouId);

    /**
     * 获取所有的目标店铺
     * 
     * @param sourceOwner
     * @return
     */
    public List<TransferOwnerTargetCommand> findAllTargetOwner(String sourceOwner, Long ouId);

    /**
     * 保存
     * 
     * @param tos
     * @return
     */
    public String insertTransferOwnerSource(TransferOwnerSource tos);

    /**
     * 优先店铺列表
     * 
     * @param start
     * @param pageSize
     * @return
     */
    public Pagination<TransferOwnerSourceCommand> findTransferOwnerSource(int start, int pageSize, Long ouId);


    /**
     * 删除优先店铺配置
     * 
     * @param id
     */
    public void deleteTransferOwnerSource(Long id);

    /**
     * 商品目标店铺分配比例列表
     * 
     * @param start
     * @param pageSize
     * @return
     */
    public Pagination<TransferOwnerTargetCommand> findTransferOwnerTarget(int start, int pageSize, Map<String, Object> m);

    void deleteTransferOwnerTarget(List<Long> idList);

    /**
     * 修改目标店铺比例
     * 
     * @param totList
     */
    public void updateTransferOwnerTarget(List<TransferOwnerTargetCommand> totList);

    void updateDeliveryInfo(Long id);

    /**
     * 获取运单号 BY MQ
     * 
     * @param orderCount
     * @return
     */
    public List<Long> findNoTransStaByMq(Long orderCount);

    List<InventoryOccupyCommand> findSalesOutboundToOccupyInventoryNew(Integer saleOcpType, Long staId, String wooCode);

    void occupiedInventoryByStaToMqUpdateSta(StockTransApplication sta, String ocpCode);

    List<StockTransApplicationCommand> findStaByPickingListCode(String pickCode, Long ouId);

    void importLfSta(String staCode, String wls, String ydh, Double weight, String field, String ctnType);

    /**
     * 商品产地分页查询
     * 
     */
    Pagination<SkuCountryOfOriginCommand> getSkuCountryOfOriginPage(int start, int pageSize, Long id, SkuCountryOfOriginCommand skuCommand, Sort[] sorts);

    Pagination<StaLineCommand> getOutboundDetailList(int start, int pageSize, Long staid, Sort[] sorts);



    Pagination<StockTransApplicationCommand> findOutboundPickingTaskList(int start, int pageSize, StockTransApplicationCommand staCmd, Long ouid, Sort[] sorts);

    /**
     * 删除商品产地信息
     */
    String delSkuCountryOfOrigin(List<String> list, User u, OperationUnit op);

    /**
     * 查询批次是否是澳门件
     */
    Boolean queryIsMacaoOrder(String slipCode);

    /**
     * gucci退仓指令
     * 
     * @param start
     * @param pageSize
     * @param startTime
     * @param endTime
     * @param slipCode
     * @param toLoction
     * @return
     * @throws JSONException
     */
    public Pagination<VmiRtoCommand> findGucciRtnList(int start, int pageSize, Date startTime, Date endTime, String slipCode, String toLoction) throws JSONException;

    /**
     * gucci退仓指令明细
     * 
     * @param start
     * @param pageSize
     * @param rtoId
     * @return
     * @throws JSONException
     */
    public Pagination<VmiRtoLineCommand> findGucciRtoLineList(int start, int pageSize, Long rtoId);

    /**
     * 查询批量可冻结库存数据
     * 
     * @param skuId
     * @param locationId
     * @return
     */
    List<InventoryCommand> findInventoryBatchLockList(String locationCode, String skuCode, Long shopId, Long ouid, Long inventoryStatusId, String productionDate, String expireDate);

    /**
     * 根据库存ID查询对应可冻结数据
     * 
     * @return
     */
    List<InventoryCommand> findInventoryBatchLockListByInvIds(String invIds, Long ouid);


    public Pagination<DeliveryChanngeLogCommand> findDeliveryChanngeLogCommandList(int start, int pageSize, Sort[] sorts, Long ouId, DeliveryChanngeLogCommand deCommand);

    public StockTransApplication findSta(Long staId);


    List<PickingListCommand> getExportAgv(String plCode, String areaCode);

    // 封装agv出库信息
    void saveCommomAgvOutBound(Long staId, Long pId);

    // 取消AGV单据
    boolean cancelAgv(String staCode, Long ouId);
    
    /**
     * 保税仓出库明细查询
     */
    List<CustomsDeclarationLineCommand> queryBaoShuiOutStaLineList(Long id,String skuCode,String upc);
    
    /**
     * 更新明细
     * @param BaoShuiOutStaLineId
     * @param gQty
     */
    void  updateBaoShuiOutStaLine(Long baoShuiOutStaLineId,Long gQty);
    
    void updateBaoShuiOutSta(CustomsDeclarationDto cd);
    
    /**
     * 重新计算净重毛重
     */
    void updateWeight(Long ouId);
    /**
     * 查询补货建议数据
     * 
     * @param ouid
     * @param skuInfoMap
     * @param rowMapper
     * @return
     */
    List<SkuReplenishmentCommand> findReplenishSuggest(Long ouId, String staCode);


    /**
     * 根据仓库号查询NIKE收货-导入箱号关系
     * 
     * @return
     */
    Pagination<RelationNike> findRelationNikeByOuid(int start, int size, Sort[] sorts, Long id, RelationNike relationNike);

    /**
     * 根据实物箱号获取系统箱号
     * 
     * @return
     */
    RelationNike getSysCaseNumber(String caseNumber, Long ouId);

    /**
     * ad异常包裹
     * 
     * @return
     */
    Pagination<AdPackageLineDealDto> adPackageList(String adOrderId, String wmsOrderId, String extended, String adOrderType, Integer status, String trankNo, String lpCode, Date fromTime, Date endTime, Integer opStatus, String skuId, int start, int size,
            Sort[] sorts);

    /**
     * ad异常包裹明细
     * 
     * @return
     */
    AdPackageLineDeal adPackageDetail(Long id);


    /**
     * ad异常包裹操作日志
     * 
     * @return
     */
    Pagination<AdPackageLineDealLogDto> adPackageLog(String adOrderId, int start, int size, Sort[] sorts);

    /**
     * ad异常包裹修改
     * 
     * @return
     */
    void adPackageCommit(Long id, String wmsStatus, String remark, String userName);

    /**
     * ad异常包裹操作日志插入
     * 
     * @return
     */
    void insertAdPackageLog(Long id);

    /**
     * 获取Po按箱收货列表
     * 
     * @param start
     * @param size
     * @param ouId
     * @param createTime
     * @param endCreateTime
     * @param finishTime
     * @param endFinishTime
     * @param orderCreateTime
     * @param toOrderCreateTime
     * @param sta
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> findPoStaList(int start, int size, Long ouId, Sort[] sorts, StockTransApplicationCommand sta);

    /**
     * 查询po按箱收货明细
     * 
     * @param staId
     * @return
     */
    List<StaLineCommand> findPoLineStaList(Long staId);
    
    /* 查询仓库
     * 
     * @return
     */
    Warehouse findWareHouseById(Long ouId);

    List<StarbucksIcePackage> findStarbucksIcePackage();

    StockTransApplicationCommand findStarbucksDetail(String code);

/**
     * 查询待库内取消单据
     * @return
     */
    Pagination<WmsCancelOrder> findCancelSta(int start, int size, String staCode,String slipCode,String owner,int status,Date startTime,Date endTime,Long ouId, Sort[] sorts);

    
   /**
     * 查询待库内取消单据明细
     * @return
     */
    List<WmsCancelOrderLine> findCancelStaLine(Long id);
     /**
     * 保存库内取消单据
     */
	 void saveCancelStaInfo(WmsCancelOrder cancelOrder,List<WmsCancelOrderLine> list);
    
    /**
     * 保存并推送库内取消单据
     */
    void saveAndSendCancelStaInfo(WmsCancelOrder cancelOrder,List<WmsCancelOrderLine> list);
	
	Sku findSkuRfidByBarCode(String barCode, Long ouId);

    List<SkuRfid> findRfidResult(String skuBarCode, List<String> rfid);
}
