package com.jumbo.webservice.sfNew.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.jumbo.wms.model.BaseModel;

/**
 * 顺风下单实体
 * 
 * @author dly
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Order")
public class SfOrder extends BaseModel {

    public static final String ORDER_TYPE_STANDARD = "1"; // 1 标准快递
    public static final String ORDER_TYPE_SPECIAL_OFFER = "2"; // 2 顺丰特惠
    public static final String ORDER_TYPE_ELECTRIC = "3"; // 3 电商特惠
    public static final String ORDER_TYPE_SAME_DAY = "6"; // 6 当日达
    public static final String ORDER_TYPE_NEXT_MORNING = "5";// 次晨达

    public static final String SF_SERVICE_ORDER_TEMPLATE_COD = "COD";
    public static final String SF_SERVICE_ORDER_TEMPLATE_INSURE = "INSURE";


    public static final int SF_PAYMETHOD_TYPE_SEND_PARTY = 1;// 寄方付
    public static final int SF_PAYMETHOD_TYPE_DEBTI_PAID = 2;// 收方付
    public static final int SF_PAYMETHOD_TYPE_THIRD_PARTY_PAYMETHOD = 3;// 第三方付
    /**
	 * 
	 */
    private static final long serialVersionUID = 6666851157476649456L;

    public static final String SERVICE_NAME = "OrderService";
    /**
     * 订单号
     */
    @XmlAttribute(required = true, name = "orderid")
    private String orderId;
    /**
     * 快件产品类别 快件产品类别: 1 标准快递 2 顺丰特惠 3 电商特惠 5 次晨达 6 当日达
     */
    @XmlAttribute(required = true, name = "express_type")
    private String expressType;
    /**
     * 寄件方公司名称
     */
    @XmlAttribute(required = true, name = "j_company")
    private String jCompany = "_SYSTEM";
    /**
     * 寄件方联系人
     */
    @XmlAttribute(required = true, name = "j_contact")
    private String jContact;
    /**
     * 寄件方座机
     */
    @XmlAttribute(required = true, name = "j_tel")
    private String jTel;
    /**
     * 寄件方详细地址
     */
    @XmlAttribute(required = true, name = "j_address")
    private String jAddress;
    /**
     * 寄件方所在省份
     */
    @XmlAttribute(required = true, name = "j_province")
    private String jProvince;
    /**
     * 寄件方所属城市名称
     */
    @XmlAttribute(required = true, name = "j_city")
    private String jCity;
    /**
     * 到件方公司名称
     */
    @XmlAttribute(required = true, name = "d_company")
    private String dCompany;
    /**
     * 到件方联系人
     */
    @XmlAttribute(required = true, name = "d_contact")
    private String dContact;
    /**
     * 到件方座机
     */
    @XmlAttribute(required = true, name = "d_tel")
    private String dTel;
    /**
     * 到件方所在省份
     */
    @XmlAttribute(required = true, name = "d_province")
    private String dProvince;
    /**
     * 到件方所属城市名称
     */
    @XmlAttribute(required = true, name = "d_city")
    private String dCity;
    /**
     * 到件方地址
     */
    @XmlAttribute(required = true, name = "d_address")
    private String dAddress;
    /**
     * 包裹数量
     */
    @XmlAttribute(required = true, name = "parcel_quantity")
    private Integer parcelQuantity = 1;
    /**
     * 付款方式：1:寄方付 2:收方付 3:第三方付
     */
    @XmlAttribute(required = true, name = "pay_method")
    private Integer payMethod = 1;

    /**
     * 是否下 call 1-下 其他否 SYSTEM 表 示如果不提供，将从系统配置获取
     */
    @XmlAttribute(required = true, name = "is_docall")
    private Integer isDocall;
    /**
     * 是否申请运单号
     */
    @XmlAttribute(required = true, name = "is_gen_bill_no")
    private Integer isGenBillNo = 1;
    /**
     * 是否生成电子运单图片
     */
    @XmlAttribute(required = true, name = "is_gen_eletric_pic")
    private Integer isGenEletricPic;
    /**
     * 可选字段
     */
    @XmlElement(name = "OrderOption")
    private SfOrderOption orderOption;
    
    
    /**
     * declared_value_currency 申报价值币种
     * @return
     */
    @XmlAttribute(required = true, name = "declared_value_currency")
    private String declaredValueCurrency;
    
    /**
     * declared_value 申报价值
     * @return
     */
    @XmlAttribute(required = true, name = "declared_value")
    private BigDecimal declaredValue;
    
    

    public BigDecimal getDeclaredValue() {
        return declaredValue;
    }

    public void setDeclaredValue(BigDecimal declaredValue) {
        this.declaredValue = declaredValue;
    }

    public String getDeclaredValueCurrency() {
        return declaredValueCurrency;
    }

    public void setDeclaredValueCurrency(String declaredValueCurrency) {
        this.declaredValueCurrency = declaredValueCurrency;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getExpressType() {
        return expressType;
    }

    public void setExpressType(String expressType) {
        this.expressType = expressType;
    }

    public String getjCompany() {
        return jCompany;
    }

    public void setjCompany(String jCompany) {
        this.jCompany = jCompany;
    }

    public String getjContact() {
        return jContact;
    }

    public void setjContact(String jContact) {
        this.jContact = jContact;
    }

    public String getjTel() {
        return jTel;
    }

    public void setjTel(String jTel) {
        this.jTel = jTel;
    }

    public String getjAddress() {
        return jAddress;
    }

    public void setjAddress(String jAddress) {
        this.jAddress = jAddress;
    }

    public String getjProvince() {
        return jProvince;
    }

    public void setjProvince(String jProvince) {
        this.jProvince = jProvince;
    }

    public String getjCity() {
        return jCity;
    }

    public void setjCity(String jCity) {
        this.jCity = jCity;
    }

    public String getdCompany() {
        return dCompany;
    }

    public void setdCompany(String dCompany) {
        this.dCompany = dCompany;
    }

    public String getdContact() {
        return dContact;
    }

    public void setdContact(String dContact) {
        this.dContact = dContact;
    }

    public String getdTel() {
        return dTel;
    }

    public void setdTel(String dTel) {
        this.dTel = dTel;
    }

    public String getdProvince() {
        return dProvince;
    }

    public void setdProvince(String dProvince) {
        this.dProvince = dProvince;
    }

    public String getdCity() {
        return dCity;
    }

    public void setdCity(String dCity) {
        this.dCity = dCity;
    }

    public String getdAddress() {
        return dAddress;
    }

    public void setdAddress(String dAddress) {
        this.dAddress = dAddress;
    }

    public Integer getParcelQuantity() {
        return parcelQuantity;
    }

    public void setParcelQuantity(Integer parcelQuantity) {
        this.parcelQuantity = parcelQuantity;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public Integer getIsDocall() {
        return isDocall;
    }

    public void setIsDocall(Integer isDocall) {
        this.isDocall = isDocall;
    }

    public Integer getIsGenBillNo() {
        return isGenBillNo;
    }

    public void setIsGenBillNo(Integer isGenBillNo) {
        this.isGenBillNo = isGenBillNo;
    }

    public Integer getIsGenEletricPic() {
        return isGenEletricPic;
    }

    public void setIsGenEletricPic(Integer isGenEletricPic) {
        this.isGenEletricPic = isGenEletricPic;
    }

    public SfOrderOption getOrderOption() {
        return orderOption;
    }

    public void setOrderOption(SfOrderOption orderOption) {
        this.orderOption = orderOption;
    }
}
