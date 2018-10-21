package com.jumbo.wms.model.warehouse;

public class BiChannelImperfectLineCommand extends BiChannelImperfectLine {

    /**
     * 
     */
    private static final long serialVersionUID = 4699462942150087342L;
    /**
     * 渠道残次名称
     */
    private String imperfectName;

    public String getImperfectName() {
        return imperfectName;
    }

    public void setImperfectName(String imperfectName) {
        this.imperfectName = imperfectName;
    }

}
