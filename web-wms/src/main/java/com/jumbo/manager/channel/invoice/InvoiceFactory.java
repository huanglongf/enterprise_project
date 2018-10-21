package com.jumbo.manager.channel.invoice;

public interface InvoiceFactory {
    
    String INVOICE_TYPE_BURBERRY = "burberry";
    String INVOICE_TYPE_COACH = "coach";

    /**
     * 获取发票类型
     * 
     * @param type
     * @return
     */
    InvoiceTypeInterface getInvoice(String type);
}
