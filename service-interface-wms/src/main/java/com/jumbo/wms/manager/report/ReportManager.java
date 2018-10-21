/**
 * 
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
package com.jumbo.wms.manager.report;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import loxia.support.excel.WriteStatus;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.command.report.OrderStatusCountCommand;
import com.jumbo.wms.model.report.SalesDataDetailCommand;
import com.jumbo.wms.model.report.SalesRaDataCommand;
import com.jumbo.wms.model.report.SalesReportCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;

public interface ReportManager extends BaseManager {

    String REPORT_EXPORT_FOR_SALES = "excel/tplt_report_export_for_sales.xls";
    String REPORT_EXPORT_FOR_SALES_DETIAL = "excel/tplt_report_export_for_sales_detial.xls";
    String REPORT_EXPORT_FOR_TIME_INVENTORY = "excel/tplt_report_export_for_time_inventory_by_shop.xls";
    String REPORT_EXPORT_FOR_LEVIS_INVENTORY_LOG = "excel/tplt_report_export_for_levis_inventory_log.xls";
    String REPORT_EXPORT_FOR_LEVIS_RA = "excel/tplt_report_export_for_lvs_ra.xls";
    String REPORT_EXPORT_FOR_LEVIS_CURRENT_IVN = "excel/tplt_report_export_for_current_inventory_by_shop.xls";

    String REPORT_EXPORT_EXCEL_NAME = "时间销售汇总表";

    List<String> findProductCategoryList();

    List<String> findProductLineList();

    List<String> findConsumerGroupList();

    /**
     * levis销售汇总报表导出
     */
    WriteStatus reportExportForSalesSum(ServletOutputStream outs, Date startTime, Date endTime, Long ouid);

    /**
     * 时点库存导出
     * 
     * @param os
     * @param date
     * @param ouid
     * @param cmd
     * @return
     */
    WriteStatus exportShopTimeInventory(ServletOutputStream os, Date date, Long ouid, InventoryCommand cmd);


    List<SalesReportCommand> queryByouId(OperationUnit operationUnit);

    /**
     * levis销售明细报表导
     * 
     * @param os
     * @param fromDate
     * @param toDate
     * @param supplierSkuCode
     * @param pomotionCode
     * @param ouid
     * @return
     */
    WriteStatus reportExportForSalesDetial(SalesDataDetailCommand cmd, ServletOutputStream os, Date fromDate, Date toDate, String supplierSkuCode, String pomotionCode, Long ouid);

    /**
     * levis时段库存日志报表
     * 
     * @param os
     * @param cmd
     * @param ouid
     * @return
     */
    WriteStatus reportExportForLvsInventoryLog(ServletOutputStream os, StockTransTxLogCommand cmd, Long ouid);

    void checkLineForReportExportForLvsInventoryLog(StockTransTxLogCommand cmd, Long ouid);

    /**
     * levis退换货报表
     * 
     * @param os
     * @param fromDate
     * @param toDate
     * @param ouid
     * @return
     */
    WriteStatus reportExportForLvsRa(ServletOutputStream os, SalesRaDataCommand cmd, Date fromDate, Date toDate, Long ouid);

    List<InventoryStatus> findInventoryStatusByShop(Long cmpouid);

    WriteStatus exportShopCurrentInventory(ServletOutputStream os, Long ouid, InventoryCommand cmd);

    List<String> findTransTypeByShop(Long ouid);

    List<OrderStatusCountCommand> findOrderStatusByOuId(Long ouid, String startdate, String enddate);

    List<OrderStatusCountCommand> findTransOrderStatusByOuId(Long ouid, String startdate, String enddate);
}
