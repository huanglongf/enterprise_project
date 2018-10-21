package com.jumbo.webservice.sfNew.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.jumbo.wms.model.BaseModel;

/**
 * 顺风订单发货确认 可选数据
 * 
 * @author dly
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "OrderConfirmOption")
public class SfOrderConfirmOption extends BaseModel {

    private static final long serialVersionUID = 4268289603389575454L;
    /**
     * 订单重量，单位 KG
     */
    @XmlAttribute(name = "weight")
    private String weight;
    /**
     * 托寄物的 长,宽,高，以 半角 逗号分隔 ，单位 CM ，精确到小数点后一位
     */
    @XmlAttribute(name = "volume")
    private String volume;
    /**
     * 签回单单号
     */
    @XmlAttribute(name = "return_tracking")
    private Integer returnTracking;
    /**
     * 子单mailnos
     */
    @XmlAttribute(name = "children_nos")
    private String childrenNos;

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public Integer getReturnTracking() {
        return returnTracking;
    }

    public void setReturnTracking(Integer returnTracking) {
        this.returnTracking = returnTracking;
    }

    public String getChildrenNos() {
        return childrenNos;
    }

    public void setChildrenNos(String childrenNos) {
        this.childrenNos = childrenNos;
    }

}
