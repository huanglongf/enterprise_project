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
@XmlType(name = "", propOrder = {"msgs", "batchNo", "batchGroupCode", "batchGroupTotal", "batchTotal", "type"})
@XmlRootElement(name = "mqmsglist")
public class MqMsgList implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 589558717910327282L;

    @XmlElements({@XmlElement(name = "mqdeliveryinfomsg", type = MqDeliveryInfoMsg.class), @XmlElement(name = "mqinventorymsg", type = MqInventoryMsg.class), @XmlElement(name = "mqskumsg", type = MqSkuMsg.class),
            @XmlElement(name = "mqskupricemsg", type = MqSkuPriceMsg.class), @XmlElement(name = "mqsolinemsg", type = MqSoLineMsg.class), @XmlElement(name = "mqsomsg", type = MqSoMsg.class),
            @XmlElement(name = "mqsoresultmsg", type = MqSoResultMsg.class), @XmlElement(name = "mqrefundmsg", type = MqRefundMsg.class), @XmlElement(name = "mqmdpricemsg", type = MqMdPriceMsg.class),
            @XmlElement(name = "mqbsskupricemsg", type = MqBSSkuPriceMsg.class), @XmlElement(name = "mqsopaymentinfomsg", type = MqSoPaymentInfoMsg.class), @XmlElement(name = "mqramsg", type = MqReturnApplicationMsg.class),
            @XmlElement(name = "mqomsshopinfomsg", type = MqOmsShopInfoMsg.class)})
    private List<Object> msgs;

    /**
     * 消息批次号
     */
    private Long batchNo;

    /**
     * 消息组编码(多个批次对应一个消息组)
     */
    private String batchGroupCode;

    /**
     * 消息组中总消息数
     */
    private Integer batchGroupTotal;

    /**
     * 当前消息批中决消息数
     */
    private Integer batchTotal;

    /**
     * 消息类型
     */
    private String type;

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

    public String getBatchGroupCode() {
        return batchGroupCode;
    }

    public void setBatchGroupCode(String batchGroupCode) {
        this.batchGroupCode = batchGroupCode;
    }

    public Integer getBatchGroupTotal() {
        return batchGroupTotal;
    }

    public void setBatchGroupTotal(Integer batchGroupTotal) {
        this.batchGroupTotal = batchGroupTotal;
    }

    public Integer getBatchTotal() {
        return batchTotal;
    }

    public void setBatchTotal(Integer batchTotal) {
        this.batchTotal = batchTotal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
