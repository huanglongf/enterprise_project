package com.jumbo.wms.model.mongodb;

import java.util.Date;
import java.util.List;

import com.jumbo.wms.model.BaseModel;

public class AsnReceive extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -3031260819793741170L;

    private Long id;

    private String code;

    private String slipCode;

    private Date createTime;

    private List<OrderLine> orderLine;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<OrderLine> getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(List<OrderLine> orderLine) {
        this.orderLine = orderLine;
    }

}
