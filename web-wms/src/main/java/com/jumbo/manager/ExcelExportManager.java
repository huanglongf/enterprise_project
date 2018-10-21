package com.jumbo.manager;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.compensation.WhCompensationCommand;
import com.jumbo.wms.model.vmi.Default.VmiRtoLineCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.SkuReplenishmentCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StvLineCommand;

import loxia.support.excel.WriteStatus;

public interface ExcelExportManager extends BaseManager {
    
    String DELIVERY_INFO_EXCEL = "excel/tplt_export_delivery_info.xls";
    String DELIVERY_INFO_EXPORT_FOR_ACCOUNT = "excel/tplt_delivery_info_export.xls";
    String STA_TEMPLATE_FOR_PURCHASE = "excel/tpl_sta_purchase.xls";
    String PICKING_LIST_EXPORT = "excel/tpl_export_picking_list.xls";
    String TRANS_AREA_EXP = "excel/tplt_import_tracs_area_exp.xls";
    String STA_TEMPLATE_FOR_PREDEFINED = "excel/tpl_sta_predefined.xls";
    String EXTERNAL_IN_INFO_EXCEL = "excel/tpl_external_in_info.xls";
    String RECEVING_MOVE_SUGGEST_EXCEL = "excel/tpl_receving_move_suggest.xls";
    String EXTERNAL_OUT_INFO_EXCEL = "excel/tpl_external_out_info.xls";
    String LOCATION_TEMPLATE_FOR_IMPORT = "excel/tpl_location_import.xls";
    String VMI_INVOICE_TAX = "excel/tplt_VMI_invoice_tax.xls";
    String HAND_OVER_LINE = "excel/tplt_export_hand_over_list.xls";
    String INVENTORY_CHECK = "excel/tplt_export_inventory_check.xls";
    String VMI_ADJUSTMENT_INVCHECK = "excel/tplt_export_vmi_adjustment_invcheck.xls";
    String CHECK_OVERAGE = "excel/tpl_export_check_overage.xlsx";
    String HAND_OVER_LIST_EXPORT = "excel/tpl_hand_over_list_export.xls";
    String PREDEFINED_OUT_EXCEL = "excel/tplt_export_predefined_out.xls";
    String PAD_POST_ERROR_LOG = "excel/tplt_pda_post_error_log.xls";
    String PO_CONFRIM_REPORT = "excel/tplt_po_confirm_export.xls";
    String EMS_KEYWORD_EXPORT = "excel/tplt_import_ems_keyword.xls";
    String PICKINGLIST_EXPORT_MODE1 = "excel/tplt_pickinglist_export_mode1.xls";
    String PDA_POST_LOG_EXPORT = "excel/tplt_pda_post_log.xls";
    String INV_TEMPLATE_FOR_PURCHASE = "excel/tpl_inbound_purchase.xls";
    String SKU_REPLENISHMENT = "excel/tpl_sku_replenishment.xls";
    String EXPORT_SKU_REPLENISH = "excel/tpl_export_sku_replenish.xls";
    String EXPORT_SKU_REPLENISHB = "excel/tpl_export_sku_replenishB.xls";
    String SKU_PROVIDE_PICKING_EXPORT = "excel/tplt_sku_provide_picking_maintain.xls";
    String SKU_PROVIDE_UN_MAINTAIN_EXPORT = "excel/tplt_sku_provide_un_maintain.xls";
    String UNFINISHED_STA_SKU_PROVIDE_UN_MAINTAIN_EXPORT = "excel/tplt_unfinished_sta_sku_provide_un_maintain.xls";

    String SKU_PROVIDE_PICKING_EXPORT1 = "excel/tplt_sku_provide_picking_maintain1.xls";
    String SKU_PROVIDE_UN_MAINTAIN_EXPORT1 = "excel/tplt_sku_provide_un_maintain1.xls";
    String UNFINISHED_STA_SKU_PROVIDE_UN_MAINTAIN_EXPORT1 = "excel/tplt_unfinished_sta_sku_provide_un_maintain1.xls";

