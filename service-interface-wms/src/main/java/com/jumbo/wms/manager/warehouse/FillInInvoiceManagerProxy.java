package com.jumbo.wms.manager.warehouse;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;

/**
 * 补开发票业务逻辑接口
 * 
 * @author jinlong.ke
 * 
 */
public interface FillInInvoiceManagerProxy extends BaseManager {
    /**
     * 预处理补寄发票单据<br/>
     * 匹配单号<br/>
     * 生成批次<br/>
     * 
     * @param wioIdlist
     */
    String preExportInvoice(List<Long> wioIdlist);

}
