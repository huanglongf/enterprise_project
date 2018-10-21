/**
 * EventObserver * Copyright (c) 2010 Jumbomart All Rights Reserved.
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.excel.ExcelWriter;
import loxia.support.excel.WriteStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.af.AFInventoryCompareDao;
import com.jumbo.dao.warehouse.ImportFileLogDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.JohnsonInventoryDao;
import com.jumbo.dao.warehouse.MsSnReportCommandDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.WmsInvoiceOrderDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.TaskManager;
import com.jumbo.wms.model.command.JohnsonInventoryCommand;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.af.AFInvComReportCommand;
import com.jumbo.wms.model.warehouse.ImportFileLog;
import com.jumbo.wms.model.warehouse.InventoryReport;
import com.jumbo.wms.model.warehouse.MsSnReportCommand;

@Transactional
@Service("excelWriterManager")
public class ExcelWriterManagerImpl extends BaseManagerImpl implements ExcelWriterManager {

    private static final long serialVersionUID = 8791522769636655736L;

    @Autowired
    private MsSnReportCommandDao msSnReportCommandDao;
    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private JohnsonInventoryDao johnsonInventoryDao;
    @Autowired
    private AFInventoryCompareDao aFInventoryCompareDao;
    @Autowired
    private WmsInvoiceOrderDao wmsInvoiceOrderDao;
    @Autowired
    private ImportFileLogDao importFileLogDao;
    /**
     * EXCEL writer
     */
    @Resource(name = "msSnExportWriter")
    private ExcelWriter msSnExportWriter;
    @Resource(name = "invInfoWriter")
    private ExcelWriter invInfoWriter;
    @Resource(name = "totalJNJInvWriter")
    private ExcelWriter totalJNJInvWriter;
    @Resource(name = "afInvComReportWriter")
    private ExcelWriter afInvComReportWriter;
    @Autowired
    private ExcelReadManager excelReadManager;

    /**
     * web层无调用，无需迁移
     */
    public WriteStatus msSnReportExport(OutputStream os) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<MsSnReportCommand> list = msSnReportCommandDao.findYesterdayData(new BeanPropertyRowMapper<MsSnReportCommand>(MsSnReportCommand.class));
        if (list == null) {
            list = new ArrayList<MsSnReportCommand>();
        }
        beans.put("data", list);
        return msSnExportWriter.write(MS_SN_REPORT_EXCEL, os, beans);
    }


    // 获取税控发票导出文件名称
    public String getExportFileName(Long pickingListId) {
        return pickingListDao.findExportFileName(pickingListId, new SingleColumnRowMapper<String>(String.class));
    }

    /**
     * 统计 物流对账信息导出的总数量
     */
    public Long findDeliveryInfoCount(List<Long> oulist, Long deliveryid, Long ouid, Date starttime, Date endtime) {
        Long count = packageInfoDao.findDeliveryInfoCount(oulist, deliveryid, ouid, starttime, endtime, new SingleColumnRowMapper<Long>(Long.class));
        return count;
    }



    /**
     * 库存报表 -- web层无调用，无需迁移
     */
    public WriteStatus getInvStatisticsData(OutputStream outputStream, String district) {
        Map<String, Object> beans = new HashMap<String, Object>();
        if (TaskManager.MAINLAND.equals(district)) {
            beans.put("inv1", inventoryDao.findStatisticsInv1(new BeanPropertyRowMapper<InventoryReport>(InventoryReport.class)));
            beans.put("inv2", inventoryDao.findStatisticsInv2(new BeanPropertyRowMapper<InventoryReport>(InventoryReport.class)));
        } else {
            beans.put("inv1", inventoryDao.findStatisticsInv(new BeanPropertyRowMapper<InventoryReport>(InventoryReport.class)));
        }
        return invInfoWriter.write(INV_INTO_EXCEL, outputStream, beans);
    }


    /**
     * 强生实时库存导出 -- web层无调用，无需迁移
     * 
     * @param outputStream
     * @return
     */
    public WriteStatus johnsonInvExport(OutputStream outputStream) {
        ChooseOption co = chooseOptionDao.findByCategoryCode("JohnsonJohnsonInv");
        String channels = null;
        List<String> cList = new ArrayList<String>();
        if (co != null) {
            channels = co.getOptionValue();
            if (channels != null) {// 格式化渠道编码
                String[] channel = channels.split(",");
                for (String c : channel) {
                    cList.add(c);
                }
            }
        }
        Map<String, Object> beans = new HashMap<String, Object>();
        List<JohnsonInventoryCommand> list = johnsonInventoryDao.findJNJInv(cList, new BeanPropertyRowMapperExt<JohnsonInventoryCommand>(JohnsonInventoryCommand.class));
        if (list == null) {
            list = new ArrayList<JohnsonInventoryCommand>();
        }
        beans.put("invList", list);
        return totalJNJInvWriter.write(JNJ_INV_EXPORT, outputStream, beans);
    }

    /**
     * AF库存对比数据
     * 
     * @param outputStream
     * @return
     */
    public WriteStatus afInvComReport(OutputStream outputStream, String vmiSource) {

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date nowTime = calendar.getTime();
        Map<String, Object> beans = new HashMap<String, Object>();
        List<AFInvComReportCommand> afList = aFInventoryCompareDao.findAFInventoryCompareData(nowTime, new BeanPropertyRowMapper<AFInvComReportCommand>(AFInvComReportCommand.class));

        if (afList == null) {
            afList = new ArrayList<AFInvComReportCommand>();
        }

        beans.put("afinvComList", afList);
        return afInvComReportWriter.write(AF_INVCOM_EXPORT_EXCE, outputStream, beans);
    }

    public String getExportFileName(String batchNo, List<Long> wioIdList) {
        return wmsInvoiceOrderDao.findExportFileNameByBatchNo(batchNo, wioIdList, new SingleColumnRowMapper<String>(String.class));
    }

    public List<ImportFileLog> findAllfileList(Long whId) {
        return importFileLogDao.findAllTodoFile(1, 1, whId, new BeanPropertyRowMapperExt<ImportFileLog>(ImportFileLog.class));
    }

    public void updateFileLog(Long id, String msg) {
        int count = importFileLogDao.bindFileByStaCode(id, msg, "");
        if (count != 1) {
            log.error("bindFileByStaCode save is error:" + id);
        }
    }

}
