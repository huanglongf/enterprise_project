package com.jumbo.webservice.biaogan.command;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Detail")
public class Detail implements Serializable {

    private static final long serialVersionUID = -3809581781490691884L;


    private String SkuCode;

    /**
     * 收货时间
     */
    private String ReceivedTime;

    /**
     * 预计入库数量
     */
    private Long ExpectedQty;

    /**
     * 实收数量
     */
    private Long ReceivedQty;

    /**
     * 批次属性01
     */
    private String Lotatt01;

    /**
     * 批次属性02
     */
    private String Lotatt02;

    /**
     * 批次属性03
     */
    private String Lotatt03;

    /**
     * 批次属性04
     */
    private String Lotatt04;

    /**
     * 批次属性05
     */
    private String Lotatt05;

    /**
     * 批次属性06
     */
    private String Lotatt06;

    /**
     * 批次属性07
     */
    private String Lotatt07;

    /**
     * 批次属性08
     */
    private String Lotatt08;

    /**
     * 批次属性09
     */
    private String Lotatt09;

    /**
     * 批次属性10
     */
    private String Lotatt10;

    /**
     * 批次属性11
     */
    private String Lotatt11;
    /**
     * 批次属性12
     */
    private String Lotatt12;

    public String getSkuCode() {
        return SkuCode;
    }

    public void setSkuCode(String skuCode) {
        SkuCode = skuCode;
    }

    public String getReceivedTime() {
        return ReceivedTime;
    }

    public void setReceivedTime(String receivedTime) {
        ReceivedTime = receivedTime;
    }

    public Long getExpectedQty() {
        return ExpectedQty;
    }

    public void setExpectedQty(Long expectedQty) {
        ExpectedQty = expectedQty;
    }

    public Long getReceivedQty() {
        return ReceivedQty;
    }

    public void setReceivedQty(Long receivedQty) {
        ReceivedQty = receivedQty;
    }

    public String getLotatt01() {
        return Lotatt01;
    }

    public void setLotatt01(String lotatt01) {
        Lotatt01 = lotatt01;
    }

    public String getLotatt02() {
        return Lotatt02;
    }

    public void setLotatt02(String lotatt02) {
        Lotatt02 = lotatt02;
    }

    public String getLotatt03() {
        return Lotatt03;
    }

    public void setLotatt03(String lotatt03) {
        Lotatt03 = lotatt03;
    }

    public String getLotatt04() {
        return Lotatt04;
    }

    public void setLotatt04(String lotatt04) {
        Lotatt04 = lotatt04;
    }

    public String getLotatt05() {
        return Lotatt05;
    }

    public void setLotatt05(String lotatt05) {
        Lotatt05 = lotatt05;
    }

    public String getLotatt06() {
        return Lotatt06;
    }

    public void setLotatt06(String lotatt06) {
        Lotatt06 = lotatt06;
    }

    public String getLotatt07() {
        return Lotatt07;
    }

    public void setLotatt07(String lotatt07) {
        Lotatt07 = lotatt07;
    }

    public String getLotatt08() {
        return Lotatt08;
    }

    public void setLotatt08(String lotatt08) {
        Lotatt08 = lotatt08;
    }

    public String getLotatt09() {
        return Lotatt09;
    }

    public void setLotatt09(String lotatt09) {
        Lotatt09 = lotatt09;
    }

    public String getLotatt10() {
        return Lotatt10;
    }

    public void setLotatt10(String lotatt10) {
        Lotatt10 = lotatt10;
    }

    public String getLotatt11() {
        return Lotatt11;
    }

    public void setLotatt11(String lotatt11) {
        Lotatt11 = lotatt11;
    }

    public String getLotatt12() {
        return Lotatt12;
    }

    public void setLotatt12(String lotatt12) {
        Lotatt12 = lotatt12;
    }

}
