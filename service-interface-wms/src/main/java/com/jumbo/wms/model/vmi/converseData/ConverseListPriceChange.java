package com.jumbo.wms.model.vmi.converseData;

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
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.vmi.itData.ConversePriceChangeStatus;


@Entity
@Table(name = "T_CONVERSE_LISTPRICE_CHANGE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ConverseListPriceChange extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6818124467202152637L;

    private Long id;

    private ConversePriceChangeStatus status;

    private String styleId;

    private String skuNO;

    private String color;

    private BigDecimal price;

    private BigDecimal oldPrice;

    private Date version;

    private Long msgBatchId;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CONVERSE_LISTPRICE_CHANGE", sequenceName = "S_T_CONVERSE_LISTPRICE_CHANGE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONVERSE_LISTPRICE_CHANGE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.vmi.itData.ConversePriceChangeStatus")})
    public ConversePriceChangeStatus getStatus() {
        return status;
    }

    public void setStatus(ConversePriceChangeStatus status) {
        this.status = status;
    }

    @Column(name = "STYLE_ID", length = 20)
    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    @Column(name = "SKU_NO", length = 25)
    public String getSkuNO() {
        return skuNO;
    }

    public void setSkuNO(String skuNO) {
        this.skuNO = skuNO;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "OLD_PRICE")
    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
    }

    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Column(name = "COLOR", length = 10)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "MSG_BATCH_ID")
    public Long getMsgBatchId() {
        return msgBatchId;
    }

    public void setMsgBatchId(Long msgBatchId) {
        this.msgBatchId = msgBatchId;
    }



}
