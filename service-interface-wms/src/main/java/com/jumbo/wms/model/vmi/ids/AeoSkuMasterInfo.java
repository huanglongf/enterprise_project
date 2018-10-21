package com.jumbo.wms.model.vmi.ids;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_AEO_SKU_MASTER")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class AeoSkuMasterInfo extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -2939465574043618325L;

    private Long id;

    private int version;

    /**
     * 创建时间
     */
    private Date createDate;

    private String barCode;

    private String brand;

    private String color;

    private String colorSize;

    private String enName;

    private BigDecimal listPrice;

    private String aeoSize;

    private String skuClass;

    private String style;

    private String upc;

    private String Vendor;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_AEO_SKU_MASTER", sequenceName = "S_T_AEO_SKU_MASTER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AEO_SKU_MASTER")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "BARCODE", length = 50)
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
    @Column(name = "BRAND", length = 30)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    @Column(name = "COLOR", length = 50)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "COLOR_SIZE", length = 50)
    public String getColorSize() {
        return colorSize;
    }

    public void setColorSize(String colorSize) {
        this.colorSize = colorSize;
    }
    @Column(name = "ENNAME", length = 50)
    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }
    @Column(name = "LIST_PRICE", length = 50)
    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }
   
    @Column(name = "AEO_SIZE", length = 30)
    public String getAeoSize() {
        return aeoSize;
    }

    public void setAeoSize(String aeoSize) {
        this.aeoSize = aeoSize;
    }

    @Column(name = "SKUCLASS", length = 50)
    public String getSkuClass() {
        return skuClass;
    }
   

    public void setSkuClass(String skuClass) {
        this.skuClass = skuClass;
    }

    @Column(name = "STYLE", length = 50)
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Column(name = "UPC", length = 50)
    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    @Column(name = "VENDOR", length = 50)
    public String getVendor() {
        return Vendor;
    }

    public void setVendor(String vendor) {
        Vendor = vendor;
    }


}