    String SALES_SEND_OUT_INFO_EXPORT = "excel/tplt_sales_send_out_info_export.xls";
    String RETURN_REGISTER_INFO_EXPORT = "excel/tplt_return_register_info_export.xls";
    String INVENTORY_REPORT_EXPORT = "excel/tplt_inventory_report_export.xls";
    String SKU_SALES_REPORT_FROM = "excel/tpl_sales_report_form.xls";
    String STA_DELIVER_TEMPLATE = "excel/tpl_export_sta_transno.xls";
    String STA_TEMPLATE_IN_BOUND = "excel/tpl_in_bound.xls";
    String STA_TEMPLATE_CONFIRM_DIVERSIT = "excel/tpl_confirm_diversity.xls";
    String STA_TEMPLATE_PRO_INFO_MAINTAIN = "excel/tpl_import_pro_info_maintain.xls";
    String STA_TEMPLATE_IN_BOUND_SHELVES = "excel/tpl_in_bound_shelves.xls";
    String STA_TEMPLATE_VIR_IN_BOUND_SHELVES = "excel/tplt_export_shev.xls";
    String NIKE_CRW_CARTON_LINE = "excel/tplt_export_nike_crw_carton_line.xls";
    String AGV = "excel/agv.xls";

    String WH_REPLENISH_EXPROT_TPL = "excel/tpl_export_replenish_report.xls";
    String STA_EXCEL_FILE_FULL_PATH = "excel/tplt_so_invoice_tax.xls";
    String CONVENIENCE_STORE_ORDER_INFO_EXCEL = "excel/tplt_store_order_info.xls";
    String STA_DISTRIBUTION_FAILURE_INFO_EXPORT = "excel/tplt_distribution_failure_info.xls";
    String INVENTORY_SKU = "excel/tplt_inventory_sku_export.xls";
    String PICK_ZONE = "excel/tplt_pick_zone_export.xls";
    String PROCUREMENT_RETURN_INBOUND_PUTAWAY = "excel/procument_return_inbound_putaway.xls";

    String STA_TEMPLATE_IN_BOUND_SHELVES_IMPERFECT = "excel/tpl_in_bound_shelves_imperfect.xls";
    String SF_NEXT_MORNING_CONFIG = "excel/tpl_sf_next_morning_config.xls";
    String SF_TIME_TYPE_CONFIG = "excel/tpl_sf_time_type_config.xls";
    String NIKE_TODAY_SEND_CONFIG = "excel/tpl_nike_today_send_config.xls";
    String CLAIMANT_INFO = "excel/tpl_export_claimant_list.xls";
    String PDA_SORT_PICKING_EXCEL = "excel/pda_sort_picking.xls";
    String GUCCI_RTO_LINE_INFO = "excel/tpl_export_gucci_rto_line.xls";
    String SKU_REPLENISHMENT_SUGGEST="excel/sku_replenishment_suggest.xls";//导出补货建议模板
    String AD_PACKAGE = "excel/tpl_export_ad_package.xls";
    String THREE_DIMENSIONAL_SKU_INFO = "excel/tpl_export_sku_three_Dimensional.xlsx";


    
    String DWH_DISTRI_BUTION_AREA_LOC = "excel/dwhdistributionarealoc.xls";

    String NO_THREE_DIMENSIONAL_SKU = "excel/tpl_export_no_three_Dimensional_sku.xls";

    /**
     * 根据配货清单导出税控发票
     * 
     * @param os
     * @param plId
     * @return
     */
    WriteStatus exportSoInvoiceByPickingList(OutputStream os, Long plId, Long staId);

    WriteStatus exportNikeCRWCatonLine(OutputStream outputStream, StockTransApplicationCommand sta, Long ouId);

    /**
     * 根据配货清单导出税控发票
     * 
     * @param out
     * @param plId
     * @return
     */
    WriteStatus exportSoInvoiceByPickingLists(OutputStream out, List<Long> plIds, Long staId);

    WriteStatus skuProvideInfoPickingDistrictExport1(OutputStream os, Long ouid);


    public WriteStatus importStaByOwner(Long id, OutputStream outputStream);

    /***
      * 仓库未完成补货信息商品导出
     * 
     * @param os
     * @param ouid
     * @return
     */
    WriteStatus skuProvideInfoUnMaintainExport1(OutputStream os, Long ouid);

    /****
     * 仓库未完成入库作业单 未维护补货信息商品导出
     * 
     * @param outs
     * @param id
     * @return
     */
    WriteStatus unfinishedStaUnMaintainProductExport1(OutputStream outs, Long id);

    /**
     * 导出补货汇总
     * 
     * @param os
     * @param sta
     * @return
     */
    WriteStatus exportSkuReplenishmentInfo(Long ouId, SkuReplenishmentCommand com, OutputStream os);

