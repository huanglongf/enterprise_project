package com.jumbo.webservice.sfNew.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.jumbo.wms.model.BaseModel;

/**
 * 顺风订单发货确认
 * 
 * @author dly
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "OrderOption")
public class SfOrderOption extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 2185271835326050291L;
    /**
     * 月结卡号
     */
    @XmlAttribute(name = "custid")
    private String custid;
    /**
     * 模板选择
     */
    @XmlAttribute(name = "template")
    private String template;
    /**
     * 寄件 方代码
     */
    @XmlAttribute(name = "j_shippercode")
    private String jShippercode;
    /**
     * 到件 方代码
     */
    @XmlAttribute(name = "d_deliverycode")
    private String dDeliverycode;
    /**
     * 货物 名称，可有多个货物 名称，如果有多个货物以逗号分 ，如：“手机 ,IPAD, ,IPAD, ,IPAD, 充电器 充电器 ” 注： 如需生成电子运单， 则此字段必填
     */
    @XmlAttribute(name = "cargo")
    private String cargo = "宝尊商品";
    /**
     * 货物数量，多个时以逗号分隔且与 货物数量，且与 cargo 中描述 的货物相对应 如： 2,1,3 注：多个货物时，此项必填。
     */
    @XmlAttribute(name = "cargo_count")
    private String cargoCount;
    /**
     * 货物单位，多个时以逗号分隔且与 货物数量，且与 cargo 中描述 的货物相对应 如：个 ,台,本 注：多个货物时，此项必填。
     */
    @XmlAttribute(name = "cargo_unit")
    private String cargoUnit = "个";
    /**
     * 货物单位，多个时以逗号分隔且与 货物数量，且与 cargo 中描述 的货物相对应 如： 1.5,1.0,3.0 注：多个货物时，此项必填。
     */
    @XmlAttribute(name = "cargo_weight")
    private String cargoWeight;
    /**
     * 货物单位，多个时以逗号分隔且与 货物数量，且与 cargo 中描述 的货物相对应 如： 1000,2000,1500 注：多个货物时，此项必填。
     */
    @XmlAttribute(name = "cargo_amount")
    private String cargoAmount;
    /**
     * 订单货物总重量，单位 KG ，如果提供此值， 必须 >0
     */
    @XmlAttribute(name = "cargo_total_weight")
    private String cargoTotalWeight;
    /**
     * 要求上门取件开始时间，格式： YYYY-MM-DD HH24:MM:SS，示例： 2014-01-01 18:59:59， 默认为 系统收到订单的时间
     */
    @XmlAttribute(name = "sendstarttime")
    private String sendstarttime;
    /**
     * 运单号 ，一个订单只能有主号,如果是子母的情况，请以半角逗号分隔.
     */
    @XmlAttribute(name = "mailno")
    private String mailno;
    /**
     * 签回单单号
     */
    @XmlAttribute(name = "return_tracking")
    private String returnTracking;
    /**
     * 寄件方手机
     */
    @XmlAttribute(name = "j_mobile")
    private String jMobile;
    /**
     * 到件方手机
     */
    @XmlAttribute(name = "d_mobile")
    private String dMobile;
    /**
     * 寄件人所在县 /区，必须是标准的县 /区称谓，示例： “福 田区 ”（区字不要省略） （区字不要省略）
     */
    @XmlAttribute(name = "j_county")
    private String jCounty;
    /**
     * 到件人所在县 /区，必须是标准的县 /区称谓，示例： “福 田区 ”（区字不要省略） （区字不要省略）
     */
    @XmlAttribute(name = "d_county")
    private String dCounty;
    /**
     * 备注
     */
    @XmlAttribute(name = "remark")
    private String remark;
    /**
     * 是否需要签回单号 ，1：
     */
    @XmlAttribute(name = "need_return_tracking_no")
    private String needReturnTrackingNo;
    /**
     * 增值服务
     */
    @XmlElement(name = "AddedService")
    private List<SfAddedService> list;
    
    public String getCustid() {
        return custid;
    }
    public void setCustid(String custid) {
        this.custid = custid;
    }
    public String getTemplate() {
        return template;
    }
    public void setTemplate(String template) {
        this.template = template;
    }
    public String getjShippercode() {
        return jShippercode;
    }
    public void setjShippercode(String jShippercode) {
        this.jShippercode = jShippercode;
    }
    public String getdDeliverycode() {
        return dDeliverycode;
    }
    public void setdDeliverycode(String dDeliverycode) {
        this.dDeliverycode = dDeliverycode;
    }
    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    public String getCargoCount() {
        return cargoCount;
    }
    public void setCargoCount(String cargoCount) {
        this.cargoCount = cargoCount;
    }
    public String getCargoUnit() {
        return cargoUnit;
    }
    public void setCargoUnit(String cargoUnit) {
        this.cargoUnit = cargoUnit;
    }
    public String getCargoWeight() {
        return cargoWeight;
    }
    public void setCargoWeight(String cargoWeight) {
        this.cargoWeight = cargoWeight;
    }
    public String getCargoAmount() {
        return cargoAmount;
    }
    public void setCargoAmount(String cargoAmount) {
        this.cargoAmount = cargoAmount;
    }
    public String getCargoTotalWeight() {
        return cargoTotalWeight;
    }
    public void setCargoTotalWeight(String cargoTotalWeight) {
        this.cargoTotalWeight = cargoTotalWeight;
    }
    public String getSendstarttime() {
        return sendstarttime;
    }
    public void setSendstarttime(String sendstarttime) {
        this.sendstarttime = sendstarttime;
    }
    public String getMailno() {
        return mailno;
    }
    public void setMailno(String mailno) {
        this.mailno = mailno;
    }
    public String getReturnTracking() {
        return returnTracking;
    }
    public void setReturnTracking(String returnTracking) {
        this.returnTracking = returnTracking;
    }
    public String getjMobile() {
        return jMobile;
    }
    public void setjMobile(String jMobile) {
        this.jMobile = jMobile;
    }
    public String getdMobile() {
        return dMobile;
    }
    public void setdMobile(String dMobile) {
        this.dMobile = dMobile;
    }
    public String getjCounty() {
        return jCounty;
    }
    public void setjCounty(String jCounty) {
        this.jCounty = jCounty;
    }
    public String getdCounty() {
        return dCounty;
    }
    public void setdCounty(String dCounty) {
        this.dCounty = dCounty;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getNeedReturnTrackingNo() {
        return needReturnTrackingNo;
    }
    public void setNeedReturnTrackingNo(String needReturnTrackingNo) {
        this.needReturnTrackingNo = needReturnTrackingNo;
    }
    public List<SfAddedService> getList() {
        return list;
    }
    public void setList(List<SfAddedService> list) {
        this.list = list;
    }
}
