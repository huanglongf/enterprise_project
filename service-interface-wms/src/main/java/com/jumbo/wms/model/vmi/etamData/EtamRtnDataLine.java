package com.jumbo.wms.model.vmi.etamData;

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
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_WH_ETAM_RTN_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class EtamRtnDataLine extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 467754785564260911L;

    public static final Integer CREATE_STATUS = 1;
    public static final Integer TODO_STATUS = 5;
    public static final Integer FINISH_STATUS = 10;

    private Long id;
    /**
     * 
     */
    private int version;
    /**
     * 
     */
    private Integer status;

    private Long skuId;

    private String billCode;
    /**
     * sku条码
     */
    private String skuCode;
    /**
     * 数量
     */
    private Long quantity;
    /**
     * 库存状态 CC | DD
     */
    private String invStatus;
    /**
     * 备用字段1 - 库存状态
     */
    private String userDef1;
    /**
     * 备用字段2
     */
    private String userDef2;
    /**
     * 备用字段3
     */
    private String userDef3;

    private EtamRtnData etamRtnData;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_ETAM_RTN_LINE", sequenceName = "S_T_WH_ETAM_RTN_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_ETAM_RTN_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "Version")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    // @Column(name="sHDTLUP",length=25)

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "SKU_CODE", length = 25)
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "QUANTITY", length = 19)
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Column(name = "USER_DEF1", length = 25)
    public String getUserDef1() {
        return userDef1;
    }

    public void setUserDef1(String userDef1) {
        this.userDef1 = userDef1;
    }

    @Column(name = "USER_DEF2", length = 25)
    public String getUserDef2() {
        return userDef2;
    }

    public void setUserDef2(String userDef2) {
        this.userDef2 = userDef2;
    }

    @Column(name = "USER_DEF3", length = 25)
    public String getUserDef3() {
        return userDef3;
    }

    public void setUserDef3(String userDef3) {
        this.userDef3 = userDef3;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ETAM_RTN_ID")
    @Index(name = "IDX_ETAM_RTN_DATA")
    public EtamRtnData getEtamRtnData() {
        return etamRtnData;
    }

    public void setEtamRtnData(EtamRtnData etamRtnData) {
        this.etamRtnData = etamRtnData;
    }

    @Column(name = "BILL_CODE", length = 25)
    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    @Column(name = "INV_STATUS", length = 25)
    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        EtamRtnDataLine other = (EtamRtnDataLine) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
