package com.jumbo.web.manager.print;

import java.util.List;

import com.jumbo.web.manager.BaseWebManager;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.print.PrintCustomize;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.StaSpecialExecuteType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.TransPackageCommand;

import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public interface PrintManager extends BaseWebManager {
    /***
     * 打印装箱订单
     * 
     * @param plCmd
     * @return
     */
    JasperPrint printPackingPage(Long plid, Long staid, Long userId, Boolean isPostPrint, String paperType);

    /***
     * 打印装箱订单
     * 
     * @param plCmd
     * @return
     */
    JasperPrint printPackingPageReturn(Long plid, Long staid, Long userId, Boolean isPostPrint);

    /***
     * 打印O2OQS装箱订单
     * 
     * @param plCmd
     * @return
     */
    JasperPrint printPackingPage(Long plid, Long ppId, Long staid, Long userId, Boolean isPostPrint, Boolean isSpecial, String template);


    List<JasperPrint> printExpressBillBySta1(String id, Boolean isParent, Boolean isOline, Long userId) throws Exception;

    /**
     * 打印配货清单 销售模式2
     * 
     * @throws JasperPrintFailureException
     * @throws JRException
     * @throws JasperReportNotFoundException
     * @throws Exception
     */
    public JasperPrint printPickingListMode2(PickingListCommand plCmd, Long warehouseOuid, String psize) throws JasperPrintFailureException, JRException, JasperReportNotFoundException, Exception;

    public JasperPrint printPickingListMode1(PickingListCommand plCmd, Long warehouseOuid, Long userId, String flag) throws JasperPrintFailureException, JRException, JasperReportNotFoundException, Exception;

    JasperPrint printPickingListNewMode1(Long plid, Integer pickZoneId, Long warehouseOuid, String psize, Long userId, String flag) throws JasperPrintFailureException, JRException, JasperReportNotFoundException, Exception;

    /**
     * 打印采购上架sku信息
     * 
     * @param stv
     * @param id
     * @return
     * @throws Exception
     */
    JasperPrint printPurchaseInfo(StockTransVoucher stv, Long id) throws Exception;


    /**
     * 物流交接单 打印
     * 
     * @param handOverList
     * @param ouid
     * @return
     * @throws Exception
     */
    JasperPrint printHandOverList(Long holid, Long ouid) throws Exception;

    /**
     * 新增物流交接清单打印
     * 
     * @param holid
     * @param ouid
     * @return
     * @throws Exception
     */
    JasperPrint newPrintHandOverList(Long holid, Long ouid) throws Exception;


    /***
     * 物流交接清单打印批量
     * 
     * @param plCmd
     * @return
     * @throws JasperReportNotFoundException
     */
    List<JasperPrint> newPrintHandOverList2(String hoIds, Long wId) throws JasperReportNotFoundException, JasperPrintFailureException;


    /**
     * 自动化仓打印交接清单汇总
     * 
     * @param hoId
     * @param wId
     * @return
     * @throws JasperReportNotFoundException
     * @throws JasperPrintFailureException
     */
    public List<JasperPrint> printAutoWhHandOverList(String hoId, Long wId) throws JasperReportNotFoundException, JasperPrintFailureException;

    /**
     * 库间移动 占用打印
     * 
     * @param staid
     * @param ouid
     * @return
     */
    JasperPrint PrintInventoryOccupay(Long staid, Long ouid, boolean other);

    JasperPrint printVmiReturnInfo(Long staID, Long ouid);

    /**
     * 商品条码打印
     * 
     * @param skuId
     * @param id
     * @return
     * @throws JasperReportNotFoundException
     * @throws JasperPrintFailureException
     * @throws Exception
     */
    JasperPrint printSkuBarcode(Long skuId) throws JasperReportNotFoundException, JasperPrintFailureException, Exception;


    JasperPrint printStaCode(Long staid) throws Exception;

    /**
     * 收货确认单Jasper打印
     * 
     * @param ouid
     * @return
     */
    JasperPrint printPoConfirmReport(Long staId) throws JasperPrintFailureException, JRException, JasperReportNotFoundException;

    /**
     * 收货单明细打印
     * 
     * @param ouid
     * @return
     * @throws Exception
     */
    JasperPrint printInboundDetailReport(Long staId) throws JasperPrintFailureException, JRException, JasperReportNotFoundException, Exception;

    JasperPrint printOutBoundSendInfo(Long id, Long id2, PrintCustomize printCustomize) throws Exception;

    JasperPrint printOutBoundPackageInfo(Long staid) throws Exception;

    JasperPrint printOutBoundPackingIntegrity(Long staid) throws Exception;

    JasperPrint printLocationBarCode(Long id) throws Exception;

    JasperPrint printDistrictCode(Long id) throws Exception;

    JasperPrint printDistrictRelativeLocations(Long id) throws Exception;

    /**
     * 特殊打印
     * 
     * @param giftLineId
     * @return
     * @throws Exception
     */
    JasperPrint printGift(Long giftLineId) throws Exception;

    /**
     * 
     * @param batchCode
     * @return
     * @throws Exception
     */
    JasperPrint printReturnPackage(String batchCode) throws Exception;

    /**
     * 特殊包装打印
     * 
     * @param lpId
     * @param staId
     * @return
     * @throws Exception
     */
    JasperPrint bySPTypePrint(Long staId, StaSpecialExecuteType type) throws Exception;

    /**
     * 按配货清单连打面单,有LPCODE
     * 
     * @param pickingListId
     * @return
     * @throws Exception
     */
    JasperPrint printExpressBillByPickingListLpCode(Long pickingListId, Boolean isOline, Long userId) throws Exception;

    List<JasperPrint> printExpressBillByPickingList(Long pickingListId, Boolean isOline, Long userId) throws Exception;


    /**
     * 线下按作业单打面单
     * 
     * @param staId
     * @return
     * @throws Exception
     */
    JasperPrint printOffLineExpressBillBySta(TransPackageCommand pack, Boolean isParent, Boolean isOline, Long userId, Long wid) throws Exception;


    /**
     * 按作业单打面单
     * 
     * @param staId
     * @return
     * @throws Exception
     */
    JasperPrint printExpressBillBySta(Long staId, Boolean isParent, Boolean isOline, Long userId) throws Exception;

    /**
     * 按作业单打面单
     * 
     * @param staId
     * @return
     * @throws Exception
     */
    JasperPrint printExpressBillByTrankNo(Long staId, Boolean isOline, String trankNo, Long userId) throws Exception;

    JasperPrint printImportInfo();

    JasperPrint printSingleVmiDelivery(Long staId, Boolean isOline, Long userId) throws Exception;

    /**
     * 根据箱号打印物流面单
     * 
     * @param staId
     * @param cartonId
     * @param isOline
     * @param userId
     * @return
     * @throws Exception
     */
    public JasperPrint printSingleVmiDeliveryByCarton(Long staId, Long cartonId, Boolean isOline, Long userId) throws Exception;

    List<JasperPrint> printSnCardErrorList(String staCode, String plCode, Long cmpOuid) throws Exception;

    /**
     * 打印报关清单
     * 
     * @param pickingListId
     * @return
     * @throws Exception
     */
    List<JasperPrint> printImportEntryList(Long pickingListId) throws Exception;

    /**
     * 
     * @return
     */
    List<JasperPrint> printPickingBulkListMode1(List<PickingListCommand> commands, Integer pickZoneId, Long warehouseOuid, Long userId) throws JasperPrintFailureException, JRException, JasperReportNotFoundException, Exception;

    /**
     * vmi退仓拣货单打印
     */
    List<JasperPrint> vmiBackPrint(String sid) throws JasperReportNotFoundException, JasperPrintFailureException;

    /***
     * 打印装箱订单-团购
     * 
     * @param plCmd
     * @return
     */
    List<JasperPrint> printPackingPageBulk(List<Long> plid, Long staid, Long userId, Boolean isPostPrint);


    /**
     * 发票批量打印物流单
     * 
     * @param batchNo
     * @param userId
     * @return
     * @throws Exception
     */
    List<JasperPrint> printExpressBillByWioListId(List<Long> wioListId, String batchNo, Long ouId, Long userId) throws Exception;


    /***
     * 打印物流面单-团购
     * 
     * @param plCmd
     * @return
     */
    List<JasperPrint> printExpressBillByPickingBulkList(List<Long> plIds, Boolean isOline, Long userId) throws Exception;

    /**
     * 按InvoiceOrder打面单
     * 
     * @param staId
     * @return
     * @throws Exception
     */
    JasperPrint printExpressBillByInvoiceOrder(Long wioId, Boolean isParent, Boolean isOline, Long ouId, Long userId) throws Exception;

    List<JasperPrint> printExpressBillForInvoiceOrder(List<Long> list);

    JasperPrint getJpByInvoiceOrder(Long id);

    Warehouse queryTotalPickingList(Long ouId);

    /***
     * 打印残次条码
     * 
     * @param plCmd
     * @return
     * @throws JasperReportNotFoundException
     */
    JasperPrint skuImperfect(Long imperfectId) throws JasperReportNotFoundException, JasperPrintFailureException;

    /***
     * 打印残次条码
     * 
     * @param plCmd
     * @return
     * @throws JasperReportNotFoundException
     */
    List<JasperPrint> skuImperfects(String imperfectIds) throws JasperReportNotFoundException, JasperPrintFailureException;

    /***
     * 打印残次条码
     * 
     * @param plCmd
     * @return
     * @throws JasperReportNotFoundException
     */
    JasperPrint skuImperfectStv(Long imperfectStvId) throws JasperReportNotFoundException, JasperPrintFailureException;

    /**
     * 根据周装箱条码打印
     * 
     * @param tId
     * @return
     * @throws Exception
     */
    List<JasperPrint> printTurnoverBoxBarCode(Long tId) throws Exception;

    List<JasperPrint> printImportEntryLists(Long pickingListId) throws Exception;

    List<JasperPrint> printImportHGDEntryLists(Long pickingListId, Long staId) throws Exception;

    JasperPrint printPackingSummaryForNike(Long staId, Long cartonId) throws Exception;

    /***
     * 打印O2O相关单据标签
     * 
     * @param plCmd
     * @return
     */
    JasperPrint printSlipCode(String slipCode) throws JasperReportNotFoundException, JasperPrintFailureException;

    /**
     * 打印货箱编码
     * 
     * @param staId
     * @param type
     * @return
     * @throws Exception
     */
    public List<JasperPrint> printContainerCode(Long staId, String type) throws Exception;


    /**
     * 打印相关单据号
     * 
     * @param staId
     * @return
     * @throws Exception
     */
    public List<JasperPrint> printSlipCode(Long staId) throws Exception;


    /**
     * PDA拣货条码打印
     * 
     * @return
     */
    public List<JasperPrint> printPdaBarCode(Long plId) throws Exception;


    List<JasperPrint> printExpressBillBySta5(String id, Boolean isParent, Boolean isOline, Long userId) throws Exception;

    /**
     * PDA拣货条码打印
     * 
     * @return
     */
    public List<JasperPrint> printPdaBarCodeS(String[] plIds) throws Exception;

    JasperPrint printExpressBillByTrankNo1(Long staId, Boolean isOline, String trankNo, Long userId, String newLpcode) throws Exception;

    List<JasperPrint> diekingLinePrint(String staid, String plList) throws JasperReportNotFoundException, JasperPrintFailureException;

    /**
     * PDA退仓拣货条码打印
     * 
     * @return
     */
    public List<JasperPrint> printPdaBarCodeRtw(String[] plIds) throws Exception;

    /**
     * 退仓装箱单打印
     * 
     * @return
     * @throws Exception
     */
    JasperPrint printReturnWarehousePackingInfo(Long staId, Long ouId, PrintCustomize printCustomize) throws Exception;

    /**
     * 箱标签打印
     * 
     * @param staId
     * @return
     * @throws Exception
     */
    public JasperPrint printBoxLabel(Long cartonId, PrintCustomize pc) throws Exception;

    public JasperPrint printNikeCrwLabel(Long cartonId) throws Exception;

    public JasperPrint printNikeCrwPod(Long staId, Long cartonId) throws Exception;

}
