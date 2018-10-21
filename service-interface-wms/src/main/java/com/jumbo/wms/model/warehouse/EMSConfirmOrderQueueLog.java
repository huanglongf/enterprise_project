package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * EMS订单确认队列
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_EMS_CONFIRM_ORDER_QUEUE_LOG")
public class EMSConfirmOrderQueueLog extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -3274655534952334972L;
    /**
     * PK
     */
    private Long id;
    /**
     * 作业单号
     */
    private String staCode;
    /**
     * 订单号
     */
    private String billNo;
    /**
     * 重量
     */
    private String weight;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 完成时间
     */
    private Date finishTime;
    /**
     * 大客户号
     */
    private String sysAccount;
    /**
     * 大客户密码
     */
    private String passWord;


    /**
     * 订单分类 (1:线下包裹 )
     * 
     * @return
     */
    private Integer type;

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SFCQQ", sequenceName = "S_EMS_CRM_ORDER_QUEUE_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SFCQQ")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_CODE", length = 60)
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "weight", length = 20)
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "BILL_NO", length = 50)
    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    @Column(name = "SYS_ACCOUNT", length = 50)
    public String getSysAccount() {
        return sysAccount;
    }

    public void setSysAccount(String sysAccount) {
        this.sysAccount = sysAccount;
    }

    @Column(name = "PASSWORD", length = 50)
    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Column(name = "FINISH_TIME")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

}
