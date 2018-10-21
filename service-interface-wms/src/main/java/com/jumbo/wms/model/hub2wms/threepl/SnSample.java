package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.List;

public class SnSample implements Serializable {

    private static final long serialVersionUID = -7982644894300739320L;

    /**
     * sn示例顺序
     */
    private String snSeq;
    /**
     * sn示例描述
     */
    private String sampleDesc;
    /**
     * 示例规则列表
     */
    private List<SampleRule> sampleRuleList;

    public String getSnSeq() {
        return snSeq;
    }

    public void setSnSeq(String snSeq) {
        this.snSeq = snSeq;
    }

    public String getSampleDesc() {
        return sampleDesc;
    }

    public void setSampleDesc(String sampleDesc) {
        this.sampleDesc = sampleDesc;
    }

    public List<SampleRule> getSampleRuleList() {
        return sampleRuleList;
    }

    public void setSampleRuleList(List<SampleRule> sampleRuleList) {
        this.sampleRuleList = sampleRuleList;
    }

}
