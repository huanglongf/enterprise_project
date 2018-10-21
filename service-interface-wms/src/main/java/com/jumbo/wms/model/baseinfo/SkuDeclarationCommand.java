package com.jumbo.wms.model.baseinfo;

public class SkuDeclarationCommand extends SkuDeclaration{

    /**
     * 
     */
    private static final long serialVersionUID = 3892877132898858311L;

    private Long skuOriginDeclarationId;

    private String orogin;

    private String isDiscountName;

    private String statusName;

    private String origin;

    public Long getSkuOriginDeclarationId() {
        return skuOriginDeclarationId;
    }

    public void setSkuOriginDeclarationId(Long skuOriginDeclarationId) {
        this.skuOriginDeclarationId = skuOriginDeclarationId;
    }

    public String getOrogin() {
        return orogin;
    }

    public void setOrogin(String orogin) {
        this.orogin = orogin;
    }

    public String getIsDiscountName() {
        return isDiscountName;
    }

    public void setIsDiscountName(String isDiscountName) {
        this.isDiscountName = isDiscountName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }


}
