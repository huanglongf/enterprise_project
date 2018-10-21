/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.jumbo.wms.model.warehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.User;

/**
 * ESPRIT门店信息
 * 
 * @author lihui
 * 
 */
@Entity
@Table(name = "T_WH_ESPRIT_STORE")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class EspritStore extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -5230740198900560818L;
    /**
     * PK
     */
    private Long id;
    /**
     * 门店名称
     */
    private String name;
    /**
     * 门店编码
     */
    private String code;
    /**
     * 城市编码
     */
    private String cityCode;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 联系人
     */
    private String contacts;
    /**
     * 电话
     */
    private String telephone;
    /**
     * 修改人
     */
    private User modifyUser;

    /**
     * 门店GLN
     * 
     * @return
     */
    private String gln;

    /**
     * 城市GLN
     * 
     * @return
     */
    private String cityGln;

    @Column(name = "CITY_GLN")
    public String getCityGln() {
        return cityGln;
    }

    public void setCityGln(String cityGln) {
        this.cityGln = cityGln;
    }

    @Column(name = "GLN")
    public String getGln() {
        return gln;
    }

    public void setGln(String gln) {
        this.gln = gln;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CG", sequenceName = "S_T_WH_BI_CHANNEL_GROUP", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "CITY_CODE")
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @Column(name = "PROVINCE")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "DISTRICT")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "CONTACTS")
    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @Column(name = "TELEPHONE")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODIFY_USER")
    public User getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(User modifyUser) {
        this.modifyUser = modifyUser;
    }



}
