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
@XmlRootElement(name = "wmsMerchantCatalogBatchRequest")
public class WmsMerchantCatalogBatchRequest implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6084407129753279138L;
    @XmlElement
    private String checkword;
    @XmlElement
    private String company;
    @XmlElement
    private String interface_action_code;
    @XmlElement
    private Items itemlist;

    public String getCheckword() {
        return checkword;
    }

    public void setCheckword(String checkword) {
        this.checkword = checkword;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


    public Items getItemlist() {
        return itemlist;
    }

    public void setItemlist(Items itemlist) {
        this.itemlist = itemlist;
    }

    public String getInterface_action_code() {
        return interface_action_code;
    }

    public void setInterface_action_code(String interfaceActionCode) {
        interface_action_code = interfaceActionCode;
    }


}
