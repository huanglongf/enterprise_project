package com.jumbo.wms.model.command;

import java.io.Serializable;

public class Packages implements Serializable {


    /**
     * 
     */
    private static final long serialVersionUID = -3827516751713441661L;

    private String sidaiCode;

    private String peishiCode;

    private Integer qty;

    private String skuCode;

    private String wishCard;

    private String wishComment;

    public String getSidaiCode() {
        return sidaiCode;
    }

    public void setSidaiCode(String sidaiCode) {
        this.sidaiCode = sidaiCode;
    }

    public String getPeishiCode() {
        return peishiCode;
    }

    public void setPeishiCode(String peishiCode) {
        this.peishiCode = peishiCode;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getWishCard() {
        return wishCard;
    }

    public void setWishCard(String wishCard) {
        this.wishCard = wishCard;
    }

    public String getWishComment() {
        return wishComment;
    }

    public void setWishComment(String wishComment) {
        this.wishComment = wishComment;
    }
}