    /**
     * STA确认收货导出
     * 
     * @param os
     * @param sta
     * @return
     */
    WriteStatus staExportForPurchase(OutputStream os, StockTransApplication sta);

    /**
     * 
     * 配货批次导出
     * 
     * @param os
     * @param sta
     * @return
     */
    WriteStatus exportPickingList(OutputStream os, PickingList sta);

    /**
     * 
     * 配送范围导出
     * 
     * @param os
     * @param sta
     * @return
     */
    WriteStatus expTransAreaDetail(OutputStream os, Long groupId);

    /**
     * 导出补货汇总
     * 
     * @param os
     * @param sta
     * @return
     */
    WriteStatus exportSkuReplenishmentInfo(int type, Long ouId, SkuReplenishmentCommand com, OutputStream os);

    /**
     * 收货暂存区移库建议
     */
    WriteStatus recevingMoveSuggestExport(OutputStream os, StvLineCommand cmd, Long ouId);

    WriteStatus predefinedOutExport(OutputStream os, StockTransApplication sta);

    WriteStatus externalOutOutport(OutputStream os, StockTransApplication sta);

    /**
     * PDA日志导出
     * 
     * @param os
     * @param code
     * @return
     */
    WriteStatus exportPdaLogReport(OutputStream os, String code);

    /**
     * 盘盈须要处理数据
     */
    WriteStatus exportCheckOverage(OutputStream os, Long invCheckId, OperationUnit whou);

    WriteStatus externalInExport(OutputStream os, StockTransApplication sta);

    /**
     * 物流面单导出
     * 
     * @param outputStream
     * @param pickingListId
     */
    WriteStatus exportDeliveryInfo(OutputStream outputStream, Long pickingListId, Long ouid);

    /**
     * inv确认收货导出
     * 
     * @param os
     * @param sta
     * @return
     */
    WriteStatus invExportForPurchase(OutputStream os, StockTransApplication sta);

    WriteStatus pickingListMode1Export(PickingListCommand plCmd, Long warehouseOuid, OutputStream os, String psize);

    /**
     * 销售出库发票导出
     * 
     * @param os
     * @param plId
     * @return
     */
    WriteStatus exportVMIInvoiceRecord(OutputStream os, Long ouid, Date fromDate, Date endDate);

    /**
     * 交接清单导出
     * 
     * @param fromDate
     * @param endDate
     * @return
     */
    WriteStatus findHandOverListExportReturnOrderRecord(OutputStream os, Date _fromDate, Date _endDate, String lpCode, List<Long> idList, Long ouid);

    WriteStatus deliveryInfoExport(ServletOutputStream outputStream, List<Long> oulist, Long deliveryid, Long ouid, Date starttime, Date endtime);


    /**
     * 物流交接单
     * 
     * @param handOverList
     * @param ouid
     * @return
     */
    WriteStatus exportPoConfirmReport(OutputStream os, Long staId);

    WriteStatus exportInfoByHoListId(OutputStream os, Long hoListId, String fileName);


    WriteStatus exportPdaErrorLog(ServletOutputStream outputStream, Long staid);

    WriteStatus exportVMIInventoryCheck(OutputStream os, Long invCkId);

    /**
     * 导出盘点批列表
     * 
     * @param os
     * @param invCkId 盘点批id
     * @return
     */
    WriteStatus exportInventoryCheck(OutputStream os, Long invCkId);

    /**
     * 
     * @param os
     * @return
     */
    WriteStatus locationTPLExport(OutputStream os);


    /**
     * 销售（换货）发货表导出
     * 
     * @param outputStream
     * @param whOuId
     * @return
     */
    WriteStatus salesSendOutInfoExport(OutputStream outputStream, Long plId);


    /**
     * 销售（换货）发货表导出
     * 
     * @param outputStream
     * @param whOuId
     * @return
     */
    WriteStatus returnRegisterInfoExport(OutputStream outputStream, Long whOuId);


    /**
     * 库存表导出
     * 
     * @param outputStream
     * @param whOuId
     * @return
     */
    WriteStatus inventoryReportkExport(OutputStream outputStream, Long whOuId);

    /**
     * PDA拣货短拣导出
     */
    public WriteStatus pdaSortPickingExport(ServletOutputStream os, Map<String, Object> params);

    /**
     * 销售订单报表----
     */
    WriteStatus exportSalesReportForm(Date outboundTime, Date endOutboundTime, OutputStream os);

