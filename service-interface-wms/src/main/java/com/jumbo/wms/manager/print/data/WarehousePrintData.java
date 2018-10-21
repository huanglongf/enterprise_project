package com.jumbo.wms.manager.print.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jumbo.pac.manager.extsys.wms.rmi.model.SalesTicketResult;
import com.jumbo.wms.manager.print.BasePrintManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuCategories;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.jasperReport.ImportHGDEntryListObj;
import com.jumbo.wms.model.jasperReport.InventoryOccupay;
import com.jumbo.wms.model.jasperReport.OutBoundPackageInfoObj;
import com.jumbo.wms.model.jasperReport.OutBoundPackingObj;
import com.jumbo.wms.model.jasperReport.OutBoundSendInfo;
import com.jumbo.wms.model.jasperReport.PackingSummaryForNike;
import com.jumbo.wms.model.jasperReport.PickingListObj;
import com.jumbo.wms.model.jasperReport.SpecialPackagingData;
import com.jumbo.wms.model.lf.StaLfCommand;
import com.jumbo.wms.model.warehouse.Carton;
import com.jumbo.wms.model.warehouse.GiftLine;
import com.jumbo.wms.model.warehouse.HandOverList;
import com.jumbo.wms.model.warehouse.HandOverListLineCommand;
import com.jumbo.wms.model.warehouse.ImperfectStvCommand;
import com.jumbo.wms.model.warehouse.ImportPrintData;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.PoCommand;
import com.jumbo.wms.model.warehouse.SkuImperfectCommand;
import com.jumbo.wms.model.warehouse.SkuSizeConfig;
import com.jumbo.wms.model.warehouse.StaDeliveryInfoCommand;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StoProCode;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.VMIBackOrder;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WhInfoTimeRef;
import com.jumbo.wms.model.warehouse.WhTransAreaNo;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrderCommand;

public interface WarehousePrintData extends BasePrintManager {

    List<StoProCode> queryStoProCode();

    Long getPickListMergeSta(Long plid, Long ouid);

    Map<Long, PickingListObj> findPickingListByPlid(Long plid, Integer pickZoneId, Long ouid, String psize, String flag);

    Map<Long, PickingListObj> findPickingListByPlid1(Long plid, Integer pickZoneId, Long ouid, String psize, String flag);

    PackageInfo findByPackageInfoByLpcode(Long id);

    List<PackageInfo> findByPackageInfoByStaId(Long id);

    PickingList getPickingListById(Long id) throws Exception;

    SkuSizeConfig getSkuSizeConfigById(Long id);

    StockTransApplication queryStaById(Long id);

    SkuCategories getSkuCategoriesById(Long id) throws Exception;

    WhInfoTimeRef getFirstPrintDate(String slipCode, Integer billType, Integer nodeType);

    void insertWhInfoTime(String slipCode, int billType, int nodeType, Long userId, Long whOuId);

    OperationUnit getOperationUnitById(Long id) throws Exception;

    List<StvLineCommand> findPurchaseSkuInfo(Long stvid, Long ouid);

    Map<Long, OutBoundPackageInfoObj> findPrintCartonDetailInfo3(Long cid);

    List<HandOverListLineCommand> findLineDetailByHoListId(Long holid);

    HandOverList getHandOverListById(Long id) throws Exception;

    Integer findTotalSkuCountByHoId(Long hoid);

    String findExpNameByLpcode(String lpCode);

    InventoryOccupay findVmiReturnOccupyInventoryByStaId(Long staid, Long ouid);

    StockTransApplication getStaById(Long id) throws Exception;

    InventoryOccupay findInventoryOccupay(Long staid, Long ouid);

    InventoryOccupay findInventoryOccLineList(Long staid, InventoryOccupay inventoryoccupay);

    List<StvLine> findStvLineListByStaId(Long staId);

    Sku getSkuById(Long id) throws Exception;

    List<StvLineCommand> findPoConfirmStvLineBySta(Long staid);

    PoCommand findPoInfo(Long staid);

    List<StaLineCommand> findInBoundStaLineForPrint(Long staId);

    OutBoundSendInfo findOutBoundSendInfo(Long staId);

    OutBoundSendInfo findOutBoundSendInfoLine(Long staid, OutBoundSendInfo outBoundSendInfo);

    OutBoundSendInfo findGucciOutBoundSendInfoLine(Long staid, OutBoundSendInfo outBoundSendInfo);

    List<PickingListCommand> findPickingListByPickingId(Long plCmdId, Integer pickZoneId, Long warehouseOuid, String flag);

    List<VMIBackOrder> findBackListByStaId(String staid);

