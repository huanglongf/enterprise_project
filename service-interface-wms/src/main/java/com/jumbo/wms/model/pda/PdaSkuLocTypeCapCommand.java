package com.jumbo.wms.model.pda;

public class PdaSkuLocTypeCapCommand extends PdaSkuLocTypeCap {

    /**
     * 
     */
    private static final long serialVersionUID = 9218178995758646227L;

    private String typeName;

    private String typeCode;

    private String skuName;


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

}
