package com.jumbo.wms.model.warehouse;

import java.io.Serializable;

public class BiChannelRefCommand implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 4153537271507668862L;

	private Long cgId;
    
    private Long channelId;

    public Long getCgId() {
        return cgId;
    }

    public void setCgId(Long cgId) {
        this.cgId = cgId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }
}
