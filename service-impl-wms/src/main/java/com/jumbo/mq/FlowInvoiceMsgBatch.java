package com.jumbo.mq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"msgs", "batchNo"})
@XmlRootElement(name = "flowinvoicemsgbatch")
public class FlowInvoiceMsgBatch implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 589558717910327282L;

    @XmlElements({@XmlElement(name = "flowinvoicemsg", type = FlowInvoiceMsg.class), @XmlElement(name = "flowInvoiceprintMsg", type = FlowInvoicePrintMsg.class)})
    private List<Object> msgs;

    /**
     * 消息批次号
     */
    private Long batchNo;

    public List<Object> getMsgs() {
        if (msgs == null) {
            msgs = new ArrayList<Object>();
        }
        return msgs;
    }

    public void setMsgs(List<Object> msgs) {
        this.msgs = msgs;
    }

    public Long getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(Long batchNo) {
        this.batchNo = batchNo;
    }


}
