package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
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
 * 接口/类说明：CXC 订单确认队列
 * @ClassName: CxcConfirmOrderQueue 
 * @author LuYingMing
 * @date 2016年5月30日 下午1:28:27
 */
@Entity
@Table(name = "T_CXC_CONFIRM_ORDER_QUEUE")
public class CxcConfirmOrderQueue extends BaseModel {


    /**
	 * 属性说明：
	 * @author LuYingMing
	 * @date 2016年6月3日 上午11:46:54 
	 */
	private static final long serialVersionUID = 7704302882330739660L;
	/**
     * PK
     */
    private Long id;
    /**
     * 客户编号
     */
    private String cmCode;
    /**
     * 批号(客户代码)
     */
    private String bdShipmentno;
    /**
     * 运单编号
     */
    private String bdCode;
    /**
     * 派送详细地址
     */
    private String bdCaddress;
    /**
     * 收件人
     */
    private String bdConsigneename;
    /**
     * 收件人电话
     */
    private String bdConsigneephone;
    /**
     * 货物数量
     */
    private Long bdGoodsnum;
    /**
     * 重量
     */
    private Double bdWeidght;
    /**
     * 长度
     */
    private BigDecimal bdLength;
    /**
     * 高度
     */
    private BigDecimal bdHeight;
    /**
     * 宽度
     */
    private BigDecimal bdWidth;
    /**
     * 标准箱代码
     */
    private String bdBoxcode;
    /**
     * Cod到付
     * '0'代表否,如果是就传入实际金额
     */
    private BigDecimal bdCodpay;
    /**
     * 备注
     */
    private String bdRemark;
    /**
     * 创建时间
     */
    private Date createdate;
    /**
     * 包号
     */
    private Long bdPackageno;
    /**
     * 保价
     */
    private BigDecimal bdProductprice;
    /**
     * 保费
     */
    private BigDecimal bdPremium;
    /**
     * 手续费
     */
    private BigDecimal bdProcedurefee;
    /**
     * 代收货款
     */
    private BigDecimal bdPurprice;

    // /**
    // * 状态(包含新建,调用CXC接口后的...)
    // * 1:新建 5:失败 8:成功
    // */
    // private Integer status;
    
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CXC_ORDER_QUEUE", sequenceName = "S_T_CXC_CONFIRM_ORDER_QUEUE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CXC_ORDER_QUEUE")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "CM_CODE", length = 200, nullable = false)
	public String getCmCode() {
		return cmCode;
	}
	public void setCmCode(String cmCode) {
		this.cmCode = cmCode;
	}
	@Column(name = "BD_SHIPMENT_NO", length = 200)
	public String getBdShipmentno() {
		return bdShipmentno;
	}
	public void setBdShipmentno(String bdShipmentno) { 
		this.bdShipmentno = bdShipmentno;
	}
	@Column(name = "BD_CODE", length = 200, nullable = false)
	public String getBdCode() {
		return bdCode;
	}
	public void setBdCode(String bdCode) {
		this.bdCode = bdCode;
	}
	@Column(name = "BD_CONSIGNEE_ADDRESS", length = 200, nullable = false)
	public String getBdCaddress() {
		return bdCaddress;
	}
	public void setBdCaddress(String bdCaddress) {
		this.bdCaddress = bdCaddress;
	}
	@Column(name = "BD_CONSIGNEE_NAME", length = 200, nullable = false)
	public String getBdConsigneename() {
		return bdConsigneename;
	}
	public void setBdConsigneename(String bdConsigneename) {
		this.bdConsigneename = bdConsigneename;
	}
	@Column(name = "BD_CONSIGNEE_PHONE", length = 20)
	public String getBdConsigneephone() {
		return bdConsigneephone;
	}
	public void setBdConsigneephone(String bdConsigneephone) {
		this.bdConsigneephone = bdConsigneephone;
	}
	@Column(name = "BD_GOODS_NUM")
	public Long getBdGoodsnum() {
		return bdGoodsnum;
	}
	public void setBdGoodsnum(Long bdGoodsnum) {
		this.bdGoodsnum = bdGoodsnum;
	}
	@Column(name = "BD_WEIDGHT", nullable = false, precision = 10, scale = 2)
	public Double getBdWeidght() {
		return bdWeidght;
	}
	public void setBdWeidght(Double bdWeidght) {
		this.bdWeidght = bdWeidght;
	}
	@Column(name = "BD_LENGTH")
	public BigDecimal getBdLength() {
		return bdLength;
	}
	public void setBdLength(BigDecimal bdLength) {
		this.bdLength = bdLength;
	}
	@Column(name = "BD_HEIGHT")
	public BigDecimal getBdHeight() {
		return bdHeight;
	}
	public void setBdHeight(BigDecimal bdHeight) {
		this.bdHeight = bdHeight;
	}
	@Column(name = "BD_WIDTH")
	public BigDecimal getBdWidth() {
		return bdWidth;
	}
	public void setBdWidth(BigDecimal bdWidth) {
		this.bdWidth = bdWidth;
	}
	@Column(name = "BD_BOX_CODE", length = 200)
	public String getBdBoxcode() {
		return bdBoxcode;
	}
	public void setBdBoxcode(String bdBoxcode) {
		this.bdBoxcode = bdBoxcode;
	}
	@Column(name = "BD_COD_PAY")
	public BigDecimal getBdCodpay() {
		return bdCodpay;
	}
	public void setBdCodpay(BigDecimal bdCodpay) {
		this.bdCodpay = bdCodpay;
	}
	@Column(name = "BD_REMARK", length = 200)
	public String getBdRemark() {
		return bdRemark;
	}
	public void setBdRemark(String bdRemark) {
		this.bdRemark = bdRemark;
	}
	@Column(name = "CREATEDATE", nullable = false, columnDefinition="DATE")
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	@Column(name = "BD_PACKAGE_NO", nullable = false)
	public Long getBdPackageno() {
		return bdPackageno;
	}
	public void setBdPackageno(Long bdPackageno) {
		this.bdPackageno = bdPackageno;
	}
	@Column(name = "BD_PRODUCT_PRICE")
	public BigDecimal getBdProductprice() {
		return bdProductprice;
	}
	public void setBdProductprice(BigDecimal bdProductprice) {
		this.bdProductprice = bdProductprice;
	}
	@Column(name = "BD_PREMIUM")
	public BigDecimal getBdPremium() {
		return bdPremium;
	}
	public void setBdPremium(BigDecimal bdPremium) {
		this.bdPremium = bdPremium;
	}
	@Column(name = "BD_PROCEDURE_FEE")
	public BigDecimal getBdProcedurefee() {
		return bdProcedurefee;
	}
	public void setBdProcedurefee(BigDecimal bdProcedurefee) {
		this.bdProcedurefee = bdProcedurefee;
	}
	@Column(name = "BD_PURPRICE")
	public BigDecimal getBdPurprice() {
		return bdPurprice;
	}
	public void setBdPurprice(BigDecimal bdPurprice) {
		this.bdPurprice = bdPurprice;
	}
}
