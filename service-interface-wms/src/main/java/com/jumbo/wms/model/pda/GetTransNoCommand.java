package com.jumbo.wms.model.pda;

import java.io.Serializable;
import java.math.BigDecimal;

public class GetTransNoCommand implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8622615464542121031L;
	private String mapKey;
    private String lpCode;
    private String uniqueCode;
    private String transNo;
    private String receiver;
    private BigDecimal weight;

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
