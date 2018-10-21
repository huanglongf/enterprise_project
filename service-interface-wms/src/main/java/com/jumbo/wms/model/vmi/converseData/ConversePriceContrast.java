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

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_CONVERSE_EC_TM_PRICE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ConversePriceContrast extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3161218789375083841L;

    private Long id;

    private String product;

    private BigDecimal ECPrice;

    private BigDecimal OMSPrice;

    private Date sendDate;

    private Date version;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CONVERSE_EC_TM_PRICE", sequenceName = "S_T_CONVERSE_EC_TM_PRICE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONVERSE_EC_TM_PRICE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "product", length = 50)
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Column(name = "ECPrice")
    public BigDecimal getECPrice() {
        return ECPrice;
    }

    public void setECPrice(BigDecimal eCPrice) {
        ECPrice = eCPrice;
    }

    @Column(name = "OMSPrice")
    public BigDecimal getOMSPrice() {
        return OMSPrice;
    }

    public void setOMSPrice(BigDecimal oMSPrice) {
        OMSPrice = oMSPrice;
    }

    @Column(name = "sendDate")
    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    @Column(name = "version")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }
}
