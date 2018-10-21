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
@XmlRootElement(name = "item")
public class Item implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2250377127046060580L;
    @XmlElement
    private String item;// <item>商品编码</item>
    @XmlElement
    private String description;// <description>商品名称</description>
    @XmlElement
    private String department;// <department>品牌中文描述</department>
    @XmlElement
    private String division;// <division>物品规格描述</division>
    @XmlElement
    private String long_description;// <long_description>质检标准</long_description>
    @XmlElement
    private String item_category1;// <item_category1>商品种类1</item_category1>
    @XmlElement
    private String item_category2;// <item_category2>商品种类2</item_category2>
    @XmlElement
    private String item_category3;// <item_category3>是否禁航</item_category3>
    @XmlElement
    private String item_category4;// <item_category4>商品种类4</item_category4>
    @XmlElement
    private String item_category5;// <item_category5>商品种类5</item_category5>
    @XmlElement
    private String item_category6;// <item_category6>商品种类6</item_category6>
    @XmlElement
    private String item_category7;// <item_category7>商品种类7</item_category7>
    @XmlElement
    private String item_category8;// <item_category8>商品种类8</item_category8>
    @XmlElement
    private String item_category9;// <item_category9>商品种类9</item_category9>
    @XmlElement
    private String item_category10;// <item_category10>商品种类10</item_category10>
    @XmlElement
    private String item_class;// <item_class>货品类</item_class>
    @XmlElement
    private String item_color;// <item_color>颜色</item_color>
    @XmlElement
    private String item_size;// <item_size>尺寸</item_size>
    @XmlElement
    private String storage_template;// <storage_template>存储模板</storage_template>
    @XmlElement
    private String item_style;// <item_style>保质期前置天数</item_style>
    @XmlElement
    private String x_ref_item_1;// <x_ref_item_1>条码1</x_ref_item_1>
    @XmlElement
    private String x_ref_item_2;// <x_ref_item_2>条码2</x_ref_item_2>
    @XmlElement
    private String x_ref_item_3;// <x_ref_item_3>条码3</x_ref_item_3>
    @XmlElement
    private String x_ref_item_4;// <x_ref_item_4>条码4</x_ref_item_4>
    @XmlElement
    private BigDecimal sequence_1;// <sequence_1>序号1</sequence_1>
    @XmlElement
    private BigDecimal conversion_qty_1;// <conversion_qty_1>数量单位1转换比例</conversion_qty_1>
    @XmlElement
    private BigDecimal height_1;// <height_1>高</height_1>
    @XmlElement
    private BigDecimal width_1;// <width_1>宽</width_1>
    @XmlElement
    private BigDecimal length_1;// <length_1>长</length_1>
    @XmlElement
    private BigDecimal weight_1;// <weight_1>重量</weight_1>
    @XmlElement
    private String quantity_um_1;// <quantity_um_1>货品数量单位1</quantity_um_1>
    @XmlElement
    private String weight_um_1;// <weight_um_1>重量单位1</weight_um_1>
    @XmlElement
    private String demension_um_1;// <dimension_um_1>尺寸单位1</dimension_um_1>
    @XmlElement
    private String treat_as_loose_1;// <treat_as_loose_1>包装单位1视为零头</treat_as_loose_1>
    @XmlElement
    private BigDecimal sequence_2;// <sequence_2>序号2</sequence_2>
    @XmlElement
    private BigDecimal conversion_qty_2;// <conversion_qty_2>数量单位2转换比例</conversion_qty_2>
    @XmlElement
    private BigDecimal height_2;// <height_2>高</height_2>
    @XmlElement
    private BigDecimal width_2;// <width_2>宽</width_2>
    @XmlElement
    private BigDecimal length_2;// <length_2>长</length_2>
    @XmlElement
    private BigDecimal weight_2;// <weight_2>重量</weight_2>
    @XmlElement
    private String quantity_um_2;// <quantity_um_2>货品数量单位2</quantity_um_2>
    @XmlElement
    private String weight_um_2;// <weight_um_2>重量单位2</weight_um_2>
    @XmlElement
    private String demension_um_2;// <dimension_um_2>尺寸单位2</dimension_um_2>
    @XmlElement
    private String treat_as_loose_2;// <treat_as_loose_2>包装单位2视为零头</treat_as_loose_2>
    @XmlElement
    private BigDecimal sequence_3;// <sequence_3>序号3</sequence_3>
    @XmlElement
    private BigDecimal conversion_qty_3;// <conversion_qty_3>数量单位3转换比例</conversion_qty_3>
    @XmlElement
    private BigDecimal height_3;// <height_3>高</height_3>
    @XmlElement
    private BigDecimal width_3;// <width_3>宽</width_3>
    @XmlElement
    private BigDecimal length_3;// <length_3>长</length_3>
    @XmlElement
    private BigDecimal weight_3;// <weight_3>重量</weight_3>
    @XmlElement
    private String quantity_um_3;// <quantity_um_3>货品数量单位3</quantity_um_3>
    @XmlElement
    private String weight_um_3;// <weight_um_3>重量单位3</weight_um_3>
    @XmlElement
    private String demension_um_3;// <dimension_um_3>尺寸单位3</dimension_um_3>
    @XmlElement
    private String treat_as_loose_3;// <treat_as_loose_3>包装单位3视为零头</treat_as_loose_3>
    @XmlElement
    private BigDecimal sequence_4;// <sequence_4>序号4</sequence_4>
    @XmlElement
    private BigDecimal conversion_qty_4;// <conversion_qty_4>数量单位4转换比例</conversion_qty_4>
    @XmlElement
    private BigDecimal height_4;// <height_4>高</height_4>
    @XmlElement
    private BigDecimal width_4;// <width_4>宽</width_4>
    @XmlElement
    private BigDecimal length_4;// <length_4>长</length_4>
    @XmlElement
    private BigDecimal weight_4;// <weight_4>重量</weight_4>
    @XmlElement
    private String quantity_um_4;// <quantity_um_4>货品数量单位4</quantity_um_4>
    @XmlElement
    private String weight_um_4;// <weight_um_4>重量单位4</weight_um_4>
    @XmlElement
    private String demension_um_4;// <dimension_um_4>尺寸单位4</dimension_um_4>
    @XmlElement
    private String treat_as_loose_4;// <treat_as_loose_4>包装单位4视为零头</treat_as_loose_4>
    @XmlElement
    private String lot_controlled;// <lot_controlled>是否批号控制</lot_controlled>
    @XmlElement
    private String lot_template;// <lot_template>批次模板</lot_template>
    @XmlElement
    private String serial_num_track_inbound;// <serial_num_track_inbound>入库序列号跟踪</serial_num_track_inbound>
    @XmlElement
    private String serial_num_track_outbound;// <serial_num_track_outbound>出库序列号跟踪</serial_num_track_outbound>
    @XmlElement
    private String bom_action;// <bom_action>是否BOM主件</bom_action>
    @XmlElement
    private String user_def1;// <user_def1>预留字段</user_def1>
    @XmlElement
    private String user_def2;// <user_def2>预留字段</user_def2>
    @XmlElement
    private String user_def3;// <user_def3>预留字段</user_def3>
    @XmlElement
    private String user_def4;// <user_def4>预留字段</user_def4>
    @XmlElement
    private String user_def5;// <user_def5>预留字段</user_def5>
    @XmlElement
    private String user_def6;// <user_def6>预留字段</user_def6>
    @XmlElement
    private BigDecimal user_def7;// <user_def7>预留字段</user_def7>
    @XmlElement
    private BigDecimal user_def8;// <user_def8>预留字段</user_def8>

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String longDescription) {
        long_description = longDescription;
    }

    public String getItem_category1() {
        return item_category1;
    }

    public void setItem_category1(String itemCategory1) {
        item_category1 = itemCategory1;
    }

    public String getItem_category2() {
        return item_category2;
    }

    public void setItem_category2(String itemCategory2) {
        item_category2 = itemCategory2;
    }

    public String getItem_category3() {
        return item_category3;
    }

    public void setItem_category3(String itemCategory3) {
        item_category3 = itemCategory3;
    }

    public String getItem_category4() {
        return item_category4;
    }

    public void setItem_category4(String itemCategory4) {
        item_category4 = itemCategory4;
    }

    public String getItem_category5() {
        return item_category5;
    }

    public void setItem_category5(String itemCategory5) {
        item_category5 = itemCategory5;
    }

    public String getItem_category6() {
        return item_category6;
    }

    public void setItem_category6(String itemCategory6) {
        item_category6 = itemCategory6;
    }

    public String getItem_category7() {
        return item_category7;
    }

    public void setItem_category7(String itemCategory7) {
        item_category7 = itemCategory7;
    }

    public String getItem_category8() {
        return item_category8;
    }

    public void setItem_category8(String itemCategory8) {
        item_category8 = itemCategory8;
    }

    public String getItem_category9() {
        return item_category9;
    }

    public void setItem_category9(String itemCategory9) {
        item_category9 = itemCategory9;
    }

    public String getItem_category10() {
        return item_category10;
    }

    public void setItem_category10(String itemCategory10) {
        item_category10 = itemCategory10;
    }

    public String getItem_class() {
        return item_class;
    }

    public void setItem_class(String itemClass) {
        item_class = itemClass;
    }

    public String getItem_color() {
        return item_color;
    }

    public void setItem_color(String itemColor) {
        item_color = itemColor;
    }

    public String getItem_size() {
        return item_size;
    }

    public void setItem_size(String itemSize) {
        item_size = itemSize;
    }

    public String getStorage_template() {
        return storage_template;
    }

    public void setStorage_template(String storageTemplate) {
        storage_template = storageTemplate;
    }

    public String getItem_style() {
        return item_style;
    }

    public void setItem_style(String itemStyle) {
        item_style = itemStyle;
    }

    public String getX_ref_item_1() {
        return x_ref_item_1;
    }

    public void setX_ref_item_1(String xRefItem_1) {
        x_ref_item_1 = xRefItem_1;
    }

    public String getX_ref_item_2() {
        return x_ref_item_2;
    }

    public void setX_ref_item_2(String xRefItem_2) {
        x_ref_item_2 = xRefItem_2;
    }

    public String getX_ref_item_3() {
        return x_ref_item_3;
    }

    public void setX_ref_item_3(String xRefItem_3) {
        x_ref_item_3 = xRefItem_3;
    }

    public String getX_ref_item_4() {
        return x_ref_item_4;
    }

    public void setX_ref_item_4(String xRefItem_4) {
        x_ref_item_4 = xRefItem_4;
    }

    public BigDecimal getSequence_1() {
        return sequence_1;
    }

    public void setSequence_1(BigDecimal sequence_1) {
        this.sequence_1 = sequence_1;
    }

    public BigDecimal getConversion_qty_1() {
        return conversion_qty_1;
    }

    public void setConversion_qty_1(BigDecimal conversionQty_1) {
        conversion_qty_1 = conversionQty_1;
    }

    public BigDecimal getHeight_1() {
        return height_1;
    }

    public void setHeight_1(BigDecimal height_1) {
        this.height_1 = height_1;
    }

    public BigDecimal getWidth_1() {
        return width_1;
    }

    public void setWidth_1(BigDecimal width_1) {
        this.width_1 = width_1;
    }

    public BigDecimal getLength_1() {
        return length_1;
    }

    public void setLength_1(BigDecimal length_1) {
        this.length_1 = length_1;
    }

    public BigDecimal getWeight_1() {
        return weight_1;
    }

    public void setWeight_1(BigDecimal weight_1) {
        this.weight_1 = weight_1;
    }

    public String getQuantity_um_1() {
        return quantity_um_1;
    }

    public void setQuantity_um_1(String quantityUm_1) {
        quantity_um_1 = quantityUm_1;
    }

    public String getWeight_um_1() {
        return weight_um_1;
    }

    public void setWeight_um_1(String weightUm_1) {
        weight_um_1 = weightUm_1;
    }

    public String getDemension_um_1() {
        return demension_um_1;
    }

    public void setDemension_um_1(String demensionUm_1) {
        demension_um_1 = demensionUm_1;
    }

    public String getTreat_as_loose_1() {
        return treat_as_loose_1;
    }

    public void setTreat_as_loose_1(String treatAsLoose_1) {
        treat_as_loose_1 = treatAsLoose_1;
    }

    public BigDecimal getSequence_2() {
        return sequence_2;
    }

    public void setSequence_2(BigDecimal sequence_2) {
        this.sequence_2 = sequence_2;
    }

    public BigDecimal getConversion_qty_2() {
        return conversion_qty_2;
    }

    public void setConversion_qty_2(BigDecimal conversionQty_2) {
        conversion_qty_2 = conversionQty_2;
    }

    public BigDecimal getHeight_2() {
        return height_2;
    }

    public void setHeight_2(BigDecimal height_2) {
        this.height_2 = height_2;
    }

    public BigDecimal getWidth_2() {
        return width_2;
    }

    public void setWidth_2(BigDecimal width_2) {
        this.width_2 = width_2;
    }

    public BigDecimal getLength_2() {
        return length_2;
    }

    public void setLength_2(BigDecimal length_2) {
        this.length_2 = length_2;
    }

    public BigDecimal getWeight_2() {
        return weight_2;
    }

    public void setWeight_2(BigDecimal weight_2) {
        this.weight_2 = weight_2;
    }

    public String getQuantity_um_2() {
        return quantity_um_2;
    }

    public void setQuantity_um_2(String quantityUm_2) {
        quantity_um_2 = quantityUm_2;
    }

    public String getWeight_um_2() {
        return weight_um_2;
    }

    public void setWeight_um_2(String weightUm_2) {
        weight_um_2 = weightUm_2;
    }

    public String getDemension_um_2() {
        return demension_um_2;
    }

    public void setDemension_um_2(String demensionUm_2) {
        demension_um_2 = demensionUm_2;
    }

    public String getTreat_as_loose_2() {
        return treat_as_loose_2;
    }

    public void setTreat_as_loose_2(String treatAsLoose_2) {
        treat_as_loose_2 = treatAsLoose_2;
    }

    public BigDecimal getSequence_3() {
        return sequence_3;
    }

    public void setSequence_3(BigDecimal sequence_3) {
        this.sequence_3 = sequence_3;
    }

    public BigDecimal getConversion_qty_3() {
        return conversion_qty_3;
    }

    public void setConversion_qty_3(BigDecimal conversionQty_3) {
        conversion_qty_3 = conversionQty_3;
    }

    public BigDecimal getHeight_3() {
        return height_3;
    }

    public void setHeight_3(BigDecimal height_3) {
        this.height_3 = height_3;
    }

    public BigDecimal getWidth_3() {
        return width_3;
    }

    public void setWidth_3(BigDecimal width_3) {
        this.width_3 = width_3;
    }

    public BigDecimal getLength_3() {
        return length_3;
    }

    public void setLength_3(BigDecimal length_3) {
        this.length_3 = length_3;
    }

    public BigDecimal getWeight_3() {
        return weight_3;
    }

    public void setWeight_3(BigDecimal weight_3) {
        this.weight_3 = weight_3;
    }

    public String getQuantity_um_3() {
        return quantity_um_3;
    }

    public void setQuantity_um_3(String quantityUm_3) {
        quantity_um_3 = quantityUm_3;
    }

    public String getWeight_um_3() {
        return weight_um_3;
    }

    public void setWeight_um_3(String weightUm_3) {
        weight_um_3 = weightUm_3;
    }

    public String getDemension_um_3() {
        return demension_um_3;
    }

    public void setDemension_um_3(String demensionUm_3) {
        demension_um_3 = demensionUm_3;
    }

    public String getTreat_as_loose_3() {
        return treat_as_loose_3;
    }

    public void setTreat_as_loose_3(String treatAsLoose_3) {
        treat_as_loose_3 = treatAsLoose_3;
    }

    public BigDecimal getSequence_4() {
        return sequence_4;
    }

    public void setSequence_4(BigDecimal sequence_4) {
        this.sequence_4 = sequence_4;
    }

    public BigDecimal getConversion_qty_4() {
        return conversion_qty_4;
    }

    public void setConversion_qty_4(BigDecimal conversionQty_4) {
        conversion_qty_4 = conversionQty_4;
    }

    public BigDecimal getHeight_4() {
        return height_4;
    }

    public void setHeight_4(BigDecimal height_4) {
        this.height_4 = height_4;
    }

    public BigDecimal getWidth_4() {
        return width_4;
    }

    public void setWidth_4(BigDecimal width_4) {
        this.width_4 = width_4;
    }

    public BigDecimal getLength_4() {
        return length_4;
    }

    public void setLength_4(BigDecimal length_4) {
        this.length_4 = length_4;
    }

    public BigDecimal getWeight_4() {
        return weight_4;
    }

    public void setWeight_4(BigDecimal weight_4) {
        this.weight_4 = weight_4;
    }

    public String getQuantity_um_4() {
        return quantity_um_4;
    }

    public void setQuantity_um_4(String quantityUm_4) {
        quantity_um_4 = quantityUm_4;
    }

    public String getWeight_um_4() {
        return weight_um_4;
    }

    public void setWeight_um_4(String weightUm_4) {
        weight_um_4 = weightUm_4;
    }

    public String getDemension_um_4() {
        return demension_um_4;
    }

    public void setDemension_um_4(String demensionUm_4) {
        demension_um_4 = demensionUm_4;
    }

    public String getTreat_as_loose_4() {
        return treat_as_loose_4;
    }

    public void setTreat_as_loose_4(String treatAsLoose_4) {
        treat_as_loose_4 = treatAsLoose_4;
    }

    public String getLot_controlled() {
        return lot_controlled;
    }

    public void setLot_controlled(String lotControlled) {
        lot_controlled = lotControlled;
    }

    public String getLot_template() {
        return lot_template;
    }

    public void setLot_template(String lotTemplate) {
        lot_template = lotTemplate;
    }

    public String getSerial_num_track_inbound() {
        return serial_num_track_inbound;
    }

    public void setSerial_num_track_inbound(String serialNumTrackInbound) {
        serial_num_track_inbound = serialNumTrackInbound;
    }

    public String getSerial_num_track_outbound() {
        return serial_num_track_outbound;
    }

    public void setSerial_num_track_outbound(String serialNumTrackOutbound) {
        serial_num_track_outbound = serialNumTrackOutbound;
    }

    public String getBom_action() {
        return bom_action;
    }

    public void setBom_action(String bomAction) {
        bom_action = bomAction;
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

    public BigDecimal getUser_def7() {
        return user_def7;
    }

    public void setUser_def7(BigDecimal userDef7) {
        user_def7 = userDef7;
    }

    public BigDecimal getUser_def8() {
        return user_def8;
    }

    public void setUser_def8(BigDecimal userDef8) {
        user_def8 = userDef8;
    }
}
