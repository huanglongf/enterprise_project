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
 * SF 订单确认队列
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_SF_ORDER_FILTER_LOG")
public class SfOrderFilterLog extends BaseModel {

    private static final long serialVersionUID = -5925962720018635221L;

    /**
     * PK
     */
    private Long id;
    /**
     * 作业单号
     */
    private String staCode;
    /**
     * 是否接收
     */
    private Boolean isAccept;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 运单号
     */
    private String mailno;
    /**
     * 创建时间
     */
    private Date createTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SFCQQ", sequenceName = "S_T_SF_ORDER_FILTER_LOG", allocationSize = 1)
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

    @Column(name = "order_id", length = 100)
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "mailno", length = 50)
    public String getMailno() {
        return mailno;
    }

    public void setMailno(String mailno) {
        this.mailno = mailno;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "IS_ACCEPT")
    public Boolean getIsAccept() {
        return isAccept;
    }

    public void setIsAccept(Boolean isAccept) {
        this.isAccept = isAccept;
    }
}
