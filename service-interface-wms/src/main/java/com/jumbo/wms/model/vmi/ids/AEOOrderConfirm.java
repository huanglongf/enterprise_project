package com.jumbo.wms.model.vmi.ids;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"BatchID", "Status", "ErrorCode", "AETransactionID", "ErrorReason"})
@XmlRootElement(name = "BaozunOrderResponse")
public class AEOOrderConfirm implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2165264110941544872L;

    @XmlElement(required = true, name = "BZBatchID")
    protected String BatchID;

    @XmlElement(required = true, name = "Status")
    protected String Status;

    @XmlElement(required = true, name = "ErrorCode")
    protected String ErrorCode;

    @XmlElement(required = true, name = "AETransactionID")
    protected String AETransactionID;

    @XmlElement(required = true, name = "ErrorReason")
    protected String ErrorReason;

    public String getErrorReason() {
        return ErrorReason;
    }

    public void setErrorReason(String errorReason) {
        ErrorReason = errorReason;
    }

    public String getBatchID() {
        return BatchID;
    }

    public void setBatchID(String batchID) {
        BatchID = batchID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public String getAETransactionID() {
        return AETransactionID;
    }

    public void setAETransactionID(String aETransactionID) {
        AETransactionID = aETransactionID;
    }

    public AEOOrderConfirm() {
        super();
    }

}
