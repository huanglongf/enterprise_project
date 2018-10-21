package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * O2O门店信息表
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_BI_SHOP_STORE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class ShopStoreInfo extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 8695663985575848155L;
    /**
     * id
     */
    private Long id;
    /**
     * 门店编码
     */
    private String code;
    /**
     * 门店名称
     */
    private String name;
    /**
     * 联系人
     */
    private String receiver;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date lastModifyTime;
    /**
     * version
     */
    private int version;
    /**
     * 国家
     */
    private String country;
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

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_BI_SHOP_STORE", sequenceName = "S_T_BI_SHOP_STORE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_BI_SHOP_STORE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE", length = 10)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "NAME", length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "RECEIVER", length = 20)
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Column(name = "TELEPHONE", length = 20)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "ADDRESS", length = 500)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "CREATE_TIME", length = 500)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "COUNTRY", length = 20)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "PROVINCE", length = 20)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "CITY", length = 20)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "DISTRICT", length = 20)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
