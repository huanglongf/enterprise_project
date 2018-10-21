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
@Table(name = "T_EMS_CONFIRM_ORDER_QUEUE")
public class EMSConfirmOrderQueue extends BaseModel {
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
     * 物品长度
     */
    private String length;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 大客户号
     */
    private String sysAccount;
    /**
     * 大客户密码
     */
    private String passWord;
    /**
     * 执行次数
     */
    private Long exeCount;
    
    /**
     * 单据标识
     */
    private Long order_flag;

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
    @SequenceGenerator(name = "SEQ_SFCQQ", sequenceName = "S_T_EMS_CONFIRM_ORDER_QUEUE", allocationSize = 1)
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

    @Column(name = "EXE_COUNT")
    public Long getExeCount() {
        return exeCount;
    }

    public void setExeCount(Long exeCount) {
        this.exeCount = exeCount;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    @Column(name = "ORDER_FLAG")
    public Long getOrder_flag() {
        return order_flag;
    }

    public void setOrder_flag(Long order_flag) {
        this.order_flag = order_flag;
    }

    
}
