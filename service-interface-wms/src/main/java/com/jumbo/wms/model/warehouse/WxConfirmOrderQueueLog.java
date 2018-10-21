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
 * 万象 订单确认日志
 * 
 * @author NCGZ-DZ-
 *
 */
@Entity
@Table(name = "T_WX_CONFIRM_ORDER_QUEUE_LOG")
public class WxConfirmOrderQueueLog extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 637760448009407445L;
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
    private String orderId;

    /**
     * 发货单号
     */
    private String orderCode;
    /**
     * 重量
     */
    private String weight;
    /**
     * 运单号
     */
    private String mailno;
    /**
     * 验证码
     */
    private String checkword;
    /**
     * 创建时间
     */
    private Date createTime;

    private String filter1;
    /**
     * 长
     */
    private String filter4;
    /**
     * 宽
     */
    private String filter2;
    /**
     * 高
     */
    private String filter3;

    private Date finishTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SFCQQ", sequenceName = "S_T_WX_CONFIRM_ORDER_QUEUE_LOG", allocationSize = 1)
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

    @Column(name = "weight", length = 20)
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Column(name = "mailno", length = 50)
    public String getMailno() {
        return mailno;
    }

    public void setMailno(String mailno) {
        this.mailno = mailno;
    }

    @Column(name = "checkword", length = 75)
    public String getCheckword() {
        return checkword;
    }

    public void setCheckword(String checkword) {
        this.checkword = checkword;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "filter4", length = 20)
    public String getFilter4() {
        return filter4;
    }

    public void setFilter4(String filter4) {
        this.filter4 = filter4;
    }

    @Column(name = "filter1", length = 20)
    public String getFilter1() {
        return filter1;
    }

    public void setFilter1(String filter1) {
        this.filter1 = filter1;
    }

    @Column(name = "filter2", length = 20)
    public String getFilter2() {
        return filter2;
    }

    public void setFilter2(String filter2) {
        this.filter2 = filter2;
    }

    @Column(name = "filter3", length = 20)
    public String getFilter3() {
        return filter3;
    }

    public void setFilter3(String filter3) {
        this.filter3 = filter3;
    }

    @Column(name = "FINISH_TIME")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Column(name = "order_code", length = 100)
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

}
