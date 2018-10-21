package com.jumbo.wms.model.warehouse;

import java.util.Date;

import com.jumbo.wms.model.BaseModel;

public class MsSnReportCommand extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1473352581091201777L;

    private String mobile;

    private String sn;

    private String skuName;

    private Date purchaseDate;

    private String purchaseSource;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseSource() {
        return purchaseSource;
    }

    public void setPurchaseSource(String purchaseSource) {
        this.purchaseSource = purchaseSource;
    }
}
