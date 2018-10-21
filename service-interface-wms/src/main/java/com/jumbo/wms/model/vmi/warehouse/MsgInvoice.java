package com.jumbo.wms.model.vmi.warehouse;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;

@Entity
@Table(name = "T_WH_MSG_INVOICE")
public class MsgInvoice extends BaseModel {

    private static final long serialVersionUID = 1587783284717773239L;
    /**
     * PK
     */
    private Long id;
    /**
     * STA CODE
     */
    private String staCode;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 付款人
     */
    private String payer;
    /**
     * 项目2
     */
    private String item2;
    /**
     * 单价2
     */
    private BigDecimal unitPrice2 = new BigDecimal(0.00);
    /**
     * 数量2
     */
    private Long qty2 = 1L;
    /**
     * 合计2
     */
    private BigDecimal amt2 = new BigDecimal(0.00);
    /**
     * 项目3
     */
    private String item3;
    /**
     * 单价3
     */
    private BigDecimal unitPrice3 = new BigDecimal(0.00);
    /**
     * 数量3
     */
    private Long qty3 = 1L;
    /**
     * 合计3
     */
    private BigDecimal amt3 = new BigDecimal(0.00);
    /**
     * 项目1
     */
    private String item1;
    /**
     * 单价1
     */
    private BigDecimal unitPrice1;
    /**
     * 数量1
     */
    private Long qty1;
    /**
     * 合计1
     */
    private BigDecimal amt1;
    /**
     * 备注
     */
    private String remark;
    /**
     * 开票日期
     */
    private String invoiceTime;
    /**
     * 状态
     */
    private DefaultStatus status;
    /**
     * 来源
     */
    private String source;
    /**
     * 收款人
     */
    private String payee;
    /**
     * 开票人
     */
    private String drawer;

    private String sourceWh;

    private Date updateTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_MSG_INC", sequenceName = "S_T_WH_MSG_INVOICE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MSG_INC")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "PAYER", length = 50)
    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    @Column(name = "ITEM1", length = 50)
    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    @Column(name = "UNIT_PRICE1")
    public BigDecimal getUnitPrice1() {
        return unitPrice1;
    }

    public void setUnitPrice1(BigDecimal unitPrice1) {
        this.unitPrice1 = unitPrice1;
    }

    @Column(name = "QTY1")
    public Long getQty1() {
        return qty1;
    }

    public void setQty1(Long qty1) {
        this.qty1 = qty1;
    }

    @Column(name = "AMT1")
    public BigDecimal getAmt1() {
        return amt1;
    }

    public void setAmt1(BigDecimal amt1) {
        this.amt1 = amt1;
    }

    @Column(name = "REMARK", length = 500)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "STA_CODE", length = 50)
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "INVOICE_TIME", length = 50)
    public String getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(String invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "SOURCE", length = 50)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "ITEM2", length = 300)
    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    @Column(name = "UNIT_PRICE2")
    public BigDecimal getUnitPrice2() {
        return unitPrice2;
    }

    public void setUnitPrice2(BigDecimal unitPrice2) {
        this.unitPrice2 = unitPrice2;
    }

    @Column(name = "QTY2")
    public Long getQty2() {
        return qty2;
    }

    public void setQty2(Long qty2) {
        this.qty2 = qty2;
    }

    @Column(name = "AMT2")
    public BigDecimal getAmt2() {
        return amt2;
    }

    public void setAmt2(BigDecimal amt2) {
        this.amt2 = amt2;
    }

    @Column(name = "ITEM3", length = 300)
    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    @Column(name = "UNIT_PRICE3")
    public BigDecimal getUnitPrice3() {
        return unitPrice3;
    }

    public void setUnitPrice3(BigDecimal unitPrice3) {
        this.unitPrice3 = unitPrice3;
    }

    @Column(name = "QTY3")
    public Long getQty3() {
        return qty3;
    }

    public void setQty3(Long qty3) {
        this.qty3 = qty3;
    }

    @Column(name = "AMT3")
    public BigDecimal getAmt3() {
        return amt3;
    }

    public void setAmt3(BigDecimal amt3) {
        this.amt3 = amt3;
    }

    @Column(name = "PAYEE", length = 30)
    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    @Column(name = "DRAWER", length = 30)
    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    @Column(name = "source_wh", length = 30)
    public String getSourceWh() {
        return sourceWh;
    }

    public void setSourceWh(String sourceWh) {
        this.sourceWh = sourceWh;
    }

    @Column(name = "UPADATE_TIME")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
