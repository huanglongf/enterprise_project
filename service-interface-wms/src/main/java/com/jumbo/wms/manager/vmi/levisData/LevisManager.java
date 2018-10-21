package com.jumbo.wms.manager.vmi.levisData;

import java.io.File;

import com.jumbo.wms.manager.BaseManager;

public interface LevisManager extends BaseManager {
    /**
     * 按照箱号创建作业单
     * 
     * @param code
     */
    void generateInboundStaByCode(String code);
    
    void createStkrData();

    void readStockInFile(File file, String localBackPath);

    void createSkmrData(String dateFormatStr);

}
