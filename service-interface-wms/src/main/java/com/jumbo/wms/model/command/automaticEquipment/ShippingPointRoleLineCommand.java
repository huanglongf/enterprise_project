package com.jumbo.wms.model.command.automaticEquipment;

import com.jumbo.wms.model.automaticEquipment.ShippingPointRoleLine;

/**
 * 
 * @author xiaolong.fei
 * 
 */
public class ShippingPointRoleLineCommand extends ShippingPointRoleLine {
    private static final long serialVersionUID = -1907560327541348478L;

    /**
     * 时效类型
     */
    private Integer timeTypes;
    /**
     * 时效类型
     */
    private String timeTypeStr;
    /**
     * 是否COD
     */
    private Integer isCods;
    /**
     * 是否COD
     */
    private String isCodStr;
    /**
     * 作业类型
     */
    private Integer types;

    /**
     * 作业类型
     */
    private String staType;

    /**
     * 集货口名称
     */
    private String pointName;

    /**
     * 集货口ID
     */
    private Long pointId;

    /**
     * 集货口编码
     */
    private String pointCode;

    public Integer getTimeTypes() {
        return timeTypes;
    }

    public void setTimeTypes(Integer timeTypes) {
        this.timeTypes = timeTypes;
    }

    public Integer getIsCods() {
        return isCods;
    }

    public void setIsCods(Integer isCods) {
        this.isCods = isCods;
    }

    public Integer getTypes() {
        return types;
    }

    public void setTypes(Integer types) {
        this.types = types;
    }

    public String getIsCodStr() {
        return isCodStr;
    }

    public void setIsCodStr(String isCodStr) {
        this.isCodStr = isCodStr;
    }

    public String getTimeTypeStr() {
        return timeTypeStr;
    }

    public void setTimeTypeStr(String timeTypeStr) {
        this.timeTypeStr = timeTypeStr;
    }

    public String getStaType() {
        return staType;
    }

    public void setStaType(String staType) {
        this.staType = staType;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Long getPointId() {
        return pointId;
    }

    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    public String getPointCode() {
        return pointCode;
    }

    public void setPointCode(String pointCode) {
        this.pointCode = pointCode;
    }

}
