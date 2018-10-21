package com.jumbo.wms.model.vmi.warehouse;

import java.util.Date;

public class MsgRtnInboundOrderCommand extends MsgRtnInboundOrder {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4748033667053085288L;

    private String slipCode;

    private Date startDate;

    private Date endDate;

    private int intStatus;

    private Long qtyLine;

    private Long invStatusLine;

    private String skuCode;
    /*
     * 增加String辅助实现时间精确到时分秒
     */
    private String startDate1;
    private String endDate1;

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(int intStatus) {
        this.intStatus = intStatus;
    }

    public Long getQtyLine() {
        return qtyLine;
    }

    public void setQtyLine(Long qtyLine) {
        this.qtyLine = qtyLine;
    }

    public Long getInvStatusLine() {
        return invStatusLine;
    }

    public void setInvStatusLine(Long invStatusLine) {
        this.invStatusLine = invStatusLine;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getStartDate1() {
        return startDate1;
    }

    public void setStartDate1(String startDate1) {
        this.startDate1 = startDate1;
    }

    public String getEndDate1() {
        return endDate1;
    }

    public void setEndDate1(String endDate1) {
        this.endDate1 = endDate1;
    }


}
