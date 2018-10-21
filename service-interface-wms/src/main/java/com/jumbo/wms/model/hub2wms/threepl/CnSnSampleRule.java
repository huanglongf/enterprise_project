package com.jumbo.wms.model.hub2wms.threepl;

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
 * 菜鸟大宝SN号规则
 * 
 * @author hui.li
 * 
 */
@Entity
@Table(name = "T_WH_CN_SN_SAMPLE_RULE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CnSnSampleRule extends BaseModel {
    private static final long serialVersionUID = -2045353585393267193L;

    private Long id;// 主键
    /**
     * 规则描述
     */
    private String ruleDesc;
    /**
     * 规则正则表达式
     */
    private String ruleRegularExpression;
    /**
     * 规则对应图面url
     */
    private String ruleImgUrl;
    /**
     * 规则示例
     */
    private String ruleSample;

    /**
     * SN示例
     */
    private CnSnSample cnSnSample;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WCSSR", sequenceName = "S_T_WH_CN_SN_SAMPLE_RULE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WCSSR")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "RULE_DESC")
    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    @Column(name = "RULE_REGULAR_EXPRESSION")
    public String getRuleRegularExpression() {
        return ruleRegularExpression;
    }

    public void setRuleRegularExpression(String ruleRegularExpression) {
        this.ruleRegularExpression = ruleRegularExpression;
    }

    @Column(name = "RULE_IMG_URL")
    public String getRuleImgUrl() {
        return ruleImgUrl;
    }

    public void setRuleImgUrl(String ruleImgUrl) {
        this.ruleImgUrl = ruleImgUrl;
    }

    @Column(name = "RULE_SAMPLE")
    public String getRuleSample() {
        return ruleSample;
    }

    public void setRuleSample(String ruleSample) {
        this.ruleSample = ruleSample;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CN_SN_SAMPLE_ID")
    public CnSnSample getCnSnSample() {
        return cnSnSample;
    }

    public void setCnSnSample(CnSnSample cnSnSample) {
        this.cnSnSample = cnSnSample;
    }



}
