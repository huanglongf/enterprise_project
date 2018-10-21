package com.jumbo.wms.model.hub2wms;

import java.util.Date;
import java.util.List;

import com.jumbo.wms.model.BaseModel;

/**
 * 库存流水
 * 
 * @author cheng.su
 * 
 */
public class WmsInvLog extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -3733124524856462851L;
    /**
     * 商品
     */
    private String sku;
    /**
     * 数量
     */
    private long qty;
    /**
     * Sn号
     */
    private List<String> sns;
    /**
     * 渠道信息
     */
    private String owner;
    /**
     * 操作时间
     */
    private Date tranTime;
    /**
     * 批次号
     */
    private String btachCode;
    /**
     * 商品批次
     */
    private String barchNo;
    /**
     * 库存状态
     */
    private String invStatus;
    /**
     * 仓库编码
     */
    private String warehouceCode;

    /**
     * 是否可销售
     */
    private Boolean marketability;
    
    private String lineNo;
    

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    public List<String> getSns() {
        return sns;
    }

    public void setSns(List<String> sns) {
        this.sns = sns;
    }

    public String getBtachCode() {
        return btachCode;
    }

    public void setBtachCode(String btachCode) {
        this.btachCode = btachCode;
    }

    public String getBarchNo() {
        return barchNo;
    }

    public void setBarchNo(String barchNo) {
        this.barchNo = barchNo;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public String getWarehouceCode() {
        return warehouceCode;
    }

    public void setWarehouceCode(String warehouceCode) {
        this.warehouceCode = warehouceCode;
    }

    public Boolean getMarketability() {
        return marketability;
    }

    public void setMarketability(Boolean marketability) {
        this.marketability = marketability;
    }


}
