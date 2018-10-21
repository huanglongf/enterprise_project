package com.jumbo.webservice.sfwarehouse.command;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author jinlong.ke
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "header")
public class WmsSailOrderRequestHeader implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 3718248950214334153L;
    @XmlElement
    private String company;// <company>货主</company>
    @XmlElement
    private String warehouse;// <warehouse>仓库</warehouse>
    @XmlElement
    private String shop_name;// <shop_name>商家店铺名称</shop_name>
    @XmlElement
    private String erp_order;// <erp_order>订单号码</erp_order>
    @XmlElement
    private String order_type;// <order_type>订单类型</order_type>
    @XmlElement
    private String order_date;// <order_date>下单日期</order_date>
    @XmlElement
    private String ship_from_name;// <ship_from_name>寄件公司</ship_from_name>
    @XmlElement
    private String ship_from_attention_to;// <ship_from_attention_to>寄件人</ship_from_attention_to>
    @XmlElement
    private String ship_from_country;// <ship_from_country>寄件国家或地区</ship_from_country>
    @XmlElement
    private String ship_from_province;// <ship_from_province>寄件省</ship_from_province>
    @XmlElement
    private String ship_from_city;// <ship_from_city>寄件市</ship_from_city>
    @XmlElement
    private String ship_from_area;// <ship_from_area>寄件区/县</ship_from_area>
    @XmlElement
    private String ship_from_address;// <ship_from_address>寄件地址</ship_from_address>
    @XmlElement
    private String ship_from_postal_code;// <ship_from_postal_code>寄件邮编</ship_from_postal_code>
    @XmlElement
    private String ship_from_phone_num;// <ship_from_phone_num>寄件手机</ship_from_phone_num>
    @XmlElement
    private String ship_from_tel_num;// <ship_from_tel_num>寄件固定电话</ship_from_tel_num>
    @XmlElement
    private String ship_from_email_address;// <ship_from_email_address>寄件邮箱地址</ship_from_email_address>
    @XmlElement
    private String ship_to_name;// <ship_to_name>收件公司</ship_to_name>
    @XmlElement
    private String ship_to_attention_to;// <ship_to_attention_to>收件人</ship_to_attention_to>
    @XmlElement
    private String ship_to_country;// <ship_to_country>收件国家或地区</ship_to_country>
    @XmlElement
    private String ship_to_province;// <ship_to_province>收件省</ship_to_province>
    @XmlElement
    private String ship_to_city;// <ship_to_city>收件市</ship_to_city>
    @XmlElement
    private String ship_to_area;// <ship_to_area>收件区/县</ship_to_area>
    @XmlElement
    private String ship_to_address;// <ship_to_address>收件地址</ship_to_address>
    @XmlElement
    private String ship_to_postal_code;// <ship_to_postal_code>收件邮编</ship_to_postal_code>
    @XmlElement
    private String ship_to_phone_num;// <ship_to_phone_num>收件手机</ship_to_phone_num>
    @XmlElement
    private String ship_to_tel_num;// <ship_to_tel_num>收件固定电话</ship_to_tel_num>
    @XmlElement
    private String ship_to_email_address;// <ship_to_email_address>收件邮箱地址</ship_to_email_address>
    @XmlElement
    private String carrier;// <carrier>承运商</carrier>
    @XmlElement
    private String carrier_service;// <carrier_service>承运商服务类型</carrier_service>
    @XmlElement
    private String route_numbers;// <route_numbers>路线编号</route_numbers>
    @XmlElement
    private String packing_note;// <packing_note>货品包装备注</packing_note>
    @XmlElement
    private String complete_delivery;// <complete_delivery>需整单发货</complete_delivery>
    @XmlElement
    private BigDecimal freight;// <freight>运费</freight>
    @XmlElement
    private String payment_of_charge;// <payment_of_charge>货主运费付款方式</payment_of_charge>
    @XmlElement
    private String payment_district;// <payment_district>付款地区：城市名称</payment_district>
    @XmlElement
    private String cod;// <cod>是否货到付款</cod>
    @XmlElement
    private BigDecimal amount;// <amount>代收货款金额</amount>
    @XmlElement
    private String self_pickup;// <self_pickup>是否上门自取</self_pickup>
    @XmlElement
    private String value_insured;// <value_insured>是否保价</value_insured>
    @XmlElement
    private String declared_value;// <declared_value>声明价值</declared_value>
    @XmlElement
    private String return_receipt_service;// <return_receipt_service>签回单</return_receipt_service>
    @XmlElement
    private String delivery_date;// <delivery_date>发货日期</delivery_date>
    @XmlElement
    private String delivery_requested;// <delivery_requested>配送要求</delivery_requested>
    @XmlElement
    private String invoice;// <invoice>需要发票</invoice>
    @XmlElement
    private String invoice_type;// <invoice_type>发票类型</invoice_type>
    @XmlElement
    private String invoice_title;// <invoice_title>发票抬头</invoice_title>
    @XmlElement
    private String invoice_content;// <invoice_content>发票内容</invoice_content>
    @XmlElement
    private String order_note;// <order_note>订单备注</order_note>
    @XmlElement
    private String company_note;// <company_note>商家备注</company_note>
    @XmlElement
    private BigDecimal priority;// <priority>订单优先级</priority>
    @XmlElement
    private BigDecimal order_total_amount;// <order_total_amount>订单总金额</order_total_amount>
    @XmlElement
    private BigDecimal order_discount;// <order_discount>订单优惠金额</order_discount>
    @XmlElement
    private BigDecimal balance_amount;// <balance_amount>余额支付金额</balance_amount>
    @XmlElement
    private BigDecimal coupons_amount;// <coupons_amount>优惠卷金额</coupons_amount>
    @XmlElement
    private BigDecimal gift_card_amount;// <gift_card_amount>礼品卡金额</gift_card_amount>
    @XmlElement
    private BigDecimal other_charge;// <other_charge>其它金额</other_charge>
    @XmlElement
    private BigDecimal actual_amount;// <actual_amount>实际支付金额</actual_amount>
    @XmlElement
    private String customer_payment_method;// <customer_payment_method>客户付款方式</customer_payment_method>
    @XmlElement
    private String monthly_account;// <monthly_account>月结账号</monthly_account>
    @XmlElement
    private String from_flag;// <from_flag>寄件方标识</from_flag>
    @XmlElement
    private String user_def1;// <user_def1>预留字段1</user_def1>
    @XmlElement
    private String user_def2;// <user_def2>预留字段2</user_def2>
    @XmlElement
    private String user_def3;// <user_def3>预留字段3</user_def3>
    @XmlElement
    private String user_def4;// <user_def4>预留字段4</user_def4>
    @XmlElement
    private String user_def5;// <user_def5>预留字段5</user_def5>
    @XmlElement
    private String user_def6;// <user_def6>预留字段6</user_def6>
    @XmlElement
    private String user_def7;// <user_def7>预留字段7</user_def7>
    @XmlElement
    private String user_def8;// <user_def8>预留字段8</user_def8>
    @XmlElement
    private String user_def9;// <user_def9>预留字段9</user_def9>
    @XmlElement
    private String user_def10;// <user_def10>预留字段10</user_def10>
    @XmlElement
    private String user_def11;// <user_def11>预留字段11</user_def11>
    @XmlElement
    private String user_def12;// <user_def12>预留字段12</user_def12>
    @XmlElement
    private String user_def13;// <user_def13>预留字段13</user_def13>
    @XmlElement
    private String user_def14;// <user_def14>预留字段14</user_def14>
    @XmlElement
    private String user_def15;// <user_def15>预留字段15</user_def15>
    @XmlElement
    private String user_def16;// <user_def16>预留字段16</user_def16>
    @XmlElement
    private String user_def17;// <user_def17>预留字段17</user_def17>
    @XmlElement
    private String user_def18;// <user_def18>预留字段18</user_def18>
    @XmlElement
    private String user_def19;// <user_def19>预留字段19</user_def19>
    @XmlElement
    private String user_def20;// <user_def20>预留字段20</user_def20>
    @XmlElement
    private String user_def50;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shopName) {
        shop_name = shopName;
    }

    public String getErp_order() {
        return erp_order;
    }

    public void setErp_order(String erpOrder) {
        erp_order = erpOrder;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String orderType) {
        order_type = orderType;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String orderDate) {
        order_date = orderDate;
    }

    public String getShip_from_name() {
        return ship_from_name;
    }

    public void setShip_from_name(String shipFromName) {
        ship_from_name = shipFromName;
    }

    public String getShip_from_attention_to() {
        return ship_from_attention_to;
    }

    public void setShip_from_attention_to(String shipFromAttentionTo) {
        ship_from_attention_to = shipFromAttentionTo;
    }

    public String getShip_from_country() {
        return ship_from_country;
    }

    public void setShip_from_country(String shipFromCountry) {
        ship_from_country = shipFromCountry;
    }

    public String getShip_from_province() {
        return ship_from_province;
    }

    public void setShip_from_province(String shipFromProvince) {
        ship_from_province = shipFromProvince;
    }

    public String getShip_from_city() {
        return ship_from_city;
    }

    public void setShip_from_city(String shipFromCity) {
        ship_from_city = shipFromCity;
    }

    public String getShip_from_area() {
        return ship_from_area;
    }

    public void setShip_from_area(String shipFromArea) {
        ship_from_area = shipFromArea;
    }

    public String getShip_from_address() {
        return ship_from_address;
    }

    public void setShip_from_address(String shipFromAddress) {
        ship_from_address = shipFromAddress;
    }

    public String getShip_from_postal_code() {
        return ship_from_postal_code;
    }

    public void setShip_from_postal_code(String shipFromPostalCode) {
        ship_from_postal_code = shipFromPostalCode;
    }

    public String getShip_from_phone_num() {
        return ship_from_phone_num;
    }

    public void setShip_from_phone_num(String shipFromPhoneNum) {
        ship_from_phone_num = shipFromPhoneNum;
    }

    public String getShip_from_tel_num() {
        return ship_from_tel_num;
    }

    public void setShip_from_tel_num(String shipFromTelNum) {
        ship_from_tel_num = shipFromTelNum;
    }

    public String getShip_from_email_address() {
        return ship_from_email_address;
    }

    public void setShip_from_email_address(String shipFromEmailAddress) {
        ship_from_email_address = shipFromEmailAddress;
    }

    public String getShip_to_name() {
        return ship_to_name;
    }

    public void setShip_to_name(String shipToName) {
        ship_to_name = shipToName;
    }

    public String getShip_to_attention_to() {
        return ship_to_attention_to;
    }

    public void setShip_to_attention_to(String shipToAttentionTo) {
        ship_to_attention_to = shipToAttentionTo;
    }

    public String getShip_to_country() {
        return ship_to_country;
    }

    public void setShip_to_country(String shipToCountry) {
        ship_to_country = shipToCountry;
    }

    public String getShip_to_province() {
        return ship_to_province;
    }

    public void setShip_to_province(String shipToProvince) {
        ship_to_province = shipToProvince;
    }

    public String getShip_to_city() {
        return ship_to_city;
    }

    public void setShip_to_city(String shipToCity) {
        ship_to_city = shipToCity;
    }

    public String getShip_to_area() {
        return ship_to_area;
    }

    public void setShip_to_area(String shipToArea) {
        ship_to_area = shipToArea;
    }

    public String getShip_to_address() {
        return ship_to_address;
    }

    public void setShip_to_address(String shipToAddress) {
        ship_to_address = shipToAddress;
    }

    public String getShip_to_postal_code() {
        return ship_to_postal_code;
    }

    public void setShip_to_postal_code(String shipToPostalCode) {
        ship_to_postal_code = shipToPostalCode;
    }

    public String getShip_to_phone_num() {
        return ship_to_phone_num;
    }

    public void setShip_to_phone_num(String shipToPhoneNum) {
        ship_to_phone_num = shipToPhoneNum;
    }

    public String getShip_to_tel_num() {
        return ship_to_tel_num;
    }

    public void setShip_to_tel_num(String shipToTelNum) {
        ship_to_tel_num = shipToTelNum;
    }

    public String getShip_to_email_address() {
        return ship_to_email_address;
    }

    public void setShip_to_email_address(String shipToEmailAddress) {
        ship_to_email_address = shipToEmailAddress;
    }


    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getCarrier_service() {
        return carrier_service;
    }

    public void setCarrier_service(String carrierService) {
        carrier_service = carrierService;
    }

    public String getRoute_numbers() {
        return route_numbers;
    }

    public void setRoute_numbers(String routeNumbers) {
        route_numbers = routeNumbers;
    }

    public String getPacking_note() {
        return packing_note;
    }

    public void setPacking_note(String packingNote) {
        packing_note = packingNote;
    }

    public String getComplete_delivery() {
        return complete_delivery;
    }

    public void setComplete_delivery(String completeDelivery) {
        complete_delivery = completeDelivery;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public String getPayment_of_charge() {
        return payment_of_charge;
    }

    public void setPayment_of_charge(String paymentOfCharge) {
        payment_of_charge = paymentOfCharge;
    }

    public String getPayment_district() {
        return payment_district;
    }

    public void setPayment_district(String paymentDistrict) {
        payment_district = paymentDistrict;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSelf_pickup() {
        return self_pickup;
    }

    public void setSelf_pickup(String selfPickup) {
        self_pickup = selfPickup;
    }

    public String getValue_insured() {
        return value_insured;
    }

    public void setValue_insured(String valueInsured) {
        value_insured = valueInsured;
    }

    public String getDeclared_value() {
        return declared_value;
    }

    public void setDeclared_value(String declaredValue) {
        declared_value = declaredValue;
    }

    public String getReturn_receipt_service() {
        return return_receipt_service;
    }

    public void setReturn_receipt_service(String returnReceiptService) {
        return_receipt_service = returnReceiptService;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String deliveryDate) {
        delivery_date = deliveryDate;
    }

    public String getDelivery_requested() {
        return delivery_requested;
    }

    public void setDelivery_requested(String deliveryRequested) {
        delivery_requested = deliveryRequested;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(String invoiceType) {
        invoice_type = invoiceType;
    }

    public String getInvoice_title() {
        return invoice_title;
    }

    public void setInvoice_title(String invoiceTitle) {
        invoice_title = invoiceTitle;
    }

    public String getInvoice_content() {
        return invoice_content;
    }

    public void setInvoice_content(String invoiceContent) {
        invoice_content = invoiceContent;
    }

    public String getOrder_note() {
        return order_note;
    }

    public void setOrder_note(String orderNote) {
        order_note = orderNote;
    }

    public String getCompany_note() {
        return company_note;
    }

    public void setCompany_note(String companyNote) {
        company_note = companyNote;
    }

    public BigDecimal getPriority() {
        return priority;
    }

    public void setPriority(BigDecimal priority) {
        this.priority = priority;
    }

    public BigDecimal getOrder_total_amount() {
        return order_total_amount;
    }

    public void setOrder_total_amount(BigDecimal orderTotalAmount) {
        order_total_amount = orderTotalAmount;
    }

    public BigDecimal getOrder_discount() {
        return order_discount;
    }

    public void setOrder_discount(BigDecimal orderDiscount) {
        order_discount = orderDiscount;
    }

    public BigDecimal getBalance_amount() {
        return balance_amount;
    }

    public void setBalance_amount(BigDecimal balanceAmount) {
        balance_amount = balanceAmount;
    }

    public BigDecimal getCoupons_amount() {
        return coupons_amount;
    }

    public void setCoupons_amount(BigDecimal couponsAmount) {
        coupons_amount = couponsAmount;
    }

    public BigDecimal getGift_card_amount() {
        return gift_card_amount;
    }

    public void setGift_card_amount(BigDecimal giftCardAmount) {
        gift_card_amount = giftCardAmount;
    }

    public BigDecimal getOther_charge() {
        return other_charge;
    }

    public void setOther_charge(BigDecimal otherCharge) {
        other_charge = otherCharge;
    }

    public BigDecimal getActual_amount() {
        return actual_amount;
    }

    public void setActual_amount(BigDecimal actualAmount) {
        actual_amount = actualAmount;
    }

    public String getCustomer_payment_method() {
        return customer_payment_method;
    }

    public void setCustomer_payment_method(String customerPaymentMethod) {
        customer_payment_method = customerPaymentMethod;
    }

    public String getMonthly_account() {
        return monthly_account;
    }

    public void setMonthly_account(String monthlyAccount) {
        monthly_account = monthlyAccount;
    }

    public String getFrom_flag() {
        return from_flag;
    }

    public void setFrom_flag(String fromFlag) {
        from_flag = fromFlag;
    }

    public String getUser_def1() {
        return user_def1;
    }

    public void setUser_def1(String userDef1) {
        user_def1 = userDef1;
    }

    public String getUser_def2() {
        return user_def2;
    }

    public void setUser_def2(String userDef2) {
        user_def2 = userDef2;
    }

    public String getUser_def3() {
        return user_def3;
    }

    public void setUser_def3(String userDef3) {
        user_def3 = userDef3;
    }

    public String getUser_def4() {
        return user_def4;
    }

    public void setUser_def4(String userDef4) {
        user_def4 = userDef4;
    }

    public String getUser_def5() {
        return user_def5;
    }

    public void setUser_def5(String userDef5) {
        user_def5 = userDef5;
    }

    public String getUser_def6() {
        return user_def6;
    }

    public void setUser_def6(String userDef6) {
        user_def6 = userDef6;
    }

    public String getUser_def7() {
        return user_def7;
    }

    public void setUser_def7(String userDef7) {
        user_def7 = userDef7;
    }

    public String getUser_def8() {
        return user_def8;
    }

    public void setUser_def8(String userDef8) {
        user_def8 = userDef8;
    }

    public String getUser_def9() {
        return user_def9;
    }

    public void setUser_def9(String userDef9) {
        user_def9 = userDef9;
    }

    public String getUser_def10() {
        return user_def10;
    }

    public void setUser_def10(String userDef10) {
        user_def10 = userDef10;
    }

    public String getUser_def11() {
        return user_def11;
    }

    public void setUser_def11(String userDef11) {
        user_def11 = userDef11;
    }

    public String getUser_def12() {
        return user_def12;
    }

    public void setUser_def12(String userDef12) {
        user_def12 = userDef12;
    }

    public String getUser_def13() {
        return user_def13;
    }

    public void setUser_def13(String userDef13) {
        user_def13 = userDef13;
    }

    public String getUser_def14() {
        return user_def14;
    }

    public void setUser_def14(String userDef14) {
        user_def14 = userDef14;
    }

    public String getUser_def15() {
        return user_def15;
    }

    public void setUser_def15(String userDef15) {
        user_def15 = userDef15;
    }

    public String getUser_def16() {
        return user_def16;
    }

    public void setUser_def16(String userDef16) {
        user_def16 = userDef16;
    }

    public String getUser_def17() {
        return user_def17;
    }

    public void setUser_def17(String userDef17) {
        user_def17 = userDef17;
    }

    public String getUser_def18() {
        return user_def18;
    }

    public void setUser_def18(String userDef18) {
        user_def18 = userDef18;
    }

    public String getUser_def19() {
        return user_def19;
    }

    public void setUser_def19(String userDef19) {
        user_def19 = userDef19;
    }

    public String getUser_def20() {
        return user_def20;
    }

    public void setUser_def20(String userDef20) {
        user_def20 = userDef20;
    }

    public String getUser_def50() {
        return user_def50;
    }

    public void setUser_def50(String userDef50) {
        user_def50 = userDef50;
    }
}
