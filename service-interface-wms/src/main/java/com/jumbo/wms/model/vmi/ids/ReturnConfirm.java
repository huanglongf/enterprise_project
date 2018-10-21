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
@XmlType(name = "", propOrder = {"BatchID", "Results"})
@XmlRootElement(name = "Response")
public class ReturnConfirm implements Serializable {



    private static final long serialVersionUID = 8875890939750308381L;

    @XmlElement(required = true)
    protected String BatchID = "";

    @XmlElement(required = true)
    protected List<Results> Results = new ArrayList<Results>();

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"Result"})
    public static class Results implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 487380846799683735L;
        @XmlElement(required = true)
        protected List<Result> Result;

        public List<Result> getResult() {
            return Result;
        }

        public void setResult(List<Result> result) {
            Result = result;
        }
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"ExternDocKey", "Success", "Reason", "Description"})
    public static class Result implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = -7214894659094157753L;
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


    public List<Results> getResults() {
        return Results;
    }


    public void setResults(List<Results> results) {
        Results = results;
    }

}
