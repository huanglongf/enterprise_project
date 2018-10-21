package com.jumbo.wms.model.vmi.guess;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name="T_GUESS_SALESORDER_DATA")
@org.hibernate.annotations.Entity(optimisticLock=OptimisticLockType.VERSION)
public class GuessSalesorderData extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6777657256485149946L;

	private Long id;
	
	private String storeCode;
	
	private String transreFerence;
	
	private String barCode;
	
	private BigDecimal prodTotal;
	
	private BigDecimal rcptNote2;
	
	private String transDate;
	 
	private String transTime;
	
	private BigDecimal QTY;
	 
	private BigDecimal priceSold ; 
	
	private BigDecimal amountDiscount;
	 
	private String reasonCode;
	
	private String tenderCode;
	 
	private String rcptNote1;
	
	private Long orderId;
	
	private String rType;
	
	private Long status;

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "SEQ_GUESS_SALESORDER_DATA", sequenceName = "S_T_GUESS_SALESORDER_DATA", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GUESS_SALESORDER_DATA")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "STORE_CODE",length=10)
	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	@Column(name = "TRANSRE_FERENCE",length=50)
	public String getTransreFerence() {
		return transreFerence;
	}

	public void setTransreFerence(String transreFerence) {
		this.transreFerence = transreFerence;
	}
	@Column(name = "BAR_CODE",length=25)
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	@Column(name = "PROD_TOTAL",precision=12,scale=2)
	public BigDecimal getProdTotal() {
		return prodTotal;
	}

	public void setProdTotal(BigDecimal prodTotal) {
		this.prodTotal = prodTotal;
	}
	@Column(name = "RCPT_NOTE2",precision=12,scale=2)
	public BigDecimal getRcptNote2() {
		return rcptNote2;
	}

	public void setRcptNote2(BigDecimal rcptNote2) {
		this.rcptNote2 = rcptNote2;
	}

	@Column(name = "TRANS_DATE")
	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	@Column(name = "TRANS_TIME")
	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
	@Column(name = "QTY",precision=6,scale=2)
	public BigDecimal getQTY() {
		return QTY;
	}

	public void setQTY(BigDecimal qTY) {
		QTY = qTY;
	}

	@Column(name = "PRICE_SOLD",precision=16,scale=6)
	public BigDecimal getPriceSold() {
		return priceSold;
	}

	public void setPriceSold(BigDecimal priceSold) {
		this.priceSold = priceSold;
	}
	@Column(name = "AMOUNT_DISCOUNT",precision=12,scale=2)
	public BigDecimal getAmountDiscount() {
		return amountDiscount;
	}

	public void setAmountDiscount(BigDecimal amountDiscount) {
		this.amountDiscount = amountDiscount;
	}
	@Column(name = "REASON_CODE",length=4)
	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	@Column(name = "TENDER_CODE",length=4)
	public String getTenderCode() {
		return tenderCode;
	}

	public void setTenderCode(String tenderCode) {
		this.tenderCode = tenderCode;
	}
	@Column(name = "RCPT_NOTE1",length=50)
	public String getRcptNote1() {
		return rcptNote1;
	}

	public void setRcptNote1(String rcptNote1) {
		this.rcptNote1 = rcptNote1;
	}
	@Column(name = "ORDER_ID",length=4)
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	@Column(name = "RTYPE")
	public String getrType() {
		return rType;
	}

	public void setrType(String rType) {
		this.rType = rType;
	}
    @Column(name = "STATUS")
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
	
	
}
