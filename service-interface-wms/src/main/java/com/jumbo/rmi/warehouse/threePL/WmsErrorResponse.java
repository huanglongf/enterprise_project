package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 外包仓反馈异常
 * 
 * @author jumbo
 * 
 */
public class WmsErrorResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String customer; // 客户来源
    private ErrorMsg message; // 错误信息

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public ErrorMsg getMessage() {
        return message;
    }

    public void setMessage(ErrorMsg message) {
        this.message = message;
    }

    public static class ErrorMsg implements Serializable {
        private static final long serialVersionUID = 2119952115708102594L;
        private String errorCode; // 错误编码
        private String msg; // 错误消息

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

    }
}
