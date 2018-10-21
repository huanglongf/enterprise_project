package com.jumbo.wms.newInventoryOccupy;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.manager.warehouse.excel.ExcelWriterManager;
import com.jumbo.wms.model.warehouse.ImportFileLog;

/**
 * exl导入文件执行
 * 
 * @author fxl
 * 
 */
public class FileExecuteThread implements Runnable {
    protected static final Logger log = LoggerFactory.getLogger(FileExecuteThread.class);

    private Long whId;
    private ExcelWriterManager excelWriterManager;
    private WareHouseManagerProxy wareHouseManagerProxy;


    public FileExecuteThread(Long whId, ExcelWriterManager excelWriterManager) {
        this.whId = whId;
        this.excelWriterManager = excelWriterManager;
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        wareHouseManagerProxy = (WareHouseManagerProxy) webContext.getBean("wareHouseManagerProxy");
    }

    public void run() {
        List<ImportFileLog> fileList = excelWriterManager.findAllfileList(whId);
        for (ImportFileLog file : fileList) {
            String msg = wareHouseManagerProxy.exeExlFile(file);
            if (StringUtils.hasLength(msg)) {
                if (msg.length() > 400) {
                    msg = msg.substring(0, 400);// 只保留500个字符1
                }
                excelWriterManager.updateFileLog(file.getId(), msg);
            }
        }
    }


    public ExcelWriterManager getExcelWriterManager() {
        return excelWriterManager;
    }

    public void setExcelWriterManager(ExcelWriterManager excelWriterManager) {
        this.excelWriterManager = excelWriterManager;
    }

    public Long getWhId() {
        return whId;
    }

    public void setWhId(Long whId) {
        this.whId = whId;
    }


}
