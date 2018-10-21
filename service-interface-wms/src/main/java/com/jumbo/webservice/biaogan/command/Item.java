package com.jumbo.webservice.biaogan.command;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "item")
public class Item implements Serializable {

    private static final long serialVersionUID = 8207993215748586951L;

    /**
     * SKU
     */
    private String spuCode = "";

    /**
     * 商品名称
     */
    private String itemName = "";

    /**
     * 商品数量
     */
    private Long itemCount = 0L;

    /**
     * 商品金额
     */
    private BigDecimal itemValue = new BigDecimal(0);

    public String getSpuCode() {
        return spuCode;
    }

    public void setSpuCode(String spuCode) {
        this.spuCode = spuCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getItemCount() {
        return itemCount;
    }

    public void setItemCount(Long itemCount) {
        this.itemCount = itemCount;
    }

    public BigDecimal getItemValue() {
        return itemValue;
    }

    public void setItemValue(BigDecimal itemValue) {
        this.itemValue = itemValue;
    }


}
