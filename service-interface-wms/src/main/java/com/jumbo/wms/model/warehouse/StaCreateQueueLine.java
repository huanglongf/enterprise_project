package com.jumbo.wms.model.warehouse;

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

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_WH_STA_CQ_LINE")
public class StaCreateQueueLine extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7752951231895717460L;
    /**
     * PK
     */
    private Long id;
    /**
     * UPC
     */
    private String upc;
    /**
     * 数量
     */
    private Integer qty;
    /**
     * 货品类型，100为A品，120为B品，101为残次品
     */
    private Long invType;
    /**
     * 是否特殊包装
     */
    private Boolean isPackage;
    /**
     * 是否存在礼品卡
     */
    private Boolean isGiftCards;
    /**
     * 礼品卡备注
     */
    private String memo;
    /**
     * 礼品卡备注
     */
    private String packageMemo;
    /**
     * 队列头
     */
    private StaCreateQueue staCrateQueue;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SCQL", sequenceName = "S_T_WH_STA_CQ_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCQL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "UPC", length = 50)
    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    @Column(name = "QTY")
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Column(name = "INT_TYPE")
    public Long getInvType() {
        return invType;
    }

    public void setInvType(Long invType) {
        this.invType = invType;
    }
    
    @Column(name = "IS_PACKAGE")
    public Boolean getIsPackage() {
        return isPackage;
    }

    public void setIsPackage(Boolean isPackage) {
        this.isPackage = isPackage;
    }
    
    @Column(name = "IS_GIFT_CARDS")
    public Boolean getIsGiftCards() {
        return isGiftCards;
    }

    public void setIsGiftCards(Boolean isGiftCards) {
        this.isGiftCards = isGiftCards;
    }
    
    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "PACKAGE_MEMO")
    public String getPackageMemo() {
        return packageMemo;
    }

    public void setPackageMemo(String packageMemo) {
        this.packageMemo = packageMemo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCQ_ID")
    @Index(name = "IDX_SCQL_SCQ_ID")
    public StaCreateQueue getStaCrateQueue() {
        return staCrateQueue;
    }

    public void setStaCrateQueue(StaCreateQueue staCrateQueue) {
        this.staCrateQueue = staCrateQueue;
    }
}
