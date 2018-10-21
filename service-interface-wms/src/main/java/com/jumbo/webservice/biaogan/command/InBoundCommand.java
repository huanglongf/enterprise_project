package com.jumbo.webservice.biaogan.command;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RequestPurchaseInfo")
public class InBoundCommand implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = -1546448831730125262L;

    public String warehouseid = "";
    public String type = "";
    public String orderCode = "";
    public String customerId = "";

    public String ZDRQ = "";
    public String DHRQ = "";
    public String ZDR = "";
    public String BZ = "";

    @XmlElement(name = "products", type = InBoundLineList.class)
    public InBoundLineList list;

    public InBoundLineList getList() {
        return list;
    }

    public void setList(InBoundLineList list) {
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getZDRQ() {
        return ZDRQ;
    }

    public void setZDRQ(String zDRQ) {
        ZDRQ = zDRQ;
    }

    public String getDHRQ() {
        return DHRQ;
    }

    public void setDHRQ(String dHRQ) {
        DHRQ = dHRQ;
    }

    public String getZDR() {
        return ZDR;
    }

    public void setZDR(String zDR) {
        ZDR = zDR;
    }

    public String getBZ() {
        return BZ;
    }

    public void setBZ(String bZ) {
        BZ = bZ;
    }

    public String getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }


}
