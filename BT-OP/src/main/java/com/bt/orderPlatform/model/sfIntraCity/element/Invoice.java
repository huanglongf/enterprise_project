package com.bt.orderPlatform.model.sfIntraCity.element;


public class Invoice{
    
    private String need_invoice;

    private String invoice_title;

    private String invoice_code;

    private String invoice_content;

    public Invoice(String need_invoice, String invoice_title, String invoice_code, String invoice_content){
        this.need_invoice = need_invoice;
        this.invoice_title = invoice_title;
        this.invoice_code = invoice_code;
        this.invoice_content = invoice_content;
    }

    public String getNeed_invoice(){
        return need_invoice;
    }

    public void setNeed_invoice(String need_invoice){
        this.need_invoice = need_invoice;
    }

    public String getInvoice_title(){
        return invoice_title;
    }

    public void setInvoice_title(String invoice_title){
        this.invoice_title = invoice_title;
    }

    public String getInvoice_code(){
        return invoice_code;
    }

    public void setInvoice_code(String invoice_code){
        this.invoice_code = invoice_code;
    }

    public String getInvoice_content(){
        return invoice_content;
    }

    public void setInvoice_content(String invoice_content){
        this.invoice_content = invoice_content;
    }
}
