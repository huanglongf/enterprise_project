package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
import com.jumbo.wms.model.warehouse.StaInvoice;

public class StaInvoiceCommand extends StaInvoice {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6732050581476143007L;
	/**
     * 箱号
     */
    private String seqNo;
    /**
     * 作业单号
     */
    private String staCode;
    /**
     * 相关单据号
     */
    private String refSlipCode;
    /**
     * 总金额
     */
    private BigDecimal totalAmt;
    /**
     * 公司简称
     */
    private String companyName;

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getRefSlipCode() {
        return refSlipCode;
    }

    public void setRefSlipCode(String refSlipCode) {
        this.refSlipCode = refSlipCode;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
