package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;

public class PackingSummaryForNike implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7243910946876888747L;
    
    private String code;
    private String name;
    private String toLocation;
    private String seqNo;
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getToLocation() {
        return toLocation;
    }
    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }
    public String getSeqNo() {
        return seqNo;
    }
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }
    
}
