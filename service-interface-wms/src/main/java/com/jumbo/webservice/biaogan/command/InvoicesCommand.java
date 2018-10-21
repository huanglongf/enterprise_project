package com.jumbo.webservice.biaogan.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "invoices")
public class InvoicesCommand implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6055744950037544098L;
    /**
     * 作业单号
     */
    @XmlElement(required = true, name = "soreference")
    private String soreference;
    /**
     * 公司名称
     */
    @XmlElement(required = true, name = "remark1")
    private String remark1 = "";

    @XmlElement(required = true, name = "remark2")
    private String remark2 = "";

    @XmlElement(required = true, name = "invoice")
    private List<InvoiceCommand> list = new ArrayList<InvoiceCommand>();

    public String getSoreference() {
        return soreference;
    }

    public void setSoreference(String soreference) {
        this.soreference = soreference;
    }

    public List<InvoiceCommand> getList() {
        return list;
    }

    public void setList(List<InvoiceCommand> list) {
        this.list = list;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

}
