package com.jumbo.manager.channel.invoice;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("invoiceFactory")
public class InvoiceFactoryImpl implements InvoiceFactory {

    @Resource(name = "invoiceTypeCoach")
    private InvoiceTypeInterface coach;
    @Resource(name = "invoiceTypeDefault")
    private InvoiceTypeInterface defaultType;
    @Resource(name = "invoiceTypeBurberry")
    private InvoiceTypeInterface burberry;

    @Override
    public InvoiceTypeInterface getInvoice(String type) {
        if (StringUtils.hasText(type)) {
            if (INVOICE_TYPE_BURBERRY.equals(type)) {
                return burberry;
            }else if(INVOICE_TYPE_COACH.equals(type)){
                return coach;
            }
        } else {
            return defaultType;
        }
        return defaultType;
    }

}
