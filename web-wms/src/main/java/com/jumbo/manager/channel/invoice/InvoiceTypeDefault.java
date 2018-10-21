package com.jumbo.manager.channel.invoice;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import loxia.support.excel.ExcelWriter;
import loxia.support.excel.WriteStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.wms.manager.channel.ChannelManager;
import com.jumbo.wms.model.warehouse.StaInvoiceCommand;

/**
 * 默认发票类型
 * 
 * @author jumbo
 * 
 */
@Service("invoiceTypeDefault")
public class InvoiceTypeDefault implements InvoiceTypeInterface {

	private final static String EXCEL_FILE_FULL_PATH = "excel/tplt_so_invoice_tax.xls";
	private final static String EXCEL_DATA_NAME = "invoiceList";

	@Autowired
	private ChannelManager channelManager;
	@Resource(name = "soInvoiceExportWriter")
	private ExcelWriter soInvoiceExportWriter;

	@Override
	public List<StaInvoiceCommand> getInvoiceData(Long plId, Long staId) {
		return channelManager.findDefaultInvoice(plId, staId);
	}

	@Override
	public WriteStatus writeExcel(List<StaInvoiceCommand> data, OutputStream os) {
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put(EXCEL_DATA_NAME, data);
		return soInvoiceExportWriter.write(EXCEL_FILE_FULL_PATH, os, beans);
	}

    @Override
    public List<StaInvoiceCommand> getReplenishInvoiceData(String batchNo, List<Long> wioIdList) {
        return channelManager.findDefaultInvoice(batchNo, wioIdList);
    }

}
