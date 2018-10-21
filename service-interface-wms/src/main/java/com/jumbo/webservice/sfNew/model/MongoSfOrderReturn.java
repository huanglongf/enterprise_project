package com.jumbo.webservice.sfNew.model;

import java.util.Date;

import com.jumbo.wms.model.BaseModel;

/**
 * 顺风下单反馈实体Mongo
 * 
 * 
 */

public class MongoSfOrderReturn extends BaseModel {

    private static final long serialVersionUID = -7542431373791362151L;

    private String Id;
    /**
     * 到件方联系人
     */
    private String dContact;
    /**
     * 到件方座机
     */
    private String dTel;



    /**
     * 到件方所在省份
     */
    private String dProvince;
    /**
     * 到件方所属城市名称
     */
    private String dCity;
    /**
     * 到件方地址
     */
    private String dAddress;

    private String content;// 报文内容

    private Date createTime;// 创建时间

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getdContact() {
        return dContact;
    }

    public void setdContact(String dContact) {
        this.dContact = dContact;
    }

    public String getdTel() {
        return dTel;
    }

    public void setdTel(String dTel) {
        this.dTel = dTel;
    }

    public String getdProvince() {
        return dProvince;
    }

    public void setdProvince(String dProvince) {
        this.dProvince = dProvince;
    }

    public String getdCity() {
        return dCity;
    }

    public void setdCity(String dCity) {
        this.dCity = dCity;
    }

    public String getdAddress() {
        return dAddress;
    }

    public void setdAddress(String dAddress) {
        this.dAddress = dAddress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
