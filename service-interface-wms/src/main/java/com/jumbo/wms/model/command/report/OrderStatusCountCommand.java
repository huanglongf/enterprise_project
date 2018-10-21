package com.jumbo.wms.model.command.report;

import com.jumbo.wms.model.BaseModel;

public class OrderStatusCountCommand extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 3557733952582629848L;

    private String whName;
    private String status;
    private String lpcode;
    private Long totalQty;

    /**
     * 取消状态数量
     */
    private Long statusCaneclQty;

    /**
     * 新建状态数量
     */
    private Long statusNewQty;
    /**
     * 占用状态数据
     */
    private Long statusOcpQty;
    /**
     * 建批状态数量
     */
    private Long statusBatchQty;
    /**
     * 核对状态数量
     */
    private Long statusCheckedQty;
    /**
     * 出库状态数量
     */
    private Long statusOutboundQty;
    /**
     * 完成状态数量
     */
    private Long statusFinishedQty;

    public String getWhName() {
        return whName;
    }

    public void setWhName(String whName) {
        this.whName = whName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    public Long getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Long totalQty) {
        this.totalQty = totalQty;
    }

    public Long getStatusNewQty() {
        return statusNewQty;
    }

    public void setStatusNewQty(Long statusNewQty) {
        this.statusNewQty = statusNewQty;
    }

    public Long getStatusOcpQty() {
        return statusOcpQty;
    }

    public void setStatusOcpQty(Long statusOcpQty) {
        this.statusOcpQty = statusOcpQty;
    }

    public Long getStatusBatchQty() {
        return statusBatchQty;
    }

    public void setStatusBatchQty(Long statusBatchQty) {
        this.statusBatchQty = statusBatchQty;
    }

    public Long getStatusCheckedQty() {
        return statusCheckedQty;
    }

    public void setStatusCheckedQty(Long statusCheckedQty) {
        this.statusCheckedQty = statusCheckedQty;
    }

    public Long getStatusOutboundQty() {
        return statusOutboundQty;
    }

    public void setStatusOutboundQty(Long statusOutboundQty) {
        this.statusOutboundQty = statusOutboundQty;
    }

    public Long getStatusFinishedQty() {
        return statusFinishedQty;
    }

    public void setStatusFinishedQty(Long statusFinishedQty) {
        this.statusFinishedQty = statusFinishedQty;
    }

    public Long getStatusCaneclQty() {
        return statusCaneclQty;
    }

    public void setStatusCaneclQty(Long statusCaneclQty) {
        this.statusCaneclQty = statusCaneclQty;
    }



}
