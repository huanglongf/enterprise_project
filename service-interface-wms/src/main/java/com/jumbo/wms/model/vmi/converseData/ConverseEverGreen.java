package com.jumbo.wms.model.vmi.converseData;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.vmi.itData.ConverseEverGreenStatus;


@Entity
@Table(name = "T_CONVERSE_EVER_GREEN")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ConverseEverGreen extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6818124467202152637L;

    private Long id;

    private ConverseEverGreenStatus status;

    private String style;

    private String color;

    private String size;

    private String sku;

    private String upc;

    private Date version;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CONVERSE_EVER_GREEN", sequenceName = "S_T_CONVERSE_EVER_GREEN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONVERSE_EVER_GREEN")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.vmi.itData.ConverseEverGreenStatus")})
    public ConverseEverGreenStatus getStatus() {
        return status;
    }

    public void setStatus(ConverseEverGreenStatus status) {
        this.status = status;
    }

    @Column(name = "STYLE")
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Column(name = "COLOR")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "EVER_SIZE")
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Column(name = "SKU")
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Column(name = "UPC")
    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

}
