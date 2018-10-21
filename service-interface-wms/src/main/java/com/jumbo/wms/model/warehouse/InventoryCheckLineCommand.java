package com.jumbo.wms.model.warehouse;

/**
 * 库存盘点明细
 * 
 * @author sjk
 * 
 */
public class InventoryCheckLineCommand extends InventoryCheckLine {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2799499530054588389L;

    public static final int LINE_TYPE_DISTRICT = 1;
    public static final int LINE_TYPE_LOCATION = 2;
    public static final int LINE_TYPE_OWNER = 3;
    public static final int LINE_TYPE_BRAND = 4;


    /**
     * 创建提交参数ID
     */
    private Long paramId;
    /**
     * 库区ID
     */
    private Long districtId;
    /**
     * 行类型
     */
    private Integer lineType;
    /**
     * 库位编码
     */
    private String locationCode;
    /**
     * 库区编码
     */
    private String districtCode;
    /**
     * 库区名称
     */
    private String districtName;
    /**
     * 店铺
     */
    private String owner;
    /**
     * 品牌
     */
    private Long brandId;

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Integer getLineType() {
        return lineType;
    }

    public void setLineType(Integer lineType) {
        this.lineType = lineType;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Long getParamId() {
        return paramId;
    }

    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

}
