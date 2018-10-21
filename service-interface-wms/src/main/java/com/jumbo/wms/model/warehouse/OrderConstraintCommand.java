package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
import java.util.Date;

import com.jumbo.wms.model.BaseModel;

public class OrderConstraintCommand extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2577096003607917613L;
    private Long id;
    private Date createTime = new Date();
    private Date outputTime; // 输出文件时间
    private String outputFile; // 输出文件(目前文件直接被导出在用户终端,由用户在本地保存,所以该字段填写导出时的时间戳. 以后可以扩展为固定格式的文件名.)
    private Integer outputCount = 0; // 实际输出文件次数
    private Date feedbackTime; // 实际发票号回填时间
    private String code; // 实体系统编码
    private String sysRemark;
    private String soInvoiceCode; // 关联的系统发票编码
    private Integer autoType; // 开票方式：1.自动　2.手动
    private String realInvoiceNo; // 真实发票号(税控机生成的发票号)
    private Date invoiceDate; // 开票日期(YYYY/MM/DD)
    private String payer; // 付款单位(最多200个字符)
    private String item1; // 经营项目1(最多20个字符)
    private BigDecimal unitPrice1; // 单价1(保留2位小数)
    private Integer qty1; // 数量1
    private BigDecimal amt1; // 金额1(保留2位小数)
    private String item2; // 经营项目2
    private BigDecimal unitPrice2; // 单价2
    private Integer qty2; // 数量2
    private BigDecimal amt2; // 金额2
    private String item3; // 经营项目3
    private BigDecimal unitPrice3; // 单价3
    private Integer qty3; // 数量3
    private BigDecimal amt3; // 金额3
    private String memo; // 备注(最多252个字符)
    private String payee; // 收款人(最多10个字符)
    private String drawer; // 开票人(最多10个字符)
    private Boolean isBillingInvoiceDetail; // 发票是否需要备注明细(税控发票用,标识发票备注栏中是否需要记录商品明细)
    private Long soId;// 关联订单编号
    private String reversedRealNo; // 红冲的蓝票实际发票号
    private Integer rbType; // 发票红蓝类型(红字发票,蓝字发票)
    /**
     * 机打发票类型
     * 
     * see @com.erry.service.sales.util@InvoiceTaxType
     */
    private Integer sourceType;
    private OrderConstraintCommand redInvoiceTax; // 没有退换货的红冲发票对应的蓝票,对应的红票
    private OrderConstraintCommand reversedBlueTax; // 所红冲的蓝票
    private Boolean isReversed; // 是否已被冲销
    private Long commonLogId;
    private String invoiceType;// 标记虚票还是实票


    /**
	 * 
	 */
    private Long skuId;
    /**
     * 指同步k3后是否需要勾稽，目前在接口中我们仅仅处理一张销售订单只开一张发票时的勾稽关系
     * 
     * 一单多票，以及退换货产生的发票，以及退票原因产生的发票，均同步K3但不处理自动勾稽
     */
    private Boolean isNeedGouJi = Boolean.FALSE;

    /**
     * 是否已经同步过K3，目前暂时用来控制任务注册，即当注册了该K3同步任务后，该标记为TRUE
     */
    private Boolean isSynced = Boolean.FALSE;

    /**
     * 原始订单code,记录原始的销售订单，在退换过程中或退票过程中产生的所有发票该字段均记录原始销售订单code
     */
    private String rootSoCode;
    /**
     * 如果是退换货申请相关的发票，则记录其相关的退换货申请
     */
    private String feedbackOperator;



    public Long getCommonLogId() {
        return commonLogId;
    }

    public void setCommonLogId(Long commonLogId) {
        this.commonLogId = commonLogId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getOutputTime() {
        return outputTime;
    }

    public void setOutputTime(Date outputTime) {
        this.outputTime = outputTime;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public Date getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Date feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSysRemark() {
        return sysRemark;
    }

    public void setSysRemark(String sysRemark) {
        this.sysRemark = sysRemark;
    }

    public String getSoInvoiceCode() {
        return soInvoiceCode;
    }

    public void setSoInvoiceCode(String soInvoiceCode) {
        this.soInvoiceCode = soInvoiceCode;
    }

    public Integer getAutoType() {
        return autoType;
    }

    public void setAutoType(Integer autoType) {
        this.autoType = autoType;
    }

    public String getRealInvoiceNo() {
        return realInvoiceNo;
    }

    public void setRealInvoiceNo(String realInvoiceNo) {
        this.realInvoiceNo = realInvoiceNo;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public BigDecimal getUnitPrice1() {
        return unitPrice1;
    }

    public void setUnitPrice1(BigDecimal unitPrice1) {
        this.unitPrice1 = unitPrice1;
    }

    public Integer getQty1() {
        return qty1;
    }

    public void setQty1(Integer qty1) {
        this.qty1 = qty1;
    }

    public BigDecimal getAmt1() {
        return amt1;
    }

    public void setAmt1(BigDecimal amt1) {
        this.amt1 = amt1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public BigDecimal getUnitPrice2() {
        return unitPrice2;
    }

    public void setUnitPrice2(BigDecimal unitPrice2) {
        this.unitPrice2 = unitPrice2;
    }

    public Integer getQty2() {
        return qty2;
    }

    public void setQty2(Integer qty2) {
        this.qty2 = qty2;
    }

    public BigDecimal getAmt2() {
        return amt2;
    }

    public void setAmt2(BigDecimal amt2) {
        this.amt2 = amt2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public BigDecimal getUnitPrice3() {
        return unitPrice3;
    }

    public void setUnitPrice3(BigDecimal unitPrice3) {
        this.unitPrice3 = unitPrice3;
    }

    public Integer getQty3() {
        return qty3;
    }

    public void setQty3(Integer qty3) {
        this.qty3 = qty3;
    }

    public BigDecimal getAmt3() {
        return amt3;
    }

    public void setAmt3(BigDecimal amt3) {
        this.amt3 = amt3;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    public Integer getOutputCount() {
        return outputCount;
    }

    public void setOutputCount(Integer outputCount) {
        this.outputCount = outputCount;
    }

    public Boolean getIsBillingInvoiceDetail() {
        return isBillingInvoiceDetail;
    }

    public void setIsBillingInvoiceDetail(Boolean isBillingInvoiceDetail) {
        this.isBillingInvoiceDetail = isBillingInvoiceDetail;
    }

    public String getReversedRealNo() {
        return reversedRealNo;
    }

    public void setReversedRealNo(String reversedRealNo) {
        this.reversedRealNo = reversedRealNo;
    }

    public Integer getRbType() {
        return rbType;
    }

    public void setRbType(Integer rbType) {
        this.rbType = rbType;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public OrderConstraintCommand getRedInvoiceTax() {
        return redInvoiceTax;
    }

    public void setRedInvoiceTax(OrderConstraintCommand redInvoiceTax) {
        this.redInvoiceTax = redInvoiceTax;
    }

    public OrderConstraintCommand getReversedBlueTax() {
        return reversedBlueTax;
    }

    public void setReversedBlueTax(OrderConstraintCommand reversedBlueTax) {
        this.reversedBlueTax = reversedBlueTax;
    }

    public Boolean getIsReversed() {
        return isReversed;
    }

    public void setIsReversed(Boolean isReversed) {
        this.isReversed = isReversed;
    }

    public Boolean getIsNeedGouJi() {
        return isNeedGouJi;
    }

    public void setIsNeedGouJi(Boolean isNeedGouJi) {
        this.isNeedGouJi = isNeedGouJi;
    }

    public Boolean getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(Boolean isSynced) {
        this.isSynced = isSynced;
    }

    public String getRootSoCode() {
        return rootSoCode;
    }

    public void setRootSoCode(String rootSoCode) {
        this.rootSoCode = rootSoCode;
    }

    public String getFeedbackOperator() {
        return feedbackOperator;
    }

    public void setFeedbackOperator(String feedbackOperator) {
        this.feedbackOperator = feedbackOperator;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Long getSoId() {
        return soId;
    }

    public void setSoId(Long soId) {
        this.soId = soId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}
