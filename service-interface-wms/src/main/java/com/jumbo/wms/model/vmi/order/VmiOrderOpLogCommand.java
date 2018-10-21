package com.jumbo.wms.model.vmi.order;

import java.util.Date;

/**
 * 
 * @author wudan
 * 
 */
public class VmiOrderOpLogCommand extends VmiOrderOpLog {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1730688590802126830L;

    private String opTypeStr;

    private Date createTimeBegin;

    private Date createTimeEnd;

    public String getOpTypeStr() {
        return opTypeStr;
    }

    public void setOpTypeStr(String opTypeStr) {
        this.opTypeStr = opTypeStr;
    }

    public Date getCreateTimeBegin() {
        return createTimeBegin;
    }

    public void setCreateTimeBegin(Date createTimeBegin) {
        this.createTimeBegin = createTimeBegin;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }
}
