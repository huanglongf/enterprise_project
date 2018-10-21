package com.jumbo.wms.model.vmi.etamData;

import java.math.BigDecimal;

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

/**
 * 
 * @author peijie.ouyang 商品主档
 */
@Entity
@Table(name = "T_ETAM_SKU")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class EtamSku extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4654738013001925542L;

    private String sku;
    private String bar9;
    private String color;
    private String size;
    private String year;
    private String brand;
    private String season;
    private String style;
    private String story;
    private String family;
    private String component;
    private String wash;
    private BigDecimal tagPrice;
    private String userDef1;
    private String userDef2;
    private String userDef3;
    private int version;
    private Long id;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_ETAM_SKU", sequenceName = "S_T_ETAM_SKU", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_ETAM_SKU")
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

    @Column(name = "SKU", length = 25)
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Column(name = "BAR9", length = 25)
    public String getBar9() {
        return bar9;
    }

    public void setBar9(String bar9) {
        this.bar9 = bar9;
    }

    @Column(name = "COLOR", length = 25)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "SIZES", length = 50)
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Column(name = "YEAR", length = 10)
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Column(name = "BRAND", length = 25)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "SEASON", length = 25)
    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    @Column(name = "STYLE", length = 50)
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Column(name = "STORY", length = 50)
    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    @Column(name = "FAMILY", length = 5)
    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    @Column(name = "COMPONENT", length = 500)
    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    @Column(name = "WASH", length = 500)
    public String getWash() {
        return wash;
    }

    public void setWash(String wash) {
        this.wash = wash;
    }

    @Column(name = "TAGPRICE")
    public BigDecimal getTagPrice() {
        return tagPrice;
    }

    public void setTagPrice(BigDecimal tagPrice) {
        this.tagPrice = tagPrice;
    }

    @Column(name = "USERDEF1", length = 25)
    public String getUserDef1() {
        return userDef1;
    }

    public void setUserDef1(String userDef1) {
        this.userDef1 = userDef1;
    }

    @Column(name = "USERDEF2", length = 25)
    public String getUserDef2() {
        return userDef2;
    }

    public void setUserDef2(String userDef2) {
        this.userDef2 = userDef2;
    }

    @Column(name = "USERDEF3", length = 25)
    public String getUserDef3() {
        return userDef3;
    }

    public void setUserDef3(String userDef3) {
        this.userDef3 = userDef3;
    }

}
