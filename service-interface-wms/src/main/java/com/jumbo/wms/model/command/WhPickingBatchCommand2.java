package com.jumbo.wms.model.command;

import com.jumbo.wms.model.automaticEquipment.WhPickingBatch;


public class WhPickingBatchCommand2 extends WhPickingBatch {

    private static final long serialVersionUID = -7452080450816144827L;

    private String userName;

    private Long pId;

    private String zoonName;

    private Long pQty;// 计划拣货数量

    private Long qty;// 实际拣货数量

    public Long getpQty() {
        return pQty;
    }

    public void setpQty(Long pQty) {
        this.pQty = pQty;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getZoonName() {
        return zoonName;
    }

    public void setZoonName(String zoonName) {
        this.zoonName = zoonName;
    }

}
