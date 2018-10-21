package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;

public class ConsignInvoinceConfirm implements Serializable {

    private static final long serialVersionUID = -6105243163607567560L;

    /**
     * 发票ID
     */
    private Long billId;
    /**
     * 发票号
     */
    private String invoiceNumber;
    /**
     * 发票代码
     */
    private String invoiceCode;

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

}
