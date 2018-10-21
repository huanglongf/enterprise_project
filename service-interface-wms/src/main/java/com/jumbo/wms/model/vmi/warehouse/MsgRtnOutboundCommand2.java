package com.jumbo.wms.model.vmi.warehouse;

import java.util.Date;

public class MsgRtnOutboundCommand2 extends MsgRtnOutbound {


    /**
	 * 
	 */
    private static final long serialVersionUID = 8707047082950298387L;

    private Date startDate;

    private Date endDate;

    private int intStatus;

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
}
