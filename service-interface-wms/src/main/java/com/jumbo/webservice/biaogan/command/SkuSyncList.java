package com.jumbo.webservice.biaogan.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "skus")
public class SkuSyncList implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4565679939841084856L;
    @XmlElement(name = "sku", type = SkuSyncCommand.class)
    public List<SkuSyncCommand> skus;

    public List<SkuSyncCommand> getSkus() {
        if (skus == null) {
            skus = new ArrayList<SkuSyncCommand>();
        }
        return skus;
    }

    public void setSkus(List<SkuSyncCommand> skus) {
        this.skus = skus;
    }
}
