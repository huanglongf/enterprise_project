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
 * CNP订单确认队列
 * 
 * @author xiaolong.fei
 * 
 */
@Entity
@Table(name = "T_CNP_CONFIRM_ORDER_QUEUE")
public class CNPConfirmOrderQueue extends BaseModel {
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
    private String orderCode;
    /**
     * 平台订单号
     */
    private String tradeId;
    /**
     * 订单来源
     */
    private String orderSource;
    /**
     * 二级物流商编码， 菜鸟获取，用户回传
     */
    private String tmsCode;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 执行次数
     */
    private Long exeCount;

    /**
     * 仓库ID
     */
    private Long ouId;

    /**
     * 作业单ID
     */
    private Long staId;

    /**
     * 包裹号
     */
    private Integer packageNo;
    /**
     * 包裹数
     */
    private Integer packageCount;
    /**
     * 重量
     */
    private String packageWeight;

    /**
     * 快递单号
     */
    private String mailNo;

    /**
     * 菜鸟返回的单号
     */
    private String logisticsOrderCode;

    /**
     * 重量
     */
    private String weight;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CNP_CONFIRM", sequenceName = "s_T_CNP_CONFIRM_ORDER_QUEUE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CNP_CONFIRM")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "order_code")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "trade_id")
    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    @Column(name = "order_source")
    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    @Column(name = "tms_code")
    public String getTmsCode() {
        return tmsCode;
    }

    public void setTmsCode(String tmsCode) {
        this.tmsCode = tmsCode;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "EXE_COUNT")
    public Long getExeCount() {
        return exeCount;
    }

    public void setExeCount(Long exeCount) {
        this.exeCount = exeCount;
    }

    @Column(name = "ou_id")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Column(name = "package_no")
    public Integer getPackageNo() {
        return packageNo;
    }

    public void setPackageNo(Integer packageNo) {
        this.packageNo = packageNo;
    }

    @Column(name = "package_count")
    public Integer getPackageCount() {
        return packageCount;
    }

    public void setPackageCount(Integer packageCount) {
        this.packageCount = packageCount;
    }

    @Column(name = "package_weight")
    public String getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(String packageWeight) {
        this.packageWeight = packageWeight;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "mail_no")
    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    @Column(name = "logistics_order_code")
    public String getLogisticsOrderCode() {
        return logisticsOrderCode;
    }

    public void setLogisticsOrderCode(String logisticsOrderCode) {
        this.logisticsOrderCode = logisticsOrderCode;
    }

    @Column(name = "weight")
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

}
