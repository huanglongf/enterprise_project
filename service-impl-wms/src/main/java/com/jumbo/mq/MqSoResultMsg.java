package com.jumbo.mq;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class MqSoResultMsg implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7206068560766079470L;

    /**
     * oms店铺ID
     */
    private Long omsShopId;

    /**
     * 订单号(oms)
     */
    private String soCode;

    /**
     * 商城订单编码
     */
    private String outerOrderCode;

    /**
     * 操作类型
     */
    private Integer opType; // 1:订单创建成功 2:订单取消 3:过单到仓库 5:销售出库 7:退货已入库 8:换货已入库 9:换货已出库 13:订单完成

    /**
     * 操作时间
     */
    private Date opTime;

    /**
     * 备注(相关明细信息拼接成的JSON字符串)
     */
    private String description;

    /**
     * 物流单号
     */
    private String transCode;

    /**
     * 物流商全称
     */
    private String transName;

    public Long getOmsShopId() {
        return omsShopId;
    }

    public void setOmsShopId(Long omsShopId) {
        this.omsShopId = omsShopId;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getOuterOrderCode() {
        return outerOrderCode;
    }

    public void setOuterOrderCode(String outerOrderCode) {
        this.outerOrderCode = outerOrderCode;
    }

    public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

}
