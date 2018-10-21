package com.jumbo.wms.model.warehouse;

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

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 渠道残次品配置
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "t_wh_stv_imperfect_line")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class ImperfectStv extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -4141255991007605979L;
    private Long id;
    private String barCode;
    private String jmCode;
    private String keyProperties;
    private String name;
    private String supplierCode;
    private String statusName;
    private Integer quantity;
    private Integer completeQuantity;
    private Integer addQuantity;
    private String skuSn;
    private String imperfectName;
    private String imperfectLineName;
    private String memo;
    private StockTransApplication sta;
    private Date createTime;
    private InventoryStatus statusId;
    private String defectCode;
    private String poId;
    private String factoryCode;
    private String sn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    public InventoryStatus getStatusId() {
        return statusId;
    }

    public void setStatusId(InventoryStatus statusId) {
        this.statusId = statusId;
    }

    @Column(name = "defect_Code")
    public String getDefectCode() {
        return defectCode;
    }

    public void setDefectCode(String defectCode) {
        this.defectCode = defectCode;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_stv_imperfect_line", sequenceName = "s_t_wh_stv_imperfect_line", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_stv_imperfect_line")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "bar_code")
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Column(name = "jm_code")
    public String getJmCode() {
        return jmCode;
    }

    public void setJmCode(String jmCode) {
        this.jmCode = jmCode;
    }

    @Column(name = "Key_Properties")
    public String getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(String keyProperties) {
        this.keyProperties = keyProperties;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "supplier_code")
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    @Column(name = "status_name")
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Column(name = "Quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(name = "completeQuantity")
    public Integer getCompleteQuantity() {
        return completeQuantity;
    }

    public void setCompleteQuantity(Integer completeQuantity) {
        this.completeQuantity = completeQuantity;
    }

    @Column(name = "addQuantity")
    public Integer getAddQuantity() {
        return addQuantity;
    }

    public void setAddQuantity(Integer addQuantity) {
        this.addQuantity = addQuantity;
    }

    @Column(name = "skuSn")
    public String getSkuSn() {
        return skuSn;
    }

    public void setSkuSn(String skuSn) {
        this.skuSn = skuSn;
    }

    @Column(name = "imperfect_name")
    public String getImperfectName() {
        return imperfectName;
    }

    public void setImperfectName(String imperfectName) {
        this.imperfectName = imperfectName;
    }

    @Column(name = "imperfectLine_name")
    public String getImperfectLineName() {
        return imperfectLineName;
    }

    public void setImperfectLineName(String imperfectLineName) {
        this.imperfectLineName = imperfectLineName;
    }

    @Column(name = "mome")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }
    @Column(name="po_id")
	public String getPoId() {
		return poId;
	}

	public void setPoId(String poId) {
		this.poId = poId;
	}
	@Column(name="factory_code")
	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	@Column(name="sn")
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
	
    


}
