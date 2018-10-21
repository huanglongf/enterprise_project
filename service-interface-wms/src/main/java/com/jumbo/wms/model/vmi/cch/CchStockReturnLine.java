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
@Table(name = "T_CCH_STOCK_RETURN_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class CchStockReturnLine extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7224316655772207277L;

    private Long id;

    private String type;

    private String skuCode;

    private Long purchasePrice;

    private Long quantityShipped;

    private Date createTime;

    private CchStockReturnInfo returnInfo;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CCH_STOCK_RETURN_LINE", sequenceName = "S_T_CCH_STOCK_RETURN_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CCH_STOCK_RETURN_LINE")
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

    @Column(name = "SKU_CODE", length = 50)
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

    @Column(name = "QUANTITY_SHIPPED")
    public Long getQuantityShipped() {
        return quantityShipped;
    }

    public void setQuantityShipped(Long quantityShipped) {
        this.quantityShipped = quantityShipped;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RETURN_INFO_ID")
    @Index(name = "IDX_CCH_RETURN_INFO")
    public CchStockReturnInfo getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(CchStockReturnInfo returnInfo) {
        this.returnInfo = returnInfo;
    }


}
