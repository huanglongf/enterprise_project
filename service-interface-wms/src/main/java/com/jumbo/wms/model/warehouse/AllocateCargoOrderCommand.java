package com.jumbo.wms.model.warehouse;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jumbo.util.StringUtils;

import com.jumbo.util.DateUtil;


public class AllocateCargoOrderCommand extends PickingList {
    /**
     * 配货清单查询
     */
    private static final long serialVersionUID = -2724556516803505854L;

    /**
     * 创建时间 form
     */
    private Date formCrtime;
    /**
     * 创建时间 to
     */
    private Date toCrtime;

    /**
     * 配货时间 form
     */
    private Date formPickingTime;
    /**
     * 配货时间 to
     */
    private Date toPickingTime;

    /**
     * 发货时间 form
     */
    private Date formOutBoundTime;
    /**
     * 发货时间 to
     */
    private Date toOutBoundTime;

    /**
     * 最后核对时间 form
     */
    private Date formCheckedTime;
    /**
     * 最后核对时间 to
     */
    private Date toCheckedTime;

    /**
     * 申请单编码
     */
    private String staCode;

    /**
     * 前置单据编码
     */
    private String refSlipCode;

    /**
     * 已发货单据数
     */
    private Long shipStaCount;
    /**
     * 已发货商品数
     */
    private Long shipSkuQty;

    /**
     * 创建人
     */
    private String createName;

    /**
     * 修改人
     */
    private String operUserName;

    /**
     * 配货模式
     */
    private Integer pkModeInt;


    private String owner;

    private Integer intStatus;

    private List<Integer> statusList;
    /*
     * 新增String类型时间 辅助完成查询时间精确到时分秒
     */
    private String wname;
    private String formCrtime1;
    private String toCrtime1;
    private String formPickingTime1;
    private String toPickingTime1;
    private String formOutBoundTime1;
    private String toOutBoundTime1;
    private String formCheckedTime1;
    private String toCheckedTime1;

    private String slipCode; //
    private String nodeType; // 状态
    private String userName; // 登陆名
    private Date executionTime; // 完成时间

    /**
     * 是否OTO批次
     */
    private Integer otoPick;



    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getOperUserName() {
        return operUserName;
    }

    public void setOperUserName(String operUserName) {
        this.operUserName = operUserName;
    }

    public Integer getPkModeInt() {
        return pkModeInt;
    }

    public void setPkModeInt(Integer pkModeInt) {
        this.pkModeInt = pkModeInt;
    }

    public Integer getOtoPick() {
        return otoPick;
    }

    public void setOtoPick(Integer otoPick) {
        this.otoPick = otoPick;
    }

    public Long getShipStaCount() {
        return shipStaCount;
    }

    public void setShipStaCount(Long shipStaCount) {
        this.shipStaCount = shipStaCount;
    }

    public Long getShipSkuQty() {
        return shipSkuQty;
    }

    public void setShipSkuQty(Long shipSkuQty) {
        this.shipSkuQty = shipSkuQty;
    }

    public Date getFormCrtime() {
        return formCrtime;
    }

    public void setFormCrtime(Date formCrtime) {
        this.formCrtime = formCrtime;
    }

    public Date getToCrtime() {
        return toCrtime;
    }

    public void setToCrtime(Date toCrtime) {
        this.toCrtime = toCrtime;
    }

    public Date getFormPickingTime() {
        return formPickingTime;
    }

    public void setFormPickingTime(Date formPickingTime) {
        this.formPickingTime = formPickingTime;
    }

    public Date getToPickingTime() {
        return toPickingTime;
    }

    public void setToPickingTime(Date toPickingTime) {
        this.toPickingTime = toPickingTime;
    }

    public Date getFormOutBoundTime() {
        return formOutBoundTime;
    }

    public void setFormOutBoundTime(Date formOutBoundTime) {
        this.formOutBoundTime = formOutBoundTime;
    }

    public Date getToOutBoundTime() {
        return toOutBoundTime;
    }

    public void setToOutBoundTime(Date toOutBoundTime) {
        this.toOutBoundTime = toOutBoundTime;
    }

    public Date getFormCheckedTime() {
        return formCheckedTime;
    }

    public void setFormCheckedTime(Date formCheckedTime) {
        this.formCheckedTime = formCheckedTime;
    }

    public Date getToCheckedTime() {
        return toCheckedTime;
    }