    List<PickingListCommand> findPickingListByPickingId3(Long plCmdId, Integer pickZoneId, Long warehouseOuid, String flag);

    Map<Long, OutBoundPackageInfoObj> findPrintCartonDetailInfo2(Long cid);

    List<OutBoundPackingObj> findOutBoundPackageByStaid(Long staid);

    List<StvLineCommand> findOutBoundPackageLineByStaid(Long staid);

    String findLocationCodeByid(Long id);

    String findDistrictCodeByid(Long id);

    WarehouseDistrict getWarehouseDistrictById(Long id) throws Exception;

    List<String> findAllAvailLocationsByDistrictId(Long id);

    GiftLine getGiftLineById(Long id) throws Exception;

    List<SpecialPackagingData> findBurberryPrintInfo(Long staid);

    int getCoachSpecialStaType(Long staid);

    SalesTicketResult getSalesTicket(String code, Integer type);

    List<StaDeliveryInfoCommand> findPrintExpressBillData(Long pickingListId);

    String findAllOptionListByOptionKey(String optionkey, String categoryCode);

    Transportator findByCode(String lpCode);

    Warehouse getByOuId(Long whOuId) throws Exception;

    HashMap<String, List<WhTransAreaNo>> transAreaCache();

    List<WhTransAreaNo> getTransAreaByLpcode(String lpcode);

    BigDecimal getInsurance(String code, BigDecimal amount);

    List<Long> findAllStaByPickingList(Long id);

    List<StaDeliveryInfoCommand> findPrintExpressBillData(Boolean isOnlyParent, Long plid, Long staid);

    List<StaDeliveryInfoCommand> findPrintExpressBillData2(Long plid, Long staid, String packId);

    List<ImportPrintData> selectAllData();

    void deleteAll(List<ImportPrintData> listInfo);

    Long getMainWarehouseId(Long staid);

    List<StaDeliveryInfoCommand> printSingleVmiDelivery(Long staid, Long cartonId);

    List<StaDeliveryInfoCommand> printCategeryAndQtyDeliveryByStaId(Long staId);

    WmsInvoiceOrder getWmsInvoiceOrderById(Long id);

    List<WmsInvoiceOrderCommand> findWmsInvoiceOrderBillData(Boolean isOnlyParent, String BatchNo, Long wioId);

    /**
     * 根据补寄发票单封装电子面单打印数据
     * 
     * @param id
     * @return
     */
    List<StaDeliveryInfoCommand> findWmsInvoiceOrderBillData(Long id);

    /**
     * 查询残次标签
     * 
     * @param id
     * @return
     */
    SkuImperfectCommand findSkuImperfect(Long id);

    /**
     * 查询残次标签
     * 
     * @param id
     * @return
     */
    ImperfectStvCommand findSkuImperfectStv(Long id);

    PackingSummaryForNike findPackingSummaryForNike(Long staId, Long cartonId);

    Long findPackingCheckCount(Long staId);

    Carton findCartonById(Long cartonId);

    /**
     * 澳门件海关单
     */
    Map<Long, ImportHGDEntryListObj> printImportMacaoHGDEntryList(Long staid);

    /**
     * 查询商品分类汇总
     */
    String queryTotalCatrgories(String slipCode);

    /**
     * 查询是否配置过AD定制打印包裹明细交接清单
     * 
     */
    Integer findIsPrintPackageDetail(Long holid, Long ouid);

    List<HandOverListLineCommand> findLineDetailByHoListIdAD(Long holid);

    /**
     * 装箱清单防伪编码
     * 
     * @param skuMsg
     * @param owner
     * @return
     */
    public String greAntiFakeCode(String skuMsg, String key);

    List<VMIBackOrder> findDiekingLineListByDiekingId(Long id, Long staId);
    
    /**
     * 获取退仓装箱信息
     * @param staId
     * @return
     */
    OutBoundSendInfo findRetrnWhPickingInfo(Long staId);
    
    /**
     * 获取退仓装箱信息明细
     * @param staId
     * @return
     */
    OutBoundSendInfo findRetrnWhPickingInfoLine(Long staId,OutBoundSendInfo outBoundSendInfo);

    /**
     * nike crw 箱标签
     * 
     * @param cartonId
     * @return
     */
    public StaLfCommand findNikeOutBoundLabel(Long cartonId);
    
    /**
     * 获取箱标签打印数据
     * @param staId
     * @return
     */
    public List<OutBoundSendInfo> findBoxLabelByCartonId(Long cartonId);
    

    /**
     * nike crw POD单
     * 
     * @param staId
     * @return
     */
    public StaLfCommand printNikeCrwPod(Long staId, Long cartonId);

}