    /**
     * 确认数量模版导出
     */
    WriteStatus exportStaDeliver(ServletOutputStream outs, PickingList pickingList);

    /**
     * STA收货暂存区模版导出
     */
    WriteStatus staExportForGI(OutputStream os, StockTransApplication sta);

    /**
     * 确认数量模版导出
     */
    WriteStatus exportInBoundNumber(OutputStream outputStream, StockTransApplicationCommand sta);

    /**
     * 审核差异调整
     */
    WriteStatus exportConfirmDiversity(OutputStream outputStream, StockTransApplicationCommand sta);

    /**
     * 商品基础信息导出
     */
    WriteStatus exportSKUinfo(OutputStream outputStream, StockTransApplicationCommand sta);

    /**
     * 上架模版导出
     */
    WriteStatus exportInboundShelves(OutputStream outputStream, StockTransApplicationCommand sta);

    /**
     * 合并上架模版导出
     */
    WriteStatus exportMergeInboundShelves(OutputStream outputStream, String ids);

    /**
     * 导出补货报表
     */
    public WriteStatus exportreplenishorder(ServletOutputStream outs, Long wrId);

    /**
     * 销售|换货出库发票导出
     * 
     * @throws Exception
     */
    WriteStatus exportsoinvoiceforsalesreturnorder(OutputStream os, Long ouid, String fromDate, String endDate);

    /**
     * 便利店自提信息导出
     * 
     * @param outputStream
     * @param id
     * @param _fromDate
     * @param _endDate
     * @return
     */
    WriteStatus exportConvenienceStoreOrderInfo(ServletOutputStream outputStream, Long id, Date _fromDate, Date _endDate);

    /**
     * 导出配货失败 缺货的信息
     * 
     * @param outputStream
     * @param id
     * @return
     */
    WriteStatus exportStaDistributionOfFailureInfo(OutputStream outputStream, Long id);

    /**
     * 批量导出SKU库存数据
     * 
     * @param os
     * @param invCkId
     * @return
     */
    WriteStatus exportInventorySku(OutputStream os, List<InventoryCommand> cmmd);

    /**
     * 导出库位列表
     * 
     * @param os
     * @param district
     * @param location
     * @param pickZoneName
     * @param pickZoneCode
     * @return
     */
    WriteStatus exportLocationByParam(OutputStream os, String district, String location, String pickZoneName, String pickZoneCode, Long ouid);



    /**
     * 反向采购退货入库上架导出
     * 
     * @param os
     * @param stvId
     * @return
     */
    WriteStatus exportProcurementReturnInboud(OutputStream os, List<Long> stvId);

    /**
     * 根据batchNo导出税控发票
     * 
     * @param os
     * @param plId
     * @return
     */
    WriteStatus exportSoInvoiceByBatchNo(OutputStream os, String batchNo, List<Long> wioIdList);

    WriteStatus exportInboundShelvesImperfect(OutputStream os, List<Long> staIds);

    WriteStatus exportSfNextMorningConfigByOuId(Long id, ServletOutputStream os);

    WriteStatus exportSfConfigByOuId(Long ouId, Long cId, ServletOutputStream os);


    WriteStatus exportNikeTodaySendConfigByOuId(Long id, ServletOutputStream os);

    WriteStatus exportAdPackage(Long id, ServletOutputStream os);

    /**
     * 导出索赔信息
     */
    WriteStatus exportClaimantInfo(ServletOutputStream os, List<WhCompensationCommand> CompensationList);

    WriteStatus exportGucciRtoLineInfo(ServletOutputStream os, List<VmiRtoLineCommand> gucciRtoLineList);

    WriteStatus getExportAgv(ServletOutputStream os, String plCode, String areaCode);
    
    /**
     * 导出补货建议
     * 
     * @param os
     * @param sta
     * @return
     */
    WriteStatus exportSkuReplenishmentSuggest(Long ouId,String staCode, OutputStream os);

    
    /**
     * 导出库位
     */
    public WriteStatus exportDistriBution(Long mainWhid,ServletOutputStream outs,String locCodeName,String locCode,String locDistriButionAreaCode,String locDistriButionAreaName);


    WriteStatus exportThreeDimensionalSkuInfo(ServletOutputStream os, List<SkuCommand> staList);


    WriteStatus exportNoThreeDimensional(ServletOutputStream os, List<SkuCommand> skuList);
}
