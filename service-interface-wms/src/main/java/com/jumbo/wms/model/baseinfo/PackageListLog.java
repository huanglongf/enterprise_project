package com.jumbo.wms.model.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 装箱清单信息记得日志
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "t_wh_package_list_log")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class PackageListLog extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 2718405693695765966L;
    private Long id;
    private String trackingNo; // 退货订单号
    private String sender; // 发货人
    private String receiver; // 收件人
    private String mailingAddress; // 发件地址
    private String consigneeAddress; // 收件地址
    // private String delivery;
    private String receivingPhone; // 收货电话
    private String telephone; // 发货人电话
    private String staCode; // 销售订单号

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_package_list_log", sequenceName = "S_t_wh_package_list_log", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_package_list_log")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "tracking_No")
    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @Column(name = "sender")
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Column(name = "receiver")
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Column(name = "mailing_Address")
    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    @Column(name = "consignee_Address")
    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    @Column(name = "receiving_Phone")
    public String getReceivingPhone() {
        return receivingPhone;
    }

    public void setReceivingPhone(String receivingPhone) {
        this.receivingPhone = receivingPhone;
    }

    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "sta_Code")
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

}
