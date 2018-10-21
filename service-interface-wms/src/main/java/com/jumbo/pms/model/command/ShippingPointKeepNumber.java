package com.jumbo.pms.model.command;

import java.io.Serializable;

/**
 * 存储在MongoDB中的对象 作为ShippingPoint每次循环的计数器
 * 
 * @author Administrator
 * 
 */
public class ShippingPointKeepNumber implements Serializable {

    private static final long serialVersionUID = 765559585563919489L;

    private Long id;

    private Long keepNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKeepNumber() {
        return keepNumber;
    }

    public void setKeepNumber(Long keepNumber) {
        this.keepNumber = keepNumber;
    }

    @Override
    public String toString() {
        return "ShippingPointKeepNumber [id=" + id + ", keepNumber=" + keepNumber + "]";
    }
}
