package com.jumbo.wms.model.warehouse;

import java.io.Serializable;
import java.util.Date;

public class VMIBackOrder extends StaLine implements Serializable {

    private static final long serialVersionUID = 2480006161942125594L;
    
    private String district;//出库库区
    
    private String locationcode;//出库库位
    
    private String pickzonecode;//拣货区域编码
    
    private String skucode;//sku编码
    
    private String barcode;//条形码
    
    private String jmcode;//商品货号
    
    private String keyproperties;//关键属性
    
    private String strexpiredate;//过期时间
    
    //private String quantity;//数量
    
    private Date createtime;//创建时间
    
    private String housename;//仓库名称
    
    private String stacode;//作业单号
    
    private String skyqty;//数量

    private String vas;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLocationcode() {
        return locationcode;
    }

    public void setLocationcode(String locationcode) {
        this.locationcode = locationcode;
    }

    public String getPickzonecode() {
        return pickzonecode;
    }

    public void setPickzonecode(String pickzonecode) {
        this.pickzonecode = pickzonecode;
    }

    public String getSkucode() {
        return skucode;
    }

    public void setSkucode(String skucode) {
        this.skucode = skucode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getJmcode() {
        return jmcode;
    }

    public void setJmcode(String jmcode) {
        this.jmcode = jmcode;
    }

    public String getKeyproperties() {
        return keyproperties;
    }

    public void setKeyproperties(String keyproperties) {
        this.keyproperties = keyproperties;
    }

    public String getStrexpiredate() {
        return strexpiredate;
    }

    public void setStrexpiredate(String strexpiredate) {
        this.strexpiredate = strexpiredate;
    }

    /*public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }*/

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getHousename() {
        return housename;
    }

    public void setHousename(String housename) {
        this.housename = housename;
    }

    public String getStacode() {
        return stacode;
    }

    public void setStacode(String stacode) {
        this.stacode = stacode;
    }

    public String getSkyqty() {
        return skyqty;
    }

    public void setSkyqty(String skyqty) {
        this.skyqty = skyqty;
    }

    public String getVas() {
        return vas;
    }

    public void setVas(String vas) {
        this.vas = vas;
    }


}
