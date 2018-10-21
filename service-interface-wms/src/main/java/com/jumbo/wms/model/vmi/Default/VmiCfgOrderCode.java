package com.jumbo.wms.model.vmi.Default;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_VMI_CFG_ORDER_CODE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class VmiCfgOrderCode extends BaseModel {

    private static final long serialVersionUID = 8485344011292123935L;

    /**
     * id
     */
    private Long id;

    /**
     * 品牌来源 渠道vmi_source
     */
    private String vmiSource;

    /**
     * 系列组
     */
    private String seqCategory;

    /**
     * 序号最小长度
     */
    private int minLength;

    /**
     * 填充字符
     */
    private String fillChar;

    /**
     * 前缀字符串
     */
    private String pxCode;

    /**
     * 品牌单据类型
     */
    private VmiCfgOrderCodeType type;


    private int isDefault = 1;

    /**
     * 版本
     */
    private int version;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_VMI_CFG_ORDER_CODE", sequenceName = "S_T_VMI_CFG_ORDER_CODE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VMI_CFG_ORDER_CODE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "VMI_SOURCE")
    public String getVmiSource() {
        return vmiSource;
    }

    public void setVmiSource(String vmiSource) {
        this.vmiSource = vmiSource;
    }

    @Column(name = "SEQ_CATEGORY")
    public String getSeqCategory() {
        return seqCategory;
    }

    public void setSeqCategory(String seqCategory) {
        this.seqCategory = seqCategory;
    }

    @Column(name = "MIN_LENGTH")
    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    @Column(name = "FILL_CHAR")
    public String getFillChar() {
        return fillChar;
    }

    public void setFillChar(String fillChar) {
        this.fillChar = fillChar;
    }

    @Column(name = "PX_CODE")
    public String getPxCode() {
        return pxCode;
    }

    public void setPxCode(String pxCode) {
        this.pxCode = pxCode;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.vmi.Default.VmiCfgOrderCodeType")})
    public VmiCfgOrderCodeType getType() {
        return type;
    }

    public void setType(VmiCfgOrderCodeType type) {
        this.type = type;
    }

    @Column(name = "IS_DEFAULT")
    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
