package com.jumbo.wms.model.vmi;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;
@Entity
@Table(name = "T_WH_GUESS_INVENTORY_RETAIL")
public class WhGuessInventoryRetail extends BaseModel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6661124497750764113L;

	/**
     * id
     */
    private Long id;

    /**
     * 日期
     */
    private String transDate;

    /**
     * 时间
     */
    private String transTime;

    /**
     * 店普遍吗
     */
    private String storeCode;

    /**
     * 箱号
     */
    private String binCode;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 颜色描述
     */
    private String colorDescription;

    /**
     * 尺寸描述
     */
    private String sizeDescription;

    /**
     * 短sku
     */
    private String shortSku;

    /**
     * 当前数量
     */
    private String onhandQty;
    
    /**
     * 创建时间
     */
    private Date createTime;

    
    @Id
    @SequenceGenerator(name = "T_WH_GUESS_INVENTORY_RETAIL_GENERATOR", sequenceName = "S_T_WH_GUESS_INVENTORY_RETAIL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_WH_GUESS_INVENTORY_RETAIL_GENERATOR")
    @Column(name = "ID", unique = true, nullable = false, scale = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TRANS_DATE", length = 50)
    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    @Column(name = "TRANS_TIME", length = 50)
    public  String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    @Column(name = "STORE_CODE", length = 50)
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Column(name = "BIN_CODE", length = 50)
    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }

    @Column(name = "PRODUCT_CODE", length = 50)
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name = "COLOR_DESCRIPTION", length = 50)
    public String getColorDescription() {
        return colorDescription;
    }

    public void setColorDescription(String colorDescription) {
        this.colorDescription = colorDescription;
    }

    @Column(name = "SIZE_DESCRIPTION", length = 50)
    public String getSizeDescription() {
        return sizeDescription;
    }

    public void setSizeDescription(String sizeDescription) {
        this.sizeDescription = sizeDescription;
    }

    @Column(name = "SHORT_SKU", length = 50)
    public String getShortSku() {
        return shortSku;
    }

    public void setShortSku(String shortSku) {
        this.shortSku = shortSku;
    }

    @Column(name = "ONHAND_QTY", length = 50)
    public String getOnhandQty() {
        return onhandQty;
    }

    public void setOnhandQty(String onhandQty) {
        this.onhandQty = onhandQty;
    }
    
    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
