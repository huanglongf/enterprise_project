package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.List;

public class ConsignTmsOrder implements Serializable {

    private static final long serialVersionUID = 3110074405726881244L;

    /**
     * 快递公司服务编码
     */
    private String tmsCode;
    /**
     * 运单编码,包裹上贴的运单的编码 注意：运单号会进行规则校验，新接入仓时，请在菜鸟资源中心配置
     */
    private String tmsOrderCode;
    /**
     * 包裹号，仓库管理包裹的id
     */
    private String packageCode;
    /**
     * 包裹重量，单位：克
     */
    private Integer packageWeight;
    /**
     * 包裹长度，单位：毫米
     */
    private Integer packageLength;
    /**
     * 包裹宽度，单位：毫米
     */
    private Integer packageWidth;
    /**
     * 包裹高度，单位：毫米
     */
    private Integer packageHeight;
    /**
     * 包裹的包材信息列表
     */
    private List<ConsignPackageMaterial> packageMaterialList;
    /**
     * 包裹里面的商品信息列表
     */
    private List<ConsignTmsItem> tmsItems;

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

    public List<ConsignPackageMaterial> getPackageMaterialList() {
        return packageMaterialList;
    }

    public void setPackageMaterialList(List<ConsignPackageMaterial> packageMaterialList) {
        this.packageMaterialList = packageMaterialList;
    }

    public List<ConsignTmsItem> getTmsItems() {
        return tmsItems;
    }

    public void setTmsItems(List<ConsignTmsItem> tmsItems) {
        this.tmsItems = tmsItems;
    }

}
