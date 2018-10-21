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
@Table(name = "T_RFD_CONFIRM_ORDER_QUEUE")
public class RfdConfirmOrderQueue extends BaseModel {

	private static final long serialVersionUID = 1342809217044330881L;
	
	private Long id;            // PK
    private Date createTime;    // 创建时间
    private Long exeCount;      // 执行次数
    private String transNo;     // 运单号
    private String staCode;     // 作业单号
    private String weight;      // 重量
    private String height;      // 高度
    private String length;      // 长度
    private String whight;      // 宽
    
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_RFDCQQ", sequenceName = "S_T_RFD_CONFIRM_ORDER_QUEUE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RFDCQQ")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Column(name="create_time")
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @Column(name="exe_count")
    public Long getExeCount() {
        return exeCount;
    }
    public void setExeCount(Long exeCount) {
        this.exeCount = exeCount;
    }
    @Column(name="trans_no")
    public String getTransNo() {
        return transNo;
    }
    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }
    @Column(name="sta_code")
    public String getStaCode() {
        return staCode;
    }
    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }
    @Column(name="weight")
    public String getWeight() {
        return weight;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }
    @Column(name="height")
    public String getHeight() {
        return height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    @Column(name="length")
    public String getLength() {
        return length;
    }
    public void setLength(String length) {
        this.length = length;
    }
    @Column(name="whight")
    public String getWhight() {
        return whight;
    }
    public void setWhight(String whight) {
        this.whight = whight;
    }
}
