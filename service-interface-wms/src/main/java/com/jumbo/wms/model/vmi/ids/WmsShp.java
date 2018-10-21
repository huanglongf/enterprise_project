package com.jumbo.wms.model.vmi.ids;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"BatchID", "SHPHD"})
@XmlRootElement(name = "WMSSHP")
public class WmsShp implements Serializable {



    private static final long serialVersionUID = 6933028861292861572L;

    @XmlElement(required = true)
    protected String BatchID = "";

    @XmlElement(required = true)
    protected List<shpHd> SHPHD;



    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"StorerKey", "Facility", "ExternOrderKey", "M_Company", "SOStatus", "ShipperKey", "LoadKey", "Weight", "Cube", "EffectiveDate", "Mbol", "OrderInformation", "SHPDT"})
    public static class shpHd implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 2418485948001694331L;
        @XmlElement(required = true)
        protected String StorerKey = "";
        @XmlElement(required = true)
        protected String Facility = "";
        @XmlElement(required = true)
        protected String ExternOrderKey = "";
        @XmlElement(required = true)
        protected String M_Company = "";
        @XmlElement(required = true)
        protected String SOStatus = "";
        @XmlElement(required = true)
        protected String ShipperKey = "";
        @XmlElement(required = true)
        protected String LoadKey = "";
        @XmlElement(required = true)
        protected String Weight;
        @XmlElement
        protected String Cube;
        @XmlElement(required = true)
        protected String EffectiveDate = "";
        @XmlElement(required = true)
        protected Mbol Mbol;
        @XmlElement(required = true)
        protected OrderInformation OrderInformation;
        @XmlElement(required = true)
        protected List<SHPDT> SHPDT;

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

        public String getSOStatus() {
            return SOStatus;
        }

        public void setSOStatus(String sOStatus) {
            SOStatus = sOStatus;
        }

        public String getShipperKey() {
            return ShipperKey;
        }

        public void setShipperKey(String shipperKey) {
            ShipperKey = shipperKey;
        }

        public String getLoadKey() {
            return LoadKey;
        }

        public void setLoadKey(String loadKey) {
            LoadKey = loadKey;
        }

        public String getWeight() {
            return Weight;
        }

        public void setWeight(String weight) {
            Weight = weight;
        }

        public String getCube() {
            return Cube;
        }

        public void setCube(String cube) {
            Cube = cube;
        }

        public String getEffectiveDate() {
            return EffectiveDate;
        }

        public void setEffectiveDate(String effectiveDate) {
            EffectiveDate = effectiveDate;
        }

        public Mbol getMbol() {
            return Mbol;
        }

        public void setMbol(Mbol mbol) {
            Mbol = mbol;
        }

        public OrderInformation getOrderInformation() {
            return OrderInformation;
        }

        public void setOrderInformation(OrderInformation orderInformation) {
            OrderInformation = orderInformation;
        }

        public List<SHPDT> getSHPDT() {
            return SHPDT;
        }

        public void setSHPDT(List<SHPDT> sHPDT) {
            SHPDT = sHPDT;
        }


    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"Containers"})
    public static class Mbol implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 283115292071065413L;
        @XmlElement(required = true)
        protected Containers Containers;

        public Containers getContainers() {
            return Containers;
        }

        public void setContainers(Containers containers) {
            Containers = containers;
        }
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"Container"})
    public static class Containers implements Serializable {

        private static final long serialVersionUID = 577451502143160528L;

        @XmlElement(required = true)
        protected List<Container> Container;

        public List<Container> getContainer() {
            return Container;
        }

        public void setContainers(List<Container> container) {
            Container = container;
        }
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"CtnType", "CtnCnt"})
    public static class Container implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 8249426825390054549L;
        @XmlElement(required = true, name = "CtnType")
        protected String CtnType = "";
        @XmlElement(required = true, name = "CtnCnt")
        protected String CtnCnt = "";

        public String getCtnType() {
            return CtnType;
        }

        public void setCtnType(String ctnType) {
            CtnType = ctnType;
        }

        public String getCtnCnt() {
            return CtnCnt;
        }

        public void setCtnCnt(String ctnCnt) {
            CtnCnt = ctnCnt;
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"OrderInfo"})
    public static class OrderInformation implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = -5410282669350529843L;
        @XmlElement(required = true)
        protected List<OrderInfo> OrderInfo;

        public List<OrderInfo> getOrderInfo() {
            return OrderInfo;
        }

        public void setOrderInfo(List<OrderInfo> orderInfo) {
            OrderInfo = orderInfo;
        }


    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"OrderInfo_No", "OrderInfo_Amount", "OrderInfo_Type", "OrderInfo_Title", "OrderInfo_Content"})
    public static class OrderInfo implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 865125057058686039L;
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
    @XmlType(name = "", propOrder = {"ExternLineNo", "SKU", "ShippedQty"})
    public static class SHPDT implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = -5999950414695916147L;

        @XmlElement(required = true)
        protected String ExternLineNo;

        @XmlElement(required = true)
        protected String SKU;

        @XmlElement(required = true)
        protected int ShippedQty;

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

        public int getShippedQty() {
            return ShippedQty;
        }

        public void setShippedQty(int shippedQty) {
            ShippedQty = shippedQty;
        }


    }

    public String getBatchID() {
        return BatchID;
    }


    public void setBatchID(String batchID) {
        BatchID = batchID;
    }


    public List<shpHd> getSHPHD() {
        return SHPHD;
    }


    public void setSHPHD(List<shpHd> sHPHD) {
        SHPHD = sHPHD;
    }

}
