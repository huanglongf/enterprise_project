package com.jumbo.wms.model.hub2wms;

import java.math.BigDecimal;
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

import com.jumbo.wms.model.BaseModel;

/**
 * HUB2WMS过仓 销售订单明细队列
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_SO_OR_RO_LINE")
public class WmsSalesOrderLineQueue extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -2664853085648855912L;
    /*
     * PK
     */
    private Long id;
    /*
     * 平台行号
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
     * 销售平台商品名称
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
     * 行折扣
     */
    private BigDecimal lineDiscount;
    /*
     * 行总金额
     */
    private BigDecimal lineAmt;
    /*
     * 活动编码
     */
    private String activeCode;
    /*
     * 订单货主
     */
    private String owner;
    /*
     * 商品状态 
     */
    private String invStatus;
    /*
     * 是否是赠品 
     */
    private boolean isGift;
    /*
     * 扩展字段
     */
    private String ext_code;
    /*
     * 产地
     */
    private String origin;
    /*
     * 批次号 
     */
    private String batchNo;
    /*
     * 起始过期时间
     */
    private Date sExpDate;
    /*
     * 结束过期时间
     */
    private Date eExpDate;
    /*
     * 销售/换货出库单据头
     */
    private WmsSalesOrderQueue wmsSalesOrderQueue;
    /*
     * 订单行仓库
     */
    private String warehouseCode;
    /*
     * SKU ID
     */
    private Long skuId;
    /*
     * 最终仓库ID
     */
    private Long ouId;
    /*
     * 是否赠品不足删除
     */
    private Boolean isDelete = false;
    /*
     * 物流商
     */
    private String lpCode;

    private String color;// 商品颜色

    private String skuSize;// 商品尺码

    private String orderLineNo;// 平台订单行号

    private Long orderQty;// 平台订单计划量


    @Column(name = "COLOR")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "ORDER_LINE_NO")
    public String getOrderLineNo() {
        return orderLineNo;
    }

    @Column(name = "SKU_SIZE")
    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

    public void setOrderLineNo(String orderLineNo) {
        this.orderLineNo = orderLineNo;
    }

    @Column(name = "ORDER_QTY")
    public Long getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Long orderQty) {
        this.orderQty = orderQty;
    }

    public void setGift(boolean isGift) {
        this.isGift = isGift;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_SOORRO_LINE", sequenceName = "S_T_WH_SO_OR_RO_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_SOORRO_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "LINE_NO", length = 30)
    public String getLineNo() {
        return lineNo;
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

    @Column(name = "LINE_DISCOUNT", precision = 15, scale = 2)
    public BigDecimal getLineDiscount() {
        return lineDiscount;
    }

    public void setLineDiscount(BigDecimal lineDiscount) {
        this.lineDiscount = lineDiscount;
    }

    @Column(name = "LINE_AMT", precision = 15, scale = 2)
    public BigDecimal getLineAmt() {
        return lineAmt;
    }

    public void setLineAmt(BigDecimal lineAmt) {
        this.lineAmt = lineAmt;
    }

    @Column(name = "ACTIVE_CODE", length = 50)
    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
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

    @Column(name = "IS_GIFT")
    public boolean getIsGift() {
        return isGift;
    }

    public void setIsGift(boolean isGift) {
        this.isGift = isGift;
    }

    @Column(name = "EXT_CODE", length = 2000)
    public String getExt_code() {
        return ext_code;
    }

    public void setExt_code(String ext_code) {
        this.ext_code = ext_code;
    }


    @Column(name = "ORIGIN", length = 50)
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Column(name = "BATCH_NO", length = 30)
    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    @Column(name = "SEXP_DATE")
    public Date getsExpDate() {
        return sExpDate;
    }

    public void setsExpDate(Date sExpDate) {
        this.sExpDate = sExpDate;
    }

    @Column(name = "EEXP_DATE")
    public Date geteExpDate() {
        return eExpDate;
    }

    public void seteExpDate(Date eExpDate) {
        this.eExpDate = eExpDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OUT_ORDER_ID")
    @Index(name = "IDX_LINE_OUTORDER_ID")
    public WmsSalesOrderQueue getWmsSalesOrderQueue() {
        return wmsSalesOrderQueue;
    }

    public void setWmsSalesOrderQueue(WmsSalesOrderQueue wmsSalesOrderQueue) {
        this.wmsSalesOrderQueue = wmsSalesOrderQueue;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "WAREHOUSE_CODE", length = 20)
    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    @Column(name = "OU_ID")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Column(name = "IS_DELETE")
    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Column(name = "LP_CODE")
    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }



}
