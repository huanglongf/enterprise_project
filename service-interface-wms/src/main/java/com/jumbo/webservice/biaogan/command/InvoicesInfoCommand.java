package com.jumbo.webservice.biaogan.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "invoiceInfo")
public class InvoicesInfoCommand implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5885611921515781694L;
    @XmlElement(required = true, name = "invoices")
    private List<InvoicesCommand> list = new ArrayList<InvoicesCommand>();

    public List<InvoicesCommand> getList() {
        return list;
    }

    public void setList(List<InvoicesCommand> list) {
        this.list = list;
    }
}
