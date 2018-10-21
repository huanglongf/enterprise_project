package com.jumbo.wms.model.warehouse;

import java.util.Date;

import com.jumbo.wms.model.baseinfo.BiChannelSpecialAction;


public class BiChannelSpecialActionCommand extends BiChannelSpecialAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3778140312820378435L;


    /**
     * 保价金额缓存过期时间
     */
    private Date expDate;
    
    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

}
