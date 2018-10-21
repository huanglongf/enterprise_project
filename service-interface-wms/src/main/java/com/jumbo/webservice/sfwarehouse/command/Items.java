package com.jumbo.webservice.sfwarehouse.command;

import java.io.Serializable;
import java.util.List;

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
@XmlRootElement(name = "item")
public class Items implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 813874982278935199L;
    @XmlElement
    private List<Item> item;// <item>商品编码</item>

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

}
