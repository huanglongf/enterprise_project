package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;

public class BatchSendCtrlParam implements Serializable {

    private static final long serialVersionUID = 1146064810990261803L;
    /**
     * 下发总共ITEM数
     */
    private Integer totalOrderItemCount;
    /**
     * 是否分批下发: 0 最后一次下发 1 多次发送
     */
    private Integer distributeType;

    public Integer getTotalOrderItemCount() {
        return totalOrderItemCount;
    }

    public void setTotalOrderItemCount(Integer totalOrderItemCount) {
        this.totalOrderItemCount = totalOrderItemCount;
    }

    public Integer getDistributeType() {
        return distributeType;
    }

    public void setDistributeType(Integer distributeType) {
        this.distributeType = distributeType;
    }

}
