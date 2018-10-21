package com.jumbo.rmi.warehouse;

import java.io.Serializable;


/**
 * EBS反馈数据
 * 
 * @author jinlong.ke
 * 
 */
public class BaseResult implements Serializable {

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
     * 等待
     */
    public static final int STATUS_WAIT = 5;

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
     * 数据类型
     */
    private int type;
    /**
     * 仓库ID
     */
    private Long whId;

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
    
}
