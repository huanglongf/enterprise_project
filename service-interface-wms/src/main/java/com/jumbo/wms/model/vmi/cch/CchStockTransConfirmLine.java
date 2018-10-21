package com.jumbo.wms.model.vmi.cch;

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

@Entity
@Table(name = "T_CCH_STOCKIN_CONFIRM_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class CchStockTransConfirmLine extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3681737130017559725L;

    private Long id;

    private String type;

    private String skuCode;

    private Long purchasePrice;

    private Long receiverQuantity;

    private Long quantity2;

    private Date createDate;

    private Integer status;

    private CchStockTransConfirm stockTransConfirm;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CCH_STOCKIN_CONFIRM_LINE", sequenceName = "S_T_CCH_STOCKIN_CONFIRM_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CCH_STOCKIN_CONFIRM_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TYPE", length = 20)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "SKU_CODE", length = 20)
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "PURCHASE_PRICE")
    public Long getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Long purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @Column(name = "RECEIVER_QUANTITY")
    public Long getReceiverQuantity() {
        return receiverQuantity;
    }

    public void setReceiverQuantity(Long receiverQuantity) {
        this.receiverQuantity = receiverQuantity;
    }

    @Column(name = "QUANTITY2")
    public Long getQuantity2() {
        return quantity2;
    }

    public void setQuantity2(Long quantity2) {
        this.quantity2 = quantity2;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANS_CONFRIM_ID")
    @Index(name = "IDX_TRANS_CONFRIM")
    public CchStockTransConfirm getStockTransConfirm() {
        return stockTransConfirm;
    }

    public void setStockTransConfirm(CchStockTransConfirm stockTransConfirm) {
        this.stockTransConfirm = stockTransConfirm;
    }

}
