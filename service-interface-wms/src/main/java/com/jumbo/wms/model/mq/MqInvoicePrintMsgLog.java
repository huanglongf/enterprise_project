package com.jumbo.wms.model.mq;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_MQ_INVOICE_PRINT_MSG_LOG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MqInvoicePrintMsgLog extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7349026131215353365L;

    private Long id;

    private Date version;

    /**
     * 开票日期 税控机里发票打印日期
     */
    private String printDate;

    /**
     * 发票类型 正常: 表示蓝票 退票:表示红冲发票
     */
    private String invoiceType;

    /**
     * 发票代码 12位长度
     */
    private String invoiceCode;

    /**
     * 实际发票号 ８位长度且唯一
     */
    private String invoiceNo;

    /**
     * 收款单位 上海宝尊电子商务有限公司
     */
    private String payeeCompany;

    /**
     * 付款人 １）红冲发票该字段为：所红冲的蓝票发票代码＋该蓝票的发票号 ２）蓝票该列内容为：付款人
     */
    private String payer;

    /**
     * 开票金额 蓝票为正，红票为负
     */
    private BigDecimal amt;

    /**
     * 开票人
     */
    private String drawer;

    /**
     * 开票人
     */
    private String payee;

    /**
     * 开票机设备号
     */
    private String deviceNo;

    /**
     * 备注信息 红票：该字段为空 蓝票：该字段为系统发票编码/商品明细信息
     */
    private String memo;

    private Integer status; // 1:待处理 5:处理失败 10:处理成功

    /**
     * 处理备注
     */
    private String operateRemark;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_MQINVPRINTMSG", sequenceName = "S_T_MQ_INVOICE_PRINT_MSG_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MQINVPRINTMSG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Column(name = "PRINT_DATE", length = 50)
    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    @Column(name = "INVOICE_TYPE", length = 50)
    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    @Column(name = "INVOICE_CODE", length = 50)
    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    @Column(name = "INVOICE_NO", length = 50)
    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    @Column(name = "PAYEE_COMPANY", length = 50)
    public String getPayeeCompany() {
        return payeeCompany;
    }

    public void setPayeeCompany(String payeeCompany) {
        this.payeeCompany = payeeCompany;
    }

    @Column(name = "PAYER", length = 100)
    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    @Column(name = "AMT")
    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    @Column(name = "DRAWER", length = 50)
    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    @Column(name = "PAYEE", length = 50)
    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    @Column(name = "DEVICE_NO", length = 50)
    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    @Column(name = "MEMO", length = 500)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "OPERATE_REMARK", length = 1000)
    public String getOperateRemark() {
        return operateRemark;
    }

    public void setOperateRemark(String operateRemark) {
        this.operateRemark = operateRemark;
    }

}
