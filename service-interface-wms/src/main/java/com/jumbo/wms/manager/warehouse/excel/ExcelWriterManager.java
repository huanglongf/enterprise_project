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


import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import loxia.support.excel.WriteStatus;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.ImportFileLog;


public interface ExcelWriterManager extends BaseManager {

    String INV_INTO_EXCEL = "excel/tpl_inv_info.xls";
    String MS_SN_REPORT_EXCEL = "excel/tpl_report_ms_sn.xls";
    String JNJ_INV_EXPORT = "excel/tplt_johnson_inventory_export.xls";
    String AF_INVCOM_EXPORT_EXCE = "excel/tpl_af_inventory_compare.xls";

    /**
     * 获取到处名称
     * 
     * @param pickingListId
     * @return
     */
    String getExportFileName(Long pickingListId);


    /**
     * MS SN 出库报表 web层无调用，无需迁移
     * 
     * @param os
     * @return
     */
    WriteStatus msSnReportExport(OutputStream os);



    /**
     * 统计 物流对账信息导出的总数量
     */
    Long findDeliveryInfoCount(List<Long> oulist, Long deliveryid, Long ouid, Date starttime, Date endtime);


    WriteStatus getInvStatisticsData(OutputStream outputStream, String district);



    /**
     * 强生实时库存导出
     * 
     * @param outputStream
     * @return
     */
    WriteStatus johnsonInvExport(OutputStream outputStream);

    /**
     * AF库存对比数据
     * 
     * @param outputStream
     * @param vmiSource
     * @return
     */
    WriteStatus afInvComReport(OutputStream outputStream, String vmiSource);

    /**
     * 获取到处名称
     * 
     * @return
     */
    String getExportFileName(String batchNo, List<Long> wioIdList);

    List<ImportFileLog> findAllfileList(Long whId);

    void updateFileLog(Long id, String msg);

}
