package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 待创建队列-订单发货信息
 * 
 * @author jumbo
 * 
 */
@Entity
@Table(name = "T_WH_Q_STA_DELIVERY_INFO")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class QueueStaDeliveryInfo extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1825361979601548931L;
    /**
     * PK
     */
    private long id;
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
    /**
     * 送货地址
     */
    private String address;
    /**
     * 电话
     */
    private String telephone;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 联系人
     */
    private String receiver;
    /**
     * 邮编
     */
    private String zipcode;
    /**
     * 是否货到付款
     */
    private Boolean iscode;
    /**
     * 物流商简称
     */
    private String lpcode;
    /**
     * 运单号
     */
    private String trackingno;
    /**
     * 订单发货信息备注
     */
    private String remark;
    /**
     * 退货信息备注
     */
    private String inboundRemark;
    /**
     * 运送方式(快递附加服务)
     */
    private Integer transtype;
    /**
     * 快递时间限制（快递附加服务）
     */
    private Integer transtimetype;
    /**
     * 快递运单备注(快递附加服务)
     */
    private String transmemo;
    /**
     * 下单用户邮箱
     */
    private String orderusermail;
    /**
     * 下单用户帐号
     */
    private String orderusercode;
    /**
     * 退货发件人
     */
    private String sender;
    /**
     * 退货物流商
     */
    private String sendLpcode;
    /**
     * 退货人手机
     */
    private String sendMobile;
    /**
     * 退货运单号
     */
    private String sendTransNo;
    /**
     * 保价金额
     */
    private BigDecimal insuranceAmount;

    private String addressEn;
    private String cityEn;
    private String countryEn;
    private String districtEn;
    private String provinceEn;
    private String receiverEn;
    private String remarkEn;


    /**
     * 是否POS机刷卡
     */
    private Boolean isCodPos;
    /**
     * 自提便利店编码
     */
    private String convenienceStore;

    @Column(name = "IS_TRANS_COD_POS")
    public Boolean getIsCodPos() {
        return isCodPos;
    }

    public void setIsCodPos(Boolean isCodPos) {
        this.isCodPos = isCodPos;
    }



    @Column(name = "ADDRESS_EN")
    public String getAddressEn() {
        return addressEn;
    }

    public void setAddressEn(String addressEn) {
        this.addressEn = addressEn;
    }

    @Column(name = "CITY_EN")
    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    @Column(name = "COUNTRY_EN")
    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    @Column(name = "DISTRICT_EN")
    public String getDistrictEn() {
        return districtEn;
    }

    public void setDistrictEn(String districtEn) {
        this.districtEn = districtEn;
    }

    @Column(name = "PROVINCE_EN")
    public String getProvinceEn() {
        return provinceEn;
    }

    public void setProvinceEn(String provinceEn) {
        this.provinceEn = provinceEn;
    }

    @Column(name = "RECEIVER_EN")
    public String getReceiverEn() {
        return receiverEn;
    }

    public void setReceiverEn(String receiverEn) {
        this.receiverEn = receiverEn;
    }

    @Column(name = "REMARK_EN")
    public String getRemarkEn() {
        return remarkEn;
    }

    public void setRemarkEn(String remarkEn) {
        this.remarkEn = remarkEn;
    }

    @Column(name = "insurance_amount")
    public BigDecimal getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(BigDecimal insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    @Column(name = "sender", length = 100)
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Column(name = "send_lpcode", length = 100)
    public String getSendLpcode() {
        return sendLpcode;
    }

    public void setSendLpcode(String sendLpcode) {
        this.sendLpcode = sendLpcode;
    }

    @Column(name = "sender_mobile", length = 100)
    public String getSendMobile() {
        return sendMobile;
    }

    public void setSendMobile(String sendMobile) {
        this.sendMobile = sendMobile;
    }

    @Column(name = "send_trans_no", length = 100)
    public String getSendTransNo() {
        return sendTransNo;
    }

    public void setSendTransNo(String sendTransNo) {
        this.sendTransNo = sendTransNo;
    }

    @Id
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "country", length = 100)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "province", length = 100)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "city", length = 100)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "district", length = 100)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "address", length = 100)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "telephone", length = 100)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "mobile", length = 100)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "receiver", length = 100)
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Column(name = "zipcode", length = 100)
    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Column(name = "is_code")
    public Boolean getIscode() {
        return iscode;
    }

    public void setIscode(Boolean iscode) {
        this.iscode = iscode;
    }

    @Column(name = "lpcode", length = 100)
    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    @Column(name = "tracking_no", length = 100)
    public String getTrackingno() {
        return trackingno;
    }

    public void setTrackingno(String trackingno) {
        this.trackingno = trackingno;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "trans_type")
    public Integer getTranstype() {
        return transtype;
    }

    public void setTranstype(Integer transtype) {
        this.transtype = transtype;
    }

    @Column(name = "trans_time_type")
    public Integer getTranstimetype() {
        return transtimetype;
    }

    public void setTranstimetype(Integer transtimetype) {
        this.transtimetype = transtimetype;
    }

    @Column(name = "trans_memo")
    public String getTransmemo() {
        return transmemo;
    }

    public void setTransmemo(String transmemo) {
        this.transmemo = transmemo;
    }

    @Column(name = "order_user_mail", length = 100)
    public String getOrderusermail() {
        return orderusermail;
    }

    public void setOrderusermail(String orderusermail) {
        this.orderusermail = orderusermail;
    }

    @Column(name = "order_user_code", length = 100)
    public String getOrderusercode() {
        return orderusercode;
    }

    public void setOrderusercode(String orderusercode) {
        this.orderusercode = orderusercode;
    }

    @Column(name = "inbound_remark", length = 100)
    public String getInboundRemark() {
        return inboundRemark;
    }

    public void setInboundRemark(String inboundRemark) {
        this.inboundRemark = inboundRemark;
    }

    @Column(name = "CONVENIENCE_STORE", length = 20)
    public String getConvenienceStore() {
        return convenienceStore;
    }

    public void setConvenienceStore(String convenienceStore) {
        this.convenienceStore = convenienceStore;
    }
}
