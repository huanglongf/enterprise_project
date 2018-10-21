package com.jumbo.wms.model.warehouse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jumbo.util.StringUtils;

import com.jumbo.util.DateUtil;

public class ExpressOrderCommand extends StockTransApplication {
    /**
     * 快递单号修改
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
     * 物流商code
     */
    private String lpcode;
    /**
     * 物流商名称
     */
    private String lpName;
    /**
     * 快递单号
     */
    private String trackingNo;
    /**
     * 计划量
     */
    private Long planQty;
    /**
     * 创建人
     */
    private String crUser;
    /**
     * 店铺ID
     */
    private String shopId;
    /**
     * 仓库名称
     */
    private String wname;
    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname;
    }

    /*
     * 新增String时间字段 辅助完成查询时间精确到时分秒
     */
    private String formCrtime1;
    private String toCrtime1;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCrUser() {
        return crUser;
    }

    public void setCrUser(String crUser) {
        this.crUser = crUser;
    }

    public Long getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Long planQty) {
        this.planQty = planQty;
    }

    public String getLpName() {
        return lpName;
    }

    public void setLpName(String lpName) {
        this.lpName = lpName;
    }

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
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

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Map<String, Object> toMapSqlParamer() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mainWarehouse", getMainWarehouse().getId());

        if (getFormCrtime() != null) {
            map.put("fromCrtime", getFormCrtime());
        }

        if (getToCrtime() != null) {
            map.put("ToCrtime", DateUtil.addDays(getToCrtime(), 1));
        }

        if (StringUtils.hasText(getCode())) {
            map.put("code", getCode() + "%");
        }
        
        if (StringUtils.hasText(getRefSlipCode())) {
            map.put("refSlipCode", getRefSlipCode() + "%");
        }

        
        if (StringUtils.hasText(getLpcode())) {
            map.put("lpCode", getLpcode());// 物流商
        }

        if (StringUtils.hasText(getTrackingNo())) {
            map.put("trackingNo", getTrackingNo() + "%");// 快递单号
        }

        if (StringUtils.hasText(getOwner())) {
            map.put("owner", getOwner());// 店铺
        }

        if (StringUtils.hasText(getCrUser())) {
            map.put("crUser", getCrUser() + "%");
        }
        List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(3);
        map.put("statusList", statusList);
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
}
