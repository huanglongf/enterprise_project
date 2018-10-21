package com.jumbo.wms.model.vmi.espData;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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


@Entity
@Table(name = "T_ESPRIT_PO_TYPE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ESPPoType extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8866439039762208971L;
    public static final String ESPRIT_PO_TYPE_1 = "直送";
    public static final String ESPRIT_PO_TYPE_3 = "非直送";
    public static final String ESPRIT_PO_TYPE_5 = "进口";
    public static final String ESPRIT_PO_TYPE_7 = "特许证产品";
    public static final String ESPRIT_PO_TYPE_9 = "大连自提";

    public static final Map<String, Integer> ESPRIT_PO_TYPE;
    static {
        ESPRIT_PO_TYPE = new HashMap<String, Integer>();
        ESPRIT_PO_TYPE.put(ESPRIT_PO_TYPE_1, 1);
        ESPRIT_PO_TYPE.put(ESPRIT_PO_TYPE_3, 3);
        ESPRIT_PO_TYPE.put(ESPRIT_PO_TYPE_5, 5);
        ESPRIT_PO_TYPE.put(ESPRIT_PO_TYPE_7, 7);
        ESPRIT_PO_TYPE.put(ESPRIT_PO_TYPE_9, 9);
    }

    private Long id;

    private String po;

    private EspritOrderPOType type;

    private Date version;

    private String poStyle;

    /**
     * 发票号
     */
    private String invoiceNumber;
    /**
     * 税收系数(ESPRITS使用)
     */
    private Double dutyPercentage;
    /**
     * 其他系数(ESPRITS使用)
     */
    private Double miscFeePercentage;
    /**
     * 佣金系数
     */
    private Double commPercentage;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ESPRIT_PO_TYPE", sequenceName = "S_T_ESPRIT_PO_TYPE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESPRIT_PO_TYPE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "po", length = 100)
    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    @Column(name = "type", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.vmi.espData.EspritOrderPOType")})
    public EspritOrderPOType getType() {
        return type;
    }

    public void setType(EspritOrderPOType type) {
        this.type = type;
    }

    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Column(name = "PO_STYLE")
    public String getPoStyle() {
        return poStyle;
    }

    public void setPoStyle(String poStyle) {
        this.poStyle = poStyle;
    }

    @Column(name = "INVOICE_NUMBER", length = 100)
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @Column(name = "DUTY_PERCENTAGE")
    public Double getDutyPercentage() {
        return dutyPercentage;
    }

    public void setDutyPercentage(Double dutyPercentage) {
        this.dutyPercentage = dutyPercentage;
    }

    @Column(name = "MISC_FEE_PERCENTAGE")
    public Double getMiscFeePercentage() {
        return miscFeePercentage;
    }

    public void setMiscFeePercentage(Double miscFeePercentage) {
        this.miscFeePercentage = miscFeePercentage;
    }

    @Column(name = "COMM_PERCENTAGE")
    public Double getCommPercentage() {
        return commPercentage;
    }

    public void setCommPercentage(Double commPercentage) {
        this.commPercentage = commPercentage;
    }
    
    

}
