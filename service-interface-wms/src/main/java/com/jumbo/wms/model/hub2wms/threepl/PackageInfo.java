package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.List;

public class PackageInfo implements Serializable {

    private static final long serialVersionUID = -8040452788007638137L;

    /**
     * 快递公司服务编码
     */
    private String tmsCode;
    /**
     * 运单编码
     */
    private String tmsOrderCode;
    /**
     * 包裹号
     */
    private String packageCode;
    /**
     * 包裹质量，单位：克
     */
    private Integer packageWeight;
    /**
     * 包裹长度，单位：毫米
     */
    private Integer packageLength;
    /**
     * 包裹宽度,单位：毫米
     */
    private Integer packageWidth;
    /**
     * 包裹高度，单位：毫米
     */
    private Integer packageHeight;
    /**
     * 包裹里面的商品信息列表
     */
    private List<PackageItem> packageItemItems;

    public String getTmsCode() {
        return tmsCode;
    }

    public void setTmsCode(String tmsCode) {
        this.tmsCode = tmsCode;
    }

    public String getTmsOrderCode() {
        return tmsOrderCode;
    }

    public void setTmsOrderCode(String tmsOrderCode) {
        this.tmsOrderCode = tmsOrderCode;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public Integer getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(Integer packageWeight) {
        this.packageWeight = packageWeight;
    }

    public Integer getPackageLength() {
        return packageLength;
    }

    public void setPackageLength(Integer packageLength) {
        this.packageLength = packageLength;
    }

    public Integer getPackageWidth() {
        return packageWidth;
    }

    public void setPackageWidth(Integer packageWidth) {
        this.packageWidth = packageWidth;
    }

    public Integer getPackageHeight() {
        return packageHeight;
    }

    public void setPackageHeight(Integer packageHeight) {
        this.packageHeight = packageHeight;
    }

    public List<PackageItem> getPackageItemItems() {
        return packageItemItems;
    }

    public void setPackageItemItems(List<PackageItem> packageItemItems) {
        this.packageItemItems = packageItemItems;
    }
}
