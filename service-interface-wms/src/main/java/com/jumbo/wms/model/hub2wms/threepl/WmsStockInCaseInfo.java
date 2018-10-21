package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WmsStockInCaseInfo implements Serializable {

    private static final long serialVersionUID = 1735653085059193966L;

    /**
     * 箱号
     */
    private String caseNumber;
    /**
     * 托盘号
     */
    private String palletNumebr;
    /**
     * 集装箱编号
     */
    private String containerNumber;
    /**
     * 重量 单位克
     */
    private Long weight;
    /**
     * 体积 单位立方厘米
     */
    private Integer volume;
    /**
     * 长 单位 mm
     */
    private Integer length;
    /**
     * 宽 单位 mm
     */
    private Integer width;
    /**
     * 高 单位 mm
     */
    private Integer height;
    /**
     * 装箱明细列表
     */
    private List<WmsStockInCaseItem> caseItemList;

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getPalletNumebr() {
        return palletNumebr;
    }

    public void setPalletNumebr(String palletNumebr) {
        this.palletNumebr = palletNumebr;
    }

    public String getContainerNumber() {
        return containerNumber;
    }

    public void setContainerNumber(String containerNumber) {
        this.containerNumber = containerNumber;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<WmsStockInCaseItem> getCaseItemList() {
        if (this.caseItemList == null) {
            this.caseItemList = new ArrayList<WmsStockInCaseItem>();
        }
        return caseItemList;
    }

    public void setCaseItemList(List<WmsStockInCaseItem> caseItemList) {
        this.caseItemList = caseItemList;
    }

}
