package com.jumbo.mongo.entry;

public class Inventory extends BaseEntity {

    private String sku;
    private String owner;
    private String whCode;
    private Long qty;
    private Long ocpQty;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getOcpQty() {
        return ocpQty;
    }

    public void setOcpQty(Long ocpQty) {
        this.ocpQty = ocpQty;
    }

}
