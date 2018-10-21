package com.jumbo.wms.model.vmi.ids;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"BatchID", "ITRHD"})
@XmlRootElement(name = "WMSITR")
public class WmsItr implements Serializable {


    private static final long serialVersionUID = 4034611358264744237L;

    @XmlElement(required = true)
    protected String BatchID = "";

    @XmlElement(required = true)
    protected List<ITRHD> ITRHD;


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"ITRType", "WMSDocKey", "StorerKey", "Facility", "CustomerRefNo", "OtherRefNo", "EffectiveDate", "ReasonCode", "Remark", "ITRDT"})
    public static class ITRHD implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = -4990279330128742381L;

        @XmlElement(required = true)
        protected String ITRType;

        @XmlElement(required = true)
        protected String WMSDocKey;

        @XmlElement(required = true)
        protected String StorerKey;

        @XmlElement(required = true)
        protected String Facility;

        @XmlElement(required = true)
        protected String CustomerRefNo;

        @XmlElement(required = true)
        protected String OtherRefNo;

        @XmlElement(required = true)
        protected String EffectiveDate;

        @XmlElement(required = true)
        protected String ReasonCode;

        @XmlElement(required = true)
        protected String Remark;

        @XmlElement(required = true)
        protected List<ITRDT> ITRDT;

        public String getITRType() {
            return ITRType;
        }

        public void setITRType(String iTRType) {
            ITRType = iTRType;
        }

        public String getWMSDocKey() {
            return WMSDocKey;
        }

        public void setWMSDocKey(String wMSDocKey) {
            WMSDocKey = wMSDocKey;
        }

        public String getStorerKey() {
            return StorerKey;
        }

        public void setStorerKey(String storerKey) {
            StorerKey = storerKey;
        }

        public String getFacility() {
            return Facility;
        }

        public void setFacility(String facility) {
            Facility = facility;
        }

        public String getCustomerRefNo() {
            return CustomerRefNo;
        }

        public void setCustomerRefNo(String customerRefNo) {
            CustomerRefNo = customerRefNo;
        }

        public String getOtherRefNo() {
            return OtherRefNo;
        }

        public void setOtherRefNo(String otherRefNo) {
            OtherRefNo = otherRefNo;
        }

        public String getEffectiveDate() {
            return EffectiveDate;
        }

        public void setEffectiveDate(String effectiveDate) {
            EffectiveDate = effectiveDate;
        }

        public String getReasonCode() {
            return ReasonCode;
        }

        public void setReasonCode(String reasonCode) {
            ReasonCode = reasonCode;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }

        public List<ITRDT> getITRDT() {
            return ITRDT;
        }

        public void setITRDT(List<ITRDT> iTRDT) {
            ITRDT = iTRDT;
        }


    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"WMSDocLineNo", "SKU", "Qty", "HostWHCode"})
    public static class ITRDT implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = -4119834234613918073L;

        @XmlElement(required = true)
        protected String WMSDocLineNo;

        @XmlElement(required = true)
        protected String SKU;

        @XmlElement(required = true)
        protected int Qty;

        @XmlElement(required = true)
        protected String HostWHCode;

        public String getWMSDocLineNo() {
            return WMSDocLineNo;
        }

        public void setWMSDocLineNo(String wMSDocLineNo) {
            WMSDocLineNo = wMSDocLineNo;
        }

        public String getSKU() {
            return SKU;
        }

        public void setSKU(String sKU) {
            SKU = sKU;
        }

        public int getQty() {
            return Qty;
        }

        public void setQty(int qty) {
            Qty = qty;
        }

        public String getHostWHCode() {
            return HostWHCode;
        }

        public void setHostWHCode(String hostWHCode) {
            HostWHCode = hostWHCode;
        }
    }

    public String getBatchID() {
        return BatchID;
    }

    public void setBatchID(String batchID) {
        BatchID = batchID;
    }

    public List<ITRHD> getITRHD() {
        return ITRHD;
    }

    public void setITRHD(List<ITRHD> iTRHD) {
        ITRHD = iTRHD;
    }


}
