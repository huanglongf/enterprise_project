package com.jumbo.wms.model.warehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_IMPORT_PRINT")
public class ImportPrintData extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -4171261872079553913L;
    private Long id;
    private String sender;
    private String senderTel;
    private String senderAddress;
    private String receiver;
    private String mobile;
    private String address;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_IMPORT_PRINT", sequenceName = "S_T_IMPORT_PRINT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_IMPORT_PRINT")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SENDER", length = 50)
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Column(name = "SENDER_TEL", length = 50)
    public String getSenderTel() {
        return senderTel;
    }

    public void setSenderTel(String senderTel) {
        this.senderTel = senderTel;
    }

    @Column(name = "SENDER_ADDRESS", length = 50)
    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    @Column(name = "RECEIVER", length = 50)
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Column(name = "MOBILE", length = 50)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "ADDRESS", length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