    public void setToCheckedTime(Date toCheckedTime) {
        this.toCheckedTime = toCheckedTime;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getRefSlipCode() {
        return refSlipCode;
    }

    public void setRefSlipCode(String refSlipCode) {
        this.refSlipCode = refSlipCode;
    }

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

    public Map<String, Object> toMapSqlParamer() {
        Map<String, Object> map = new HashMap<String, Object>();
        if (getWarehouse() != null) {
            map.put("ouId", getWarehouse().getId());
        }
        if (getPickingMode() != null) {
            map.put("pickingMode", getPickingMode().getValue());
        }


        if (StringUtils.hasText(getCode())) {
            map.put("code", getCode() + "%");
        }


        if (StringUtils.hasText(getStaCode())) {
            map.put("staCode", getStaCode() + "%");
        }


        if (StringUtils.hasText(getRefSlipCode())) {
            map.put("refSlipCode", getRefSlipCode() + "%");
        }

        if (getIntStatus() != null && getIntStatus() >= 0) {
            map.put("status", getIntStatus());
        } else if (statusList != null && statusList.size() > 0) {
            map.put("statusList", getStatusList());
        }

        if (getFormCrtime() != null) {
            map.put("fromCrtime", getFormCrtime());
        }

        if (getToCrtime() != null) {
            map.put("ToCrtime", DateUtil.addDays(getToCrtime(), 1));
        }

        if (getFormPickingTime() != null) {
            map.put("formPickingTime", getFormPickingTime());
        }

        if (getToPickingTime() != null) {
            map.put("toPickingTime", DateUtil.addDays(getToPickingTime(), 1));
        }

        if (getFormOutBoundTime() != null) {
            map.put("formOutBoundTime", getFormOutBoundTime());
        }

        if (getToOutBoundTime() != null) {
            map.put("toOutBoundTime", DateUtil.addDays(getToOutBoundTime(), 1));
        }

        if (getFormCheckedTime() != null) {
            map.put("formCheckedTime", getFormCheckedTime());
        }

        if (getToCheckedTime() != null) {
            map.put("toCheckedTime", DateUtil.addDays(getToCheckedTime(), 1));
        }


        if (StringUtils.hasText(createName)) {
            map.put("createName", createName + "%");
        }

        if (StringUtils.hasText(operUserName)) {
            map.put("operUserName", operUserName + "%");
        }

        if (StringUtils.hasText(owner)) {
            map.put("owner", owner);
        }

        if (pkModeInt != null) {
            map.put("pkModeInt", pkModeInt);
        }
        if (otoPick != null) {
            map.put("otoPick", otoPick);
        }
        return map;
    }

    public String getFormCrtime1() {
        return formCrtime1;
    }

    public void setFormCrtime1(String formCrtime1) {
        this.formCrtime1 = formCrtime1;
    }

    public String getToCrtime1() {
        return toCrtime1;
    }

    public void setToCrtime1(String toCrtime1) {
        this.toCrtime1 = toCrtime1;
    }

    public String getFormPickingTime1() {
        return formPickingTime1;
    }

    public void setFormPickingTime1(String formPickingTime1) {
        this.formPickingTime1 = formPickingTime1;
    }

    public String getToPickingTime1() {
        return toPickingTime1;
    }

    public void setToPickingTime1(String toPickingTime1) {
        this.toPickingTime1 = toPickingTime1;
    }

    public String getFormOutBoundTime1() {
        return formOutBoundTime1;
    }

    public void setFormOutBoundTime1(String formOutBoundTime1) {
        this.formOutBoundTime1 = formOutBoundTime1;
    }

    public String getToOutBoundTime1() {
        return toOutBoundTime1;
    }

    public void setToOutBoundTime1(String toOutBoundTime1) {
        this.toOutBoundTime1 = toOutBoundTime1;
    }

    public String getFormCheckedTime1() {
        return formCheckedTime1;
    }

    public void setFormCheckedTime1(String formCheckedTime1) {
        this.formCheckedTime1 = formCheckedTime1;
    }

    public String getToCheckedTime1() {
        return toCheckedTime1;
    }

    public void setToCheckedTime1(String toCheckedTime1) {
        this.toCheckedTime1 = toCheckedTime1;
    }

    @Override
    public Integer getIntStatus() {
        if (getStatus() == null) {
            return intStatus;
        }
        return getStatus().getValue();
    }

    @Override
    public void setIntStatus(Integer intStatus) {
        this.intStatus = intStatus;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }



}
