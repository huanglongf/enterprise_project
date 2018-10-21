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
 * yamato物流反馈hub队列日志表
 * 
 * @author jinggang.chen
 *
 */
@Entity
@Table(name = " T_YMT_CONFIRM_ORDER_QUEUE_LOG")
public class YamatoConfirmOrderQueueLog extends BaseModel {

    private static final long serialVersionUID = 4494138663259061408L;

    /**
     * PK
     */
    private Long id;

    /**
     * 作业单号
     */
    private String staCode;

    /**
     * 运单号
     */
    private String mailno;

    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 完成时间
     */
    private Date finishTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_YMTCQQ", sequenceName = "S_T_YMT_CONFIRM_ORDERQUEUE_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_YMTCQQ")
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

    @Column(name = "MAILNO", length = 50)
    public String getMailno() {
        return mailno;
    }

    public void setMailno(String mailno) {
        this.mailno = mailno;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="FINISH_TIME")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

}
