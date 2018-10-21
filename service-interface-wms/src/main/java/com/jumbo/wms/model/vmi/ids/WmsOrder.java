package com.jumbo.wms.model.vmi.ids;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.wms.model.command.Packages;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"BatchID", "ORDHD"})
@XmlRootElement(name = "WMSORD")
public class WmsOrder implements Serializable {


    private static final long serialVersionUID = -1107443692867542032L;

    @XmlElement(required = true)
    protected String BatchID = "";

    @XmlElement(required = true)
    protected List<ORDHD> ORDHD;


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"InterfaceActionFlag", "StorerKey", "Facility", "ExternOrderKey", "M_Company", "Salesman", "DeliveryPlace", "Priority", "Type", "InvoiceAmount", "PrintFlag", "IntermodalVehicle", "ShipperKey", "Consignee", "Notes",
            "OrderDate", "DeliveryNote", "UserDefines", "OrderInformation", "ORDDT", "LabelPrice", "BuyerPO", "C_Company"})
    public static class ORDHD implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 5824331090992401148L;
        @XmlElement(required = true)
        protected String InterfaceActionFlag;
        @XmlElement(required = true)
        protected String StorerKey;
        @XmlElement(required = true)
        protected String Facility;
        @XmlElement(required = true)
        protected String ExternOrderKey;
        @XmlElement(required = true)
        protected String M_Company;
        @XmlElement(required = true)
        protected String Salesman;
        @XmlElement(required = true)
        protected String DeliveryPlace;
        @XmlElement(required = true)
        protected String Priority;
        @XmlElement(required = true)
        protected String Type;
        @XmlElement(required = true)
        protected BigDecimal InvoiceAmount;
        @XmlElement(required = true)
        protected String PrintFlag;
        @XmlElement(required = true)
        protected String IntermodalVehicle;
        @XmlElement(required = true)
        protected String ShipperKey;
        @XmlElement(required = true)
        protected Consignee Consignee;
        @XmlElement(required = true)
        protected String Notes;
        @XmlElement(required = true)
        protected String LabelPrice;
        @XmlElement(required = true)
        protected String OrderDate;
        @XmlElement(required = true)
        protected String DeliveryNote;
        @XmlElement(required = true)
        protected String BuyerPO;
        @XmlElement(required = true)
        protected String C_Company;



        public String getBuyerPO() {
            return BuyerPO;
        }

        public void setBuyerPO(String buyerPO) {
            BuyerPO = buyerPO;
        }

        public String getC_Company() {
            return C_Company;
        }

        public void setC_Company(String c_Company) {
            C_Company = c_Company;
        }

        public String getDeliveryNote() {
            return DeliveryNote;
        }

        public void setDeliveryNote(String deliveryNote) {
            DeliveryNote = deliveryNote;
        }

        @XmlElement(required = true)
        protected List<UserDefine> UserDefines;
        @XmlElement(required = true)
        protected List<OrderInfo> OrderInformation;
        @XmlElement(required = true)
        protected List<ORDDT> ORDDT;

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

        public String getExternOrderKey() {
            return ExternOrderKey;
        }

        public void setExternOrderKey(String externOrderKey) {
            ExternOrderKey = externOrderKey;
        }

        public String getM_Company() {
            return M_Company;
        }

        public void setM_Company(String mCompany) {
            M_Company = mCompany;
        }

        public String getLabelPrice() {
            return LabelPrice;
        }

        public void setLabelPrice(String labelPrice) {
            LabelPrice = labelPrice;
        }

        public String getSalesman() {
            return Salesman;
        }

        public void setSalesman(String salesman) {
            Salesman = salesman;
        }

        public String getDeliveryPlace() {
            return DeliveryPlace;
        }

        public void setDeliveryPlace(String deliveryPlace) {
            DeliveryPlace = deliveryPlace;
        }

        public String getPriority() {
            return Priority;
        }

        public void setPriority(String priority) {
            Priority = priority;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        public BigDecimal getInvoiceAmount() {
            return InvoiceAmount;
        }

        public void setInvoiceAmount(BigDecimal invoiceAmount) {
            InvoiceAmount = invoiceAmount;
        }

        public String getPrintFlag() {
            return PrintFlag;
        }

        public void setPrintFlag(String printFlag) {
            PrintFlag = printFlag;
        }

        public String getIntermodalVehicle() {
            return IntermodalVehicle;
        }

        public void setIntermodalVehicle(String intermodalVehicle) {
            IntermodalVehicle = intermodalVehicle;
        }

        public String getShipperKey() {
            return ShipperKey;
        }

        public void setShipperKey(String shipperKey) {
            ShipperKey = shipperKey;
        }

        public Consignee getConsignee() {
            return Consignee;
        }

        public void setConsignee(Consignee consignee) {
            Consignee = consignee;
        }

        public String getNotes() {
            return Notes;
        }

        public void setNotes(String notes) {
            Notes = notes;
        }

        public String getOrderDate() {
            return OrderDate;
        }

        public void setOrderDate(String orderDate) {
            OrderDate = orderDate;
        }

        public List<UserDefine> getUserDefines() {
            return UserDefines;
        }

        public void setUserDefines(List<UserDefine> userDefines) {
            UserDefines = userDefines;
        }

        public List<OrderInfo> getOrderInformation() {
            return OrderInformation;
        }

        public void setOrderInformation(List<OrderInfo> orderInformation) {
            OrderInformation = orderInformation;
        }

        public List<ORDDT> getORDDT() {
            return ORDDT;
        }

        public void setORDDT(List<ORDDT> oRDDT) {
            ORDDT = oRDDT;
        }


    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"C_Contact1", "C_Zip", "C_Phone1", "C_Phone2", "C_State", "C_City", "C_Address1", "C_Address2"})
    public static class Consignee implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 5004154503791200417L;
        @XmlElement(required = true)
        protected String C_Contact1 = "";
        @XmlElement(required = true)
        protected String C_Zip = "";
        @XmlElement(required = true)
        protected String C_Phone1 = "";
        @XmlElement(required = true)
        protected String C_Phone2 = "";
        @XmlElement(required = true)
        protected String C_State = "";
        @XmlElement(required = true)
        protected String C_City = "";
        @XmlElement(required = true)
        protected String C_Address1 = "";
        @XmlElement(required = true)
        protected String C_Address2 = "";

        public String getC_Contact1() {
            return C_Contact1;
        }

        public void setC_Contact1(String cContact1) {
            C_Contact1 = cContact1;
        }

        public String getC_Zip() {
            return C_Zip;
        }

        public void setC_Zip(String cZip) {
            C_Zip = cZip;
        }

        public String getC_Phone1() {
            return C_Phone1;
        }

        public void setC_Phone1(String cPhone1) {
            C_Phone1 = cPhone1;
        }

        public String getC_Phone2() {
            return C_Phone2;
        }

        public void setC_Phone2(String cPhone2) {
            C_Phone2 = cPhone2;
        }

        public String getC_State() {
            return C_State;
        }

        public void setC_State(String cState) {
            C_State = cState;
        }

        public String getC_City() {
            return C_City;
        }

        public void setC_City(String cCity) {
            C_City = cCity;
        }

        public String getC_Address1() {
            return C_Address1;
        }

        public void setC_Address1(String cAddress1) {
            C_Address1 = cAddress1;
        }

        public String getC_Address2() {
            return C_Address2;
        }

        public void setC_Address2(String cAddress2) {
            C_Address2 = cAddress2;
        }


    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"UserDefine"})
    public static class UserDefine implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = -6981151134723458130L;
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
        private static final long serialVersionUID = 5109508374475382755L;
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
    @XmlType(name = "", propOrder = {"OrderInfo"})
    public static class OrderInfo implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 5855786874326363251L;
        @XmlElement(required = true)
        protected OrderInfoDetail OrderInfo = new OrderInfoDetail();

        public OrderInfoDetail getOrderInfo() {
            return OrderInfo;
        }

        public void setOrderInfo(OrderInfoDetail orderInfo) {
            OrderInfo = orderInfo;
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"OrderInfo_No", "OrderInfo_Amount", "OrderInfo_Type", "OrderInfo_Title", "OrderInfo_Content", "OrderInfo_Num", "OrderInfo_Address", "OrderInfo_Tel"})
    public static class OrderInfoDetail implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = -7804295473715596214L;
        @XmlElement(required = true)
        protected String OrderInfo_No = "";
        @XmlElement(required = true)
        protected BigDecimal OrderInfo_Amount;
        @XmlElement(required = true)
        protected String OrderInfo_Type = "";
        @XmlElement(required = true)
        protected String OrderInfo_Title = "";
        @XmlElement(required = true)
        protected String OrderInfo_Content = "";
        @XmlElement
        protected String OrderInfo_Num;
        @XmlElement
        protected String OrderInfo_Address;
        @XmlElement
        protected String OrderInfo_Tel;

        public String getOrderInfo_No() {
            return OrderInfo_No;
        }

        public void setOrderInfo_No(String orderInfoNo) {
            OrderInfo_No = orderInfoNo;
        }

        public BigDecimal getOrderInfo_Amount() {
            return OrderInfo_Amount;
        }

        public void setOrderInfo_Amount(BigDecimal orderInfoAmount) {
            OrderInfo_Amount = orderInfoAmount;
        }

        public String getOrderInfo_Type() {
            return OrderInfo_Type;
        }

        public void setOrderInfo_Type(String orderInfoType) {
            OrderInfo_Type = orderInfoType;
        }

        public String getOrderInfo_Title() {
            return OrderInfo_Title;
        }

        public void setOrderInfo_Title(String orderInfoTitle) {
            OrderInfo_Title = orderInfoTitle;
        }

        public String getOrderInfo_Content() {
            return OrderInfo_Content;
        }

        public void setOrderInfo_Content(String orderInfoContent) {
            OrderInfo_Content = orderInfoContent;
        }

        public String getOrderInfo_Num() {
            return OrderInfo_Num;
        }

        public void setOrderInfo_Num(String orderInfo_Num) {
            OrderInfo_Num = orderInfo_Num;
        }

        public String getOrderInfo_Address() {
            return OrderInfo_Address;
        }

        public void setOrderInfo_Address(String orderInfo_Address) {
            OrderInfo_Address = orderInfo_Address;
        }

        public String getOrderInfo_Tel() {
            return OrderInfo_Tel;
        }

        public void setOrderInfo_Tel(String orderInfo_Tel) {
            OrderInfo_Tel = orderInfo_Tel;
        }


    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"ExternLineNo", "SKU", "OpenQty", "UnitPrice", "UserDefines", "Packages"})
    public static class ORDDT implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = -613299928769061207L;
        @XmlElement(required = true)
        protected String ExternLineNo = "";
        @XmlElement(required = true)
        protected String SKU = "";
        @XmlElement(required = true)
        protected int OpenQty;
        @XmlElement(required = true)
        protected BigDecimal UnitPrice;
        @XmlElement(required = true)
        protected List<UserDefine> UserDefines;

        @XmlElement(required = true)
        protected List<Packages> Packages;

        public List<Packages> getPackages() {
            return Packages;
        }

        public void setPackages(List<Packages> packages) {
            Packages = packages;
        }

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

        public int getOpenQty() {
            return OpenQty;
        }

        public void setOpenQty(int openQty) {
            OpenQty = openQty;
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

    public List<ORDHD> getORDHD() {
        return ORDHD;
    }

    public void setORDHD(List<ORDHD> oRDHD) {
        ORDHD = oRDHD;
    }


}
