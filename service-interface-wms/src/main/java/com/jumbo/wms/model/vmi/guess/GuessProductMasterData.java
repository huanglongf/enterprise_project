package com.jumbo.wms.model.vmi.guess;

import java.math.BigDecimal;
import java.util.Date;

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
@Table(name = "T_GUESS_PRODUCT_MASTER_DATA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class GuessProductMasterData extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 9059420576534802017L;

    private Long id;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 产品代码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productDesc;

    /**
     * 颜色说明
     */
    private String colorDesc;

    /**
     * 尺寸说明
     */
    private String sizeDesc;

    /**
     * 外条码
     */
    private String barCode;

    /**
     * 内条码
     */
    private String shortSku;

    /**
     * 产品价格
     */
    private BigDecimal cnMsrp;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_GUESS_PRODUCT_MASTER", sequenceName = "S_T_GUESS_PRODUCT_MASTER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GUESS_PRODUCT_MASTER")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "PRODUCT_CODE", length = 30)
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name = "PRODUCT_DESC", length = 100)
    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    @Column(name = "COLOR_DESC", length = 30)
    public String getColorDesc() {
        return colorDesc;
    }

    public void setColorDesc(String colorDesc) {
        this.colorDesc = colorDesc;
    }

    @Column(name = "SIZE_DESC", length = 100)
    public String getSizeDesc() {
        return sizeDesc;
    }

    public void setSizeDesc(String sizeDesc) {
        this.sizeDesc = sizeDesc;
    }

    @Column(name = "BAR_CODE", length = 100)
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Column(name = "SHORT_SKU", length = 20)
    public String getShortSku() {
        return shortSku;
    }

    public void setShortSku(String shortSku) {
        this.shortSku = shortSku;
    }

    @Column(name = "CN_MSRP")
    public BigDecimal getCnMsrp() {
        return cnMsrp;
    }

    public void setCnMsrp(BigDecimal cnMsrp) {
        this.cnMsrp = cnMsrp;
    }
}
