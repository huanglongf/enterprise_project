package com.jumbo.wms.model.vmi.cathKidstonData;

import java.io.Serializable;

public class CKReceiveConfrimCommand implements Serializable {


    private static final long serialVersionUID = -5749316143513032880L;

    private Long id;

    private int status;

    private String deliveryNo;

    private String dateTime;

    private String fromLocation;

    private String cartonId;

    private String toLoaction;

    private String store;

    private String upc;

    private Long quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLoaction() {
        return toLoaction;
    }

    public void setToLoaction(String toLoaction) {
        this.toLoaction = toLoaction;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getCartonId() {
        return cartonId;
    }

    public void setCartonId(String cartonId) {
        this.cartonId = cartonId;
    }


}
