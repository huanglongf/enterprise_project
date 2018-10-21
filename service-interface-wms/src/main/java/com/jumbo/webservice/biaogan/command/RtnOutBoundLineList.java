package com.jumbo.webservice.biaogan.command;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "send")
public class RtnOutBoundLineList implements Serializable {

    private static final long serialVersionUID = -4065338090132580681L;
    public List<RtnOutBoundLineCommand> sku;

    public List<RtnOutBoundLineCommand> getSku() {
        return sku;
    }

    public void setSku(List<RtnOutBoundLineCommand> sku) {
        this.sku = sku;
    }



}
