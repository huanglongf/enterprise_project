package com.jumbo.wms.model.warehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * wms库内取消单据明细
 * @author CSH5574
 *
 */
@Entity
@Table(name="T_WH_WMS_CANCEL_ORDER_LINE")
public class WmsCancelOrderLine  extends BaseModel{

    private static final long serialVersionUID = 4234511708076271428L;
    
    private Long id ;//key
    
    private Long  cancelOrderId;//取消头id
    
    private String skuCode;//商品编码
    
    private String barCode;//条码
    
    private Long planQty;//计划数量
    
    private Long usableQty;//wms可用量
    
    private Long lackQty ;//报缺数量
    
    private String reason;//报缺原因
    
    private String skuName;//商品名称

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CANCEL_ORDER_LINE", sequenceName = "S_WH_WMS_CANCEL_ORDER_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CANCEL_ORDER_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="CANCEL_ORDER_ID")
    public Long getCancelOrderId() {
        return cancelOrderId;
    }

    public void setCancelOrderId(Long cancelOrderId) {
        this.cancelOrderId = cancelOrderId;
    }

    @Column(name="SKU_CODE")
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }
    
    @Column(name="BAR_CODE")
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Column(name="PLAN_QTY")
    public Long getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Long planQty) {
        this.planQty = planQty;
    }
    
    @Column(name="USABLE_QTY")
    public Long getUsableQty() {
        return usableQty;
    }

    public void setUsableQty(Long usableQty) {
        this.usableQty = usableQty;
    }
    
    @Column(name="LACK_QTY")
    public Long getLackQty() {
        return lackQty;
    }

    public void setLackQty(Long lackQty) {
        this.lackQty = lackQty;
    }

    @Column(name="REASON")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
    @Column(name="SKU_NAME")
    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }
    
}
