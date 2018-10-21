package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 外包仓反馈请求参数
 * 
 * @author xiaolong.fei
 * 
 */
public class WmsMsgRtnInResponse implements Serializable {

    private static final long serialVersionUID = -4090296751995275796L;
    private String customer; // 客户来源
    private RtnResponseMessage message; // 反馈消息

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public RtnResponseMessage getMessage() {
        return message;
    }

    public void setMessage(RtnResponseMessage message) {
        this.message = message;
    }

    public static class RtnResponseMessage implements Serializable {

        private static final long serialVersionUID = 3477102386561542542L;
        private String uuid;
        private String status; // 1:成功 0：失败
        private String msg; // 备注

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

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
}
