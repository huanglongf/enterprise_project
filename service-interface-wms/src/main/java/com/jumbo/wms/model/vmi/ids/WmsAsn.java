package com.jumbo.wms.model.vmi.ids;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"BatchID", "ASNHD"})
@XmlRootElement(name = "WMSASN")
public class WmsAsn implements Serializable {



    private static final long serialVersionUID = -1998773588194658904L;

    @XmlElement(required = true)
    protected String BatchID = "";

    @XmlElement(required = true)
    protected List<ASNHD> ASNHD = new ArrayList<ASNHD>();


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"InterfaceActionFlag", "StorerKey", "Facility", "ExternReceiptKey", "WarehouseReference", "VehicleNumber", "Carrier", "DocType", "Notes", "UserDefines", "ASNDT"})
    public static class ASNHD implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = -7031140346322808377L;

        @XmlElement(required = true)
        protected String InterfaceActionFlag = "";

        @XmlElement(required = true)
        protected String StorerKey = "";

        @XmlElement(required = true)
        protected String Facility = "";

        @XmlElement(required = true)
        protected String ExternReceiptKey = "";

        @XmlElement(required = true)
        protected String WarehouseReference = "";

        @XmlElement(required = true)
        protected String VehicleNumber = "";

        @XmlElement(required = true)
        protected Carrier Carrier;

        @XmlElement(required = true)
        protected String DocType = "";

        @XmlElement(required = true)
        protected String Notes = " ";

        @XmlElement(required = true)
        protected List<UserDefine> UserDefines;

        @XmlElement(required = true)
        protected List<ASNDT> ASNDT = new ArrayList<ASNDT>();

        public String getInterfaceActionFlag() {
            return InterfaceActionFlag;
        }

        public void setInterfaceActionFlag(String interfaceActionFlag) {
            InterfaceActionFlag = interfaceActionFlag;
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

        public String getVehicleNumber() {
            return VehicleNumber;
        }

        public void setVehicleNumber(String vehicleNumber) {
            VehicleNumber = vehicleNumber;
        }

        public Carrier getCarrier() {
            return Carrier;
        }

        public void setCarrier(Carrier carrier) {
            Carrier = carrier;
        }

        public String getDocType() {
            return DocType;
        }

        public void setDocType(String docType) {
            DocType = docType;
        }

        public String getNotes() {
            return Notes;
        }

        public void setNotes(String notes) {
            Notes = notes;
        }

        public List<UserDefine> getUserDefines() {
            return UserDefines;
        }

        public void setUserDefines(List<UserDefine> userDefines) {
            UserDefines = userDefines;
        }

        public List<ASNDT> getASNDT() {
            return ASNDT;
        }

        public void setASNDT(List<ASNDT> aSNDT) {
            ASNDT = aSNDT;
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"CarrierKey", "CarrierName", "CarrierZip", "CarrierState", "CarrierCity", "CarrierAddress1", "CarrierAddress2"})
    public static class Carrier implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 8789105362573200564L;

        @XmlElement(required = true)
        protected String CarrierKey;

        @XmlElement(required = true)
        protected String CarrierName;

        @XmlElement(required = true)
        protected String CarrierZip;

        @XmlElement(required = true)
        protected String CarrierState;

        @XmlElement(required = true)
        protected String CarrierCity;

        @XmlElement(required = true)
        protected String CarrierAddress1;

        @XmlElement(required = true)
        protected String CarrierAddress2;

        public String getCarrierKey() {
            return CarrierKey;
        }

        public void setCarrierKey(String carrierKey) {
            CarrierKey = carrierKey;
        }

        public String getCarrierName() {
            return CarrierName;
        }

        public void setCarrierName(String carrierName) {
            CarrierName = carrierName;
        }

        public String getCarrierZip() {
            return CarrierZip;
        }

        public void setCarrierZip(String carrierZip) {
            CarrierZip = carrierZip;
        }

        public String getCarrierState() {
            return CarrierState;
        }

        public void setCarrierState(String carrierState) {
            CarrierState = carrierState;
        }

        public String getCarrierCity() {
            return CarrierCity;
        }

        public void setCarrierCity(String carrierCity) {
            CarrierCity = carrierCity;
        }

        public String getCarrierAddress1() {
            return CarrierAddress1;
        }

        public void setCarrierAddress1(String carrierAddress1) {
            CarrierAddress1 = carrierAddress1;
        }

        public String getCarrierAddress2() {
            return CarrierAddress2;
        }

        public void setCarrierAddress2(String carrierAddress2) {
            CarrierAddress2 = carrierAddress2;
        }



    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"UserDefine"})
    public static class UserDefine implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = -5856711181607770371L;
        @XmlElement(required = true)
        protected UserDefineInfo UserDefine = new UserDefineInfo();

        public UserDefineInfo getUserDefine() {
            return UserDefine;
        }

        public void setUserDefine(UserDefineInfo userDefine) {
            UserDefine = userDefine;
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"UserDefine_No", "UserDefine_Value"})
    public static class UserDefineInfo implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = -6649361586256286795L;
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
    @XmlType(name = "", propOrder = {"ExternLineNo", "SKU", "QtyExpected", "UnitPrice", "UserDefines"})
    public static class ASNDT implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 379204156273814632L;
        @XmlElement(required = true)
        protected String ExternLineNo = "";
        @XmlElement(required = true)
        protected String SKU = "";
        @XmlElement(required = true)
        protected String QtyExpected = "";
        @XmlElement(required = true)
        protected BigDecimal UnitPrice;
        @XmlElement(required = true)
        protected List<UserDefine> UserDefines;



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

        public String getQtyExpected() {
            return QtyExpected;
        }

        public void setQtyExpected(String qtyExpected) {
            QtyExpected = qtyExpected;
        }

        public BigDecimal getUnitPrice() {
            return UnitPrice;
        }

        public void setUnitPrice(BigDecimal unitPrice) {
            UnitPrice = unitPrice;
        }

        public List<UserDefine> getUserDefines() {
            return UserDefines;
        }

        public void setUserDefines(List<UserDefine> userDefines) {
            UserDefines = userDefines;
        }

    }

    public String getBatchID() {
        return BatchID;
    }

    public void setBatchID(String batchID) {
        BatchID = batchID;
    }

    public List<ASNHD> getASNHD() {
        return ASNHD;
    }

    public void setASNHD(List<ASNHD> aSNHD) {
        ASNHD = aSNHD;
    }


}
