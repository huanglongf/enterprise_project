package com.jumbo.wms.model.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * 
 * 物流商扩展信息
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_WH_TRANS_SF")
public class TransSfInfo extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -5511040730495995423L;
    /**
     * PK
     */
    private Long id;
    /**
     * 寄件方客户卡号
     */
    private String jCustid;
    /**
     * 寄件方顾客编码
     */
    private String jCusttag;
    /**
     * 校验字段，用于校验客户系统请求是否合法
     */
    private String checkword;
    /**
     * 默认使用
     */
    private Boolean isDefault;
    private String specialUse;
    /**
     * 特殊定制模板，目前仅限补寄发票
     */
    private String template;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_twts", sequenceName = "s_T_WH_TRANS_Sf", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_twts")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "jcustid", length = 50)
    public String getjCustid() {
        return jCustid;
    }

    public void setjCustid(String jCustid) {
        this.jCustid = jCustid;
    }

    @Column(name = "jcusttag", length = 50)
    public String getjCusttag() {
        return jCusttag;
    }

    public void setjCusttag(String jCusttag) {
        this.jCusttag = jCusttag;
    }

    @Column(name = "checkword", length = 64)
    public String getCheckword() {
        return checkword;
    }

    public void setCheckword(String checkword) {
        this.checkword = checkword;
    }

    @Column(name = "is_default")
    public Boolean getisDefault() {
        return isDefault;
    }

    public void setisDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    @Column(name = "SPECIAL_USE")
    public String getSpecialUse() {
        return specialUse;
    }

    public void setSpecialUse(String specialUse) {
        this.specialUse = specialUse;
    }

    @Column(name = "TEMPLATE")
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }


}
