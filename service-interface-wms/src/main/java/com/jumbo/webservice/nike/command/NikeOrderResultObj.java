package com.jumbo.webservice.nike.command;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA. User: hlz Date: 2010-8-16 Time: 15:02:45 To change this template use
 * File | Settings | File Templates.
 */
public class NikeOrderResultObj implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1997533254008446475L;
    private String code; // 业务单据号
    private String resultType; // 单据类型
    private Date fulfillTime; // 执行时间
    private String lpCode; // 物理供应商编码
    private String trackingNo; // 快递单号
    private Integer status; // 状态


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Date getFulfillTime() {
        return fulfillTime;
    }

    public void setFulfillTime(Date fulfillTime) {
        this.fulfillTime = fulfillTime;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    @Override
    public String toString() {
        return "NikeOrderResultObj [code=" + code + ", fulfillTime=" + fulfillTime + ", lpCode=" + lpCode + ", resultType=" + resultType + ", status=" + status + ", trackingNo=" + trackingNo + "]";
    }
}
