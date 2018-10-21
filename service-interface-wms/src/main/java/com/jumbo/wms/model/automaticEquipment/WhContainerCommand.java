package com.jumbo.wms.model.automaticEquipment;


/**
 * 
 * @author jinlong.ke
 * 
 */
public class WhContainerCommand extends WhContainer {

    /**
     * 
     */
    private static final long serialVersionUID = -9222443048017060702L;
    /**
     * 占用作业单号(后续入库用到）
     */
    private String staCode;
    /**
     * 占用配货清单号
     */
    private String pickingListCode;
    /**
     * 占用二级批次号
     */
    private String pbCode;
    /**
     * 状态数值
     */
    private int statusIntValue;

    private String ouName;

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getPickingListCode() {
        return pickingListCode;
    }

    public void setPickingListCode(String pickingListCode) {
        this.pickingListCode = pickingListCode;
    }

    public String getPbCode() {
        return pbCode;
    }

    public void setPbCode(String pbCode) {
        this.pbCode = pbCode;
    }

    public int getStatusIntValue() {
        return statusIntValue;
    }

    public void setStatusIntValue(int statusIntValue) {
        this.statusIntValue = statusIntValue;
    }

    public String getOuName() {
        return ouName;
    }

    public void setOuName(String ouName) {
        this.ouName = ouName;
    }



}
