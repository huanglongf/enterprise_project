package com.jumbo.webservice.sfwarehouse.command;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author jinlong.ke
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "wmsInventoryAdjustPushInfo")
public class WmsInventoryAdjustPushInfoRequest implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -7024484860199022007L;
    @XmlElement
    private String company;
    @XmlElement
    private String warehouse;
    @XmlElement
    private WmsInventoryAdjustPushInfoAdjustList adjustlist;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public WmsInventoryAdjustPushInfoAdjustList getAdjustlist() {
        return adjustlist;
    }

    public void setAdjustlist(WmsInventoryAdjustPushInfoAdjustList adjustlist) {
        this.adjustlist = adjustlist;
    }
}
