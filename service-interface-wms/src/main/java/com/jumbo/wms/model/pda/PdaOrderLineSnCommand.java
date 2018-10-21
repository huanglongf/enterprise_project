package com.jumbo.wms.model.pda;

/**
 * 
 * @author dly
 * 
 */
public class PdaOrderLineSnCommand extends PdaOrderLineSn {

    /**
     * 
     */
    private static final long serialVersionUID = 8080477313843773760L;

    private String echoType;

    public String getEchoType() {
        return echoType;
    }

    public void setEchoType(String echoType) {
        this.echoType = echoType;
    }

}
