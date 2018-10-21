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
@Entity
@Table(name = "T_ZTO_CONFIRM_ORDER_QUEUE")
public class ZtoConfirmOrderQueue extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * PK
     */
    private Long id;
    private String userName;
    private String passWord;
    private Date createTime;
    private Long exeCount;
    private String mailno;
    private String staCode;
    private String weight;
    private String whight;
    private String height;
    private String length;
    
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ZTOCQQ", sequenceName = "S_T_ZTO_CONFIRM_ORDER_QUEUE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ZTOCQQ")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "user_name", length = 75)
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Column(name = "pass_word", length = 50)
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @Column(name = "exe_count")
    public Long getExeCount() {
        return exeCount;
    }
    public void setExeCount(Long exeCount) {
        this.exeCount = exeCount;
    }
    @Column(name = "mailno", length = 50)
    public String getMailno() {
        return mailno;
    }
    public void setMailno(String mailno) {
        this.mailno = mailno;
    }
    @Column(name = "sta_code", length = 100)
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
    @Column(name = "p_whight", length = 20)
    public String getWhight() {
        return whight;
    }
    public void setWhight(String whight) {
        this.whight = whight;
    }
    @Column(name = "p_height", length = 20)
    public String getHeight() {
        return height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    @Column(name = "p_length", length = 20)
    public String getLength() {
        return length;
    }
    public void setLength(String length) {
        this.length = length;
    }
}
