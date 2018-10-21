package com.jumbo.wms.model.vmi.ids;

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
@Table(name = "T_IDS_INV_SYNCHRONOUS_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class IdsInventorySynchronousLine extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 7883079196910623869L;

    private Long id;

    /**
     * WMS行号
     */
    private String wMSDocLineNo;

    /**
     * 商品编码
     */
    private String sku;

    /**
     * 数量(+/-)
     */
    private int qty;

    /**
     * 库存状态
     */
    private String hostWHCode;
    
    public IdsInventorySynchronous idsInventorySynchronous;

   

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_IDS_INV_SYN_LINE", sequenceName = "S_T_IDS_INV_SYN_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_IDS_INV_SYN_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "WMS_DOCLINE_NO", length = 10)
    public String getwMSDocLineNo() {
        return wMSDocLineNo;
    }

    public void setwMSDocLineNo(String wMSDocLineNo) {
        this.wMSDocLineNo = wMSDocLineNo;
    }

    @Column(name = "SKU", length = 30)
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Column(name = "QTY")
    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Column(name = "HOST_WHCODE", length = 30)
    public String getHostWHCode() {
        return hostWHCode;
    }

    public void setHostWHCode(String hostWHCode) {
        this.hostWHCode = hostWHCode;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDS_INV_SYN_ID")
    @Index(name = "IDX_IDS_INV_SYN_ID")
    public IdsInventorySynchronous getIdsInventorySynchronous() {
        return idsInventorySynchronous;
    }

    public void setIdsInventorySynchronous(IdsInventorySynchronous idsInventorySynchronous) {
        this.idsInventorySynchronous = idsInventorySynchronous;
    }

   

}
