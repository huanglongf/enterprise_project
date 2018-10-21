package com.jumbo.wms.model.vmi.ids;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"BatchID", "ORDHD"})
@XmlRootElement(name = "WMSORD")
public class WmsPreOrder implements Serializable {


    private static final long serialVersionUID = -1107443692867542032L;

    @XmlElement(required = true)
    protected String BatchID = "";

    @XmlElement(required = true)
    protected List<ORDHD> ORDHD;


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


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"InterfaceActionFlag", "StorerKey", "Facility", "ExternOrderKey", "M_Company", "Notes"})
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
        protected String Notes;



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

    }

}
