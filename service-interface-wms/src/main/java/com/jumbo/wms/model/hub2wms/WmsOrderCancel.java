package com.jumbo.wms.model.hub2wms;

import java.util.List;

import com.jumbo.wms.model.BaseModel;

public class WmsOrderCancel extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 7756211438665232775L;

    private String orderCode;

    private Integer status;

    private String channel;

    private List<WmsOrderCancelLine> orderLineNos;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<WmsOrderCancelLine> getOrderLineNos() {
        return orderLineNos;
    }

    public void setOrderLineNos(List<WmsOrderCancelLine> orderLineNos) {
        this.orderLineNos = orderLineNos;
    }

}
