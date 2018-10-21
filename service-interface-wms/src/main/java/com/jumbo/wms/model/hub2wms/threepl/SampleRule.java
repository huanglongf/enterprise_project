package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;

public class SampleRule implements Serializable {

    private static final long serialVersionUID = 3616241671214136423L;
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

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public String getRuleRegularExpression() {
        return ruleRegularExpression;
    }

    public void setRuleRegularExpression(String ruleRegularExpression) {
        this.ruleRegularExpression = ruleRegularExpression;
    }

    public String getRuleImgUrl() {
        return ruleImgUrl;
    }

    public void setRuleImgUrl(String ruleImgUrl) {
        this.ruleImgUrl = ruleImgUrl;
    }

    public String getRuleSample() {
        return ruleSample;
    }

    public void setRuleSample(String ruleSample) {
        this.ruleSample = ruleSample;
    }

}
