package com.jumbo.rmi.warehouse;

import java.io.Serializable;
import java.util.List;



/**
 * EBS反馈数据
 * 
 * @author jinlong.ke
 * 
 */
public class OrderResult implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2465806062199095923L;
    /**
     * 成功
     */
    public static final int STATUS_SUCCESS = 1;
    /**
     * 失败
     */
    public static final int STATUS_ERROR = 0;
    /**
     * 状态 1：成功 0：失败
     */
    private int status;
    /**
     * 原因
     */
    private String msg;
    /**
     * 接收的单据号
     */
    private String code;
    /**
     * 相关单据号
     */
    private String slipCode;
    /**
     * 数据类型
     */
    private int type;
    /**
     * 仓库ID
     */
    private Long whId;
    
    /**
     * 明细行
     */
    private List<OrderLine> lines;

    public Long getWhId() {
        return whId;
    }

    public void setWhId(Long whId) {
        this.whId = whId;
    }

    public final int getStatus() {
        return status;
    }

    public final void setStatus(int status) {
        this.status = status;
    }

    public final String getMsg() {
        return msg;
    }

    public final void setMsg(String msg) {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public void setLines(List<OrderLine> lines) {
        this.lines = lines;
    }
}
