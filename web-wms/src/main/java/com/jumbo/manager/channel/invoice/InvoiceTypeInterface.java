package com.jumbo.manager.channel.invoice;

import java.io.OutputStream;
import java.util.List;

import loxia.support.excel.WriteStatus;

import com.jumbo.wms.model.warehouse.StaInvoiceCommand;



public interface InvoiceTypeInterface {
    /**
     * 获取发票数据
     * 
     * @return
     */
    List<StaInvoiceCommand> getInvoiceData(Long plId, Long staId);

    /**
     * 导出EXCEL数据
     * 
     * @param data
     * @return
     */
    WriteStatus writeExcel(List<StaInvoiceCommand> data, OutputStream os);
    
    /**
     * 发票单批次号和发票单Id导出
     * 
     * @return
     */
    List<StaInvoiceCommand> getReplenishInvoiceData(String batchNo, List<Long> wioIdList);
    

}
