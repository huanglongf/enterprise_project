package com.jumbo.wms.model.hub2wms;

import java.math.BigDecimal;

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

/**
 * 退入订单明细
 * 
 * @author cheng.su
 */
@Entity
@Table(name = "T_WH_RO_IN_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class WmsRtnOrderLineQueue extends BaseModel {
    /*
	 * 
	 */
    private static final long serialVersionUID = -1798893641551068273L;
    /*
     * PK
     */
    private Long id;
    /*
     * 行号
     */
    private String lineNo;
    /*
     * 商品
     */
    private String sku;
    /*
     * 数量
     */
    private Long qty;
    /*
     * 销售商品名称
     */
    private String skuName;
    /*
     * 吊牌价
     */
    private BigDecimal listPrice;
    /*
     * 单价
     */
    private BigDecimal unitPrice;
    /*
     * 行总金额
     */
    private BigDecimal lineAmt;
    /*
     * 订单货主
     */
    private String owner;
    /*
     * 商品状态
     */
    private String invStatus;
    /*
     * 扩展字段
     */
    private String ext_code;

    /*
     * 退换货单入库单据头
     */
    private WmsRtnInOrderQueue wmsRtnInOrderQueue;

    /*
     * 颜色打印值
     */
    private String color;

    /*
     * SIZE打印值
     */
    private String lineSize;

    private String orderLineNo;// 平台订单行号

    private Long orderQty;// 平台订单计划量

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_RO_IN_LINE", sequenceName = "S_T_WH_RO_IN_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_RO_IN_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "order_qty")
    public Long getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Long orderQty) {
        this.orderQty = orderQty;
    }

    @Column(name = "color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    @Column(name = "LINE_NO", length = 30)
    public String getLineNo() {
        return lineNo;
    }

    @Column(name = "line_size")
    public String getLineSize() {
        return lineSize;
    }

    public void setLineSize(String lineSize) {
        this.lineSize = lineSize;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    @Column(name = "SKU_CODE", length = 50)
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Column(name = "QTY", precision = 15)
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "SKU_NAME", length = 100)
    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    @Column(name = "LIST_PRICE", precision = 15, scale = 2)
    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    @Column(name = "UNIT_PRICE", precision = 15, scale = 2)
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "LINE_AMT", precision = 15, scale = 2)
    public BigDecimal getLineAmt() {
        return lineAmt;
    }

    public void setLineAmt(BigDecimal lineAmt) {
        this.lineAmt = lineAmt;
    }

    @Column(name = "OWNER", length = 50)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "INV_STATUS", length = 10)
    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    @Column(name = "EXT_CODE", length = 2000)
    public String getExt_code() {
        return ext_code;
    }

    public void setExt_code(String ext_code) {
        this.ext_code = ext_code;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IN_ORDER_ID")
    @Index(name = "IDX_LINE_INORDER_ID")
    public WmsRtnInOrderQueue getWmsRtnInOrderQueue() {
        return wmsRtnInOrderQueue;
    }

    public void setWmsRtnInOrderQueue(WmsRtnInOrderQueue wmsRtnInOrderQueue) {
        this.wmsRtnInOrderQueue = wmsRtnInOrderQueue;
    }

    @Column(name = "order_line_no")
    public String getOrderLineNo() {
        return orderLineNo;
    }

    public void setOrderLineNo(String orderLineNo) {
        this.orderLineNo = orderLineNo;
    }


}
