package com.jumbo.wms.model.vmi.ids;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"BatchID", "Result"})
@XmlRootElement(name = "Response")
public class OrderConfirm implements Serializable {


    private static final long serialVersionUID = -5555869182975057828L;

    @XmlElement(required = true)
    protected String BatchID = "";

    @XmlElement(required = true)
    protected List<ConfirmResult> Result = new ArrayList<ConfirmResult>();


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"ExternDocKey", "Success", "Reason", "Description"})
    public static class ConfirmResult implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = -1719376923914180753L;
        @XmlElement(required = true)
        protected String ExternDocKey = "";
        @XmlElement(required = true)
        protected String Success = "";
        @XmlElement(required = true)
        protected String Reason = "";
        @XmlElement(required = true)
        protected String Description = "";

        public String getExternDocKey() {
            return ExternDocKey;
        }

        public void setExternDocKey(String externDocKey) {
            ExternDocKey = externDocKey;
        }

        public String getSuccess() {
            return Success;
        }

        public void setSuccess(String success) {
            Success = success;
        }

        public String getReason() {
            return Reason;
        }

        public void setReason(String reason) {
            Reason = reason;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

    }


    public String getBatchID() {
        return BatchID;
    }


    public void setBatchID(String batchID) {
        BatchID = batchID;
    }


    public List<ConfirmResult> getResult() {
        return Result;
    }


    public void setResult(List<ConfirmResult> result) {
        Result = result;
    }



}
