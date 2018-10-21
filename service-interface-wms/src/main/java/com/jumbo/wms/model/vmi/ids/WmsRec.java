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
@XmlType(name = "", propOrder = {"BatchID", "RECHD"})
@XmlRootElement(name = "WMSREC")
public class WmsRec implements Serializable {


    private static final long serialVersionUID = -8253698766047087523L;

    @XmlElement(required = true)
    protected String BatchID = "";

    @XmlElement(required = true)
    protected List<RECHD> RECHD;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"StorerKey", "Facility", "ExternReceiptKey", "WarehouseReference", "ASNStatus", "UserDefines", "EffectiveDate", "RECDT"})
    public static class RECHD implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = -4042593199806738509L;
        @XmlElement(required = true)
        protected String StorerKey = "";
        @XmlElement(required = true)
        protected String Facility = "";
        @XmlElement(required = true)
        protected String ExternReceiptKey = "";
        @XmlElement(required = true)
        protected String WarehouseReference = "";
        @XmlElement(required = true)
        protected String ASNStatus = "";
        @XmlElement(required = true)
        protected UserDefine UserDefines;
        @XmlElement(required = true)
        protected String EffectiveDate = "";

        @XmlElement(required = true)
        protected List<RECDT> RECDT = new ArrayList<RECDT>();

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

        public String getExternReceiptKey() {
            return ExternReceiptKey;
        }

        public void setExternReceiptKey(String externReceiptKey) {
            ExternReceiptKey = externReceiptKey;
        }

        public String getWarehouseReference() {
            return WarehouseReference;
        }

        public void setWarehouseReference(String warehouseReference) {
            WarehouseReference = warehouseReference;
        }

        public String getASNStatus() {
            return ASNStatus;
        }

        public void setASNStatus(String aSNStatus) {
            ASNStatus = aSNStatus;
        }

        public UserDefine getUserDefines() {
            return UserDefines;
        }

        public void setUserDefines(UserDefine userDefines) {
            UserDefines = userDefines;
        }

        public String getEffectiveDate() {
            return EffectiveDate;
        }

        public void setEffectiveDate(String effectiveDate) {
            EffectiveDate = effectiveDate;
        }

        public List<RECDT> getRECDT() {
            return RECDT;
        }

        public void setRECDT(List<RECDT> rECDT) {
            RECDT = rECDT;
        }


    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"UserDefine_No", "UserDefine_Value"})
    public static class UserDefine implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 8373936696225822735L;
        @XmlElement(required = true)
        protected String UserDefine_No = "";
        @XmlElement(required = true)
        protected String UserDefine_Value = "";


        public String getUserDefine_No() {
            return UserDefine_No;
        }

        public void setUserDefine_No(String userDefineNo) {
            UserDefine_No = userDefineNo;
        }

        public String getUserDefine_Value() {
            return UserDefine_Value;
        }

        public void setUserDefine_Value(String userDefineValue) {
            UserDefine_Value = userDefineValue;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"ExternLineNo", "SKU", "QtyReceived","HostWHCode"})
    public static class RECDT implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 9036836704078176840L;
        @XmlElement(required = true)
        protected String ExternLineNo = "";
        @XmlElement(required = true)
        protected String SKU = "";
        @XmlElement(required = true)
        protected String QtyReceived = "";
        @XmlElement(required = true)
        protected String HostWHCode="";
       

        public String getExternLineNo() {
            return ExternLineNo;
        }

        public void setExternLineNo(String externLineNo) {
            ExternLineNo = externLineNo;
        }

        public String getSKU() {
            return SKU;
        }

        public void setSKU(String sKU) {
            SKU = sKU;
        }

        public String getQtyReceived() {
            return QtyReceived;
        }

        public void setQtyReceived(String qtyReceived) {
            QtyReceived = qtyReceived;
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

    public List<RECHD> getRECHD() {
        return RECHD;
    }

    public void setRECHD(List<RECHD> rECHD) {
        RECHD = rECHD;
    }


}
