package com.jumbo.webservice.nike.command;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA. User: hlz Date: 2010-9-8 Time: 18:04:38 To change this template use
 * File | Settings | File Templates.
 */
public class InventoryObj implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1817509659522598240L;
    private String upcCode;
    private Integer quantity;


    public String getUpcCode() {
        return upcCode;
    }

    public void setUpcCode(String upcCode) {
        this.upcCode = upcCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "：  InventoryObj [quantity=" + quantity + ", upcCode=" + upcCode + "]";
    }
}
