package com.jumbo.wms.web.commond;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jumbo.wms.model.BaseModel;

/**
 * 页面配货清单信息
 * 
 * @author sjk
 * 
 */
public class PickingListCommand extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -5549933669356014088L;

    // 配货清单Id
    private Long plId;
    // 配货清单code
    private String plCode;
    // 配货清单状态
    private Integer status;
    // 是否后置运单
    private Boolean isPostpositionExpressBill;
    // 关联作业单信息
    private List<OrderCheckCommand> orders;
    // 是否操作过报缺
    private Boolean isHaveReportMissing;
    // 商品产地
    private Map<String, Set<String>> originMap;

    public Long getPlId() {
        return plId;
    }

    public void setPlId(Long plId) {
        this.plId = plId;
    }

    public String getPlCode() {
        return plCode;
    }

    public void setPlCode(String plCode) {
        this.plCode = plCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<OrderCheckCommand> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderCheckCommand> orders) {
        this.orders = orders;
    }

    public Boolean getIsPostpositionExpressBill() {
        return isPostpositionExpressBill;
    }

    public void setIsPostpositionExpressBill(Boolean isPostpositionExpressBill) {
        this.isPostpositionExpressBill = isPostpositionExpressBill;
    }

    public Boolean getIsHaveReportMissing() {
        return isHaveReportMissing;
    }

    public void setIsHaveReportMissing(Boolean isHaveReportMissing) {
        this.isHaveReportMissing = isHaveReportMissing;
    }

    public Map<String, Set<String>> getOriginMap() {
        return originMap;
    }

    public void setOriginMap(Map<String, Set<String>> originMap) {
        this.originMap = originMap;
    }


}
