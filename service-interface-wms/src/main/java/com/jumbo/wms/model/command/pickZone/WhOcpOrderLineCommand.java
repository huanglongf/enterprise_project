package com.jumbo.wms.model.command.pickZone;

import java.io.Serializable;

import com.jumbo.wms.model.pickZone.WhOcpOrderLine;



public class WhOcpOrderLineCommand extends WhOcpOrderLine implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4259379842436927901L;

    private Long ocpId;

    private Long skuId;

    private Long ouId;

    private String ocpCode;


    public Long getOcpId() {
        return ocpId;
    }

    public void setOcpId(Long ocpId) {
        this.ocpId = ocpId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Override
    public String getOcpCode() {
        return ocpCode;
    }

    @Override
    public void setOcpCode(String ocpCode) {
        this.ocpCode = ocpCode;
    }

    
}
