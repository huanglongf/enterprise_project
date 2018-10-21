package com.jumbo.wms.model.vmi.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.mq.MqMsgBatch;

@Entity
@Table(name = "T_VMI_ORDER_OP_LOG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class VmiOrderOpLog extends BaseModel {



    /**
	 * 
	 */
    private static final long serialVersionUID = 7458035391029180415L;

    public Long id;

    /**
     * 操作人
     */
    public String operatorName;

    /**
     * 相关单据号
     */
    public String orderCode;

    /**
     * 相关单据创建时间
     */
    public Date orderCreateTime;

    /**
     * 店铺
     */
    public BiChannel shop;

    /**
     * 
     * 操作类型
     * 
     * 1:发送成功 2:订单确认成功 3:订单确认失败 4:出库成功 5:取消处理完成 6:未发送
     */
    public String opType;

    /**
     * 操作时间
     */
    public Date opTime;

    /**
     * 操作备注
     */
    public String memo;

    /**
     * 同步消息批ID t_mq_msg_batch
     */
    public MqMsgBatch msgBatchId;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_VMI_ORDER_OP_LOG", sequenceName = "S_T_VMI_ORDER_OP_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_VMI_ORDER_OP_LOG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "OPERATOR_NAME", length = 100)
    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    @Column(name = "ORDER_CODE", length = 50)
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "ORDER_CREATE_TIME")
    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    @Column(name = "OP_TYPE", length = 50)
    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    @Column(name = "OP_TIME")
    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    @Column(name = "MEMO", length = 200)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MSG_BATCH_ID")
    @Index(name = "IDX_MSG_BATCH_ID")
    public MqMsgBatch getMsgBatchId() {
        return msgBatchId;
    }

    public void setMsgBatchId(MqMsgBatch msgBatchId) {
        this.msgBatchId = msgBatchId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOP_ID")
    @Index(name = "IDX_ORDER_OP_SHOP_ID")
    public BiChannel getShop() {
        return shop;
    }

    public void setShop(BiChannel shop) {
        this.shop = shop;
    }
}
