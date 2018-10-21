package com.jumbo.wms.web.commond;

import java.util.List;

import com.jumbo.wms.model.BaseModel;

public class SkuCommand extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4885706329834082413L;
    // 商品编码
    private String code;
    // 商品名称
    private String name;
    // 条码
    private String barcode;
    // 货号
    private String supCode;
    // 关键属性
    private String keyProp;
    // 颜色尺码
    private String colorSize;
    // 是否Sn
    private Boolean isSn;
    // 多条码
    private List<String> barcodes;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSupCode() {
        return supCode;
    }

    public void setSupCode(String supCode) {
        this.supCode = supCode;
    }

    public String getKeyProp() {
        return keyProp;
    }

    public void setKeyProp(String keyProp) {
        this.keyProp = keyProp;
    }

    public String getColorSize() {
        return colorSize;
    }

    public void setColorSize(String colorSize) {
        this.colorSize = colorSize;
    }

    public List<String> getBarcodes() {
        return barcodes;
    }

    public void setBarcodes(List<String> barcodes) {
        this.barcodes = barcodes;
    }

    public Boolean getIsSn() {
        return isSn;
    }

    public void setIsSn(Boolean isSn) {
        this.isSn = isSn;
    }
}
