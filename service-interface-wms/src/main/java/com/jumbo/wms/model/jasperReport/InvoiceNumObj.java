/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;
import java.math.BigDecimal;

public class InvoiceNumObj implements Serializable {

    private static final long serialVersionUID = 868177900256429783L;

    /**
     * 作业单号
     */
    private String code;
    /**
     * 是否需要发票
     */
    private Integer needInvoice;
    /**
     * 发票类型 - 1 普票
     */
    private Integer invoiceType;
    /**
     * 发票是否拆票
     */
    private BigDecimal billingManual;
    /**
     * 作业类型
     */
    private Integer staType;
    /**
     * 发票数
     */
    private Integer num;

    // private String slipCode;
    // private BigDecimal outPutCount;
    // select tmp.code,tmp.needInvoice,tmp.invoiceType,tmp.billingManual,tmp.type as staType,tmp.num
    // from (
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStaType() {
        return staType;
    }

    public void setStaType(Integer staType) {
        this.staType = staType;
    }

    public BigDecimal getBillingManual() {
        return billingManual;
    }

    public void setBillingManual(BigDecimal billingManual) {
        this.billingManual = billingManual;
    }

    public Integer getNeedInvoice() {
        return needInvoice;
    }

    public void setNeedInvoice(Integer needInvoice) {
        this.needInvoice = needInvoice;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        InvoiceNumObj other = (InvoiceNumObj) obj;
        if (code == null) {
            if (other.code != null) {
                return false;
            }
        } else if (!code.equals(other.code)) {
            return false;
        }
        return true;
    }
}
