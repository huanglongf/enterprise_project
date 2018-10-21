package com.jumbo.wms.model;

import javax.persistence.Column;

public class MongoDBSfLogistics extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -281023624979452730L;
    /**
     * 城市代码
     */
    private String code;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /**
     * 省
     */
    private String province;

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "district")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "Province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }


}
