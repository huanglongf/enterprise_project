package com.jumbo.wms.model.vmi.ids;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "v1:BaozunOrderRequest", propOrder = {"BatchID", "OrderHeader"})
@XmlRootElement(name = "v1:BaozunOrderRequest")
public class BaozunOrderRequest implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5048061996583371534L;

    // String requestParams =
    // " <v1:BaozunOrderRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:v1=\"http:/service.ae.aeo.com/enterprise/schema/baozunorder/v1\">";

    @XmlElement(required = true, name = "v1:BatchID")
    protected String BatchID = "";

    @XmlElement(required = true, name = "v1:OrderHeader")
    protected List<OrderHeader> OrderHeader;

    @XmlAttribute(required = true, name = "xmlns:v1")
    protected String server = "http:/service.ae.aeo.com/enterprise/schema/baozunorder/v1";

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "v1:OrderHeader", propOrder = {"InterfaceActionFlag", "StorerKey", "Facility", "ExternOrderKey", "M_Company", "Salesman", "DeliveryPlace", "Priority", "Type", "InvoiceAmount", "PrintFlag", "IntermodalVehicle", "ShipperKey",
            "Consignee", "OrderDate", "UserDefines", "OrderInformation", "ORDDT", "LabelPrice"})
    public static class OrderHeader implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 5824331090992401148L;
        @XmlElement(required = true, name = "v1:InterfaceActionFlag")
        protected String InterfaceActionFlag;
        @XmlElement(required = true, name = "v1:StorerKey")
        protected String StorerKey;
        @XmlElement(required = true, name = "v1:Facility")
        protected String Facility;
        @XmlElement(required = true, name = "v1:ExternOrderKey")
        protected String ExternOrderKey;
        @XmlElement(required = true, name = "v1:M_Company")
        protected String M_Company;
        @XmlElement(required = true, name = "v1:Salesman")
        protected String Salesman;
        @XmlElement(required = true, name = "v1:DeliveryPlace")
        protected String DeliveryPlace;
        @XmlElement(required = true, name = "v1:Priority")
        protected String Priority;
        @XmlElement(required = true, name = "v1:Type")
        protected String Type;
        @XmlElement(required = true, name = "v1:InvoiceAmount")
        protected BigDecimal InvoiceAmount;
        @XmlElement(required = true, name = "v1:PrintFlag")
        protected String PrintFlag;
        @XmlElement(required = true, name = "v1:IntermodalVehicle")
        protected String IntermodalVehicle;
        @XmlElement(required = true, name = "v1:ShipperKey")
        protected String ShipperKey;
        @XmlElement(required = true, name = "v1:Consignee")
        protected Consignee Consignee;
        @XmlElement(required = true, name = "v1:OrderDate")
        protected String OrderDate;
        @XmlElement(required = true, name = "v1:UserDefines")
        protected List<UserDefine> UserDefines;
        @XmlElement(required = true, name = "v1:OrderInformation")
        protected List<OrderInfo> OrderInformation;
        @XmlElement(required = true, name = "v1:BzOrderDtls")
        protected List<ORDDT> ORDDT;
        @XmlElement(required = true, name = "v1:LabelPrice")
        protected String LabelPrice;


        public List<ORDDT> getORDDT() {
            return ORDDT;
        }

        public void setORDDT(List<ORDDT> oRDDT) {
            ORDDT = oRDDT;
        }

        public String getLabelPrice() {
            return LabelPrice;
        }

        public void setLabelPrice(String labelPrice) {
            LabelPrice = labelPrice;
        }

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

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "v1:Consignee", propOrder = {"C_Contact1", "C_Zip", "C_Phone1", "C_Phone2", "C_State", "C_City", "C_Address1", "C_Address2"})
    public static class Consignee implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 5004154503791200417L;
        @XmlElement(required = true, name = "v1:C_Contact1")
        protected String C_Contact1 = "";
        @XmlElement(required = true, name = "v1:C_Zip")
        protected String C_Zip = "";
        @XmlElement(required = true, name = "v1:C_Phone1")
        protected String C_Phone1 = "";
        @XmlElement(required = true, name = "v1:C_Phone2")
        protected String C_Phone2 = "";
        @XmlElement(required = true, name = "v1:C_State")
        protected String C_State = "";
        @XmlElement(required = true, name = "v1:C_City")
        protected String C_City = "";
        @XmlElement(required = true, name = "v1:C_Address1")
        protected String C_Address1 = "";
        @XmlElement(required = true, name = "v1:C_Address2")
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
    @XmlType(propOrder = {"UserDefine"})
    public static class UserDefine implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 3929993339478809782L;
        @XmlElement(required = true, name = "v1:UserDefine")
        protected UserDefineInfo UserDefine = new UserDefineInfo();

        public UserDefineInfo getUserDefine() {
            return UserDefine;
        }

        public void setUserDefine(UserDefineInfo userDefine) {
            UserDefine = userDefine;
        }


    }



    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"UserDefine_No", "UserDefine_Value"})
    public static class UserDefineInfo implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 5109508374475382755L;
        @XmlElement(required = true, name = "v1:UserDefine_No")
        protected String UserDefine_No = "";
        @XmlElement(required = true, name = "v1:UserDefine_Value")
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
    @XmlType(name = "v1:OrderInfo", propOrder = {"OrderInfo"})
    public static class OrderInfo implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 5855786874326363251L;
        @XmlElement(required = true, name = "v1:OrderInfo")
        protected OrderInfoDetail OrderInfo = new OrderInfoDetail();

        public OrderInfoDetail getOrderInfo() {
            return OrderInfo;
        }

        public void setOrderInfo(OrderInfoDetail orderInfo) {
            OrderInfo = orderInfo;
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"OrderInfo_No", "OrderInfo_Amount", "OrderInfo_Type", "OrderInfo_Title", "OrderInfo_Content"})
    public static class OrderInfoDetail implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = -7804295473715596214L;
        @XmlElement(required = true, name = "v1:OrderInfo_No")
        protected String OrderInfo_No = "";
        @XmlElement(required = true, name = "v1:OrderInfo_Amount")
        protected BigDecimal OrderInfo_Amount;
        @XmlElement(required = true, name = "v1:OrderInfo_Type")
        protected String OrderInfo_Type = "";
        @XmlElement(required = true, name = "v1:OrderInfo_Title")
        protected String OrderInfo_Title = "";
        @XmlElement(required = true, name = "v1:OrderInfo_Content")
        protected String OrderInfo_Content = "";


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


    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"ExternLineNo", "SKU", "OpenQty", "UnitPrice", "UserDefines"})
    public static class ORDDT implements Serializable {

        private static final long serialVersionUID = -902496721712021790L;


        @XmlElement(required = true, name = "v1:ExternLineNo")
        protected String ExternLineNo = "";
        @XmlElement(required = true, name = "v1:SKU")
        protected String SKU = "";
        @XmlElement(required = true, name = "v1:OpenQty")
        protected int OpenQty;
        @XmlElement(required = true, name = "v1:UnitPrice")
        protected BigDecimal UnitPrice;
        @XmlElement(required = true, name = "v1:UserDefines")
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

    public List<OrderHeader> getOrderHeader() {
        return OrderHeader;
    }

    public void setOrderHeader(List<OrderHeader> orderHeader) {
        OrderHeader = orderHeader;
    }



}
