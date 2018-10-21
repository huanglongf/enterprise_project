package com.jumbo.pms.model.command.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 获取包裹快照
 * author : ge.sheng    
 */
public class GetParcelInfoVo implements Serializable {


	/**
     * 
     */
    private static final long serialVersionUID = 2565941795618045488L;

    /**
     * 运单号
     */
    private String mailNo;
    
    /**
     * 物流商编码
     */
    private String lpCode;
    
    /**
     * 门店关联的所以
     */
    private List<String> channelCodes;

    
	public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}

	public String getLpCode() {
		return lpCode;
	}

	public void setLpCode(String lpCode) {
		this.lpCode = lpCode;
	}

    public List<String> getChannelCodes() {
        return channelCodes;
    }

    public void setChannelCodes(List<String> channelCodes) {
        this.channelCodes = channelCodes;
    }

}
