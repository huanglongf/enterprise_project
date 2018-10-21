package com.jumbo.wms.model.command.expressRadar;

import com.jumbo.wms.model.expressRadar.RadarTransNo;

/**
 * @author lihui
 *
 *         2015年5月25日 下午4:44:25
 */
public class RadarTransNoCommand extends RadarTransNo {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7598210947927868953L;
    private Long radarWarningReasonId;
    private Long sysRadarWarningLvId;
    private Long physicalWarehouseId;
    private Long currentRouteStatusId;
    private Long tpId;// 物流商ID

    private String stayLanshou; // 待揽收
    private String hasLanshou;// 已揽收
    private String inTheTransif;// 中转中
    private String exceptionPiece; // 异常件
    private String totalPiece; // 合计
    private String city; // 市
    private String warehouseName; // 仓库名
    private String lpName; // 物流商

    private String lOverTime; // 揽件超时
    private String pOverTime; // 配货超时
    private String customerReason; // 客户原因
    private String changeL; // 送货上门变只提
    private String rejects; // 拒收
    private String spillage;// 货损
    private String shortage;// 货差

    public Long getRadarWarningReasonId() {
        return radarWarningReasonId;
    }

    public void setRadarWarningReasonId(Long radarWarningReasonId) {
        this.radarWarningReasonId = radarWarningReasonId;
    }

    public Long getSysRadarWarningLvId() {
        return sysRadarWarningLvId;
    }

    public void setSysRadarWarningLvId(Long sysRadarWarningLvId) {
        this.sysRadarWarningLvId = sysRadarWarningLvId;
    }

    public Long getPhysicalWarehouseId() {
        return physicalWarehouseId;
    }

    public void setPhysicalWarehouseId(Long physicalWarehouseId) {
        this.physicalWarehouseId = physicalWarehouseId;
    }

    public Long getCurrentRouteStatusId() {
        return currentRouteStatusId;
    }

    public void setCurrentRouteStatusId(Long currentRouteStatusId) {
        this.currentRouteStatusId = currentRouteStatusId;
    }

    public String getStayLanshou() {
        return stayLanshou;
    }

    public void setStayLanshou(String stayLanshou) {
        this.stayLanshou = stayLanshou;
    }


    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getHasLanshou() {
        return hasLanshou;
    }

    public void setHasLanshou(String hasLanshou) {
        this.hasLanshou = hasLanshou;
    }

    public String getInTheTransif() {
        return inTheTransif;
    }

    public void setInTheTransif(String inTheTransif) {
        this.inTheTransif = inTheTransif;
    }

    public String getExceptionPiece() {
        return exceptionPiece;
    }

    public void setExceptionPiece(String exceptionPiece) {
        this.exceptionPiece = exceptionPiece;
    }


    public String getTotalPiece() {
        return totalPiece;
    }

    public void setTotalPiece(String totalPiece) {
        this.totalPiece = totalPiece;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLpName() {
        return lpName;
    }

    public void setLpName(String lpName) {
        this.lpName = lpName;
    }

    public String getlOverTime() {
        return lOverTime;
    }

    public void setlOverTime(String lOverTime) {
        this.lOverTime = lOverTime;
    }

    public String getpOverTime() {
        return pOverTime;
    }

    public void setpOverTime(String pOverTime) {
        this.pOverTime = pOverTime;
    }

    public String getCustomerReason() {
        return customerReason;
    }

    public void setCustomerReason(String customerReason) {
        this.customerReason = customerReason;
    }

    public String getChangeL() {
        return changeL;
    }

    public void setChangeL(String changeL) {
        this.changeL = changeL;
    }



    public String getRejects() {
        return rejects;
    }

    public void setRejects(String rejects) {
        this.rejects = rejects;
    }

    public String getSpillage() {
        return spillage;
    }

    public void setSpillage(String spillage) {
        this.spillage = spillage;
    }

    public String getShortage() {
        return shortage;
    }

    public void setShortage(String shortage) {
        this.shortage = shortage;
    }

    public Long getTpId() {
        return tpId;
    }

    public void setTpId(Long tpId) {
        this.tpId = tpId;
    }



}
