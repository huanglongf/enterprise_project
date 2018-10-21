package com.jumbo.wms.model.automaticEquipment;

import java.io.Serializable;

/**
 * WCS消息反馈实体
 * 
 * @author xiaolong.fei
 * 
 */
public class MsgToWcsResponse implements Serializable {

    private static final long serialVersionUID = 855129973116381032L;
    /**
     * 状态 1：成功 0：失败
     */
    private String status;
    /**
     * 消息，失败时错误说明
     */
    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
